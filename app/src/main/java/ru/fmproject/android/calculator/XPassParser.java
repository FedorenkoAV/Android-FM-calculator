package ru.fmproject.android.calculator;

import android.content.Context;
import android.os.AsyncTask;
import ru.fmproject.android.calculator.L;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

class XPassParser extends AsyncTask<String, Void, String> {

    private static final String TAG = "XPassParser";
    TextView contentView;
    Context context;

    public XPassParser(Context context, TextView contentView) {
        super();
        this.context = context;
        this.contentView = contentView;
    }

    @Override
    protected String doInBackground(String... path) {
        L.d(TAG, "doInBackground() запущен.");
        InputSource content;
        String kurs = "";
        String valute = "USD";
        try {
            content = getContent(path[0]);
            L.d(TAG, String.valueOf(content));
            kurs = getKursFromXml(content, valute);
            L.d(TAG, "kurs: " + kurs);
        } catch (IOException ex) {
            kurs = ex.getMessage();
        }
        return (valute + " = " + kurs);
    }

    @Override
    protected void onPostExecute(String content) {
        L.d(TAG, "onPostExecute() запущен.");
        contentView.setText(content);
        Toast.makeText(context, "Данные загружены", Toast.LENGTH_SHORT)
                .show();
    }

    private InputSource getContent(String path) throws IOException {
        L.d(TAG, "getContent() запущен.");
        BufferedReader reader = null;
        try {
            URL url = new URL(path);
            HttpsURLConnection c = (HttpsURLConnection) url.openConnection();
            c.setRequestMethod("GET");
            c.setReadTimeout(10000);
            c.connect();
            InputStream inputStream = c.getInputStream();
            return new InputSource(inputStream);
        } catch (Exception e) {
            L.d(TAG, "Произошла ошибка: " + e);
            L.d(TAG, "Message: " + e.getMessage());
            L.d(TAG, "LocalizedMessage: " + e.getLocalizedMessage());
            L.d(TAG, "StackTraceElement:");
            StackTraceElement[] stackTraceElements = e.getStackTrace();
            for (int i = 0; i < stackTraceElements.length; i++) {
                L.d(TAG, i + ": " + stackTraceElements[i].toString());
            }
        } finally {
        }
        return null;
    }

    private String getKursFromXml(InputSource xmlMessage, String valute) {
        String xPathString = "/ValCurs/Valute[CharCode=\""+valute+"\"]/Value"// Получить скомпилированный вариант XPath-выражения
//                    + "[starts-with(preceding-sibling::CharCode, '" + valute + "')"
//                    + " or "
//                    + "starts-with(following-sibling::CharCode, '" + valute + "')]"
                ;
        return findInXml(xmlMessage, xPathString);
    }

    private String getLastDateFromXml(InputSource xmlMessage) {
        String xPathString = "//GetLatestDateTimeResponse/GetLatestDateTimeResult";// Получить скомпилированный вариант XPath-выражения
        return findInXml(xmlMessage, xPathString);
    }

    // XPath - парсер XML-документов. Рекомендован W3C
    private String findInXml(InputSource xmlMessage, String xPathString) {
        try {
            L.d(TAG, "findInXml() запущен.");
            L.d(TAG, "xPathString: " + xPathString);

            XPathFactory pathFactory = XPathFactory.newInstance();// Создать XPathFactory
            XPath xpath = pathFactory.newXPath();// Создать XPath
            XPathExpression expr = xpath.compile(xPathString);
            NodeList nodes = (NodeList) expr.evaluate(xmlMessage, XPathConstants.NODESET);// Применить XPath-выражение к документу для поиска нужных элементов
            L.d(TAG, "nodes.getLength:" + nodes.getLength());
            if (nodes.getLength() == 1) {
                Node n = nodes.item(0);
                return n.getTextContent();
            }
        } catch (XPathExpressionException e) {
            L.d(TAG, "Произошла ошибка XPathExpressionException: " + e);
            L.d(TAG, "Message: " + e.getMessage());
            L.d(TAG, "LocalizedMessage: " + e.getLocalizedMessage());
            L.d(TAG, "StackTraceElement:");
            StackTraceElement[] stackTraceElements = e.getStackTrace();
            for (int i = 0; i < stackTraceElements.length; i++) {
                L.d(TAG, i + ": " + stackTraceElements[i].toString());
            }
        } catch (DOMException e) {
            L.d(TAG, "Произошла ошибка DOMException: " + e);
            L.d(TAG, "Message: " + e.getMessage());
            L.d(TAG, "LocalizedMessage: " + e.getLocalizedMessage());
            L.d(TAG, "StackTraceElement:");
            StackTraceElement[] stackTraceElements = e.getStackTrace();
            for (int i = 0; i < stackTraceElements.length; i++) {
                L.d(TAG, i + ": " + stackTraceElements[i].toString());
            }
        }
        return "";
    }
}
