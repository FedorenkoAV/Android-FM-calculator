package ru.fmproject.android.calculator.editors;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class NewEditor {

    StringBuilder argument = new StringBuilder();

    public void add(char pressedKey) {
        argument.append(pressedKey);
    }

    public void del() {

    }

    public void ce() {
        argument = new StringBuilder();
    }

    void sign() {

    }

    public void exp() {
        if(argument.length() == 0) {
            argument.append('1');
        }
        argument.append('E');
        new BigDecimal("123.456E5").toEngineeringString(); // "12.3456E+6"
        new BigDecimal("123.456E78").toPlainString(); // "123456000000000000000000000000000000000000000000000000000000000000000000000000000"
        System.out.println(new BigDecimal("123.4", new MathContext(4, RoundingMode.HALF_UP)));
    }
}
