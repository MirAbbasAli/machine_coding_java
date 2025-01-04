package org.splitwise.app.util;

public class TransformUtils {
    public static Double roundOff(Double value){
        return Math.round(value * 100.0) / 100.0;
    }
}
