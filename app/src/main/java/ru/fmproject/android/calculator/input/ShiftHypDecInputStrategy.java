package ru.fmproject.android.calculator.input;

import ru.fmproject.android.calculator.MyExceptions;
import ru.fmproject.android.calculator.editors.EditXDec;

public class ShiftHypDecInputStrategy extends AbstractShiftHypInputStrategy {

    private final EditXDec editXDec;

    public ShiftHypDecInputStrategy(EditXDec editXDec) {
        super(editXDec);
        this.editXDec = editXDec;
    }

    @Override
    public void buttonSin() throws MyExceptions {
        // Гиперболический арксинус
        editXDec.arsh();
    }

    @Override
    public void buttonCos() throws MyExceptions {
        // Гиперболический арккосинус
        editXDec.arch();
    }

    @Override
    public void buttonTan() throws MyExceptions {
        // Гиперболический арктангенс
        editXDec.arth();
    }
}
