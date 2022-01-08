package ru.fmproject.android.calculator;

import ru.fmproject.android.calculator.L;

/**
 * Created by User on 29.06.2017.
 * Объект этого класса хранит данные памяти калькулятора. При создании объекта значение должно браться из сохраненного значения (файла настроек)
 */

public class MemoryStore {

    private static final String TAG = "MemoryStore";

    private Status status;
    private Preferences preferences;

    public MemoryStore(Preferences preferences, Status status) {
        this.status = status;
        this.preferences = preferences;
        L.d(TAG, "status: " + status);
        L.d(TAG, "preferences: " + preferences);
        displayMemory(getMemory());
    }

    public double getMemory() {
        double memory = preferences.getMemory();
        L.d(TAG, "Получаем значение памяти из настроек: " + memory);
        return memory;
    }

    public void setMemory(double memory) {
        preferences.setMemory(memory);
        displayMemory(memory);
        L.d(TAG, "Теперь в памяти: " + memory);
    }

    private void displayMemory(double memory){
        if (memory != 0) {
            status.onMemory();
        } else {
            status.offMemory();
        }
    }
}
