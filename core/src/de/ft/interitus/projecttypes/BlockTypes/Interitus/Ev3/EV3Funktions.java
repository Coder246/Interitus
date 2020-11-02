/*
 * Copyright (c) 2020.
 * Copyright by Tim and Felix
 */

package de.ft.interitus.projecttypes.BlockTypes.Interitus.Ev3;

import com.kotcrab.vis.ui.widget.VisTable;
import de.ft.interitus.Block.Block;
import de.ft.interitus.Program;
import de.ft.interitus.UI.ManualConfig.DeviceConfiguration;
import de.ft.interitus.UI.UI;
import de.ft.interitus.UI.UIElements.dropdownmenue.DropDownElement;
import de.ft.interitus.deviceconnection.ev3connection.*;
import de.ft.interitus.deviceconnection.ev3connection.usb.USBConnectionHandle;
import de.ft.interitus.deviceconnection.ev3connection.usb.USBDevice;
import de.ft.interitus.loading.AssetLoader;
import de.ft.interitus.projecttypes.ProjectFunktions;
import de.ft.interitus.projecttypes.Tool;
import de.ft.interitus.utils.ArrayList;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.UUID;

public class EV3Funktions implements ProjectFunktions {
    public com.badlogic.gdx.utils.DelayedRemovalArray<Device> ev3devices = new com.badlogic.gdx.utils.DelayedRemovalArray<>();
    public ConnectionHandle usbConnectionHandle = new USBConnectionHandle();
    private int counter = 0;

    @Override
    public void create() {

    }

    public void removeEv3(Device device) {

        if(device instanceof USBDevice&&((USBDevice) device).getDevice().isOpen()) {
            ((USBDevice) device).getDevice().close();

        }

        for (int i = 0; i < UI.runselection.getElements().size(); i++) {


            if (UI.runselection.getElements().get(i).getIdentifier().equals(device)) {
                UI.runselection.getElements().remove(UI.runselection.getElements().get(i));
                break;

            }

        }

    }

    public void addEv3(Device device) {
        if(device instanceof USBDevice&&!((USBDevice) device).getDevice().isOpen()) {

            ((USBDevice) device).getDevice().open();

        }
        String name = device.getName();
        UI.runselection.addelement(new DropDownElement(AssetLoader.DigitalWrite_description_image, name, device));

        ArrayList<Byte> connected = new ArrayList<>();
        connected.addAll(Operations.playSound("../tools/Bluetooth/Connect",100,false));
        device.getConnectionHandle().sendData(ev3.makeDirectCmd(connected,0,0),device);

        registernewEv3(device);


    }



    @Override
    public void update() {
        counter++;
        usbConnectionHandle.update();



        if (UI.runselection.getSelectedElement() == null && UI.runselection.getElements().size() > 0) {

            UI.runselection.setSelectedElement(UI.runselection.getElements().get(0));

        } else {

            if (UI.runselection.getElements().size() == 0) {

                UI.runselection.setDefaultText("Bitte Gerät verbinden");
                UI.runselection.setSelectedElement(null);

            }

        }


        if (counter >= 6) {

            counter = 0;

            if (UI.runselection.getSelectedElement() != null) {
                UI.runselection.getSelectedElement().setText(((USBDevice) UI.runselection.getSelectedElement().getIdentifier()).getName());

            }


        }


    }

    @Override
    public void switchedto() {
        for(Device device:ev3devices) {

            if(device instanceof USBDevice) {
                ((USBDevice) device).getDevice().close();
            }

        }
        ev3devices.clear();
    }

    @Override
    public void runconfigsettings(VisTable builder, DeviceConfiguration configuration) {

    }

    @Override
    public void projectsettings(VisTable builder, Object settings) {

    }


    @Override
    public void changedrunconfig() {

    }

    @Override
    public boolean isblockconnected(Block block) {
        return true;
    }

    @Override
    public boolean isVariableAvailable(String name) {
        return false;
    }

    @Override
    public ArrayList<Tool> getProjectTools() {
        return null;
    }

    private void registernewEv3(Device device) {
        try {
            boolean existsfolder = false;
            for(String string:Ev3SystemUtils.listedfilestoStrings(Ev3SystemUtils.ListFilesinPath("/home/root/lms2012/apps/IR Control/",device))) {
                if(string.contentEquals("Interitus/")) {
                    existsfolder = true;
                    break;
                }
            }

            if(!existsfolder) {
                Ev3SystemUtils.create_Dir("/home/root/lms2012/apps/IR Control/Interitus/",device);

            }



            boolean existsUUID = false;
            for(String string:Ev3SystemUtils.listedfilestoStrings(Ev3SystemUtils.ListFilesinPath("/home/root/lms2012/apps/IR Control/Interitus/",device))) {
                if (string.contentEquals("uuid")) {

                    existsUUID = true;

                }

                //TODO Check others
            }


            if(!existsUUID) {

                Ev3SystemUtils.downloadFile("/home/root/lms2012/apps/IR Control/Interitus/uuid", UUID.randomUUID().toString(),device);

            }



        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
