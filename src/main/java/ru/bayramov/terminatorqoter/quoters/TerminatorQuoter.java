package ru.bayramov.terminatorqoter.quoters;

import lombok.Setter;
import ru.bayramov.terminatorqoter.quoters.annotations.deprecated.DeprecatedClass;
import ru.bayramov.terminatorqoter.quoters.annotations.injectrandomint.InjectRandomInt;
import ru.bayramov.terminatorqoter.quoters.annotations.postproxy.PostProxy;
import ru.bayramov.terminatorqoter.quoters.annotations.profilling.Profiling;

import javax.annotation.PostConstruct;

@Profiling
@DeprecatedClass(newImpl = T1000.class)
public class TerminatorQuoter implements Quoter {
    @InjectRandomInt(min = 2, max = 7)
    private int repeat;

    @Setter
    private String message;

    @PostConstruct
    public void init() {
        System.out.println("Phase 2");
        System.out.println(repeat);

    }

    public TerminatorQuoter() {
        System.out.println("Phase 1");
    }

    public void setRepeat(int repeat) {
        this.repeat = repeat;
    }

    @Override
    @PostProxy
    public void sayQuote() {
        System.out.println("Phase 3");
        for (int i = 0; i < repeat; i++) {
            System.out.println("message = " + message);
        }
    }
}
