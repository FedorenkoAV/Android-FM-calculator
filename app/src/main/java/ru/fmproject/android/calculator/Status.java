package ru.fmproject.android.calculator;

import android.widget.Button;

/**
 * Created by User on 06.06.2017.
 */

class Status {

    private boolean shift = false;
    private boolean hyp = false;
    private boolean bracket = false;
    private StatusDisplay statusDisplay;
    Button[] btnStore;


    Status(StatusDisplay statusDisplay, Button[] btnStore) {
        this.statusDisplay = statusDisplay;
        this.btnStore = btnStore;
    }

    boolean isShift() {
        return shift;
    }

    void switchShift() {
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

    void offShift() {
        shift = false;
        statusDisplay.offShift();
        btnStore[28].setText("MR");
    }

    boolean isHyp() {
        return hyp;
    }

    void switchHyp() {
        hyp = !hyp;
        if (hyp) {
            statusDisplay.onHyp();
        } else {
            statusDisplay.offHyp();
        }
    }

    void onHyp() {
        hyp = true;
        statusDisplay.onHyp();
    }

    void offHyp() {
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

    void onMemory() {
        statusDisplay.onMemory();
    }

    void offMemory() {
        statusDisplay.offMemory();
    }

    void onError() {
        statusDisplay.onError();
        for (Button btn : btnStore) {
            btn.setEnabled(false);
        }
        btnStore[4].setEnabled(true);
        btnStore[5].setEnabled(true);
    }

    void offError() {
        statusDisplay.offError();
        for (Button btn : btnStore) {
            btn.setEnabled(true);
        }
    }
}
