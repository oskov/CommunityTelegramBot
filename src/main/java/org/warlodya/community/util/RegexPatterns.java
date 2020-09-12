package org.warlodya.community.util;

import java.util.regex.Pattern;

public class RegexPatterns {
    public static final String MYSQL_DATE_REGEX = "\\d{4}-\\d{2}-\\d{2}";
    public static final String MYSQL_DATE_TIME_PATTERN_REGEX = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}";

    public static final Pattern MYSQL_DATE_PATTERN = Pattern.compile(MYSQL_DATE_REGEX);
    public static final Pattern MYSQL_DATE_TIME_PATTERN = Pattern.compile(MYSQL_DATE_TIME_PATTERN_REGEX);
}
