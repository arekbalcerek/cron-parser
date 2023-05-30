package com.arekb.cron.domain;

import java.util.List;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class CronExpression {

    private final List<Integer> minutes;
    private final List<Integer> hours;
    private final List<Integer> days;
    private final List<Integer> months;
    private final List<Integer> daysOfWeek;
    private final String command;

    public CronExpression(Expression minutes, Expression hours, Expression days, Expression months, Expression daysOfWeek, String command) {
        this.minutes = evaluateDeduplicateSort(minutes, 0, 59);
        this.hours = evaluateDeduplicateSort(hours, 0, 23);
        this.days = evaluateDeduplicateSort(days, 1, 31);
        this.months = evaluateDeduplicateSort(months, 1, 12);
        this.daysOfWeek = evaluateDeduplicateSort(daysOfWeek, 1, 7);
        this.command = command;
    }

    public List<Integer> getMinutes() {
        return minutes;
    }

    public List<Integer> getHours() {
        return hours;
    }

    public List<Integer> getDays() {
        return days;
    }

    public List<Integer> getMonths() {
        return months;
    }

    public List<Integer> getDaysOfWeek() {
        return daysOfWeek;
    }

    public String getCommand() {
        return command;
    }

    public void describe() {
        System.out.println("Minutes       " + toString(minutes));
        System.out.println("Hours         " + toString(hours));
        System.out.println("Days          " + toString(days));
        System.out.println("Months        " + toString(months));
        System.out.println("Days of week  " + toString(daysOfWeek));
        System.out.println("Command       " + command);
    }

    private String toString(List<Integer> values) {
        return values.stream()
                .map(Objects::toString)
                .collect(Collectors.joining(" "));
    }

    private List<Integer> evaluateDeduplicateSort(Expression expression, int min, int max) {
        SortedSet<Integer> output = new TreeSet<>(expression.eval(min, max));
        return output.stream().toList();
    }
}
