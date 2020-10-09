/*
 * Copyright (c) 2020.
 * Copyright by Tim and Felix
 */

package de.ft.interitus.plugin;

import com.badlogic.gdx.graphics.Texture;
import de.ft.interitus.WindowManager;
import de.ft.interitus.UI.UI;

public class PluginDrawer {
    public static void draw() {


        for(PluginRenderer renderer: ProgramRegistry.pluginRenderer) {
            renderer.render(WindowManager.batch, UI.UIbatch, WindowManager.BlockshapeRenderer, WindowManager.shapeRenderer);
        }



    }

    public static void loadimages() {
        for (int i = 0; i < ProgramRegistry.pluginpixmaps.size(); i++) {
            ProgramRegistry.pluginTextures.add(new Texture(ProgramRegistry.pluginpixmaps.get(i)));
        }

    }

}
