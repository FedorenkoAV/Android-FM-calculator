package ru.fmproject.android.calculator;

import android.content.Context;
import android.content.SharedPreferences;
import ru.fmproject.android.calculator.L;

/**
 * Created by User on 01.07.2017.
 * Объект этого класса взаимодействует с файлом настроек (чтение и запись). Этот класс - платформозависимый
 */

class Preferences {

    private static final String TAG = "Preferences";

    private static final String APP_PREFERENCES = "mysettings"; //Константа для имени файла настроек
    private static final String APP_PREFERENCES_MEMORY = "memory"; //содержимое памяти
    private static final String APP_PREFERENCES_FIX = "fix";// режим округления
    private static final String APP_PREFERENCES_ANGLE = "angle";//еденицы измерения углов
    private static final String APP_PREFERENCES_BIN = "bin";//число байт в двоичном числе
    private static final String APP_PREFERENCES_HEX = "hex";//число байт в шестнадцатиричном числе
    private static final String APP_PREFERENCES_OCT = "oct";//число байт в восьмеричном числе
    private static final String APP_PREFERENCES_EXP = "exp";//длина экспоненциальной части числа 1, 2, 3
    private static final String APP_PREFERENCES_NUM = "num";//длина числа

    private SharedPreferences mSettings; //переменная, представляющая экземпляр класса SharedPreferences, которая отвечает за работу с настройками

    Preferences(Context context) {
        mSettings = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE); //инициализируем переменную, которая отвечает за работу с настройками
        L.d(TAG, "Создали новый Preferences");
    }

    double getMemory() {
        double memoryValue = 0.0;
        if (mSettings.contains(APP_PREFERENCES_MEMORY)) {
            // Получаем значения из настроек
            memoryValue = Double.parseDouble(mSettings.getString(APP_PREFERENCES_MEMORY, "0,0"));
            L.d(TAG, "Получаем значение памяти из настроек: " + memoryValue);
        }
        return memoryValue;
    }

    void setMemory(double memoryValue) {
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString(APP_PREFERENCES_MEMORY, String.valueOf(memoryValue));
        L.d(TAG, "Занесли значение памяти в настройки: " + memoryValue);
        editor.apply();
        L.d(TAG, "Зафиксировали изменения в файле настроек.");
    }

    int getFixMode() {
        int fixModeScale = -1;
        if (mSettings.contains(APP_PREFERENCES_FIX)) {
            // Получаем значения из настроек
            fixModeScale = mSettings.getInt(APP_PREFERENCES_FIX, -1);
            L.d(TAG, "Получаем значение режима округления из настроек: " + fixModeScale);

        }
        return fixModeScale;
    }

    void setFixMode(int fixModeScale) {
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putInt(APP_PREFERENCES_FIX, fixModeScale);
        L.d(TAG, "Занесли значение режима округления в настройки: " + fixModeScale);
        editor.apply();
        L.d(TAG, "Зафиксировали изменения в файле настроек.");
    }

    int getAngleUnit() {
        int angleUnit = 0;
        if (mSettings.contains(APP_PREFERENCES_ANGLE)) {
            // Получаем значения из настроек
            angleUnit = mSettings.getInt(APP_PREFERENCES_ANGLE, 0);
            L.d(TAG, "Получаем значение едениц измерения угла из настроек: " + angleUnit);
        }
        return angleUnit;
    }

    void setAngleUnit(int angleUnit) {
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putInt(APP_PREFERENCES_ANGLE, angleUnit);
        L.d(TAG, "Занесли значение едениц измерения угла в настройки: " + angleUnit);
        editor.apply();
        L.d(TAG, "Зафиксировали изменения в файле настроек.");
    }

    int getBinLength() {
        int binLength = 1;
        if (mSettings.contains(APP_PREFERENCES_BIN)) {
            // Получаем значения из настроек
            binLength = mSettings.getInt(APP_PREFERENCES_BIN, 1);
            L.d(TAG, "Получаем значение режима округления из настроек: " + binLength);
        }
        return binLength;
    }

    void setBinLength(int binLength) {
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putInt(APP_PREFERENCES_BIN, binLength);
        L.d(TAG, "Занесли значение режима округления в настройки: " + binLength);
        editor.apply();
        L.d(TAG, "Зафиксировали изменения в файле настроек.");
    }

    int getHexLength() {
        int hexLength = 5;
        if (mSettings.contains(APP_PREFERENCES_HEX)) {
            // Получаем значения из настроек
            hexLength = mSettings.getInt(APP_PREFERENCES_HEX, 5);
            L.d(TAG, "Получаем значение режима округления из настроек: " + hexLength);
        }
        return hexLength;
    }

    void setHexLength(int hexLength) {
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putInt(APP_PREFERENCES_HEX, hexLength);
        L.d(TAG, "Занесли значение режима округления в настройки: " + hexLength);
        editor.apply();
        L.d(TAG, "Зафиксировали изменения в файле настроек.");
    }

    int getOctLength() {
        int octLength = 1;
        if (mSettings.contains(APP_PREFERENCES_OCT)) {
            // Получаем значения из настроек
            octLength = mSettings.getInt(APP_PREFERENCES_OCT, 3);
            L.d(TAG, "Получаем значение режима округления из настроек: " + octLength);
        }
        return octLength;
    }

    void setOctLength(int octLength) {
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putInt(APP_PREFERENCES_OCT, octLength);
        L.d(TAG, "Занесли значение режима округления в настройки: " + octLength);
        editor.apply();
        L.d(TAG, "Зафиксировали изменения в файле настроек.");
    }

    int getExpLength() {
        int expLength = 2;
        if (mSettings.contains(APP_PREFERENCES_EXP)) {
            // Получаем значения из настроек
            expLength = mSettings.getInt(APP_PREFERENCES_EXP, 2);
            L.d(TAG, "Получаем значение режима округления из настроек: " + expLength);
        }
        return expLength;
    }

    void setExpLength(int expLength) {
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putInt(APP_PREFERENCES_EXP, expLength);
        L.d(TAG, "Занесли значение режима округления в настройки: " + expLength);
        editor.apply();
        L.d(TAG, "Зафиксировали изменения в файле настроек.");
    }

    int getNumLength() {
        int numLength = 18;
        if (mSettings.contains(APP_PREFERENCES_NUM)) {
            // Получаем значения из настроек
            numLength = mSettings.getInt(APP_PREFERENCES_NUM, 18);
            L.d(TAG, "Получаем значение режима округления из настроек: " + numLength);
        }
        return numLength;
    }

    void setNumLength(int numLength) {
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putInt(APP_PREFERENCES_NUM, numLength);
        L.d(TAG, "Занесли значение режима округления в настройки: " + numLength);
        editor.apply();
        L.d(TAG, "Зафиксировали изменения в файле настроек.");
    }
}