package ru.fmproject.android.calculator;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Created by User on 20.06.2017.
 * Класс StatisticMode содержит методы для работы со статистическими данными
 */
public class StatisticMode {
    private static final String TAG = "StatisticMode";

    private final Deque<Double> sdStack;

    private final Protocol protocol;

    public StatisticMode(Protocol protocol) {
        this.protocol = protocol;
        sdStack = new ArrayDeque<>();
        protocol.println("Statistic mode.");
        L.d(TAG, "Создан стек для статистических вычислений.");
    }

    public int getStackSize() {
        int n = sdStack.size();
        protocol.println("n = " + n);
        return n;
    }

    public boolean isStackEmpty() {
        return sdStack.isEmpty();
    }

    public int singleNumberToStack(double currentNum) {
        sdStack.push(currentNum);             // здесь кладем значение в SD стек
        protocol.println(sdStack.size() + ": " + currentNum);
        L.d(TAG, "В стек положено число " + currentNum);
        return sdStack.size();
    }

    public int pushMultipleNumberToStack(double currentNum, int count) throws MyExceptions {
        for (int n = 0; n < count; n++) {
            sdStack.push(currentNum);
            protocol.println(sdStack.size() + ": " + currentNum);
        }
        L.d(TAG, "Число " + currentNum + " положено в стек " + count + " раз.");
        return sdStack.size();
    }

    public int deleteTopNumber() throws MyExceptions { //Удаляем значение с вершины стека
        if (isStackEmpty()) {
            return 0;
        }
        sdStack.pop();             // здесь удаляем значение из стека
        protocol.println(sdStack.size() + ": " + sdStack.peek());
        return sdStack.size();
    }

    public int deleteSingleNumber(double currentNum) { //Удаляем конкретное число из стека
        if (sdStack.remove(currentNum)) {
            L.d(TAG, "Значение " + currentNum + " успешно удалено из стека");
        } else {
            L.d(TAG, "Значение " + currentNum + " в стеке не найдено");
        }
        protocol.println(sdStack.size() + ": " + sdStack.peek());
        return sdStack.size();
    }

    public int deleteMultipleNumberFromStack(double dataValue, int i) {
        int count = 0;
        for (int n = 0; n < i; n++) {
            if (sdStack.remove(dataValue)) {
                count++;
            }
        }
        L.d(TAG, "Число " + dataValue + " удалено из стека " + count + " раз.");
        protocol.println(sdStack.size() + ": " + sdStack.peek());
        return sdStack.size();
    }

    public double totalOfAllData() {
        return totalOfDatum(sdStack);
    }

    public double totalOfSquareOfAllData() {
        return totalOfSquare(sdStack);
    }

    public double average() {
        return averageOfDatum(sdStack);
    }

    public double sampleStandartDeviation() {
        return sampleStdDevi(sdStack); //Стандартное отклонение выборки
    }

    public double populationStandartDeviation() {
        return stdDeviOfPopu(sdStack); //Стандартное отклонение популяции
    }


    /**
     * @param stack
     * @return
     */
    private double totalOfDatum(Deque<Double> stack) {
        if (stack.isEmpty()) {
            return 0;
        }
        double totalOfDatum = stack.stream()
                .mapToDouble(x -> x)
                .sum();
        protocol.println("∑(x)=" + totalOfDatum);
        return totalOfDatum;
    }

    /**
     * @param stack
     * @return
     */
    private double totalOfSquare(Deque<Double> stack) {
        if (stack.isEmpty()) {
            return 0;
        }
        double totalOfSquare = stack.stream()
                .mapToDouble(x -> x * x)
                .sum();
        protocol.println("∑(x²)=" + totalOfSquare);
        return totalOfSquare;
    }

    /**
     * @param stack
     * @return
     */
    private double averageOfDatum(Deque<Double> stack) {
        if (stack.isEmpty()) {
            return 0;
        }
        double averageOfDatum = totalOfDatum(stack) / stack.size();
        protocol.println("x = " + averageOfDatum);
        return averageOfDatum;
    }

    /**
     * @param stack
     * @return
     */
    //Standard deviation of population
    private double stdDeviOfPopu(Deque<Double> stack) {
        if (stack.isEmpty()) {
            return 0;
        }
        int stackSize = stack.size();
        double totalOfDiffSquare = totalOfDiffSquare(stack);
        double stdDeviOfPopu = Math.sqrt(totalOfDiffSquare / (stackSize));
        protocol.println("Ϭ\u2099 = " + stdDeviOfPopu);
        return stdDeviOfPopu;
    }

    /**
     * @param stack
     * @return
     */
    private double totalOfDiffSquare(Deque<Double> stack) {
        if (stack.isEmpty()) {
            return 0;
        }
        double averageOfDatum = averageOfDatum(stack); //вычисляем среднее значение
        return stack.stream()
                .mapToDouble(stackValue -> Math.pow((stackValue - averageOfDatum), 2))
                .sum();
    }

    /**
     * @param stack
     * @return
     */
    private double sampleStdDevi(Deque<Double> stack) {
        if (stack.isEmpty()) {
            return 0;
        }
        int stackSize = stack.size();
        double totalOfDiffSquare = totalOfDiffSquare(stack);
        double sampleStdDevi = Math.sqrt(totalOfDiffSquare / (stackSize - 1));
        protocol.println("Ϭ\u2099\u208B\u2081 = " + sampleStdDevi);
        return sampleStdDevi;
    }
}
