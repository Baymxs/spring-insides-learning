package ru.bayramov.terminatorqoter;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.bayramov.terminatorqoter.quoters.Quoter;

@SpringBootApplication
public class TerminatorQuoterApplication {
    public static void main(String[] args) throws InterruptedException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        context.getBean(Quoter.class).sayQuote();
    }
}
