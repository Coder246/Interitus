/*
 * Copyright (c) 2020.
 * Copyright by Tim and Felix
 */

package de.ft.interitus.projecttypes.BlockTypes.Interitus.Arduino.programmablauf.For;

import com.badlogic.gdx.graphics.Texture;
import de.ft.interitus.loading.AssetLoader;
import de.ft.interitus.projecttypes.Addons.Addon;
import de.ft.interitus.projecttypes.BlockTypes.BlockCategories;
import de.ft.interitus.projecttypes.BlockTypes.Interitus.Arduino.programmablauf.If.IfDefaultBlockModi;
import de.ft.interitus.projecttypes.BlockTypes.Interitus.Arduino.programmablauf.If.IfElse;
import de.ft.interitus.projecttypes.BlockTypes.Interitus.Arduino.programmablauf.If.IfEnd;
import de.ft.interitus.projecttypes.BlockTypes.PlatformSpecificBlock;
import de.ft.interitus.projecttypes.ProjectTypes;

public class For extends PlatformSpecificBlock {

    public For(ProjectTypes projectTypes, Addon addon) {
        super(projectTypes, addon);
        super.blockModis.add(new ForDefaultBlockModi());

        super.actBlockModiIndex = 0;

    }

    @Override
    public String getName() {
        return "For";
    }

    @Override
    public String getdescription() {
        return "";
    }



    @Override
    public BlockCategories getBlockCategorie() {
        return BlockCategories.Programm_Sequence;
    }




    @Override
    public Texture getDescriptionImage() {
        return AssetLoader.if_description;
    }

    @Override
    public boolean canbedeleted() {
        return true;
    }

    @Override
    public boolean canhasrightconnector() {
        return true;
    }

    @Override
    public boolean canhasleftconnector() {
        return true;
    }
}