package de.ft.interitus.UI.shortcut.shortcuts;

import com.badlogic.gdx.Input;
import de.ft.interitus.UI.shortcut.ShortCut;
import de.ft.interitus.UI.shortcut.ShortCutChecker;

import java.util.ArrayList;

public class BlockShortcuts implements ShortCutChecker {

    public static ShortCut shortCut_deleteBlock = new ShortCut("Block löschen", Input.Keys.FORWARD_DEL);


    public BlockShortcuts() {

    }

    public static ArrayList<ShortCut> retunrarray() {

        ArrayList<ShortCut> returnarraylist = new ArrayList<>();
        returnarraylist.add(shortCut_deleteBlock);


        return returnarraylist;

    }

    @Override
    public void check() {

    }
}
