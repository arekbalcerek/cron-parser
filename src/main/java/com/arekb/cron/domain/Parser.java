package com.arekb.cron.domain;

import java.util.Objects;

public class Parser {

    private String input;
    private int position;

    public Parser(String input) {
        this.input = input + ' ';
    }

    public CronExpression parse() {
        Expression minutes = parseExpression();

        position++;
        Expression hours = parseExpression();

        position++;
        Expression days = parseExpression();

        position++;
        Expression months = parseExpression();

        position++;
        Expression daysOfWeek = parseExpression();

        position++;
        String command = parseCommand();

        if (position < input.length() - 1) {
            throw new IllegalArgumentException("Cannot parse the expression.");
        }

        return new CronExpression(minutes, hours, days, months, daysOfWeek, command);
    }

    private Expression parseExpression() {
        char c = lookAhead();
        if (c == '*' && lookNext() == ' ') {
            position++;
            return new Asterisk(null);
        }

        Expression finalExpression = parseListItem();
        c = lookAhead();

        while (c == ',') {
            position++;
            Expression currentExpression = parseListItem();
            finalExpression = new Composition(finalExpression, currentExpression);
            c = lookAhead();
        }

        if (c != ' ') {
            throw new IllegalArgumentException("Cannot parse the expression.");
        }
        return finalExpression;
    }

    private Expression parseListItem() {
        char c = lookAhead();
        if (c == '*') {
            position++;
            Integer step = parseStep();
            if (Objects.isNull(step)) {
                throw new IllegalArgumentException("Cannot parse the expression. '*' must not be used with other values.");
            }
            return new Asterisk(step);
        }

        int lower = parseConstant();
        Integer upper = null;
        c = lookAhead();
        if (c == '-') {
            position++;
            upper = parseConstant();
        }
        Integer step = parseStep();
        if (Objects.isNull(upper)) {
            return new Constant(lower, step);
        } else {
            return new Range(lower, upper, step);
        }
    }

    private Integer parseStep() {
        Integer step = null;
        char c = lookAhead();
        if (c == '/') {
            position++;
            step = parseConstant();
        }
        return step;
    }

    private int parseConstant() {
        int output = 0;
        char c = lookAhead();
        while (Character.isDigit(c)) {
            output *= 10;
            output += c - '0';
            position++;
            c = lookAhead();
        }
        return output;
    }

    private String parseCommand() {
        StringBuilder output = new StringBuilder();
        char c = lookAhead();
        while (c != ' ') {
            output.append(c);
            position++;
            c = lookAhead();
        }
        return output.toString();
    }

    private char lookAhead() {
        return input.charAt(position);
    }

    private char lookNext() {
        return input.charAt(position + 1);
    }
}
