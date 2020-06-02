package de.ft.interitus.projecttypes.device.BlockTypes.Arduino.Arduino;

import de.ft.interitus.projecttypes.ProjectTypes;
import de.ft.interitus.projecttypes.device.BlockTypes.PlatformSpecificBlock;
import de.ft.interitus.projecttypes.device.DeviceTypes.Arduino.Arduino.Arduino;

import java.util.ArrayList;

public class InitArduino {
   static ArrayList<PlatformSpecificBlock> blocks  = new ArrayList<>();




    public static ProjectTypes init() {


        blocks.add(new Wait());







        return new ProjectTypes(new Arduino(),"Arduino-Projekt",blocks);
    }
}
