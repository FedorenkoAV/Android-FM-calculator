package ru.fmproject.android.calculator;

import android.annotation.SuppressLint;
import android.app.Activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
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

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, MyDialogFragment.NoticeDialogListener {

    private static final String TAG = "MainActivity";

    private static final int REQUEST_CODE_CURRENCY = 0;

    public final static int TOAST = 0;
    public final static int STATUS_DISPLAY = 1;
    public final static int STATUS = 2;
    public final static int MODE = 3;
    public final static int ANGLE = 4;
    public final static int MEMORY = 5;
    public final static int MAIN_DISPLAY = 6;
    public final static int STACK_CALCULATOR = 7;
    public final static int EDIT_X = 8;
    public final static int CPLX = 9;
    public final static int SD = 10;
    public final static int INPUT_DRIVER = 11;
    public final static int MAIN_ACTIVITY = 12;
    public final static int PREFERENCES = 13;
    public final static int EDIT_X_BIN = 14;
    public final static int EDIT_X_OCT = 15;
    public final static int EDIT_X_HEX = 16;
    public final static int PROTOCOL = 17;
    public final static int FRAGMENT_MANAGER = 18;

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

    Object objStore[] = new Object[19];

    ClipboardManager clipboardManager;
    ClipData clipData;

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
//    private boolean mVisible;
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
//    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
//        @Override
//        public boolean onTouch(View view, MotionEvent motionEvent) {
//            L.d(TAG, "onTouch(View view, MotionEvent motionEvent) в mDelayHideTouchListener запущен.");
//            try {
//                if (AUTO_HIDE) {
//                    delayedHide(AUTO_HIDE_DELAY_MILLIS);
//                }
//            } catch (Exception e) {
//                L.d(TAG, "Произошла ошибка в onTouch(View view, MotionEvent motionEvent) в mDelayHideTouchListener: " + e);
//                StackTraceElement[] stackTraceElements = e.getStackTrace();
//                for (int i = 0; i < stackTraceElements.length; i++) {
//                    L.d(TAG, i + ": " + stackTraceElements[i].toString());
//                }
//            }
//            return false;
//        }
//    };

//    private View mDecorView;

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
            mContentView = findViewById(R.id.fullscreen_content);
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
//            StackTraceElement[] stackTraceElements = e.getStackTrace();
//
//            for (int i = 0; i < stackTraceElements.length; i++) {
//                L.d(TAG, i + ": " + stackTraceElements[i].toString());
//            }
            L.printStackTrace(e);
            this.onDestroy();
        }

        try {
            objStore[TOAST] = customToast;
            L.d(TAG, "Создали свой тост https://developer.android.com/guide/topics/ui/notifiers/toasts.html");



            display = (TextViewPlus) findViewById(R.id.display);
            display.setOnCreateContextMenuListener(this);

//        Находим метки статуса и режима работы
            L.d(TAG, "Находим метки статуса и режима работы");
            shift = (TextViewPlus) findViewById(R.id.status_shift);
            hyp = (TextViewPlus) findViewById(R.id.status_hyp);
            deg = (TextViewPlus) findViewById(R.id.status_deg);
            rad = (TextViewPlus) findViewById(R.id.status_rad);
            grad = (TextViewPlus) findViewById(R.id.status_grad);
            bracket = (TextViewPlus) findViewById(R.id.status_bracket);
            bin = (TextViewPlus) findViewById(R.id.status_bin);
            oct = (TextViewPlus) findViewById(R.id.status_oct);
            hex = (TextViewPlus) findViewById(R.id.status_hex);
            cplx = (TextViewPlus) findViewById(R.id.status_cplx);
            sd = (TextViewPlus) findViewById(R.id.status_sd);
            memory = findViewById(R.id.memory);
            error = (TextViewPlus) findViewById(R.id.error);

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

            Button btnStore[] = {btn_shift, btn_hyp, btn_inv, btn_onc, btn_off, btn_onc,
                    btn_del, btn_sci, btn_drg, btn_sin, btn_cos, btn_tan,
                    btn_exp, btn_x_pow_y, btn_sqr, btn_to_rad, btn_ln, btn_log,
                    btn_x2, btn_a, btn_b, btn_open_bracket, btn_close_bracket, btn_x_to_mem,

                    btn_7, btn_8, btn_9, btn_mem_plus, btn_mem_read,
                    btn_4, btn_5, btn_6, btn_mult, btn_div,
                    btn_1, btn_2, btn_3, btn_plus, btn_minus,
                    btn_0, btn_dot, btn_sign, btn_calc, btn_ce};


//        Создаем объект statusDisplay, который будет управлять отображением меток статуса и режима работы на дисплее
            TextView statusDisplayLabStore[] = {shift, hyp, deg, rad, grad, bracket, bin, oct, hex, cplx, sd, error};
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

            tvProtocol = (TextViewPlus) findViewById(R.id.protocol);
            tvProtocol.setSelected(true);
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);//создаём объект класса AlertDialog.Builder, передав в качестве параметра контекст приложения
//            builder.setTitle(R.string.protocol);//задаём для создаваемого диалога заголовок
//            View adv = getLayoutInflater().inflate(R.layout.dialog, null);// создаем view из dialog.xml
//            builder.setView(adv);// устанавливаем ее, как содержимое тела диалога
//            TextView tvAlertDialogProtocol = adv.findViewById(R.id.tvAlertDialogProtocol);// находим TexView для отображения протокола
//            Dialog alertDialog = builder.create();

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

            btn_shift.setOnClickListener(inputDriver); //
            btn_hyp.setOnClickListener(inputDriver); //
            btn_inv.setOnClickListener(inputDriver); //
//            btn_4.setOnClickListener(inputDriver); //
            btn_off.setOnClickListener(inputDriver);
            btn_onc.setOnClickListener(inputDriver);
            btn_del.setOnClickListener(inputDriver);
            btn_sci.setOnClickListener(inputDriver);
            btn_drg.setOnClickListener(inputDriver);
            btn_sin.setOnClickListener(inputDriver);
            btn_cos.setOnClickListener(inputDriver);
            btn_tan.setOnClickListener(inputDriver);
            btn_exp.setOnClickListener(inputDriver);
            btn_x_pow_y.setOnClickListener(inputDriver);
            btn_sqr.setOnClickListener(inputDriver);
            btn_to_rad.setOnClickListener(inputDriver);
            btn_ln.setOnClickListener(inputDriver);
            btn_log.setOnClickListener(inputDriver);
            btn_x2.setOnClickListener(inputDriver);
            btn_a.setOnClickListener(inputDriver);
            btn_b.setOnClickListener(inputDriver);
            btn_open_bracket.setOnClickListener(inputDriver);
            btn_close_bracket.setOnClickListener(inputDriver);
            btn_x_to_mem.setOnClickListener(inputDriver);
            btn_7.setOnClickListener(inputDriver);
            btn_8.setOnClickListener(inputDriver);
            btn_9.setOnClickListener(inputDriver);
            btn_mem_plus.setOnClickListener(inputDriver);
            btn_mem_read.setOnClickListener(inputDriver);
            btn_4.setOnClickListener(inputDriver);
            btn_5.setOnClickListener(inputDriver);
            btn_6.setOnClickListener(inputDriver);
            btn_mult.setOnClickListener(inputDriver);
            btn_div.setOnClickListener(inputDriver);
            btn_1.setOnClickListener(inputDriver);
            btn_2.setOnClickListener(inputDriver);
            btn_3.setOnClickListener(inputDriver);
            btn_plus.setOnClickListener(inputDriver);
            btn_minus.setOnClickListener(inputDriver);
            btn_0.setOnClickListener(inputDriver);
            btn_dot.setOnClickListener(inputDriver);
            btn_sign.setOnClickListener(inputDriver);
            btn_calc.setOnClickListener(inputDriver);
            btn_ce.setOnClickListener(inputDriver);

            for (int i = 0; i < objStore.length; i++) {

                L.d(TAG, "В objStore[" + i + "]: " + objStore[i]);
            }

            // Sample AdMob app ID: ca-app-pub-3940256099942544~3347511713
            // Sample AdMob app ID: ca-app-pub-9802856203007191~8964036984
//            MobileAds.initialize(this, "ca-app-pub-9802856203007191~8964036984");

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    AdView mAdView = findViewById(R.id.adView);
                    //AdRequest adRequest = new AdRequest.Builder().addTestDevice("C656855C66D6AB6FF2E42A97286F3B59").build(); //Genymotion Custom Phone - 8.0 API26
                    //AdRequest adRequest = new AdRequest.Builder().addTestDevice("7AC37A42A3DEC77A354D9A0C0B1C4325").build();// Xiaomi Redmi 5A
                    AdRequest adRequest = new AdRequest.Builder().build();
                    mAdView.loadAd(adRequest);
                    mAdView.setAdListener(new AdListener() {
                        @Override
                        public void onAdLoaded() {
                            // Code to be executed when an ad finishes loading.
                            L.d(TAG, "onAdLoaded() запущен.");
                        }

                        @Override
                        public void onAdFailedToLoad(int errorCode) {
                            // Code to be executed when an ad request fails.
                            L.d(TAG, "onAdFailedToLoad() запущен.");
                        }

                        @Override
                        public void onAdOpened() {
                            // Code to be executed when an ad opens an overlay that
                            // covers the screen.
                            L.d(TAG, "onAdOpened() запущен.");
                        }

                        @Override
                        public void onAdLeftApplication() {
                            // Code to be executed when the user has left the app.
                            L.d(TAG, "onAdLeftApplication() запущен.");
                        }

                        @Override
                        public void onAdClosed() {
                            // Code to be executed when the user is about to return
                            // to the app after tapping on an ad.
                            L.d(TAG, "onAdClosed() запущен.");
                        }

                        @Override
                        public void onAdClicked() {
                            // Code to be executed when a click is recorded for an ad.
                            L.d(TAG, "onAdClicked() запущен.");
                        }

                        @Override
                        public void onAdImpression() {
                            // Code to be executed when an impression is recorded for an ad.
                            L.d(TAG, "onAdImpression() запущен.");
                        }
                    });
                }
            }, 10000);
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
        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
//        try {
//            delayedHide(100);
//        } catch (Exception e) {
//            customToast.setToastText("Произошла ошибка в onPostCreate(): " + e);
//            customToast.show();
//            L.d(TAG, "Произошла ошибка в onPostCreate(): " + e);
//            StackTraceElement[] stackTraceElements = e.getStackTrace();
//
//            for (int i = 0; i < stackTraceElements.length; i++) {
//                L.d(TAG, i + ": " + stackTraceElements[i].toString());
//            }
//            this.onDestroy();
//        }
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
//        super.onCreateContextMenu(menu, v, menuInfo);
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
//            customToast.setToastText("Произошла ошибка в onCreateOptionsMenu(Menu menu): " + e);
//            customToast.show();
            L.d(TAG, "Произошла ошибка в onCreateOptionsMenu(Menu menu): " + e);
//            StackTraceElement[] stackTraceElements = e.getStackTrace();
//
//            for (int i = 0; i < stackTraceElements.length; i++) {
//                L.d(TAG, i + ": " + stackTraceElements[i].toString());
//            }
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
//        try {
//            int id = item.getItemId();
//
////            //noinspection SimplifiableIfStatement
////            if (id == R.id.action_settings) {
////                return true;
////            }
//        } catch (Exception e) {
//            customToast.setToastText("Произошла ошибка в onOptionsItemSelected(MenuItem item): " + e);
//            customToast.show();
//            L.d(TAG, "Произошла ошибка в onOptionsItemSelected(MenuItem item): " + e);
//            StackTraceElement[] stackTraceElements = e.getStackTrace();
//
//            for (int i = 0; i < stackTraceElements.length; i++) {
//                L.d(TAG, i + ": " + stackTraceElements[i].toString());
//            }
//            this.onDestroy();
//        }
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

//    @SuppressLint("InlinedApi")
//    private void show() {
//        // Show the system bar
//        L.d(TAG, "show() запущен.");
//        try {
//            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
//            mVisible = true;
//
//            // Schedule a runnable to display UI elements after a delay
//            mHideHandler.removeCallbacks(mHidePart2Runnable);
//            mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
//        } catch (Exception e) {
////            customToast.setToastText("Произошла ошибка в show(): " + e);
////            customToast.show();
//            L.d(TAG, "Произошла ошибка в show(): " + e);
//            StackTraceElement[] stackTraceElements = e.getStackTrace();
//
//            for (int i = 0; i < stackTraceElements.length; i++) {
//                L.d(TAG, i + ": " + stackTraceElements[i].toString());
//            }
//            this.onDestroy();
//        }
//    }

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
//            customToast.setToastText("Произошла ошибка в delayedHide(int delayMillis): " + e);
//            customToast.show();
            L.d(TAG, "Произошла ошибка в delayedHide(int delayMillis): " + e);
//            StackTraceElement[] stackTraceElements = e.getStackTrace();
//
//            for (int i = 0; i < stackTraceElements.length; i++) {
//                L.d(TAG, i + ": " + stackTraceElements[i].toString());
//            }
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
//            StackTraceElement[] stackTraceElements = e.getStackTrace();
//
//            for (int i = 0; i < stackTraceElements.length; i++) {
//                L.d(TAG, i + ": " + stackTraceElements[i].toString());
//            }
            L.printStackTrace(e);
            this.onDestroy();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
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
//            customToast.setToastText("onNavigationItemSelected(MenuItem item): " + e);
//            customToast.show();
            L.d(TAG, "onNavigationItemSelected(MenuItem item): " + e);
//            StackTraceElement[] stackTraceElements = e.getStackTrace();
//
//            for (int i = 0; i < stackTraceElements.length; i++) {
//                L.d(TAG, i + ": " + stackTraceElements[i].toString());
//            }
            L.printStackTrace(e);
            this.onDestroy();
        }
        return true;
    }

    void currency_rate(String currency) {
        L.d(TAG, "Запускаем дочернюю активность.");
        L.d(TAG, "Запрашиваем курс " + currency);
//            CurrencyRateQueryDialog currencyRateQueryDialog = new CurrencyRateQueryDialog();
//            currencyRateQueryDialog.show(manager, "dialog");
        Intent intent = CurrencyRateQueryActivity.newIntent(MainActivity.this, currency);
//        Intent intent = new Intent(activity, CurrencyRateQueryActivity.class);
        startActivityForResult(intent, REQUEST_CODE_CURRENCY);
    }

    void bitcoin_rate(String currency) {
        L.d(TAG, "Запускаем дочернюю активность.");
        L.d(TAG, "Запрашиваем курс " + currency);
//            CurrencyRateQueryDialog currencyRateQueryDialog = new CurrencyRateQueryDialog();
//            currencyRateQueryDialog.show(manager, "dialog");
        Intent intent = CurrencyRateQueryActivity.newIntent(MainActivity.this, currency);
//        Intent intent = new Intent(activity, CurrencyRateQueryActivity.class);
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
//                    return;
                }
                switch (resultCode) {
                    case (Activity.RESULT_OK):
                        L.d(TAG, "Выход из дочерней активности RESULT_OK.");
                        String currency_rate = null;
                        if (data != null) {
                            currency_rate = CurrencyRateQueryActivity.getCurrencyRate(data);
                        }
                        L.d(TAG, "currency_rate = " + currency_rate);
//                        customToast.setToastText(getString(R.string.download_ok));
//                        customToast.show();
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
