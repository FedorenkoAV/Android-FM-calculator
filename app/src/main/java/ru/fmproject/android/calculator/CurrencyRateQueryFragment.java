package ru.fmproject.android.calculator;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;


public class CurrencyRateQueryFragment extends Fragment implements View.OnLongClickListener, XMLPullParser.MyCustomCallBack {

    private static final String TAG = "FragmCurrencyRateQuery";

    private String error;
    private XMLPullParser mXMLPullParser;
    private ExmoParser mExmoParser;


    public interface SendDataToActivity {
        void getResult(String[] result);
    }

    ProgressBar mProgressBar;
    Button mButton;
    TextView tvMessage;
    Context context;

    @Override
    public void onAttach(Context context) { //Вызывается, когда фрагмент связывается с операцией (ему передается объект Activity)
        this.context = context;
        super.onAttach(context);
        L.d(TAG, "onAttach() запущен.");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        L.d(TAG, "onCreate() запущен.");
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {//Вызывается для создания иерархии представлений, связанной с фрагментом.
        L.d(TAG, "onCreateView() запущен.");
        View view = inflater.inflate(R.layout.fragment_currency_rate_query, container, false);
        tvMessage = view.findViewById(R.id.tvMessage);
        mButton = view.findViewById(R.id.button);

        mProgressBar = view.findViewById(R.id.progressBar);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle bundle) {//Вызывается, когда метод onCreate(), принадлежащий операции, возвращает управление.
        super.onActivityCreated(bundle);
        L.d(TAG, "onActivityCreated() запущен.");
    }

    @Override
    public void onViewStateRestored(Bundle bundle) {
        super.onViewStateRestored(bundle);
        L.d(TAG, "onViewStateRestored() запущен.");
    }

    @Override
    public void onStart() {
        super.onStart();
        L.d(TAG, "onStart() запущен.");
    }

    @Override
    public void onResume() {
        super.onResume();
        L.d(TAG, "onResume() запущен.");
    }

    @Override
    public void onPause() {
        super.onPause();
        L.d(TAG, "onPause() запущен.");
    }

    @Override
    public void onStop() {
        super.onStop();
        L.d(TAG, "onStop() запущен.");
    }

    @Override
    public void onDestroyView() {//Вызывается при удалении иерархии представлений, связанной с фрагментом.
        super.onDestroyView();
        L.d(TAG, "onDestroyView() запущен.");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        L.d(TAG, "onDestroy() запущен.");
    }

    @Override
    public void onDetach() {//Вызывается при разрыве связи фрагмента с операцией.
        super.onDetach();
        L.d(TAG, "onDetach() запущен.");
    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }

    //    private class ProgressTask extends AsyncTask<String, Void, String> {
//
//        @Override
//        protected void onPreExecute() {
//        }
//
//        @Override
//        protected String doInBackground(String... path) {
//            L.d(TAG, "doInBackground() запущен.");
//            return "";
//        }
//
////        @Override
////        protected void onProgressUpdate(Integer... values) {
////            super.onProgressUpdate(values);
////            L.d(TAG, "onProgressUpdate() запущен.");
////        }
//
//        @Override
//        protected void onPostExecute(String content) {
//            L.d(TAG, "onPostExecute() запущен.");
//        }
//
//        @Override
//        protected void onCancelled() {
//            super.onCancelled();
//            L.d(TAG, "onCancelled() запущен.");
//
//        }
//    }
    public void setCurrType(String currency) {//Здесь передаем в AsyncTask тип запрашиваемой валюты и запускаем AsyncTask
        L.d(TAG, "setCurrType(String currency) запущен.");
        switch (currency){
            case ("USD"):
            case ("EUR"):
                mXMLPullParser = new XMLPullParser(getActivity(), tvMessage, this);
                mXMLPullParser.execute("www.cbr.ru/scripts/XML_daily.asp", currency);
                mButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mXMLPullParser.cancel(true);
                    }
                });
                break;
            case "BTC_USD":
                mExmoParser = new ExmoParser(getActivity(), tvMessage, this);
                mExmoParser.execute("api.exmo.com/v1/ticker/", currency);
                mButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mExmoParser.cancel(true);
                    }
                });
                break;
        }

    }

    @Override
    public void getResult(String[] result) {//этот метод вызовется, когда у asynctask'а вызовется метод onPostExecute
        L.d(TAG, "getResult(String[] result) запущен.");
        error = result[0];
        if (error.equals("OK")) {
            SendDataToActivity sendDataToActivity = (SendDataToActivity) getActivity();
            sendDataToActivity.getResult(result);
            return;
        }
        mButton.setText("Понятно");
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        mProgressBar.setVisibility(android.view.View.GONE);
//        tvMessage.append(error);
    }

}
