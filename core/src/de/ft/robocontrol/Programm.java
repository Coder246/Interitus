package de.ft.robocontrol;

import com.badlogic.gdx.Game;
import de.ft.robocontrol.data.programm.Data;
import de.ft.robocontrol.data.user.experience.ExperienceManager;
import de.ft.robocontrol.loading.Loading;
import de.ft.robocontrol.utils.NetworkScan;

public class Programm extends Game {

    public static Programm INSTANCE;
    public static boolean inLoading = true;

    public Programm() {

        INSTANCE = this;
    }
    @Override
    public void create() {

        NetworkScan.get();

        ExperienceManager.init();
        setScreen(new Loading());
    }


    public void dispose() {
        System.out.println("Save");
        Data.close();
        System.out.println("saved");
    }


    public void pause() {

    }
}


