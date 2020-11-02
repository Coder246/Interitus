/*
 * Copyright (c) 2020.
 * Copyright by Tim and Felix
 */

package de.ft.interitus.projecttypes.BlockTypes.Interitus.Ev3.actionBlocks.light;

import com.badlogic.gdx.graphics.Texture;
import de.ft.interitus.loading.AssetLoader;
import de.ft.interitus.projecttypes.Addons.Addon;
import de.ft.interitus.projecttypes.BlockTypes.BlockCategories;
import de.ft.interitus.projecttypes.BlockTypes.PlatformSpecificBlock;
import de.ft.interitus.projecttypes.ProjectType;

public class StatusLightBlock extends PlatformSpecificBlock {
    public StatusLightBlock(ProjectType projectType, Addon addon) {
        super(projectType, addon);
        super.actBlockModiIndex=0;
        super.blockModis.add(new An());
        super.blockModis.add(new Aus());
        super.blockModis.add(new Zuruecksetzen());
    }

    @Override
    public String getName() {
        return "Status Licht";
    }

    @Override
    public String getdescription() {
        return "Kann das Staturlicht kontrollieren";
    }

    @Override
    public BlockCategories getBlockCategorie() {
        return BlockCategories.ActionBlocks;
    }

    @Override
    public Texture getDescriptionImage() {
        return AssetLoader.Parameter_randomdice;
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
