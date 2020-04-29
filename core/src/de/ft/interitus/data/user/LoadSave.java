package de.ft.interitus.data.user;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import de.ft.interitus.ProgrammingSpace;
import de.ft.interitus.data.programm.Data;
import de.ft.interitus.data.user.changes.DataManager;
import de.ft.interitus.DisplayErrors;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

import static de.ft.interitus.ProgrammingSpace.saver;

public class LoadSave {
   static Thread open;
    static Thread save;
    public static void saveas() {
        save = new Thread() {
            @Override
            public void run() {

                try {


                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setDialogTitle("Speichern unter...");
                    fileChooser.setFileFilter(new FileNameExtensionFilter("Projektdatei (.rac)", "rac"));

                    int userSelection = fileChooser.showSaveDialog(saver);
                    fileChooser.setMultiSelectionEnabled(false);


                    if (userSelection == JFileChooser.APPROVE_OPTION) {
                        File fileToSave = fileChooser.getSelectedFile();
                        System.out.println("Save as file: " + fileToSave.getName());


                        if (fileToSave.getAbsolutePath().contains(".rac")) {
                            DataManager.path = fileToSave.getAbsolutePath();
                            DataSaver.save(Gdx.files.absolute(fileToSave.getAbsolutePath()));
                            DataManager.filename = fileToSave.getName();

                        } else {
                            DataManager.path = fileToSave.getAbsolutePath() + ".rac";
                            DataSaver.save(Gdx.files.absolute(fileToSave.getAbsolutePath() + ".rac"));
                            DataManager.filename = fileToSave.getName() + ".rac";
                        }
                        System.out.println(DataManager.path);

                        if (Data.filename.size() > 9) {
                            Data.filename.remove(0);

                        }

                        if (Data.filename.indexOf(fileToSave.getName()) > -1) {
                            int temp = Data.filename.indexOf(fileToSave.getName());
                            Data.filename.remove(temp);
                            Data.path.remove(temp);
                        }


                        Data.filename.add(fileToSave.getName());
                        Data.path.add(fileToSave.getAbsolutePath());

                        DataManager.saved();


                    }
                }catch (Exception e) {
                    e.printStackTrace();//for debug to find errors
                    DisplayErrors.error = e;
                }
            }

            //SAVE DATA


        };

        save.start();
    }


    public static void open() {
         open = new Thread() {
            @Override
            public void run() {
                JFileChooser fileChooser = new JFileChooser();

                fileChooser.setFileFilter(new FileNameExtensionFilter("Projektdatei (.rac)", "rac"));
                int result = fileChooser.showOpenDialog(ProgrammingSpace.saver);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();

                    System.out.println("Selected file: " + selectedFile.getName());
                    FileHandle handle = Gdx.files.internal(selectedFile.getAbsolutePath());
                    DataManager.path = selectedFile.getAbsolutePath();
                    DataManager.filename = selectedFile.getName();
                    DataLoader.load(handle);
                    DataManager.saved();
                    DataManager.filename = selectedFile.getName();

                    if (Data.filename.size() > 9) {
                        Data.filename.remove(0);

                    }
                    if (Data.path.indexOf(selectedFile.getAbsolutePath()) > -1) {
                        int temp = Data.filename.indexOf(selectedFile.getName());
                        Data.filename.remove(temp);
                        Data.path.remove(temp);
                    }


                    Data.filename.add(selectedFile.getName());
                    Data.path.add(selectedFile.getAbsolutePath());

                }
            }
        };
        open.start();
    }

    /***
     *
     * @return a boolean if the open dialoge is opened
     */

    public static boolean isopenopen() {

        try {
            if (open.isAlive()) {
                return true;
            } else {
                return false;
            }
        }catch (Exception e) {
            return false;
        }
    }

    /***
     *
     * @return a boolean if the save dialoge is opened
     */

    public static boolean issaveopen() {

        try {
            if (save.isAlive()) {
                return true;
            } else {
                return false;
            }
        }catch (Exception e) {
            return false;
        }
    }

}
