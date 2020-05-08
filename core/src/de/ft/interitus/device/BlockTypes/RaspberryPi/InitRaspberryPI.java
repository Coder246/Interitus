package de.ft.interitus.device.BlockTypes.RaspberryPi;

import de.ft.interitus.device.BlockTypes.Ev3.Wait;
import de.ft.interitus.device.BlockTypes.PlatformSpecificBlock;
import de.ft.interitus.device.DeviceTypes.Ev3;
import de.ft.interitus.plugin.PluginManagerHandler;
import de.ft.interitus.projecttypes.ProjektTypes;

import java.util.ArrayList;

public class InitRaspberryPI {
    static ArrayList<PlatformSpecificBlock> platformSpecificBlocks = new ArrayList<>();
    public static ArrayList<PlatformSpecificBlock> init() {

        platformSpecificBlocks.add(new Wait());
        PluginManagerHandler.projekttypes.add(new ProjektTypes(new Ev3(),"RaspberryPi-Projekt",platformSpecificBlocks));
        return platformSpecificBlocks;
    }
}
