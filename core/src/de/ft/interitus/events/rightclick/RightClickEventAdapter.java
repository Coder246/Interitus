/*
 * Copyright (c) 2021.
 * Copyright by Tim and Felix
 */

package de.ft.interitus.events.rightclick;

import de.ft.interitus.UI.popup.PopupMenue;

public class RightClickEventAdapter implements RightClickEventListener {

    @Override
    public PopupMenue openRequest(RightClickOpenRequestEvent e, float Pos_X, float Pos_Y) {
        return null;
    }

    @Override
    public void performAction(RightClickPerformActionEvent e, PopupMenue popupMenue, int Buttonindex) {

    }


}
