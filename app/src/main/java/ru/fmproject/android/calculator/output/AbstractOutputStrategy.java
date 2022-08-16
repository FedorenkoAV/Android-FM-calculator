package ru.fmproject.android.calculator.output;

import static android.util.TypedValue.COMPLEX_UNIT_DIP;

import android.content.Context;
import android.content.res.Resources;
import android.widget.TextView;

import ru.fmproject.android.calculator.ArgXParent;
import ru.fmproject.android.calculator.L;
import ru.fmproject.android.calculator.Preferences;
import ru.fmproject.android.calculator.R;

public abstract class AbstractOutputStrategy implements OutputStrategy{

    private static final String TAG = "AbstractOutputStrategy";
    private final TextView mainDisplay;
    private final Preferences preferences;

    private final int S_TEXT_SIZE;
    private final int M_TEXT_SIZE;
    private final int L_TEXT_SIZE;

    private final int M_TEXT_LENGTH;
    private final int L_TEXT_LENGTH;

    private float mainTextSize;

    public AbstractOutputStrategy(Context context, TextView mainDisplay, Preferences preferences) {
        Resources res = context.getResources();
        this.preferences = preferences;
        S_TEXT_SIZE = res.getInteger(R.integer.s_text_size);
        M_TEXT_SIZE = res.getInteger(R.integer.m_text_size);
        L_TEXT_SIZE = res.getInteger(R.integer.l_text_size);

        M_TEXT_LENGTH = res.getInteger(R.integer.m_text_length);
        L_TEXT_LENGTH = res.getInteger(R.integer.l_text_length);
        this.mainDisplay = mainDisplay;
        mainTextSize = mainDisplay.getTextSize();
        L.d(TAG, "mainTextSize = " + mainTextSize);
    }

    protected void setTextSize (int textLength) {
        if (textLength <= L_TEXT_LENGTH) {
            mainDisplay.setTextSize(COMPLEX_UNIT_DIP, L_TEXT_SIZE);
            return;
        }
        if (textLength > L_TEXT_LENGTH  && textLength <= M_TEXT_LENGTH) {
            mainDisplay.setTextSize(COMPLEX_UNIT_DIP, M_TEXT_SIZE);
            return;
        }
        if (textLength > M_TEXT_LENGTH) {
            mainDisplay.setTextSize(COMPLEX_UNIT_DIP, S_TEXT_SIZE);
        }
    }

    protected void setMainDisplayText(String mainDisplayText) {
        mainDisplay.setText(mainDisplayText);
    }

    @Override
    public void printString(ArgXParent argXParent) {
        ArgXParent argX = argXParent;
        L.d(TAG, "Начинаем собирать Bin строку для отображения на экране.");
        L.d(TAG, "В ArgXBin строка: " + argX.getNumber());
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

    public Preferences getPreferences() {
        return preferences;
    }

    protected abstract StringBuilder getStringBuilder(ArgXParent argX);
}















