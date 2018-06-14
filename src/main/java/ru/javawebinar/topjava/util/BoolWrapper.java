package ru.javawebinar.topjava.util;

public class BoolWrapper {
    private boolean state;

    public BoolWrapper() {
        state = Boolean.FALSE;
    }

    public boolean getState(){
        return state;
    }

    public void setTrue(){
        state = Boolean.TRUE;
    }
    public void setFalse(){
        state = Boolean.FALSE;
    }
}
