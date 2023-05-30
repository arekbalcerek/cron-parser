package com.arekb.cron.domain;

import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

class Constant implements Expression{

    private final int value;
    private final Integer step;

    public Constant(int value, Integer step) {
        this.value = value;
        this.step = step;
    }

    @Override
    public List<Integer> eval(int min, int max) {
        if (value < min || value > max) {
            throw new IllegalArgumentException("Cannot parse the expression. " + value + " out of allowed values.");
        }
        if (Objects.isNull(step)) {
            return List.of(value);
        } else {
            return IntStream.iterate(value, c -> c <= max, c -> c + step).boxed().toList();
        }
    }
}
