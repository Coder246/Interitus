package de.ft.interitus.events.global;

import java.util.EventObject;

public class GlobalCloseEvent extends EventObject {
    /**
     * Constructs a prototypical Event.
     *
     * return false to not close the programm
     */
    public GlobalCloseEvent(Object source) {
        super(source);
    }
}