package ru.fmproject.android.calculator.input;

import ru.fmproject.android.calculator.MyExceptions;
import ru.fmproject.android.calculator.editors.EditXDec;

public class ShiftStatisticInputStrategy extends ShiftDecInputStrategy {

    private final EditXDec editXDec;

    public ShiftStatisticInputStrategy(EditXDec editXDec) {
        super(editXDec);
        this.editXDec = editXDec;
    }

    @Override
    public void buttonCloseBracket() throws MyExceptions {
        // total
        editXDec.total();
    }

    @Override
    public void buttonXtoM() {
        //totalSquare
        editXDec.totalSquare();
    }

    @Override
    public void buttonMem() throws MyExceptions {
        // deleteDataFromStack
        editXDec.deleteDataFromStack();
    }

    @Override
    public void buttonMR() {
        // populationStandartDeviation
        editXDec.populationStandartDeviation();
    }
}
