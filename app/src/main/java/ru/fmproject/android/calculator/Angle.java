package ru.fmproject.android.calculator;

/**
 * Created by User on 11.06.2017.
 * Объект этого класса отвечает за переключение едениц измерения углов. Само значение едениц
 * измерения хранится в файле настроек.
 * При создании объекта необходимо указать объект отвечающий за настройки и объект отвечающий за
 * отображение едениц измерения углов на статусном дисплее.
 */

public class Angle {

    private static final String TAG = "Angle";

    private StatusDisplay statusDisplay;
    private Preferences preferences;

    public Angle(Preferences preferences, StatusDisplay statusDisplay) {
        this.statusDisplay = statusDisplay;
        this.preferences = preferences;
        L.d(TAG, "statusDisplay: " + statusDisplay);
        L.d(TAG, "preferences: " + preferences);
        displayAngleUnit(getAngleUnit());
    }

    /**
     *
     * @return - возвращает текущие еденицы измерения углов.
     */
    public AngleUnit getAngleUnit() {
        AngleUnit angleUnit = preferences.getAngleUnit();
        L.d(TAG, "Получаем значение едениц измерения угла из настроек: " + angleUnit.getName());
        return angleUnit;
    }

    private void setAngleUnit(AngleUnit angleUnit) {
        preferences.setAngleUnit(angleUnit);
        L.d(TAG, "Занесли значение едениц измерения угла в настройки: " + angleUnit.getName());
        displayAngleUnit(angleUnit);
    }

    private void displayAngleUnit(AngleUnit angleUnit) {
        switch (angleUnit) {
            case DEG:
                statusDisplay.offRad();
                statusDisplay.offGrad();
                statusDisplay.onDeg();
                break;
            case RAD:
                statusDisplay.offDeg();
                statusDisplay.offGrad();
                statusDisplay.onRad();
                break;
            case GRAD:
                statusDisplay.offDeg();
                statusDisplay.offRad();
                statusDisplay.onGrad();
                break;
        }
    }

    /**
     * Переключает по кругу еденицы измерения углов DEG->RAD->GRAD->DEG.
     */

    public void switchAngleUnit() {
        AngleUnit angleUnit = preferences.getAngleUnit();
        switch (angleUnit) {
            case DEG:
                angleUnit = AngleUnit.RAD;
                break;
            case RAD:
                angleUnit = AngleUnit.GRAD;
                break;
            case GRAD:
                angleUnit = AngleUnit.DEG;
                break;
        }
        setAngleUnit(angleUnit);
    }
}
