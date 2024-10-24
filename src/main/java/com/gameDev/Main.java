package com.gameDev;

import com.gameDev.consoleUI.ConsoleUI;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {

        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        new ConsoleUI(context).start();

    }
}
