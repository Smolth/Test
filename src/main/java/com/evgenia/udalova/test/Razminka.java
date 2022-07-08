package com.evgenia.udalova.test;

import com.evgenia.udalova.test.service.RazminkaService;
import org.springframework.context.annotation.Configuration;

import java.util.Scanner;

@Configuration
public class Razminka {
    public static long time;
    public static void main(String[] args) {
        RazminkaService service = new RazminkaService();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter string, please");
        String string = scanner.nextLine();
        System.out.println(string + " -> "+ service.reverse(string));
        time = System.currentTimeMillis();

        for(int i=0; i<1000; i++) {
            service.reverse(string);
        }
        time = System.currentTimeMillis() - time;
        System.out.println("For 1000 times: " + time);

        time = System.currentTimeMillis();
        for(int i=0; i<10000; i++) {
            service.reverse(string);
        }
        time = System.currentTimeMillis() - time;
        System.out.println("For 10 000 times: " + time);

        time = System.currentTimeMillis();
        for(int i=0; i<100000; i++) {
            service.reverse(string);
        }
        time = System.currentTimeMillis() - time;
        System.out.println("For 100 000 times: " + time);
    }
}
