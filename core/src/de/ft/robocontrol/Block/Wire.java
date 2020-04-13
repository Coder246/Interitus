package de.ft.robocontrol.Block;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import de.ft.robocontrol.ProgrammingSpace;
import de.ft.robocontrol.loading.AssetLoader;

public class Wire {
    private VisibleObjects left_connection;
    private VisibleObjects right_connection;

    private boolean space_between_blocks = false;
    private boolean movebymouse = false;
    private Vector3 tempvector = new Vector3();


    public Wire(Block left_connection,Block right_connection) {
        this.left_connection = left_connection;
        this.right_connection = right_connection;
    }

    public Wire(Block left_connection) {
        this.left_connection = left_connection;
    }

    public void draw() {



        if(!space_between_blocks) {

            if(movebymouse) {

            if(Gdx.input.isButtonJustPressed(0)) {



            }





                boolean temp = false;
                if(!ProgrammingSpace.batch.isDrawing()) {
                    ProgrammingSpace.batch.begin();
                    temp = true;
                }

                Sprite sprite = new Sprite(AssetLoader.wire);



float a = left_connection.getX_exit() - ProgrammingSpace.cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(),0)).x;
float b = left_connection.getY_exit() - ProgrammingSpace.cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(),0)).y;

                sprite.setPosition(left_connection.getX_exit(),left_connection.getY_exit());


                double weite = Math.sqrt(a * a + b * b);

                //sprite.setOrigin(left_connection.getX_exit(),left_connection.getY_exit());

               // sprite.setOrigin(left_connection.getX_exit(),left_connection.getY_exit());


                if(ProgrammingSpace.cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(),0)).x-left_connection.getX_exit()>0) {
                    sprite.setRotation((float) ((float) Math.atan((float) ((ProgrammingSpace.cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)).y - left_connection.getY_exit()) / (ProgrammingSpace.cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)).x - left_connection.getX_exit()))) * 180 / Math.PI));
                }else{
                    sprite.setRotation((float) ((float) Math.atan((float) ((ProgrammingSpace.cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)).y - left_connection.getY_exit()) / (ProgrammingSpace.cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)).x - left_connection.getX_exit()))) * 180 / Math.PI)+180);

                }
               ProgrammingSpace.batch.draw(AssetLoader.switch_background_white,873,575,5,5);



             //   System.out.println((float) ((float) Math.acos((float) ((ProgrammingSpace.cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(),0)).x-left_connection.getX_exit())/weite))*180/Math.PI));
              sprite.setSize((float) weite,5);
              sprite.setOrigin(0,0);
             //   sprite.setSize(50,50);
              //  sprite.setRotation(20);
               // sprite.setRotation();



               // System.out.println("X: "+left_connection.getX_exit()+" Y: "+left_connection.getY_exit()+" Mouse: X:" + ProgrammingSpace.cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(),0)).x+" Y:"+ProgrammingSpace.cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(),0)).y+" Origion X:"+sprite.getOriginX()+" Y:"+sprite.getOriginY()+"   Rotation "+sprite.getRotation());


                sprite.draw(ProgrammingSpace.batch);


                //ProgrammingSpace.batch.draw(sprite,left_connection.getwireconnector_right().x,left_connection.getwireconnector_right().y,(float) weite,10);
                //ProgrammingSpace.batch.draw(sprite,left_connection.getwireconnector_right().x,left_connection.getwireconnector_right().y,);


                if(temp) {
                    ProgrammingSpace.batch.end();
                }




            }else{

            }


        }


    }


    public boolean isSpace_between_blocks() {
        return space_between_blocks;
    }


    public Block getLeft_connection() {
        return left_connection.getblock();
    }

    public Block getRight_connection() {
        return right_connection.getblock();
    }

    public void setSpace_between_blocks(boolean space_between_blocks) {
        this.space_between_blocks = space_between_blocks;
    }

    public boolean isMovebymouse() {
        return movebymouse;
    }

    public void setMovebymouse(boolean movebymouse) {
        this.movebymouse = movebymouse;
    }

    public boolean isvisible() {

        if(movebymouse)  {
            return true;
        }

        try {
            if(left_connection.isVisible()) {
                return true;
            }

        }catch (Exception e) {

        }

        try {
            if (right_connection.isVisible()) {
                return true;
            }
        }catch (Exception e) {

        }

        return false;




    }


}