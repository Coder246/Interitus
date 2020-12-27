/*
 * Copyright (c) 2020.
 * Copyright by Tim and Felix
 */

package de.ft.interitus.Block.BlockUpdate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import de.ft.interitus.Block.Block;
import de.ft.interitus.UI.SelectionRectDrawer;
import de.ft.interitus.UI.UIElements.check.CheckCollision;
import de.ft.interitus.UI.UIElements.check.CheckMouse;
import de.ft.interitus.UI.UIVar;
import de.ft.interitus.Var;
import de.ft.interitus.projecttypes.ProjectManager;
import de.ft.interitus.utils.Unproject;

import java.awt.*;


public class BlockMarkManager {
    static Rectangle selectionRect = new Rectangle();
    private static boolean selecting = false;

    protected static void update() {

        if (ProjectManager.getActProjectVar().moveingdatawire == null)
            selectingRect();

        assert ProjectManager.getActProjectVar() != null;

        //Unselect Other Blocks if you released mouse on a selected Block
        if(Var.mouseDownPos.dst(Unproject.unproject()) < BlockMovingManager.movingTolerance&&!Gdx.input.isButtonPressed(0)) {
            for(Block block:ProjectManager.getActProjectVar().marked_blocks) {
                if(!CheckCollision.checkVectorWithBlock(block,Var.mouseReleasePos)) continue;
                ProjectManager.getActProjectVar().marked_blocks.clear();
                ProjectManager.getActProjectVar().marked_blocks.add(block);
                break;
            }


        }

        if (!Gdx.input.isButtonPressed(0)) return;
        if (wasMouseDownOnBlockSettings()&& UIVar.isBlockSettingsopen) return;

        if (Gdx.input.isButtonJustPressed(0) && !isMultiSelectMode()) {
            if (ProjectManager.getActProjectVar().marked_blocks.size() == 1) {
                ProjectManager.getActProjectVar().marked_blocks.clear();
            } else if (!clickedOnSelectedBlock()) {
                ProjectManager.getActProjectVar().marked_blocks.clear();
            }
        }

        if (!Gdx.input.isButtonJustPressed(0)) return;
        if (ProjectManager.getActProjectVar().moving_block != null)
            return; //Do not allow to select an other Block while anyone is moving
        for (Block block : ProjectManager.getActProjectVar().blocks) {

            if (CheckCollision.checkVectorWithBlock(block, Var.mouseDownPos)) {

                if (ProjectManager.getActProjectVar().marked_blocks.contains(block)) continue;
                ProjectManager.getActProjectVar().marked_blocks.add(block);
            }
        }


    }


    private static boolean wasMouseDownOnBlockSettings() {
        return CheckMouse.wasMousePressed(UIVar.blockeinstellungen_x, UIVar.blockeinstellungen_y, UIVar.blockeinstellungen_w, UIVar.blockeinstellungen_h);
    }

    private static boolean clickedOnSelectedBlock() {

        for (Block block : ProjectManager.getActProjectVar().visible_blocks) {
            if (CheckCollision.checkmousewithblock(block)&&ProjectManager.getActProjectVar().marked_blocks.contains(block)) return true;
        }

        return false;

    }

    private static boolean isMultiSelectMode() {

        return Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT) || Gdx.input.isKeyPressed(Input.Keys.CONTROL_RIGHT);

    }

    private static void selectingRect() {
        if (Gdx.input.isButtonPressed(0) && ProjectManager.getActProjectVar().moving_block == null) {
            if(!selecting&&!CheckMouse.wasMousePressed(UIVar.abstandvonRand,UIVar.programmflaeche_y,Gdx.graphics.getWidth()-(2* UIVar.abstandvonRand), UIVar.programmflaeche_h)) return;
            selectionRect.setLocation((int) Var.mouseDownPos.x, (int) Var.mouseDownPos.y);
            selectionRect.setSize((int) (Unproject.unproject().x - Var.mouseDownPos.x), (int) (Unproject.unproject().y - Var.mouseDownPos.y));
            SelectionRectDrawer.draw(selectionRect);
            selecting = true;

        }

        if (!Gdx.input.isButtonPressed(0) && selecting) {

            for (Block checkblock : ProjectManager.getActProjectVar().visible_blocks) {
                if (CheckCollision.rectCollision(selectionRect, new Rectangle(checkblock.getX(), checkblock.getY(), checkblock.getW(), checkblock.getH()))) {
                    ProjectManager.getActProjectVar().marked_blocks.add(checkblock);
                }
            }
            selecting=false;

        }

    }


}
