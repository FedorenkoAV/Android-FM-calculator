package ru.fmproject.android.calculator.input;

import ru.fmproject.android.calculator.editors.EditXBin;

public class BinInputStrategy extends AbstractInputStrategy {

    private final EditXBin editXBin;

    public BinInputStrategy(EditXBin editXBin) {
        super(editXBin);
        this.editXBin = editXBin;
    }
}
