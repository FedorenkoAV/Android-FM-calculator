package ru.fmproject.android.calculator.input;

import ru.fmproject.android.calculator.editors.EditXOct;

public class ShiftOctInputStrategy extends AbstractShiftInputStrategy {

    private final EditXOct editXOct;

    public ShiftOctInputStrategy(EditXOct editXOct) {
        super(editXOct);
        this.editXOct = editXOct;
    }
}
