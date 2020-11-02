/*
 * Copyright (c) 2020.
 * Copyright by Tim and Felix
 */

package de.ft.interitus.projecttypes.BlockTypes.Interitus.Ev3.actionBlocks.light;

import com.badlogic.gdx.graphics.Texture;
import de.ft.interitus.Block.Parameter;
import de.ft.interitus.loading.AssetLoader;
import de.ft.interitus.projecttypes.BlockTypes.BlockSettings;
import de.ft.interitus.projecttypes.BlockTypes.Interitus.Ev3.Ev3Block;
import de.ft.interitus.utils.ArrayList;

public class Aus extends Ev3Block {
    @Override
    public String getCode() {
        return "UI_WRITE(LED,0x00)";
    }

    @Override
    public ArrayList<Parameter> getBlockParameter() {
        return null;
    }

    @Override
    public BlockSettings getblocksettings() {
        return null;
    }

    @Override
    public int getWidth() {
        return 50;
    }

    @Override
    public String getname() {
        return "Statuslicht Aus";
    }

    @Override
    public Texture getModiImage() {
        return AssetLoader.yellow_bar_middle;
    }
}
