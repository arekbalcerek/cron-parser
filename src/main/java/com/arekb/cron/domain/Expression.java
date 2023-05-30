package com.arekb.cron.domain;

import java.util.List;
interface Expression {
    List<Integer> eval(int min, int max);
}
