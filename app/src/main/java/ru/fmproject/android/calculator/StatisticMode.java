package ru.fmproject.android.calculator;

import java.util.Stack;

/**
 * Created by User on 20.06.2017.
 * Класс StatisticMode содержит методы для работы со статистическими данными
 */
public class StatisticMode {
    private static final String TAG = "StatisticMode";

    private Stack<Double> sdStack;

    private Protocol protocol;

    public StatisticMode(Protocol protocol) {
        this.protocol = protocol;
        sdStack = new Stack<>();
        protocol.println("Statistic mode.");
        L.d(TAG, "Создан стек для статистических вычислений.");
    }

    public int getStackSize() {
        int n = sdStack.size();
        protocol.println("n = " + n);
        return n;
    }

    public boolean isStackEmpty() {
        return sdStack.empty();
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
        if (sdStack.removeElement(currentNum)) {
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
            if (sdStack.removeElement(dataValue)) {
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
    private double totalOfDatum(Stack stack) {
        if (stack.empty()) {
            return 0;
        }
        double totalOfDatum = 0;
        double stackValue;
        for (int n = 0; n < stack.size(); n++) {
            stackValue = (double) stack.get(n);
            L.d(TAG, "Из стека считан " + n + " элемент = " + stackValue);
            totalOfDatum = totalOfDatum + stackValue;
        }
        protocol.println("∑(x)=" + totalOfDatum);
        return totalOfDatum;
    }

    /**
     * @param stack
     * @return
     */
    private double totalOfSquare(Stack stack) {
        if (stack.empty()) {
            return 0;
        }
        double totalOfSquare = 0;
        double stackValue;
        for (int n = 0; n < stack.size(); n++) {
            stackValue = (double) stack.get(n);
            L.d(TAG, "Из стека считан " + n + " элемент = " + stackValue);
            stackValue = stackValue * stackValue;
            L.d(TAG, " его квадрат равен " + stackValue);
            totalOfSquare = totalOfSquare + stackValue;
        }
        protocol.println("∑(x²)=" + totalOfSquare);
        return totalOfSquare;
    }

    /**
     * @param stack
     * @return
     */
    private double averageOfDatum(Stack stack) {
        if (stack.empty()) {
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
    private double stdDeviOfPopu(Stack stack) {
        if (stack.empty()) {
            return 0;
        }
        int stackSize = stack.size();
        double totalOfdiffSquare = totalOfdiffSquare(stack);
        double stdDeviOfPopu = Math.sqrt(totalOfdiffSquare / (stackSize));
        protocol.println("Ϭ\u2099 = " + stdDeviOfPopu);
        return stdDeviOfPopu;
    }

    /**
     * @param stack
     * @return
     */
    private double totalOfdiffSquare(Stack stack) {
        if (stack.empty()) {
            return 0;
        }
        double totalOfdiffSquare = 0;
        double stackValue;
        double averageOfDatum = averageOfDatum(stack); //вычисляем среднее значение
        double squareDiff;
        int stackSize = stack.size();
        for (int n = 0; n < stackSize; n++) {
            stackValue = (double) stack.get(n);
            L.d(TAG, "Из стека считан " + n + " элемент = " + stackValue);
            squareDiff = Math.pow((stackValue - averageOfDatum), 2);
            totalOfdiffSquare = totalOfdiffSquare + squareDiff;
        }
        return totalOfdiffSquare;
    }

    /**
     * @param stack
     * @return
     */
    private double sampleStdDevi(Stack stack) {
        if (stack.empty()) {
            return 0;
        }
        int stackSize = stack.size();
        double totalOfdiffSquare = totalOfdiffSquare(stack);
        double sampleStdDevi = Math.sqrt(totalOfdiffSquare / (stackSize - 1));
        protocol.println("Ϭ\u2099\u208B\u2081 = " + sampleStdDevi);
//         Create spannable text and set style.
//        Spannable text = new SpannableString("σn-1");
//        text.setSpan(new SubscriptSpan(), 1, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        protocol.print(text);
//        protocol.println(" = " + sampleStdDevi);
//        protocol.println(Html.fromHtml("Ϭ<sub>n-1</sub>") + " = " + sampleStdDevi);
        return sampleStdDevi;
    }
}
