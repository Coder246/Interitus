/*
 * Copyright (c) 2020.
 * Copyright by Tim and Felix
 */

package de.ft.interitus.projecttypes.BlockTypes.Interitus.Arduino.programmaufbau;

import com.badlogic.gdx.graphics.Texture;
import de.ft.interitus.Block.Parameter;
import de.ft.interitus.loading.AssetLoader;
import de.ft.interitus.projecttypes.BlockTypes.BlockCategories;
import de.ft.interitus.projecttypes.BlockTypes.BlockTopParameter;
import de.ft.interitus.projecttypes.BlockTypes.Interitus.Arduino.ArduinoBlock;
import de.ft.interitus.projecttypes.BlockTypes.PlatformSpecificBlock;
import de.ft.interitus.projecttypes.ProjectTypes;
import de.ft.interitus.utils.ArrayList;

import java.awt.*;

public class SetupBlock extends PlatformSpecificBlock implements ArduinoBlock {

    public SetupBlock(ProjectTypes arduino) {
        super(arduino);


    }

    @Override
    public ArrayList<Parameter> getBlockParameter() {
        return null;
    }

    @Override
    public String getName() {
        return "Setup";
    }

    @Override
    public String getdescription() {
        return "Alles was an diesen Block angehängt wird einmal bei Programmstart ausgeführt";
    }

    @Override
    public ArrayList<BlockTopParameter> getblocktopparamter() {
        return null;
    }

    @Override
    public Color blockcolor() {
        return null;
    }

    @Override
    public BlockCategories getBlockCategoration() {
        return null;
    }

    @Override
    public Texture getSmallImage() {
        return AssetLoader.img_block;
    }

    @Override
    public Texture getImageRight() {
        return AssetLoader.SetupBlock_right;
    }

    @Override
    public Texture getImageLeft() {
        return AssetLoader.SetupBlock_left;
    }

    @Override
    public Texture getImageCenter() {
        return AssetLoader.SetupBlock_middle;
    }

    @Override
    public Texture getDescriptionImage() {
        return null;
    }


    @Override
    public int getWidth() {
        return 74;
    }


    @Override
    public boolean canbedeleted() {
        return false;
    }

    @Override
    public boolean canhasrightconnector() {
        return true;
    }

    @Override
    public boolean canhasleftconnector() {
        return false;
    }


    @Override
    public String getCode() {
        return "void setup(){";
    }
}
