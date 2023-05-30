package com.arekb.cron.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Composition implements Expression {

    private Expression left;
    private Expression right;

    public Composition(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public List<Integer> eval(int min, int max) {
        List<Integer> output = new ArrayList<>(left.eval(min, max));
        output.addAll(right.eval(min, max));
        return Collections.unmodifiableList(output);
    }
}
