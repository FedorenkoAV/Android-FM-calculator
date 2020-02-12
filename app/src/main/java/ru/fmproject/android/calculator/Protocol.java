package ru.fmproject.android.calculator;

import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.TextView;
import org.apache.commons.math3.complex.ComplexFormat;
import org.apache.commons.math3.complex.Complex;
import java.util.List;

public class Protocol implements View.OnLongClickListener {

    private static final String TAG = "Protocol";

    public final int NOP2 = 0b000;
    public final int PLUS2 = 0b010;
    public final int MINUS2 = 0b011;
    public final int MULTIPLY2 = 0b100;
    public final int DIVIDE2 = 0b101;
    public final int POWER2 = 0b110;
    public final int X_SQR_Y2 = 0b111;

    Context context;
    private TextView tvProtocol;
    private FragmentManager manager;
    //    private AlertDialog.Builder builder;
    private MyDialogFragment myDialogFragment;

    private boolean doCls;

    private SpannableStringBuilder sbProtocol;

    private ComplexFormat complexFormat;
    Mode mode;

//    public Protocol(Context context, TextView tvProtocol, Mode mode) {
//        this.context = context;
//        this.tvProtocol = tvProtocol;
//        this.mode = mode;
//        tvProtocol.setOnLongClickListener(this);
//        sbProtocol = new StringBuilder("");
//        complexFormat = new ComplexFormat();
//        doCls = true;
//    }
//
//    public Protocol(MainActivity context, TextView tvProtocol, Mode mode, View adv) {
//        this.context = context;
//        this.tvProtocol = tvProtocol;
//        this.adv = adv;
//        this.mode = mode;
//        tvProtocol.setOnLongClickListener(this);
//        sbProtocol = new StringBuilder("");
//        complexFormat = new ComplexFormat();
//        doCls = true;
//    }

    Protocol(TextView tvProtocol, Mode mode, MyDialogFragment myDialogFragment, FragmentManager manager) {
        this.tvProtocol = tvProtocol;
        this.mode = mode;
        this.myDialogFragment = myDialogFragment;
        this.manager = manager;

        tvProtocol.setOnLongClickListener(this);
        clearProtocol();
        complexFormat = new ComplexFormat();
        doCls = true;

    }

    void cls() {
        tvProtocol.setText("");
    }

    void println() {
        if (doCls) {
            cls();
        }
        doCls = true;
    }

    void println(String msg) {
        if (doCls) {
            cls();
        }
        tvProtocol.append(msg);
        doCls = true;
        sbProtocol.append(msg).append("\n");
    }

    void println(int integerNum) {
        String printString = "";
        switch (mode.getMode()) {
            case Mode.BIN:
                printString = Integer.toBinaryString(integerNum);
                break;
            case Mode.HEX:
                printString = Integer.toHexString(integerNum);
                break;
            case Mode.OCT:
                printString = Integer.toOctalString(integerNum);
                break;
            case Mode.DEC:
                printString = Integer.toString(integerNum);
                break;
        }
        if (doCls) {
            cls();
        }
        tvProtocol.append(printString);
        doCls = true;
        sbProtocol.append(printString).append("\n");
    }

    void println(double doubleNum) {
        String printString = "";
        int integerNum = (int) doubleNum;
        switch (mode.getMode()) {
            case Mode.BIN:
                printString = Integer.toBinaryString(integerNum);
                break;
            case Mode.HEX:
                printString = Integer.toHexString(integerNum);
                break;
            case Mode.OCT:
                printString = Integer.toOctalString(integerNum);
                break;
            case Mode.DEC:
            case Mode.SD:
                printString = String.valueOf(doubleNum);
                break;
        }
        if (doCls) {
            cls();
        }
        tvProtocol.append(printString);
        doCls = true;
        sbProtocol.append(printString).append("\n");
    }

    void println(Complex complex) {
        if (doCls) {
            cls();
        }
        tvProtocol.append("(" + complexFormat.format(complex) + ")");
        doCls = true;
        sbProtocol.append("(").append(complexFormat.format(complex)).append(")\n");
    }

    void println(List cplxNumList) {
        if (doCls) {
            cls();
        }
        Complex complex = (Complex) cplxNumList.get(0);
        tvProtocol.append("(" + complexFormat.format(complex) + "), ...\n");
        doCls = false;

        for (int i = 0; i < cplxNumList.size(); i++) {
            complex = (Complex) cplxNumList.get(i);
            sbProtocol.append("\n(").append(complexFormat.format(complex)).append(")");
        }
        doCls = true;
        sbProtocol.append("\n");
    }




    void println(Object someObj) {
        if (doCls) {
            cls();
        }
        String strToPrint = someObj.toString();
        tvProtocol.append(strToPrint);
        doCls = true;
        sbProtocol.append(strToPrint).append("\n");
    }

    void print() {
        if (doCls) {
            cls();
        }
        doCls = false;
    }

    void print(String msg) {
        //protocol.jTextArea1.append(msg);
        if (doCls) {
            cls();
        }
        tvProtocol.append(msg);
        doCls = false;
        sbProtocol.append(msg);
    }

    void print(int integerNum) {
        String printString = "";
        switch (mode.getMode()) {
            case Mode.BIN:
                printString = Integer.toBinaryString(integerNum);
                break;
            case Mode.HEX:
                printString = Integer.toHexString(integerNum);
                break;
            case Mode.OCT:
                printString = Integer.toOctalString(integerNum);
                break;
            case Mode.DEC:
                printString = Integer.toString(integerNum);
                break;
        }
        if (doCls) {
            cls();
        }
        tvProtocol.append(printString);
        doCls = true;
        sbProtocol.append(printString);
    }

    void print(double doubleNum) {
        String printString = "";
        int integerNum = (int) doubleNum;
        switch (mode.getMode()) {
            case Mode.BIN:
                printString = Integer.toBinaryString(integerNum);
                break;
            case Mode.HEX:
                printString = Integer.toHexString(integerNum);
                break;
            case Mode.OCT:
                printString = Integer.toOctalString(integerNum);
                break;
            case Mode.DEC:
                printString = String.valueOf(doubleNum);
                break;
        }
        if (doCls) {
            cls();
        }
        tvProtocol.append(printString);
        doCls = true;
        sbProtocol.append(printString);
    }

    void print(Complex complex) {
        if (doCls) {
            cls();
        }
        tvProtocol.append("(" + complexFormat.format(complex) + ")");
        doCls = false;
        sbProtocol.append("(").append(complexFormat.format(complex)).append(")");
    }

    void print(List cplxNumList) {
        if (doCls) {
            cls();
        }
        Complex complex = (Complex) cplxNumList.get(0);
        tvProtocol.append("(" + complexFormat.format(complex) + ")");
        doCls = false;

        for (int i = 0; i < cplxNumList.size(); i++) {
            complex = (Complex) cplxNumList.get(i);
            sbProtocol.append("\n+(").append(complexFormat.format(complex)).append(")");
        }

    }

    void print(Object someObj) {
        if (doCls) {
            cls();
        }
        String strToPrint = someObj.toString();
        tvProtocol.append(strToPrint);
        doCls = false;
        sbProtocol.append(strToPrint);
    }

    @Override
    public boolean onLongClick(View v) {
        alertDialog();
        return false;
    }

    void alertDialog() {
        myDialogFragment.setText(sbProtocol.toString());
        manager.beginTransaction().add(myDialogFragment, "myDialogFragment")
                                    .show(myDialogFragment)
                                    .commit();
    }

    void clearProtocol () {
        sbProtocol = new SpannableStringBuilder();
    }
}
