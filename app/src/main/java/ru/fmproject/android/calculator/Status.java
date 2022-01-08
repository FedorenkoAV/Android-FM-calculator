package ru.fmproject.android.calculator;

import android.widget.Button;

/**
 * Created by User on 06.06.2017.
 */

public class Status {

    private boolean shift = false;
    private boolean hyp = false;
    private boolean bracket = false;
    private StatusDisplay statusDisplay;
    Button[] btnStore;


    public Status(StatusDisplay statusDisplay, Button[] btnStore) {
        this.statusDisplay = statusDisplay;
        this.btnStore = btnStore;
    }

    public boolean isShift() {
        return shift;
    }

    public void switchShift() {
        shift = !shift;
        if (shift) {
            statusDisplay.onShift();
            btnStore[28].setText("MC");
        } else {
            statusDisplay.offShift();
            btnStore[28].setText("MR");
        }
    }

    public void onShift() {
        shift = true;
        statusDisplay.onShift();
        btnStore[28].setText("MC");
    }

    public void offShift() {
        shift = false;
        statusDisplay.offShift();
        btnStore[28].setText("MR");
    }

    public boolean isHyp() {
        return hyp;
    }

    public void switchHyp() {
        hyp = !hyp;
        if (hyp) {
            statusDisplay.onHyp();
        } else {
            statusDisplay.offHyp();
        }
    }

    public void onHyp() {
        hyp = true;
        statusDisplay.onHyp();
    }

    public void offHyp() {
        hyp = false;
        statusDisplay.offHyp();
    }


    public boolean isBracket() {
        return bracket;
    }

    public void setBracket(boolean bracket) {
        this.bracket = bracket;
        if (bracket) {
            statusDisplay.onBracket();
        } else {
            statusDisplay.offBracket();
        }
    }

    public void onMemory() {
        statusDisplay.onMemory();
    }

    public void offMemory() {
        statusDisplay.offMemory();
    }

    public void onError() {
        statusDisplay.onError();
        for (Button btn : btnStore) {
            btn.setEnabled(false);
        }
        btnStore[4].setEnabled(true);
        btnStore[5].setEnabled(true);
    }

    public void offError() {
        statusDisplay.offError();
        for (Button btn : btnStore) {
            btn.setEnabled(true);
        }
    }
}
