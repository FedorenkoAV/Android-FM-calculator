package ru.fmproject.android.calculator;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class CurrencyRateQueryActivity extends AppCompatActivity implements CurrencyRateQueryFragment.SendDataToActivity {

    private static final String TAG = "ActCurrencyRateQuery";

    private static final String EXTRA_GET_CURRENCY_RATE ="ru.fmproject.android.calculator.get_currency_rate";
    private static final String EXTRA_CURRENCY_RATE ="ru.fmproject.android.calculator.currency_rate";
    private static final String EXTRA_CURRENCY_NAME ="ru.fmproject.android.calculator.currency_name";
    private String currency;
    String rate = "3.14";


    public static Intent newIntent(Context packageContext, String currency) {
        L.d(TAG, "newIntent(Context packageContext, String currency) запущен.");
        Intent intent = new Intent(packageContext, CurrencyRateQueryActivity.class);
        intent.putExtra(EXTRA_GET_CURRENCY_RATE, currency);
        return intent;
    }

    public static String getCurrencyRate(Intent result) {
        L.d(TAG, "getCurrencyRate(Intent result) запущен.");
        String strResult = result.getStringExtra(EXTRA_CURRENCY_RATE);
        return strResult;
    }

    public static String getCurrencyName(Intent result) {
        L.d(TAG, "getCurrencyName(Intent result) запущен.");
        String strCurrName = result.getStringExtra(EXTRA_CURRENCY_NAME);
        return strCurrName;
    }
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            L.d(TAG, "mHidePart2Runnable запущен.");
            try {
                mContentView.setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LOW_PROFILE                         //0x00000001
                                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION           //0x00000002
                                | View.SYSTEM_UI_FLAG_FULLSCREEN                //0x00000004
                                //0x00000008
                                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE             //0x00000100
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION    //0x00000200
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN         //0x00000400
//                        | View.SYSTEM_UI_FLAG_IMMERSIVE                       //0x00000800
                                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY          //0x00001000
//                        | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR                //0x00002000
                );
                L.d(TAG, "Полноэкранный режим действует!");
            } catch (Exception e) {
                L.d(TAG, "Произошла ошибка в mHidePart2Runnable: " + e);
                StackTraceElement[] stackTraceElements = e.getStackTrace();
                for (int i = 0; i < stackTraceElements.length; i++) {
                    L.d(TAG, i + ": " + stackTraceElements[i].toString());
                }
            }
        }
    };
    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            L.d(TAG, "mShowPart2Runnable запущен.");
            try {
                ActionBar actionBar = getSupportActionBar();
                if (actionBar != null) {
                    actionBar.show();
                }
//            mControlsView.setVisibility(View.VISIBLE);
            } catch (Exception e) {
                L.d(TAG, "Произошла ошибка в mShowPart2Runnable: " + e);
                StackTraceElement[] stackTraceElements = e.getStackTrace();
                for (int i = 0; i < stackTraceElements.length; i++) {
                    L.d(TAG, i + ": " + stackTraceElements[i].toString());
                }
            }
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            L.d(TAG, "mHideRunnable запущен.");
            try {
                hide();
            } catch (Exception e) {
                L.d(TAG, "Произошла ошибка в mHideRunnable: " + e);
                StackTraceElement[] stackTraceElements = e.getStackTrace();
                for (int i = 0; i < stackTraceElements.length; i++) {
                    L.d(TAG, i + ": " + stackTraceElements[i].toString());
                }
            }
        }
    };
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        L.d(TAG, "onCreate() запущен.");

        try {
            setContentView(R.layout.activity_currency_rate_query);

            mVisible = true;
//            mControlsView = findViewById(R.id.fullscreen_content_controls);
            mContentView = findViewById(R.id.fullscreen_content);

            // Set up the user interaction to manually show or hide the system UI.
//            mContentView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    toggle();
//                }
//            });

            // Upon interacting with UI controls, delay any scheduled hide()
            // operations to prevent the jarring behavior of controls going away
            // while interacting with the UI.
//            findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);

            currency = getIntent().getStringExtra(EXTRA_GET_CURRENCY_RATE);
            sendCurrType(currency);
//TO DO: Здесь нужно передать фрагменту тип валюты currency и получить от фрагмента курс rate
            rate = "3.16";


        } catch (Exception e) {
            L.d(TAG, "При старте приложения произошла ошибка: " + e);
            StackTraceElement[] stackTraceElements = e.getStackTrace();

            for (int i = 0; i < stackTraceElements.length; i++) {
                L.d(TAG, i + ": " + stackTraceElements[i].toString());
            }
            this.onDestroy();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        L.d(TAG, "onStart() запущен.");
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        L.d(TAG, "onPostCreate() запущен.");
    }

    @Override
    protected void onResume() {
        super.onResume();
        L.d(TAG, "onResume() запущен.");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        L.d(TAG, "onRestart() запущен.");
    }


    @Override
    protected void onPause() {
        super.onPause();
        L.d(TAG, "onPause() запущен.");
    }

    @Override
    protected void onStop() {
        super.onStop();
        L.d(TAG, "onStop() запущен.");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        L.d(TAG, "onDestroy() запущен.");
    }

    private void toggle() {
        L.d(TAG, "toggle() запущен.");
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        L.d(TAG, "hide() запущен.");
        try {
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.hide();
            }
//        mControlsView.setVisibility(View.GONE);
            mVisible = false;

            // Schedule a runnable to remove the status and navigation bar after a delay
            mHideHandler.removeCallbacks(mShowPart2Runnable);
            mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
        } catch (Exception e) {
//            customToast.setToastText("Произошла ошибка в hide(): " + e);
//            customToast.show();
            L.d(TAG, "Произошла ошибка в hide(): " + e);
            StackTraceElement[] stackTraceElements = e.getStackTrace();

            for (int i = 0; i < stackTraceElements.length; i++) {
                L.d(TAG, i + ": " + stackTraceElements[i].toString());
            }
            this.onDestroy();
        }
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        L.d(TAG, "show() запущен.");
        try {
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
            mVisible = true;

            // Schedule a runnable to display UI elements after a delay
            mHideHandler.removeCallbacks(mHidePart2Runnable);
            mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
        } catch (Exception e) {
//            customToast.setToastText("Произошла ошибка в show(): " + e);
//            customToast.show();
            L.d(TAG, "Произошла ошибка в show(): " + e);
            StackTraceElement[] stackTraceElements = e.getStackTrace();

            for (int i = 0; i < stackTraceElements.length; i++) {
                L.d(TAG, i + ": " + stackTraceElements[i].toString());
            }
            this.onDestroy();
        }
    }

    /**
     * Schedules a call to hide() in delay milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        L.d(TAG, "delayedHide(int delayMillis) запущен.");
        try {
            mHideHandler.removeCallbacks(mHideRunnable);
            mHideHandler.postDelayed(mHideRunnable, delayMillis);
        } catch (Exception e) {
//            customToast.setToastText("Произошла ошибка в delayedHide(int delayMillis): " + e);
//            customToast.show();
            L.d(TAG, "Произошла ошибка в delayedHide(int delayMillis): " + e);
            StackTraceElement[] stackTraceElements = e.getStackTrace();

            for (int i = 0; i < stackTraceElements.length; i++) {
                L.d(TAG, i + ": " + stackTraceElements[i].toString());
            }
            this.onDestroy();
        }
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        L.d(TAG, "onWindowFocusChanged(boolean hasFocus) запущен.");
        super.onWindowFocusChanged(hasFocus);
        try {
            if (hasFocus) {
                delayedHide(100);
//                mContentView.setSystemUiVisibility(
//                        View.SYSTEM_UI_FLAG_LOW_PROFILE                         //0x00000001
//                                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION           //0x00000002
//                                | View.SYSTEM_UI_FLAG_FULLSCREEN                //0x00000004
//                                //0x00000008
//                                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE             //0x00000100
//                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION    //0x00000200
//                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN         //0x00000400
//                                //                        | View.SYSTEM_UI_FLAG_IMMERSIVE                       //0x00000800
//                                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY          //0x00001000
//                        //                        | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR                //0x00002000
//                );
            }
        } catch (Exception e) {
            L.d(TAG, "Произошла ошибка в onWindowFocusChanged(boolean hasFocus): " + e);
            StackTraceElement[] stackTraceElements = e.getStackTrace();

            for (int i = 0; i < stackTraceElements.length; i++) {
                L.d(TAG, i + ": " + stackTraceElements[i].toString());
            }
            this.onDestroy();
        }
    }


    public void sendCurrType(String currency) {//передаем данные в фрагмент
        // подключаем FragmentManager
        FragmentManager fragmentManager = getSupportFragmentManager();
        // Получаем ссылку на фрагмент по ID
        CurrencyRateQueryFragment fragment = (CurrencyRateQueryFragment) fragmentManager.findFragmentById(R.id.contentFragment);

        // Выводим нужную информацию
        if (fragment != null) {
            fragment.setCurrType(currency);
        }
    }

    @Override
    public void getResult(String[] result) {
        L.d(TAG, "getResult(String[] result) запущен.");
        for (String content : result) {
            L.d(TAG,content);
        }
        Intent data = new Intent();
        data.putExtra(EXTRA_CURRENCY_NAME, currency);
//        rate = result[2];
//        date = result[1];
//        error = result[0];
        if (result[0].equals("OK")) {
            rate = result[2];
            data.putExtra(EXTRA_CURRENCY_RATE, rate);
            setResult(RESULT_OK, data);
            finish();
        }
        if (result[0].equals("CANCELLED")) {
            rate = "";
            data.putExtra(EXTRA_CURRENCY_RATE, rate);
            setResult(RESULT_CANCELED, data);
            finish();
        }
        setResult(RESULT_FIRST_USER, data);
        finish();
//
    }
}
