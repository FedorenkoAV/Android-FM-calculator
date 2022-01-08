package ru.fmproject.android.calculator.input;

import ru.fmproject.android.calculator.MyExceptions;
import ru.fmproject.android.calculator.editors.EditXDec;

public class ShiftDecInputStrategy extends AbstractShiftInputStrategy {

    private final EditXDec editXDec;

    public ShiftDecInputStrategy(EditXDec editXDec) {
        super(editXDec);
        this.editXDec = editXDec;
    }

    @Override
    public void buttonSci() {
        // FIX
        editXDec.setFixMode();
    }

    @Override
    public void buttonDrg() {
        // translate angle
        editXDec.changeAngleUnit();
    }

    @Override
    public void buttonSin() throws MyExceptions {
        // asin
        editXDec.asin();
    }

    @Override
    public void buttonCos() throws MyExceptions {
        // acos
        editXDec.acos();
    }

    @Override
    public void buttonTan() throws MyExceptions {
        // atan
        editXDec.atan();
    }

    @Override
    public void buttonExp() {
        //  pi
        editXDec.pi();
    }

    @Override
    public void buttonXpowY() throws MyExceptions {
        // x_sqr_y
        editXDec.x_sqr_y();
    }

    @Override
    public void buttonSqr() {
        // cbrt
        editXDec.cbrt();
    }

    @Override
    public void buttonToRad() {
        // fromDeg
        editXDec.fromDeg();
    }

    @Override
    public void buttonLn() throws MyExceptions {
        // e pow x
        editXDec.exponent();
    }

    @Override
    public void buttonLog() {
        // 10^x
        editXDec._10x();
    }

    @Override
    public void buttonX2() throws MyExceptions {
        // 1 div x
        editXDec._1_div_x();
    }

    @Override
    public void buttonA() {
        // R to P
        editXDec.r_to_p();
    }

    @Override
    public void buttonB() {
        // P to R
        editXDec.p_to_r();
    }

    @Override
    public void button01() {
        // dbm to w
        editXDec.dbm_to_w();
    }

    @Override
    public void button02() {
        // w to dbm
        editXDec.w_to_dbm();
    }

    @Override
    public void buttonDot() {
        // rnd
        editXDec.random();
    }

    @Override
    public void buttonCalc() throws MyExceptions {
        // %
        editXDec.percent();
    }

    @Override
    public void buttonCE() throws MyExceptions {
        //x!
        editXDec.factorial();
    }
}
