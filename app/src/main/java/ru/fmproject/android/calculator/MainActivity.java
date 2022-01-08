package ru.fmproject.android.calculator;

import android.app.Activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.navigation.NavigationView;

import androidx.fragment.app.FragmentManager;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.DisplayMetrics;
import android.view.ContextMenu;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.HashMap;
import java.util.Map;

import ru.fmproject.android.calculator.editors.EditXDec;
import ru.fmproject.android.calculator.input.InputControl;

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
    public static final int EDIT_X_DEC = 8;
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

    Button btnShift;
    Button btnHyp;
    Button btnInv;
    Button btnOff;
    Button btnOnC;

    Button btnDel;
    Button btnSci;
    Button btnDrg;
    Button btnSin;
    Button btnCos;
    Button btnTan;

    Button btnExp;
    Button btnXPowY;
    Button btnSqr;
    Button btnToRad;
    Button btnLn;
    Button btnLog;

    Button btnX2;
    Button btnA;
    Button btnB;
    Button btnOpenBracket;
    Button btnCloseBracket;
    Button btnXToMem;

    Button btn7;
    Button btn8;
    Button btn9;
    Button btnMemPlus;
    Button btnMemRead;

    Button btn4;
    Button btn5;
    Button btn6;
    Button btnMult;
    Button btnDiv;

    Button btn1;
    Button btn2;
    Button btn3;
    Button btnPlus;
    Button btnMinus;

    Button btn0;
    Button btnDot;
    Button btnSign;
    Button btnCalc;
    Button btnCe;

    TextView tvShift;
    TextView tvHyp;
    TextView tvDeg;
    TextView tvRad;
    TextView tvGrad;
    TextView tvBracket;
    TextView tvBin;
    TextView tvOct;
    TextView tvHex;
    TextView tvCplx;
    TextView tvSd;
    ImageView tvMemory;
    TextView tvError;
    TextView tvDisplay;
    TextView tvProtocol;

    Bundle savedInstanceState;

    Protocol protocol;
    CustomToast customToast;
    Angle angle;
    StackCalculator stackCalculator;
    MainDisplay mainDisplay;
    EditXDec editXDec;

    Status status;
    MemoryStore memoryStore;
//    InputDriver inputDriver;
    InputControl inputControl;

    Object[] objStore = new Object[19];
    Map<String, Object> classesMap = new HashMap<>();
    ClipboardManager clipboardManager;
    ClipData clipData;

    private static final int UI_ANIMATION_DELAY = 100;
    private static final int ADS_DELAY = 30000;
    private final Handler mHideHandler = new Handler();
    private final Handler mAdsHandler = new Handler();
    private View mContentView;
    private final Runnable mHidePart2Runnable = () -> {
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

    private final Runnable mAdsRunnable = this::ads;

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
            classesMap.put("mainActivity", activity);
//        Создаем свой тост https://developer.android.com/guide/topics/ui/notifiers/toasts.html
            customToast = new CustomToast(activity, "В разработке");
        } catch (Exception e) {
            L.d(TAG, "При старте приложения произошла ошибка: " + e);
            L.printStackTrace(e);
            this.onDestroy();
        }

        try {
            objStore[TOAST] = customToast;
            classesMap.put("customToast", customToast);
            L.d(TAG, "Создали свой тост https://developer.android.com/guide/topics/ui/notifiers/toasts.html");
            tvDisplay = findViewById(R.id.display);
            tvDisplay.setOnCreateContextMenuListener(this);

//        Находим метки статуса и режима работы
            L.d(TAG, "Находим метки статуса и режима работы");
            tvShift = findViewById(R.id.status_shift);
            tvHyp = findViewById(R.id.status_hyp);
            tvDeg = findViewById(R.id.status_deg);
            tvRad = findViewById(R.id.status_rad);
            tvGrad = findViewById(R.id.status_grad);
            tvBracket = findViewById(R.id.status_bracket);
            tvBin = findViewById(R.id.status_bin);
            tvOct = findViewById(R.id.status_oct);
            tvHex = findViewById(R.id.status_hex);
            tvCplx = findViewById(R.id.status_cplx);
            tvSd = findViewById(R.id.status_sd);
            tvMemory = findViewById(R.id.memory);
            tvError = findViewById(R.id.error);

//        Находим кнопки
            L.d(TAG, "Находим кнопки");
            btnShift = findViewById(R.id.FuncButtonShift);
            btnHyp = findViewById(R.id.FuncButtonHyp);
            btnInv = findViewById(R.id.FuncButtonGear);
            btnOff = findViewById(R.id.FuncButtonOff);
            btnOnC = findViewById(R.id.FuncButtonOnC);
            btnDel = findViewById(R.id.FuncButtonDel);
            btnSci = findViewById(R.id.FuncButtonSci);
            btnDrg = findViewById(R.id.FuncButtonDrg);
            btnSin = findViewById(R.id.FuncButtonSin);
            btnCos = findViewById(R.id.FuncButtonCos);
            btnTan = findViewById(R.id.FuncButtonTan);
            btnExp = findViewById(R.id.FuncButtonExp);
            btnXPowY = findViewById(R.id.FuncButtonXpowY);
            btnSqr = findViewById(R.id.FuncButtonSqr);
            btnToRad = findViewById(R.id.FuncButtonToRad);
            btnLn = findViewById(R.id.FuncButtonLn);
            btnLog = findViewById(R.id.FuncButtonLog);
            btnX2 = findViewById(R.id.FuncButtonX2);
            btnA = findViewById(R.id.FuncButtonA);
            btnB = findViewById(R.id.FuncButtonB);
            btnOpenBracket = findViewById(R.id.FuncButtonOpenBracket);
            btnCloseBracket = findViewById(R.id.FuncButtonCloseBracket);
            btnXToMem = findViewById(R.id.FuncButtonXtoM);

            btn7 = findViewById(R.id.Button07);
            btn8 = findViewById(R.id.Button08);
            btn9 = findViewById(R.id.Button09);
            btnMemPlus = findViewById(R.id.ButtonMem);
            btnMemRead = findViewById(R.id.ButtonMR);
            btn4 = findViewById(R.id.Button04);
            btn5 = findViewById(R.id.Button05);
            btn6 = findViewById(R.id.Button06);
            btnMult = findViewById(R.id.ButtonMult);
            btnDiv = findViewById(R.id.ButtonDiv);
            btn1 = findViewById(R.id.Button01);
            btn2 = findViewById(R.id.Button02);
            btn3 = findViewById(R.id.Button03);
            btnPlus = findViewById(R.id.ButtonPlus);
            btnMinus = findViewById(R.id.ButtonMinus);
            btn0 = findViewById(R.id.Button0);
            btnDot = findViewById(R.id.ButtonDot);
            btnSign = findViewById(R.id.ButtonSign);
            btnCalc = findViewById(R.id.ButtonCalc);
            btnCe = findViewById(R.id.ButtonCE);

            Button[] btnStore = {btnShift, btnHyp, btnInv, btnOnC, btnOff, btnOnC,
                    btnDel, btnSci, btnDrg, btnSin, btnCos, btnTan,
                    btnExp, btnXPowY, btnSqr, btnToRad, btnLn, btnLog,
                    btnX2, btnA, btnB, btnOpenBracket, btnCloseBracket, btnXToMem,

                    btn7, btn8, btn9, btnMemPlus, btnMemRead,
                    btn4, btn5, btn6, btnMult, btnDiv,
                    btn1, btn2, btn3, btnPlus, btnMinus,
                    btn0, btnDot, btnSign, btnCalc, btnCe};


//        Создаем объект statusDisplay, который будет управлять отображением меток статуса и режима работы на дисплее
            TextView[] statusDisplayLabStore = {tvShift, tvHyp, tvDeg, tvRad, tvGrad, tvBracket, tvBin, tvOct, tvHex, tvCplx, tvSd, tvError};
            StatusDisplay statusDisplay = new StatusDisplay(this, statusDisplayLabStore, tvMemory);
            objStore[STATUS_DISPLAY] = statusDisplay;
            classesMap.put("statusDisplay", statusDisplay);

            L.d(TAG, "Создали объект statusDisplay, который будет управлять отображением меток статуса и режима работы на дисплее");

//          Создаем объект Preferences который будет читать и записывать настройки в файл
            Preferences preferences = new Preferences(this);
            L.d(TAG, "Создали объект preferences, который будет читать и записывать настройки в файл." + preferences);
            objStore[PREFERENCES] = preferences;
            classesMap.put("preferences", preferences);

//        Создаем объект status, который будет менять статусы и режимы работы, а так же текст в метках и кнопках
            status = new Status(statusDisplay, btnStore);
            L.d(TAG, "Создали объект status, который будет менять статусы и режимы работы" + status);
            objStore[STATUS] = status;
            classesMap.put("status", status);
//        Создаем объект mode, который будет менять режим работы на CPLX и SD
            Mode mode = new Mode(statusDisplay);
            L.d(TAG, "Создали объект mode, который будет менять режим работы на CPLX и SD");
            objStore[MODE] = mode;
            classesMap.put("mode", mode);
            tvProtocol = findViewById(R.id.protocol);
            tvProtocol.setSelected(true);

//        Создаем объект - FragmentManager
            FragmentManager manager = getSupportFragmentManager();
            objStore[FRAGMENT_MANAGER] = manager;
            classesMap.put("manager", manager);
            MyDialogFragment myDialogFragment = new MyDialogFragment();

            protocol = new Protocol(tvProtocol, mode, myDialogFragment, manager);
            objStore[PROTOCOL] = protocol;
            classesMap.put("protocol", protocol);
            L.d(TAG, "Создали объект protocol, который будет протоколировать вычисления");

//        Создаем объект angle, который будет менять удуницы измерения углов
            angle = new Angle(preferences, statusDisplay);
            L.d(TAG, "Создали объект angle, который будет менять удуницы измерения углов");
            objStore[ANGLE] = angle;
            classesMap.put("angle", angle);
//        Создаем объект memoryStore, который будет работать с памятью
            memoryStore = new MemoryStore(preferences, status);
            L.d(TAG, "Создали объект memoryStore, который будет работать с памятью");
            objStore[MEMORY] = memoryStore;
            classesMap.put("memoryStore", memoryStore);
//        Создаем объект mainDisplay, который будет выводить цифровую информацию
            mainDisplay = new MainDisplay(this, tvDisplay, preferences);
            L.d(TAG, "Создали объект mainDisplay, который будет выводить цифровую информацию");
            objStore[MAIN_DISPLAY] = mainDisplay;
            classesMap.put("mainDisplay", mainDisplay);
//        Создаем объект stackCalculator, который будет выполнять все вычисления
            stackCalculator = new StackCalculator(angle, protocol);
            L.d(TAG, "Создали объект stackCalculator, который будет выполнять все вычисления");
            objStore[STACK_CALCULATOR] = stackCalculator;
            classesMap.put("stackCalculator", stackCalculator);
//        Создаем объект editX, который будет отвечать за ввод всего
            editXDec = new EditXDec(objStore);
            L.d(TAG, "Создали объект editX, который будет отвечать за ввод всего");
            objStore[EDIT_X_DEC] = editXDec;
            classesMap.put("editX", editXDec);
//        Создаем объект - обработчик нажатия кнопок и вешаем его на кнопки
//            inputDriver = new InputDriver(objStore); //инициализируем clickListener
            inputControl = new InputControl(objStore); //инициализируем clickListener
            L.d(TAG, "Создали объект - обработчик нажатия кнопок и вешаем его на кнопки");
//            objStore[INPUT_DRIVER] = inputDriver;
            objStore[INPUT_DRIVER] = inputControl;
//            classesMap.put("inputDriver", inputDriver);
            classesMap.put("inputDriver", inputControl);
            for (Button button : btnStore) {
//                button.setOnClickListener(inputDriver);
                button.setOnClickListener(inputControl);
            }

            for (int i = 0; i < objStore.length; i++) {
                L.d(TAG, "В objStore[" + i + "]: " + objStore[i]);
            }
            delayedAds(ADS_DELAY);

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
//        clipData = ClipData.newPlainText("Text", inputDriver.copyToClipboard());
        clipData = ClipData.newPlainText("Text", inputControl.copyToClipboard());
        clipboardManager.setPrimaryClip(clipData);
    }

    void contextPaste() {
        ClipData data = clipboardManager.getPrimaryClip();
        ClipData.Item clipDataItem = null;
        if (data != null) {
            clipDataItem = data.getItemAt(0);
        }
        if (clipDataItem != null) {
//            inputDriver.pasteFromClipboard(clipDataItem.getText().toString());
            inputControl.pasteFromClipboard(clipDataItem.getText().toString());
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

    private void hide() {
        // Hide UI first
        L.d(TAG, "hide() запущен.");
        try {
            // узнаем размеры экрана из класса Display
            Display display = getWindowManager().getDefaultDisplay();
            DisplayMetrics metricsB = new DisplayMetrics();
            display.getMetrics(metricsB);
            mContentView = findViewById(R.id.fullscreen_content);
            if (Float.compare(metricsB.ydpi, 409.0f) < 0) {
                ActionBar actionBar = getSupportActionBar();
                if (actionBar != null) {
                    actionBar.hide();
                }
            }
            if (Float.compare(metricsB.ydpi, 320.0f) <= 0) {//Если высота экрана 320 и меньше, то убираем с экрана все
                // Schedule a runnable to remove the status and navigation bar after a delay
                mHideHandler.removeCallbacks(mHidePart2Runnable);
                mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
            }

        } catch (Exception e) {
            L.d(TAG, "Произошла ошибка в hide(): " + e);
            StackTraceElement[] stackTraceElements = e.getStackTrace();
            for (int i = 0; i < stackTraceElements.length; i++) {
                L.d(TAG, i + ": " + stackTraceElements[i].toString());
            }
            this.onDestroy();
        }
    }

    private void ads() {
        MobileAds.initialize(this, initializationStatus -> {
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
    }

    private void delayedAds(int delayMillis) {
        L.d(TAG, "delayedAds(int delayMillis) запущен.");
        mAdsHandler.removeCallbacks(mAdsRunnable);
        mAdsHandler.postDelayed(mAdsRunnable, delayMillis);
    }

    /**
     * Schedules a call to hide() in delay milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide() {
        L.d(TAG, "delayedHide(int delayMillis) запущен.");
        try {
            mHideHandler.removeCallbacks(mHideRunnable);
            mHideHandler.postDelayed(mHideRunnable, UI_ANIMATION_DELAY);
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
                default:
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

    public void currencyRate(String currency) {
        L.d(TAG, "Запускаем дочернюю активность.");
        L.d(TAG, "Запрашиваем курс " + currency);
        Intent intent = CurrencyRateQueryActivity.newIntent(MainActivity.this, currency);
        startActivityForResult(intent, REQUEST_CODE_CURRENCY);
    }

    public void bitcoinRate(String currency) {
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
                        editXDec.dollar(currency_rate);
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
