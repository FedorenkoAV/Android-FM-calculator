package ru.fmproject.android.calculator;

import android.app.Activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.ResponseInfo;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.navigation.NavigationView;

import androidx.fragment.app.FragmentManager;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, MyDialogFragment.NoticeDialogListener {

    private AdView mAdView;

    private static final String TAG = "MainActivity";

    private static final int REQUEST_CODE_CURRENCY = 0;

    public static final int TOAST = 0;
    public static final int STATUS_DISPLAY = 1;
    public static final int STATUS = 2;
    public static final int MODE = 3;
    public static final int ANGLE = 4;
    public static final int MEMORY = 5;
    public static final int MAIN_DISPLAY = 6;
    public static final int STACK_CALCULATOR = 7;
    public static final int EDIT_X = 8;
    public static final int CPLX = 9;
    public static final int SD = 10;
    public static final int INPUT_DRIVER = 11;
    public static final int MAIN_ACTIVITY = 12;
    public static final int PREFERENCES = 13;
    public static final int EDIT_X_BIN = 14;
    public static final int EDIT_X_OCT = 15;
    public static final int EDIT_X_HEX = 16;
    public static final int PROTOCOL = 17;
    public static final int FRAGMENT_MANAGER = 18;

    Button btn_shift;
    Button btn_hyp;
    Button btn_inv;
//    Button btn_4;
    Button btn_off;
    Button btn_onc;

    Button btn_del;
    Button btn_sci;
    Button btn_drg;
    Button btn_sin;
    Button btn_cos;
    Button btn_tan;

    Button btn_exp;
    Button btn_x_pow_y;
    Button btn_sqr;
    Button btn_to_rad;
    Button btn_ln;
    Button btn_log;

    Button btn_x2;
    Button btn_a;
    Button btn_b;
    Button btn_open_bracket;
    Button btn_close_bracket;
    Button btn_x_to_mem;

    Button btn_7;
    Button btn_8;
    Button btn_9;
    Button btn_mem_plus;
    Button btn_mem_read;

    Button btn_4;
    Button btn_5;
    Button btn_6;
    Button btn_mult;
    Button btn_div;

    Button btn_1;
    Button btn_2;
    Button btn_3;
    Button btn_plus;
    Button btn_minus;

    Button btn_0;
    Button btn_dot;
    Button btn_sign;
    Button btn_calc;
    Button btn_ce;

    TextView lab_pi;
//    TextView lab_xy;
    TextView lab_cbt;
    TextView lab_todrg;
    TextView lab_ex;
    TextView lab_10x;

    TextView shift;
    TextView hyp;
    TextView deg;
    TextView rad;
    TextView grad;
    TextView bracket;
    TextView bin;
    TextView oct;
    TextView hex;
    TextView cplx;
    TextView sd;
    ImageView memory;
    TextView error;
    TextView display;
    TextView tvProtocol;

    Bundle savedInstanceState;

    Protocol protocol;
    CustomToast customToast;
    Angle angle;
    StackCalculator stackCalculator;
    MainDisplay mainDisplay;
    EditX editX;

    Status status;
    MemoryStore memoryStore;
    InputDriver inputDriver;

    Object[] objStore = new Object[19];

    ClipboardManager clipboardManager;
    ClipData clipData;

    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
//        @SuppressLint("InlinedApi")
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

    private final Runnable mShowPart2Runnable = () -> {
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
    };
    private final Runnable mHideRunnable = () -> {
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
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.savedInstanceState = savedInstanceState;

        super.onCreate(savedInstanceState);
        L.d(TAG, "onCreate() запущен.");
        if (BuildConfig.DEBUG) {
            // Режим отладки, ведём логи
            L.d(TAG, "Режим отладки, ведём логи.");
        } else {
            android.util.Log.d(TAG, "Это релизная версия. Логов не будет.");
        }
        try {
            setContentView(R.layout.drawable_layout);
//            mVisible = true;
//            mControlsView = findViewById(R.id.fullscreen_content_controls);
//            mContentView = findViewById(R.id.fullscreen_content);
            // Set up the user interaction to manually show or hide the system UI.
//            mContentView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
////                    toggle();
//                }
//            });

            // Upon interacting with UI controls, delay any scheduled hide()
            // operations to prevent the jarring behavior of controls going away
            // while interacting with the UI.
//            findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);

            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);

            clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            L.d(TAG, "Начнем");

            MainActivity activity = MainActivity.this;
            L.d(TAG, "Activity activity = MainActivity.this создана.");

            objStore[MAIN_ACTIVITY] = activity;

//        Создаем свой тост https://developer.android.com/guide/topics/ui/notifiers/toasts.html
            customToast = new CustomToast(activity, "В разработке");
        } catch (Exception e) {
            L.d(TAG, "При старте приложения произошла ошибка: " + e);
            L.printStackTrace(e);
            this.onDestroy();
        }

        try {
            objStore[TOAST] = customToast;
            L.d(TAG, "Создали свой тост https://developer.android.com/guide/topics/ui/notifiers/toasts.html");
            display = findViewById(R.id.display);
            display.setOnCreateContextMenuListener(this);

//        Находим метки статуса и режима работы
            L.d(TAG, "Находим метки статуса и режима работы");
            shift = findViewById(R.id.status_shift);
            hyp = findViewById(R.id.status_hyp);
            deg = findViewById(R.id.status_deg);
            rad = findViewById(R.id.status_rad);
            grad = findViewById(R.id.status_grad);
            bracket = findViewById(R.id.status_bracket);
            bin = findViewById(R.id.status_bin);
            oct = findViewById(R.id.status_oct);
            hex = findViewById(R.id.status_hex);
            cplx = findViewById(R.id.status_cplx);
            sd = findViewById(R.id.status_sd);
            memory = findViewById(R.id.memory);
            error = findViewById(R.id.error);

//        Находим кнопки
            L.d(TAG, "Находим кнопки");
            btn_shift = findViewById(R.id.FuncButtonShift);
            btn_hyp = findViewById(R.id.FuncButtonHyp);
            btn_inv = findViewById(R.id.FuncButtonGear);
//            btn_4 = findViewById(R.id.FuncButton4);
            btn_off = findViewById(R.id.FuncButtonOff);
            btn_onc = findViewById(R.id.FuncButtonOnC);
            btn_del = findViewById(R.id.FuncButtonDel);
            btn_sci = findViewById(R.id.FuncButtonSci);
            btn_drg = findViewById(R.id.FuncButtonDrg);
            btn_sin = findViewById(R.id.FuncButtonSin);
            btn_cos = findViewById(R.id.FuncButtonCos);
            btn_tan = findViewById(R.id.FuncButtonTan);
            btn_exp = findViewById(R.id.FuncButtonExp);
            btn_x_pow_y = findViewById(R.id.FuncButtonXpowY);
            btn_sqr = findViewById(R.id.FuncButtonSqr);
            btn_to_rad = findViewById(R.id.FuncButtonToRad);
            btn_ln = findViewById(R.id.FuncButtonLn);
            btn_log = findViewById(R.id.FuncButtonLog);
            btn_x2 = findViewById(R.id.FuncButtonX2);
            btn_a = findViewById(R.id.FuncButtonA);
            btn_b = findViewById(R.id.FuncButtonB);
            btn_open_bracket = findViewById(R.id.FuncButtonOpenBracket);
            btn_close_bracket = findViewById(R.id.FuncButtonCloseBracket);
            btn_x_to_mem = findViewById(R.id.FuncButtonXtoM);

            btn_7 = findViewById(R.id.Button07);
            btn_8 = findViewById(R.id.Button08);
            btn_9 = findViewById(R.id.Button09);
            btn_mem_plus = findViewById(R.id.ButtonMem);
            btn_mem_read = findViewById(R.id.ButtonMR);
            btn_4 = findViewById(R.id.Button04);
            btn_5 = findViewById(R.id.Button05);
            btn_6 = findViewById(R.id.Button06);
            btn_mult = findViewById(R.id.ButtonMult);
            btn_div = findViewById(R.id.ButtonDiv);
            btn_1 = findViewById(R.id.Button01);
            btn_2 = findViewById(R.id.Button02);
            btn_3 = findViewById(R.id.Button03);
            btn_plus = findViewById(R.id.ButtonPlus);
            btn_minus = findViewById(R.id.ButtonMinus);
            btn_0 = findViewById(R.id.Button0);
            btn_dot = findViewById(R.id.ButtonDot);
            btn_sign = findViewById(R.id.ButtonSign);
            btn_calc = findViewById(R.id.ButtonCalc);
            btn_ce = findViewById(R.id.ButtonCE);

            Button[] btnStore = {btn_shift, btn_hyp, btn_inv, btn_onc, btn_off, btn_onc,
                    btn_del, btn_sci, btn_drg, btn_sin, btn_cos, btn_tan,
                    btn_exp, btn_x_pow_y, btn_sqr, btn_to_rad, btn_ln, btn_log,
                    btn_x2, btn_a, btn_b, btn_open_bracket, btn_close_bracket, btn_x_to_mem,

                    btn_7, btn_8, btn_9, btn_mem_plus, btn_mem_read,
                    btn_4, btn_5, btn_6, btn_mult, btn_div,
                    btn_1, btn_2, btn_3, btn_plus, btn_minus,
                    btn_0, btn_dot, btn_sign, btn_calc, btn_ce};


//        Создаем объект statusDisplay, который будет управлять отображением меток статуса и режима работы на дисплее
            TextView[] statusDisplayLabStore = {shift, hyp, deg, rad, grad, bracket, bin, oct, hex, cplx, sd, error};
            StatusDisplay statusDisplay = new StatusDisplay(this, statusDisplayLabStore, memory);
            objStore[STATUS_DISPLAY] = statusDisplay;

            L.d(TAG, "Создали объект statusDisplay, который будет управлять отображением меток статуса и режима работы на дисплее");

//          Создаем объект Preferences который будет читать и записывать настройки в файл
            Preferences preferences = new Preferences(this);
            L.d(TAG, "Создали объект preferences, который будет читать и записывать настройки в файл." + preferences);
            objStore[PREFERENCES] = preferences;

//        Создаем объект status, который будет менять статусы и режимы работы, а так же текст в метках и кнопках
            status = new Status(statusDisplay, btnStore);
            L.d(TAG, "Создали объект status, который будет менять статусы и режимы работы" + status);
            objStore[STATUS] = status;
//        Создаем объект mode, который будет менять режим работы на CPLX и SD
            Mode mode = new Mode(statusDisplay);
            L.d(TAG, "Создали объект mode, который будет менять режим работы на CPLX и SD");
            objStore[MODE] = mode;

            tvProtocol = findViewById(R.id.protocol);
            tvProtocol.setSelected(true);

//        Создаем объект - FragmentManager
            FragmentManager manager = getSupportFragmentManager();
            objStore[FRAGMENT_MANAGER] = manager;

            MyDialogFragment myDialogFragment = new MyDialogFragment();

            protocol = new Protocol(tvProtocol, mode, myDialogFragment, manager);
            objStore[PROTOCOL] = protocol;
            L.d(TAG, "Создали объект protocol, который будет протоколировать вычисления");

//        Создаем объект angle, который будет менять удуницы измерения углов
            angle = new Angle(preferences, statusDisplay);
            L.d(TAG, "Создали объект angle, который будет менять удуницы измерения углов");
            objStore[ANGLE] = angle;

//        Создаем объект memoryStore, который будет работать с памятью
            memoryStore = new MemoryStore(preferences, status);
            L.d(TAG, "Создали объект memoryStore, который будет работать с памятью");
            objStore[MEMORY] = memoryStore;

//        Создаем объект mainDisplay, который будет выводить цифровую информацию
            mainDisplay = new MainDisplay(this, display, preferences);
            L.d(TAG, "Создали объект mainDisplay, который будет выводить цифровую информацию");
            objStore[MAIN_DISPLAY] = mainDisplay;

//        Создаем объект stackCalculator, который будет выполнять все вычисления
            stackCalculator = new StackCalculator(angle, protocol);
            L.d(TAG, "Создали объект stackCalculator, который будет выполнять все вычисления");
            objStore[STACK_CALCULATOR] = stackCalculator;

//        Создаем объект editX, который будет отвечать за ввод всего
            editX = new EditX(objStore);
            L.d(TAG, "Создали объект editX, который будет отвечать за ввод всего");
            objStore[EDIT_X] = editX;

//        Создаем объект - обработчик нажатия кнопок и вешаем его на кнопки
            inputDriver = new InputDriver(objStore); //инициализируем clickListener
            L.d(TAG, "Создали объект - обработчик нажатия кнопок и вешаем его на кнопки");
            objStore[INPUT_DRIVER] = inputDriver;

            for (Button button : btnStore) {
                button.setOnClickListener(inputDriver);
            }

            for (int i = 0; i < objStore.length; i++) {
                L.d(TAG, "В objStore[" + i + "]: " + objStore[i]);
            }

//            MobileAds.initialize(this, "ca-app-pub-9802856203007191~8964036984");

            MobileAds.initialize(this, new OnInitializationCompleteListener() {
                @Override
                public void onInitializationComplete(InitializationStatus initializationStatus) {
                }
            });

            mAdView = findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
            mAdView.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    // Code to be executed when an ad finishes loading.
                    L.d("Ads", "onAdLoaded() запустился.");
                    L.d("Ads", "mAdView responseInfo: " + mAdView.getResponseInfo());
                }

                @Override
                public void onAdFailedToLoad(LoadAdError adError) {
                    // Code to be executed when an ad request fails.
                    L.d("Ads", "onAdFailedToLoad запустился.");
                    // Gets the domain from which the error came.
                    L.d("Ads", "errorDomain: " + adError.getDomain());
                    // Gets the error code. See
                    // https://developers.google.com/android/reference/com/google/android/gms/ads/AdRequest#constant-summary
                    // for a list of possible codes.
                    L.d("Ads", "errorCode: " + adError.getCode());
                    // Gets an error message.
                    // For example "Account not approved yet". See
                    // https://support.google.com/admob/answer/9905175 for explanations of
                    // common errors.
                    L.d("Ads", "errorMessage: " + adError.getMessage());
                    // Gets additional response information about the request. See
                    // https://developers.google.com/admob/android/response-info for more
                    // information.
                    L.d("Ads", "adError responseInfo: " + adError.getResponseInfo());
                    // Gets the cause of the error, if available.
                    L.d("Ads", "cause: " + adError.getCause());
                    // All of this information is available via the error's toString() method.
                    //L.d("Ads", adError.toString());
                }

                @Override
                public void onAdOpened() {
                    // Code to be executed when an ad opens an overlay that
                    // covers the screen.
                    L.d("Ads", "onAdOpened() запустился.");
                }

                @Override
                public void onAdClicked() {
                    // Code to be executed when the user clicks on an ad.
                    L.d("Ads", "onAdClicked() запустился.");
                }

                @Override
                public void onAdClosed() {
                    // Code to be executed when the user is about to return
                    // to the app after tapping on an ad.
                    L.d("Ads", "onAdClosed() запустился.");
                }
            });

        } catch (Exception e) {
            customToast.setToastText("Произошла неизвестная ошибка: " + e);
            customToast.show();
            L.d(TAG, "При старте приложения произошла ошибка: " + e);
            StackTraceElement[] stackTraceElements = e.getStackTrace();

            for (int i = 0; i < stackTraceElements.length; i++) {
                L.d(TAG, i + ": " + stackTraceElements[i].toString());
            }
            L.printStackTrace(e);
            this.onDestroy();
        }
    }

    @Override
    public void onBackPressed() {
        L.d(TAG, "onBackPressed() запущен.");
        try {
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        } catch (Exception e) {
            customToast.setToastText("Произошла ошибка в onBackPressed(): " + e);
            customToast.show();
            L.d(TAG, "Произошла ошибка в onBackPressed(): " + e);
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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.main, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.context_copy:
                contextCopy();
                customToast.setToastText(getString(R.string.ok_copy_to_clipboard));
                customToast.show();
                break;
            case R.id.context_paste:
                contextPaste();
                customToast.setToastText(getString(R.string.ok_paste_from_clipboard));
                customToast.show();
                break;
            default:
                break;
        }
        return super.onContextItemSelected(item);
    }

    void contextCopy() {
        clipData = ClipData.newPlainText("Text", inputDriver.copyToClipboard());
        clipboardManager.setPrimaryClip(clipData);
    }

    void contextPaste() {
        ClipData data = clipboardManager.getPrimaryClip();
        ClipData.Item clipDataItem = null;
        if (data != null) {
            clipDataItem = data.getItemAt(0);
        }
        if (clipDataItem != null) {
            inputDriver.pasteFromClipboard(clipDataItem.getText().toString());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        L.d(TAG, "onCreateOptionsMenu(Menu menu) запущен.");
        try {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.main, menu);
        } catch (Exception e) {
            L.d(TAG, "Произошла ошибка в onCreateOptionsMenu(Menu menu): " + e);
            L.printStackTrace(e);
            this.onDestroy();
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        L.d(TAG, "onOptionsItemSelected(MenuItem item) запущен.");
        return super.onOptionsItemSelected(item);
    }


//    private void toggle() {
//        L.d(TAG, "toggle() запущен.");
//        if (mVisible) {
//            hide();
//        } else {
//            show();
//        }
//    }

    private void hide() {
        // Hide UI first
        L.d(TAG, "hide() запущен.");
        try {
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.hide();
            }
//        mControlsView.setVisibility(View.GONE);
//            mVisible = false;

            // Schedule a runnable to remove the status and navigation bar after a delay
            mHideHandler.removeCallbacks(mShowPart2Runnable);
            mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
        } catch (Exception e) {
            L.d(TAG, "Произошла ошибка в hide(): " + e);
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
    private void delayedHide() {
        L.d(TAG, "delayedHide(int delayMillis) запущен.");
        try {
            mHideHandler.removeCallbacks(mHideRunnable);
            mHideHandler.postDelayed(mHideRunnable, 100);
        } catch (Exception e) {
            L.d(TAG, "Произошла ошибка в delayedHide(int delayMillis): " + e);
            L.printStackTrace(e);
            this.onDestroy();
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        L.d(TAG, "onWindowFocusChanged(boolean hasFocus) запущен.");
        super.onWindowFocusChanged(hasFocus);
        try {
            if (hasFocus) {
                delayedHide();
            }
        } catch (Exception e) {
            L.d(TAG, "Произошла ошибка в onWindowFocusChanged(boolean hasFocus): " + e);
            L.printStackTrace(e);
            this.onDestroy();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        L.d(TAG, "onNavigationItemSelected(MenuItem item) запущен.");
        try {
            int id = item.getItemId();

            switch (id) {
                case (R.id.nav_copy):
                    contextCopy();
                    customToast.setToastText(getString(R.string.ok_copy_to_clipboard));
                    customToast.show();
                    break;
                case (R.id.nav_paste):
                    contextPaste();
                    customToast.setToastText(getString(R.string.ok_paste_from_clipboard));
                    customToast.show();
                    break;
                case (R.id.nav_protocol):
                    protocol.alertDialog();
                    break;
                case (R.id.nav_settings):
                    customToast.setToastText(getString(R.string.settings_message));
                    customToast.show();
                    break;
                case (R.id.nav_about):
                    customToast.setToastText(getString(R.string.about_message));
                    customToast.show();
                    break;
                case (R.id.nav_exit):
                    this.finish();
                    break;
            }

            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        } catch (Exception e) {
            L.d(TAG, "onNavigationItemSelected(MenuItem item): " + e);
            L.printStackTrace(e);
            this.onDestroy();
        }
        return true;
    }

    void currencyRate(String currency) {
        L.d(TAG, "Запускаем дочернюю активность.");
        L.d(TAG, "Запрашиваем курс " + currency);
        Intent intent = CurrencyRateQueryActivity.newIntent(MainActivity.this, currency);
        startActivityForResult(intent, REQUEST_CODE_CURRENCY);
    }

    void bitcoinRate(String currency) {
        L.d(TAG, "Запускаем дочернюю активность.");
        L.d(TAG, "Запрашиваем курс " + currency);
        Intent intent = CurrencyRateQueryActivity.newIntent(MainActivity.this, currency);
        startActivityForResult(intent, REQUEST_CODE_CURRENCY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        L.d(TAG, "onActivityResult(int requestCode, int resultCode, Intent data) запущен.");
        switch (requestCode) {
            case (REQUEST_CODE_CURRENCY):
                L.d(TAG, "requestCode == REQUEST_CODE_CURRENCY");
                if (data == null) {
                    L.d(TAG, "data == null");
                }
                switch (resultCode) {
                    case (Activity.RESULT_OK):
                        L.d(TAG, "Выход из дочерней активности RESULT_OK.");
                        String currency_rate = null;
                        if (data != null) {
                            currency_rate = CurrencyRateQueryActivity.getCurrencyRate(data);
                        }
                        L.d(TAG, "currency_rate = " + currency_rate);
                        editX.dollar(currency_rate);
                        break;
                    case (Activity.RESULT_CANCELED):
                        L.d(TAG, "Выход из дочерней активности RESULT_CANCELED.");
                        customToast.setToastText(getString(R.string.download_cancelled));
                        customToast.show();
                        break;
                    case (Activity.RESULT_FIRST_USER):
                        L.d(TAG, "Выход из дочерней активности RESULT_FIRST_USER.");
                        customToast.setToastText(getString(R.string.download_error));
                        customToast.show();
                        break;
                }
                break;
        }
    }

    @Override
    public void onDialogNegativeClick(MyDialogFragment myDialogFragment) {
        protocol.clearProtocol();
    }
}
