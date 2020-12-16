package com.github.loglib.api;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * Created by lvming on 12/14/20 7:02 PM.
 * Email: lvming@guazi.com
 * Description:
 */
public abstract class BasePrinter implements IPrinter {

    private static final String TAG = BasePrinter.class.getSimpleName();
    private static final int JSON_INDENT = 2;

    @Override
    public void w(String tag, String msg, Throwable tr) {
        if (TextUtils.isEmpty(msg)) {
            msg = "";
        }
        msg += "\n" + android.util.Log.getStackTraceString(tr);
        w(tag, msg);
    }

    @Override
    public void e(String tag, String msg, Throwable tr) {
        if (TextUtils.isEmpty(msg)) {
            msg = "";
        }
        msg += "\n" + android.util.Log.getStackTraceString(tr);
        e(tag, msg);
    }

    @Override
    public void json(String json) {
        if (TextUtils.isEmpty(json)) {
            d(TAG, "Empty/Null json content");
            return;
        }
        try {
            json = json.trim();
            if (json.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(json);
                String message = jsonObject.toString(JSON_INDENT);
                d(TAG, message);
                return;
            }
            if (json.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(json);
                String message = jsonArray.toString(JSON_INDENT);
                d(TAG, message);
                return;
            }
            e(TAG, "Invalid Json");
        } catch (JSONException e) {
            e(TAG, "Invalid Json");
        }
    }

    @Override
    public void xml(String xml) {
        if (TextUtils.isEmpty(xml)) {
            d(TAG, "Empty/Null xml content");
            return;
        }
        try {
            Source xmlInput = new StreamSource(new StringReader(xml));
            StreamResult xmlOutput = new StreamResult(new StringWriter());
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(xmlInput, xmlOutput);
            d(TAG, xmlOutput.getWriter().toString().replaceFirst(">", ">\n"));
        } catch (TransformerException e) {
            e(TAG, "Invalid xml");
        }
    }
}
