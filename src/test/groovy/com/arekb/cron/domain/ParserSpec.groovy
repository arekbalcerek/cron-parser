package com.arekb.cron.domain

import spock.lang.Specification
import spock.lang.Unroll

class ParserSpec extends Specification {

    @Unroll
    def "should parse cron expression"(expression, minutes, hours, days, months, daysOfWeek, command) {
        given:
        def parser = new Parser(expression)

        when:
        def cronExpression = parser.parse()

        then:
        cronExpression.minutes == minutes
        cronExpression.hours == hours
        cronExpression.days == days
        cronExpression.months == months
        cronExpression.daysOfWeek == daysOfWeek
        cronExpression.command == command

        where:
        expression                                                                            | _
        "* * * * * /scripts/script1.sh"                                                       | _
        "*/4,*/3,4-9/3,5-10/2,22-23,59,4/10 5,6,7 1-5,11,31 */2,12 5,6,7 /scripts/script2.sh" | _
        __
        minutes                                                                                                                                                                                                                                | hours
        [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59] | [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23]
        [0, 3, 4, 5, 6, 7, 8, 9, 12, 14, 15, 16, 18, 20, 21, 22, 23, 24, 27, 28, 30, 32, 33, 34, 36, 39, 40, 42, 44, 45, 48, 51, 52, 54, 56, 57, 59]                                                                                           | [5, 6, 7]
        __
        days                                                                                                                | months
        [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31] | [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12]
        [1, 2, 3, 4, 5, 11, 31]                                                                                             | [1, 3, 5, 7, 9, 11, 12]
        __
        daysOfWeek             | command
        [1, 2, 3, 4, 5, 6, 7]  | "/scripts/script1.sh"
        [5, 6, 7]              | "/scripts/script2.sh"
    }

    @Unroll
    def "should not parse cron expression"(expression) {
        given:
        def parser = new Parser(expression)

        when:
        parser.parse()

        then:
        thrown IllegalArgumentException

        where:
        expression                          | _
        "* * * * /scripts/script1.sh"       | _
        "* * * * * * /scripts/script1.sh"   | _
        "*,4 * * * * /scripts/script1.sh"   | _
        "23-21 * * * * /scripts/script1.sh" | _
        "60 * * * * /scripts/script1.sh"    | _
        "* * 12,a * * /scripts/script1.sh"  | _
    }
}
