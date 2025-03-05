package com.irc.BotSombra.utils;

import java.util.Random;

public class Verificador {
    public static boolean verificador(){
        Random rand = new Random();
        int num = rand.nextInt(100) + 1;
        if(num % 2 == 0 && rand.nextDouble() < 0.7){
            num++;
        }
        return num % 2 == 0;
    }
}
