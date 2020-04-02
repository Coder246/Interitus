package de.ft.robocontrol.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.MenuBar;
import com.kotcrab.vis.ui.widget.MenuItem;
import de.ft.robocontrol.MainGame;
import de.ft.robocontrol.Settings;
import de.ft.robocontrol.data.programm.Data;
import de.ft.robocontrol.data.user.changes.SaveChanges;

import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static de.ft.robocontrol.UI.MenuBar.createSubMenu;

public class UI {
    static final Table root = new Table();
    public static Stage stage;
    public static ConnectionWindow connectionWindow;
    protected static MenuItem recent;
    protected static MenuItem revert;
    protected static MenuItem redo;
    protected static MenuItem copy;
    protected static MenuItem paste;
    protected static MenuBar menuBar;
    protected static SettingsUI set;
    private static Vector2 lastframecamposition = new Vector2(MainGame.cam.position.x, MainGame.cam.position.y);

    public static void initdragui() {

    }

    public static void abgerundetesRechteck(ShapeRenderer renderer, int x, int y, int w, int h, int r) {
        w = w - r * 2;
        h = h - r * 2;
        renderer.circle(x + r, y + r, r);
        renderer.circle(x + r + w, y + r, r);
        renderer.circle(x + r, y + r + h, r);
        renderer.circle(x + r + w, y + r + h, r);

        renderer.rect(x + r, y, w, 40);
        renderer.rect(x + r, y + h, w, r * 2);
        renderer.rect(x, y + r, r * 2, h);
        renderer.rect(x + w, y + r, r * 2, h);

        renderer.rect(x + r, y + r, w, h);
    }


    public static void updatedragui(ShapeRenderer renderer, boolean flaeche) {
        renderer.begin(ShapeRenderer.ShapeType.Filled);


        if (Settings.darkmode) {
            renderer.setColor(new Color(0.15f, 0.15f, 0.15f, 1));
        } else {
            renderer.setColor(new Color(1f, 1f, 1f, 1));
        }


        int abstandvonRand = Gdx.graphics.getWidth() / (Gdx.graphics.getWidth() / 7);


        int unteneinteilung = 300;
        int untenhohe = 125;
        int radius = 3;




        if (flaeche == true) {
            abgerundetesRechteck(renderer, abstandvonRand, untenhohe + abstandvonRand, Gdx.graphics.getWidth() - abstandvonRand * 2, Gdx.graphics.getHeight() - untenhohe + abstandvonRand - 45 - abstandvonRand, radius);
        } else {
            if (Settings.darkmode) {
                renderer.setColor(new Color(0.2f, 0.1f, 0.1f, 1));
            } else {
                renderer.setColor(new Color(1f, 1f, 1f, 1));
            }
            abgerundetesRechteck(renderer, abstandvonRand, abstandvonRand, Gdx.graphics.getWidth() - abstandvonRand * 2 - unteneinteilung, untenhohe - abstandvonRand, radius);
            if (Settings.darkmode) {
                renderer.setColor(new Color(0.2f, 0.2f, 0.2f, 1));
            } else {
                renderer.setColor(new Color(1f, 1f, 1f, 1));
            }
            abgerundetesRechteck(renderer, Gdx.graphics.getWidth() - unteneinteilung, abstandvonRand, unteneinteilung - abstandvonRand, untenhohe - abstandvonRand, radius);
        }
        renderer.end();

    }

    public static void init() {
        VisUI.load(VisUI.SkinScale.X1);
        stage = new Stage(MainGame.viewport, MainGame.UIbatch);

        root.setFillParent(true);
        stage.addActor(root);
        Gdx.input.setInputProcessor(stage);

        set = new SettingsUI();

        menuBar = new MenuBar();


        root.add(menuBar.getTable()).expandX().fillX().row();
        root.add().expand().fill().row();


        connectionWindow = new ConnectionWindow();

        connectionWindow.show();//TODO ist hier nicht geplant muss noch verschoben werden

        de.ft.robocontrol.UI.MenuBar.createMenus();


        Thread UIthread = new Thread() {

            @Override
            public void run() {
                Timer time = new Timer();
                time.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {


                        ////////////recent//////////////////////////


                        for (int i = 0; i < Data.path.size(); i++) {
                            if (!(new File(Data.path.get(i)).exists())) {
                                Data.path.remove(i);
                                Data.filename.remove(i);
                            }
                        }

                        if (Data.path.size() == 0) {
                            recent.setDisabled(true);
                        } else {
                            recent.setDisabled(false);
                        }


                        ///////////////////////////////////


                        /////////////revert//////////////

                        if (SaveChanges.checkstack()) {
                            revert.setDisabled(true);
                        } else {
                            revert.setDisabled(false);
                        }

                        ///////Redo//////////////


                        if (SaveChanges.checkredostack()) {
                            redo.setDisabled(true);
                        } else {
                            redo.setDisabled(false);
                        }


                    }
                }, 0, 500);
            }
        };

        UIthread.start();


    }

    public static void update() {
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 60f));

        //root.setPosition(0,0);


        root.setPosition(MainGame.cam.position.x - ((float) Gdx.graphics.getWidth()) / 2, MainGame.cam.position.y - ((float) Gdx.graphics.getHeight()) / 2);


        stage.draw();

        recent.setSubMenu(createSubMenu(Data.filename.size(), GetStringArray(Data.filename)));

    }


    public static String[] GetStringArray(ArrayList<String> arr) {

        // declaration and initialise String Array
        String[] str = new String[arr.size()];

        // ArrayList to Array Conversion
        for (int j = 0; j < arr.size(); j++) {


            // Assign each value to String array
            str[j] = arr.get(j);
        }

        return str;
    }

    public static void updateView(int width, int height) {
        stage.getViewport().update(width, height, true);
    }
}
