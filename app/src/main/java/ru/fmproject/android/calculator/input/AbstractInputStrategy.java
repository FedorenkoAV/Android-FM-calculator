package ru.fmproject.android.calculator.input;

import ru.fmproject.android.calculator.editors.EditX;
import ru.fmproject.android.calculator.MyExceptions;

public abstract class AbstractInputStrategy implements InputStrategy{

    private final EditX editX;

    public AbstractInputStrategy(EditX editX) {
        this.editX = editX;
    }

    @Override
    public void buttonDel() {
        editX.del();
    }

    @Override
    public void buttonSci() {

    }

    @Override
    public void buttonDrg() {

    }

    @Override
    public void buttonSin() {

    }

    @Override
    public void buttonCos() {

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
    public void buttonLn() {

    }

    @Override
    public void buttonLog() {

    }

    @Override
    public void buttonX2() {

    }

    @Override
    public void buttonA() {

    }

    @Override
    public void buttonB() {

    }

    @Override
    public void buttonOpenBracket() throws MyExceptions {

    }

    @Override
    public void buttonCloseBracket() throws MyExceptions {

    }

    @Override
    public void buttonXtoM() {
        // X to memoryStore
        editX.x_to_m();
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
        //  M+
        editX.memoryPlus();
    }

    @Override
    public void buttonMR() {
        //  MR
        editX.readMemory();
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
    public void buttonMult() throws MyExceptions {
        //  Mult
        editX.mult();
    }

    @Override
    public void buttonDiv() throws MyExceptions {
        //  Div
        editX.div();
    }

    @Override
    public void button01() {
        // 1
        editX.add('1');
    }

    @Override
    public void button02() {

    }

    @Override
    public void button03() {

    }

    @Override
    public void buttonPlus() throws MyExceptions {
        //  Plus
        editX.plus();
    }

    @Override
    public void buttonMinus() throws MyExceptions {
        //Minus
        editX.minus();
    }

    @Override
    public void button00() {
        // 0
        editX.add('0');
    }

    @Override
    public void buttonDot() {

    }

    @Override
    public void buttonSign() {
        // +/-
        editX.sign();
    }

    @Override
    public void buttonCalc() throws MyExceptions {
        // =
        editX.calc();
    }

    @Override
    public void buttonCE() {
        // CE
        editX.ce();
    }
}
