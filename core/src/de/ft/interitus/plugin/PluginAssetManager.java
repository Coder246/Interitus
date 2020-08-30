/*
 * Copyright (c) 2020.
 * Copyright by Tim and Felix
 */

package de.ft.interitus.plugin;

import com.badlogic.gdx.graphics.Texture;

public class PluginAssetManager {
    public Texture collectTextureAsset(int id) {
      return ProgramRegistry.pluginTextures.get(id);
    }
}
