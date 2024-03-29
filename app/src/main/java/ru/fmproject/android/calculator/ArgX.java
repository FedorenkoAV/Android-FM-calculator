package ru.fmproject.android.calculator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by User on 12.06.2017.
 * ArgX это переменная, в которой в текстовом виде хранятся мантисса и экспонента
 */

public class ArgX extends ArgXParent{

    private StringBuilder mantissaIntegerPart; //Целая часть мантиссы
    private StringBuilder mantissaFractionalPart; //Дробная часть мантиссы
//    private boolean sign;// Знак мантиссы
    private StringBuilder exponent; //Экспоненциальная часть числа
    private boolean exponentSign; //Знак экспоненты
    private boolean isExponent;// Флаг наличия экспоненциальной части числа
    private boolean isMantissaFractionalPart;//Флаг наличия дробной части мантиссы

    private static final String TAG = "ArgX";

    public ArgX() {
        mantissaIntegerPart = new StringBuilder();
        mantissaFractionalPart = new StringBuilder();
        exponent = new StringBuilder();
        isExponent = false;
//        sign = false;
        exponentSign = false;
        isMantissaFractionalPart = false;
        //L.d(TAG, "Создали новый пустой ArgX");
    }

    public ArgX(double number) {
        setDouble(number);
    }

    @Override
    void setNumber(int intNumber) {
        setDouble(intNumber);
    }

    @Override
    void setNumber(long longNumber) {
        setDouble(longNumber);
    }

    @Override
    long getLong(int byteLength) {
        return Long.parseLong(getMantissaIntegerPart().toString());
    }

    public void setDouble(double number) {
        StringBuilder sb = new StringBuilder();
        sb.append(number); //В sb у нас все число, начинаем его пилить
        setFromStringBuilder(sb);
    }

    public void setFromString(String numberStr) {
        StringBuilder sb = new StringBuilder(numberStr);
        setFromStringBuilder(sb);
    }

    @Override
    public void setFromStringBuilder(StringBuilder sb) {  //В sb у нас все число, начинаем его пилить
        mantissaIntegerPart = new StringBuilder();
        mantissaFractionalPart = new StringBuilder();
        exponent = new StringBuilder();
        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        StringBuilder sb3 = new StringBuilder();
        int index = sb.indexOf(","); //Заменяем запятую на точку
        if (index != -1) {
            sb.replace(index, index + 1, ".");
        }

        //L.d(TAG, "Начинаем пилить число " + sb);
/*//        1. Отделяем мантиссу от экспоненты
*           2. Отделяем знак от экспоненты
*           3. Отделяем знак от мантиссы
*           4. Отделяем дробную часть от мантиссы
*           5. У нас должна остаться целая часть мантиссы*/
        sb1.append(getExponentSB(sb)); // В sb1 экспонента со знаком
        //L.d(TAG, "Вся экспонента вместе со знаком " + sb1);
        if (sb1.length() == 0) { // Если нет экспоненты
            //L.d(TAG, "Нет экспоненты.");
            //L.d(TAG, "exponent = " + exponent);
            isExponent = false;
            exponentSign = false;
        } else {
            isExponent = true;
            //L.d(TAG, "isExponent " + true);
            exponentSign = hasMinus(sb1);
            //L.d(TAG, "exponentSign " + exponentSign);
            exponent.append(getUnSignNumberSB(sb1));
            //L.d(TAG, "exponent " + exponent);
        }
        //L.d(TAG, "Начинаем пилить мантиссу");
        sb2.append(getMantissaSB(sb)); // В sb2 вся мантисса со знаком
        //L.d(TAG, "Вся мантисса со знаком " + sb2);
        setSign(hasMinus(sb2));
        sb3.append(getUnSignNumberSB(sb2)); //В sb3 вся мантисса без знака
        //L.d(TAG, "Вся мантисса без знака " + sb3);
        mantissaFractionalPart.append(getMantissaFractionalPartSB(sb3));
        isMantissaFractionalPart = true;
        if (mantissaFractionalPart.length() == 0) {
            isMantissaFractionalPart = false;
            mantissaFractionalPart.append("0");
        }
        mantissaIntegerPart.append(getMantissaIntPartSB(sb3));
        if (getMantissaIntegerPart().length() == 0) {
            mantissaIntegerPart.append("0");
        }
        //L.d(TAG, "Создали новый ArgX");
        //L.d(TAG, "Мантисса со знаком: " + mantissaSign);
        //L.d(TAG, "Целая часть мантиссы: " + mantissaIntegerPart);
        //L.d(TAG, "Дробная часть мантиссы: " + mantissaFractionalPart);

        //L.d(TAG, "Экспонента со знаком: " + exponentSign);
        //L.d(TAG, "Экспонента: " + exponent);
        //L.d(TAG, "Есть экспонента: " + isExponent);
        //L.d(TAG, "Есть дробная часть мантиссы: " + isMantissaFractionalPart);
        setVirginity(false);
        setEditable(false);
    }


    public void setMantissaIntegerPart(StringBuilder mantissaIntegerPart) {
        this.mantissaIntegerPart = mantissaIntegerPart;
        setVirginity(false);
    }

    public StringBuilder getMantissaIntegerPart() {
        return mantissaIntegerPart;
    }


    public void setMantissaFractionalPart(StringBuilder mantissaFractionalPart) {
        this.mantissaFractionalPart = mantissaFractionalPart;
    }

    public StringBuilder getMantissaFractionalPart() {
        return mantissaFractionalPart;
    }

    public void setExponent(StringBuilder exponent) {
        this.exponent = exponent;
    }

    public StringBuilder getExponent() {
        if (isExponent()) {
            return exponent;
        }
        return new StringBuilder("0");
    }

    public void setExponentSign(boolean exponentSign) {
        this.exponentSign = exponentSign;
    }

    public boolean isExponentSign() {
        return exponentSign;
    }

    public void setIsExponent(boolean exponent) {
        isExponent = exponent;
    }

    public boolean isExponent() {
        return isExponent;
    }

    public boolean isMantissaFractionalPart() {
        return isMantissaFractionalPart;
    }

    public void setIsMantissaFractionalPart(boolean mantissaFractionalPart) {
        isMantissaFractionalPart = mantissaFractionalPart;
        setVirginity(false);
    }

//    public boolean isEditable() {
//        return this.editable;
//    }

    public double getMantissaIntegerPartInDouble() {
        //L.d(TAG, "Целая часть мантиссы: " + mantissaIntegerPart);
        if (mantissaIntegerPart.length() == 0) {
            return 0.0;
        } else {
            return Double.parseDouble(mantissaIntegerPart.toString());
        }
    }

    public double getMantissaFractionalPartInDouble() {
        //L.d(TAG, "Дробная часть мантиссы: " + mantissaFractionalPart);
        if (mantissaFractionalPart.length() == 0) {
            return 0.0;
        } else {
            return Double.parseDouble("0." + mantissaFractionalPart.toString());
        }
    }

    public double getArgX() {
        double number = 0.0;
        try {
            number = Double.parseDouble(getArgXSB().toString());
        } catch (NumberFormatException e) {
            //L.d(TAG, "Вылет с ошибкой: " + e);
        }
        setEditable(false);
        return number;
    }

    public StringBuilder getArgXSB() {
        StringBuilder str = new StringBuilder();  //Создаем пустую строку
        if (isSign()) { //Если мантисса с минусом, то добавляем сначала его
            str.append('-');
            //L.d(TAG, "В str добавили минус мантиссе: " + str);
        }
        //        Обрабатываем целую часть мантиссы
        if (mantissaIntegerPart.length() < 1) { //Если целая часть мантиссы пустая, то
            str.append('0'); // добавляем 0
        } else {
            str.append(mantissaIntegerPart);// Добавляем целую часть мантиссы
        }
        //L.d(TAG, "В str добавили целую часть мантиссы : " + str);

        //        Обрабатываем дробную часть мантиссы
        str.append('.'); //сначала добавляем точку
        if (!isMantissaFractionalPart) { // Если нет дробной части мантиссы, то
            str.append('0'); // добавляем 0
        } else { // а если есть дробная часть мантиссы, то
            str.append(mantissaFractionalPart);//добавляем дробную часть мантиссы
        }
        //L.d(TAG, "В str добавили дробную часть мантиссы : " + str);

        //        Обрабатываем экспоненциальную часть числа
        if (isExponent) { //Если есть экспоненциальная часть, то
            str.append('E');//сначала добавляем 'E'
            //L.d(TAG, "В str добавили E: " + str);
            if (exponentSign) {//Если экспонента с минусом, то здесь добавляем его
                str.append('-');
                //L.d(TAG, "В str добавили минус экспоненте: " + str);
            }

            str.append(exponent);//затем добавляем саму экспоненту
            //L.d(TAG, "В str добавили экспоненту: " + str);
        }
        //L.d(TAG, "В результате в str: " + str);
        getMantissaSB(str);
        getExponentSB(str);
        return str;
    }

    /**
     * Функция принимает строковую переменную, содержащую число в
     * экспоненциальной форме и возвращает строковую переменную, содержащую
     * мантиссу числа. Если число не в экспоненциальной форме, то
     * функция возвращает строковую переменную без изменений
     *
     * @param SBNumber - строковая переменная, содержащая число в экспоненциальной форме.
     * @return SBMantissa - строковая переменная, содержащая мантиссу числа
     */
    private StringBuilder getMantissaSB(StringBuilder SBNumber) {
        StringBuilder SBMantissa = new StringBuilder();
        int expIndex = SBNumber.indexOf("E"); //Ищем букву E
        if (expIndex != -1) { // Если есть экспоненциальная часть числа, то отделяем мантиссу от экспоненты
            SBMantissa.append(SBNumber.substring(0, expIndex));
        } else {
            SBMantissa.append(SBNumber);
        }
        //L.d(TAG, "SB Мантисса: " + SBMantissa);
        return SBMantissa;
    }

    /**
     * Функция принимает строковую переменную, содержащую число в
     * экспоненциальной форме и возвращает строковую переменную, содержащую
     * экспоненциальную часть числа. Если число не в экспоненциальной форме, то
     * функция возвращает ""
     *
     * @param SBNumber - строковая переменная, содержащая число в экспоненциальной форме.
     * @return SBExponent - строковая переменная, содержащая экспоненциальную часть числа.
     */
    private StringBuilder getExponentSB(StringBuilder SBNumber) {
        StringBuilder SBExponent = new StringBuilder();
        int expIndex = SBNumber.indexOf("E"); //Ищем букву E
        if (expIndex != -1) { // Если есть экспоненциальная часть числа, то отделяем экспоненту от мантиссы
            SBExponent.append(SBNumber.substring(expIndex + 1, SBNumber.length()));
        }
        //L.d(TAG, "SB Экспонента: " + SBExponent);
        return SBExponent;
    }

    private StringBuilder getMantissaFractionalPartSB(StringBuilder mantissa) {
        int pointIndex;
        StringBuilder fracPart = new StringBuilder();
        pointIndex = mantissa.indexOf(".");
        if (pointIndex != -1) {
            fracPart.append(mantissa.substring(pointIndex + 1, mantissa.length()));
        }
        return fracPart;
    }

    private StringBuilder getMantissaIntPartSB(StringBuilder mantissa) {
        int pointIndex;
        StringBuilder intPart = new StringBuilder();
        pointIndex = mantissa.indexOf(".");
        if (pointIndex == -1) {//если нет точки, то вся мантисса и есть целая часть
            intPart.append(mantissa);
        } else {
            intPart.append(mantissa.substring(0, pointIndex));
        }
        return intPart;
    }

    private boolean hasMinus(StringBuilder number) {
        return number.charAt(0) == '-';
    }

    private StringBuilder getUnSignNumberSB(StringBuilder number) {
        if (!hasMinus(number)) { //Если нет минуса, возвращаем то же число
            return number;
        }
        number.deleteCharAt(0);
        //L.d(TAG, "После удаления минуса:" + number);
        return number;
    }

    public StringBuilder getRoundedMantissaFracPart(int scale, boolean withZeros) {
        //L.d(TAG, "Округляем дробную часть мантиссы");
        double num = getMantissaFractionalPartInDouble(); //Берем дробную часть мантиссы
        StringBuilder mantissaFractionalPart = getMantissaFractionalPart();//Берем дробную часть мантиссы
        //L.d(TAG, "Здесь дробная часть мантиссы : " + num);
        BigDecimal newBigDecimal = BigDecimal.valueOf(num).setScale(scale, RoundingMode.HALF_UP);// Округляем ее
        BigDecimal newBigDecimal2 = new BigDecimal(mantissaFractionalPart.toString()).setScale(scale, RoundingMode.HALF_UP);// Округляем ее
        //L.d(TAG, "newBigDecimal: " + newBigDecimal);
        num = newBigDecimal.doubleValue(); // num это наша округленная мантисса без нулей в конце
        //L.d(TAG, "Дробная часть мантиссы после округления: " + num);
        StringBuilder numSB = new StringBuilder();
        numSB.append(fracParttoDecimalFormat(num));
        //L.d(TAG, "Дробная часть мантиссы после нормализации: " + numSB);
        StringBuilder newSB = new StringBuilder();
        if (withZeros) {
            boolean zero = false;
            if (newBigDecimal.toString().equals("0E-7")) {
                newSB.append("0.0000000");
                zero = true;
            }
            if (newBigDecimal.toString().equals("0E-8")) {
                newSB.append("0.00000000");
                zero = true;
            }
            if (newBigDecimal.toString().equals("0E-9")) {
                newSB.append("0.000000000");
                zero = true;
            }
            if (!zero) {
                newSB.append((newBigDecimal));
            }
        } else {
            newSB.append(numSB);
        }
        newSB.delete(0, 2);//Удаляем '0' и '.'
        //L.d(TAG, "Дробная часть мантиссы после округления: " + newSB);
        String testString = newBigDecimal2.toPlainString();
        return newSB;
//        return new StringBuilder(newBigDecimal2.toPlainString());
    }

    private StringBuilder fracParttoDecimalFormat(Object srtDigit) {
        NumberFormat sciForm = new DecimalFormat("0.###############");
        return new StringBuilder(sciForm.format(srtDigit));
    }
}








