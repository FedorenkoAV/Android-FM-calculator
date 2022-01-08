package ru.fmproject.android.calculator.subscriber;

import java.io.File;

//Общий интерфейс подписчиков.
// Во многих языках, поддерживающих функциональные типы, можно обойтись без этого интерфейса и
// конкретных классов, заменив объекты подписчиков функциями.
public interface EventListener {
    void update(String eventType, File file);
}
