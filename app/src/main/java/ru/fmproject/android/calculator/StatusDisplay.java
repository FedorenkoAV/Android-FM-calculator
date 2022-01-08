package ru.fmproject.android.calculator;

import android.content.Context;
import androidx.core.content.ContextCompat;

import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by User on 06.06.2017. *
 * Класс StatusDisplay это связующее звено между внутренним устройством и внешним миром.
 * Объекту этого класса передаются ссылки на объекты которые будут отбражать статус устройства.
 * Этот класс нужно переписывать/редактировать заново для каждого нового устройства
 */

public class StatusDisplay {

    public final int SHIFT = 0;
    public final int HYP = 1;
    public final int DEG = 2;
    public final int RAD = 3;
    public final int GRAD = 4;
    public final int BRCKET = 5;
    public final int BIN = 6;
    public final int OCT = 7;
    public final int HEX = 8;
    public final int CPLX = 9;
    public final int SD = 10;
//    public final int MEMORY = 11;
    public final int ERROR = 11;

    private TextView shift;
    private TextView hyp;
    private TextView deg;
    private TextView rad;
    private TextView grad;
    private TextView bracket;
    private TextView bin;
    private TextView oct;
    private TextView hex;
    private TextView cplx;
    private TextView sd;
    private ImageView memory;
    private TextView error;
    int backgroundColor;
    int fontColor;

    StatusDisplay(Context context, TextView statusDisplayLabStore[], ImageView memoryStore) {

        this.shift = statusDisplayLabStore[SHIFT];
        this.hyp = statusDisplayLabStore[HYP];
        this.deg = statusDisplayLabStore[DEG];
        this.rad = statusDisplayLabStore[RAD];
        this.grad = statusDisplayLabStore[GRAD];
        this.bracket = statusDisplayLabStore[BRCKET];
        this.bin = statusDisplayLabStore[BIN];
        this.oct = statusDisplayLabStore[OCT];
        this.hex = statusDisplayLabStore[HEX];
        this.cplx = statusDisplayLabStore[CPLX];
        this.sd = statusDisplayLabStore[SD];
//        this.memory = statusDisplayLabStore[MEMORY];
        this.error = statusDisplayLabStore[ERROR];
        this.memory = memoryStore;
        backgroundColor = ContextCompat.getColor(context, R.color.colorLCDBackground);
        fontColor = ContextCompat.getColor(context, R.color.colorLCDFont);
        offShift();
        offHyp();
        offDeg();
        offRad();
        offGrad();
        offBracket();
        offBin();
        offOct();
        offHex();
        offCplx();
        offSd();
    }

    void onShift() {
        shift.setTextColor(fontColor);
    }

    void offShift() {
        shift.setTextColor(backgroundColor);
    }

    void onHyp() {
        hyp.setTextColor(fontColor);
    }

    void offHyp() {
        hyp.setTextColor(backgroundColor);
    }

    void onDeg() {
        deg.setTextColor(fontColor);
    }

    void offDeg() {
        deg.setTextColor(backgroundColor);
    }

    void onRad() {
        rad.setTextColor(fontColor);
    }

    void offRad() {
        rad.setTextColor(backgroundColor);
    }

    void onGrad() {
        grad.setTextColor(fontColor);
    }

    void offGrad() {
        grad.setTextColor(backgroundColor);
    }

    void onBracket() {
        bracket.setTextColor(fontColor);
    }

    void offBracket() {
        bracket.setTextColor(backgroundColor);
    }

    void onBin() {
        bin.setTextColor(fontColor);
    }

    void offBin() {
        bin.setTextColor(backgroundColor);
    }

    void onOct() {
        oct.setTextColor(fontColor);
    }

    void offOct() {
        oct.setTextColor(backgroundColor);
    }

    void onHex() {
        hex.setTextColor(fontColor);
    }

    void offHex() {
        hex.setTextColor(backgroundColor);
    }

    void onCplx() {
        cplx.setTextColor(fontColor);
    }

    void offCplx() {
        cplx.setTextColor(backgroundColor);
    }

    void onSd() {
        sd.setTextColor(fontColor);
    }

    void offSd() {
        sd.setTextColor(backgroundColor);
    }

    void onMemory() {
//        memory.setTextColor(fontColor);
        memory.setImageAlpha(0xFF);
//        memory.setVisibility(View.VISIBLE);
    }

    void offMemory() {
//        memory.setTextColor(backgroundColor);
        memory.setImageAlpha(0x0F);
//        memory.setVisibility(View.INVISIBLE);
    }

    void onError() {
        error.setTextColor(fontColor);
    }

    void offError() {
        error.setTextColor(backgroundColor);
    }
}
