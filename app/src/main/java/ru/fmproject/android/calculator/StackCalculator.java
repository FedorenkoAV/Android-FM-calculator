package ru.fmproject.android.calculator;


import ru.fmproject.android.calculator.L;

import java.util.Stack;

import static java.lang.Double.compare;


/**
 * Created by User on 15.06.2017.
 */


class StackCalculator {

    private static final String TAG = "StackCalculator";

    final int NOP = 0b000;
    final int PLUS = 0b010;
    final int MINUS = 0b011;
    final int MULTIPLY = 0b100;
    final int DIVIDE = 0b101;
    final int POWER = 0b110;
    final int X_SQR_Y = 0b111;

    //Приоритет 0
    final int NOP0 = 0;//=
    final int NOP1 = 1;//операция=

    //Приоритет 1
    final int NOP2 = 2;//->

    //Приоритет 2
    final int NOP3 = 3;//?:

    //Приоритет 3
    final int NOP4 = 4;//||

    //Приоритет 4
    final int NOP5 = 5;//&&

    //Приоритет 5
    final int OR = 6;//|

    //Приоритет 6
    final int XOR = 7;//^

    //Приоритет 7
    final int AND = 8;//&

    //Приоритет 8
    final int NOP9 = 9;//==
    final int NOP10 = 10;//!=

    //Приоритет 9
    final int NOP11 = 11;//>
    final int NOP12 = 12;//>=
    final int NOP13 = 13;//<
    final int NOP14 = 14;//<=
    final int NOP15 = 15;//instanceof

    //Приоритет 10
    final int SHR = 16;//>> SHR
    final int USHR = 17;//>>> USHR
    final int SHL = 18;//<< SHL
    final int RR = 18;//<< RR
    final int RL = 18;//<< RL

    //Приоритет 11
    final int PLUS2 = 19;
    final int MINUS2 = 20;

    //Приоритет 12
    final int MOD = 21;
    final int DIV = 22;

    //Приоритет 13
    final int MULTIPLY2 = 23;
    final int DIVIDE2 = 24;

    //Приоритет 14
    final int POWER2 = 6;
    final int X_SQR_Y2 = 7;


    private final char operations[] = {'=', ')', '+', '-', '×', '÷', '^', '√'};

    private double autoConstantNumber;
    private double secondOp;
    private int autoConstantOperation;
    private Stack<Double> numberStack;
    private Stack<Integer> operationStack;
    private Stack<Stack<Double>> stackForNumberStack;
    private Stack<Stack<Integer>> stackForOperationStack;

    private Stack<Integer> autoConstantOperationStack;
    private Stack<Double> autoConstantNumberStack;

    private Angle angle;
    private Protocol protocol;

    private boolean result;


    StackCalculator(Angle angle, Protocol protocol) {
        this.angle = angle;
        this.protocol = protocol;
        restart();
        L.d(TAG, "Создали новый StackCalculator");
    }

    void restart() {
        protocol.cls();
        autoConstantOperation = NOP;
        secondOpToProtocol(0.0);
        autoConstant(0.0);
        numberStack = new Stack<>();
        operationStack = new Stack<>();
        stackForNumberStack = new Stack<>();
        stackForOperationStack = new Stack<>();
        autoConstantNumberStack = new Stack<>();
        autoConstantOperationStack = new Stack<>();
        result = false;
    }

//    void toOCT() throws MyExceptions {
//
//    }
//
//    void toBIN() throws MyExceptions {
//
//    }
//
//    void toDEC() throws MyExceptions {
//
//    }
//
//    void toHEX() throws MyExceptions {
//
//    }

    /**
     * Функция перевода градусов в радианы
     *
     * @param deg число в градусах
     * @return число в радианах
     */
    private double degToRad(double deg) throws MyExceptions {
        return (deg * Math.PI / 180);
    }

    /**
     * Функция переводит радианы в грады
     *
     * @param rad
     * @return
     */
    private double radToGrad(double rad) throws MyExceptions {
        return (rad * 200 / Math.PI);
    }

    /**
     * Функция переводит грады в градусы
     *
     * @param grad
     * @return
     */
    private double gradToDeg(double grad) throws MyExceptions {
        return (grad * 180 / 200);
    }

    /**
     * Функция переводит грады в радианы
     *
     * @param grad
     * @return
     */
    private double gradToRad(double grad) throws MyExceptions {
        return (grad * Math.PI / 200);
    }

    /**
     * Функция переводит радианы в градусы
     *
     * @param rad
     * @return
     */
    private double radToDeg(double rad) throws MyExceptions {
        return (rad * 180 / Math.PI);
    }

    /**
     * Функция переводит текущие еденицы измерения угла (градусы, радианы,
     * грады), в радианы. Текущие еденицы измерения берутся из переменной
     * angleUnit
     *
     * @param angle
     * @return
     */
    private double toRad(double angle, int angleUnit) throws MyExceptions {
        double rad = 0;
        switch (angleUnit) {
            case Angle.RAD:
                rad = angle;
                break;
            case Angle.DEG:
                rad = degToRad(angle);
                break;
            case Angle.GRAD:
                rad = (gradToRad(angle));
                break;
        }
        return rad;
    }

    double dbm_to_w(double a1) {
        double res;
        res = 0.001 * (Math.pow(10, (a1 * 0.1)));
        protocol.println(a1 + "dbm = " + res + "W");
        return res;
    }

    double w_to_dbm(double a1) {
        double res;
        res = 10 * Math.log10(a1 / 0.001);
        protocol.println(a1 + "W = " + res + "dbm");
        return res;
    }

    double percent(double a1) throws MyExceptions {
        double currentNumber = a1;
        if (!operationStack.empty()) {
            int operation2 = operationStack.peek();
            if (operation2 == PLUS || operation2 == MINUS) {
                L.d(TAG, currentNumber + "% от " + numberStack.peek() + " = ");
                protocol.print(currentNumber + "% от " + numberStack.peek() + " = ");
                currentNumber = numberStack.peek() * currentNumber / 100;
            } else {
                L.d(TAG, "Набрано: " + currentNumber + "% = ");
                protocol.print("Набрано: " + currentNumber + "% = ");
                currentNumber = currentNumber / 100;
            }
        } else {
            L.d(TAG, "Набрано: " + currentNumber + "% = ");
            protocol.print(currentNumber + "% = ");
            currentNumber = currentNumber / 100;
        }
        protocol.println("" + currentNumber);
        result = true;
        return currentNumber;
    }

    double factorial(ArgX argX) throws MyExceptions {
        int n;
        if (argX.isMantissaFractionalPart()) {
            throw new MyExceptions(MyExceptions.Companion.getNOT_INTEGER());
        }
        double a1 = argX.getArgX();

        n = (int) a1;

        if (n < 0) {
            L.d(TAG, "Отрицательное число");
            throw new MyExceptions(MyExceptions.Companion.getNEGATIVE());
        }
        if (n > 170) {
            L.d(TAG, "Слишком большое число для вычисления факториала");
            throw new MyExceptions(MyExceptions.Companion.getTOO_BIG());
        }
        a1 = 1;
        for (int i = 1; i <= n; i++) {
            a1 = a1 * i;
        }
        protocol.println(n + "! = " + a1);
        result = true;
        return a1;
    }

    double random() throws MyExceptions {
        result = true;
        return Math.random();
    }

    double maxValue() throws MyExceptions {
        result = true;
        return Double.MAX_VALUE;
    }

    double minValue() throws MyExceptions {
        result = true;
        return Double.MIN_VALUE;
    }

    double maxExponent() throws MyExceptions {
        result = true;
        return Double.MAX_EXPONENT;
    }

    double minExponent() throws MyExceptions {
        result = true;
        return Double.MIN_EXPONENT;
    }

    double minNormal() throws MyExceptions {
        result = true;
        return Double.MIN_NORMAL;
    }


    /**
     * Функция переводит радианы в текущие еденицы измерения угла (градусы,
     * радианы, грады), текущие еденицы измерения берутся из переменной
     * angleUnit
     *
     * @param rad
     * @return
     */
    private double fromRad(double rad, int angleUnit) throws MyExceptions {
        L.d(TAG, "Переводим угол из радиан в");
        double angle = 0;
        switch (angleUnit) {
            case Angle.RAD:
                L.d(TAG, "радианы");
                angle = rad;
                break;
            case Angle.DEG:
                L.d(TAG, "градусы");
                angle = radToDeg(rad);
                break;
            case Angle.GRAD:
                L.d(TAG, "грады");
                angle = radToGrad(rad);
                break;
        }
        L.d(TAG, rad + " радиан = " + angle + " градусов или градов");
        return angle;
    }


    double x_to_y(double firstNumber) throws MyExceptions {
        double secondNumber;
        if (!numberStack.empty()) {
            secondNumber = numberStack.pop();
            numberStack.push(firstNumber);
        } else {
            secondNumber = secondOp;
        }
        secondOpToProtocol(firstNumber);
        result = true;
        return secondNumber;
    }

    double changeAngleUnit(double a1) throws MyExceptions {
        double res = 0.0;
        L.d(TAG, "Переводим углы");
        int angleUnit = angle.getAngle();
        L.d(TAG, a1 + angle.toString(angleUnit) + " -> ");
        protocol.print(a1 + angle.toString(angleUnit) + " -> ");
        switch (angleUnit) {
            case Angle.DEG:
                res = degToRad(a1);
                break;
            case Angle.RAD:
                res = radToGrad(a1);
                break;
            case Angle.GRAD:
                res = gradToDeg(a1);
                break;
        }
        angle.switchAngle();
        angleUnit = angle.getAngle();
        L.d(TAG, res + angle.toString(angleUnit));
        protocol.println(res + angle.toString(angleUnit));
        result = true;
        return res;
    }

    void r_to_p(ArgX argA, ArgX argB) throws MyExceptions {
        double tmp;
        double keyA = argA.getArgX();
        double keyB = argB.getArgX();
        protocol.print("( " + keyA + " , " + keyB + " ) -> ");
        tmp = Math.sqrt(keyA * keyA + keyB * keyB);
        keyB = fromRad(Math.atan(keyB / keyA), angle.getAngle());
        keyA = tmp;
        protocol.println("( " + keyA + " , " + keyB + angle.toString(angle.getAngle()) + ")");
        argA.setDouble(keyA);
        argB.setDouble(keyB);
        result = true;
    }

    void p_to_r(ArgX argA, ArgX argB) throws MyExceptions {
        double tmp;
        double keyA = argA.getArgX();
        L.d(TAG, "keyA: " + keyA);
        double keyB = argB.getArgX();
        L.d(TAG, "keyB: " + keyB);
        protocol.print("( " + keyA + " , " + keyB + angle.toString(angle.getAngle()) + ") -> ");
        tmp = keyA * Math.cos(toRad(keyB, angle.getAngle()));
        keyB = keyA * Math.sin(toRad(keyB, angle.getAngle()));
        keyA = tmp;
        L.d(TAG, "Результат вычислений:" + keyB);
        L.d(TAG, "keyA: " + keyA);
        L.d(TAG, "keyB: " + keyB);
        protocol.println("( " + keyA + " , " + keyB + " )");
        argA.setDouble(keyA);
        argB.setDouble(keyB);
        result = true;
    }

    double x2(double a1) throws MyExceptions {
        double res;
        res = (a1 * a1);
        L.d(TAG, a1 + "^2 = " + res);
        protocol.println(a1 + "^2 = " + res);
        result = true;
        return res;
    }

    double _1_div_x(double a1) throws MyExceptions {
        if (compare(a1, 0.0) < 0) {
            throw new MyExceptions(MyExceptions.Companion.getDIVIDE_BY_ZERO());
        }
        double res;
        res = (1 / a1);
        L.d(TAG, "1/" + a1 + " = " + res);
        protocol.println("1/" + a1 + " = " + res);
        result = true;
        return res;
    }

    double log(double a1) throws MyExceptions {
        double res;
        res = Math.log10(a1);
        L.d(TAG, "Log(" + a1 + ") = " + res);
        protocol.println("Log(" + a1 + ") = " + res);
        result = true;
        return res;
    }

    double _10x(double a1) throws MyExceptions {
        double res;
        res = Math.pow(10, a1);
        L.d(TAG, "10^" + a1 + " = " + res);
        protocol.println("10^" + a1 + " = " + res);
        result = true;
        return res;
    }

    double ln(double a1) throws MyExceptions {
        double res;
        res = Math.log(a1);
        L.d(TAG, "ln(" + a1 + ") = " + res);
        protocol.println("ln(" + a1 + ") = " + res);
        result = true;
        return res;
    }

    double exp(double a1) throws MyExceptions {
        double res;
        res = Math.exp(a1);
        L.d(TAG, "e^" + a1 + ") = " + res);
        protocol.println("e^" + a1 + ") = " + res);
        result = true;
        return res;
    }

    public double involute(double a1) throws MyExceptions {
        int angleUnit = angle.getAngle();
        protocol.print("inv(" + a1 + angle.toString(angleUnit) + ") = ");
        a1 = toRad(a1, angleUnit);
        if (compare(a1, 0.0) < 0) {
            throw new MyExceptions(MyExceptions.Companion.getNEGATIVE());
        }
        if (compare(a1, Math.PI / 2.0) >= 0) {
            throw new MyExceptions(MyExceptions.Companion.getTOO_BIG(), "Ожидается угол меньше " + fromRad(Math.PI / 2.0, angleUnit) + angle.toString(angleUnit));
        }
        double res = Math.tan(a1) - a1;
        L.d(TAG, "inv(" + a1 + angleUnit + ") = " + res);
        protocol.println(res);
        result = true;
        return res;
    }

    double sin(double a1) throws MyExceptions {
        int angleUnit = angle.getAngle();
        protocol.print("sin(" + a1 + angle.toString(angleUnit) + ") = ");
        a1 = toRad(a1, angleUnit);
        double res = sinradian(a1);
        L.d(TAG, "sin(" + a1 + angleUnit + ") = " + res);
        protocol.println(res);
        result = true;
        return res;
    }

    private double sinradian(double a1) throws MyExceptions {
        a1 = a1 % (2.0 * Math.PI);
        if (compare(Math.abs(a1), 0.0) == 0 || compare(Math.abs(a1), Math.PI) == 0) {//0, 180, 360
            return 0.0;
        }
        if (compare(Math.abs(a1), Math.PI / 6.0) == 0 || compare(Math.abs(a1), 5.0 * Math.PI / 6.0) == 0) {//30, 150
            return 0.5;
        }
        if (compare(Math.abs(a1), Math.PI / 2.0) == 0) {// 90
            return 1.0;
        }
        if (compare(Math.abs(a1), 7.0 * Math.PI / 6.0) == 0 || compare(Math.abs(a1), 11.0 * Math.PI / 6.0) == 0) {//210, 330
            return -0.5;
        }
        if (compare(Math.abs(a1), 3.0 * Math.PI / 2.0) == 0) {//270
            return -1.0;
        }

        return Math.sin(a1);
    }

    double arsh(double a1) throws MyExceptions {
        double res = Math.log(a1 + Math.sqrt(a1 * a1 + 1));
        L.d(TAG, "Arsh(" + a1 + ") = " + res);
        protocol.println("Arsh(" + a1 + ") = " + res);
        result = true;
        return res;
    }

    public double evolvent(double inv) throws MyExceptions {
        double f = 0.0;
        L.d(TAG, "Значение инвалюты: " + inv);
        if (compare(inv, 0.0) < 0) {
            throw new MyExceptions(MyExceptions.Companion.getNEGATIVE());
        }
        if (compare(inv, 1.8) > 0) {
            throw new MyExceptions(MyExceptions.Companion.getTOO_BIG(), "Максимальное число для расчета эвольвенты 1.8");
        }
        if (compare(inv, 1.0) < 0) {//Метод Ласкина (Laskin)
//            compare (inv, 1.0);
            L.d(TAG, "Будем считать по методу Ласкина.");
            f = 1.441 * Math.cbrt(inv) - 0.374 * inv;
            L.d(TAG, "f0 = " + f);
            for (int i = 0; i < 4; ++i) {
                f = f + (inv - (Math.tan(f) - f)) / Math.pow(Math.tan(f), 2);
                L.d(TAG, "f" + (i + 1) + " = " + f);
            }
        } else {//Метод Ченга (Cheng)
            L.d(TAG, "Будем считать по методу Ченга.");
            double f0 = Math.pow(3.0 * inv, 1.0 / 3.0);
            double f1 = Math.pow(3.0, 2.0 / 3.0);
            double f2 = Math.pow(inv, 5.0 / 3.0);
            double f3 = Math.pow(3.0, 1.0 / 3.0);
            double f4 = Math.pow(inv, 7.0 / 3.0);
            double f5 = Math.pow(inv, 3.0);
//            double f6 = Math.pow(3.0, 2.0 / 3.0);
            double f7 = Math.pow(inv, 11.0 / 3.0);
//            double f8 = Math.pow(3.0, 1.0 / 3.0);
            double f9 = Math.pow(inv, 13.0 / 3.0);
            f = f0
                    - (2.0 * inv) / 5.0
                    + (9.0 / 175.0)
                    * f1
                    * f2
                    - (2.0 / 175.0)
                    * f3
                    * f4
                    - (144.0 / 67375.0)
                    * f5
                    + (3258.0 / 3128125.0)
                    * f1
                    * f7
                    - (49711.0 / 153278125.0)
                    * f3
                    * f9;
        }
        int angleUnit = angle.getAngle();
        f = fromRad(f, angleUnit);
        L.d(TAG, "evolvent(" + inv + ") = " + f + angle.getAngle());
        protocol.println("evolvent(" + inv + ") = " + f + angle.toString(angleUnit));
        result = true;
        return f;
    }

    double asin(double a1) throws MyExceptions {
        if (compare(a1, 1.0) > 0 || compare(a1, -1.0) < 0) {
            throw new MyExceptions(MyExceptions.Companion.getTOO_BIG(), "Ожидается число в диапазоне  [-1 .. 1]");
        }
        double res = Math.asin(a1);
        int angleUnit = angle.getAngle();
        res = fromRad(res, angleUnit);
        L.d(TAG, "arcsin(" + a1 + ") = " + res + angle.getAngle());
        protocol.println("arcsin(" + a1 + ") = " + res + angle.toString(angleUnit));
        result = true;
        return res;
    }

    double sinh(double a1) throws MyExceptions {
        double res = Math.sinh(a1);
        L.d(TAG, "sh(" + a1 + ") = " + res);
        protocol.println("sh(" + a1 + ") = " + res);
        result = true;
        return res;
    }

    double cos(double a1) throws MyExceptions {
        int angleUnit = angle.getAngle();
        double res;
        protocol.print("cos(" + a1 + angle.toString(angleUnit) + ") = ");
        a1 = toRad(a1, angleUnit);
        res = cosradian(a1);
        L.d(TAG, "cos(" + a1 + angleUnit + ") = " + res);
        protocol.println(res);
        result = true;
        return res;
    }

    private double cosradian(double a1) throws MyExceptions {
        a1 = a1 % (2.0 * Math.PI);
        if (compare(Math.abs(a1), 0.0) == 0) {
            return 1.0;
        }
        if (compare(Math.abs(a1), Math.PI / 3.0) == 0 || compare(Math.abs(a1), 5.0 * Math.PI / 3.0) == 0) {
            return 0.5;
        }
        if (compare(Math.abs(a1), Math.PI / 2.0) == 0 || compare(Math.abs(a1), 3.0 * Math.PI / 2.0) == 0) {
            return 0.0;
        }
        if (compare(Math.abs(a1), 2.0 * Math.PI / 3.0) == 0 || compare(Math.abs(a1), 4.0 * Math.PI / 3.0) == 0) {
            return -0.5;
        }
        if (compare(Math.abs(a1), Math.PI) == 0) {
            return -1.0;
        }
        return Math.cos(a1);
    }

    double arch(double a1) throws MyExceptions {
        double res = Math.log(a1 + Math.sqrt(a1 + 1) * Math.sqrt(a1 - 1));
        L.d(TAG, "Arch(" + a1 + ") = " + res);
        protocol.println("Arch(" + a1 + ") = " + res);
        result = true;
        return res;
    }

    double acos(double a1) throws MyExceptions {
        if (compare(a1, 1.0) > 0 || compare(a1, -1.0) < 0) {
            throw new MyExceptions(MyExceptions.Companion.getTOO_BIG(), "Ожидается число в диапазоне  [-1 .. 1]");
        }
        double res = Math.acos(a1);
        int angleUnit = angle.getAngle();
        res = fromRad(res, angleUnit);
        L.d(TAG, "arccos(" + a1 + ") = " + res + angle.getAngle());
        protocol.println("arccos(" + a1 + ") = " + res + angle.toString(angleUnit));
        result = true;
        return res;
    }

    double cosh(double a1) throws MyExceptions {
        double res = Math.cosh(a1);
        L.d(TAG, "ch(" + a1 + ") = " + res);
        protocol.println("ch(" + a1 + ") = " + res);
        result = true;
        return res;
    }

    double tan(double a1) throws MyExceptions {
        int angleUnit = angle.getAngle();
        double res;
        protocol.print("tan(" + a1 + angle.toString(angleUnit) + ") = ");
        a1 = toRad(a1, angleUnit);
        res = tanradian(a1);
        L.d(TAG, "tan(" + a1 + angleUnit + ") = " + res);
        protocol.println(res);
        result = true;
        return res;
    }

    private double tanradian(double a1) throws MyExceptions {
        a1 = a1 % (2.0 * Math.PI);
        if (compare(Math.abs(a1), Math.PI / 2.0) == 0 || compare(Math.abs(a1), 3.0 * Math.PI / 2.0) == 0) {
            throw new MyExceptions(MyExceptions.Companion.getINFINITY());
        }
        if (compare(Math.abs(a1), Math.PI) == 0) {
            return 0.0;
        }
        if (compare(Math.abs(a1), Math.PI / 4.0) == 0 || compare(Math.abs(a1), 5.0 * Math.PI / 4.0) == 0) {
            return 1.0;
        }
        if (compare(Math.abs(a1), 3.0 * Math.PI / 4.0) == 0 || compare(Math.abs(a1), 7.0 * Math.PI / 4.0) == 0) {
            return -1.0;
        }
        return Math.tan(a1);
    }


    double arth(double a1) throws MyExceptions {
        double res = 0.5 * Math.log((1 + a1) / (1 - a1));
        L.d(TAG, "Arth(" + a1 + ") = " + res);
        protocol.println("Arth(" + a1 + ") = " + res);
        result = true;
        return res;
    }

    double atan(double a1) throws MyExceptions {
        double res = Math.atan(a1);
        int angleUnit = angle.getAngle();
        res = fromRad(res, angleUnit);
        L.d(TAG, "arctan(" + a1 + ") = " + res + angle.getAngle());
        protocol.println("arctan(" + a1 + ") = " + res + angle.toString(angleUnit));
        result = true;
        return res;
    }

    double tanh(double a1) throws MyExceptions {
        double res = Math.tanh(a1);
        L.d(TAG, "tanh(" + a1 + ") = " + res);
        protocol.println("tanh(" + a1 + ") = " + res);
        result = true;
        return res;
    }

    double pi() throws MyExceptions {
        result = true;
        return Math.PI;
    }

    double sqrt(double a1) throws MyExceptions {
        double res = Math.sqrt(a1);
        L.d(TAG, "√(" + a1 + ") = " + res);
        protocol.println("√(" + a1 + ") = " + res);
        result = true;
        return res;
    }

    double cbrt(double a1) throws MyExceptions {
        double res = Math.cbrt(a1);
        L.d(TAG, "³√(" + a1 + ") = " + res);
        protocol.println("³√(" + a1 + ") = " + res);
        result = true;
        return res;
    }

    private double stringToDouble(StringBuilder strNumber) throws MyExceptions {
        double doubleNumber = 0;
//        switch (workMode) {
//            case BIN:
//                strNumber = binToDec (strNumber);
//                break;
//            case OCT:
//                strNumber = octToDec (strNumber);
//                break;
//            case HEX:
//                strNumber = hexToDec (strNumber);
//                break;
//        }
        try {
            doubleNumber = Double.parseDouble(strNumber.toString());
        } catch (Exception ex) {
            L.d(TAG, "Ошибка перевода строки в число");
        }
        L.d(TAG, "После превода строки в число, получилось" + doubleNumber);
        return doubleNumber;
    }

    double fromDeg(ArgX argX) throws MyExceptions { //переводим градусы в ГГ.ММССсс
        /*
         * 1. Берем целую часть градусов и запоминаем ее
         * 2. Берем дробную часть градусов
         * 3. Дробную часть градусов умножаем на 60, получатся минуты состоящие из целой и дробной частей
         * 4. Берем целую часть минут и запоминаем ее
         * 5. Берем дробную часть минут
         * 6. Дробную часть минут умножаем на 60, получатся секунды состоящие из целой и дробной частей
         * 7. Собираем ответ по формуле degree = degreeInt + minInt/100 + sec/10000
         *
         * */
        double degreeInt = argX.getMantissaIntegerPartinDouble();
        L.d(TAG, "Целая часть градусов = " + degreeInt);

        double degreeFrac = argX.getMantissaFractionalPartinDouble();
        L.d(TAG, "Дробная часть градусов = " + degreeFrac);

        double min = degreeFrac * 60;
        L.d(TAG, "Минуты = " + degreeFrac + " * 60 = " + min);

        ArgX minArgX = new ArgX(min);
        double minInt = minArgX.getMantissaIntegerPartinDouble();
        L.d(TAG, "Целая часть минут = " + minInt);

        double minFrac = minArgX.getMantissaFractionalPartinDouble();
        L.d(TAG, "Дробная часть минут = " + minFrac);

        double sec = minFrac * 60;
        L.d(TAG, "Секунды = " + minFrac + " * 60 = " + sec);

        double degree = degreeInt + minInt / 100 + sec / 10000;
        L.d(TAG, argX.getArgX() + "\u00B0 -> " + (int) degreeInt + "\u00B0 " + (int) minInt + "′ " + sec + "″");
        protocol.println(argX.getArgX() + "\u00B0 -> " + (int) degreeInt + "\u00B0 " + (int) minInt + "′ " + sec + "″");
        result = true;
        return degree;
    }

    double toDeg(ArgX argX) throws MyExceptions {
        /*1. Берем челую часть мантиссы - это будут градусы
         * 2. Берем дробную часть мантиссы
         * 3. Выделяем из дробной части мантиссы первые два символа - это будут минуты
         * 4. Выделяем из дробной части мантиссы следующие два символа - это будут секунды
         * 5. Выделяем из дробной части мантиссы оставшиеся символы - это будут милисекунды
         * */

        L.d(TAG, "Переводим градусы, минуты, секунды и миллисекунды в градусы.");

        double degree;
        double minD;
        double secD;
        double msecD;

        StringBuilder minSB = new StringBuilder("");
        StringBuilder secSB = new StringBuilder("");
        StringBuilder msecSB = new StringBuilder("0.");

        int mantissaFractionalPartLength;

        mantissaFractionalPartLength = argX.getMantissaFractionalPart().length();

        if (mantissaFractionalPartLength < 5) {
            L.d(TAG, "Дробная часть мантиссы, всего " + mantissaFractionalPartLength + " символов, добавим нулей.");
            argX.setIsMantissaFractionalPart(true);
            argX.setMantissaFractionalPart(argX.getMantissaFractionalPart().append("00000"));
            mantissaFractionalPartLength = argX.getMantissaFractionalPart().length();
            L.d(TAG, "Теперь дробная часть мантиссы " + argX.getMantissaFractionalPart());
        }

        degree = argX.getMantissaIntegerPartinDouble();
        if (argX.isSign()) {
            degree = -degree;
        }
        L.d(TAG, "Градусы: " + degree);

        minSB.append(argX.getMantissaFractionalPart(), 0, 2);
        minD = stringToDouble(minSB);
        L.d(TAG, "Минуты: " + minD);

        secSB.append(argX.getMantissaFractionalPart(), 2, 4);
        secD = stringToDouble(secSB);
        L.d(TAG, "Секунды: " + secD);

        msecSB.append(argX.getMantissaFractionalPart(), 4, mantissaFractionalPartLength);
        msecD = stringToDouble(msecSB);
        L.d(TAG, "Миллисекунды: " + msecD);
        protocol.print((int) degree + "\u00B0" + minSB + "′" + secSB + msecSB.substring(1, msecSB.length()) + "′′" + " -> ");
        degree = (degree + minD / 60 + (secD + msecD) / 3600);
        L.d(TAG, "Результат: " + degree);
        protocol.println(degree + "\u00B0");
        result = true;
        return degree;
    }

    private double plus(double number1, double number2) throws MyExceptions {
        autoConstantOperation = PLUS;
        autoConstant(number2);
        return (number1 + number2);
    }

    private double minus(double number1, double number2) throws MyExceptions {
        autoConstantOperation = MINUS;
        autoConstant(number2);
        return (number1 - number2);
    }

    private double multiply(double number1, double number2) throws MyExceptions {
        autoConstantOperation = MULTIPLY;
        autoConstant(number1);
        return (number1 * number2);
    }

    private double divide(double number1, double number2) throws MyExceptions {
        autoConstantOperation = DIVIDE;
        autoConstant(number2);
        if (compare(number2, 0.0) < Double.MIN_VALUE) {
            throw new MyExceptions(MyExceptions.Companion.getDIVIDE_BY_ZERO());
        }
        return (number1 / number2);
    }

    private double power(double number1, double number2) throws MyExceptions {
        autoConstantOperation = POWER;
        autoConstant(number2);
        return (Math.pow(number1, number2));
    }

    private double xSqrY(double number1, double number2) throws MyExceptions {
        autoConstantOperation = X_SQR_Y;
        autoConstant(number2);
        return (Math.pow(number1, 1 / number2));
    }

    private void autoConstant(double number) {

        autoConstantNumber = number;
//            protocol.setAutoConstant(autoConstantOperation, number);

    }


    /**
     * secondOpToProtocol выводит в протокол второй операнд
     */
    private void secondOpToProtocol(double number) {
        secondOp = number;
//        protocol.setSecondOp(number);
        //jTextFieldSecondOp.setText(doubleToString(secondOpToProtocol));
        L.d(TAG, "Сейчас второй операнд: " + secondOp);
    }

    private double autoConstantCalculator(double currentNumber) throws MyExceptions {
        switch (autoConstantOperation) {
            case PLUS:
                L.d(TAG, "Вычисления с использованием автоконстанты: " + currentNumber + "+" + autoConstantNumber + "=");
                currentNumber = currentNumber + autoConstantNumber;
                L.d(TAG, "" + currentNumber);
                break;
            case MINUS:
                L.d(TAG, "Вычисления с использованием автоконстанты: " + currentNumber + "-" + autoConstantNumber + "=");
                currentNumber = currentNumber - autoConstantNumber;
                L.d(TAG, "" + currentNumber);
                break;
            case MULTIPLY:
                L.d(TAG, "Вычисления с использованием автоконстанты: " + currentNumber + "×" + autoConstantNumber + "=");
                currentNumber = currentNumber * autoConstantNumber;
                L.d(TAG, "" + currentNumber);
                break;
            case DIVIDE:
                L.d(TAG, "Вычисления с использованием автоконстанты: " + currentNumber + "÷" + autoConstantNumber + "=");
                currentNumber = currentNumber / autoConstantNumber;
                L.d(TAG, "" + currentNumber);
                break;
            case POWER:
                L.d(TAG, "Вычисления с использованием автоконстанты: " + currentNumber + "^" + autoConstantNumber + "=");
                currentNumber = Math.pow(currentNumber, autoConstantNumber);
                L.d(TAG, "" + currentNumber);
                break;
            case X_SQR_Y:
                L.d(TAG, "Вычисления с использованием автоконстанты: " + currentNumber + "√" + autoConstantNumber + "=");
                currentNumber = Math.pow(currentNumber, 1 / autoConstantNumber);
                L.d(TAG, "" + currentNumber);
                break;
            case NOP:
                L.d(TAG, "Не задано арифмитическое действие. Ничего не делаю.");
                break;
        }
        return currentNumber;
    }


    void oldStacksRestore() {
        numberStack = stackForNumberStack.pop();
        operationStack = stackForOperationStack.pop();
        autoConstantNumber = autoConstantNumberStack.pop();
        autoConstantOperation = autoConstantOperationStack.pop();
        if (!numberStack.empty()) {
            secondOpToProtocol(numberStack.peek());
        } else {
            secondOpToProtocol(0.0);
        }
        L.d(TAG, "Восстановлены старые стеки операций и чисел");
    }

    boolean isNumberStackEmpty() {
        return numberStack.empty();
    }

    boolean isOperationStackEmpty() {
        return operationStack.empty();
    }

    boolean isStackForNumberStackEmpty() {
        return stackForNumberStack.empty();
    }

    boolean isStackForOperationStackEmpty() {
        return stackForOperationStack.empty();
    }

    boolean isResult() {
        return result;
    }

    void setResult(boolean result) {
        this.result = result;
    }

    void stacksInStacks() {
        secondOp = 0.0;
        stackForOperationStack.push(operationStack);    //При открытии скобки стек операций и стек чисел сохраняем в стеке
        stackForNumberStack.push(numberStack);          //и создаем новые
        autoConstantNumberStack.push(autoConstantNumber);
        autoConstantOperationStack.push(autoConstantOperation);
        numberStack = new Stack<>();
        operationStack = new Stack<>();
        L.d(TAG, "Созданы новые стеки операций и чисел");
    }

    void restoreStacks() {
        numberStack = stackForNumberStack.pop();
        operationStack = stackForOperationStack.pop();
        autoConstantNumber = autoConstantNumberStack.pop();
        autoConstantOperation = autoConstantOperationStack.pop();
        if (!numberStack.empty()) {
            secondOpToProtocol(numberStack.peek());
        } else {
            secondOpToProtocol(0.0);
        }
    }

    double putInStack(double currentNumber, int currentOperation) {
        numberStack.push(currentNumber);
        secondOpToProtocol(currentNumber);
        L.d(TAG, "В стек чисел заношу: " + currentNumber);
        operationStack.push(currentOperation);
        L.d(TAG, "В стек операций заношу: " + operations[currentOperation]);
        L.d(TAG, "");
        return currentNumber;
    }

    double calcAutoConstant(double currentNumber) throws MyExceptions {
        currentNumber = autoConstantCalculator(currentNumber);
        result = true;
        return currentNumber;
    }

    public double setAutoConstant(double currentNumber) {
        if (!numberStack.empty() && !operationStack.empty()) {
            autoConstantOperation = operationStack.pop();
            autoConstant(numberStack.pop());
            L.d(TAG, "После setAutoConstant размер стека операций = " + operationStack.size());
            L.d(TAG, "После setAutoConstant размер стека чисел = " + numberStack.size());
        }
        result = false;
        return currentNumber;
    }

    boolean isMultiply() {//Возвращает true только если в стеке чисел что-то есть, а в стеке операций - умножить.
//        return !(isOperationStackEmpty() || isNumberStackEmpty()) && operationStack.peek() == MULTIPLY;
        if (isOperationStackEmpty() || isNumberStackEmpty())
            return false;
        if (operationStack.peek() == MULTIPLY) {
            return true;
        }
        return false;
    }

    double getNumberForStatistic() {
        operationStack.pop(); // Очищаем стек операций от оператора умножения
        return numberStack.pop(); //извлекаем число из стека чисел
    }

    double calc(double currentNumber, int currentOperation) throws MyExceptions {
        L.d(TAG, "stackCalculator() запущен");
        int prevOperation;
        double prevNumber;
        int currentPrioritet;
        int prevPrioritet;

        L.d(TAG, "В начале calc размер стека операций = " + operationStack.size());
        L.d(TAG, "В начале calc размер стека чисел = " + numberStack.size());
        prevOperation = operationStack.peek(); // Смотрим какая была предыдущая операция
        currentPrioritet = currentOperation >>> 1; //Вычисляем приоритет
        prevPrioritet = prevOperation >>> 1; //Вычисляем приоритет
        prevNumber = numberStack.peek();
        L.d(TAG, "Предыдущая операция была: " + operations[prevOperation] + " ее приоритет: " + prevPrioritet);
        L.d(TAG, "Текущая операция: " + operations[currentOperation] + " ее приоритет: " + currentPrioritet);
        L.d(TAG, "Первое число: " + prevNumber);
        L.d(TAG, "Второе число: " + currentNumber);
        if (prevPrioritet < currentPrioritet) { // Пункт №2 если предыдущая операция более низкого приоритета - занести в стек число и операцию
            L.d(TAG, "У текущей операции приоритет выше, чем у предыдущей, заношу все в стек");
            numberStack.push(currentNumber);
            secondOpToProtocol(currentNumber);
            L.d(TAG, "В стек чисел заношу: " + currentNumber);
            operationStack.push(currentOperation);
            L.d(TAG, "В стек операций заношу: " + operations[currentOperation]);
            return currentNumber;
        }
        // Здесь уже ясно, что предыдущая операция такого же или более высокого приоритета, т.е. Пункт №3
        do {
            prevOperation = operationStack.pop();
            prevNumber = numberStack.pop();
            secondOpToProtocol(currentNumber);
            switch (prevOperation) {
                case PLUS:
                    L.d(TAG, prevNumber + " + " + currentNumber + " = ");
                    protocol.print(prevNumber + " + " + currentNumber + " = ");
                    currentNumber = plus(prevNumber, currentNumber);
                    break;
                case MINUS:
                    L.d(TAG, prevNumber + " - " + currentNumber + " = ");
                    protocol.print(prevNumber + " - " + currentNumber + " = ");
                    currentNumber = minus(prevNumber, currentNumber);
                    break;
                case MULTIPLY:
                    L.d(TAG, prevNumber + " × " + currentNumber + " = ");
                    protocol.print(prevNumber + " × " + currentNumber + " = ");
                    currentNumber = multiply(prevNumber, currentNumber);
                    break;
                case DIVIDE:
                    L.d(TAG, prevNumber + " ÷ " + currentNumber + " = ");
                    protocol.print(prevNumber + " ÷ " + currentNumber + " = ");
                    currentNumber = divide(prevNumber, currentNumber);
                    break;
                case POWER:
                    L.d(TAG, prevNumber + " ^ " + currentNumber + " = ");
                    protocol.print(prevNumber + " ^ " + currentNumber + " = ");
                    currentNumber = power(prevNumber, currentNumber);
                    break;
                case X_SQR_Y:
                    L.d(TAG, prevNumber + " √ " + currentNumber + " = ");
                    protocol.print(prevNumber + " √ " + currentNumber + " = ");
                    currentNumber = xSqrY(prevNumber, currentNumber);
                    break;
                case NOP:
                    break;
            }
            L.d(TAG, currentNumber + "");
            protocol.println(currentNumber + "");
        }
        while (!operationStack.empty() && (operationStack.peek() >>> 1) >= currentPrioritet); // если в стеке операций еще что-то есть и оно более высокого приоритета, то повторить

        if (!operationStack.empty()) { //Стек будет не пуст, если предыдущая операция оказалась более низкого приоритета
            prevOperation = operationStack.peek();
            prevPrioritet = prevOperation >>> 1;
            L.d(TAG, "Предыдущая операция была: " + operations[prevOperation]);
            L.d(TAG, "Её приоритет: " + prevPrioritet);
        }

        L.d(TAG, "В конце calc размер стека операций = " + operationStack.size());
        L.d(TAG, "В конце calc размер стека чисел = " + numberStack.size());
        L.d(TAG, "" + currentNumber);

        if (currentOperation != NOP) { //Если была любая операция кроме NOP, то заносим значения в стек чисел и в стек операций
            L.d(TAG, "Вычисления еще не окончены. Заношу значения в стек чисел и в стек операций");
            numberStack.push(currentNumber);
            secondOpToProtocol(currentNumber);
            L.d(TAG, "В стек чисел заношу: " + currentNumber);
            operationStack.push(currentOperation);
            L.d(TAG, "В стек операций заношу: " + operations[currentOperation]);
            L.d(TAG, "");
        }
        //printCalc(currentNumber);
        L.d(TAG, "Расчет окончен.");
        L.d(TAG, "------------------------------------------------");
        result = true;
        return currentNumber;
    }


}
