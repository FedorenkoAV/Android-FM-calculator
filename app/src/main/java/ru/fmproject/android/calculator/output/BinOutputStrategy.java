package ru.fmproject.android.calculator.output;


import android.content.Context;
import android.widget.TextView;

import ru.fmproject.android.calculator.ArgXParent;
import ru.fmproject.android.calculator.L;
import ru.fmproject.android.calculator.Preferences;

public class BinOutputStrategy extends AbstractOutputStrategy {

    private static final String TAG = "BinOutputStrategy";

    public BinOutputStrategy(Context context, TextView mainDisplay, Preferences preferences) {
        super(context, mainDisplay, preferences);
        L.d(TAG, "BinOutputStrategy создан");
    }

    @Override
    protected StringBuilder getStringBuilder(ArgXParent argX) {
        StringBuilder mainDisplayTmp = new StringBuilder();
        if (argX.getNumber().length() == 0) { //Если нет числа, то выводим 0
            mainDisplayTmp.append("0");
            L.d(TAG, "Нет числа, добавили ноль.");
            L.d(TAG, mainDisplayTmp.toString());
        } else {
            StringBuilder number = argX.getNumber();
            long aLong = Long.parseLong(number.toString(), 2);
            if (argX.isSign()) {
                aLong = -aLong;
            }
            mainDisplayTmp.append(Long.toBinaryString(aLong));
        }
        return mainDisplayTmp;
    }
}
