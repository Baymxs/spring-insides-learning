package ru.bayramov.terminatorqoter.quoters.annotations.profilling;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProfilingController implements ProfilingControllerMBean {
    private boolean enabled = true;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
