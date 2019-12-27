package com.lteixeira.stringcaculator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class Calculator {

    public int add(String expression) {
        int result = 0;
        List<String> delimiters = parseDelimiters(expression);
        expression = cleanDelimiterLine(expression);
        if (expression.length() > 0) {
            List<String> tokenDelimiters = new ArrayList<>();
            tokenDelimiters.add(expression);
            for (String delimiter : delimiters) {
                List<String> tmp = new ArrayList<>();
                for (String token : tokenDelimiters) {
                    tmp.addAll(Arrays.asList(token.split(Pattern.quote(delimiter))));
                }
                tokenDelimiters = tmp;
            }
            List<String> tokens = new ArrayList<>();
            tokenDelimiters.forEach(tc -> tokens.addAll(Arrays.asList(tc.split("\n"))));
            if (tokens.size() > 1) {
                List<Integer> negatives = new ArrayList<>();
                for(String token : tokens) {
                    Integer number = Integer.parseInt(token);
                    if (number < 0) {
                        negatives.add(number);
                    }
                    if (number > 1000) {
                        continue;
                    }
                    result += number;
                }
                if (negatives.size() > 0) {
                    throwNegativeException(negatives);
                }
            } else {
                result = Integer.parseInt(expression);
            }
        }
        return result;
    }

    private String parseDelimiter(String expression) {
        String[] tokens = expression.split("\n");
        if (tokens[0].startsWith("//")) {
            int start = tokens[0].indexOf("[");
            int end = tokens[0].indexOf("]");
            if(start < 0) {
                return tokens[0].substring(2);
            }
            return tokens[0].substring(start + 1, end);
        }
        return ",";
    }

    private List<String> parseDelimiters(String expression) {
        List<String> delimiters = new ArrayList<>();
        String[] tokens = expression.split("\n");
        if (tokens[0].startsWith("//")) {
            int start = tokens[0].indexOf("[");
            int end = tokens[0].indexOf("]");
            if (start < 0) {
                delimiters.add(tokens[0].substring(2));
            }
            while (start > 0) {
                delimiters.add(tokens[0].substring(start + 1, end));
                start = tokens[0].indexOf("[", start + 1);
                end = tokens[0].indexOf("]", end + 1);
            }
            return delimiters;
        }
        delimiters.add(",");
        return delimiters;
    }

    private String cleanDelimiterLine(String expression) {
        String[] tokens = expression.split("\n");
        if (tokens[0].startsWith("//")) {
            int end = tokens[0].lastIndexOf("]");
            if(end < 0 ) {
                return expression.substring(4);
            }
            return expression.substring(end + 2);
        }
        return expression;
    }

    private void throwNegativeException(List<Integer> negativeNumbers) {
        String message = "negatives not allowed - ";
        for(Integer i : negativeNumbers) {
            message += i + " ";
        }
        throw new IllegalArgumentException(message);
    }
}
