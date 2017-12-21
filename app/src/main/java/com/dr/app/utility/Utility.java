/**
 * @author valkesh Patel
 */
package com.dr.app.utility;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Utility {
    private static Activity activity;
    public static File outputFile;

    public Utility(Activity activity) {
        this.activity = activity;
    }

    public static void toast(String toastMessage, Context context) {
        Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show();
    }

    static Toast toast;

    public static void toast_message(String toastMessage, Context context) {

        if (toast == null) {
            toast = Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT);
        } else {
            toast.setText(toastMessage);
        }

        if (!toast.getView().isShown()) {
            toast.show();
        }

    }

    public static void showDialog(final Context context, String message,
                                  final String deviceAddress) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                arg0.dismiss();
                            }
                        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    public static boolean writeSharedPreferences(Context mContext, String key,
                                                 String value) {
        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        SharedPreferences settings = mContext.getSharedPreferences(
                Constants.PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        return editor.commit();

    }

    public static boolean writeSharedPreferencesInt(Context mContext,
                                                    String key, int value) {
        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        SharedPreferences settings = mContext.getSharedPreferences(
                Constants.PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, value);
        // Commit the edits!
        editor.commit();
        return true;
    }

    public static void writeSharedPreferencesBool(Context mContext, String key,
                                                  Boolean value) {
        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        mContext = Golly.getInstance();

        SharedPreferences settings = mContext.getSharedPreferences(
                Constants.PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, value);
        // Commit the edits!
        editor.commit();
    }

    public static String getAppPrefString(Context mContext, String key) {
        try {
            SharedPreferences settings = mContext.getSharedPreferences(
                    Constants.PREFS_NAME, 0);
            String value = settings.getString(key, "");
            return value;
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    public static Integer getAppPrefInt(Context mContext, String key) {
        try {
            SharedPreferences settings = mContext.getSharedPreferences(
                    Constants.PREFS_NAME, 0);
            int value = settings.getInt(key, 0);
            return value;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    public static Boolean getAppPrefBool(Context mContext, String key) {
        try {
            SharedPreferences settings = mContext.getSharedPreferences(
                    Constants.PREFS_NAME, 0);
            Boolean value = settings.getBoolean(key, false);
            return value;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static void errorDialog(String message, final Activity context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message).setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }

    public static String getCurrentDateTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }

    public static boolean isNetworkAvaliable(Context ctx) {
        ConnectivityManager connectivityManager = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if ((connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE) != null && connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED)
                || (connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI) != null && connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .getState() == NetworkInfo.State.CONNECTED)) {
            return true;
        } else {
            return false;
        }
    }

    public static String getMacAddress(Context context) {
        WifiManager wimanager = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        String macAddress = wimanager.getConnectionInfo().getMacAddress();
        if (macAddress == null) {
            macAddress = "Device don't have mac address or wi-fi is disabled";
        }
        return macAddress;
    }

    public static void alertMessage(Context mContext) {
        AlertDialog.Builder builder_notice = new AlertDialog.Builder(mContext);
        // Set a title
        builder_notice.setTitle("Notice");
        // Set a message
        builder_notice.setMessage("No internet connection!");

        builder_notice.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        dialog.cancel();
                    }
                });

        // Create the dialog
        AlertDialog alertdialog = builder_notice.create();

        // show the alertdialog
        alertdialog.show();
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
            Log.i("valkesh", "In utility 0000" + encodeString);
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

    public static String savePhoto(Bitmap bm) {
        File mLocation = new File(Environment.getExternalStorageDirectory()
                + "/EyeWitness/Thumb/");
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

    public static String getOutputMediaFile(String image_name, Bitmap image_) {
        FileOutputStream image = null;
        File mediaStorageDir = new File(
                Environment.getExternalStorageDirectory() + "/Drinkz/Thumb/");

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                // return null;
                mediaStorageDir.mkdir();
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        mediaFile = new File(mediaStorageDir, image_name + timeStamp + ".png");
        try {

            image = new FileOutputStream(mediaFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        image_.compress(CompressFormat.PNG, 100, image);
        return mediaFile.getPath();
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

    public static void addImageToGallery(final String filePath,
                                         final Context context) {
        ContentValues values = new ContentValues();
        values.put(Images.Media.DATE_TAKEN, System.currentTimeMillis());
        values.put(Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.MediaColumns.DATA, filePath);
        context.getContentResolver().insert(Images.Media.EXTERNAL_CONTENT_URI,
                values);
    }

//	public static String getGmailAccountName(Context mContext) {
//		String strGmail = null;
//		Account[] accounts = AccountManager.get(mContext).getAccounts();
//		for (Account account : accounts) {
//			String possibleEmail = account.name;
//			String type = account.type;
//
//			if (type.equals("com.google")) {
//				strGmail = possibleEmail;
//				Log.e("", "Emails: " + strGmail);
//				break;
//			}
//		}
//		return strGmail;
//	}

    public static boolean emailValidator(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static String getOutputFilePath(String type) {

        try {

            File mediaStorageDir = new File(
                    Environment.getExternalStorageDirectory(), "/Quorg/"
                    + type);

            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {
                    Log.d(" Storage Directory", "failed to create directory");
                }
            }

            if (type.equalsIgnoreCase("AUDIO")) {

                outputFile = new File(mediaStorageDir,
                        "AUD_"
                                + new SimpleDateFormat("yyyyMMdd_HHmmss")
                                .format(new Date()) + ".mp3");

            } else if (type.equalsIgnoreCase("VIDEO")) {

                outputFile = new File(mediaStorageDir,
                        "VID_"
                                + new SimpleDateFormat("yyyyMMdd_HHmmss")
                                .format(new Date()) + ".mp4");

            } else {

                outputFile = new File(mediaStorageDir,
                        "IMG_"
                                + new SimpleDateFormat("yyyyMMdd_HHmmss")
                                .format(new Date()) + ".jpg");

            }
            String fileName = outputFile.getAbsolutePath();

            return fileName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public static String getCurrentTimeStamp() {
        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss");
            String currentTimeStamp = dateFormat.format(new Date()); // Find
            // todays
            // date

            return currentTimeStamp;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
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

    public static JSONObject getLocationInfo(String address) {
        StringBuilder stringBuilder = new StringBuilder();
        try {

            address = address.replaceAll(" ", "%20");

            HttpPost httppost = new HttpPost(
                    "http://maps.google.com/maps/api/geocode/json?address="
                            + address + "&sensor=false");
            HttpClient client = new DefaultHttpClient();
            HttpResponse response;
            stringBuilder = new StringBuilder();

            response = client.execute(httppost);
            HttpEntity entity = response.getEntity();
            InputStream stream = entity.getContent();
            int b;
            while ((b = stream.read()) != -1) {
                stringBuilder.append((char) b);
            }
        } catch (ClientProtocolException e) {
        } catch (IOException e) {
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringBuilder.toString());

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return jsonObject;
    }

    public static String getLatLong(JSONObject jsonObject) {
        String lat_long = "0.0,0.0";

        try {

            double longitute = ((JSONArray) jsonObject.get("results"))
                    .getJSONObject(0).getJSONObject("geometry")
                    .getJSONObject("location").getDouble("lng");

            double latitude = ((JSONArray) jsonObject.get("results"))
                    .getJSONObject(0).getJSONObject("geometry")
                    .getJSONObject("location").getDouble("lat");


        } catch (JSONException e) {
            return lat_long;
        }
        return lat_long;
    }


    public static boolean isMaterialAnimationSupported() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }
	/*
     * public static ArrayList<String> getNameEmailDetails() { ArrayList<String>
	 * names = new ArrayList<String>(); ContentResolver cr =
	 * activity.getContentResolver(); Cursor cur =
	 * cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
	 * if (cur.getCount() > 0) { while (cur.moveToNext()) { String id =
	 * cur.getString(cur .getColumnIndex(ContactsContract.Contacts._ID)); Cursor
	 * cur1 = cr.query( ContactsContract.CommonDataKinds.Email.CONTENT_URI,
	 * null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", new
	 * String[] { id }, null); while (cur1.moveToNext()) { // to get the contact
	 * names String name = cur1 .getString(cur1
	 * .getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
	 * Log.e("Name :", name); String email = cur1 .getString(cur1
	 * .getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
	 * Log.e("Email", email); if (email != null) { names.add(email); } }
	 * cur1.close(); } } return names; }
	 * 
	 * public static ArrayList<String> getNameNumberDetails() { ContentResolver
	 * cr = activity.getContentResolver(); // Activity/Application //
	 * android.content.Context Cursor cursor =
	 * cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
	 * ArrayList<String> alContacts = new ArrayList<String>(); if
	 * (cursor.moveToFirst()) {
	 * 
	 * do { String id = cursor.getString(cursor
	 * .getColumnIndex(ContactsContract.Contacts._ID));
	 * 
	 * if (Integer .parseInt(cursor.getString(cursor
	 * .getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
	 * Cursor pCur = cr.query(
	 * ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
	 * ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]
	 * { id }, null); while (pCur.moveToNext()) { String contactNumber = pCur
	 * .getString(pCur
	 * .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
	 * Log.e("NUMBER", contactNumber); String DISPLAY_NAME = cursor
	 * .getString(cursor
	 * .getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
	 * Log.e("DISPLAY_NAME", DISPLAY_NAME); alContacts.add(contactNumber);
	 * break; } pCur.close(); }
	 * 
	 * } while (cursor.moveToNext()); } return alContacts; }
	 */

//	public static ArrayList<ContactModel> getNameNumberDetails(Activity context) {
//		ContentResolver cr = context.getContentResolver(); // Activity/Application
//		ArrayList<ContactModel> contactInfoList = new ArrayList<ContactModel>(); // android.content.Context
//		Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
//				null, null, null);
//		if (cursor.moveToFirst()) {
//
//			do {
//				String id = cursor.getString(cursor
//						.getColumnIndex(ContactsContract.Contacts._ID));
//
//				if (Integer
//						.parseInt(cursor.getString(cursor
//								.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
//					Cursor pCur = cr.query(
//							ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//							null,
//							ContactsContract.CommonDataKinds.Phone.CONTACT_ID
//									+ " = ?", new String[] { id }, null);
//					while (pCur.moveToNext()) {
//						ContactModel contactInfo = new ContactModel();
//						String contactNumber = pCur
//								.getString(pCur
//										.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//						Log.e("NUMBER", contactNumber);
//						contactInfo.setStrNumber_Email(contactNumber);
//						String contactName = cursor
//								.getString(cursor
//										.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
//						contactInfo.setStrName(contactName);
//						Log.e("DISPLAY_NAME", contactName);
//						if (contactInfo != null) {
//							contactInfoList.add(contactInfo);
//						}
//						break;
//					}
//					pCur.close();
//				}
//
//			} while (cursor.moveToNext());
//		}
//		Collections.sort(contactInfoList, new Comparator<ContactModel>() {
//            @Override
//            public int compare(ContactModel s1, ContactModel s2) {
//                return s1.getStrName().compareToIgnoreCase(s2.getStrName());
//            }
//        });
//		return contactInfoList;
//	}

//	public static ArrayList<ContactModel> getNameEmailDetails(Context context) {
//		ArrayList<ContactModel> contactInfoList = new ArrayList<ContactModel>();
//		ContentResolver cr = context.getContentResolver();
//		Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
//				null, null, null);
//		if (cursor.getCount() > 0) {
//			while (cursor.moveToNext()) {
//				String id = cursor.getString(cursor
//						.getColumnIndex(ContactsContract.Contacts._ID));
//				Cursor cur1 = cr.query(
//						ContactsContract.CommonDataKinds.Email.CONTENT_URI,
//						null, ContactsContract.CommonDataKinds.Email.CONTACT_ID
//								+ " = ?", new String[] { id }, null);
//
//				while (cur1.moveToNext()) {
//					// to get the contact names
//					ContactModel contactInfo = new ContactModel();
//					String name = cur1
//							.getString(cur1
//									.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
//					Log.e("Name :", name);
//					if (name != null && name.length() > 0)
//						contactInfo.setStrName(name);
//					String email = cur1
//							.getString(cur1
//									.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
//					if (email != null && email.length() > 0)
//						contactInfo.setStrNumber_Email(email);
//
//					Log.e("Email", email);
//					if (contactInfo != null) {
//						contactInfoList.add(contactInfo);
//					}
//				}
//				cur1.close();
//			}
//		}
//		Collections.sort(contactInfoList, new Comparator<ContactModel>() {
//            @Override
//            public int compare(ContactModel s1, ContactModel s2) {
//                return s1.getStrName().compareToIgnoreCase(s2.getStrName());
//            }
//        });
//
//		return contactInfoList;
//	}
}