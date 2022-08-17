package ru.fmproject.android.calculator.output;

import static android.util.TypedValue.COMPLEX_UNIT_DIP;

import android.content.Context;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import ru.fmproject.android.calculator.ArgX;
import ru.fmproject.android.calculator.ArgXParent;
import ru.fmproject.android.calculator.L;
import ru.fmproject.android.calculator.Preferences;

public class DecOutputStrategy extends AbstractOutputStrategy{

    private static final String TAG = "DecOutputStrategy";

    private boolean fixMode;
    private boolean withZeros;
    private boolean sciMode;
    private final NumberFormat normForm;
    private final NumberFormat sciForm;
    public int exponentLength; // Эту переменную нужно перенести в файл настроек
    public int numberLength;// Эту переменную нужно перенести в файл настроек

    public DecOutputStrategy(Context context, TextView mainDisplay, Preferences preferences) {
        super(context, mainDisplay, preferences);
        fixMode = false;
        withZeros = false;
        setFixModeScale(getFixModeScale());
        exponentLength = preferences.getExpLength();
        sciMode = false;
        normForm = new DecimalFormat("##############.##############");
        switch (exponentLength) {
            case 1:
                sciForm = new DecimalFormat("0.##############E0");
                break;
            case 3:
                sciForm = new DecimalFormat("0.##############E000");
                break;
            default:
                sciForm = new DecimalFormat("0.##############E00");
                break;
        }
        numberLength = preferences.getNumLength();
        L.d(TAG, "DecOutputStrategy создан");
    }

    public void setFixModeScale(int fixModeScale) {
        getPreferences().setFixMode(fixModeScale);
        L.d(TAG, "Занесли значение режима округления в настройки: " + fixModeScale);
        setFixModeFlags(fixModeScale);
    }

    public int getFixModeScale() {
        int fixModeScale = getPreferences().getFixMode();
        L.d(TAG, "Получаем значение режима округления из настроек: " + fixModeScale);
        setFixModeFlags(fixModeScale);
        return fixModeScale;
    }

    private void setFixModeFlags(int fixModeScale) {
        fixMode = true;
        withZeros = true;
        if (fixModeScale == -1) {
            fixMode = false;
            withZeros = false;
        }
    }

    public void switchSciMode() {
        sciMode = !sciMode;
    }

    public void offSciMode() {
        sciMode = false;
    }

    @Override
    public void printString(ArgXParent argXOriginal) {
        L.d(TAG, "Начинаем собирать строку для отображения на экране.");
        if (argXOriginal.isVirginity()) {
            printVirginArgX((ArgX) argXOriginal);
            return;
        }
        if (argXOriginal.isEditable()) {
            printEditableArgX((ArgX)argXOriginal);
            return;
        }
        printFinalArgX((ArgX)argXOriginal);
    }

    private void printFinalArgX(ArgX argXOriginal) {
        L.d(TAG, "Начинаем собирать нередактируемую строку для отображения на экране.");
        ArgX argX = createArgX(argXOriginal);
        StringBuilder mainDisplayTmp = new StringBuilder(getMantissaIntegerPart(argX));
        if (argX.isMantissaFractionalPart() || fixMode) { // Если есть дробная часть мантиссы, то добавляем ее после точки, если включен режим FIX, то дополняем нулями
            L.d(TAG, "Есть дробная часть мантиссы.");
            if (argX.getMantissaFractionalPartInDouble() != 0.0 || fixMode) {// и если нет дробной части мантиссы то ничего не добавляем
                int scale = numberLength - argX.getMantissaIntegerPart().length();
                if (argX.isExponent()) {
                    scale = scale - exponentLength;
                }
                withZeros = false;
                if (fixMode) {
                    scale = getFixModeScale();
                    withZeros = true;
                }
                mainDisplayTmp.append(argX.getRoundedMantissaFracPart(scale, withZeros));//Добавляем округленную мантиссу
            }
            L.d(TAG, "Добавили дробную часть мантиссы.");
            L.d(TAG, mainDisplayTmp.toString());
        }
        mainDisplayTmp.append(getExponent(argX));
        L.d(TAG, "В результате на дисплей выводим: " + mainDisplayTmp.toString());
        setTextSize(mainDisplayTmp.length());
        setMainDisplayText(mainDisplayTmp.toString());
    }

    private void printEditableArgX(ArgX argX) {
        L.d(TAG, "Начинаем собирать редактируемую строку для отображения на экране.");
        StringBuilder mainDisplayTmp = new StringBuilder(getMantissaIntegerPart(argX));
        if (argX.isMantissaFractionalPart()) { // Если есть дробная часть мантиссы, то добавляем ее после точки
            L.d(TAG, "Есть дробная часть мантиссы.");
            //Добавляем дробную часть мантиссы
            mainDisplayTmp.append(argX.getMantissaFractionalPart());
        }
        mainDisplayTmp.append(getExponent(argX));
        L.d(TAG, "В результате на дисплей выводим: " + mainDisplayTmp.toString());
        setTextSize(mainDisplayTmp.length());
        setMainDisplayText(mainDisplayTmp.toString());
    }

    private void printVirginArgX(ArgX argXOriginal) {
        L.d(TAG, "Начинаем собирать пустую строку для отображения на экране.");
        ArgX argX = createArgX(argXOriginal);
        StringBuilder mainDisplayTmp = new StringBuilder(getMantissaIntegerPart(argX));
        //если включен режим FIX, то дополняем нулями
        if (fixMode) {
            mainDisplayTmp.append(argX.getRoundedMantissaFracPart(getFixModeScale(), withZeros));//Добавляем округленную мантиссу
            L.d(TAG, "Включен режим FIX, поэтому добавляем нули в дробную часть.");
            L.d(TAG, mainDisplayTmp.toString());
        }
        mainDisplayTmp.append(getExponent(argX));
        L.d(TAG, "В результате на дисплей выводим: " + mainDisplayTmp.toString());
        setTextSize(0);
        setMainDisplayText(mainDisplayTmp.toString());
    }

    private ArgX createArgX(ArgX argXOriginal) {
        ArgX argX = new ArgX();
        if (sciMode ||
                (Long.parseLong(argXOriginal.getExponent().toString()) +
                        argXOriginal.getMantissaFractionalPart().length() +
                        argXOriginal.getMantissaIntegerPart().length()) > numberLength) {// Если работаем в режиме SCI или
            // если значение экспоненты больше чем длины числа которую можно отобразить на экране или
            // если
            L.d(TAG, "Exponent: " + Long.parseLong(argXOriginal.getExponent().toString()));
            L.d(TAG, "MantissaFractionalPart length: " + argXOriginal.getMantissaFractionalPart().length());
            L.d(TAG, "MantissaIntegerPart length: " + argXOriginal.getMantissaIntegerPart().length());
            L.d(TAG, "Работаем в режиме SCI");
            StringBuilder sb = new StringBuilder(argXOriginal.getArgXSB());
            L.d(TAG, "Взяли argXOriginal: " + sb);
            double num = Double.parseDouble(sb.toString());
            String str = null;
            try {
                str = sciForm.format(num);
            } catch (Exception e) {
                L.d(TAG, "Ошибка: " + e);
            }
            L.d(TAG, "Преобразовали к формату SCI: " + str);
            argX.setFromString(str);
            L.d(TAG, "argX собран для режима SCI");
        } else {
            L.d(TAG, "Работаем в режиме NORMAL");
            StringBuilder sb = new StringBuilder(argXOriginal.getArgXSB());
            L.d(TAG, "Взяли argXOriginal: " + sb);
            double num = Double.parseDouble(sb.toString());
            String str = null;
            try {
                str = normForm.format(num);
            } catch (Exception e) {
                L.d(TAG, "Ошибка: " + e);
            }
            L.d(TAG, "Преобразовали к формату NORMAL: " + str);
            argX.setFromString(str);
            L.d(TAG, "argX собран для режима NORMAL");
        }
        return argX;
    }

    private StringBuilder getExponent(ArgX argX) {
        StringBuilder mainDisplayTmp = new StringBuilder();
        if (!argX.isExponent()) {// Если нет экспоненты, то выходим
            return mainDisplayTmp;
        }
        L.d(TAG, "Добавляем экспоненциальную часть.");
        if (argX.isExponentSign()) { // Если есть минус перед экспонентой, то добавляем его
            mainDisplayTmp.append("-");
            L.d(TAG, "Добавили минус перед экспонентой.");
        } else {
            mainDisplayTmp.append(" ");
            L.d(TAG, "Минуса нет, добавили пробел перед экспонентой.");
        }
        L.d(TAG, mainDisplayTmp.toString());
        //Добавляем ведущие нули
        for (int i = exponentLength - argX.getExponent().length(); i > 0; i--) {
            mainDisplayTmp.append("0");
            L.d(TAG, "Добавили ноль.");
            L.d(TAG, mainDisplayTmp.toString());
        }
        mainDisplayTmp.append(argX.getExponent());// Теперь добавляем экспоненту
        L.d(TAG, "Добавили экспоненту.");
        L.d(TAG, mainDisplayTmp.toString());
        return mainDisplayTmp;
    }

    private String getMantissaIntegerPart(ArgX argX) {
        StringBuilder mantissaBuilder = new StringBuilder();
        if (argX.isSign()) { // Если есть минус перед мантиссой, то добавляем его
            mantissaBuilder.append("-");
            L.d(TAG, "Добавили минус перед мантиссой.");
            L.d(TAG, mantissaBuilder.toString());
        }
        if (argX.getMantissaIntegerPart().length() == 0) { //Если нет целой части мантиссы, то выводим 0
            mantissaBuilder.append("0");
            L.d(TAG, "Нет целой части мантиссы, добавили ноль.");
        } else {
            mantissaBuilder.append(argX.getMantissaIntegerPart());// Теперь добавляем целую часть мантиссы
            L.d(TAG, "Добавили целую часть мантиссы.");
        }
        L.d(TAG, mantissaBuilder.toString());

        mantissaBuilder.append(".");// Всегда добавляем точку в конец целой части
        L.d(TAG, "Добавили точку в конец целой части.");
        L.d(TAG, mantissaBuilder.toString());
        return mantissaBuilder.toString();
    }

    @Override
    protected StringBuilder getStringBuilder(ArgXParent argX) {
        return null;
    }
}
