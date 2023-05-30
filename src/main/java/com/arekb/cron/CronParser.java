package com.arekb.cron;

import com.arekb.cron.domain.CronExpression;
import com.arekb.cron.domain.Parser;

public class CronParser {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Provide cron expression as a parameter.");
            System.exit(0);
        }
        Parser parser = new Parser(args[0]);
        CronExpression cronExpression = parser.parse();
        cronExpression.describe();
    }
}
