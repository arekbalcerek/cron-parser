# cron-parser

Maven is needed to build and run the app (Maven 3.9.2 was used while working on the task).
The app was compiled and run using Open JDK 19.0.2

Basic standard cron expression is supported. Only numbers are supported (no support for string literals like MON, TUE, JAN, FEB etc.)

# Tests
There are some tests added covering basic cases. The tests are created in Spock. ParserSpec contains the tests. To execute the test just run the command:
mvn clean test

# Usage
To parse the expression run the command (exec.arguments - should be cron expression you want to parse):
mvn clean compile exec:java -Dexec.mainClass="com.arekb.cron.CronParser" -Dexec.arguments="* * * * * /scripts/script1.sh"