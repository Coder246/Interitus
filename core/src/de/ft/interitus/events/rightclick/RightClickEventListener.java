package de.ft.interitus.events.rightclick;

import java.util.EventListener;

public interface RightClickEventListener extends EventListener {

    void openrightclickwindow(RightClickOpenEvent e);

    void buttonclickedinwindow(RightClickButtonSelectEvent e);
}