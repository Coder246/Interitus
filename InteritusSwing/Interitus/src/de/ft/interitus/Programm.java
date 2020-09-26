/*
 * Copyright (c) 2020.
 * Copyright by Tim and Felix
 */

package de.ft.interitus;


import de.ft.interitus.Logging.DebugPrinter;
import de.ft.interitus.Logging.LoggerInit;
import de.ft.interitus.UI.CheckShortcuts;
import de.ft.interitus.UI.Theme.ThemeManager;
import de.ft.interitus.UI.UI;
import de.ft.interitus.datamanager.programmdata.Data;
import de.ft.interitus.datamanager.programmdata.experience.ExperienceManager;
import de.ft.interitus.datamanager.userdata.UserDataInit;
import de.ft.interitus.events.EventVar;
import de.ft.interitus.events.UI.UILoadEvent;
import de.ft.interitus.loading.Loading;
import de.ft.interitus.loading.SplashScreen;
import de.ft.interitus.plugin.PluginManagerHandler;
import de.ft.interitus.plugin.PluginSandboxSecurityPolicy;

import de.ft.interitus.projecttypes.ProjectManager;
import de.ft.interitus.utils.FolderUtils;
import de.ft.interitus.utils.UserNameGetter;
import org.springframework.boot.SpringApplication;

import java.io.IOException;
import java.nio.file.Path;
import java.security.Policy;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

public class Programm {

    public static Programm INSTANCE;
    public static boolean inLoading = true;
    public static Logger logger = Logger.getLogger(Programm.class.getName());


    public Programm() {


        INSTANCE = this;
    }

    
    public void create() {

        Policy.setPolicy(new PluginSandboxSecurityPolicy());
        System.setSecurityManager(new SecurityManager());


        LoggerInit.init();

        DebugPrinter.detect();


        if (Var.savemode) {
            logger.warning("Programm is running in savemode");
        }
       // ((Lwjgl3Graphics) Gdx.graphics).getWindow().iconifyWindow();
        Var.splashscreen = SplashScreen.create();
      
  
        //GLFW.glfwHideWindow(Var.window.getWindowHandle());




        Var.username = UserNameGetter.get();


        ThemeManager.register(); //Load all Themes

        Thread loadplugins = new Thread(() -> DisplayErrors.error = PluginManagerHandler.register());

       Programm.logger.config("Loaded Vis-UI");


        try {
            if (!Var.disablePluginSubSystem && !Var.nointernetconnection) {
                loadplugins.start(); //Plugins laden
                Thread springthread = new Thread() {
                    @Override
                    public void run() {

                       // SpringApplication app = new SpringApplication(PluginInstallerServer.class);
                        //app.setDefaultProperties(Collections.singletonMap("server.port","8459"));
                      //  app.run();

                    }
                };
                springthread.start();

            }
        } catch (Exception e) {
            Programm.logger.warning("No Internet Connection!");
            Var.nointernetconnection = true;
        }

        EventVar.uiEventManager.UILoadEvent(new UILoadEvent(this));



        UI.init();
        ProjectManager.init();

        Programm.logger.config("UI element loaded");
        if (!Var.savemode) {
            CheckShortcuts.loadArrayList();//bevor CheckShortcuts.loatArraylist muss die ui schon die menuebar eleente erstellt haben!!!!!!!!!
        }




        if (!Var.savemode) {
            Data.init();
        } else {
            //TODO L   Dialogs.showErrorDialog(UI.stage, "Achtung Interitus läuft im Abgesicherten Modus!\nAlle Einstellungen die die hier vornimmst werden nicht übernommen.\nDieser Modus dient nur dazu, Projekte zu retten und um Plugins zu testen.");
            Data.init(".temp");
        }
        Programm.logger.finest("");
        Programm.logger.config("Theme: " + Settings.theme.getName());
        Programm.logger.config("Limit-FPS: " + Settings.limitfps);
        Programm.logger.config("Vsync: " + Settings.Vsync);
        Programm.logger.finest("");
        if (!Var.savemode) {
            UserDataInit.init();
        }
        Programm.logger.config("Setted File drop Listener");
        ExperienceManager.init();
        //TODO L   Gdx.graphics.setVSync(Settings.Vsync);

        Timer saveprogrammdatatimer = new Timer();
        saveprogrammdatatimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Data.close(false);
            }
        }, 1000 * 60 * 15, 1000 * 60 * 15);



        //TODO L setScreen(new Loading());

    }


    public void dispose() {

        ThreadManager.stopall();

        Data.close(true);

        if (Var.savemode) {
            try {
                FolderUtils.deleteFileOrFolder(Path.of(System.getProperty("user.home") + "/" + Data.foldername));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        //ALC.destroy();//Destroy Sound System todo lwjgl3


        System.exit(0);

    }


    public void pause() {

    }


}

