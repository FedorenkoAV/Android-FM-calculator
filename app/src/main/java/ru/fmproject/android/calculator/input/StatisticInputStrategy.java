package ru.fmproject.android.calculator.input;

import ru.fmproject.android.calculator.editors.EditXDec;
import ru.fmproject.android.calculator.MyExceptions;

public class StatisticInputStrategy extends DecInputStrategy {

    private final EditXDec editXDec;

    public StatisticInputStrategy(EditXDec editXDec) {
        super(editXDec);
        this.editXDec = editXDec;
    }

    @Override
    public void buttonCloseBracket() throws MyExceptions {
        //stackSize
        editXDec.stackSize();
    }

    @Override
    public void buttonXtoM() {
        // average
        editXDec.average();
    }

    @Override
    public void buttonMem() throws MyExceptions {
        //M+
        editXDec.putDataToStack();
    }

    @Override
    public void buttonMR() {
        //sampleStandartDeviation
        editXDec.sampleStandartDeviation();
    }
}
