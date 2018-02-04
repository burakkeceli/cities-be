package com.cities.city;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author srccodes.com
 * @version 1.0
 */
public class Main {
    private final Logger slf4jLogger = LoggerFactory.getLogger(Main.class);

    /**
     * Print hello in log.
     *
     * @param name
     */
    public void sayHello(String name) {
        slf4jLogger.debug("Hi, {}", name);
        slf4jLogger.debug("Welcome to the HelloWorld example of SLF4J");
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        Main slf4jHello = new Main();
        slf4jHello.sayHello("srccodes.com");
    }
}