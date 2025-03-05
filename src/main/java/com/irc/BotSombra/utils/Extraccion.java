package com.irc.BotSombra.utils;

public class Extraccion {
    public static String extraerHastaCaracter(String input, char caracter) {
        int index = input.indexOf(caracter);
        if (index != -1) {
            return input.substring(0, index);
        }
        return input;
    }
}
