/*
 * Copyright (c) 2020.
 * Copyright by Tim and Felix
 */

package de.ft.interitus.events.global;

import de.ft.interitus.UI_old.UIElements.TabBar.Tab;

public interface GlobalEventListener {
    void loadingdone(GlobalLoadingDoneEvent e);

    void loadingstart(GlobalLoadingStartEvent e);

    void erroroccurred(GlobalErrorOccurredEvent e);

    boolean filedroped(GlobalFileDropedEvent e, String[] filepaths);

    boolean closeprogramm(GlobalCloseEvent e);

    void focuslost(GlobalFocusLostEvent e);

    void tabclicked(GlobalTabClickEvent e, Tab tab);
}
