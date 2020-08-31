/*
 * Copyright (c) 2020.
 * Copyright by Tim and Felix
 */

package de.ft.interitus.loading;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import de.ft.interitus.DisplayErrors;
import de.ft.interitus.Programm;
import de.ft.interitus.Var;
import de.ft.interitus.plugin.store.StorePluginsVar;
import de.ft.interitus.utils.ArrayList;
import de.ft.interitus.utils.DownloadFile;

import java.io.IOException;
import java.util.Objects;

public class AssetLoader {


    public static ArrayList<Texture> storeimages = new ArrayList<>();

    public static Pixmap backcursor;

    public static String group = "";
    public static String workingdirectory = "";

    public static Texture block_left;
    public static Texture block_middle;
    public static Texture block_right;

    public static Texture green_bar_left;
    public static Texture green_bar_middle;
    public static Texture green_bar_right;


    //Arduino Device Images
    public static Texture arduinonanoimage;
    public static Texture arduinounoimage;
    public static Texture arduinomegaimage;


    public static Texture connector;
    public static Texture connector_offerd;

    //Switch Textures
    public static Texture switch_background;
    public static Texture switch_inside;
    public static Texture switch_background_green;

    //Switch Textures White
    public static Texture switch_background_white;
    public static Texture switch_inside_white;
    public static Texture switch_background_green_white;



    //Wire
    public static Texture wire;
    //Wire Nod
    public static Texture wire_node;

    //Plugin Warte Bild
    public static Texture pluginwait;





    public static Texture img_mappe1;
    public static Texture img_mappe2;
    public static Texture img_mappe3;
    public static Texture img_mappe4;
    public static Texture img_mappe5;
    public static Texture img_mappe6;


    public static BitmapFont welcomefont;//font
    public static BitmapFont defaultfont;//font

    /////////////////////-ButtonBar-//////////////////////////
    public static Texture img_startbutton;
    public static Texture img_startbutton_mouseover;
    public static Texture img_startbutton_pressed;

    public static Texture img_stopbutton;
    public static Texture img_stopbutton_mouseover;
    public static Texture img_stopbutton_pressed;

    public static Texture img_projectstructur;
    public static Texture img_projectstructur_mouseover;
    public static Texture img_projectstructur_pressed;

    public static Texture img_debugstart;
    public static Texture img_debugstart_mouseover;
    public static Texture img_debugstart_pressed;

    public static Texture img_editor;
    public static Texture img_editor_mouseover;
    public static Texture img_editor_pressed;

    public static Texture img_addrunconfig;
    public static Texture img_addrunconfig_mouseover;
    public static Texture img_addrunconfig_pressed;

    public static Texture waitforfinishbuild;

    ///////////////////////////-BlockParameter Bilder-/////////////////////////////

    //////////////////////////-TabBar-//////////////////////////////
    public static Texture img_Tab;


    public static Texture WaitBlock_smallimage;
    public static Texture WaitBlock_description_image;

    public static Texture PinModeBlock_smallimage;
    public static Texture PinModeBlock_description_image;

    public static Texture DigitalWrite_smallimage;
    public static Texture DigitalWrite_description_image;



    ////ParameterBilder :
    public static Texture Parameter_High_Low;
    public static Texture Parameter_Pin;
    public static Texture Parameter_IO;
    public static Texture img_WaitBlock_warteZeit_Parameter;
    public static Texture Parameter_erstens;
    public static Texture Parameter_zweitens;
    public static Texture Parameter_drittens;
    public static Texture Parameter_viertens;
    public static Texture Parameter_istgleich;
    public static Texture Parameter_Minus;
    public static Texture Parameter_Plus;


    public static Texture mouseover_links;
    public static Texture mouse_over_rechts;
    public static Texture mouse_over_mitte;

    public static Texture marked_links;
    public static Texture marked_rechts;
    public static Texture marked_mitte;

    public static Texture Plug_IntParameter;
    public static Texture Plug_StringParameter;
    public static Texture Plug_BooleanParameter;

    public static Texture aufklapppfeil;

    //UI
    public static Texture close_notification;
    public static Texture information;


    public static AssetManager manager = new AssetManager();



    public static Object save(String file, Class Type) {
        return manager.get(workingdirectory + file, Type);
    }


    public static void load() {


        try {
            group = "ParameterBilder";
            manager.load(workingdirectory + "Block/Parameter/WaitParameter.png", Texture.class);
            manager.load(workingdirectory + "Block/Parameter/Parameter_Pin.png", Texture.class);
            manager.load(workingdirectory + "Block/Parameter/Parameter_IO.png", Texture.class);
            manager.load(workingdirectory + "Block/Parameter/parameter_high_low.png", Texture.class);
            manager.load(workingdirectory + "Block/Parameter/istgleich.png", Texture.class);
            manager.load(workingdirectory + "Block/Parameter/erstens.png", Texture.class);
            manager.load(workingdirectory + "Block/Parameter/zweitens.png", Texture.class);
            manager.load(workingdirectory + "Block/Parameter/drittens.png", Texture.class);
            manager.load(workingdirectory + "Block/Parameter/viertens.png", Texture.class);
            manager.load(workingdirectory + "Block/Parameter/Minus.png", Texture.class);
            manager.load(workingdirectory + "Block/Parameter/Plus.png", Texture.class);
           // manager.load(new AssetDescriptor(Objects.requireNonNull(Programm.INSTANCE.getClass().getClassLoader().getResource("../statistics/test.png")).getFile(),Texture.class));


            //Programm.INSTANCE.getClass().getResource()







            group = "Schriftarten";
            try {
                //schriftart
                FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("comicsans.ttf"));
                FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
                parameter.size = 50;
                welcomefont = generator.generateFont(parameter); // font size 12 pixels

                FreeTypeFontGenerator generator1 = new FreeTypeFontGenerator(Gdx.files.internal("defaultfont.ttf"));
                FreeTypeFontGenerator.FreeTypeFontParameter parameter1 = new FreeTypeFontGenerator.FreeTypeFontParameter();
                parameter1.size = 15;

                defaultfont = generator1.generateFont(parameter1);

                generator.dispose(); // don't forget to dispose to avoid memory leaks!
            } catch (Exception e) {
                System.out.println("Fehler beim Laden der Schrift");
            }

            group = "Plugs";
            manager.load(workingdirectory + "Block/ParameterStecker/ZahlParameterStecker.png", Texture.class);
            manager.load(workingdirectory + "Block/ParameterStecker/TextParameterStecker.png", Texture.class);
            manager.load(workingdirectory + "Block/ParameterStecker/BooleanParameterStecker.png", Texture.class);


            group = "TabBar";

            backcursor = new Pixmap(Gdx.files.internal("backcursor.png"));


            manager.load(workingdirectory + "TabBar/Tabimage.png", Texture.class);


            manager.load(workingdirectory + "arduinomegaminiimage.png", Texture.class);
            manager.load(workingdirectory + "arduinonanominiimage.png", Texture.class);
            manager.load(workingdirectory + "arduinounominiimage.png", Texture.class);

            group = "ButtonBar";
            manager.load(workingdirectory + "ButtonBar/startbutton.png", Texture.class);
            manager.load(workingdirectory + "ButtonBar/startbutton_mouseover.png", Texture.class);
            manager.load(workingdirectory + "ButtonBar/startbutton_pressed.png", Texture.class);

            manager.load(workingdirectory + "ButtonBar/stopbutton.png", Texture.class);
            manager.load(workingdirectory + "ButtonBar/stopbutton_mouseover.png", Texture.class);
            manager.load(workingdirectory + "ButtonBar/stopbutton_pressed.png", Texture.class);

            manager.load(workingdirectory + "ButtonBar/projektstrukturbutton.png", Texture.class);
            manager.load(workingdirectory + "ButtonBar/projektstrukturbutton_mouseover.png", Texture.class);
            manager.load(workingdirectory + "ButtonBar/projektstrukturbutton_pressed.png", Texture.class);

            manager.load(workingdirectory + "ButtonBar/debugbutton.png", Texture.class);
            manager.load(workingdirectory + "ButtonBar/debugbutton_mouseover.png", Texture.class);
            manager.load(workingdirectory + "ButtonBar/debugbutton_pressed.png", Texture.class);

            manager.load(workingdirectory + "ButtonBar/editorbutton.png", Texture.class);
            manager.load(workingdirectory + "ButtonBar/editorbutton_mouseover.png", Texture.class);
            manager.load(workingdirectory + "ButtonBar/editorbutton_pressed.png", Texture.class);

            manager.load(workingdirectory + "ButtonBar/addrunconfig.png", Texture.class);
            manager.load(workingdirectory + "ButtonBar/addrunconfig_mouseover.png", Texture.class);
            manager.load(workingdirectory + "ButtonBar/addrunconfig_pressed.png", Texture.class);

            manager.load(workingdirectory + "loadinganimation.png", Texture.class);

            group = "mappen";
            manager.load(workingdirectory + "Bar/Mappe1.png", Texture.class);
            manager.load(workingdirectory + "Bar/Mappe2.png", Texture.class);
            manager.load(workingdirectory + "Bar/Mappe3.png", Texture.class);
            manager.load(workingdirectory + "Bar/Mappe4.png", Texture.class);
            manager.load(workingdirectory + "Bar/Mappe5.png", Texture.class);
            manager.load(workingdirectory + "Bar/Mappe6.png", Texture.class);


            group = "Blöcke";

            manager.load(workingdirectory + "Block/mouseover_links.png", Texture.class);
            manager.load(workingdirectory + "Block/mouseover_links.png", Texture.class);
            manager.load(workingdirectory + "Block/mouseover_links.png", Texture.class);

            manager.load(workingdirectory + "Block/block_right.png", Texture.class);
            manager.load(workingdirectory + "Block/block_middle.png", Texture.class);
            manager.load(workingdirectory + "Block/block_left.png", Texture.class);

            manager.load(workingdirectory + "Block/color_bar/green_left.png", Texture.class);
            manager.load(workingdirectory + "Block/color_bar/green_middle.png", Texture.class);
            manager.load(workingdirectory + "Block/color_bar/green_right.png", Texture.class);



            manager.load(workingdirectory + "connector.png", Texture.class);
            manager.load(workingdirectory + "connector_offerd.png", Texture.class);
            //////////////////////////////////////////////////////////////////////////////////////////////////////////

            manager.load(workingdirectory + "Block/mouseover_links.png", Texture.class);
            manager.load(workingdirectory + "Block/mouseover_rechts.png", Texture.class);
            manager.load(workingdirectory + "Block/mouseover_mitte.png", Texture.class);
            manager.load(workingdirectory + "Block/market_links.png", Texture.class);
            manager.load(workingdirectory + "Block/market_rechts.png", Texture.class);
            manager.load(workingdirectory + "Block/market_mitte.png", Texture.class);
            manager.load(workingdirectory + "Block/Block_PinMode/smallblock.png", Texture.class);
            manager.load(workingdirectory + "Block/Block_DigitalWrite/smallblock.png", Texture.class);
            manager.load(workingdirectory + "Block/Block_Wait/smallblock.png", Texture.class);
            manager.load(workingdirectory + "Block/Block_Wait/beschreibungsbild.png", Texture.class);
            manager.load(workingdirectory + "Block/Block_PinMode/beschreibungsbild.png", Texture.class);
            manager.load(workingdirectory + "Block/Block_DigitalWrite/beschreibungsbild.png", Texture.class);
            manager.load(workingdirectory + "aufklapppfeil.png", Texture.class);


            group = "Switches";
            //Switch
            //Switch Dark
            manager.load(workingdirectory + "switchbackground.png", Texture.class);
            manager.load(workingdirectory + "switchinside.png", Texture.class);
            manager.load(workingdirectory + "switchbackgroundgreen.png", Texture.class);
            //Switch White
            manager.load(workingdirectory + "switchbackground_white.png", Texture.class);
            manager.load(workingdirectory + "switchinside.png", Texture.class);
            manager.load(workingdirectory + "switchbackground_whitegreen.png", Texture.class);

            group = "Wire";
            //Wire
            manager.load(workingdirectory + "wire.png", Texture.class);
            group = "Wire-Node";
            //Wire Node
            manager.load(workingdirectory + "node.png", Texture.class);
            group = "Plugin";
            manager.load(workingdirectory + "pluginwaiting.png", Texture.class);
            group = "UI";
            manager.load(workingdirectory+"UI/close_notification.png",Texture.class);
            manager.load(workingdirectory+"UI/information.png",Texture.class);




        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }


    }

    public static void save() {


        try {
            group = "";

            //ParameterBilder
            group = "ParameterBilder";
            img_WaitBlock_warteZeit_Parameter = manager.get(workingdirectory + "Block/Parameter/WaitParameter.png", Texture.class);
            Parameter_High_Low = manager.get(workingdirectory + "Block/Parameter/parameter_high_low.png", Texture.class);
            Parameter_Pin = manager.get(workingdirectory + "Block/Parameter/Parameter_Pin.png", Texture.class);
            Parameter_IO = manager.get(workingdirectory + "Block/Parameter/Parameter_IO.png", Texture.class);
            Parameter_istgleich = manager.get(workingdirectory + "Block/Parameter/istgleich.png", Texture.class);
            Parameter_erstens = manager.get(workingdirectory + "Block/Parameter/erstens.png", Texture.class);
            Parameter_zweitens = manager.get(workingdirectory + "Block/Parameter/zweitens.png", Texture.class);
            Parameter_drittens = manager.get(workingdirectory + "Block/Parameter/drittens.png", Texture.class);
            Parameter_viertens = manager.get(workingdirectory + "Block/Parameter/viertens.png", Texture.class);
            Parameter_Minus = manager.get(workingdirectory + "Block/Parameter/Minus.png", Texture.class);
            Parameter_Plus = manager.get(workingdirectory + "Block/Parameter/Plus.png", Texture.class);




            Plug_IntParameter = manager.get(workingdirectory + "Block/ParameterStecker/ZahlParameterStecker.png", Texture.class);
            Plug_StringParameter = manager.get(workingdirectory + "Block/ParameterStecker/TextParameterStecker.png", Texture.class);
            Plug_BooleanParameter = manager.get(workingdirectory + "Block/ParameterStecker/BooleanParameterStecker.png", Texture.class);


            arduinomegaimage = manager.get(workingdirectory + "arduinomegaminiimage.png", Texture.class);
            arduinonanoimage = manager.get(workingdirectory + "arduinonanominiimage.png", Texture.class);
            arduinounoimage = manager.get(workingdirectory + "arduinounominiimage.png", Texture.class);


            img_Tab = manager.get(workingdirectory + "TabBar/Tabimage.png", Texture.class);

            img_startbutton = manager.get(workingdirectory + "ButtonBar/startbutton.png", Texture.class);
            img_startbutton_mouseover = manager.get(workingdirectory + "ButtonBar/startbutton_mouseover.png", Texture.class);
            img_startbutton_pressed = manager.get(workingdirectory + "ButtonBar/startbutton_pressed.png", Texture.class);

            img_stopbutton = manager.get(workingdirectory + "ButtonBar/stopbutton.png", Texture.class);
            img_stopbutton_mouseover = manager.get(workingdirectory + "ButtonBar/stopbutton_mouseover.png", Texture.class);
            img_stopbutton_pressed = manager.get(workingdirectory + "ButtonBar/stopbutton_pressed.png", Texture.class);

            img_projectstructur = manager.get(workingdirectory + "ButtonBar/projektstrukturbutton.png", Texture.class);
            img_projectstructur_mouseover = manager.get(workingdirectory + "ButtonBar/projektstrukturbutton_mouseover.png", Texture.class);
            img_projectstructur_pressed = manager.get(workingdirectory + "ButtonBar/projektstrukturbutton_pressed.png", Texture.class);

            img_debugstart = manager.get(workingdirectory + "ButtonBar/debugbutton.png", Texture.class);
            img_debugstart_mouseover = manager.get(workingdirectory + "ButtonBar/debugbutton_mouseover.png", Texture.class);
            img_debugstart_pressed = manager.get(workingdirectory + "ButtonBar/debugbutton_pressed.png", Texture.class);

            img_editor = manager.get(workingdirectory + "ButtonBar/editorbutton.png", Texture.class);
            img_editor_mouseover = manager.get(workingdirectory + "ButtonBar/editorbutton_mouseover.png", Texture.class);
            img_editor_pressed = manager.get(workingdirectory + "ButtonBar/editorbutton_pressed.png", Texture.class);

            img_addrunconfig = manager.get(workingdirectory + "ButtonBar/addrunconfig.png", Texture.class);
            img_addrunconfig_mouseover = manager.get(workingdirectory + "ButtonBar/addrunconfig_mouseover.png", Texture.class);
            img_addrunconfig_pressed = manager.get(workingdirectory + "ButtonBar/addrunconfig_pressed.png", Texture.class);


            waitforfinishbuild = manager.get(workingdirectory + "loadinganimation.png", Texture.class);


            img_mappe1 = manager.get(workingdirectory + "Bar/Mappe1.png", Texture.class);
            img_mappe2 = manager.get(workingdirectory + "Bar/Mappe2.png", Texture.class);
            img_mappe3 = manager.get(workingdirectory + "Bar/Mappe3.png", Texture.class);
            img_mappe4 = manager.get(workingdirectory + "Bar/Mappe4.png", Texture.class);
            img_mappe5 = manager.get(workingdirectory + "Bar/Mappe5.png", Texture.class);
            img_mappe6 = manager.get(workingdirectory + "Bar/Mappe6.png", Texture.class);



            connector = manager.get(workingdirectory + "connector.png", Texture.class);
            connector_offerd = manager.get(workingdirectory + "connector_offerd.png", Texture.class);

          block_right =  manager.get(workingdirectory + "Block/block_right.png", Texture.class);
           block_middle = manager.get(workingdirectory + "Block/block_middle.png", Texture.class);
          block_left = manager.get(workingdirectory + "Block/block_left.png", Texture.class);

          green_bar_left =  manager.get(workingdirectory + "Block/color_bar/green_left.png", Texture.class);
            green_bar_middle =  manager.get(workingdirectory + "Block/color_bar/green_middle.png", Texture.class);
            green_bar_right =  manager.get(workingdirectory + "Block/color_bar/green_right.png", Texture.class);

            mouse_over_rechts = manager.get(workingdirectory + "Block/mouseover_rechts.png", Texture.class);
            mouseover_links = manager.get(workingdirectory + "Block/mouseover_links.png", Texture.class);
            mouse_over_mitte = manager.get(workingdirectory + "Block/mouseover_mitte.png", Texture.class);
            marked_rechts = manager.get(workingdirectory + "Block/market_rechts.png", Texture.class);
            marked_links = manager.get(workingdirectory + "Block/market_links.png", Texture.class);
            marked_mitte = manager.get(workingdirectory + "Block/market_mitte.png", Texture.class);
            PinModeBlock_smallimage = manager.get(workingdirectory + "Block/Block_PinMode/smallblock.png", Texture.class);
            DigitalWrite_smallimage = manager.get(workingdirectory + "Block/Block_DigitalWrite/smallblock.png", Texture.class);
            WaitBlock_smallimage = manager.get(workingdirectory + "Block/Block_Wait/smallblock.png", Texture.class);
            WaitBlock_description_image = manager.get(workingdirectory + "Block/Block_Wait/beschreibungsbild.png", Texture.class);
            PinModeBlock_description_image = manager.get(workingdirectory + "Block/Block_PinMode/beschreibungsbild.png", Texture.class);
            DigitalWrite_description_image = manager.get(workingdirectory + "Block/Block_DigitalWrite/beschreibungsbild.png", Texture.class);
            aufklapppfeil = manager.get(workingdirectory + "aufklapppfeil.png", Texture.class);


            //Switch
            switch_background = manager.get(workingdirectory + "switchbackground.png", Texture.class);
            switch_inside = manager.get(workingdirectory + "switchinside.png", Texture.class);
            switch_background_green = manager.get(workingdirectory + "switchbackgroundgreen.png", Texture.class);
            //Switch White
            switch_background_white = manager.get(workingdirectory + "switchbackground_white.png", Texture.class);
            switch_inside_white = manager.get(workingdirectory + "switchinside.png", Texture.class);
            switch_background_green_white = manager.get(workingdirectory + "switchbackground_whitegreen.png", Texture.class);

            //wire
            wire = manager.get(workingdirectory + "wire.png", Texture.class);
            //Wire Node
            wire_node = manager.get(workingdirectory + "node.png", Texture.class);
            //Plugin
            pluginwait = manager.get(workingdirectory + "pluginwaiting.png", Texture.class);
            //UI
           close_notification =  manager.get(workingdirectory+"UI/close_notification.png",Texture.class);
           information =  manager.get(workingdirectory+"UI/information.png",Texture.class);
        } catch (Exception e) {
            e.printStackTrace();
            DisplayErrors.error = e;
        }


    }

    public static void unload() {


    }
}
