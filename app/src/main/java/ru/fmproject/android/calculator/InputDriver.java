package ru.fmproject.android.calculator;

import android.support.v4.app.FragmentManager;
import android.view.HapticFeedbackConstants;
import android.view.View;


/**
 * Класс InputDriver это связующее звено между внешним миром и внутренним устройством.
 * Объекту этого класса передаются ссылки на объекты которым будут передаваться действия пользователя
 * Этот класс нужно переписывать/редактировать заново для каждого нового устройства
 */

class InputDriver implements View.OnClickListener {

    private static final String TAG = "InputDriver";

    private CustomToast inDevelopToast;
    private CustomToast emptyFunction;
    private CustomToast sdModeToast;
    private StatusDisplay statusDisplay;
    private Status status;
    private Mode mode;
    private Angle angle;
    private MemoryStore memoryStore;

    private MainDisplay mainDisplay;
    private StackCalculator stackCalculator;

    private EditX editX;
    private EditXBin editXBin;
    private EditXOct editXOct;
    private EditXHex editXHex;
    private ComplexStackCalculator complexStackCalculator;
    private StatisticMode statisticMode;
    private InputDriver inputDriver;
    private MainActivity activity;
    private FragmentManager manager;

    Object objStore[];


    InputDriver(Object objStore[]) {
        this.objStore = objStore;
        statusDisplay = (StatusDisplay) objStore[MainActivity.STATUS_DISPLAY];
        status = (Status) objStore[MainActivity.STATUS];
        mode = (Mode) objStore[MainActivity.MODE];
        angle = (Angle) objStore[MainActivity.ANGLE];
        memoryStore = (MemoryStore) objStore[MainActivity.MEMORY];

        mainDisplay = (MainDisplay) objStore[MainActivity.MAIN_DISPLAY];
        stackCalculator = (StackCalculator) objStore[MainActivity.STACK_CALCULATOR];
        editX = (EditX) objStore[MainActivity.EDIT_X];
        editXBin = (EditXBin) objStore[MainActivity.EDIT_X_BIN];
        editXOct = (EditXOct) objStore[MainActivity.EDIT_X_OCT];
        editXHex = (EditXHex) objStore[MainActivity.EDIT_X_HEX];
        complexStackCalculator = (ComplexStackCalculator) objStore[MainActivity.CPLX];
        statisticMode = (StatisticMode) objStore[MainActivity.SD];
        inputDriver = (InputDriver) objStore[MainActivity.INPUT_DRIVER];
        activity = (MainActivity) objStore[MainActivity.MAIN_ACTIVITY];
        manager = (FragmentManager) objStore[MainActivity.FRAGMENT_MANAGER];

        for (int i = 0; i < objStore.length; i++) {

            L.d(TAG, "В objStore[" + i + "]: " + objStore[i]);
        }
        inDevelopToast = new CustomToast(activity, "В разработке");
        emptyFunction = new CustomToast(activity, "Здесь ничего нет.");
        sdModeToast = new CustomToast(activity, "Не работает в режиме статистических расчетов.");

        L.d(TAG, "Создали новый InputDriver");
    }

    private void buttonShift() {
        status.switchShift();
    }

    private void buttonHyp() {
        status.switchHyp();
    }

    private void buttonOff() {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            //
            activity.finish();
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            // 
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            // 
            status.offShift();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            // 
            status.offHyp();
            status.offShift();
            inDevelopToast.show();

        }
    }

    private void buttonOnC() {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            //  ON_C
            editX.restart();
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            //
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            //  switchSD
            editX.startSDMode();
            status.offShift();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //
            status.offHyp();
            status.offShift();
            inDevelopToast.show();

        }
    }

    private void buttonOnCBin() {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            //  ON_C
            editXBin.restart();
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            //
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            //  switchSD
            editXBin.toDec();
            mode.setDEC();
            editX.startSDMode();
            status.offShift();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //
            status.offHyp();
            status.offShift();
            inDevelopToast.show();

        }
    }

    private void buttonOnCOct() {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            //  ON_C
            editXOct.restart();
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            //
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            //  switchSD
            editXOct.toDec();
            mode.setDEC();
            editX.startSDMode();
            status.offShift();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //
            status.offHyp();
            status.offShift();
            inDevelopToast.show();

        }
    }

    private void buttonOnCHex() {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            //  ON_C
            editXHex.restart();
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            //
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            //  switchSD
            editXHex.toDec();
            mode.setDEC();
            editX.startSDMode();
            status.offShift();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //
            status.offHyp();
            status.offShift();
            inDevelopToast.show();

        }
    }

    private void buttonDel() {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            // del
            editX.del();
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            // 
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            // CPLX
//            editXBin.toDec();
//            mode.setDEC();
            editX.startCplxMode();
            status.offShift();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            // 
            status.offHyp();
            status.offShift();
            inDevelopToast.show();

        }
    }

    private void buttonDelBin() {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            // del
            editXBin.del();
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            //
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            // CPLX
            editX.startCplxMode();
            status.offShift();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //
            status.offHyp();
            status.offShift();
            inDevelopToast.show();

        }
    }

    private void buttonDelHex() {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            // del
            editXHex.del();
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            //
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            // CPLX
            editX.startCplxMode();
            status.offShift();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //
            status.offHyp();
            status.offShift();
            inDevelopToast.show();

        }
    }

    private void buttonDelOct() {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            // del
            editXOct.del();
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            //
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            // CPLX
            editX.startCplxMode();
            status.offShift();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //
            status.offHyp();
            status.offShift();
            inDevelopToast.show();

        }
    }

    private void buttonSci() {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            // SCI
            editX.switchSciMode();
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            // 
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            // FIX
            editX.setFixMode();
            status.offShift();

            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            // 
            status.offHyp();
            status.offShift();
            inDevelopToast.show();

        }
    }

    private void buttonDrg() throws MyExceptions {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            //  DRG
            angle.switchAngle();
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            // 
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            // translate angle
            editX.changeAngleUnit();
            status.offShift();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            // 
            status.offHyp();
            status.offShift();
            inDevelopToast.show();

        }
    }

    private void buttonDrgBin() throws MyExceptions {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            //  DRG
            angle.switchAngle();
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            //
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            // translate angle
            status.offShift();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //
            status.offHyp();
            status.offShift();
            inDevelopToast.show();

        }
    }

    private void buttonDrgOct() throws MyExceptions {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            //  DRG
            angle.switchAngle();
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            //
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            // translate angle
            status.offShift();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //
            status.offHyp();
            status.offShift();
            inDevelopToast.show();

        }
    }

    private void buttonDrgHex() throws MyExceptions {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            //  DRG
            angle.switchAngle();
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            //
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            // translate angle
            status.offShift();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //
            status.offHyp();
            status.offShift();
            inDevelopToast.show();

        }
    }

    private void buttonGear() throws MyExceptions {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            //involute
            editX.invalute();
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            //
            status.offHyp();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            //evolvent
            editX.evolvent();
            status.offShift();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //
            status.offHyp();
            status.offShift();
        }
    }

    private void buttonSin() throws MyExceptions {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            //sin
            editX.sin();
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            //sinh
            editX.sinh();
            status.offHyp();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            // asin
            editX.asin();
            status.offShift();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            // Гиперболический арксинус
            editX.arsh();
            status.offHyp();
            status.offShift();

        }
    }

    private void buttonCos() throws MyExceptions {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            // cos
            editX.cos();
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            // cosh
            editX.cosh();
            status.offHyp();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            // acos
            editX.acos();
            status.offShift();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            // Гиперболический арккосинус
            editX.arch();
            status.offHyp();
            status.offShift();

        }
    }

    private void buttonTan() throws MyExceptions {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            // tan
            editX.tan();
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            // tanh
            editX.tanh();
            status.offHyp();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            // atan
            editX.atan();
            status.offShift();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            // Гиперболический арктангенс
            editX.arth();
            status.offHyp();
            status.offShift();

        }
    }

    private void buttonExp() throws MyExceptions {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            //  EXP
            editX.exp();
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            // 
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            //  pi
            editX.pi();
            status.offShift();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            // 
            status.offHyp();
            status.offShift();
            inDevelopToast.show();

        }
    }

    private void buttonExpHex() throws MyExceptions {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            // A
            editXHex.add('A');
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            //
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            //
            status.offShift();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //  16.06.2017
            status.offHyp();
            status.offShift();
            inDevelopToast.show();
        }
    }

    private void buttonXpowY() throws MyExceptions {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            //  XpowY
            editX.power();
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            // 
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            // x_sqr_y
            editX.x_sqr_y();
            status.offShift();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            // 
            status.offHyp();
            status.offShift();
            inDevelopToast.show();
        }
    }

    private void buttonXpowYHex() throws MyExceptions {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            // B
            editXHex.add('b');
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            //
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            //
            status.offShift();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //  16.06.2017
            status.offHyp();
            status.offShift();
            inDevelopToast.show();
        }
    }

    private void buttonSqr() throws MyExceptions {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            // sqrt
            editX.sqrt();
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            //  16.06.2017
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            // cbrt
            editX.cbrt();
            status.offShift();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //  16.06.2017
            status.offHyp();
            status.offShift();
            inDevelopToast.show();
        }
    }

    private void buttonSqrHex() throws MyExceptions {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            // C
            editXHex.add('C');
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            //
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            //
            status.offShift();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //  16.06.2017
            status.offHyp();
            status.offShift();
            inDevelopToast.show();
        }
    }

    private void buttonToRad() throws MyExceptions {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            //toDeg
            editX.toDeg();
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            //  16.06.2017
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            // fromDeg
            editX.fromDeg();
            status.offShift();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //  16.06.2017
            status.offHyp();
            status.offShift();
            inDevelopToast.show();
        }
    }

    private void buttonToRadHex() throws MyExceptions {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            // D
            editXHex.add('d');
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            //
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            //
            status.offShift();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //  16.06.2017
            status.offHyp();
            status.offShift();
            inDevelopToast.show();
        }
    }

    private void buttonLn() throws MyExceptions {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            // ln
            editX.ln();
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            //  16.06.2017
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            // e pow x
            editX.exponent();
            status.offShift();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //  16.06.2017
            status.offHyp();
            status.offShift();
            inDevelopToast.show();
        }
    }

    private void buttonLnHex() throws MyExceptions {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            // E
            editXHex.add('E');
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            //
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            //
            status.offShift();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //  16.06.2017
            status.offHyp();
            status.offShift();
            inDevelopToast.show();
        }
    }

    private void buttonLog() throws MyExceptions {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            // L
            editX.log();
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            //  16.06.2017
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            // 10^x
            editX._10x();
            status.offShift();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //  16.06.2017
            status.offHyp();
            status.offShift();
            inDevelopToast.show();
        }
    }

    private void buttonLogHex() throws MyExceptions {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            // F
            editXHex.add('F');
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            //
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            //
            status.offShift();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //  16.06.2017
            status.offHyp();
            status.offShift();
            inDevelopToast.show();
        }
    }

    private void buttonX2() throws MyExceptions {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            // x^2
            editX.x2();
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            //  16.06.2017
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            // 1 div x
            editX._1_div_x();
            status.offShift();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //  16.06.2017
            status.offHyp();
            status.offShift();
            inDevelopToast.show();
        }
    }

    private void buttonA() throws MyExceptions {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            // toA
            editX.toA();
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            //  16.06.2017
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            // R to P
            editX.r_to_p();
            status.offShift();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //  16.06.2017
            status.offHyp();
            status.offShift();
            inDevelopToast.show();
        }
    }

    private void buttonB() throws MyExceptions {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            // toB
            editX.toB();
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            //  16.06.2017
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            // P to R
            editX.p_to_r();
            status.offShift();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //  16.06.2017
            status.offHyp();
            status.offShift();
            inDevelopToast.show();
        }
    }

    private void buttonOpenBracket() throws MyExceptions {
        if (mode.getMode() == mode.SD) {
            if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
                sdModeToast.show();
                return;
            }
            if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
                //  16.06.2017
                status.offHyp();
                inDevelopToast.show();
                return;
            }
            if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
                // 
                inDevelopToast.show();
                status.offShift();
                return;
            }
            if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
                //  16.06.2017
                status.offHyp();
                status.offShift();
                inDevelopToast.show();
            }

        } else {
            if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
                // (
                editX.openBracket();
                return;
            }
            if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
                //  16.06.2017
                status.offHyp();
                inDevelopToast.show();
                return;
            }
            if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
                // x_to_y
                editX.x_to_y();
                status.offShift();
                return;
            }
            if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
                //  16.06.2017
                status.offHyp();
                status.offShift();
                inDevelopToast.show();
            }
        }
    }

    private void buttonOpenBracketBin() throws MyExceptions {

        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            // (

            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            //  16.06.2017
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            // x_to_y
            editXBin.x_to_y();
            status.offShift();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //  16.06.2017
            status.offHyp();
            status.offShift();
            inDevelopToast.show();

        }
    }

    private void buttonOpenBracketOct() throws MyExceptions {

        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            // (

            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            //  16.06.2017
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            // x_to_y
            editXOct.x_to_y();
            status.offShift();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //  16.06.2017
            status.offHyp();
            status.offShift();
            inDevelopToast.show();

        }
    }

    private void buttonOpenBracketHex() throws MyExceptions {

        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            // (

            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            //  16.06.2017
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            // x_to_y
            editXHex.x_to_y();
            status.offShift();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //  16.06.2017
            status.offHyp();
            status.offShift();
            inDevelopToast.show();

        }
    }

    private void buttonCloseBracket() throws MyExceptions {
        if (mode.getMode() == mode.SD) {
            if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
                //stackSize
                editX.stackSize();
                return;
            }
            if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
                //  16.06.2017
                status.offHyp();
                inDevelopToast.show();
                return;
            }
            if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
                // total
                editX.total();
                status.offShift();
                return;
            }
            if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
                //  16.06.2017
                status.offHyp();
                status.offShift();
                inDevelopToast.show();
            }

        } else {
            if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
                // )
                editX.closeBracket();
                return;
            }
            if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
                //  16.06.2017
                status.offHyp();
                inDevelopToast.show();
                return;
            }
            if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
                //  16.06.2017
                status.offShift();
                inDevelopToast.show();
                return;
            }
            if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
                //  16.06.2017
                status.offHyp();
                status.offShift();
                inDevelopToast.show();
            }
        }
    }

    private void buttonXtoM() {
        if (mode.getMode() == mode.SD) {
            if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
                // average
                editX.average();
                return;
            }
            if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
                //  16.06.2017
                status.offHyp();
                inDevelopToast.show();
                return;
            }
            if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
                //totalSquare
                editX.totalSquare();
                status.offShift();
                return;
            }
            if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
                //  16.06.2017
                status.offHyp();
                status.offShift();
                inDevelopToast.show();
            }

        } else {
            if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
                // X to memoryStore
                editX.x_to_m();
                return;
            }
            if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
                //  16.06.2017
                status.offHyp();
                inDevelopToast.show();
                return;
            }
            if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
                //  16.06.2017
                status.offShift();
                inDevelopToast.show();
                return;
            }
            if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
                //  16.06.2017
                status.offHyp();
                status.offShift();
                inDevelopToast.show();
            }
        }
    }

    private void buttonXtoMBin() {

        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            // X to memoryStore
            editXBin.x_to_m();
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            //  16.06.2017
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            //  16.06.2017
            status.offShift();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //  16.06.2017
            status.offHyp();
            status.offShift();
            inDevelopToast.show();
        }
    }

    private void buttonXtoMOct() {

        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            // X to memoryStore
            editXOct.x_to_m();
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            //  16.06.2017
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            //  16.06.2017
            status.offShift();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //  16.06.2017
            status.offHyp();
            status.offShift();
            inDevelopToast.show();
        }
    }

    private void buttonXtoMHex() {

        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            // X to memoryStore
            editXHex.x_to_m();
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            //  16.06.2017
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            //  16.06.2017
            status.offShift();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //  16.06.2017
            status.offHyp();
            status.offShift();
            inDevelopToast.show();
        }
    }


    private void button07() {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            //  7
            editX.add('7');
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            //
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            //
            status.offShift();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //
            status.offHyp();
            status.offShift();
            inDevelopToast.show();
        }
    }

    private void button07Oct() {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            //  7
            editXOct.add('7');
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            //
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            //
            status.offShift();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //
            status.offHyp();
            status.offShift();
            inDevelopToast.show();
        }
    }

    private void button07Hex() {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            //  7
            editXHex.add('7');
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            //
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            //
            status.offShift();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //
            status.offHyp();
            status.offShift();
            inDevelopToast.show();
        }
    }

    private void button08() {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            //  8
            editX.add('8');
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            //
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            //
            status.offShift();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //
            status.offHyp();
            status.offShift();
            inDevelopToast.show();
        }
    }

    private void button08Hex() {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            //  8
            editXHex.add('8');
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            //
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            //
            status.offShift();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //
            status.offHyp();
            status.offShift();
            inDevelopToast.show();
        }
    }

    private void button09() throws MyExceptions {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            //  9
            editX.add('9');
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            //
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            //
            editX.maxValue();
            status.offShift();
//            inDevelopToast.show();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //
            status.offHyp();
            status.offShift();
            inDevelopToast.show();
        }
    }

    private void button09Hex() throws MyExceptions {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            //  9
            editXHex.add('9');
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            //
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            //
            status.offShift();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //
            status.offHyp();
            status.offShift();
            inDevelopToast.show();
        }
    }

    private void buttonMem() throws MyExceptions {
        if (mode.getMode() == mode.SD) {
            if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
                editX.putDataToStack();
                return;
            }
            if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
                //  16.06.2017
                status.offHyp();
                inDevelopToast.show();
                return;
            }
            if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
                //  16.06.2017
                editX.deleteDataFromStack();
                status.offShift();
                return;
            }
            if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
                //  16.06.2017
                status.offHyp();
                status.offShift();
                inDevelopToast.show();
            }

        } else {
            if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
                //  M+
                editX.memoryPlus();
                return;
            }
            if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
                // 
                status.offHyp();
                inDevelopToast.show();
                return;
            }
            if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
                // 
                status.offShift();
                inDevelopToast.show();
                return;
            }
            if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
                //  16.06.2017
                status.offHyp();
                status.offShift();
                inDevelopToast.show();
            }
        }
    }

    private void buttonMemBin() throws MyExceptions {

        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            //  M+
            editXBin.memoryPlus();
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            //
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            //
            status.offShift();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //  16.06.2017
            status.offHyp();
            status.offShift();
            inDevelopToast.show();
        }
    }

    private void buttonMemOct() throws MyExceptions {

        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            //  M+
            editXOct.memoryPlus();
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            //
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            //
            status.offShift();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //  16.06.2017
            status.offHyp();
            status.offShift();
            inDevelopToast.show();
        }
    }

    private void buttonMemHex() throws MyExceptions {

        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            //  M+
            editXHex.memoryPlus();
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            //
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            //
            status.offShift();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //  16.06.2017
            status.offHyp();
            status.offShift();
            inDevelopToast.show();
        }
    }


    private void buttonMR() {
        if (mode.getMode() == mode.SD) {
            if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
                //sampleStandartDeviation
                editX.sampleStandartDeviation();
                return;
            }
            if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
                //  16.06.2017
                status.offHyp();
                inDevelopToast.show();
                return;
            }
            if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
                // populationStandartDeviation
                editX.populationStandartDeviation();
                status.offShift();
                return;
            }
            if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
                //  16.06.2017
                status.offHyp();
                status.offShift();
                inDevelopToast.show();
            }

        } else {
            if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
                //  MR
                editX.readMemory();
                return;
            }
            if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
                // 
                status.offHyp();
                inDevelopToast.show();
                return;
            }
            if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
                // MC
                editX.clearMemory();
                status.offShift();
                return;
            }
            if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
                //  16.06.2017
                status.offHyp();
                status.offShift();
                inDevelopToast.show();
            }
        }
    }

    private void buttonMRBin() {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            //  MR
            editXBin.readMemory();
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            //
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            // MC
            editXBin.clearMemory();
            status.offShift();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //  16.06.2017
            status.offHyp();
            status.offShift();
            inDevelopToast.show();
        }
    }

    private void buttonMROct() {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            //  MR
            editXOct.readMemory();
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            //
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            // MC
            editXOct.clearMemory();
            status.offShift();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //  16.06.2017
            status.offHyp();
            status.offShift();
            inDevelopToast.show();
        }
    }

    private void buttonMRHex() {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            //  MR
            editXHex.readMemory();
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            //
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            // MC
            editXHex.clearMemory();
            status.offShift();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //  16.06.2017
            status.offHyp();
            status.offShift();
            inDevelopToast.show();
        }
    }


    private void button04() throws MyExceptions {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            //  4
            editX.add('4');
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            // 
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            //  pi
            status.offShift();
            activity.currency_rate("USD");
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //  16.06.2017
            status.offHyp();
            status.offShift();
            inDevelopToast.show();
        }
    }

    private void button04Oct() {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            //  4
            editXOct.add('4');
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            //
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            //
            status.offShift();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //  16.06.2017
            status.offHyp();
            status.offShift();
            inDevelopToast.show();
        }
    }

    private void button04Hex() {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            //  4
            editXHex.add('4');
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            //
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            //
            status.offShift();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //  16.06.2017
            status.offHyp();
            status.offShift();
            inDevelopToast.show();
        }
    }

    private void button05() {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            // 5
            editX.add('5');
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            // 
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF

            status.offShift();
            activity.currency_rate("EUR");
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //  16.06.2017
            status.offHyp();
            status.offShift();
            inDevelopToast.show();
        }
    }

    private void button05Oct() {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            // 5
            editXOct.add('5');
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            //
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF

            status.offShift();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //  16.06.2017
            status.offHyp();
            status.offShift();
            inDevelopToast.show();
        }
    }

    private void button05Hex() {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            // 5
            editXHex.add('5');
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            //
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF

            status.offShift();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //  16.06.2017
            status.offHyp();
            status.offShift();
            inDevelopToast.show();
        }
    }

    private void button06() {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            //  6
            editX.add('6');
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            // 
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            // 
            status.offShift();
            activity.bitcoin_rate("BTC_USD");
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //  16.06.2017
            status.offHyp();
            status.offShift();
            inDevelopToast.show();
        }
    }

    private void button06Oct() {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            //  6
            editXOct.add('6');
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            //
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            //
            status.offShift();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //  16.06.2017
            status.offHyp();
            status.offShift();
            inDevelopToast.show();
        }
    }

    private void button06Hex() {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            //  6
            editXHex.add('6');
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            //
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            //
            status.offShift();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //  16.06.2017
            status.offHyp();
            status.offShift();
            inDevelopToast.show();
        }
    }

    private void buttonMult() throws MyExceptions {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            //  Mult
            editX.mult();
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            //
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            //  OCT
            editX.toOct();
            mode.setOCT();
            editXOct = (EditXOct) objStore[MainActivity.EDIT_X_OCT];
            status.offShift();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //  16.06.2017
            status.offHyp();
            status.offShift();
            inDevelopToast.show();
        }
    }

    private void buttonMultBin() throws MyExceptions {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            //  Mult
            editXBin.mult();
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            //
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            //  OCT
            editXBin.toOct();
            mode.setOCT();
            editXOct = (EditXOct) objStore[MainActivity.EDIT_X_OCT];
            status.offShift();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //  16.06.2017
            status.offHyp();
            status.offShift();
            inDevelopToast.show();
        }
    }

    private void buttonMultOct() throws MyExceptions {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            //  Mult
            editXOct.mult();
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            //
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            //  OCT
            status.offShift();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //  16.06.2017
            status.offHyp();
            status.offShift();
            inDevelopToast.show();
        }
    }

    private void buttonMultHex() throws MyExceptions {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            //  Mult
            editXHex.mult();
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            //
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            //  OCT
            editXHex.toOct();
            mode.setOCT();
            editXOct = (EditXOct) objStore[MainActivity.EDIT_X_OCT];
            status.offShift();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //  16.06.2017
            status.offHyp();
            status.offShift();
            inDevelopToast.show();
        }
    }

    private void buttonDiv() throws MyExceptions {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            //  Div
            editX.div();
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            // 
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            //  BIN
            editX.toBin();
            mode.setBIN();
            editXBin = (EditXBin) objStore[MainActivity.EDIT_X_BIN];
            status.offShift();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //  16.06.2017
            status.offHyp();
            status.offShift();
            inDevelopToast.show();
        }
    }

    private void buttonDivBin() throws MyExceptions {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            //  Div
            editXBin.div();
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            //
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            //  BIN
            status.offShift();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //  16.06.2017
            status.offHyp();
            status.offShift();
            inDevelopToast.show();
        }
    }


    private void buttonDivOct() throws MyExceptions {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            //  Div
            editXOct.div();
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            //
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            //  BIN
            editXOct.toBin();
            mode.setBIN();
            editXBin = (EditXBin) objStore[MainActivity.EDIT_X_BIN];
            status.offShift();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //  16.06.2017
            status.offHyp();
            status.offShift();
            inDevelopToast.show();
        }
    }

    private void buttonDivHex() throws MyExceptions {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            //  Div
            editXHex.div();
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            //
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            //  BIN
            editXHex.toBin();
            mode.setBIN();
            editXBin = (EditXBin) objStore[MainActivity.EDIT_X_BIN];
            status.offShift();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //  16.06.2017
            status.offHyp();
            status.offShift();
            inDevelopToast.show();
        }
    }

    private void button01() throws MyExceptions {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            // 1
            editX.add('1');
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            // 
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            //
//            editX.random();
            editX.dbm_to_w();
            status.offShift();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //  16.06.2017
            status.offHyp();
            status.offShift();
            inDevelopToast.show();
        }
    }

    private void button01Bin() {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            // 1
            editXBin.add('1');
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            //
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            //
            status.offShift();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //  16.06.2017
            status.offHyp();
            status.offShift();
            inDevelopToast.show();
        }
    }

    private void button01Oct() {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            // 1
            editXOct.add('1');
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            //
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            //
            status.offShift();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //  16.06.2017
            status.offHyp();
            status.offShift();
            inDevelopToast.show();
        }
    }

    private void button01Hex() {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            // 1
            editXHex.add('1');
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            //
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            //
            status.offShift();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //  16.06.2017
            status.offHyp();
            status.offShift();
            inDevelopToast.show();
        }
    }

    private void button02() {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            //  2
            editX.add('2');
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            // 
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            // 
            status.offShift();
            editX.w_to_dbm();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //  16.06.2017
            status.offHyp();
            status.offShift();
            inDevelopToast.show();
        }
    }

    private void button02Oct() {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            //  2
            editXOct.add('2');
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            //
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            //
            status.offShift();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //  16.06.2017
            status.offHyp();
            status.offShift();
            inDevelopToast.show();
        }
    }

    private void button02Hex() {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            //  2
            editXHex.add('2');
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            //
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            //
            status.offShift();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //  16.06.2017
            status.offHyp();
            status.offShift();
            inDevelopToast.show();
        }
    }

    private void button03() {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            //  3
            editX.add('3');
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            // 
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            // 
            status.offShift();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //  16.06.2017
            status.offHyp();
            status.offShift();
            inDevelopToast.show();
        }
    }

    private void button03Oct() {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            //  3
            editXOct.add('3');
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            //
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            //
            status.offShift();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //  16.06.2017
            status.offHyp();
            status.offShift();
            inDevelopToast.show();
        }
    }

    private void button03Hex() {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            //  3
            editXHex.add('3');
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            //
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            //
            status.offShift();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //  16.06.2017
            status.offHyp();
            status.offShift();
            inDevelopToast.show();
        }
    }

    private void buttonPlus() throws MyExceptions {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            //  Plus
            editX.plus();
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            // 
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            //  DEC
            status.offShift();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //  16.06.2017
            status.offHyp();
            status.offShift();
            inDevelopToast.show();
        }
    }


    private void buttonPlusBin() throws MyExceptions {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            //  Plus
            editXBin.plus();
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            //
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            //  DEC
            editXBin.toDec();
            mode.setDEC();
            status.offShift();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //  16.06.2017
            status.offHyp();
            status.offShift();
            inDevelopToast.show();
        }
    }

    private void buttonPlusOct() throws MyExceptions {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            //  Plus
            editXOct.plus();
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            //
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            //  DEC
            editXOct.toDec();
            mode.setDEC();
            status.offShift();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //  16.06.2017
            status.offHyp();
            status.offShift();
            inDevelopToast.show();
        }
    }

    private void buttonPlusHex() throws MyExceptions {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            //  Plus
            editXHex.plus();
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            //
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            //  DEC
            editXHex.toDec();
            mode.setDEC();
            status.offShift();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //  16.06.2017
            status.offHyp();
            status.offShift();
            inDevelopToast.show();
        }
    }

    private void buttonMinus() throws MyExceptions {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            //Minus
            editX.minus();
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            // 
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            //  HEX
            editX.toHex();
            mode.setHEX();
            editXHex = (EditXHex) objStore[MainActivity.EDIT_X_HEX];
            status.offShift();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //  16.06.2017
            status.offHyp();
            status.offShift();
            inDevelopToast.show();
        }
    }

    private void buttonMinusBin() throws MyExceptions {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            //Minus
            editXBin.minus();
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            //
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            //  HEX
            editXBin.toHex();
            mode.setHEX();
            editXHex = (EditXHex) objStore[MainActivity.EDIT_X_HEX];
            status.offShift();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //  16.06.2017
            status.offHyp();
            status.offShift();
            inDevelopToast.show();
        }
    }

    private void buttonMinusOct() throws MyExceptions {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            //Minus
            editXOct.minus();
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            //
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            //  HEX
            editXOct.toHex();
            mode.setHEX();
            editXHex = (EditXHex) objStore[MainActivity.EDIT_X_HEX];
            status.offShift();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //  16.06.2017
            status.offHyp();
            status.offShift();
            inDevelopToast.show();
        }
    }

    private void buttonMinusHex() throws MyExceptions {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            //Minus
            editXHex.minus();
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            //
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            //  HEX
            status.offShift();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //  16.06.2017
            status.offHyp();
            status.offShift();
            inDevelopToast.show();
        }
    }

    private void button00() throws MyExceptions {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            //
            editX.add('0');
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            // 
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            // 
            editX.minValue();
            status.offShift();
//            inDevelopToast.show();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //  16.06.2017
            status.offHyp();
            status.offShift();
            inDevelopToast.show();
        }
    }

    private void button00Bin() throws MyExceptions {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            //
            editXBin.add('0');
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            //
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            //
            status.offShift();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //  16.06.2017
            status.offHyp();
            status.offShift();
            inDevelopToast.show();
        }
    }

    private void button00Oct() throws MyExceptions {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            //
            editXOct.add('0');
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            //
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            //
            status.offShift();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //  16.06.2017
            status.offHyp();
            status.offShift();
            inDevelopToast.show();
        }
    }

    private void button00Hex() throws MyExceptions {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            //
            editXHex.add('0');
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            //
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            //
            status.offShift();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //  16.06.2017
            status.offHyp();
            status.offShift();
            inDevelopToast.show();
        }
    }

    private void buttonDot() throws MyExceptions {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            //  dot
            editX.dot();
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            // 
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            // rnd
            editX.random();
            status.offShift();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //  16.06.2017
            status.offHyp();
            status.offShift();
            inDevelopToast.show();
        }
    }

    private void buttonSign() {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            // 
            editX.sign();
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            // 
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            // 
            status.offShift();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //  16.06.2017
            status.offHyp();
            status.offShift();
            inDevelopToast.show();
        }
    }

    private void buttonSignBin() {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            //
            editXBin.sign();
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            //
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            //
            status.offShift();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //  16.06.2017
            status.offHyp();
            status.offShift();
            inDevelopToast.show();
        }
    }

    private void buttonSignOct() {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            //
            editXOct.sign();
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            //
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            //
            status.offShift();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //  16.06.2017
            status.offHyp();
            status.offShift();
            inDevelopToast.show();
        }
    }

    private void buttonSignHex() {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            //
            editXHex.sign();
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            //
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            //
            status.offShift();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //  16.06.2017
            status.offHyp();
            status.offShift();
            inDevelopToast.show();
        }
    }

    private void buttonCalc() throws MyExceptions {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            //=
            editX.calc();
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            // 
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            // %
            editX.percent();
            status.offShift();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //  16.06.2017
            status.offHyp();
            status.offShift();
            inDevelopToast.show();
        }
    }

    private void buttonCalcBin() throws MyExceptions {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            //=
            editXBin.calc();
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            //
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            // %
            status.offShift();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //  16.06.2017
            status.offHyp();
            status.offShift();
            inDevelopToast.show();
        }
    }

    private void buttonCalcOct() throws MyExceptions {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            //=
            editXOct.calc();
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            //
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            // %
            status.offShift();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //  16.06.2017
            status.offHyp();
            status.offShift();
            inDevelopToast.show();
        }
    }

    private void buttonCalcHex() throws MyExceptions {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            //=
            editXHex.calc();
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            //
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            // %
            status.offShift();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //  16.06.2017
            status.offHyp();
            status.offShift();
            inDevelopToast.show();
        }
    }

    private void buttonCE() throws MyExceptions {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            // CE
            editX.ce();
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            //  16.06.2017
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            //x!
            editX.factorial();
            status.offShift();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //  16.06.2017
            status.offHyp();
            status.offShift();
            inDevelopToast.show();
        }
    }

    private void buttonCEBin() throws MyExceptions {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            // CE
            editXBin.ce();
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            //  16.06.2017
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            //x!
            status.offShift();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //  16.06.2017
            status.offHyp();
            status.offShift();
            inDevelopToast.show();
        }
    }

    private void buttonCEOct() throws MyExceptions {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            // CE
            editXOct.ce();
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            //  16.06.2017
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            //x!
            status.offShift();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //  16.06.2017
            status.offHyp();
            status.offShift();
            inDevelopToast.show();
        }
    }

    private void buttonCEHex() throws MyExceptions {
        if (!status.isShift() & !status.isHyp()) { // SHIFT OFF & HYP OFF
            // CE
            editXHex.ce();
            return;
        }
        if (!status.isShift() & status.isHyp()) { // SHIFT OFF & HYP ON
            //  16.06.2017
            status.offHyp();
            inDevelopToast.show();
            return;
        }
        if (status.isShift() & !status.isHyp()) { // SHIFT ON & HYP OFF
            //x!
            status.offShift();
            return;
        }
        if (status.isShift() & status.isHyp()) { // SHIFT ON & HYP ON
            //  16.06.2017
            status.offHyp();
            status.offShift();
            inDevelopToast.show();
        }
    }

    StringBuilder copyToClipboard() {
        StringBuilder sb = new StringBuilder("");
        switch (mode.getMode()) {
            case (Mode.DEC):
            case (Mode.SD):
            case (Mode.COMPLEX):
                sb.append(editX.copyToClipboard());
                break;
            case (Mode.BIN):
                sb.append(editXBin.copyToClipboard());
                break;
            case (Mode.OCT):
                sb.append(editXOct.copyToClipboard());
                break;
            case (Mode.HEX):
                sb.append(editXHex.copyToClipboard());
                break;
        }
        return sb;
    }

    void pasteFromClipboard(String str) {
        switch (mode.getMode()) {
            case (Mode.DEC):
            case (Mode.SD):
            case (Mode.COMPLEX):
                editX.pasteFromClipboard(str);
                break;
            case (Mode.BIN):
                editXBin.pasteFromClipboard(str);
                break;
            case (Mode.OCT):
                editXOct.pasteFromClipboard(str);
                break;
            case (Mode.HEX):
                editXHex.pasteFromClipboard(str);
                break;
        }
    }


    @Override
    public void onClick(View view) {
        activity.findViewById(view.getId()).performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        try {
            switch (view.getId()) {
                case (R.id.FuncButtonShift):
                    buttonShift();
                    break;
                case (R.id.FuncButtonHyp):
                    buttonHyp();
                    break;
                case (R.id.FuncButtonOff):
                    buttonOff();
                    break;
            }
            switch (mode.getMode()) {
                case (Mode.DEC):
                case (Mode.SD):
                case (Mode.COMPLEX):
                    switch (view.getId()) {
                        case (R.id.FuncButtonGear):
                            buttonGear();
                            break;
                        case (R.id.FuncButtonOnC):
                            buttonOnC();
                            break;
                        case (R.id.FuncButtonDel):
                            buttonDel();
                            break;
                        case (R.id.FuncButtonSci):
                            buttonSci();
                            break;
                        case (R.id.FuncButtonDrg):
                            buttonDrg();
                            break;
                        case (R.id.FuncButtonSin):
                            buttonSin();
                            break;
                        case (R.id.FuncButtonCos):
                            buttonCos();
                            break;
                        case (R.id.FuncButtonTan):
                            buttonTan();
                            break;
                        case (R.id.FuncButtonExp):
                            buttonExp();
                            break;
                        case (R.id.FuncButtonXpowY):
                            buttonXpowY();
                            break;
                        case (R.id.FuncButtonSqr):
                            buttonSqr();
                            break;
                        case (R.id.FuncButtonToRad):
                            buttonToRad();
                            break;
                        case (R.id.FuncButtonLn):
                            buttonLn();
                            break;
                        case (R.id.FuncButtonLog):
                            buttonLog();
                            break;
                        case (R.id.FuncButtonX2):
                            buttonX2();
                            break;
                        case (R.id.FuncButtonA):
                            buttonA();
                            break;
                        case (R.id.FuncButtonB):
                            buttonB();
                            break;
                        case (R.id.FuncButtonOpenBracket):
                            buttonOpenBracket();
                            break;
                        case (R.id.FuncButtonCloseBracket):
                            buttonCloseBracket();
                            break;
                        case (R.id.FuncButtonXtoM):
                            buttonXtoM();
                            break;
                        case (R.id.Button07):
                            button07();
                            break;
                        case (R.id.Button08):
                            button08();
                            break;
                        case (R.id.Button09):
                            button09();
                            break;
                        case (R.id.ButtonMem):
                            buttonMem();
                            break;
                        case (R.id.ButtonMR):
                            buttonMR();
                            break;
                        case (R.id.Button04):
                            button04();
                            break;
                        case (R.id.Button05):
                            button05();
                            break;
                        case (R.id.Button06):
                            button06();
                            break;
                        case (R.id.ButtonMult):
                            buttonMult();
                            break;
                        case (R.id.ButtonDiv):
                            buttonDiv();
                            break;
                        case (R.id.Button01):
                            button01();
                            break;
                        case (R.id.Button02):
                            button02();
                            break;
                        case (R.id.Button03):
                            button03();
                            break;
                        case (R.id.ButtonPlus):
                            buttonPlus();
                            break;
                        case (R.id.ButtonMinus):
                            buttonMinus();
                            break;
                        case (R.id.Button0):
                            button00();
                            break;
                        case (R.id.ButtonDot):
                            buttonDot();
                            break;
                        case (R.id.ButtonSign):
                            buttonSign();
                            break;
                        case (R.id.ButtonCalc):
                            buttonCalc();
                            break;
                        case (R.id.ButtonCE):
                            buttonCE();
                            break;
                    }
                    break;
                case (Mode.BIN):
                    switch (view.getId()) {
                        case (R.id.FuncButtonOnC):
                            buttonOnCBin();
                            break;
                        case (R.id.FuncButtonDel):
                            buttonDelBin();
                            break;
                        case (R.id.FuncButtonDrg):
                            buttonDrgBin();
                            break;
                        case (R.id.FuncButtonXtoM):
                            buttonXtoMBin();
                            break;
                        case (R.id.ButtonMem):
                            buttonMemBin();
                            break;
                        case (R.id.ButtonMR):
                            buttonMRBin();
                            break;
                        case (R.id.ButtonMult):
                            buttonMultBin();
                            break;
                        case (R.id.ButtonDiv):
                            buttonDivBin();
                            break;
                        case (R.id.Button01):
                            button01Bin();
                            break;
                        case (R.id.ButtonPlus):
                            buttonPlusBin();
                            break;
                        case (R.id.ButtonMinus):
                            buttonMinusBin();
                            break;
                        case (R.id.Button0):
                            button00Bin();
                            break;
                        case (R.id.ButtonSign):
                            buttonSignBin();
                            break;
                        case (R.id.ButtonCalc):
                            buttonCalcBin();
                            break;
                        case (R.id.ButtonCE):
                            buttonCEBin();
                            break;
                        case (R.id.FuncButtonOpenBracket):
                            buttonOpenBracketBin();
                            break;
                    }
                    break;
                case (Mode.OCT):
                    switch (view.getId()) {
                        case (R.id.FuncButtonOnC):
                            buttonOnCOct();
                            break;
                        case (R.id.FuncButtonDel):
                            buttonDelOct();
                            break;
                        case (R.id.FuncButtonDrg):
                            buttonDrgOct();
                            break;
                        case (R.id.FuncButtonXtoM):
                            buttonXtoMOct();
                            break;
                        case (R.id.Button07):
                            button07Oct();
                            break;
                        case (R.id.ButtonMem):
                            buttonMemOct();
                            break;
                        case (R.id.ButtonMR):
                            buttonMROct();
                            break;
                        case (R.id.Button04):
                            button04Oct();
                            break;
                        case (R.id.Button05):
                            button05Oct();
                            break;
                        case (R.id.Button06):
                            button06Oct();
                            break;
                        case (R.id.ButtonMult):
                            buttonMultOct();
                            break;
                        case (R.id.ButtonDiv):
                            buttonDivOct();
                            break;
                        case (R.id.Button01):
                            button01Oct();
                            break;
                        case (R.id.Button02):
                            button02Oct();
                            break;
                        case (R.id.Button03):
                            button03Oct();
                            break;
                        case (R.id.ButtonPlus):
                            buttonPlusOct();
                            break;
                        case (R.id.ButtonMinus):
                            buttonMinusOct();
                            break;
                        case (R.id.Button0):
                            button00Oct();
                            break;
                        case (R.id.ButtonSign):
                            buttonSignOct();
                            break;
                        case (R.id.ButtonCalc):
                            buttonCalcOct();
                            break;
                        case (R.id.ButtonCE):
                            buttonCEOct();
                            break;
                        case (R.id.FuncButtonOpenBracket):
                            buttonOpenBracketOct();
                            break;
                    }
                    break;
                case (Mode.HEX):
                    switch (view.getId()) {
                        case (R.id.FuncButtonOnC):
                            buttonOnCHex();
                            break;
                        case (R.id.FuncButtonDel):
                            buttonDelHex();
                            break;
                        case (R.id.FuncButtonDrg):
                            buttonDrgHex();
                            break;
                        case (R.id.FuncButtonExp):
                            buttonExpHex();
                            break;
                        case (R.id.FuncButtonXpowY):
                            buttonXpowYHex();
                            break;
                        case (R.id.FuncButtonSqr):
                            buttonSqrHex();
                            break;
                        case (R.id.FuncButtonToRad):
                            buttonToRadHex();
                            break;
                        case (R.id.FuncButtonLn):
                            buttonLnHex();
                            break;
                        case (R.id.FuncButtonLog):
                            buttonLogHex();
                            break;
                        case (R.id.FuncButtonXtoM):
                            buttonXtoMHex();
                            break;
                        case (R.id.Button07):
                            button07Hex();
                            break;
                        case (R.id.Button08):
                            button08Hex();
                            break;
                        case (R.id.Button09):
                            button09Hex();
                            break;
                        case (R.id.ButtonMem):
                            buttonMemHex();
                            break;
                        case (R.id.ButtonMR):
                            buttonMRHex();
                            break;
                        case (R.id.Button04):
                            button04Hex();
                            break;
                        case (R.id.Button05):
                            button05Hex();
                            break;
                        case (R.id.Button06):
                            button06Hex();
                            break;
                        case (R.id.ButtonMult):
                            buttonMultHex();
                            break;
                        case (R.id.ButtonDiv):
                            buttonDivHex();
                            break;
                        case (R.id.Button01):
                            button01Hex();
                            break;
                        case (R.id.Button02):
                            button02Hex();
                            break;
                        case (R.id.Button03):
                            button03Hex();
                            break;
                        case (R.id.ButtonPlus):
                            buttonPlusHex();
                            break;
                        case (R.id.ButtonMinus):
                            buttonMinusHex();
                            break;
                        case (R.id.Button0):
                            button00Hex();
                            break;
                        case (R.id.ButtonSign):
                            buttonSignHex();
                            break;
                        case (R.id.ButtonCalc):
                            buttonCalcHex();
                            break;
                        case (R.id.ButtonCE):
                            buttonCEHex();
                            break;
                        case (R.id.FuncButtonOpenBracket):
                            buttonOpenBracketHex();
                            break;
                    }
                    break;
            }


        } catch (MyExceptions e) {
            status.onError();
//            inDevelopToast.setToastText("Произошло пользовательское арифметическое исключение. " + e.getReason());
            inDevelopToast.setToastText(e.getReason() + e.getMsg());
            inDevelopToast.show();
            L.d(TAG, "Произошло пользовательское арифметическое исключение. " + e.getReason());
            status.offShift();
            status.offHyp();
            status.onError();
        } catch (ArithmeticException e) {
            inDevelopToast.setToastText("Произошло арифметическое исключение. " + e);
            inDevelopToast.show();
            L.d(TAG, "Произошло арифметическое исключение. " + e);
            status.offShift();
            status.offHyp();
            status.onError();
        } catch (NumberFormatException e) {


            e.getMessage().substring(e.getMessage().indexOf('"') + 1, e.getMessage().lastIndexOf('.'));
            switch (e.getMessage().substring(e.getMessage().indexOf('"') + 1, e.getMessage().lastIndexOf('.'))) {
                case ("-Infinity"):
                    inDevelopToast.setToastText("Произошла ошибка формата числа. -Infinity");
                    break;
                case ("Infinity"):
                    inDevelopToast.setToastText("Произошла ошибка формата числа. Infinity");
                    break;
                case ("NaN"):
                    inDevelopToast.setToastText("Произошла ошибка формата числа. NaN");
                    break;
            }
//            inDevelopToast.setToastText("Произошла ошибка формата числа. " + e.getMessage().substring(e.getMessage().indexOf('"') + 1, e.getMessage().lastIndexOf('.')));
            inDevelopToast.show();
            L.d(TAG, "Произошла ошибка формата числа. " + e.getMessage().substring(e.getMessage().indexOf('"') + 1, e.getMessage().lastIndexOf('.')));

            status.offShift();
            status.offHyp();
            status.onError();
        } catch (Exception e) {
            inDevelopToast.setToastText("Произошла неизвестная ошибка Exception. " + e);
            inDevelopToast.show();
            L.d(TAG, "Произошла неизвестная ошибка. " + e);

            StackTraceElement[] stackTraceElements = e.getStackTrace();

            for (int i = 0; i < stackTraceElements.length; i++) {
                L.d(TAG, i + ": " + stackTraceElements[i].toString());
            }
            status.offShift();
            status.offHyp();
            status.onError();

        } catch (Throwable t) {
            inDevelopToast.setToastText("Произошла неизвестная ошибка Throwable. " + t);
            inDevelopToast.show();
        }
//        finally {
//
//            status.offShift();
//            status.offHyp();
//            status.onError();
//        }
    }


}
