package ru.fmproject.android.calculator.input;

import ru.fmproject.android.calculator.editors.EditX;
import ru.fmproject.android.calculator.MyExceptions;

public abstract class AbstractShiftInputStrategy implements InputStrategy {

    private final EditX editX;

    public AbstractShiftInputStrategy(EditX editX) {
        this.editX = editX;
    }

    @Override
    public void buttonDel() {

    }

    @Override
    public void buttonSci() {

    }

    @Override
    public void buttonDrg() {

    }

    @Override
    public void buttonSin() throws MyExceptions {

    }

    @Override
    public void buttonCos() throws MyExceptions {

    }

    @Override
    public void buttonTan() throws MyExceptions {

    }

    @Override
    public void buttonExp() {

    }

    @Override
    public void buttonXpowY() throws MyExceptions {

    }

    @Override
    public void buttonSqr() {

    }

    @Override
    public void buttonToRad() {

    }

    @Override
    public void buttonLn() throws MyExceptions {

    }

    @Override
    public void buttonLog() {

    }

    @Override
    public void buttonX2() throws MyExceptions {

    }

    @Override
    public void buttonA() {

    }

    @Override
    public void buttonB() {

    }

    @Override
    public void buttonOpenBracket() throws MyExceptions {
        editX.x_to_y();
    }

    @Override
    public void buttonMR() {
        // MC
        editX.clearMemory();
    }

    @Override
    public void buttonMult() throws MyExceptions {
        //  OCT
        editX.toOct();
    }

    @Override
    public void buttonDiv() throws MyExceptions {
        //  BIN
        editX.toBin();
    }

    @Override
    public void buttonPlus() throws MyExceptions {
        //  DEC
        editX.toDec();
    }

    @Override
    public void buttonCloseBracket() throws MyExceptions {

    }

    @Override
    public void buttonXtoM() {

    }

    @Override
    public void button07() {

    }

    @Override
    public void button08() {

    }

    @Override
    public void button09() {

    }

    @Override
    public void buttonMem() throws MyExceptions {

    }

    @Override
    public void button04() {

    }

    @Override
    public void button05() {

    }

    @Override
    public void button06() {

    }

    @Override
    public void button01() {

    }

    @Override
    public void button02() {

    }

    @Override
    public void button03() {

    }

    @Override
    public void buttonMinus() throws MyExceptions {
        //  HEX
        editX.toHex();
    }

    @Override
    public void button00() {

    }

    @Override
    public void buttonDot() {

    }

    @Override
    public void buttonSign() {

    }

    @Override
    public void buttonCalc() throws MyExceptions {

    }

    @Override
    public void buttonCE() throws MyExceptions {

    }
}
