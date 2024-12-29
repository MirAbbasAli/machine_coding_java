package org.tictactoe.app.util;

import java.util.Arrays;
import java.util.stream.Collectors;

public class TransformUtils {
    public static String toTitleCase(String name){
        return Arrays.stream(name.toLowerCase().split(" "))
                .map(word -> word.substring(0,1).toUpperCase() + word.substring(1))
                .collect(Collectors.joining(" "));

    }
}
