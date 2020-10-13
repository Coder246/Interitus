/*
 * Copyright (c) 2020.
 * Copyright by Tim and Felix
 */

package de.ft.interitus.Block.BlockUpdate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.math.Vector3;
import de.ft.interitus.Block.Block;
import de.ft.interitus.Block.DataWire;
import de.ft.interitus.Block.Wire;
import de.ft.interitus.DisplayErrors;
import de.ft.interitus.Program;
import de.ft.interitus.ProgramingSpace;
import de.ft.interitus.UI.ProgramGrid;
import de.ft.interitus.UI.UIElements.check.CheckKollision;
import de.ft.interitus.UI.UIElements.check.CheckMouse;
import de.ft.interitus.UI.UIVar;
import de.ft.interitus.Var;
import de.ft.interitus.events.EventVar;
import de.ft.interitus.events.block.BlockKillMovingWiresEvent;
import de.ft.interitus.loading.AssetLoader;
import de.ft.interitus.projecttypes.ProjectManager;
import de.ft.interitus.utils.Unproject;

import java.util.Timer;
import java.util.TimerTask;


public abstract class BlockUpdate extends Thread {
    public Block block; //Der zugehörige Block den die Klasse updated
    public boolean isrunning = true; //Läuft der Thread gerade?
    public Timer time; //das  ist der Timer in dem alle Update Vorgänge laufen
    public boolean isconnectorclicked = false;//Ist der connector des zuständigen Blocks ausgelöst
    public boolean geschoben = false;
    public Wire tempwire;
    public boolean toggle; // Ist der Block von der mouse gehovert?
    Vector3 temp3;//Temp vectoren für berechnungs zwischen schritte
    Vector3 temp4;//Temp vectoren für berechnungs zwischen schritte
    private boolean willbedelete = false;
    private boolean deleteonend = false;
    private boolean IsMousealreadypressed = false;
    private boolean changewillbedeleted = false;
    private boolean tempdelete = false;

    private boolean openwindow = false;

    public BlockUpdate(Block block) {
        this.block = block; //Der Block wird zugewiesen

        temp3 = new Vector3(0, 0, 0);//Temp Vectoren init
        temp4 = new Vector3(0, 0, 0);//Temp Vectoren init
    }


    @Override
    public void run() {


        time = new Timer();

        time.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {


                control_block_errors(); //Check if BlockUpdate is running correctly

                if (!UIVar.isdialogeopend && ProjectManager.getActProjectVar() != null) {

                    try {


                        if (block == null) { //Wenn kein Block mehr verbunden ist wird der timer beendet und damit auch der thread
                            time.cancel();
                        }
                        if (block.getIndex() == -1) { //Wenn der Block gelöscht wird d.h. er hat einen Index von -1 wird der Timer beendet und damit auch der Thread
                            time.cancel();
                            time.purge();
                        }

                        toggle = CheckKollision.checkmousewithblock(block); //Wird der Block von der Mouse gehovert?


                        wire_managment();


                        if (IsMousealreadypressed && !Gdx.input.isButtonPressed(0)) {
                            IsMousealreadypressed = false;
                        }


                        /////////////////////////////Datawire erzeigen
                        if (block.getBlocktype() != null && block.getBlocktype().getBlockParameter() != null) {
                            for (int i = 0; i < block.getBlocktype().getBlockParameter().size(); i++) {
                                if (block.getBlocktype().getBlockParameter().get(i).getParameterType().isOutput()) {


                                    if (CheckKollision.checkpointwithobject(block.getBlocktype().getBlockParameter().get(i).getX(), block.getBlocktype().getBlockParameter().get(i).getY(), UIVar.parameter_width, UIVar.parameter_height, Unproject.unproject()) && Gdx.input.isButtonJustPressed(0) && ProjectManager.getActProjectVar().moveingdatawire == null) {
                                        block.getBlocktype().getBlockParameter().get(i).getDatawire().add(new DataWire(block.getBlocktype().getBlockParameter().get(i)));
                                        block.setMoving(false);
                                        ProjectManager.getActProjectVar().moveingdatawire = block.getBlocktype().getBlockParameter().get(i).getDatawire().getLastObject();

                                    }


                                }
                            }
                        }

                        block_moveingengine();

                        if (ProjectManager.getActProjectVar().marked_block == null && block.isMarked()) {
                            block.setMarked(false);
                            ProjectManager.getActProjectVar().marked_block = null;
                        }


                        if ((block.isShowdupulicate_links() && block.getBlocktype().canhasleftconnector()) || (block.isShowdupulicate_rechts() && block.getBlocktype().canhasrightconnector())) {
                            if (ProjectManager.getActProjectVar().showduplicat.indexOf(block) == -1) {
                                ProjectManager.getActProjectVar().showduplicat.add(block);
                            }
                        } else {
                            ProjectManager.getActProjectVar().showduplicat.remove(block);
                            ProjectManager.getActProjectVar().biggestblock = null;
                        }


                        int biggestvalue = 0;
                        int biggestindex = -1;
                        for (int i = 0; i < ProjectManager.getActProjectVar().showduplicat.size(); i++) {

                            try {
                                if (ProjectManager.getActProjectVar().showduplicat.get(i).getDublicatmarkedblockuberlappungsflache() > biggestvalue) {
                                    biggestvalue = ProjectManager.getActProjectVar().showduplicat.get(i).getDublicatmarkedblockuberlappungsflache();
                                    biggestindex = i;
                                }
                            } catch (Exception e) {
                                // e.printStackTrace();
                            }
                        }
                        try {
                            ProjectManager.getActProjectVar().biggestblock = ProjectManager.getActProjectVar().showduplicat.get(biggestindex);
                        } catch (IndexOutOfBoundsException e) {

                        }


                        try {


                            if (CheckKollision.object(ProjectManager.getActProjectVar().marked_block.getX(), ProjectManager.getActProjectVar().marked_block.getY(), ProjectManager.getActProjectVar().marked_block.getW(), ProjectManager.getActProjectVar().marked_block.getH(), block.getX(), block.getY(), block.getW(), block.getH()) && CheckKollision.flache(ProjectManager.getActProjectVar().marked_block.getX(), ProjectManager.getActProjectVar().marked_block.getY(), ProjectManager.getActProjectVar().marked_block.getW(), ProjectManager.getActProjectVar().marked_block.getH(), block.getX(), block.getY()) > 7000 && block != ProjectManager.getActProjectVar().marked_block) {
                                // Program.logger.config("überschneidung von markedblock und einem block");

                                if (ProjectManager.getActProjectVar().marked_block_overlapping.indexOf(block) == -1) {
                                    block.moved = false;

                                    ProjectManager.getActProjectVar().marked_block_overlapping.add(block);
                                }
                            } else {
                                ProjectManager.getActProjectVar().marked_block_overlapping.remove(block);


                            }


                            int biggestvalue2 = 0;
                            int biggestindex2 = -1;

                            for (int i = 0; i < ProjectManager.getActProjectVar().marked_block_overlapping.size(); i++) {

                                try {
                                    if (ProjectManager.getActProjectVar().marked_block_overlapping.get(i).getBlockMarkedblockuberlappungsflache() > biggestvalue2) {

                                        biggestvalue2 = ProjectManager.getActProjectVar().marked_block_overlapping.get(i).getBlockMarkedblockuberlappungsflache();
                                        biggestindex2 = i;

                                    }
                                } catch (NullPointerException e) {
                                } catch (IndexOutOfBoundsException e) {

                                }
                            }

                            try {
                                if (ProjectManager.getActProjectVar().marked_block_overlapping.get(biggestindex2) != ProjectManager.getActProjectVar().jumping_block) {
                                    ProjectManager.getActProjectVar().jumping_block = ProjectManager.getActProjectVar().marked_block_overlapping.get(biggestindex2);
                                }


                            } catch (IndexOutOfBoundsException e) {
                            }
                        } catch (NullPointerException e) {
                        }


                        try {

                            if (block == ProjectManager.getActProjectVar().jumping_block && block.moved == false && !geschoben && !block.getWire_left().isSpace_between_blocks() && !block.getWire_right().isSpace_between_blocks()) {
                                block.moved = true;
                                geschoben = true;

                                Block a = block;
                                //Program.logger.config(a);
                                block.setX(block.getX() + block.getW());

                                block.seted = false;

                                while (a.getRight() != null) {

                                    //block.getRight().setX(block.getRight().getX() + block.getW());

                                    a.getRight().setX(a.getX() + a.getW());
                                    a = a.getRight();
                                }


                            }


                        } catch (NullPointerException e) { //If there are no wires for example if you delete a block with two wires
                            if (block == ProjectManager.getActProjectVar().jumping_block && block.moved == false && !geschoben) {
                                block.moved = true;
                                geschoben = true;

                                Block a = block;
                                block.setX(block.getX() + block.getW());

                                block.seted = false;


                                while (a.getRight() != null) {

                                    //block.getRight().setX(block.getRight().getX() + block.getW());

                                    a.getRight().setX(a.getX() + a.getW());
                                    a = a.getRight();

                                }


                            }

                        }

                        if (block.seted == false && ProjectManager.getActProjectVar().biggestblock == block && !Gdx.input.isButtonPressed(0)) {

                            block.seted = true;
                            geschoben = false;
                        }

                        if (block.seted == false && ProjectManager.getActProjectVar().biggestblock != block) {
                            geschoben = false;


                            Block b = block;

                            block.setX(block.getX() - block.getW());


                            while (b.getRight() != null) {

                                //block.getRight().setX(block.getRight().getX() + block.getW());

                                b.getRight().setX(b.getRight().getX() - b.getW());
                                b = b.getRight();
                            }


                            block.seted = true;
                        }


                        if (!CheckKollision.checkmousewithblock(block) && Gdx.input.isButtonPressed(0) && !block.isMoving() && block.isMarked() && (!CheckMouse.isMouseover(UIVar.blockeinstellungen_x, UIVar.blockeinstellungen_y, UIVar.blockeinstellungen_w, UIVar.blockeinstellungen_h, false) && !CheckMouse.wasMousePressed(UIVar.blockeinstellungen_x, UIVar.blockeinstellungen_y, UIVar.blockeinstellungen_w, UIVar.blockeinstellungen_h) || !UIVar.isBlockSettingsopen)) {
                            block.setMarked(false);
                            ProjectManager.getActProjectVar().marked_block = null;
                        }


                        //Setzt die Nachbaren
                        if (ProjectManager.getActProjectVar().marked_block != null && !block.isMarked()) {
                            try {
                                if (CheckKollision.checkblockwithduplicate(ProjectManager.getActProjectVar().marked_block, block, 0) && block.getRight() == null && ProjectManager.getActProjectVar().marked_block.getWire_left() == null && ProjectManager.getActProjectVar().marked_block.getBlocktype().canhasleftconnector()) {
                                    if (ProjectManager.getActProjectVar().marked_block.isMoving()) {
                                        //Program.logger.config("Kollision!");


                                        block.setShowdupulicate_rechts(true);


                                    } else {

                                        //Set Block only if you don't move it

                                        if (block.getRight() != ProjectManager.getActProjectVar().marked_block && ProjectManager.getActProjectVar().marked_block.getLeft() != block && block.getRight() == null && ProjectManager.getActProjectVar().biggestblock == block) {

                                            //Program.logger.config("test");
                                            block.setShowdupulicate_rechts(false);


                                            block.setWire_right(ProjectManager.getActProjectVar().projectType.getWireGenerator().generate(block, ProjectManager.getActProjectVar().marked_block));
                                            ProjectManager.getActProjectVar().marked_block.setWire_left(block.getWire_right());
                                            ProjectManager.getActProjectVar().wires.add(block.getWire_right());

                                            block.setRight(ProjectManager.getActProjectVar().marked_block);
                                            ProjectManager.getActProjectVar().marked_block.setY(block.getY());
                                            ProjectManager.getActProjectVar().marked_block.setX(block.getX_dup_rechts());
                                        }


                                    }
                                } else {
                                    block.setShowdupulicate_rechts(false);
                                }
                            } catch (Exception e) {

                            }


                            try {
                                if (CheckKollision.checkblockwithduplicate(ProjectManager.getActProjectVar().marked_block, block, 1) && block.getLeft() == null && ProjectManager.getActProjectVar().marked_block.getWire_right() == null && ProjectManager.getActProjectVar().marked_block.getBlocktype().canhasrightconnector()) {

                                    if (ProjectManager.getActProjectVar().marked_block.isMoving()) {


                                        block.setShowdupulicate_links(true);


                                    } else {


                                        if (block.getRight() != ProjectManager.getActProjectVar().marked_block && ProjectManager.getActProjectVar().marked_block.getLeft() != block && block.getLeft() == null && ProjectManager.getActProjectVar().biggestblock == block) {


                                            block.setShowdupulicate_links(false);


                                            block.setWire_left(ProjectManager.getActProjectVar().projectType.getWireGenerator().generate(ProjectManager.getActProjectVar().marked_block, block));
                                            ProjectManager.getActProjectVar().marked_block.setWire_right(block.getWire_left());
                                            ProjectManager.getActProjectVar().wires.add(block.getWire_left());


                                            block.setLeft(ProjectManager.getActProjectVar().marked_block);
                                            ProjectManager.getActProjectVar().marked_block.setY(block.getY());
                                            ProjectManager.getActProjectVar().marked_block.setX(block.getX() - ProjectManager.getActProjectVar().marked_block.getW());
                                        }


                                    }
                                } else {
                                    block.setShowdupulicate_links(false);
                                }

                            } catch (NullPointerException e) {

                            }
                        }

                        if (deleteonend) {
                            ProjectManager.getActProjectVar().removeblock = false;
                            Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
                            block.delete(false);
                            this.cancel();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        try {
                            DisplayErrors.customErrorstring = "Fehler in einem " + block.getBlocktype().getName() + " Block mit der ID " + block.getIndex();
                        } catch (Exception v) {
                            DisplayErrors.customErrorstring = "Fehler in einem Block";
                        }
                        DisplayErrors.error = e;
                    }

                }

            }
        }, 0, 20);

    }


    private void wire_managment() {
        if (block.getBlocktype().canhasleftconnector() && !isIsconnectorclicked() && ProjectManager.getActProjectVar().showleftdocker && CheckKollision.object(block.getX_entrance(), block.getY_entrance(), block.getW_entrance(), block.getH_entrance(), (int) ProgramingSpace.viewport.unproject(temp3.set(Gdx.input.getX(), Gdx.input.getY(), 0)).x, (int) ProgramingSpace.viewport.unproject(temp4.set(Gdx.input.getX(), Gdx.input.getY(), 0)).y, 1, 1) && Gdx.input.isButtonJustPressed(0) && block.getLeft() == null) {
            ProjectManager.getActProjectVar().showleftdocker = false;
            ProjectManager.getActProjectVar().moving_wires.setMovebymouse(false);
            ProjectManager.getActProjectVar().moving_wires.setRight_connection(block);
            ProjectManager.getActProjectVar().moving_wires.setSpace_between_blocks(true);
            block.setWire_left(ProjectManager.getActProjectVar().moving_wires);
            try {
                ProjectManager.getActProjectVar().wire_beginn.setRight(block);
            } catch (NullPointerException e) {

                //Falls die eine Node dazwischen ist und der Nachbar über die Node gesetzt werden muss

                Program.logger.severe("Konnte Nachbar nicht setzten");


            }

            ProjectManager.getActProjectVar().moving_wires.getRight_connection().getLeft().getBlockupdate().isconnectorclicked = false;
            ProjectManager.getActProjectVar().moving_wires = null;


        }


        if ((isconnectorclicked && Gdx.input.isKeyPressed(Input.Keys.ESCAPE))) { //Um vorzeitige wire wieder aufzulösen und ggf zu richtigen umzuwandeln

            isconnectorclicked = false;
            ProjectManager.getActProjectVar().showleftdocker = false;

            try {
                tempwire.getLeft_connection().setWire_right(null);
                ProjectManager.getActProjectVar().visible_wires.remove(tempwire);
                ProjectManager.getActProjectVar().wires.remove(tempwire);

                try {

                    tempwire.getRight_connectionObject().getwirenode().setWire_left(null);
                    tempwire.setRight_connection(null);

                } catch (NullPointerException e) {
                    //Falls hier keine Wire ist
                }
                tempwire = null;
            } catch (Exception e) {

            }

            ProjectManager.getActProjectVar().moving_wires = null;


        }

        if (!isconnectorclicked && ProjectManager.getActProjectVar().showleftdocker) { //Wenn der eigene Wire connector nicht ausgelöst ist aber ein anderer
            if (CheckKollision.checkpointwithobject((int) block.getWireconnector_left().x, (int) block.getWireconnector_left().y, 20, 20, (int) ProgramingSpace.viewport.unproject(temp3.set(Gdx.input.getX(), Gdx.input.getY(), 0)).x, (int) ProgramingSpace.viewport.unproject(temp4.set(Gdx.input.getX(), Gdx.input.getY(), 0)).y)) { //Wenn die Maus über meinen connection offerer fährt...
                if (ProjectManager.getActProjectVar().connetor_offerd_hoverd_block != block) { //Und der zugehörige Block noch nicht in der Variable steht
                    ProjectManager.getActProjectVar().connetor_offerd_hoverd_block = block; //Schreibt sich der Block in die Variable welcher Block beim loslassen als wire nachbar verbunden werden würde
                }
            } else {
                if (ProjectManager.getActProjectVar().connetor_offerd_hoverd_block == block) { //Wenn die Maus nicht mehr über dem Wire connection offerer ist
                    ProjectManager.getActProjectVar().connetor_offerd_hoverd_block = null; //Wird der Block wieder aus der Variable entfernt
                }
            }
        }


        if (CheckKollision.checkpointwithobject((int) block.getwireconnector_right().x, (int) block.getwireconnector_right().y, 20, 20, (int) Var.mousepressedold.x, (int) Var.mousepressedold.y) && Gdx.input.isButtonPressed(0) && ProjectManager.getActProjectVar().moving_wires == null && !IsMousealreadypressed && block.getWire_right() == null) {
            if (!(ProjectManager.getActProjectVar().marked_block == block)) {
                if (!isconnectorclicked && ProjectManager.getActProjectVar().wires_allowed) {

                    //Create new wire

                    tempwire = ProjectManager.getActProjectVar().projectType.getWireGenerator().generate(block);

                    tempwire.setMovebymouse(true);

                    tempwire.setSpace_between_blocks(true);

                    block.setWire_right(tempwire);
                    ProjectManager.getActProjectVar().wires.add(tempwire);
                    ProjectManager.getActProjectVar().moving_wires = tempwire;
                    ProjectManager.getActProjectVar().wire_beginn = block;


                    ProjectManager.getActProjectVar().showleftdocker = true;

                    isconnectorclicked = true;
                    if (block.isMarked()) {


                        block.setMoving(false);
                        ProjectManager.getActProjectVar().ismoving = false;
                        ProjectManager.getActProjectVar().marked_block = null;
                    }

                    IsMousealreadypressed = true;

                }
            }

        }
    }


    private void control_block_errors() {
        try {

            if (ProjectManager.getActProjectVar().moving_wires != null && ProjectManager.getActProjectVar().moving_wires.getLeft_connection() == block && block.getRight() != null) {
                EventVar.blockEventManager.killmovingwires(new BlockKillMovingWiresEvent(this));
            }
            if (block.isMarked()) {
                if (block != ProjectManager.getActProjectVar().marked_block) {
                    block.setMarked(false);

                }

            }

            if (ProjectManager.getActProjectVar().blocks.indexOf(block) == -1) {
                isrunning = false;
                currentThread().interrupt();
                this.time.cancel();
            }
        } catch (Exception e) {

        }
    }


    private void block_moveingengine() {

        if(block.isMarked()) {

            if(Gdx.input.isKeyPressed(Input.Keys.D)&&!openwindow) {

                if(block.getExtendedBlocks()!=null) {

                    Program.logger.config("Open Window");


                }

            }

        }


        if(openwindow&&!Gdx.input.isKeyPressed(Input.Keys.D)) {
            openwindow =false;
        }

        if (CheckKollision.checkmousewithblock(block, Var.mousepressedold) && Gdx.input.isButtonPressed(0) && ProjectManager.getActProjectVar().ismoving == false && !block.isMarked() && ProjectManager.getActProjectVar().marked_block == null) {

            if (!CheckKollision.checkpointwithobject((int) block.getwireconnector_right().x, (int) block.getwireconnector_right().y, 20, 20, (int) Var.mousepressedold.x, (int) Var.mousepressedold.y)) {

                block.setMarked(true);
                ProjectManager.getActProjectVar().marked_block = block;
                ProjectManager.getActProjectVar().diff_save.set(ProgramingSpace.viewport.unproject(temp3.set(Gdx.input.getX(), Gdx.input.getY(), 0)).x - block.getX(), ProgramingSpace.viewport.unproject(temp4.set(Gdx.input.getX(), Gdx.input.getY(), 0)).y - block.getY());

            }
        }


        if (ProjectManager.getActProjectVar().ismoving == false && !block.isMoving() && block.isMarked() && Gdx.input.isButtonPressed(0) && CheckKollision.checkmousewithblock(block)) {


            if (!CheckKollision.checkpointwithobject((int) block.getwireconnector_right().x, (int) block.getwireconnector_right().y, 20, 20, (int) Var.mousepressedold.x, (int) Var.mousepressedold.y)) {


                int feld = 2;
                if (Math.abs(Var.mousepressedold.x - ProgramingSpace.viewport.unproject(temp3.set(Gdx.input.getX(), Gdx.input.getY(), 0)).x) > feld || Math.abs(Var.mousepressedold.y - ProgramingSpace.viewport.unproject(temp4.set(Gdx.input.getX(), Gdx.input.getY(), 0)).y) > feld) {
                    if (block.isMoving() == false && ProjectManager.getActProjectVar().ismoving == false) {
                        ProjectManager.getActProjectVar().diff_save.set(ProgramingSpace.viewport.unproject(temp3.set(Gdx.input.getX(), Gdx.input.getY(), 0)).x - block.getX(), ProgramingSpace.viewport.unproject(temp4.set(Gdx.input.getX(), Gdx.input.getY(), 0)).y - block.getY());


                        block.setMoving(true);
                        ProjectManager.getActProjectVar().ismoving = true;

                    }


                }
            }

        }


        if (block.isMoving() && Gdx.input.isButtonPressed(0)) {


            tempdelete = CheckMouse.isMouseover(UIVar.BlockBarX, UIVar.BlockBarY, UIVar.BlockBarW, UIVar.BlockBarH, false) && block.getBlocktype().canbedeleted();


            if (tempdelete != willbedelete) {
                changewillbedeleted = true;
                willbedelete = tempdelete;
            } else {
                changewillbedeleted = false;
            }

            if (changewillbedeleted) {
                //Wenn der Mauszeiger die Ablagefläche berührt

                if (willbedelete) {
                    Gdx.graphics.setCursor(Gdx.graphics.newCursor(AssetLoader.backcursor, 0, 0));
                    ProjectManager.getActProjectVar().removeblock = true;
                } else {
                    Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
                    ProjectManager.getActProjectVar().removeblock = false;
                }

            }

            //Ändere die Aktuelle Blockposition auf die Maus Position

            if (ProgramGrid.enable && ProgramGrid.block_snapping && ProgramGrid.block_active_snapping) {


                int newx = (int) (((ProgramingSpace.viewport.unproject(temp3.set(Gdx.input.getX(), Gdx.input.getY(), 0)).x - ProjectManager.getActProjectVar().diff_save.x) - UIVar.abstandvonRand) / ProgramGrid.margin);
                int newy = (int) (((ProgramingSpace.viewport.unproject(temp3.set(Gdx.input.getX(), Gdx.input.getY(), 0)).y - ProjectManager.getActProjectVar().diff_save.y) - UIVar.programmflaeche_y) / ProgramGrid.margin);
                block.setX((int) (newx * ProgramGrid.margin) + UIVar.abstandvonRand);
                block.setY((int) (newy * ProgramGrid.margin) + UIVar.programmflaeche_y);


            } else {


                block.setX((int) (ProgramingSpace.viewport.unproject(temp3.set(Gdx.input.getX(), Gdx.input.getY(), 0)).x - ProjectManager.getActProjectVar().diff_save.x));
                block.setY((int) (ProgramingSpace.viewport.unproject(temp3.set(Gdx.input.getX(), Gdx.input.getY(), 0)).y - ProjectManager.getActProjectVar().diff_save.y));
            }

            if (block.getWire_left() != null) {
                if (!block.getWire_left().isSpace_between_blocks()) {
                    if (block.getLeft() != null) {
                        block.getLeft().setRight(null);
                        try {
                            block.getWire_left().getLeft_connection().setWire_right(null); //löschen der Wire die zwischen den Blöcken war
                        } catch (Exception e) {
                            //Falls die Wire schon ein anderer Block gelöscht hat
                        }
                        ProjectManager.getActProjectVar().wires.remove(block.getWire_left());
                        block.setWire_left(null);


                    }
                    block.setLeft(null);

                }
            }

            if (block.getWire_right() != null) {
                if (!block.getWire_right().isSpace_between_blocks()) {
                    if (block.getRight() != null) {
                        block.getRight().setLeft(null);

                        block.getWire_right().getRight_connection().setWire_left(null);//löschen der Wire die zwischen den Blöcken war
                        ProjectManager.getActProjectVar().wires.remove(block.getWire_right());
                        block.setWire_right(null);

                    }
                    block.setRight(null);
                }

            }


        } else if (block.isMoving()) {


            ProjectManager.getActProjectVar().ismoving = false;
            block.setMoving(false);

            if (ProgramGrid.block_snapping && !ProgramGrid.block_active_snapping) {

                int newx = (int) ((block.getX()) / ProgramGrid.margin);
                int newy = (int) ((block.getY()) / ProgramGrid.margin);
                block.setX((int) (newx * ProgramGrid.margin));
                block.setY((int) (newy * ProgramGrid.margin));

            }

            if (willbedelete) {
                deleteonend = true;

            }


        }
    }


    public boolean isIsconnectorclicked() {
        return isconnectorclicked;
    }

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }
}
