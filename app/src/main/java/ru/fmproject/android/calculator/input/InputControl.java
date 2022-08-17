package ru.fmproject.android.calculator.input;

import android.view.HapticFeedbackConstants;
import android.view.View;

import ru.fmproject.android.calculator.Angle;
import ru.fmproject.android.calculator.CustomToast;
import ru.fmproject.android.calculator.Mode;
import ru.fmproject.android.calculator.Protocol;
import ru.fmproject.android.calculator.StatisticMode;
import ru.fmproject.android.calculator.editors.EditX;
import ru.fmproject.android.calculator.editors.EditXBin;
import ru.fmproject.android.calculator.editors.EditXDec;
import ru.fmproject.android.calculator.L;
import ru.fmproject.android.calculator.MainActivity;
import ru.fmproject.android.calculator.MyExceptions;
import ru.fmproject.android.calculator.R;
import ru.fmproject.android.calculator.Status;
import ru.fmproject.android.calculator.editors.EditXHex;
import ru.fmproject.android.calculator.editors.EditXOct;


/**
 * Класс InputDriver это связующее звено между внешним миром и внутренним устройством.
 * Объекту этого класса передаются ссылки на объекты которым будут передаваться действия пользователя
 * Этот класс нужно переписывать/редактировать заново для каждого нового устройства
 */

public class InputControl implements View.OnClickListener {

    private static final String TAG = "InputControl";

    private final Object[] objStore;
    private final CustomToast inDevelopToast;
    private final Status status;
    private final MainActivity activity;
    private final Mode mode;
    private final Angle angle;
    private StatisticMode statisticMode;
    private final Protocol protocol;

    private EditX editX;
    private EditXDec editXDec;
    private EditXBin editXBin;
    private EditXOct editXOct;
    private EditXHex editXHex;

    private InputStrategy inputStrategy;
    private BinInputStrategy binInputStrategy;
    private DecInputStrategy decInputStrategy;
    private HexInputStrategy hexInputStrategy;
    private HypDecInputStrategy hypDecInputStrategy;
    private OctInputStrategy octInputStrategy;
    private ShiftBinInputStrategy shiftBinInputStrategy;
    private ShiftDecInputStrategy shiftDecInputStrategy;
    private ShiftHexInputStrategy shiftHexInputStrategy;
    private ShiftHypDecInputStrategy shiftHypDecInputStrategy;
    private ShiftOctInputStrategy shiftOctInputStrategy;
    private ShiftStatisticInputStrategy shiftStatisticInputStrategy;
    private StatisticInputStrategy statisticInputStrategy;

    public InputControl(Object[] objStore) {
        this.objStore = objStore;
        activity = (MainActivity) objStore[MainActivity.MAIN_ACTIVITY];
        mode = (Mode) objStore[MainActivity.MODE];
        status = (Status) objStore[MainActivity.STATUS];
        angle = (Angle) objStore[MainActivity.ANGLE];
        statisticMode = (StatisticMode) objStore[MainActivity.SD];
        protocol = (Protocol) objStore[MainActivity.PROTOCOL];
        editXDec = (EditXDec) objStore[MainActivity.EDIT_X_DEC];
        editX = editXDec;
        decInputStrategy = new DecInputStrategy(editXDec);
        inputStrategy = decInputStrategy;
        for (int i = 0; i < objStore.length; i++) {
            L.d(TAG, "В objStore[" + i + "]: " + objStore[i]);
        }
        inDevelopToast = new CustomToast(activity, "В разработке");
        L.d(TAG, "Создали новый InputControl");
    }

    private void buttonShift() {
        status.switchShift();
        inputStrategy = strategySelector();
    }

    private void buttonHyp() {
        status.switchHyp();
        inputStrategy = strategySelector();
    }

    InputStrategy strategySelector() {
        if (status.isShift()) {// Если SHIFT включен
            if (status.isHyp()) {// Если Hyp включен
                return shiftHypStrategySelector();
            } else {// Если Hyp выключен
                return shiftStrategySelector();
            }
        } else {// Если SHIFT выключен
            if (status.isHyp()) {// Если Hyp включен
                return hypStrategySelector();
            } else {// Если Hyp выключен
                return commonStrategySelector();
            }
        }
    }

    private void buttonOff() {
        activity.finish();
    }

    private void buttonOnC() {
        if (status.isShift()) {// Если SHIFT включен
            if (status.isHyp()) {// Если Hyp включен
                //
                status.offHyp();
                status.offShift();
            } else {// Если Hyp выключен
                status.offShift();
                mode.switchSD();
                if (mode.getMode() == Mode.SD) {
                    if (statisticInputStrategy == null) {
                        statisticInputStrategy = new StatisticInputStrategy(editXDec);
                    }
                    inputStrategy = statisticInputStrategy;
                    editXDec.startSDMode();
                } else {
                    inputStrategy = decInputStrategy;
                    editXDec.startSDMode();
                }
            }
        } else {// Если SHIFT выключен
            if (status.isHyp()) {// Если Hyp включен
                //
                status.offHyp();
            } else {// Если Hyp выключен
                //  ON_C
                editX.restart();
            }
        }
    }


    public StringBuilder copyToClipboard() {
        return editX.copyToClipboard();
    }

    public void pasteFromClipboard(String str) {
        editX.pasteFromClipboard(str);
    }

    @Override
    public void onClick(View view) {
        activity.findViewById(view.getId()).performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        int viewId = view.getId();
        if (viewId == R.id.FuncButtonShift) {//Если нажали Shift
            buttonShift();
            return;
        }
        if (viewId == R.id.FuncButtonHyp) {//Если нажали Hyp
            buttonHyp();
            return;
        }
        if (viewId == R.id.FuncButtonOnC) {//Если нажали OnC
            buttonOnC();
            return;
        }

        if (status.isShift() && !status.isHyp()) {// Если SHIFT включен, а Hyp выключен
            double editXNumber = editX.getNumber();//забираем число из текущего редактора
            switch (viewId) {
                case (R.id.ButtonPlus)://Включаем режим DEC и соответствующую стратегию
                    mode.setDEC();
                    if (editXDec == null) {
                        editXDec = new EditXDec(objStore);
                    }
                    editX = editXDec;
                    if (decInputStrategy == null) {
                        decInputStrategy = new DecInputStrategy(editXDec);
                    }
                    inputStrategy = decInputStrategy;
                    status.offShift();
                    editX.setNumber(editXNumber);
                    return;
                case (R.id.ButtonMinus)://Включаем режим HEX и соответствующую стратегию
                    mode.setHEX();
                    if (editXHex == null) {
                        editXHex = new EditXHex(objStore);
                    }
                    editX = editXHex;
                    if (hexInputStrategy == null) {
                        hexInputStrategy = new HexInputStrategy(editXHex);
                    }
                    inputStrategy = hexInputStrategy;
                    status.offShift();
                    editX.setNumber(editXNumber);
                    return;
                case (R.id.ButtonMult)://Включаем режим OCT и соответствующую стратегию
                    mode.setOCT();
                    if (editXOct == null) {
                        editXOct = new EditXOct(objStore);
                    }
                    editX = editXOct;
                    if (octInputStrategy == null) {
                        octInputStrategy = new OctInputStrategy(editXOct);
                    }
                    inputStrategy = octInputStrategy;
                    status.offShift();
                    editX.setNumber(editXNumber);
                    return;
                case (R.id.ButtonDiv)://Включаем режим BIN и соответствующую стратегию
                    mode.setBIN();
                    if (editXBin == null) {
                        editXBin = new EditXBin(objStore);
                    }
                    editX = editXBin;
                    if (binInputStrategy == null) {
                        binInputStrategy = new BinInputStrategy(editXBin);
                    }
                    inputStrategy = binInputStrategy;
                    status.offShift();
                    editX.setNumber(editXNumber);
                    return;
                case (R.id.FuncButtonDel)://Включаем режим CPLX
                    mode.switchCplx();
                    if (mode.getMode() == Mode.COMPLEX) {
                        inputStrategy = decInputStrategy;
                        editXDec.startCplxMode();
                    } else {
                        inputStrategy = decInputStrategy;
                    }
                    inputStrategy = decInputStrategy;
                    status.offShift();
                    editX.setNumber(editXNumber);
                    return;
            }
        }

        if (!status.isShift() && !status.isHyp() && viewId == R.id.FuncButtonDrg) {// Если SHIFT выключен, Hyp выключен и нажата кнопка DRG
            angle.switchAngleUnit();//Переключаем еденицы измерения углов
            return;
        }

        try {
            switch (viewId) {
                case (R.id.FuncButtonOff):
                    buttonOff();
                    break;
                case (R.id.FuncButtonDel):
                    inputStrategy.buttonDel();
                    break;
                case (R.id.FuncButtonSci):
                    inputStrategy.buttonSci();
                    break;
                case (R.id.FuncButtonDrg):
                    inputStrategy.buttonDrg();
                    break;
                case (R.id.FuncButtonSin):
                    inputStrategy.buttonSin();
                    break;
                case (R.id.FuncButtonCos):
                    inputStrategy.buttonCos();
                    break;
                case (R.id.FuncButtonTan):
                    inputStrategy.buttonTan();
                    break;
                case (R.id.FuncButtonExp):
                    inputStrategy.buttonExp();
                    break;
                case (R.id.FuncButtonXpowY):
                    inputStrategy.buttonXpowY();
                    break;
                case (R.id.FuncButtonSqr):
                    inputStrategy.buttonSqr();
                    break;
                case (R.id.FuncButtonToRad):
                    inputStrategy.buttonToRad();
                    break;
                case (R.id.FuncButtonLn):
                    inputStrategy.buttonLn();
                    break;
                case (R.id.FuncButtonLog):
                    inputStrategy.buttonLog();
                    break;
                case (R.id.FuncButtonX2):
                    inputStrategy.buttonX2();
                    break;
                case (R.id.FuncButtonA):
                    inputStrategy.buttonA();
                    break;
                case (R.id.FuncButtonB):
                    inputStrategy.buttonB();
                    break;
                case (R.id.FuncButtonOpenBracket):
                    inputStrategy.buttonOpenBracket();
                    break;
                case (R.id.FuncButtonCloseBracket):
                    inputStrategy.buttonCloseBracket();
                    break;
                case (R.id.FuncButtonXtoM):
                    inputStrategy.buttonXtoM();
                    break;
                case (R.id.Button07):
                    inputStrategy.button07();
                    break;
                case (R.id.Button08):
                    inputStrategy.button08();
                    break;
                case (R.id.Button09):
                    inputStrategy.button09();
                    break;
                case (R.id.ButtonMem):
                    inputStrategy.buttonMem();
                    break;
                case (R.id.ButtonMR):
                    inputStrategy.buttonMR();
                    break;
                case (R.id.Button04):
                    inputStrategy.button04();
                    break;
                case (R.id.Button05):
                    inputStrategy.button05();
                    break;
                case (R.id.Button06):
                    inputStrategy.button06();
                    break;
                case (R.id.ButtonMult):
                    inputStrategy.buttonMult();
                    break;
                case (R.id.ButtonDiv):
                    inputStrategy.buttonDiv();
                    break;
                case (R.id.Button01):
                    inputStrategy.button01();
                    break;
                case (R.id.Button02):
                    inputStrategy.button02();
                    break;
                case (R.id.Button03):
                    inputStrategy.button03();
                    break;
                case (R.id.ButtonPlus):
                    inputStrategy.buttonPlus();
                    break;
                case (R.id.ButtonMinus):
                    inputStrategy.buttonMinus();
                    break;
                case (R.id.Button0):
                    inputStrategy.button00();
                    break;
                case (R.id.ButtonDot):
                    inputStrategy.buttonDot();
                    break;
                case (R.id.ButtonSign):
                    inputStrategy.buttonSign();
                    break;
                case (R.id.ButtonCalc):
                    inputStrategy.buttonCalc();
                    break;
                case (R.id.ButtonCE):
                    inputStrategy.buttonCE();
                    break;
            }
            status.offShift();
            status.offHyp();
            inputStrategy = commonStrategySelector();
        } catch (MyExceptions e) {
            status.onError();
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
    }

    InputStrategy shiftStrategySelector() {
        switch (mode.getMode()) {//Включаем стратегии c Shift, но без Hyp
            case Mode.BIN:
                if (shiftBinInputStrategy == null) {
                    shiftBinInputStrategy = new ShiftBinInputStrategy(editXBin);
                }
                return shiftBinInputStrategy;
            case Mode.DEC:
            case Mode.COMPLEX:
                if (shiftDecInputStrategy == null) {
                    shiftDecInputStrategy = new ShiftDecInputStrategy(editXDec);
                }
                return shiftDecInputStrategy;
            case Mode.HEX:
                if (shiftHexInputStrategy == null) {
                    shiftHexInputStrategy = new ShiftHexInputStrategy(editXHex);
                }
                return shiftHexInputStrategy;
            case Mode.OCT:
                if (shiftOctInputStrategy == null) {
                    shiftOctInputStrategy = new ShiftOctInputStrategy(editXOct);
                }
                return shiftOctInputStrategy;
            case Mode.SD:
                if (shiftStatisticInputStrategy == null) {
                    shiftStatisticInputStrategy = new ShiftStatisticInputStrategy(editXDec);
                }
                return shiftStatisticInputStrategy;

            default:
                throw new IllegalStateException("Unexpected value: " + mode.getMode());
        }
    }

    InputStrategy shiftHypStrategySelector() {
        switch (mode.getMode()) {//Включаем стратегии c Shift, и с Hyp
            case Mode.BIN:
                return shiftBinInputStrategy;
            case Mode.DEC:
                if (shiftHypDecInputStrategy == null) {
                    shiftHypDecInputStrategy = new ShiftHypDecInputStrategy(editXDec);
                }
                return shiftHypDecInputStrategy;
            case Mode.HEX:
                return shiftHexInputStrategy;
            case Mode.OCT:
                return shiftOctInputStrategy;
            case Mode.SD:
                return statisticInputStrategy;
            default:
                throw new IllegalStateException("Unexpected value: " + mode.getMode());
        }
    }

    InputStrategy commonStrategySelector() {
        switch (mode.getMode()) {//Включаем стратегии без Shift и Hyp
            case Mode.BIN:
                return binInputStrategy;
            case Mode.DEC:
            case Mode.COMPLEX:
                return decInputStrategy;
            case Mode.HEX:
                return hexInputStrategy;
            case Mode.OCT:
                return octInputStrategy;
            case Mode.SD:
                return statisticInputStrategy;
            default:
                throw new IllegalStateException("Unexpected value: " + mode.getMode());
        }
    }

    InputStrategy hypStrategySelector() {
        switch (mode.getMode()) {//Включаем стратегии без Shift, но с Hyp
            case Mode.BIN:
                return binInputStrategy;
            case Mode.DEC:
                if (hypDecInputStrategy == null) {
                    hypDecInputStrategy = new HypDecInputStrategy(editXDec);
                }
                return hypDecInputStrategy;
            case Mode.HEX:
                return hexInputStrategy;
            case Mode.OCT:
                return octInputStrategy;
            case Mode.SD:
                return statisticInputStrategy;
            default:
                throw new IllegalStateException("Unexpected value: " + mode.getMode());
        }
    }
}
