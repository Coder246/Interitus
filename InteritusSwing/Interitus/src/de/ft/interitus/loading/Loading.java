/*
 * Copyright (c) 2020.
 * Copyright by Tim and Felix
 */

package de.ft.interitus.loading;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import de.ft.interitus.Programm;
import de.ft.interitus.Settings;
import de.ft.interitus.UI.UI;
import de.ft.interitus.Var;
import de.ft.interitus.Welcome;
import de.ft.interitus.events.EventVar;
import de.ft.interitus.events.global.GlobalLoadingDoneEvent;
import de.ft.interitus.events.global.GlobalLoadingStartEvent;
import de.ft.interitus.projecttypes.BlockTypes.Init;
import org.lwjgl.glfw.GLFW;

public class Loading extends ScreenAdapter {
    public Loading loading = this;

    public Loading() {

        EventVar.globalEventManager.loadingstart(new GlobalLoadingStartEvent(this));



        AssetLoader.load();

    }


    public void render(float delta) {

        Gdx.gl.glClearColor(Settings.theme.ClearColor().r, Settings.theme.ClearColor().g, Settings.theme.ClearColor().b, Settings.theme.ClearColor().a);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT | (Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV : 0));
        try {

            if (AssetLoader.manager.update()) {
                try {
                    AssetLoader.save();
                } catch (Exception e) {

                    Programm.logger.severe("Error while saving Assets");
                }
                if (Programm.inLoading == true) {
                    Programm.inLoading = false;
                    this.dispose();
                    EventVar.globalEventManager.loadingdone(new GlobalLoadingDoneEvent(this));
                    UI.initnachassetsloading();
                    Init.initBlocks();


                    Var.splashscreen.destroy();
                    //GLFW.glfwMaximizeWindow(Var.window.getWindowHandle());

                    //GLFW.glfwHideWindow(Var.window.getWindowHandle());


                    Var.window.setVisible(true);
                    GLFW.glfwFocusWindow(Var.window.getWindowHandle());
                    Programm.INSTANCE.setScreen(new Welcome());
                    //  ((Lwjgl3Graphics) Gdx.graphics).getWindow().restoreWindow();

                    //GLFW.glfwMaximizeWindow(Var.window.getWindowHandle());
                    this.dispose();

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void dispose() {

    }


}