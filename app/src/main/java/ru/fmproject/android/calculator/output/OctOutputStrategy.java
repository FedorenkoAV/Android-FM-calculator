package ru.fmproject.android.calculator.output;

import android.content.Context;
import android.widget.TextView;

import ru.fmproject.android.calculator.ArgXParent;
import ru.fmproject.android.calculator.L;
import ru.fmproject.android.calculator.Preferences;

public class OctOutputStrategy extends AbstractOutputStrategy{

    private static final String TAG = "OctOutputStrategy";

    public OctOutputStrategy(Context context, TextView mainDisplay, Preferences preferences) {
        super(context, mainDisplay, preferences);
        L.d(TAG, "OctOutputStrategy создан");
    }

    @Override
    public void printString(ArgXParent argX) {
        L.d(TAG, "Начинаем собирать Hex строку для отображения на экране.");
        L.d(TAG, "В ArgXHex строка: " + argX.getNumber());
        L.d(TAG, "Знак: " + argX.isSign());
        L.d(TAG, "Редактируемое: " + argX.isEditable());
        L.d(TAG, "Пустое: " + argX.isVirginity());

        StringBuilder mainDisplayTmp;
        mainDisplayTmp = getStringBuilder(argX);

        mainDisplayTmp.append(".");// Всегда добавляем точку в конец
        L.d(TAG, "Добавили точку в конец.");
        L.d(TAG, mainDisplayTmp.toString());
        L.d(TAG, "В результате на дисплей выводим: " + mainDisplayTmp.toString());
        setTextSize(mainDisplayTmp.length());
        setMainDisplayText(mainDisplayTmp.toString());
    }

    @Override
    protected StringBuilder getStringBuilder(ArgXParent argX) {
        StringBuilder mainDisplayTmp = new StringBuilder();
        if (argX.getNumber().length() == 0) { //Если нет числа, то выводим 0
            mainDisplayTmp.append("0");
            L.d(TAG, "Нет числа, добавили ноль.");
            L.d(TAG, mainDisplayTmp.toString());
        } else {
            StringBuilder hexNumber = argX.getNumber();
            long aLong = Long.parseLong(hexNumber.toString(), 8);
            if (argX.isSign()) {
                aLong = -aLong;
            }
            mainDisplayTmp.append(Long.toHexString(aLong));
        }
        return mainDisplayTmp;
    }
}
