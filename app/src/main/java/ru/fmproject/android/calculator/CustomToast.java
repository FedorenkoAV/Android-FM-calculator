package ru.fmproject.android.calculator;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by User on 14.06.2017.
 * CustomToast это класс в котором создается объект "Всплывающее сообщение"
 * Для разных платформ (Android, Windows) нужно переписывать этот класс, так как он платформозависимый
 */

public class CustomToast {
    //    Context context;
    private Activity activity;
    private LayoutInflater inflater;
    private View toastLayout;
    private Toast toast;
    private TextView toastText;
    private ImageView toastImage;

    public final static int IC_WARNING_AMBER = R.drawable.ic_warning_amber_24dp;
    public final static int IC_ERROR_RED_64PT = R.drawable.ic_error_red_24dp;


    public CustomToast(Activity activity, String textOnToast, int imageOnToast, int toastGravity, int xOffset, int yOffset, int toastDuration,float horizontalMargin, float verticalMargin) {
        //        Создаем свой тост https://developer.android.com/guide/topics/ui/notifiers/toasts.html
        this.activity = activity;
        inflater = (LayoutInflater) activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        toastLayout = inflater.inflate(R.layout.custom_toast, (ViewGroup) activity.findViewById(R.id.custom_toast_container));
        toastText = (TextView) toastLayout.findViewById(R.id.text);
        toastImage = (ImageView) toastLayout.findViewById(R.id.my_toast_image);
        toastText.setText(textOnToast);
        toastImage.setImageResource(imageOnToast); // устанавливаем на него картинку из ресурсов
        toast = new Toast(activity.getApplicationContext());
        toast.setGravity(toastGravity, xOffset, yOffset);
        toast.setDuration(toastDuration);
        toast.setMargin(horizontalMargin, verticalMargin);
        toast.setView(toastLayout);
    }

    public CustomToast(Activity activity, String textOnToast, int imageOnToast, int toastGravity, int xOffset, int yOffset, int toastDuration) {
        //        Создаем свой тост https://developer.android.com/guide/topics/ui/notifiers/toasts.html
        this.activity = activity;
        inflater = (LayoutInflater) activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        toastLayout = inflater.inflate(R.layout.custom_toast, (ViewGroup) activity.findViewById(R.id.custom_toast_container));
        toastText = (TextView) toastLayout.findViewById(R.id.text);
        toastImage = (ImageView) toastLayout.findViewById(R.id.my_toast_image);
        toastText.setText(textOnToast);
        toastImage.setImageResource(imageOnToast); // устанавливаем на него картинку из ресурсов
        toast = new Toast(activity.getApplicationContext());
        toast.setGravity(toastGravity, xOffset, yOffset);
        toast.setDuration(toastDuration);
        toast.setView(toastLayout);
    }

    public CustomToast(Activity activity, String textOnToast, int imageOnToast, int toastGravity, int xOffset, int yOffset) {
        //        Создаем свой тост https://developer.android.com/guide/topics/ui/notifiers/toasts.html
        this.activity = activity;
        inflater = (LayoutInflater) activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        toastLayout = inflater.inflate(R.layout.custom_toast, (ViewGroup) activity.findViewById(R.id.custom_toast_container));
        toastText = (TextView) toastLayout.findViewById(R.id.text);
        toastImage = (ImageView) toastLayout.findViewById(R.id.my_toast_image);
        toastText.setText(textOnToast);
        toastImage.setImageResource(imageOnToast); // устанавливаем на него картинку из ресурсов
        toast = new Toast(activity.getApplicationContext());
        toast.setGravity(toastGravity, xOffset, yOffset);
        toast.setView(toastLayout);
    }

    public CustomToast(Activity activity, String textOnToast, int imageOnToast) {
        //        Создаем свой тост https://developer.android.com/guide/topics/ui/notifiers/toasts.html
        this.activity = activity;
        inflater = (LayoutInflater) activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        toastLayout = inflater.inflate(R.layout.custom_toast, (ViewGroup) activity.findViewById(R.id.custom_toast_container));
        toastText = (TextView) toastLayout.findViewById(R.id.text);
        toastImage = (ImageView) toastLayout.findViewById(R.id.my_toast_image);
        toastText.setText(textOnToast);
        toastImage.setImageResource(imageOnToast); // устанавливаем на него картинку из ресурсов
        toast = new Toast(activity.getApplicationContext());
        toast.setView(toastLayout);
    }

    public CustomToast(Activity activity, String textOnToast) {
        //        Создаем свой тост https://developer.android.com/guide/topics/ui/notifiers/toasts.html
        this.activity = activity;
        inflater = (LayoutInflater) activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        toastLayout = inflater.inflate(R.layout.custom_toast, (ViewGroup) activity.findViewById(R.id.custom_toast_container));
        toastText = (TextView) toastLayout.findViewById(R.id.text);
        toastImage = (ImageView) toastLayout.findViewById(R.id.my_toast_image);
        toastText.setText(textOnToast);
        toast = new Toast(activity.getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(toastLayout);

    }

    public CustomToast(Activity activity) {
        //        Создаем свой тост https://developer.android.com/guide/topics/ui/notifiers/toasts.html
        this.activity = activity;
        inflater = (LayoutInflater) activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        toastLayout = inflater.inflate(R.layout.custom_toast, (ViewGroup) activity.findViewById(R.id.custom_toast_container));
        toastText = (TextView) toastLayout.findViewById(R.id.text);
        toastImage = (ImageView) toastLayout.findViewById(R.id.my_toast_image);
        toast = new Toast(activity.getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(toastLayout);
    }

    public void show() {
        toast.show();
    }

    public void setToastText(String newText) {
        toastText.setText(newText);
    }

    public void setToastImage(int imageOnToast) {
        toastImage.setImageResource(imageOnToast); // устанавливаем на него картинку из ресурсов
    }

    public void setToastGravity (int toastGravity, int xOffset, int yOffset) {
        toast.setGravity(toastGravity, xOffset, yOffset);
    }

    public void setToastMargin (float horizontalMargin, float verticalMargin) {
        toast.setMargin(horizontalMargin, verticalMargin);
    }
}
