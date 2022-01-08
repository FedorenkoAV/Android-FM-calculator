package ru.fmproject.android.calculator;

import android.content.Context;
import android.os.AsyncTask;

import android.widget.Button;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

public class XMLPullParser extends AsyncTask<String, String, String[]> { //AsyncTask<[Input_Parameter Type], [Progress_Report Type], [Result Type]>
    //XML Pull Parser - рекомендуемый парсер для Android-приложений

    private MyCustomCallBack callback;

    private static final String TAG = "XMLPullParser";

//    private static final String NO_CONNECTION = "java.lang.NullPointerException: Attempt to invoke virtual method 'java.lang.String java.net.HttpURLConnection.getResponseMessage()' on a null object reference";
    private static final String NO_CONNECTION = "java.net.UnknownHostException: Unable to resolve host \"www.cbr.ru\": No address associated with hostname";
    private static final String CONNECTION_TIMEOUT = "java.net.SocketTimeoutException: connect timed out";
    private static final String NO_ROUTE_TO_HOST = "java.net.NoRouteToHostException: No route to host";

    private final ThreadLocal<TextView> contentView = new ThreadLocal<TextView>();
//    private final ThreadLocal<Button> mButton = new ThreadLocal<Button>();
    private Context context; //нужно только для тоста

    public interface MyCustomCallBack {
        void getResult(String[] result);
    }

    XMLPullParser(Context context, TextView contentView, final MyCustomCallBack callback) {
        super();
        this.contentView.set(contentView);
        this.context = context;
        this.callback = callback;
    }

    XMLPullParser(Context context, final MyCustomCallBack callback) {
        super();
        this.contentView.set(null);
        this.context = context;
        this.callback = callback;
    }

    private void printf(String printString) {
        if (contentView.get() != null) {
            contentView.get().append(printString + "\n");
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        printf("Загрузка...");
    }

    @Override
    protected String[] doInBackground(String... path) {
        L.d(TAG, "doInBackground() запущен.");
        String date = "";
        String kurs = "";
        String[] result = {"", "", ""};
        String currVal = getCurrencyValue(path[1]);
        URL url = null;
        HttpsURLConnection httpsUrlConnection = null;
        HttpURLConnection httpUrlConnection = null;
        InputStream inputStream = null;
        try {
            url = new URL("https://" + path[0]);
            onProgressUpdate("Загружаем данные по HTTPS.");
            L.d(TAG, "Загружаем данные по HTTPS.");
//            inputStream = httpsConnection(url);
//            HostnameVerifier hostnameVerifier = new HostnameVerifier() { //Создадим объект класса HostnameVerifer, который будет отвечать за проверку сертификата.
//                @Override
//                public boolean verify(String hostname, SSLSession session) {//В методе verify мы проверяем соответствие сертификата домену web узла.
//                    HostnameVerifier hv = HttpsURLConnection.getDefaultHostnameVerifier();
//                    return hv.verify("www.cbr.ru/scripts/XML_daily.asp", session);
//                }
//            };
            httpsUrlConnection = (HttpsURLConnection) url.openConnection();//создаём экземпляр класса HttpsURLConnection
//            httpsUrlConnection.setHostnameVerifier(hostnameVerifier);// и устанавливаем созданный нами объект HostnameVerifier в качестве проверяющего для данного конкретного соединения.
//            httpsUrlConnection.setRequestMethod("GET");// установка метода получения данных -GET
            httpsUrlConnection.setReadTimeout(10000);// установка таймаута перед выполнением - 10 000 миллисекунд
            httpsUrlConnection.setConnectTimeout(10000);// установка таймаута перед выполнением - 10 000 миллисекунд
            if (isCancelled()) {
                result[0] = "CANCELLED";
                return result;
            }
            httpsUrlConnection.connect();// подключаемся к ресурсу
            if (isCancelled()) {
                result[0] = "CANCELLED";
                return result;
            }
//                    InputStream inputStream = url.openStream();

//            L.d(TAG, "AllowUserInteraction: " + httpsUrlConnection.getAllowUserInteraction());
//            L.d(TAG, "ConnectTimeout: " + httpsUrlConnection.getConnectTimeout());
//            L.d(TAG, "ContentEncoding: " + httpsUrlConnection.getContentEncoding());
//            L.d(TAG, "ContentLength: " + httpsUrlConnection.getContentLength());
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                L.d(TAG, "ContentLengthLong: " + httpsUrlConnection.getContentLengthLong());
//            }
////            L.d(TAG, "Date: " + httpsUrlConnection.getDate());
//            Date d = new Date(httpsUrlConnection.getDate());
//            L.d(TAG, "Date: " + new Date(httpsUrlConnection.getDate()));
//            L.d(TAG, "DefaultUseCaches: " + httpsUrlConnection.getDefaultUseCaches());
//            L.d(TAG, "Expiration: " + httpsUrlConnection.getExpiration());
//            L.d(TAG, "IfModifiedSince: " + httpsUrlConnection.getIfModifiedSince());
//            L.d(TAG, "InstanceFollowRedirects: " + httpsUrlConnection.getInstanceFollowRedirects());
//            L.d(TAG, "LastModified: " + httpsUrlConnection.getLastModified());
//            L.d(TAG, "ReadTimeout: " + httpsUrlConnection.getReadTimeout());
//            L.d(TAG, "RequestMethod: " + httpsUrlConnection.getRequestMethod());
//            L.d(TAG, "ResponseCode: " + httpsUrlConnection.getResponseCode());
//            L.d(TAG, "ResponseMessage: " + httpsUrlConnection.getResponseMessage());
//            L.d(TAG, "UseCaches: " + httpsUrlConnection.getUseCaches());
//
//            L.d(TAG, "HeaderFields: ");
//            Map<String, List<String>> headerFields = httpsUrlConnection.getHeaderFields();
//            for (Map.Entry<String, List<String>> pair : headerFields.entrySet()) {
//                String key = pair.getKey();
//                L.d(TAG, "Key: " + key);
//                List<String> value = pair.getValue();
//                for (String lv : value) {
//                    L.d(TAG, "Value: " + lv);
//                }
//            }
            if (!url.getHost().equals(httpsUrlConnection.getURL().getHost())) {
                // we were redirected! Kick the user out to the browser to sign on?
                // мы были перенаправлены! Необходимо отправить пользователя в браузер для входа в систему?
                L.d(TAG, "мы были перенаправлены! Необходимо отправить пользователя в браузер для входа в систему?");
                //            throw new MyExceptions(MyExceptions.REDIRECT);
                throw new IOException("We were redirected from " + httpsUrlConnection.getURL().getHost() + " to " + url.getHost() + ". Go to browser to sign on.");
            }
            if (httpsUrlConnection.getResponseCode() != HttpsURLConnection.HTTP_OK) {
                throw new IOException(httpsUrlConnection.getResponseMessage() + ": with " + url);
            }
            inputStream = httpsUrlConnection.getInputStream();
            if (isCancelled()) {
                result[0] = "CANCELLED";
                return result;
            }
        } catch (Exception e) {
//                printf("Не удалось выполнить операцию");
            L.d(TAG, "Произошла ошибка: " + e);
//            L.d(TAG, "Message: " + e.getMessage());
//            L.d(TAG, "LocalizedMessage: " + e.getLocalizedMessage());
            L.d(TAG, "StackTraceElement:");
            StackTraceElement[] stackTraceElements = e.getStackTrace();
            for (int i = 0; i < stackTraceElements.length; i++) {
                L.d(TAG, i + ": " + stackTraceElements[i].toString());
            }
            result[0] = e.toString();
//            return result;
            if (httpsUrlConnection != null) {
                httpsUrlConnection.disconnect();
                L.d(TAG, "httpsUrlConnection.disconnect() выполнен.");
            } else {
                L.d(TAG, "httpsUrlConnection не был создан, поэтому нечего закрывать.");
            }
        }
        if (inputStream == null) {
            onProgressUpdate("HTTPS соединение установить не удалось.");
            L.d(TAG, "HTTPS соединение установить не удалось.");
            try {
                url = new URL("http://" + path[0]);
                onProgressUpdate("Загружаем данные по HTTP.");
                L.d(TAG, "Загружаем данные по HTTP.");
//                inputStream = httpConnection(url);
                httpUrlConnection = (HttpURLConnection) url.openConnection();
//            httpsUrlConnection.setRequestMethod("GET");// установка метода получения данных -GET
                httpUrlConnection.setReadTimeout(10000);// установка таймаута перед выполнением - 10 000 миллисекунд
                httpUrlConnection.setConnectTimeout(10000);// установка таймаута перед выполнением - 10 000 миллисекунд
                if (isCancelled()) {
                    result[0] = "CANCELLED";
                    return result;
                }
                httpUrlConnection.connect();// подключаемся к ресурсу
                if (isCancelled()) {
                    result[0] = "CANCELLED";
                    return result;
                }
//                    InputStream inputStream = url.openStream();

//            L.d(TAG, "AllowUserInteraction: " + httpsUrlConnection.getAllowUserInteraction());
//            L.d(TAG, "ConnectTimeout: " + httpsUrlConnection.getConnectTimeout());
//            L.d(TAG, "ContentEncoding: " + httpsUrlConnection.getContentEncoding());
//            L.d(TAG, "ContentLength: " + httpsUrlConnection.getContentLength());
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                L.d(TAG, "ContentLengthLong: " + httpsUrlConnection.getContentLengthLong());
//            }
////            L.d(TAG, "Date: " + httpsUrlConnection.getDate());
//            Date d = new Date(httpsUrlConnection.getDate());
//            L.d(TAG, "Date: " + new Date(httpsUrlConnection.getDate()));
//            L.d(TAG, "DefaultUseCaches: " + httpsUrlConnection.getDefaultUseCaches());
//            L.d(TAG, "Expiration: " + httpsUrlConnection.getExpiration());
//            L.d(TAG, "IfModifiedSince: " + httpsUrlConnection.getIfModifiedSince());
//            L.d(TAG, "InstanceFollowRedirects: " + httpsUrlConnection.getInstanceFollowRedirects());
//            L.d(TAG, "LastModified: " + httpsUrlConnection.getLastModified());
//            L.d(TAG, "ReadTimeout: " + httpsUrlConnection.getReadTimeout());
//            L.d(TAG, "RequestMethod: " + httpsUrlConnection.getRequestMethod());
//            L.d(TAG, "ResponseCode: " + httpsUrlConnection.getResponseCode());
//            L.d(TAG, "ResponseMessage: " + httpsUrlConnection.getResponseMessage());
//            L.d(TAG, "UseCaches: " + httpsUrlConnection.getUseCaches());
//
//            L.d(TAG, "HeaderFields: ");
//            Map<String, List<String>> headerFields = httpsUrlConnection.getHeaderFields();
//            for (Map.Entry<String, List<String>> pair : headerFields.entrySet()) {
//                String key = pair.getKey();
//                L.d(TAG, "Key: " + key);
//                List<String> value = pair.getValue();
//                for (String lv : value) {
//                    L.d(TAG, "Value: " + lv);
//                }
//            }
                if (!url.getHost().equals(httpUrlConnection.getURL().getHost())) {
                    // we were redirected! Kick the user out to the browser to sign on?
                    // мы были перенаправлены! Необходимо отправить пользователя в браузер для входа в систему?
                    L.d(TAG, "мы были перенаправлены! Необходимо отправить пользователя в браузер для входа в систему?");
                    throw new IOException("We were redirected from " + httpUrlConnection.getURL().getHost() + " to " + url.getHost() + ". Go to browser to sign on.");
                }
                if (httpUrlConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    throw new IOException(httpUrlConnection.getResponseMessage() + ": with " + url);
                }
                inputStream = httpUrlConnection.getInputStream();
                if (isCancelled()) {
                    result[0] = "CANCELLED";
                    return result;
                }
            } catch (Exception e) {
//                printf("Не удалось выполнить операцию");
                L.d(TAG, "Произошла ошибка: " + e);
//            L.d(TAG, "Message: " + e.getMessage());
//            L.d(TAG, "LocalizedMessage: " + e.getLocalizedMessage());
                L.d(TAG, "StackTraceElement:");
                StackTraceElement[] stackTraceElements = e.getStackTrace();
                for (int i = 0; i < stackTraceElements.length; i++) {
                    L.d(TAG, i + ": " + stackTraceElements[i].toString());
                }
                result[0] = e.toString();
                if (httpUrlConnection != null) {
                    httpUrlConnection.disconnect();
                    L.d(TAG, "httpUrlConnection.disconnect() выполнен.");
                } else {
                    L.d(TAG, "httpUrlConnection не был создан, поэтому нечего закрывать.");
                }
                onProgressUpdate("HTTP соединение установить не удалось.");
                L.d(TAG, "HTTP соединение установить не удалось.");
                return result;
            }
        }
        try {
            if (inputStream == null) {
                onProgressUpdate("HTTP соединение установить не удалось.");
                L.d(TAG, "HTTP соединение установить не удалось.");
                throw new IOException(httpUrlConnection.getResponseMessage() + ": with " + url);
            }
            XmlPullParserFactory factory = null;
            factory = XmlPullParserFactory.newInstance();
            //factory.setNamespaceAware(true); // если используется пространство имён
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(inputStream, null);
            // продолжаем, пока не достигнем конца документа
            boolean myValute = false;
            boolean myValue = false;
            while (parser.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (isCancelled()) {
                    result[0] = "CANCELLED";
                    return result;
                }
                if (parser.getEventType() == XmlPullParser.START_TAG) {
                    if (parser.getName().equalsIgnoreCase("ValCurs")) {
                        date = parser.getAttributeValue(0);
                    }
                    if (myValute && parser.getName().equalsIgnoreCase("Value")) {
                        myValue = true;
                    }
                }

                if (parser.getEventType() == XmlPullParser.START_TAG) {
                    if (parser.getName().equalsIgnoreCase("Valute")) {
                        if (parser.getAttributeValue(0).equalsIgnoreCase(currVal)) {//EUR = R01239, USD = R01235
                            myValute = true;
                        }
                    }
                    if (myValute && parser.getName().equalsIgnoreCase("Value")) {
                        myValue = true;
                    }
                }

                if (myValute && myValue && parser.getEventType() == XmlPullParser.TEXT) {
                    kurs = parser.getText();
                    break;
                }
//                    String tmp = "";
//                    switch (parser.getEventType()) {
//                        case XmlPullParser.START_DOCUMENT:
//                            L.d(TAG, "Начало документа");
//                            break;
//                        // начало тэга
//                        case XmlPullParser.START_TAG:
//                            L.d(TAG,"START_TAG: имя тега = " + parser.getName()
//                                            + ", уровень = " + parser.getDepth()
//                                            + ", число атрибутов = "
//                                            + parser.getAttributeCount());
//                            tmp = "";
//                            for (int i = 0; i < parser.getAttributeCount(); i++) {
//                                tmp = tmp + parser.getAttributeName(i) + " = "
//                                        + parser.getAttributeValue(i) + ", ";
//                            }
//                            if (!TextUtils.isEmpty(tmp))
//                                L.d(TAG, "Атрибуты: " + tmp);
//                            break;
//                        // конец тега
//                        case XmlPullParser.END_TAG:
//                            L.d(TAG, "END_TAG: имя тега = " + parser.getName());
//                            break;
//                        // содержимое тега
//                        case XmlPullParser.TEXT:
//                            L.d(TAG, "текст = " + parser.getText());
//                            break;
//
//                        default:
//                            break;
//                    }
                parser.next();
            }
        } catch (Exception e) {
//                printf("Не удалось выполнить операцию");
            L.d(TAG, "Произошла ошибка: " + e);
//            L.d(TAG, "Message: " + e.getMessage());
//            L.d(TAG, "LocalizedMessage: " + e.getLocalizedMessage());
            L.d(TAG, "StackTraceElement:");
            StackTraceElement[] stackTraceElements = e.getStackTrace();
            for (int i = 0; i < stackTraceElements.length; i++) {
                L.d(TAG, i + ": " + stackTraceElements[i].toString());
            }
            result[0] = e.toString();
            return result;
        } finally {
            if (httpsUrlConnection != null) {
                httpsUrlConnection.disconnect();
                L.d(TAG, "httpsUrlConnection.disconnect() выполнен.");
            } else {
                L.d(TAG, "httpsUrlConnection не был создан, поэтому нечего закрывать.");
            }
            if (httpUrlConnection != null) {
                httpUrlConnection.disconnect();
                L.d(TAG, "httpUrlConnection.disconnect() выполнен.");
            } else {
                L.d(TAG, "httpUrlConnection не был создан, поэтому нечего закрывать.");
            }
        }
        result[0] = "OK";
        result[1] = date;
        result[2] = kurs;
        return result;
    }

    @Override
    protected void onPostExecute(String... result) {
        L.d(TAG, "onPostExecute() запущен.");
//        for (String content : result) {
//            printf(content);
//        }
        if (!result[0].equals("OK")) {
            if (result[0].contains(NO_CONNECTION)) {
                printf("Ошибка! Не подключен интернет.");
            } else
            if (result[0].contains(CONNECTION_TIMEOUT)) {
                printf("Ошибка! Таймаут подключения.");
            } else
            if (result[0].contains(NO_ROUTE_TO_HOST)) {
                printf("Ошибка! Не могу получить маршрут до хоста.");
            } else {
                printf(result[0]);
            }

        }
        if (callback != null) {
            callback.getResult(result);
        }
//        Toast.makeText(context, "Данные загружены", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onProgressUpdate(String... values) {
        L.d(TAG, "onProgressUpdate(String... values) запущен.");
        super.onProgressUpdate(values);
        for (String currStr : values) {
            printf(currStr);
        }
    }

    @Override
    protected void onCancelled() {
        L.d(TAG, "onCancelled() запущен.");
        super.onCancelled();
        printf("Отмена запроса.");
    }

    private String getCurrencyValue(String currName) {
        String currVal = "";
        switch (currName) {
            case "USD":
                currVal = "R01235";
                break;
            case "EUR":
                currVal = "R01239";
                break;
        }
        return currVal;
    }
}
