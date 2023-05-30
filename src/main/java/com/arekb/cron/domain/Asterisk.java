package com.arekb.cron.domain;

import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

class Asterisk implements Expression{

    private final Integer step;

    public Asterisk(Integer step) {
        this.step = step;
    }

    @Override
    public List<Integer> eval(int min, int max) {
        if (Objects.isNull(step)) {
            return IntStream.range(min, max + 1).boxed().toList();
        } else {
            return IntStream.iterate(min, c -> c <= max, c -> c + step).boxed().toList();
        }
    }
}
