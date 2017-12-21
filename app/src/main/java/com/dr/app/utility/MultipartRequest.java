/**
 * @author saltinteractive
 */
package com.dr.app.utility;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import ch.boye.httpclientandroidlib.HttpEntity;
import ch.boye.httpclientandroidlib.entity.mime.MultipartEntityBuilder;
import ch.boye.httpclientandroidlib.entity.mime.content.FileBody;


public class MultipartRequest extends Request<String> {

    // private MultipartEntity entity = new MultipartEntity();

    private HttpEntity mHttpEntity;
    private MultipartEntityBuilder entity = MultipartEntityBuilder.create();
    HttpEntity httpentity;
    private static final String FILE_PART_NAME = "file";

    private final Response.Listener<String> mListener;
    private final Map<Integer, File> mFilePart;
    private final Map<String, String> mStringPart;

    public MultipartRequest(String file, String url, Response.Listener<String> listener,
                            Response.ErrorListener errorListener,
                            Map<String, String> mStringPart, final Map<Integer, File> FILES,
                            int Method) {
        super(Method, url, errorListener);

        mListener = listener;
        mFilePart = FILES;
        this.mStringPart = mStringPart;
        buildMultipartEntity(file);
    }

    public void addStringBody(String param, String value) {
        mStringPart.put(param, value);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> params = new HashMap<String, String>();
        if (!Pref.getUserID().equalsIgnoreCase("") && Pref.getIsLogin()) {
            UtilityPro.log("Token" + Pref.getUserID());
            params.put("Authorization", "Token " + Pref.getUserID());
        }
        return params;
    }

    private void buildMultipartEntity(String file) {
        String file_name = file;
//        if (file.equalsIgnoreCase("csv")) {
//            file_name = "csv";
//        } else {
//            file_name = "avatar";
//        }
        for (int i = 0; i < mFilePart.size(); i++) {
            entity.addPart(file_name, new FileBody(mFilePart.get(i)));
        }

        for (Map.Entry<String, String> entry : mStringPart.entrySet()) {
            entity.addTextBody(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public String getBodyContentType() {
        return httpentity.getContentType().getValue();
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            httpentity = entity.build();
            httpentity.writeTo(bos);
        } catch (IOException e) {
            VolleyLog.e("IOException writing to ByteArrayOutputStream");
        }
        return bos.toByteArray();
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String jsonString = new String(response.data);

        return Response.success(jsonString, getCacheEntry());
    }

    @Override
    protected void deliverResponse(String response) {

        mListener.onResponse(response);
    }
}