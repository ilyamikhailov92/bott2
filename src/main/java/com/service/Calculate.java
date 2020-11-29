package com.service;

import java.io.IOException;

public class Calculate {
    public static Integer summ(Integer firstNumber, Integer secondNumber) throws IOException {
        int result = firstNumber + secondNumber;
        dbOps.addOp(Integer.toString(result));
        return result;
    }
}
