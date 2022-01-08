package ru.fmproject.android.calculator.input;

import ru.fmproject.android.calculator.MyExceptions;
import ru.fmproject.android.calculator.editors.EditXDec;

public class HypDecInputStrategy extends AbstractHypInputStrategy{

    private final EditXDec editXDec;

    public HypDecInputStrategy(EditXDec editXDec) {
        super(editXDec);
        this.editXDec = editXDec;
    }

    @Override
    public void buttonSin() throws MyExceptions {
        //sinh
        editXDec.sinh();
    }

    @Override
    public void buttonCos() throws MyExceptions {
        // cosh
        editXDec.cosh();
    }

    @Override
    public void buttonTan() throws MyExceptions {
        // tanh
        editXDec.tanh();
    }
}
