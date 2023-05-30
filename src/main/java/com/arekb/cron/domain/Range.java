package com.arekb.cron.domain;

import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

class Range implements Expression {

    private final int lower;
    private final int upper;
    private final Integer step;

    public Range(int lower, int upper, Integer step) {
        if (lower > upper) {
            throw new IllegalArgumentException("Cannot parse the expression. " + lower + " is greater than upper " + upper);
        }
        this.lower = lower;
        this.upper = upper;
        this.step = step;
    }

    @Override
    public List<Integer> eval(int min, int max) {
        if (lower < min || lower > max) {
            throw new IllegalArgumentException("Cannot parse the expression. " + lower + " out of allowed values.");
        }
        if (upper < min || upper > max) {
            throw new IllegalArgumentException("Cannot parse the expression. " + upper + " out of allowed values.");
        }

        if (Objects.isNull(step)) {
            return IntStream.range(lower, upper + 1).boxed().toList();
        } else {
            return IntStream.iterate(lower, c -> c <= upper, c -> c + step).boxed().toList();
        }
    }
}
