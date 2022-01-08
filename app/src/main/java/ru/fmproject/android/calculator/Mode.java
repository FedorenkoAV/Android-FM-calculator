package ru.fmproject.android.calculator;

/**
 * Created by User on 11.06.2017.
 */


public class Mode {

    public final static int NORMAL = 0;
    public final static int SD = 1;
    public final static int COMPLEX = 2;
    public final static int DEC = 0;
    public final static int BIN = 3;
    public final static int OCT = 4;
    public final static int HEX = 5;

    private boolean bin = false;
    private boolean oct = false;
    private boolean hex = false;
    private boolean dec = true;

    private int mode;
    private StatusDisplay statusDisplay;

    public Mode(StatusDisplay statusDisplay) {
        this.statusDisplay = statusDisplay;
        mode = NORMAL;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
        statusDisplay.offCplx();
        statusDisplay.offSd();
        statusDisplay.offBin();
        statusDisplay.offHex();
        statusDisplay.offOct();
        switch (this.mode) {
            case (COMPLEX):
                statusDisplay.onCplx();
                break;
            case (SD):
                statusDisplay.onSd();
                break;
            case (DEC):
                break;
            case (BIN):
                statusDisplay.onBin();
                break;
            case (OCT):
                statusDisplay.onOct();
                break;
            case (HEX):
                statusDisplay.onHex();
                break;
        }
    }

    public void switchSD() {
        if (mode != SD) {
            setMode(SD);
        } else {
            setMode(NORMAL);
        }
    }

    public void switchCplx() {
        if (mode != COMPLEX) {
           setMode(COMPLEX);
        } else {
            setMode(NORMAL);
        }
    }

    public void setDEC() {
        setMode(DEC);
    }

    public void setBIN() {
        setMode(BIN);
    }

    public void setOCT() {
        setMode(OCT);
    }

    public void setHEX() {
        setMode(HEX);
    }
}
