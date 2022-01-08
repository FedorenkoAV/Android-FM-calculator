package ru.fmproject.android.calculator.input;

import ru.fmproject.android.calculator.editors.EditXHex;

public class ShiftHexInputStrategy extends AbstractShiftInputStrategy {

    private final EditXHex editXHex;

    public ShiftHexInputStrategy(EditXHex editXHex) {
        super(editXHex);
        this.editXHex = editXHex;
    }
}
