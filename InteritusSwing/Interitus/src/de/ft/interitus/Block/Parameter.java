/*
 * Copyright (c) 2020.
 * Copyright by Tim and Felix
 */

package de.ft.interitus.Block;



import de.ft.interitus.utils.ArrayList;

import java.awt.image.BufferedImage;

public class Parameter {
    private String varName;
    private final String Unit;
    private Object Parameter;
    private BufferedImage ParameterTexture;
    private String ParameterName;
    private String ParameterDescription;
    private Block block;
    private ParameterType parameterType;
    private ArrayList<DataWire> Datawire = new ArrayList<>();
    private int x =0;
    private int y=0;
    private final boolean varname;

    public Parameter(Object parameter, BufferedImage ParameterTexture, String ParameterName, String ParameterDescription, String Unit, ParameterType parameterType, boolean varname) {
        this.ParameterTexture = ParameterTexture;
        this.Parameter = parameter;
        this.ParameterName = ParameterName;
        this.ParameterDescription = ParameterDescription;
        this.Unit = Unit;

        this.parameterType = parameterType;
        this.varname = varname;


    }

    public Object getParameter() {
        if(!varname) {
            return Parameter;
        }
        if(getDatawire().size()>0) {
            return Datawire.get(0).getParam_input().getVarName();
        }else {
            return Parameter;
        }
    }

    public void setParameter(Object parameter) {
        Parameter = parameter;
    }

    public String getParameterDescription() {
        return ParameterDescription;
    }

    public void setParameterDescription(String parameterDescription) {
        ParameterDescription = parameterDescription;
    }

    public String getParameterName() {
        return ParameterName;
    }

    public void setParameterName(String parameterName) {
        ParameterName = parameterName;
    }

    public BufferedImage getParameterTexture() {
        return ParameterTexture;
    }

    public void setParameterTexture(BufferedImage parameterTexture) {
        ParameterTexture = parameterTexture;
    }

    public String getUnit() {
        return Unit;
    }

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    public ParameterType getParameterType() {
        return parameterType;
    }

    public void setParameterType(ParameterType parameterType) {
        this.parameterType = parameterType;
    }



    public ArrayList<DataWire> getDatawire() {
        return Datawire;
    }

    protected void setDatawire(ArrayList<DataWire> datawire) {
        Datawire = datawire;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }



    @Override
    public String toString() {
        if(!varname) {
            return Parameter.toString();
        }
        if(getDatawire().size()>0) {
            return Datawire.get(0).getParam_input().getVarName();
        }else {
            return Parameter.toString();
        }
    }

    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName = varName;
    }





}