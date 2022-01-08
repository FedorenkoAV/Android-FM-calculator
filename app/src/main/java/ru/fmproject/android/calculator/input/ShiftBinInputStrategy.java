package ru.fmproject.android.calculator.input;

import ru.fmproject.android.calculator.editors.EditXBin;

public class ShiftBinInputStrategy extends AbstractInputStrategy {

    private final EditXBin editXBin;

    public ShiftBinInputStrategy(EditXBin editXBin) {
        super(editXBin);
        this.editXBin = editXBin;
    }
}
