package de.ft.robocontrol.roboconnection;

import de.ft.robocontrol.Block.Arduino;
import de.ft.robocontrol.UI.ConnectionWindow;
import de.ft.robocontrol.UI.UI;
import de.ft.robocontrol.data.VerbindungsSpeicher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BurnProgramm {
    static String platform;
    private static String OS = System.getProperty("os.name").toLowerCase();

    protected static void burn(int arduino, String port, String file) {
        String methode = null;
        if (arduino == Arduino.UNO) { //TODO add platforms
            platform = "atmega328p";
            methode = "arduino";
        }

        if (arduino == Arduino.MEGA) {
            platform = "atmega2560";
            methode = "wiring";
        }


        if (isWindows()) {
            burnWindows(platform, port, file, methode);
        } else if (isMac()) {
            brunApple(platform, port, file, methode);
        } else if (isUnix()) {
            burnLinux(platform, port, file, methode);

        } else {
            System.out.println("Your OS is not support!!");
        }

    }

    //OS tesster
    private static boolean isWindows() {

        return (OS.indexOf("win") >= 0);

    }

    private static boolean isMac() {

        return (OS.indexOf("mac") >= 0);

    }

    private static boolean isUnix() {

        return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0);

    }


    //Burn start


    private static void burnLinux(String platform, String port, String file, String methode) {

        Runtime rt = Runtime.getRuntime();


        port = "/dev/" + port;
        try {
            Process pr = rt.exec("./libs/avrdude -Clibs/avrdude.conf -v -p" + platform + " -c " + methode + " -P" + port + " -b115200 -D -Uflash:w:libs/" + file + ":i"); //TODO Progress
            System.out.println(pr.getInputStream());


            BufferedReader input = new BufferedReader(new InputStreamReader(pr.getErrorStream()));
            String line = null;
            String output = null;

            while ((line = input.readLine()) != null) {
                System.out.println(line);
                output = output + line;

            }


            postproduktion(output, port);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private static void burnWindows(String platform, String port, String file, String methode) {

        Runtime rt = Runtime.getRuntime();


        port = "" + port;
        try {
            Process pr = rt.exec("libs\\avrdude.exe -C libs\\avrdude.conf -v -p " + platform + " -c" + methode + " -P" + port + " -b115200 -D -Uflash:w:libs\\" + file + ":i"); //TODO Progress
            System.out.println(pr.getInputStream());


            BufferedReader input = new BufferedReader(new InputStreamReader(pr.getErrorStream()));
            String line = null;
            String output = null;

            while ((line = input.readLine()) != null) {
                System.out.println(line);
                output = output + line;

            }


            postproduktion(output, port);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void brunApple(String platform, String port, String file, String methode) {

        Runtime rt = Runtime.getRuntime();


        port = "/dev/" + port;
        try {
            Process pr = rt.exec("libs/avrdudeapple -C libs/avrdude.conf -v -p " + platform + " -c" + methode + " -P" + port + " -b115200 -D -Uflash:w:libs/" + file + ":i"); //TODO Progress
            System.out.println(pr.getInputStream());


            BufferedReader input = new BufferedReader(new InputStreamReader(pr.getErrorStream()));
            String line = null;
            String output = null;

            while ((line = input.readLine()) != null) {
                System.out.println(line);
                output = output + line;

            }
            System.out.println(output);


            postproduktion(output, port);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static void postproduktion(String output, String port) {

        if (output.contains("AVR device initialized")) {
            ConnectionWindow.error.setText("Brennen hat funktioniert, nun kannst du dein Gerät konfigurieren");
            VerbindungsSpeicher.verbundungen.add(new VerbindungsSpeicher("Neue Verbindung"));
            UI.connectionWindow.verbindungstabs();


        } else {
            ConnectionWindow.error.setText("Unbekannter Fehler");
        }

        if (output.contains("can't open device")) {
            ConnectionWindow.error.setText("Brennen nicht erfolgreich hast du den richtigen Port und das richtige Board ausgewählt?");
        }

        if (output.contains("Expected signature")) {
            ConnectionWindow.error.setText("Brennen nicht erfolgreich du hast den falschen Board-Type ausgewählt");
        }

        if (output.contains("not found")) {
            ConnectionWindow.error.setText("Brennen nicht erfolgreich du hast den falschen Board-Type ausgewählt");
        }

        if (output.contains("programmer is not responding")) {
            ConnectionWindow.error.setText("Brennen nicht erfolgreich hast du den richtigen Port und das richtige Board ausgewählt?");
        }

        if (output.contains("Permission denied")) {
            if (isUnix()) {
                ConnectionWindow.error.setText("Keine Berechtigung! Führe den Befehl: sudo chmod a+rw " + port + " aus.");
            } else {
                ConnectionWindow.error.setText("Keine Berechtigung starte den PC neu.");
            }
        }

        ConnectionWindow.update();

    }


}