/**
 * @author Valkesh patel
 */

package com.dr.app.utility;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings.Secure;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.FileProvider;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dr.app.R;
import com.dr.app.adapter.CountryCodeAdapter;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TimeZone;


public class UtilityPro {
    public final static int GALLERY_CODE = 584;
    public  final static int CAMERA_CODE = 585;
    public final static String TRUE = "1";
    public final static String FALSE = "0";
    public final static String INRELATION = "2";
    public final static String ENGAGED = "3";
    static Uri mImageCaptureUri;
    public static boolean crop;
    private static Context context = Golly.context;

    public static void toast(String toastMessage) {
        if (toastMessage.length() > 0) {
            CharSequence cap = toastMessage.trim().substring(0, 1)
                    .toUpperCase()
                    + toastMessage.substring(1).toLowerCase();
            Toast.makeText(context, cap, Toast.LENGTH_SHORT).show();
        }
    }

    public static int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    public static void snack(Activity activity, String toastMessage) {
        if (toastMessage.length() > 0) {
            CharSequence cap = toastMessage.trim().substring(0, 1)
                    .toUpperCase()
                    + toastMessage.substring(1).toLowerCase();

//			Snackbar snackbar = Snackbar.with(activity);
//			SnackbarType type = SnackbarType.MULTI_LINE;
//			snackbar.type(type);
//			snackbar.position(Snackbar.SnackbarPosition.BOTTOM_CENTER);
//			SnackbarManager.show(snackbar.text(cap));
        }
    }

    public static void snackPersistant(Activity activity, String toastMessage) {
        if (toastMessage.length() > 0) {
            CharSequence cap = toastMessage.trim().substring(0, 1)
                    .toUpperCase()
                    + toastMessage.substring(1).toLowerCase();

//			Snackbar snackbar = Snackbar.with(activity);
//			SnackbarType type = SnackbarType.MULTI_LINE;
//			snackbar.type(type);
//			snackbar.position(Snackbar.SnackbarPosition.BOTTOM_CENTER);
//			SnackbarManager.show(snackbar.text(cap));
        }
    }

//	public static void snackWithActionIndefinite(Activity activity,
//			String toastMessage, String actionText, ActionClickListener listener) {
//		if (toastMessage.length() > 0) {
//			CharSequence cap = toastMessage.trim().substring(0, 1)
//					.toUpperCase()
//					+ toastMessage.substring(1).toLowerCase();
//
//			Snackbar snackbar = Snackbar.with(activity);
//			SnackbarType type = SnackbarType.MULTI_LINE;
//			snackbar.type(type);
//			snackbar.position(Snackbar.SnackbarPosition.BOTTOM_CENTER);
//			SnackbarManager.show(snackbar.text(cap).actionLabel(actionText)
//					.actionListener(listener)
//					.duration(Snackbar.SnackbarDuration.LENGTH_INDEFINITE));
//		}
//	}

//	public static void snackWithAction(Activity activity, String toastMessage,
//			String actionText, ActionClickListener listener) {
//		if (toastMessage.length() > 0) {
//			CharSequence cap = toastMessage.trim().substring(0, 1)
//					.toUpperCase()
//					+ toastMessage.substring(1).toLowerCase();
//
//			Snackbar snackbar = Snackbar.with(activity);
//			SnackbarType type = SnackbarType.MULTI_LINE;
//			snackbar.type(type);
//			snackbar.position(Snackbar.SnackbarPosition.BOTTOM_CENTER);
//			SnackbarManager.show(snackbar.text(cap).actionLabel(actionText)
//					.actionListener(listener).duration(4500));
//		}
//	}

    public static Point getScreenDimansions(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        return size;
    }

    public static void log(String Message) {
        Log.d("ParrisDance", "" + Message);
    }

    public static boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        for (RunningServiceInfo service : manager
                .getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;

            }
        }
        return false;
    }

    public static int dptopx(int dp) {
        Resources r = context.getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                r.getDisplayMetrics());
        return (int) px;
    }

    public static void setFont(Context c, TextView v) {
        Typeface custom_font = Typeface.createFromAsset(c.getAssets(),
                "fonts/Erika Type.ttf");
        v.setTypeface(custom_font);
    }

    public static void setFont(Context c, EditText v) {
        Typeface custom_font = Typeface.createFromAsset(c.getAssets(),
                "fonts/Erika Type.ttf");
        v.setTypeface(custom_font);
    }

    public final static boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target)
                    .matches();
        }
    }

    public static void selectImageFromGalary(Activity activity, String path,
                                             boolean Crop) {

        Intent i = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        i.setType("image/*");
        /*
         * i.putExtra("crop", "true"); i.putExtra("outputX", 200);
		 * i.putExtra("outputY", 200); i.putExtra("aspectX", 1);
		 * i.putExtra("aspectY", 1); i.putExtra("scale", true);
		 */
        File f = new File(Environment.getExternalStorageDirectory(),
                "" + path);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mImageCaptureUri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", f);
        } else {
            mImageCaptureUri = Uri.fromFile(f);
        }
        mImageCaptureUri = Uri.fromFile(f);
        i.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
        i.putExtra("outputFormat", CompressFormat.JPEG.toString());
        crop = Crop;
        activity.startActivityForResult(i, GALLERY_CODE);
    }

    public static void selectImageFromCameranew(Activity activity, String path,
                                                boolean Crop_enable) {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File f = new File(path);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mImageCaptureUri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", f);
        } else {
            mImageCaptureUri = Uri.fromFile(f);
        }

        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
        intent.putExtra("return-data", true);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        crop = Crop_enable;
        activity.startActivityForResult(intent, CAMERA_CODE);

    }


    public static void selectImageFromCamera(Activity activity, String path,
                                             boolean Crop_enable) {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File f = new File(Environment.getExternalStorageDirectory(),
                "" + path);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mImageCaptureUri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", f);
        } else {
            mImageCaptureUri = Uri.fromFile(f);
        }
        mImageCaptureUri = Uri.fromFile(f);


        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
        intent.putExtra("return-data", true);
        crop = Crop_enable;
        activity.startActivityForResult(intent, CAMERA_CODE);

    }

    public static Uri handleImagePickerActivityResult(Activity activity,
                                                      int requestCode, int resultCode, Intent data) {

        if (requestCode == GALLERY_CODE && resultCode == Activity.RESULT_OK
                && null != data) {
            mImageCaptureUri = data.getData();
//			if (crop) {
//				new Crop(mImageCaptureUri).output(mImageCaptureUri).asSquare()
//						.start(activity);
//				return null;
//			}
            return mImageCaptureUri;

        }

        if (requestCode == CAMERA_CODE && resultCode == Activity.RESULT_OK) {

            if (crop) {
//				new Crop(mImageCaptureUri).output(mImageCaptureUri).asSquare()
//						.start(activity);
//				return null;

            }
            return mImageCaptureUri;

        }

        if (resultCode == Activity.RESULT_OK) {
            return mImageCaptureUri;
        }

        return null;

    }

    public static PopupWindow popupWindowDogs(List<String> list,
                                              OnItemClickListener onClick) {

        // initialize a pop up window type
        PopupWindow popupWindow = new PopupWindow(context);


        // the drop down list is a list view
        ListView listViewDogs = new ListView(context);
        listViewDogs.setBackgroundColor(Color.parseColor("#000000"));
        listViewDogs.setDividerHeight(1);


        listViewDogs.setDivider(context.getResources()
                .getDrawable(R.drawable.divider));

        // set our adapter and pass our pop up window contents
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                R.layout.spiner_item_layout, list);
        listViewDogs.setAdapter(adapter);

        // set the item click listener
        listViewDogs.setOnItemClickListener(onClick);
        // popupWindow.setBackgroundDrawable(null);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        // some other visual settings
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(android.R.style.DeviceDefault_ButtonBar_AlertDialog);
        popupWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);

        // set the list view as pop up window content
        popupWindow.setContentView(listViewDogs);
        return popupWindow;
    }


    public static PopupWindow popupWindowDogsCustom(CountryCodeAdapter adapter) {

        // initialize a pop up window type
        PopupWindow popupWindow = new PopupWindow(context);
        LayoutInflater inflater = (LayoutInflater) Golly.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popUpView = inflater.inflate(R.layout.popupwindow, null, false);
        ListView listPopup = (ListView) popUpView.findViewById(R.id.listPopup);
        listPopup.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        // set the item click listener

        // popupWindow.setBackgroundDrawable(null);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        // some other visual settings
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(android.R.style.DeviceDefault_ButtonBar_AlertDialog);
        popupWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);

        // set the list view as pop up window content
        popupWindow.setContentView(popUpView);
        return popupWindow;
    }


    public static void setSpinner(Spinner sp, ArrayList<String> list) {
/*		ArrayAdapter<String> karant_adapter = new ArrayAdapter<String>(context, R.layout.spiner_item_layout, list);
        karant_adapter.setDropDownViewResource(R.layout.spiner_dropdown_item_layout);
		sp.setAdapter(karant_adapter);*/

        CustomSpinnerAdapter sadapter = new CustomSpinnerAdapter(context, R.layout.spiner_item_layout, list);
        sadapter.setDropDownViewResource(R.layout.spiner_item_layout);
        sp.setAdapter(sadapter);
    }


    public static void setSpinnerpro(Spinner sp, ArrayList<String> list, int DrawableLeft, String Hint) {
        if (!Hint.equalsIgnoreCase(""))
            list.add(0, Hint);
        ArrayAdapter<String> karant_adapter = new ArrayAdapter<String>(context, R.layout.spiner_item_layout, list);
        sp.setAdapter(karant_adapter);
        karant_adapter.setDropDownViewResource(R.layout.spiner_item_layout);
        TextView tv = (TextView) sp.getSelectedView();

        tv.setCompoundDrawablesWithIntrinsicBounds(DrawableLeft, 0, 0, 0);
//        tv.set(DrawableLeft, 0, 0, 0);
    }


    public static void showNotification(final Context context, String title,
                                        String message, int id, Intent intent) {
        PendingIntent pIntent = PendingIntent
                .getActivity(context, 0, intent, 0);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                context);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setContentTitle(title);
        mBuilder.setContentText(message);
        mBuilder.setAutoCancel(true);
        mBuilder.setContentIntent(pIntent);
        /*
         * mBuilder.addAction(R.drawable.ic_launcher, "Call", pIntent);
		 * mBuilder.addAction(R.drawable.ic_launcher, "More", pIntent);
		 */
        NotificationManager mNotificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        // notificationID allows you to update the notification later on.
        mNotificationManager.notify(id, mBuilder.build());
    }

    public static void animateFadeIn(View v) {
        Animation fadeInAnimation = AnimationUtils.loadAnimation(
                v.getContext(), android.R.anim.fade_in);
        fadeInAnimation.setDuration(1000);
        v.startAnimation(fadeInAnimation);
    }

    public static void animateFadeOut(View v, AnimationListener listen) {
        Animation fadeOutAnimation = AnimationUtils.loadAnimation(
                v.getContext(), android.R.anim.fade_out);
        fadeOutAnimation.setDuration(1000);
        fadeOutAnimation.setAnimationListener(listen);
        v.startAnimation(fadeOutAnimation);
    }

    public static String getUniqueDeviceId() {
        return Secure
                .getString(context.getContentResolver(), Secure.ANDROID_ID);

    }

    public static boolean isNetworkAvaliable(Context ctx) {
        ConnectivityManager connectivityManager = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        return (connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE) != null && connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED)
                || (connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI) != null && connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .getState() == NetworkInfo.State.CONNECTED);
    }

    public static String getMacAddress() {

        WifiManager wifiManager = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        String address = "";
        try {

            if (wifiManager.isWifiEnabled()) {
                // WIFI ALREADY ENABLED. GRAB THE MAC ADDRESS HERE
                WifiInfo info = wifiManager.getConnectionInfo();
                address = info.getMacAddress();
            } else {
                // ENABLE THE WIFI FIRST
                wifiManager.setWifiEnabled(true);

                // WIFI IS NOW ENABLED. GRAB THE MAC ADDRESS HERE
                WifiInfo info = wifiManager.getConnectionInfo();
                address = info.getMacAddress();
            }
            if (address.equalsIgnoreCase("")) {
                UtilityPro.toast("Require WiFi To Use This App");
            } else {
                address.replace(":", "");
            }
        } catch (Exception e) {
            UtilityPro.toast("Please turn on wifi and try again");
            return "";
        }
        return address.replace(":", "");
    }

    public static void copyToClipboard(String TEXT) {
        ClipboardManager _clipboard = (ClipboardManager) context
                .getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("ab", TEXT);
        _clipboard.setPrimaryClip(clip);
    }

    public static String convertImageToByte(Uri uri, Context mcontext) {
        String encodeString = null;
        byte[] data = null;
        try {
            ContentResolver cr = mcontext.getContentResolver();
            InputStream inputStream = cr.openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(CompressFormat.PNG, 100, baos);
            data = baos.toByteArray();
            encodeString = encodeTobase64(bitmap);
            return encodeString;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return encodeString;
    }

    public static String encodeTobase64(Bitmap image) {
        Bitmap immagex = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(CompressFormat.PNG, 10, baos);
        // Bitmap.createScaledBitmap(immagex, 10, 10, true);
        Bitmap.createScaledBitmap(immagex, 40, 40, true);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        return imageEncoded;
    }

    public static String encodeTObase64FullImage(Bitmap image) {
        Bitmap immagex = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(CompressFormat.PNG, 10, baos);
        // Bitmap.createScaledBitmap(immagex, 10, 10, true);
        Bitmap.createScaledBitmap(immagex, 100, 100, true);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        return imageEncoded;
    }

    public static String getRealPathFromURI(Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null,
                    null, null);
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public static String savePhoto(Bitmap bm) {
        File mLocation = new File(Environment.getExternalStorageDirectory(),
                "test_test.jpg");
        FileOutputStream image = null;
        try {
            image = new FileOutputStream(mLocation);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bm.compress(CompressFormat.PNG, 100, image);
        if (bm != null) {
            int h = bm.getHeight();
            int w = bm.getWidth();
        } else {
            return null;
        }
        return null;
    }

    @SuppressLint("NewApi")
    public static String getOutputMediaFile(String image_name, Bitmap image_) {
        FileOutputStream image = null;
        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "DressMe");

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                // Log.d("TattooBody", "Oops! Failed create " + "TattooBody"
                // + " directory");
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator
                + image_name + timeStamp + ".png");
        try {
            // String Image_path = Utility.getOutputMediaFile("BODY_");
            image = new FileOutputStream(mediaFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        image_.compress(CompressFormat.PNG, 100, image);
        return mediaFile.getAbsolutePath();
    }

    public static void sendSMS(String phoneNumber, final String message) {
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        PendingIntent sentPI = PendingIntent.getBroadcast(context, 0,
                new Intent(SENT), 0);

        PendingIntent deliveredPI = PendingIntent.getBroadcast(context, 0,
                new Intent(DELIVERED), 0);

        // ---when the SMS has been sent---
        context.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(context, "SMS sent", Toast.LENGTH_SHORT)
                                .show();

                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(context, "Generic failure",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(context, "No service", Toast.LENGTH_SHORT)
                                .show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(context, "Null PDU", Toast.LENGTH_SHORT)
                                .show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(context, "Radio off", Toast.LENGTH_SHORT)
                                .show();
                        break;
                }
            }
        }, new IntentFilter(SENT));

        // ---when the SMS has been delivered---
        context.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(context, "SMS delivered", Toast.LENGTH_SHORT)
                                .show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(context, "SMS not delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(DELIVERED));

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
    }

    @SuppressLint("NewApi")
    public static String getOutputMediaFile(String image_name, String Path,
                                            Bitmap image_) {
        FileOutputStream image = null;
        // External sdcard location
        File mediaStorageDir = new File(Path);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                // Log.d("TattooBody", "Oops! Failed create " + "TattooBody"
                // + " directory");
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator
                + image_name + timeStamp + ".png");
        try {
            // String Image_path = Utility.getOutputMediaFile("BODY_");
            image = new FileOutputStream(mediaFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        image_.compress(CompressFormat.PNG, 100, image);
        return mediaFile.getAbsolutePath();
    }

    public static Bitmap getbitmap(String img_path) {
        File imgFile = new File(img_path);

        if (imgFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile
                    .getAbsolutePath());

            return myBitmap;
        }
        return null;
    }

    public ArrayList<String> loadfiles(String YourFolderPath, String extension) {
        ArrayList<String> list = new ArrayList<String>();
        File file = new File(YourFolderPath);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null && files.length > 0) {
                for (File f : files) {
                    if (f.isDirectory()) {
                        // loadmp3(f.getAbsolutePath(),extension,listmp3 );
                    } else {
                        // for (int i = 0; i < extensions.length; i++) {
                        if (f.getAbsolutePath().endsWith(extension)) {
                            list.add(f.getAbsolutePath());
                        }
                        // }
                    }
                }
            }
        }

        return list;

    }

//	public static void DateDialog(Activity activity, Calendar date,
//			Calendar min_date, OnDateSetListener listener) {
//		DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
//				listener, date.get(Calendar.YEAR), date.get(Calendar.MONTH),
//				date.get(Calendar.DAY_OF_MONTH));
//		datePickerDialog.setMinDate(min_date);
//		datePickerDialog.show(activity.getFragmentManager(), "");
//	}

//	public static void DateDialog(Activity activity, Calendar date,
//			Calendar min_date, Calendar max_date, OnDateSetListener listener) {
//		DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
//				listener, date.get(Calendar.YEAR), date.get(Calendar.MONTH),
//				date.get(Calendar.DAY_OF_MONTH));
//		datePickerDialog.setMinDate(min_date);
//		datePickerDialog.setMaxDate(max_date);
//		datePickerDialog.show(activity.getFragmentManager(), "");
//	}

//	public static void TimeDialog(Activity activity, final Calendar date,
//			OnTimeSetListener listener) {
//		TimePickerDialog.newInstance(listener, date.get(Calendar.HOUR_OF_DAY),
//				date.get(Calendar.MINUTE), true).show(
//				activity.getFragmentManager(), "");
//	}

    public static Bitmap CropBitmapTransparency(Bitmap sourceBitmap) {
        int minX = sourceBitmap.getWidth();
        int minY = sourceBitmap.getHeight();
        int maxX = -1;
        int maxY = -1;
        for (int y = 0; y < sourceBitmap.getHeight(); y++) {
            for (int x = 0; x < sourceBitmap.getWidth(); x++) {
                int alpha = (sourceBitmap.getPixel(x, y) >> 24) & 255;
                if (alpha > 0) // pixel is not 100% transparent
                {
                    if (x < minX)
                        minX = x;
                    if (x > maxX)
                        maxX = x;
                    if (y < minY)
                        minY = y;
                    if (y > maxY)
                        maxY = y;
                }
            }
        }
        if ((maxX < minX) || (maxY < minY))
            return null; // Bitmap is entirely transparent

        // crop bitmap to non-transparent area and return:
        return Bitmap.createBitmap(sourceBitmap, minX, minY, (maxX - minX) + 1,
                (maxY - minY) + 1);
    }

    // ---------------------------------------------------------------------------------------PROJECT
    // SPECIFIC-----------------------------------------
    @SuppressLint("NewApi")
    public static boolean AutoTimeEnabled() {
        try {
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) {
                // only for JELLY_BEAN and older versions
                return android.provider.Settings.System.getInt(
                        context.getContentResolver(),
                        android.provider.Settings.System.AUTO_TIME, 0) != 0;
            } else {
                return android.provider.Settings.Global.getInt(
                        context.getContentResolver(),
                        android.provider.Settings.Global.AUTO_TIME, 0) != 0;
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

        return true;

    }

    public static boolean saveSharedPreferencesToFile(File dst) {
        boolean res = false;
        ObjectOutputStream output = null;
        try {
            output = new ObjectOutputStream(new FileOutputStream(dst));
            SharedPreferences pref = context.getSharedPreferences(
                    "lock_down_pref", Context.MODE_PRIVATE);
            output.writeObject(pref.getAll());

            res = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (output != null) {
                    output.flush();
                    output.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return res;
    }

    @SuppressWarnings({"unchecked"})
    public static boolean loadSharedPreferencesFromFile(File src) {
        boolean res = false;
        ObjectInputStream input = null;
        try {
            input = new ObjectInputStream(new FileInputStream(src));
            Editor prefEdit = context.getSharedPreferences("lock_down_pref",
                    Context.MODE_PRIVATE).edit();
            prefEdit.clear();
            Map<String, ?> entries = (Map<String, ?>) input.readObject();
            for (Entry<String, ?> entry : entries.entrySet()) {
                Object v = entry.getValue();
                String key = entry.getKey();

                if (v instanceof Boolean)
                    prefEdit.putBoolean(key, ((Boolean) v).booleanValue());
                else if (v instanceof Float)
                    prefEdit.putFloat(key, ((Float) v).floatValue());
                else if (v instanceof Integer)
                    prefEdit.putInt(key, ((Integer) v).intValue());
                else if (v instanceof Long)
                    prefEdit.putLong(key, ((Long) v).longValue());
                else if (v instanceof String)
                    prefEdit.putString(key, ((String) v));
            }
            prefEdit.commit();
            res = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return res;
    }

    public static String getCompleteAddressString(double LATITUDE,
                                                  double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE,
                    LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress
                            .append(returnedAddress.getAddressLine(i)).append(
                            "\n");
                }
                strAdd = strReturnedAddress.toString();
//				Log.w("My Current loction address",
//						"" + strReturnedAddress.toString());
            } else {
//				Log.w("My Current loction address", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
//			Log.w("My Current loction address", "Canont get Address!");
        }
        return strAdd;
    }

    public static String getDataFromJson(JSONObject jobj, String key) {
        try {
            if (jobj.has(key)) {
                return jobj.getString(key);
            } else {
                return "";
            }
        } catch (Exception e) {
            return "";
        }
    }

    public static Boolean getDataFromJsonBoolean(JSONObject jobj, String key) {
        try {
            if (jobj.has(key)) {
                return jobj.getBoolean(key);
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public static int getDataFromJsonInt(JSONObject jobj, String key) {
        try {
            if (jobj.has(key)) {
                return jobj.getInt(key);
            } else {
                return -1;
            }
        } catch (Exception e) {
            return -1;
        }
    }


    public static int getCameraPhotoOrientation(Context context, Uri imageUri,
                                                String imagePath) {
        int rotate = 0;
        try {
            context.getContentResolver().notifyChange(imageUri, null);
            File imageFile = new File(imagePath);

            ExifInterface exif = new ExifInterface(imageFile.getAbsolutePath());
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }

            Log.i("RotateImage", "Exif orientation: " + orientation);
            Log.i("RotateImage", "Rotate value: " + rotate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rotate;
    }

    public static void errorLogTrace(String tag, String messsage,
                                     boolean is_force_error) {
        if (Constants.IS_ERROR_LOG) {
            if (is_force_error) {
                System.out.println("::::" + tag + "::::====>" + messsage);
            }
        }
    }

    public static Bitmap rotateBitmap(String src, Bitmap bitmap) {
        try {
            int orientation = getExifOrientation(src);

            if (orientation == 1) {
                return bitmap;
            }

            Matrix matrix = new Matrix();
            switch (orientation) {
                case 2:
                    matrix.setScale(-1, 1);
                    break;
                case 3:
                    matrix.setRotate(180);
                    break;
                case 4:
                    matrix.setRotate(180);
                    matrix.postScale(-1, 1);
                    break;
                case 5:
                    matrix.setRotate(90);
                    matrix.postScale(-1, 1);
                    break;
                case 6:
                    matrix.setRotate(90);
                    break;
                case 7:
                    matrix.setRotate(-90);
                    matrix.postScale(-1, 1);
                    break;
                case 8:
                    matrix.setRotate(-90);
                    break;
                default:
                    return bitmap;
            }
            try {
                Bitmap oriented = Bitmap.createBitmap(bitmap, 0, 0,
                        bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                bitmap.recycle();
                return oriented;
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
                return bitmap;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    private static int getExifOrientation(String src) throws IOException {
        int orientation = 1;

        try {
            /**
             * if your are targeting only api level >= 5 ExifInterface exif =
             * new ExifInterface(src); orientation =
             * exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
             */
            if (Build.VERSION.SDK_INT >= 5) {
                Class<?> exifClass = Class
                        .forName("android.media.ExifInterface");
                Constructor<?> exifConstructor = exifClass
                        .getConstructor(String.class);
                Object exifInstance = exifConstructor
                        .newInstance(src);
                Method getAttributeInt = exifClass.getMethod("getAttributeInt",
                        String.class, int.class);
                Field tagOrientationField = exifClass
                        .getField("TAG_ORIENTATION");
                String tagOrientation = (String) tagOrientationField.get(null);
                orientation = (Integer) getAttributeInt.invoke(exifInstance,
                        tagOrientation, 1);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return orientation;
    }

    public static void generateKey(Context act) {
        try {
            PackageInfo info = act.getPackageManager().getPackageInfo(
                    "com.dr.app", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("===hash_key=======",
                        Base64.encodeToString(md.digest(), Base64.DEFAULT));
                // txtText.setText(Base64.encodeToString(md.digest(),
                // Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("Error", e.getMessage());

        } catch (NoSuchAlgorithmException e) {
            Log.e("Error", e.getMessage());
        }
    }


    public static void ShowToast(Context ct, String message) {
        try {
            if (message.trim().length() != 0)
                Toast.makeText(ct, message, Toast.LENGTH_SHORT).show();
        } catch (NullPointerException e) {
            Log.d("", "Exception---" + e.getLocalizedMessage());
        }
    }


    public static boolean ValidateTask(String email, String password, Context context) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (email.trim().length() > 0) {
            if (TextValidator.isAValidEmail(email)) {
                String[] domain = {".aero", ".asia", ".biz",
                        ".cat", ".com", ".coop", ".edu",
                        ".gov", ".info", ".int", ".jobs",
                        ".mil", ".mobi", ".museum", ".name",
                        ".net", ".org", ".pro", ".tel",
                        ".travel", ".ac", ".ad", ".ae", ".af",
                        ".ag", ".ai", ".al", ".am", ".an",
                        ".ao", ".aq", ".ar", ".as", ".at",
                        ".au", ".aw", ".ax", ".az", ".ba",
                        ".bb", ".bd", ".be", ".bf", ".bg",
                        ".bh", ".bi", ".bj", ".bm", ".bn",
                        ".bo", ".br", ".bs", ".bt", ".bv",
                        ".bw", ".by", ".bz", ".ca", ".cc",
                        ".cd", ".cf", ".cg", ".ch", ".ci",
                        ".ck", ".cl", ".cm", ".cn", ".co",
                        ".cr", ".cu", ".cv", ".cx", ".cy",
                        ".cz", ".de", ".dj", ".dk", ".dm",
                        ".do", ".dz", ".ec", ".ee", ".eg",
                        ".er", ".es", ".et", ".eu", ".fi",
                        ".fj", ".fk", ".fm", ".fo", ".fr",
                        ".ga", ".gb", ".gd", ".ge", ".gf",
                        ".gg", ".gh", ".gi", ".gl", ".gm",
                        ".gn", ".gp", ".gq", ".gr", ".gs",
                        ".gt", ".gu", ".gw", ".gy", ".hk",
                        ".hm", ".hn", ".hr", ".ht", ".hu",
                        ".id", ".ie", " No", ".il", ".im",
                        ".in", ".io", ".iq", ".ir", ".is",
                        ".it", ".je", ".jm", ".jo", ".jp",
                        ".ke", ".kg", ".kh", ".ki", ".km",
                        ".kn", ".kp", ".kr", ".kw", ".ky",
                        ".kz", ".la", ".lb", ".lc", ".li",
                        ".lk", ".lr", ".ls", ".lt", ".lu",
                        ".lv", ".ly", ".ma", ".mc", ".md",
                        ".me", ".mg", ".mh", ".mk", ".ml",
                        ".mm", ".mn", ".mo", ".mp", ".mq",
                        ".mr", ".ms", ".mt", ".mu", ".mv",
                        ".mw", ".mx", ".my", ".mz", ".na",
                        ".nc", ".ne", ".nf", ".ng", ".ni",
                        ".nl", ".no", ".np", ".nr", ".nu",
                        ".nz", ".om", ".pa", ".pe", ".pf",
                        ".pg", ".ph", ".pk", ".pl", ".pm",
                        ".pn", ".pr", ".ps", ".pt", ".pw",
                        ".py", ".qa", ".re", ".ro", ".rs",
                        ".ru", ".rw", ".sa", ".sb", ".sc",
                        ".sd", ".se", ".sg", ".sh", ".si",
                        ".sj", ".sk", ".sl", ".sm", ".sn",
                        ".so", ".sr", ".st", ".su", ".sv",
                        ".sy", ".sz", ".tc", ".td", ".tf",
                        ".tg", ".th", ".tj", ".tk", ".tl",
                        ".tm", ".tn", ".to", ".tp", ".tr",
                        ".tt", ".tv", ".tw", ".tz", ".ua",
                        ".ug", ".uk", ".us", ".uy", ".uz",
                        ".va", ".vc", ".ve", ".vg", ".vi",
                        ".vn", ".vu", ".wf", ".ws", ".ye",
                        ".yt", ".za", ".zm", ".zw"};
                List<String> list = Arrays.asList(domain);
                String tmp = email.substring(
                        email.lastIndexOf("."), email.length());
                if (list.contains("" + tmp)) {
                    if (password.trim().length() > 0) {
                        return true;
                    } else {
                        UtilityPro.ShowToast(context, context.getString(R.string.msg_validation_Please_enter_your_password));
                        return false;
                    }
                } else {
                    UtilityPro.ShowToast(context, context.getString(R.string.msg_validation_Please_enter_valid_email_address));
                    return false;
                }
            } else {
                UtilityPro.ShowToast(context, context.getString(R.string.msg_validation_Please_enter_valid_email_address));
                return false;
            }
        } else {
            UtilityPro.ShowToast(context, context.getString(R.string.msg_validation_Please_enter_email_address));
            return false;
        }

    }


    public static boolean ValidateEmailTask(String email, Context context) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (email.trim().length() > 0) {
            if (TextValidator.isAValidEmail(email)) {
                String[] domain = {".aero", ".asia", ".biz",
                        ".cat", ".com", ".coop", ".edu",
                        ".gov", ".info", ".int", ".jobs",
                        ".mil", ".mobi", ".museum", ".name",
                        ".net", ".org", ".pro", ".tel",
                        ".travel", ".ac", ".ad", ".ae", ".af",
                        ".ag", ".ai", ".al", ".am", ".an",
                        ".ao", ".aq", ".ar", ".as", ".at",
                        ".au", ".aw", ".ax", ".az", ".ba",
                        ".bb", ".bd", ".be", ".bf", ".bg",
                        ".bh", ".bi", ".bj", ".bm", ".bn",
                        ".bo", ".br", ".bs", ".bt", ".bv",
                        ".bw", ".by", ".bz", ".ca", ".cc",
                        ".cd", ".cf", ".cg", ".ch", ".ci",
                        ".ck", ".cl", ".cm", ".cn", ".co",
                        ".cr", ".cu", ".cv", ".cx", ".cy",
                        ".cz", ".de", ".dj", ".dk", ".dm",
                        ".do", ".dz", ".ec", ".ee", ".eg",
                        ".er", ".es", ".et", ".eu", ".fi",
                        ".fj", ".fk", ".fm", ".fo", ".fr",
                        ".ga", ".gb", ".gd", ".ge", ".gf",
                        ".gg", ".gh", ".gi", ".gl", ".gm",
                        ".gn", ".gp", ".gq", ".gr", ".gs",
                        ".gt", ".gu", ".gw", ".gy", ".hk",
                        ".hm", ".hn", ".hr", ".ht", ".hu",
                        ".id", ".ie", " No", ".il", ".im",
                        ".in", ".io", ".iq", ".ir", ".is",
                        ".it", ".je", ".jm", ".jo", ".jp",
                        ".ke", ".kg", ".kh", ".ki", ".km",
                        ".kn", ".kp", ".kr", ".kw", ".ky",
                        ".kz", ".la", ".lb", ".lc", ".li",
                        ".lk", ".lr", ".ls", ".lt", ".lu",
                        ".lv", ".ly", ".ma", ".mc", ".md",
                        ".me", ".mg", ".mh", ".mk", ".ml",
                        ".mm", ".mn", ".mo", ".mp", ".mq",
                        ".mr", ".ms", ".mt", ".mu", ".mv",
                        ".mw", ".mx", ".my", ".mz", ".na",
                        ".nc", ".ne", ".nf", ".ng", ".ni",
                        ".nl", ".no", ".np", ".nr", ".nu",
                        ".nz", ".om", ".pa", ".pe", ".pf",
                        ".pg", ".ph", ".pk", ".pl", ".pm",
                        ".pn", ".pr", ".ps", ".pt", ".pw",
                        ".py", ".qa", ".re", ".ro", ".rs",
                        ".ru", ".rw", ".sa", ".sb", ".sc",
                        ".sd", ".se", ".sg", ".sh", ".si",
                        ".sj", ".sk", ".sl", ".sm", ".sn",
                        ".so", ".sr", ".st", ".su", ".sv",
                        ".sy", ".sz", ".tc", ".td", ".tf",
                        ".tg", ".th", ".tj", ".tk", ".tl",
                        ".tm", ".tn", ".to", ".tp", ".tr",
                        ".tt", ".tv", ".tw", ".tz", ".ua",
                        ".ug", ".uk", ".us", ".uy", ".uz",
                        ".va", ".vc", ".ve", ".vg", ".vi",
                        ".vn", ".vu", ".wf", ".ws", ".ye",
                        ".yt", ".za", ".zm", ".zw"};
                List<String> list = Arrays.asList(domain);
                String tmp = email.substring(
                        email.lastIndexOf("."), email.length());
                if (list.contains("" + tmp)) {
                    return true;
                } else {
                    UtilityPro.ShowToast(context, context.getString(R.string.msg_validation_Please_enter_valid_email_address));
                    return false;
                }
            } else {
                UtilityPro.ShowToast(context, context.getString(R.string.msg_validation_Please_enter_valid_email_address));
                return false;
            }
        } else {
            UtilityPro.ShowToast(context, context.getString(R.string.msg_validation_Please_enter_email_address));
            return false;
        }

    }

    public static String getTimezone() {
        Calendar cal = Calendar.getInstance();
        TimeZone tz = TimeZone.getTimeZone("GMT");

        UtilityPro.errorLogTrace("Time zonessss",
                TimeZone.getDefault().getID(), true);
        UtilityPro.errorLogTrace("Time zone", tz.getDisplayName(), true);

        return TimeZone.getDefault().getID();
    }

    //    public static void pxtodp() {
//        Display display = context.getApplicationContext().getWindowManager().getDefaultDisplay();
//        Point size = new Point();
//        display.getSize(size);
//        int width = size.x;
//        int height = size.y;
//
//        System.out.println("=== width px====" + width);
//        System.out.println("=== height px====" + height);
//        System.out.println("=== width dp====" + UtilityPro.pxToDp(width));
//        System.out.println("=== height dp====" + UtilityPro.pxToDp(height));
//    }
    public static String getNext30DaysDates(String date_value) {
        String dt = "Fri Apr 15 09:30:22 EDT 2016";  // Start date
//        SimpleDateFormat parseFormat =
//                new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZ yyyy");
        String newdt = "";
        try {
            SimpleDateFormat formatFrom = new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZ yyyy");
            Calendar cal = Calendar.getInstance();
            cal.setTime(formatFrom.parse(date_value));
            cal.add(Calendar.DATE, +30); // add 10 days
            java.util.Date tmpDate = formatFrom.parse(date_value);

            SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd,yyyy");
//            dateFormatmonth.format(tmpDate);


            // SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MMM-dd");
            dateFormat.format(tmpDate);
            newdt = dateFormat.format(cal.getTime());
//            today = cal.getTime();
            System.out.println("Today========= " + dt + "  After 30 Date=========== " + newdt);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return newdt;
    }

    public static void updateGCMID() {
        // TODO Auto-generated method stub
       /* GCMRegistrar.unregister(getApplicationContext());
        GCMRegistrar.checkDevice(this);
		GCMRegistrar.checkManifest(this);*/
        if (UtilityPro.isNetworkAvaliable(context)) {
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        InstanceID instanceID = InstanceID.getInstance(context);
                        String token = instanceID.getToken("563610844647", GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
                        log("regs id = " + token);

                        System.out.println("==========regs - id   " + token);
                        //CommonClass.setDeviceToken(token);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            UtilityPro.toast("No Internet Connection");
        }
    }


//    public static String getUsername() {
//        AccountManager manager = AccountManager.get(context);
//        Account[] accounts = manager.getAccountsByType("com.google");
//        List<String> possibleEmails = new LinkedList<String>();
//
//        for (Account account : accounts) {
//            // TODO: Check possibleEmail against an email regex or treat
//            // account.name as an email address only for certain account.type
//            // values.
//            possibleEmails.add(account.name);
//        }
//
//        if (!possibleEmails.isEmpty() && possibleEmails.get(0) != null) {
//            String email = possibleEmails.get(0);
//            String[] parts = email.split("@");
//            if (parts.length > 0 && parts[0] != null)
//                return parts[0];
//            else
//                return null;
//        } else
//            return null;
//    }

//    public static boolean getUserEmail() {
//        boolean is_account = false;
//        Account[] accounts = AccountManager.get(context).getAccounts();
//        Log.e("", "Size: " + accounts.length);
//
//        System.out.println("============accounts.length=============" + accounts.length);
//        for (Account account : accounts) {
//            is_account = true;
//            String possibleEmail = account.name;
//            System.out.println("============String possibleEmail=============" + possibleEmail);
//            String type = account.type;
//        }
//        return false;
//    }

    public static boolean isMaterialAnimationSupported() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public static boolean isSupportedVideo() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public static String GetAddress(Context mContext, double mlatitude, double mlongitude) {
        Geocoder geocoder;
        List<Address> addresses = null;
        geocoder = new Geocoder(mContext, Locale.getDefault());
        String finalAddress = "";
        try {
            addresses = geocoder.getFromLocation(mlatitude, mlongitude, 1);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } // Here 1 represent max location result to returned, by documents it
        // recommended 1 to 5
        if (addresses != null && addresses.size() > 0) {

            Address address = null;
            if (addresses != null && addresses.size() > 0)
                address = addresses.get(0);
            if (address != null) {
                for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                    finalAddress += "" + address.getAddressLine(i) + ",";
                }
            }

            if (finalAddress.length() > 0)
                finalAddress = finalAddress.substring(0, finalAddress.length() - 1);

        }

        return finalAddress;
    }


    public static void density(Activity act) {
        DisplayMetrics metrics = new DisplayMetrics();
        act.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        System.out.println("========DENSITY_LOW=======" + metrics.densityDpi);
        switch (metrics.densityDpi) {
            case DisplayMetrics.DENSITY_LOW:
                System.out.println("========DENSITY_LOW=======");
                break;
            case DisplayMetrics.DENSITY_MEDIUM:
                System.out.println("========DENSITY_MEDIUM=======");
                break;
            case DisplayMetrics.DENSITY_XHIGH:
                System.out.println("========DENSITY_XHIGH=======");
                break;
            case DisplayMetrics.DENSITY_XXHIGH:
                System.out.println("========DENSITY_XXHIGH=======");
                break;
            case DisplayMetrics.DENSITY_XXXHIGH:
                System.out.println("========DENSITY_XXXHIGH=======");
                break;
            case DisplayMetrics.DENSITY_HIGH:
                System.out.println("========DENSITY_HIGH=======");
                break;
        }
    }


}