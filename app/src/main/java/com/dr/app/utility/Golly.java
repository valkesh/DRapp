/**
 * @author Valkesh patel
 */
package com.dr.app.utility;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dr.app.R;
import com.dr.app.crashreport.ReportHandler;
import com.dr.app.fragment.FragmentYouNotification;
import com.dr.app.fragment.FragmentYourEventNotification;
import com.dr.app.widgets.LoaderDarkView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Field;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.transform.ErrorListener;

import static com.dr.app.utility.CustomPostRequest.createSslSocketFactory;


public class Golly extends Application {

    // Properties
    private static String BASE_URL = Constants.BASE_URL;
    private static boolean SUPPORT_OFFLINE = true;
    // private static boolean SHOW_URL_IN_LOG = true;

    public static final String TAG = Golly.class.getSimpleName();
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private static Golly mInstance;
    private static int PRIORITY = 0;
    public static Context context;
    public static Activity thiss;
    static Dialog d;
    static Dialog d_dark;
    static ArrayList<String> Unwanted_messages;
    static ArrayList<String> Required_messages;
    public static ImageLoader imageLoader;
    String defaultsTabs = "{\"status\": true,\"message\": \"Category List\",\"result\": [{\"category_id\": 3,\"category_name\": \"DANCE\",\"category_image\": \"\"},{\"category_id\": 4,\"category_name\": \"MUSIC\",\"category_image\": \"\"},{\"category_id\": 5,\"category_name\": \"STYLE\",\"category_image\": \"\"},{\"category_id\": 6,\"category_name\": \"HOT\",\"category_image\": \"\"}]}";
    public static DisplayImageOptions options;
    public static DisplayImageOptions options_profile;
    public static int notificaiton_count = 0;

    public static FragmentYourEventNotification.OnnotificationCount onNotificationValue;
    public static FragmentYouNotification.OnYourNotificationCount onYourNotificationCount;

    public interface GollyListner {
        void successResponce(String response, String TAG,
                             Boolean FROM_CAHCE);

        void errorResponce();
    }

    public interface MyNotificationListener {
        // you can define any parameter as per your requirement
        public void callback(int count);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // setDefaultFont(this, "MONOSPACE", "Pacifico.ttf");
        mInstance = this;
        context = getApplicationContext();
        Unwanted_messages = new ArrayList<String>();
        Unwanted_messages.add("Successful");
        Required_messages = new ArrayList<String>();
        Required_messages.add("");
        ReportHandler.install(this, "dump.zealousys@gmail.com");

        if (imageLoader == null) {
            imageLoader = ImageLoader.getInstance();
            imageLoader.init(ImageLoaderConfiguration.createDefault(context));
            options = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.ic_launcher)
                    .showImageForEmptyUri(R.drawable.ic_launcher)
                    .showImageOnFail(R.drawable.ic_launcher)
                    .cacheInMemory(true)
                    .cacheOnDisk(true).considerExifParams(true)
                    .bitmapConfig(Bitmap.Config.RGB_565).build();


            options_profile = new DisplayImageOptions.Builder()
//                .displayer(new RoundedBitmapDisplayer(140))
                    .showImageOnLoading(R.drawable.profile_img_placeholder)
                    .showImageForEmptyUri(R.drawable.profile_img_placeholder)
                    .showImageOnFail(R.drawable.profile_img_placeholder).cacheInMemory(true)
                    .cacheOnDisk(true).considerExifParams(true)
                    .bitmapConfig(Bitmap.Config.RGB_565).build();

        }

        // ReportHandler.install(this, "dump.zealousys@gmail.com");

    }

    public static void showProgressDialog(Activity activity) {

        if (d != null) {
            d = null;
        }
        d = new Dialog(activity);
//        if (d == null) {
//        d = new Dialog(activity);
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);

        d.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        d.getWindow().setLayout(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        Window window = d.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        window.setAttributes(wlp);
//        ProgressWheel wheel = new ProgressWheel(activity);
        LoaderDarkView loader = new LoaderDarkView(activity);

        // wheel.setBarColor(activity.getResources().getColor(R.color.setting_icon));
        d.setCancelable(false);
        //wheel.spin();
        d.setContentView(loader);
//        }
        d.show();
    }




    public static void hideProgressDialog() {
        try {
            if (d != null) {
                d.dismiss();
            }
        } catch (Exception e) {

        }
    }


    public static void showDarkProgressDialogAct(Context activity) {

        if (d_dark != null) {
            d_dark = null;
        }
        d_dark = new Dialog(activity);
        d_dark.requestWindowFeature(Window.FEATURE_NO_TITLE);

        d_dark.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        d_dark.getWindow().setLayout(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        Window window = d_dark.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        window.setAttributes(wlp);
        ProgressWheel wheel = new ProgressWheel(activity);
        // LoaderDarkView loader = new LoaderDarkView(activity);
        wheel.setBarColor(activity.getResources().getColor(R.color.colorWhite));
        d_dark.setCancelable(false);
        wheel.spin();
        d_dark.setContentView(wheel);
        d_dark.show();
    }

    public static void showDarkProgressDialog(Activity activity) {

        if (d_dark != null) {
            d_dark = null;
        }
        d_dark = new Dialog(activity);
        d_dark.requestWindowFeature(Window.FEATURE_NO_TITLE);

        d_dark.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        d_dark.getWindow().setLayout(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        Window window = d_dark.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        window.setAttributes(wlp);
        ProgressWheel wheel = new ProgressWheel(activity);
        // LoaderDarkView loader = new LoaderDarkView(activity);
        wheel.setBarColor(activity.getResources().getColor(R.color.colorWhite));
        d_dark.setCancelable(false);
        wheel.spin();
        d_dark.setContentView(wheel);
        d_dark.show();
    }

    public static void hideDarkProgressDialog() {
        try {
            if (d_dark != null) {
                d_dark.dismiss();
            }
        } catch (Exception e) {

        }
    }

    public static synchronized Golly getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext(), new HurlStack(null, createSslSocketFactory()));
            }
        return mRequestQueue;
    }


//    public ImageLoader getImageLoader() {
//        getRequestQueue();
//        if (mImageLoader == null) {
//            mImageLoader = new ImageLoader(this.mRequestQueue,
//                    new LruBitmapCache());
//        }
//        return this.mImageLoader;
//    }

    public static synchronized void Fire(String URL,
                                         final Map<String, String> PARAMS, int METHOD, final String TAG,
                                         final GollyListner listen) {
        String s = "" + BASE_URL + "" + URL + "?";

        for (Map.Entry<String, String> entry : PARAMS.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            s = s + "" + key + "=" + value + "&";
            // do stuff
        }
        UtilityPro.errorLogTrace("=====BASE_URL=======", s, true);

        final String url = s.substring(0, s.length() - 1);
        StringRequest jsonObjReq = new StringRequest(METHOD, BASE_URL + URL,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        UtilityPro.errorLogTrace("Parris responce of:::"
                                + "====" + TAG, response, true);

                        //Pref.setOfflineData(url, response);
                        try {
                            JSONObject responce_obj = new JSONObject(response);
                            if (responce_obj.getBoolean("status")
                                    || Required_messages.contains(responce_obj
                                    .getString("message"))) {

                                try {
                                    if (!Unwanted_messages
                                            .contains(responce_obj
                                                    .getString("message"))) {

                                        // UtilityPro.toast(responce_obj
                                        // .getString("message"));
                                    }
                                    listen.successResponce(
                                            responce_obj
                                                    .getJSONObject("result")
                                                    .toString(), TAG, false);
                                } catch (JSONException e) {
                                    try {
                                        if (!Unwanted_messages
                                                .contains(responce_obj
                                                        .getString("message"))) {
                                            // UtilityPro.toast(responce_obj
                                            // .getString("message"));
                                        }
                                        listen.successResponce(responce_obj
                                                .getJSONArray("result")
                                                .toString(), TAG, false);
                                    } catch (JSONException ew) {
                                        if (!Unwanted_messages
                                                .contains(responce_obj
                                                        .getString("message"))) {
                                            // UtilityPro.toast(responce_obj
                                            // .getString("message"));
                                        }
                                        listen.successResponce(
                                                responce_obj
                                                        .getString("result")
                                                        .toString(), TAG, false);
                                    }
                                }
                            } else {
                                UtilityPro.toast(responce_obj
                                        .getString("message"));
                                if (TAG.equalsIgnoreCase("FB_LOGIN_REQUEST")) {
                                    try {
//										Facebook clear logs
//										Session.getActiveSession()
//												.closeAndClearTokenInformation();
                                    } catch (Exception e) {

                                    }
                                }
                                if (!Unwanted_messages.contains(responce_obj
                                        .getString("message"))) {

                                }
                            }
                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                        Golly.hideProgressDialog();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Golly.hideProgressDialog();
                if (UtilityPro.isNetworkAvaliable(context)) {
                    UtilityPro.toast("Server is not responding");
                } else {
                    UtilityPro
                            .toast(context.getString(R.string.please_check_your_internet_connection));
                }
                listen.errorResponce();
                UtilityPro.errorLogTrace("Player Vacancy======Error of"
                        + "====" + TAG, error.toString(), true);
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                if (!Pref.getUserID().equalsIgnoreCase("") && Pref.getIsLogin()) {
                    UtilityPro.log(Constants.AUTH_TOKEN_KEY + Pref.getUserID());
                    params.put(Constants.AUTH_TOKEN_TYPE, Constants.AUTH_TOKEN_KEY + Pref.getUserID());
                }
                return params;
            }

            @Override
            protected Map<String, String> getParams() {

                return PARAMS;
            }

            @Override
            public Priority getPriority() {
                Priority priority = null;
                switch (PRIORITY) {
                    case 0:
                        priority = Priority.LOW;
                        break;
                    case 1:
                        priority = Priority.NORMAL;
                        break;
                    case 2:
                        priority = Priority.IMMEDIATE;
                        break;
                    case 3:
                        priority = Priority.HIGH;
                        break;
                    default:
                        priority = Priority.NORMAL;

                }
                return priority;
            }
        };

        if (Constants.SHOW_URL_IN_LOG) {
            UtilityPro.log(TAG + " request = " + url);
        }

        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        getInstance().addToRequestQueue(jsonObjReq, TAG);

        if (SUPPORT_OFFLINE) {

            if (!Pref.getOfflineData(s).equalsIgnoreCase("")) {
                try {
                    Log.d("Offline Data", "Cache responce of" + TAG + " = "
                            + Pref.getOfflineData(url));
                    try {
                        JSONObject responce_obj = new JSONObject(
                                Pref.getOfflineData(url));
                        if (responce_obj.getBoolean("status")) {
                            listen.successResponce(
                                    responce_obj.getJSONObject("result")
                                            .toString(), TAG, true);
                        } else {
                            UtilityPro.toast(responce_obj.getString("message"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }


    // Getting Full Responce of Json
    public static synchronized void FireWebCall(String URL,
                                                final Map<String, String> PARAMS, int METHOD, final String TAG,
                                                final GollyListner listen) {
        String s = "" + BASE_URL + "" + URL + "?";

        for (Map.Entry<String, String> entry : PARAMS.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            s = s + "" + key + "=" + value + "&";
            // do stuff
        }
        UtilityPro.errorLogTrace("=====BASE_URL=======", s, true);


        final String url = s.substring(0, s.length() - 1);
        StringRequest jsonObjReq = new StringRequest(METHOD, BASE_URL + URL,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        UtilityPro.errorLogTrace("Player Vacancy responce of:::"
                                + "====" + TAG, response, true);

                        Pref.setOfflineData(url, response);
                        listen.successResponce(response, TAG, false);
                        Golly.hideProgressDialog();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Golly.hideProgressDialog();
                if (UtilityPro.isNetworkAvaliable(context)) {
                    UtilityPro.toast("Server is not responding");
                } else {
                    UtilityPro
                            .toast(context.getString(R.string.please_check_your_internet_connection));
                }
                listen.errorResponce();

                UtilityPro.errorLogTrace("Player Vacancy======Error of"
                        + "====" + TAG, error.toString(), true);
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                if (!Pref.getUserID().equalsIgnoreCase("") && Pref.getIsLogin()) {
                    UtilityPro.log(Constants.AUTH_TOKEN_KEY + Pref.getUserID());
                    params.put(Constants.AUTH_TOKEN_TYPE, Constants.AUTH_TOKEN_KEY + Pref.getUserID());
                }
                return params;
            }

            @Override
            protected Map<String, String> getParams() {

                return PARAMS;
            }

            @Override
            public Priority getPriority() {
                Priority priority = null;
                switch (PRIORITY) {
                    case 0:
                        priority = Priority.LOW;
                        break;
                    case 1:
                        priority = Priority.NORMAL;
                        break;
                    case 2:
                        priority = Priority.IMMEDIATE;
                        break;
                    case 3:
                        priority = Priority.HIGH;
                        break;
                    default:
                        priority = Priority.NORMAL;

                }
                return priority;
            }
        };

        if (Constants.SHOW_URL_IN_LOG) {
            UtilityPro.log(TAG + " request = " + url);
        }

        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        getInstance().addToRequestQueue(jsonObjReq, TAG);

        if (SUPPORT_OFFLINE) {

            if (!Pref.getOfflineData(s).equalsIgnoreCase("")) {
                try {
                    Log.d("Offline Data", "Cache responce of" + TAG + " = "
                            + Pref.getOfflineData(url));
                    try {
                        JSONObject responce_obj = new JSONObject(
                                Pref.getOfflineData(url));
                        if (responce_obj.getBoolean("status")) {
                            listen.successResponce(
                                    responce_obj.getJSONObject("result")
                                            .toString(), TAG, true);
                        } else {
                            UtilityPro.toast(responce_obj.getString("message"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

    // Getting Full Responce of Json
    public static synchronized void FireDarkWebCall(String URL,
                                                    final Map<String, String> PARAMS, int METHOD, final String TAG,
                                                    final GollyListner listen) {
        String s = "" + BASE_URL + "" + URL + "?";

        for (Map.Entry<String, String> entry : PARAMS.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            s = s + "" + key + "=" + value + "&";
            // do stuff
        }
        UtilityPro.errorLogTrace("=====BASE_URL=======", s, true);


        final String url = s.substring(0, s.length() - 1);
        StringRequest jsonObjReq = new StringRequest(METHOD, BASE_URL + URL,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        UtilityPro.errorLogTrace("Player Vacancy responce of:::"
                                + "====" + TAG, response, true);

                        Pref.setOfflineData(url, response);
                        listen.successResponce(response, TAG, false);
                        Golly.hideDarkProgressDialog();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Golly.hideDarkProgressDialog();
                if (UtilityPro.isNetworkAvaliable(context)) {
                    UtilityPro.toast("Server is not responding");
                } else {
                    UtilityPro
                            .toast(context.getString(R.string.please_check_your_internet_connection));
                }
                listen.errorResponce();

                UtilityPro.errorLogTrace("Player Vacancy======Error of"
                        + "====" + TAG, error.toString(), true);
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                if (!Pref.getUserID().equalsIgnoreCase("") && Pref.getIsLogin()) {
                    UtilityPro.log(Constants.AUTH_TOKEN_KEY + Pref.getUserID());
                    params.put(Constants.AUTH_TOKEN_TYPE, Constants.AUTH_TOKEN_KEY + Pref.getUserID());
                }
                return params;
            }

            @Override
            protected Map<String, String> getParams() {

                return PARAMS;
            }

            @Override
            public Priority getPriority() {
                Priority priority = null;
                switch (PRIORITY) {
                    case 0:
                        priority = Priority.LOW;
                        break;
                    case 1:
                        priority = Priority.NORMAL;
                        break;
                    case 2:
                        priority = Priority.IMMEDIATE;
                        break;
                    case 3:
                        priority = Priority.HIGH;
                        break;
                    default:
                        priority = Priority.NORMAL;

                }
                return priority;
            }
        };

        if (Constants.SHOW_URL_IN_LOG) {
            UtilityPro.log(TAG + " request = " + url);
        }

        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        getInstance().addToRequestQueue(jsonObjReq, TAG);

        if (SUPPORT_OFFLINE) {

            if (!Pref.getOfflineData(s).equalsIgnoreCase("")) {
                try {
                    Log.d("Offline Data", "Cache responce of" + TAG + " = "
                            + Pref.getOfflineData(url));
                    try {
                        JSONObject responce_obj = new JSONObject(
                                Pref.getOfflineData(url));
                        if (responce_obj.getBoolean("status")) {
                            listen.successResponce(
                                    responce_obj.getJSONObject("result")
                                            .toString(), TAG, true);
                        } else {
                            UtilityPro.toast(responce_obj.getString("message"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static synchronized void multipartStringRequest(String PARAMS_NAME, String URL,
                                                           final Map<String, String> PARAMS, final Map<Integer, File> FILES,
                                                           int Method, final String TAG, final GollyListner listner) {
        String s = "" + BASE_URL + "" + URL + "?";

        for (Map.Entry<String, String> entry : PARAMS.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            s = s + "" + key + "=" + value + "&";
            // do stuff
        }
        final String url = s.substring(0, s.length() - 1);
        System.out.println("========m part url===" + url);
        UtilityPro.log("TAG " + TAG + " = " + URL);
        MultipartRequest jsonObjReq = new MultipartRequest(PARAMS_NAME, URL,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.d("Just spots log", "responce of " + TAG + " = "
                                + response);
                        try {
                            JSONObject ob = new JSONObject(response);
                            listner.successResponce(ob.toString(), TAG, false);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Golly.hideDarkProgressDialog();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Just spots log",
                        "error of" + TAG + " = " + error.toString());
                error.printStackTrace();
                Golly.hideDarkProgressDialog();
            }
        }, PARAMS, FILES, Method);


        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        getInstance().addToRequestQueue(jsonObjReq, TAG);

    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

//    public static void setImage(ImageView image_view, String URL,
//                                int default_image_resource) {
//        try {
//
//            ImageLoader imageLoader = getInstance().getImageLoader();
//            imageLoader
//                    .get(BASE_URL + URL, ImageLoader.getImageListener(
//                            image_view, default_image_resource,
//                            default_image_resource));
//
//        } catch (Exception e) {
//
//        }
//    }

    public static void setImage(final ImageView image_view, String URL,
                                String app_type) {

        try {
            Target target = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap,
                                           Picasso.LoadedFrom from) {

                    image_view.setImageBitmap(bitmap);
                }

                @Override
                public void onBitmapFailed(Drawable arg0) {

                }

                @Override
                public void onPrepareLoad(Drawable arg0) {

                }
            };
            if (image_view.getTag() == null || !image_view.getTag().equals(URL)) {
                image_view.setImageBitmap(null);
                try {

                    if (app_type.equalsIgnoreCase("app")) {

                        Picasso.with(context)
                                .load(Constants.BASE_URL_FOR_IMAGE + URL)
                                .into(image_view);
                    } else {
                        Picasso.with(context).load(URL).into(image_view);
                    }

                    image_view.setTag(URL);

                } catch (Exception e) {
                }
            }
        } catch (Exception e) {

        }
    }

    public static void removeCache(String URL) {
        getInstance().getRequestQueue().getCache().remove(URL);
    }

    public static void removeCache() {
        getInstance().getRequestQueue().getCache().clear();
    }

    public static void cancelRequest(String URL) {
        getInstance().getRequestQueue().cancelAll(URL);
    }

    public static void setPriority(int priority) {
        PRIORITY = priority;
    }

    public static void setDefaultFont(Context context,
                                      String staticTypefaceFieldName, String fontAssetName) {
        final Typeface regular = Typeface.createFromAsset(context.getAssets(),
                fontAssetName);
        replaceFont(staticTypefaceFieldName, regular);
    }

    protected static void replaceFont(String staticTypefaceFieldName,
                                      final Typeface newTypeface) {
        try {
            final Field staticField = Typeface.class
                    .getDeclaredField(staticTypefaceFieldName);
            staticField.setAccessible(true);
            staticField.set(null, newTypeface);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}

class CustomPostRequest extends Request<String> {

    public CustomPostRequest(int method, String url, ErrorListener listener) {
        super(method, url, (Response.ErrorListener) listener);
        // TODO Auto-generated constructor stub
    }

    private Map<String, String> mParams;

    public void SetPostParam(String strParam, String strValue) {
        mParams.put(strParam, strValue);
    }

    @Override
    public Map<String, String> getParams() {
        return mParams;
    }

    @Override
    public String getCacheKey() {
        String temp = super.getCacheKey();
        for (Map.Entry<String, String> entry : mParams.entrySet())
            temp += entry.getKey() + "=" + entry.getValue();
        return temp;
    }

    @Override
    protected void deliverResponse(String arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public static String getNext30DaysDate(String date_value, int day) {
        String dt = "2012-Nov-13";  // Start date
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MMM-dd");
            Calendar cal = Calendar.getInstance();
            cal.setTime(dateFormat.parse(date_value));
            cal.add(Calendar.DATE, +day); // add 10 days
            String newDate = dateFormat.format(cal.getTime());
//            today = cal.getTime();
            System.out.println("Today========= " + date_value + "  After 30 Date=========== " + newDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dt;
    }



/* Add HTTPS URL WITH CERTIFICATE*/

    public static SSLSocketFactory createSslSocketFactory() {
        TrustManager[] byPassTrustManagers = new TrustManager[]{new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }

            public void checkClientTrusted(X509Certificate[] chain, String authType) {
            }

            public void checkServerTrusted(X509Certificate[] chain, String authType) {
            }
        }};

        SSLContext sslContext = null;
        SSLSocketFactory sslSocketFactory = null;
        try {
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, byPassTrustManagers, new SecureRandom());
            sslSocketFactory = sslContext.getSocketFactory();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }

        return sslSocketFactory;
    }



}

