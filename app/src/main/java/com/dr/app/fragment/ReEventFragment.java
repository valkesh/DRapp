

package com.dr.app.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.dr.app.MainActivity;
import com.dr.app.MapResultsActivity;
import com.dr.app.R;
import com.dr.app.SportResultsActivity;
import com.dr.app.utility.Constants;
import com.dr.app.utility.GPSTracker;
import com.dr.app.utility.Golly;
import com.dr.app.utility.Pref;
import com.dr.app.utility.Utility;
import com.dr.app.utility.UtilityPro;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ReEventFragment extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private static final String ARG_ITEM = "item";

    int request_Code_Map = 300;
    int request_Code_Sport = 700;

    private String item;
    ImageView imgEventImage, imgMinus, imgPlus, btnBack;
    EditText edtEventTitle, edtEventDesc;
    TextView txtSelectSport, txtDate, txtTime, txtAddress, txtCount, txtRepost;
    Button btnSave;
    int player_vacancy_count = 0;
    String sport_name = "";
    String sport_id = "";
    String sport_date = "";
    String sport_time = "";
    boolean is_sport_date;
    boolean is_sport_time;
    String sport_address = "Select Address";



    String latitude = "0.0";
    String longtitude = "0.0";
    ImageLoader imageLoader = null;
    DisplayImageOptions options;

    String camera_image_path;
    boolean is_camera = false;
    Uri imagePath = null;
    public HashMap<Integer, File> files;
    String image_non_edit = "";
    boolean isAcitve = false;

    public ReEventFragment() {
    }

    public static ReEventFragment newInstance(String item , boolean is_edited) {
        ReEventFragment fragment = new ReEventFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ITEM, item);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        item = getArguments().getString(ARG_ITEM);
        files = new HashMap<Integer, File>();

        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.img_section)
                .showImageForEmptyUri(R.drawable.img_section)
                .showImageOnFail(R.drawable.img_section).cacheInMemory(true)
                .cacheOnDisk(true).considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565).build();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_repost_event, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        imgEventImage = (ImageView) view.findViewById(R.id.imgEventImage);
        imgMinus = (ImageView) view.findViewById(R.id.imgMinus);
        imgPlus = (ImageView) view.findViewById(R.id.imgPlus);
        edtEventTitle = (EditText) view.findViewById(R.id.edtEventTitle);
        edtEventDesc = (EditText) view.findViewById(R.id.edtEventDesc);
        txtSelectSport = (TextView) view.findViewById(R.id.txtSelectSport);
        txtDate = (TextView) view.findViewById(R.id.txtDate);
        txtTime = (TextView) view.findViewById(R.id.txtTime);
        txtAddress = (TextView) view.findViewById(R.id.txtAddress);
        txtCount = (TextView) view.findViewById(R.id.txtCount);
        txtRepost = (TextView) view.findViewById(R.id.txtRepost);
        btnSave = (Button) view.findViewById(R.id.btnSave);
        btnBack = (ImageView) view.findViewById(R.id.btnBack);


        imgEventImage.setOnClickListener(this);
        imgMinus.setOnClickListener(this);
        imgPlus.setOnClickListener(this);
        txtSelectSport.setOnClickListener(this);
        txtDate.setOnClickListener(this);
        txtTime.setOnClickListener(this);
        txtAddress.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        txtRepost.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        final Map<String, String> params = new HashMap<String, String>();
        if (Pref.getIsLogin()) {
            params.put("user_id", Pref.getUserID());
            params.put("time_zone", UtilityPro.getTimezone());
        }
        params.put("event_id", item);
        event_id = Integer.parseInt(item);
        getEventDetails(params);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnBack:
                ((MainActivity) getActivity()).onBackPressed();
                break;
            case R.id.imgEventImage:
                clickImage();
                break;
            case R.id.imgMinus:
                clickMinus();
                break;
            case R.id.imgPlus:
                clickPlus();
                break;
            case R.id.txtSelectSport:
                openSelectSport();
                break;
            case R.id.txtDate:
                openDateDialog();
                break;
            case R.id.txtTime:
                openTimeDialog();
                break;
            case R.id.txtAddress:
                openAddressMap();
                break;
            case R.id.btnSave:
                rePostEvent();
                break;
        }
    }

    private void clickImage() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);

            } else {
                selectImage();
            }
        } else {
            selectImage();
        }
    }

    public void editPost() {
        if (UtilityPro.isNetworkAvaliable(getActivity())) {
            if (validatePostData()) {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", Pref.getUserID());
                params.put("event_id", event_id + "");
                params.put("event_title", edtEventTitle.getText().toString().trim());
                params.put("event_description", edtEventDesc.getText().toString().trim());
                params.put("event_sport", sport_name);
                params.put("sport_cat_id", sport_id);
                params.put("event_vacancy_count", String.valueOf(player_vacancy_count));
                params.put("event_date_time", sport_date + " " + sport_time);
                params.put("event_lat", latitude + "");
                params.put("event_long", longtitude + "");
                params.put("time_zone", UtilityPro.getTimezone());

                if (imagePath != null) {
                    File file = null;
                    if (is_camera) {
                        file = new File(camera_image_path);
                    } else {
                        file = new File(UtilityPro
                                .getRealPathFromURI(imagePath));
                    }
                    files.put(0, file);
                } else {
                    params.put("image_non_edit", image_non_edit);
                }
                editEventPostWebservice(params, files);
            }
        } else {
            UtilityPro.toast(getActivity().getString(R.string.please_check_your_internet_connection));
        }
    }

    public void rePostEvent() {
        if (UtilityPro.isNetworkAvaliable(getActivity())) {
            if (validatePostData()) {
                System.out.println("=====latitude====="+latitude + " " + latitude);

                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", Pref.getUserID());
                params.put("event_id", event_id + "");
                params.put("event_title", edtEventTitle.getText().toString().trim());
                params.put("event_description", edtEventDesc.getText().toString().trim());
                params.put("event_sport", sport_name);
                params.put("sport_cat_id", sport_id);
                params.put("event_vacancy_count", String.valueOf(player_vacancy_count));
                params.put("event_date_time", sport_date + " " + sport_time);
                params.put("event_lat", latitude + "");
                params.put("event_long", longtitude+ "");
                params.put("time_zone", UtilityPro.getTimezone());

                if (imagePath != null) {
                    File file = null;
                    if (is_camera) {
                        file = new File(camera_image_path);
                    } else {
                        file = new File(UtilityPro
                                .getRealPathFromURI(imagePath));
                    }
                    files.put(0, file);
                } else {
                    params.put("image_non_edit", image_non_edit);
                }
                createEventPostWebservice(params, files);
            }
        } else {
            UtilityPro.toast(getActivity().getString(R.string.please_check_your_internet_connection));
        }
    }


    public boolean validatePostData() {
        boolean is_valid = true;

        if (Pref.getIsLogin() == false) {
            UtilityPro.toast(getString(R.string.not_login));
            return false;
        }
        if (edtEventTitle.getText().toString().trim().equalsIgnoreCase("")) {
            UtilityPro.toast(getString(R.string.post_event_title));
            return false;
        }
        if (edtEventDesc.getText().toString().trim().equalsIgnoreCase("")) {
            UtilityPro.toast(getString(R.string.post_event_disc));
            return false;
        }
        if (sport_name.equalsIgnoreCase("") || sport_name.equalsIgnoreCase("Select sport")) {
            UtilityPro.toast(getString(R.string.post_event_sport));
            return false;
        }
        if (is_sport_date == false) {
            UtilityPro.toast(getString(R.string.post_event_date));
            return false;
        }
        if (is_sport_time == false) {
            UtilityPro.toast(getString(R.string.post_event_time));
            return false;
        }

        if (player_vacancy_count == 0) {
            UtilityPro.toast(getString(R.string.post_event_sport_vacancy));
            return false;
        }
        if (latitude.equalsIgnoreCase("0.0")) {
            UtilityPro.toast(getString(R.string.post_event_address));
            return false;
        }
        return is_valid;
    }

    public void openDateDialog() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
//      dpd.setDisabledDays(dates);
        dpd.setMinDate(now);
        dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
        dpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                Log.d("DatePicker", "Dialog was cancelled");
            }
        });
    }


    public void openTimeDialog() {
        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance(
                this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                now.get(Calendar.SECOND),
                false
        );


//    tpd.setMinTime(now.get(Calendar.HOUR_OF_DAY),
//            now.get(Calendar.MINUTE),
//            now.get(Calendar.SECOND));

        tpd.setTitle("TimePicker Title");
        tpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                Log.d("TimePicker", "Dialog was cancelled");
            }
        });
        tpd.show(getActivity().getFragmentManager(), "Timepickerdialog");
    }

    public void openAddressMap() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 0);

                return;
            } else {
                checkGps();
            }
        } else {
            checkGps();
        }
    }

    public String is12HoursFormate(String time) {

        Date dateObj = null;
        try {
            final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            dateObj = sdf.parse(time);
            System.out.println(dateObj);
            System.out.println(new SimpleDateFormat("K:mm a").format(dateObj));
        } catch (final ParseException e) {
            e.printStackTrace();
        }
        return new SimpleDateFormat("K:mm a").format(dateObj);
    }

    public void checkGps() {
        GPSTracker gps = new GPSTracker(getActivity());
        if (gps.canGetLocation()) { // gps enabled} // return boolean true/false

            gps.getLatitude(); // returns latitude
            gps.getLongitude(); // returns longitude

            Intent intent = new Intent(getActivity(), MapResultsActivity.class);
            startActivityForResult(intent, request_Code_Map);
        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }
    }

    public void openSelectSport() {
        Intent intent = new Intent(getActivity(), SportResultsActivity.class);
        startActivityForResult(intent, request_Code_Sport);
    }

    public void clickMinus() {
        if (player_vacancy_count > 0) {
            player_vacancy_count--;
            txtCount.setText(String.valueOf(player_vacancy_count));
        }
    }

    public void clickPlus() {
        player_vacancy_count++;
        txtCount.setText(String.valueOf(player_vacancy_count));
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        System.out.println("======year========" + year);
        System.out.println("======monthOfYear========" + monthOfYear + 1);
        System.out.println("======dayOfMonth========" + dayOfMonth);

        monthOfYear = monthOfYear + 1;
        String plusMonth = String.valueOf(monthOfYear).length() == 1 ? "0" + monthOfYear : String.valueOf(monthOfYear);
        String dayOfMonths = String.valueOf(dayOfMonth).length() == 1 ? "0" + dayOfMonth : String.valueOf(dayOfMonth);
        sport_date = year + "-" + plusMonth + "-" + dayOfMonths;
        txtDate.setText(sport_date);
        is_sport_date = true;
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        System.out.println("======hourOfDay========" + hourOfDay);
        System.out.println("======minute========" + minute);
        System.out.println("======second========" + second);

        String minutes_new = String.valueOf(minute).length() == 1 ? "0" + minute : String.valueOf(minute);
        String hoursofday_new = String.valueOf(hourOfDay).length() == 1 ? "0" + hourOfDay : String.valueOf(hourOfDay);
        sport_time = hoursofday_new + ":" + minutes_new + ":" + "00";

        System.out.println("===sport_time==" + sport_time);
        System.out.println("===sport_time is12HoursFormate==" + sport_time);
        txtTime.setText(is12HoursFormate(sport_time));
        //txtTime.setText(sport_time);
        is_sport_time = true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == request_Code_Map) {
            if (resultCode == getActivity().RESULT_OK) {
                latitude = data.getStringExtra("map_lat");
                longtitude = data.getStringExtra("map_long");
                sport_address = data.getStringExtra("map_address");

                System.out.println("=======sport_address====" + sport_address);
                if (sport_address.toString().equalsIgnoreCase("")) {
                } else {
                    txtAddress.setText(sport_address);
                }
            }
        } else if (requestCode == request_Code_Sport) {
            sport_name = data.getStringExtra("sport_name");
            sport_id = data.getStringExtra("sport_id");

            System.out.println("=====sport_name=====" + sport_name);
            if (sport_name.toString().equalsIgnoreCase("")) {
                // txtSelectSport.setText("Select sport");
            } else {
                txtSelectSport.setText(sport_name);
            }

        } else {
            Uri s = UtilityPro.handleImagePickerActivityResult(getActivity(),
                    requestCode, resultCode, data);
            if (s != null) {
                imagePath = s;
                String image_path = imagePath.toString();
                imgEventImage.setImageURI(s);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

            }
        }
    }


    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Profile Image");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    is_camera = true;
                    camera_image_path = Utility.getOutputFilePath("");
                    UtilityPro.selectImageFromCameranew(getActivity(),
                            camera_image_path, false);

                } else if (items[item].equals("Choose from Library")) {
                    is_camera = false;
                    UtilityPro.selectImageFromGalary(getActivity(),
                            "profile_galary_image.jpg", false);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    // Edit or registration process
    private void createEventPostWebservice(Map<String, String> params, HashMap<Integer, File> files) {
        Golly.showDarkProgressDialog(getActivity());
        Golly.multipartStringRequest("event_image", Constants.BASE_URL + Constants.CREATEEVENTPOST,
                params, files, Request.Method.POST,
                "event post", new Golly.GollyListner() {
                    @Override
                    public void successResponce(String response, String TAG,
                                                Boolean FROM_CAHCE) {
                        try {
                            UtilityPro.log("event post data : responce =  "
                                    + response);
                            JSONObject responces = new JSONObject(response);
                            if (responces.getBoolean("status")) {
                                // ((MainActivity) getActivity()).showFragment(EditEventFragment.newInstance(""), false);
                                ((MainActivity) getActivity()).onBackPressed();
                            } else {
                                UtilityPro.toast(responces.getString("message"));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void errorResponce() {

                    }
                });
    }

    // Edit or registration process
    private void editEventPostWebservice(Map<String, String> params, HashMap<Integer, File> files) {
        Golly.showDarkProgressDialog(getActivity());
        Golly.multipartStringRequest("event_image", Constants.BASE_URL + Constants.EDITEVENTPOST,
                params, files, Request.Method.POST,
                "event edit post", new Golly.GollyListner() {
                    @Override
                    public void successResponce(String response, String TAG,
                                                Boolean FROM_CAHCE) {
                        try {
                            UtilityPro.log("event edit post data : responce =  "
                                    + response);
                            JSONObject responces = new JSONObject(response);
                            if (responces.getBoolean("status")) {
                                // ((MainActivity) getActivity()).showFragment(EditEventFragment.newInstance(""), false);
                                UtilityPro.toast(responces.getString("message"));
                            } else {
                                UtilityPro.toast(responces.getString("message"));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void errorResponce() {

                    }
                });
    }

    int event_id = 0;
    int game_id = 0;


    public void getEventDetails(Map<String, String> params) {
        String Url = Constants.EVENT_DETAILS;
        Golly.showDarkProgressDialog(getActivity());
        Golly.FireDarkWebCall(Url, params, Request.Method.POST, "API_CALL GET EVENT_DETAILS",
                new Golly.GollyListner() {
                    @Override
                    public void successResponce(String response, String TAG,
                                                Boolean FROM_CAHCE) {
                        try {
                            try {
                                JSONObject responce_obj = new JSONObject(response);
                                if (responce_obj.getBoolean("status")) {
                                    ((MainActivity) getActivity()).updateNotification(UtilityPro.getDataFromJsonInt(responce_obj, "notification_count"));
                                    JSONObject json2 = responce_obj.getJSONObject("results");
                                    JSONArray image_url = json2.getJSONArray("event_join_user_image");
                                    isAcitve =   UtilityPro.getDataFromJsonBoolean(json2, "event_status");
                                    game_id = UtilityPro.getDataFromJsonInt(json2, "game_id");
                                    sport_id = String.valueOf(game_id);
                                    event_id = UtilityPro.getDataFromJsonInt(json2, "event_id");
                                    //txtCreaterName.setText(UtilityPro.getDataFromJson(json2, "creator_name"));
                                    // edtEventTitle.setText(UtilityPro.getDataFromJson(json2, "game_name"));
                                    sport_name = UtilityPro.getDataFromJson(json2, "game_name");
                                    txtSelectSport.setText(UtilityPro.getDataFromJson(json2, "game_name"));
                                    edtEventTitle.setText(UtilityPro.getDataFromJson(json2, "event_name"));
                                    txtCount.setText(UtilityPro.getDataFromJson(json2, "event_vacancy_count"));
                                    if (UtilityPro.getDataFromJson(json2, "event_vacancy_count").equalsIgnoreCase("")) {
                                        player_vacancy_count = 0;
                                    } else {
                                        player_vacancy_count = Integer.parseInt(UtilityPro.getDataFromJson(json2, "event_vacancy_count"));
                                    }
                                    String[] splitStr = UtilityPro.getDataFromJson(json2, "event_full_date_time").split("\\s+");

                                    if (UtilityPro.getDataFromJson(json2, "event_address").equalsIgnoreCase("")) {
                                        txtAddress.setText("Select Address");
                                        latitude = "0.0";
                                        longtitude = "0.0";
                                    } else {
                                        txtAddress.setText(UtilityPro.getDataFromJson(json2, "event_address"));
                                        latitude = UtilityPro.getDataFromJson(json2, "event_lat");
                                        longtitude = UtilityPro.getDataFromJson(json2, "event_long");
                                    }

                                    edtEventDesc.setText(UtilityPro.getDataFromJson(json2, "event_description"));
                                   // txtDate.setText(splitStr[0]);

//                                    sport_date = splitStr[0];
//                                    sport_time = splitStr[1];

                                   // txtTime.setText(UtilityPro.getDataFromJson(json2, "event_time"));
                                    //  txtMonth.setText(UtilityPro.getDataFromJson(json2, "event_month"));
                                    // creator_id = UtilityPro.getDataFromJsonInt(json2, "creator_id");
                                    // is_active = UtilityPro.getDataFromJsonBoolean(json2, "event_status");
                                    image_non_edit = UtilityPro.getDataFromJson(json2, "event_image");
                                    if (UtilityPro.getDataFromJson(json2, "event_image") == null && UtilityPro.getDataFromJson(json2, "event_image").equals("") &&
                                            UtilityPro.getDataFromJson(json2, "event_image").length() > 6) {
                                    } else {
                                        imageLoader.displayImage(UtilityPro.getDataFromJson(json2, "event_image"),
                                                imgEventImage, options, new SimpleImageLoadingListener() {
                                                    @Override
                                                    public void onLoadingStarted(String imageUri, View view) {
                                                        // holder.progressBar.setProgress(0);
                                                        // holder.progressBar.setVisibility(View.VISIBLE);
                                                    }

                                                    @Override
                                                    public void onLoadingFailed(String imageUri, View view,
                                                                                FailReason failReason) {
                                                        // holder.progressBar.setVisibility(View.GONE);
                                                    }

                                                    @Override
                                                    public void onLoadingComplete(String imageUri, View view,
                                                                                  Bitmap loadedImage) {
                                                        // holder.progressBar.setVisibility(View.GONE);
                                                    }
                                                }, new ImageLoadingProgressListener() {
                                                    @Override
                                                    public void onProgressUpdate(String imageUri, View view,
                                                                                 int current, int total) {
                                                        // holder.progressBar.setProgress(Math.round(100.0f
                                                        // * current / total));
                                                    }
                                                });
                                    }

                                } else {
                                    if (!UtilityPro.getDataFromJson(responce_obj, "message").equalsIgnoreCase("")) {
                                        UtilityPro.toast(UtilityPro.getDataFromJson(responce_obj, "message"));
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void errorResponce() {

                    }
                });
    }
}