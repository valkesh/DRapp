

package com.dr.app.appointment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.dr.app.MainActivity;
import com.dr.app.R;
import com.dr.app.fragment.SettingsFragment;
import com.dr.app.utility.Constants;
import com.dr.app.utility.Golly;
import com.dr.app.utility.UtilityPro;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.json.JSONObject;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.gujun.android.taggroup.TagGroup;

public class CreateEventFragmentDR extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private static final String ARG_ITEM = "item";
    private String item;
    private EditText edtName, edtNumber, edtEmail, edtEventDesc;
    private LinearLayout lnDate, lnDateofTime, lnCustomer;
    private Button btnPublish;
    private TextView txtDateLabel, txtPreferlabel, txtPatientlabel;
    private boolean is_appointment_date = false;
    final List<String> shiftsList = new ArrayList<String>();
    final List<String> patientTypeList = new ArrayList<String>();

    public CreateEventFragmentDR() {
    }

    public static CreateEventFragmentDR newInstance(String item) {
        CreateEventFragmentDR fragment = new CreateEventFragmentDR();
        Bundle args = new Bundle();
        args.putString(ARG_ITEM, item);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        item = getArguments().getString(ARG_ITEM);
        shiftsList.add("Morning");
        shiftsList.add("Afternoon");
        shiftsList.add("Evening");

        patientTypeList.add("New Patient");
        patientTypeList.add("Current Patient");
        patientTypeList.add("Returning Patient");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_eventdr, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        if (view != null) {
            edtName = (EditText) view.findViewById(R.id.edtName);
            edtNumber = (EditText) view.findViewById(R.id.edtNumber);
            edtEmail = (EditText) view.findViewById(R.id.edtEmail);
            edtEventDesc = (EditText) view.findViewById(R.id.edtEventDesc);
            lnDate = (LinearLayout) view.findViewById(R.id.lnDate);
            lnDateofTime = (LinearLayout) view.findViewById(R.id.lnDateofTime);
            lnCustomer = (LinearLayout) view.findViewById(R.id.lnCustomer);
            btnPublish = (Button) view.findViewById(R.id.btnPublish);
            txtDateLabel = (TextView) view.findViewById(R.id.txtDateLabel);
            txtPreferlabel = (TextView) view.findViewById(R.id.txtPreferlabel);
            txtPatientlabel = (TextView) view.findViewById(R.id.txtPatientlabel);
            // setOnClickListener
            btnPublish.setOnClickListener(this);
            lnDate.setOnClickListener(this);
            lnDateofTime.setOnClickListener(this);
            lnCustomer.setOnClickListener(this);


            LinearLayout ll01 = (LinearLayout)view.findViewById(R.id.mainLayer);

            WobbleView sv = new WobbleView(getActivity());
            ll01.addView(sv);


//            final TagGroup mTagGroup = (TagGroup) view.findViewById(R.id.tag_group);
//
//            ArrayList<String> strings = new ArrayList<String>();
//            final ArrayList<String> strings_new = new ArrayList<String>();
//            strings.add("valkesh patel");
//            strings.add("valkesh patel");
//            strings.add("valkesh patel");
//            strings.add("valkesh patel");
//            mTagGroup.setTags(strings);


//            mTagGroup.setTags(new String[]{"Tag1", "Tag2", "Tag3"});
//            mTagGroup.setOnTagChangeListener(new TagGroup.OnTagChangeListener() {
//                @Override
//                public void onAppend(TagGroup tagGroup, String tag) {
//                    strings_new.add(tag);
//                    mTagGroup.setSelected(true);
//                    Log.i("valkesh tag", "onViewCreated: " + strings_new.size());
//                }
//                @Override
//                public void onDelete(TagGroup tagGroup, String tag) {
//
//                }
//            });




        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnPublish:
                Button iv1 = (Button) view.findViewById(R.id.btnPublish);
                Animation shake = AnimationUtils.loadAnimation(getActivity(), R.anim.wobble);
                shake.reset();
                shake.setFillAfter(true);
                iv1.startAnimation(shake);
//                createPost();
                break;
            case R.id.lnDate:
                openDateDialog();
                break;

            case R.id.lnDateofTime:
                getPrefereDayShift(view);
                break;

            case R.id.lnCustomer:
                getPatientType(view);
                break;
        }
    }


    PopupWindow dropdownGender;
    boolean is_appointment_perefer_label = false;
    boolean is_appointment_patient_type_label = false;

    private void getPrefereDayShift(View view) {
        dropdownGender = UtilityPro.popupWindowDogs(shiftsList,
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0,
                                            View v, int arg2, long arg3) {
                        Animation fadeInAnimation = AnimationUtils
                                .loadAnimation(v.getContext(),
                                        android.R.anim.fade_in);
                        fadeInAnimation.setDuration(1000);
                        v.startAnimation(fadeInAnimation);
                        // dismiss the pop up
                        dropdownGender.dismiss();
                        // get the text and set it as the button text
                        String selectedItemText = ((TextView) v)
                                .getText().toString();
                        txtPreferlabel.setText(selectedItemText);
                        is_appointment_perefer_label = true;

                    }
                });
        dropdownGender.setWidth(view.getWidth());
        UtilityPro.log("width =  " + view.getWidth());
        dropdownGender.showAsDropDown(view, 0, 0);
    }

    private void getPatientType(View view) {
        dropdownGender = UtilityPro.popupWindowDogs(patientTypeList,
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0,
                                            View v, int arg2, long arg3) {
                        Animation fadeInAnimation = AnimationUtils
                                .loadAnimation(v.getContext(),
                                        android.R.anim.fade_in);
                        fadeInAnimation.setDuration(1000);
                        v.startAnimation(fadeInAnimation);
                        // dismiss the pop up
                        dropdownGender.dismiss();
                        // get the text and set it as the button text
                        String selectedItemText = ((TextView) v)
                                .getText().toString();
                        txtPatientlabel.setText(selectedItemText);
                        is_appointment_patient_type_label = true;
                    }
                });
        dropdownGender.setWidth(view.getWidth());
        UtilityPro.log("width =  " + view.getWidth());
        dropdownGender.showAsDropDown(view, 0, 0);
    }

    public void createPost() {
        final Animation myAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.bounce);
        // btnPublish.startAnimation(myAnim);

        if (UtilityPro.isNetworkAvaliable(getActivity())) {
            if (validatePostData()) {

                sendEmail("Hello Dr, Vinod patel, \n\n\n\n" +
                        "Requested Name:   " + edtName.getText().toString().trim() + "\n" +
                        "Requested Contact Number:   " + edtNumber.getText().toString().trim() + "\n" +
                        "Requested Email:   " + edtEmail.getText().toString().trim() + "\n" +
                        "Requested Notes:   " + edtEventDesc.getText().toString().trim() + "\n" +
                        "Requested Date:  " + txtDateLabel.getText().toString().trim() + "\n" +
                        "Requested Time:  " + txtPreferlabel.getText().toString().trim() + "\n" +
                        "Requested Type:   " + txtPatientlabel.getText().toString().trim() + "\n Thank you \n " + edtName.getText().toString().trim());
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("user_id", Pref.getUserID());
//                params.put("event_title", edtEventTitle.getText().toString().trim());
//                params.put("event_description", edtEventDesc.getText().toString().trim());
//                params.put("event_sport", sport_name);
//                params.put("sport_cat_id", sport_id);
//                params.put("event_vacancy_count", String.valueOf(player_vacancy_count));
//                params.put("event_date_time", sport_date + " " + sport_time);
//                params.put("event_lat", latitude + "");
//                params.put("event_long", longtitude + "");
//                params.put("time_zone", UtilityPro.getTimezone());
//                createEventPostWebservice(params, files);

            }
        } else {
            UtilityPro.toast(getActivity().getString(R.string.please_check_your_internet_connection));
        }

    }


    public boolean validatePostData() {
        boolean is_valid = true;

        if (edtName.getText().toString().trim().equalsIgnoreCase("")) {
            UtilityPro.toast(getString(R.string.dr_post_event_name));
            return false;
        }
        else if (edtNumber.getText().toString().trim().equalsIgnoreCase("")) {
            UtilityPro.toast(getString(R.string.dr_post_event_number));
            return false;
        }
        else if (edtEmail.getText().toString().trim().equalsIgnoreCase("")) {
            UtilityPro.toast(getString(R.string.dr_post_event_email));
            return false;
        }
        else if (!UtilityPro.isValidEmail(edtEmail.getText().toString().trim())) {
            UtilityPro.toast(getString(R.string.dr_post_event_valid_email));
            return false;
        }

        else if (is_appointment_date == false) {
            UtilityPro.toast(getString(R.string.dr_post_event_date));
            return false;
        }
        else if (is_appointment_perefer_label == false) {
            UtilityPro.toast(getString(R.string.dr_post_event_time));
            return false;
        }
        else if (is_appointment_patient_type_label == false) {
            UtilityPro.toast(getString(R.string.dr_post_event_patient_type));
            return false;
        }
        else if (edtEventDesc.getText().toString().trim().equalsIgnoreCase("")) {
            UtilityPro.toast(getString(R.string.dr_post_event_desc));
            return false;
        }
        return true;
    }

    public void openDateDialog() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
//    dpd.setDisabledDays(dates);
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

//    public void checkGps() {
//        GPSTracker gps = new GPSTracker(getActivity());
//        if (gps.canGetLocation()) { // gps enabled} // return boolean true/false
//
//            gps.getLatitude(); // returns latitude
//            gps.getLongitude(); // returns longitude
//
//            Intent intent = new Intent(getActivity(), MapResultsActivity.class);
//            startActivityForResult(intent, request_Code_Map);
//        } else {
//            // can't get location
//            // GPS or Network is not enabled
//            // Ask user to enable GPS/network in settings
//            gps.showSettingsAlert();
//        }
//    }

//    public void openSelectSport() {
//        Intent intent = new Intent(getActivity(), SportResultsActivity.class);
//        startActivityForResult(intent, request_Code_Sport);
//    }

    String sport_date = "Select date";

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        System.out.println("======year========" + year);
        System.out.println("======monthOfYear========" + monthOfYear + 1);
        System.out.println("======dayOfMonth========" + dayOfMonth);

        monthOfYear = monthOfYear + 1;
        String plusMonth = String.valueOf(monthOfYear).length() == 1 ? "0" + monthOfYear : String.valueOf(monthOfYear);
        String dayOfMonths = String.valueOf(dayOfMonth).length() == 1 ? "0" + dayOfMonth : String.valueOf(dayOfMonth);
        sport_date = year + "-" + plusMonth + "-" + dayOfMonths;
        txtDateLabel.setText(sport_date);
        txtDateLabel.setTag(true);
        is_appointment_date = true;
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        System.out.println("======hourOfDay========" + hourOfDay);
        System.out.println("======minute========" + minute);
        System.out.println("======second========" + second);

        String minutes_new = String.valueOf(minute).length() == 1 ? "0" + minute : String.valueOf(minute);
        String hoursofday_new = String.valueOf(hourOfDay).length() == 1 ? "0" + hourOfDay : String.valueOf(hourOfDay);
//        sport_time = hoursofday_new + ":" + minutes_new + ":" + "00";

//        System.out.println("===sport_time==" + sport_time);
//        System.out.println("===sport_time is12HoursFormate==" + sport_time);
//        txtTime.setText(is12HoursFormate(sport_time));
//        //txtTime.setText(sport_time);
//        is_sport_time = true;
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        if (requestCode == request_Code_Map) {
//            if (resultCode == getActivity().RESULT_OK) {
//                latitude = data.getStringExtra("map_lat");
//                longtitude = data.getStringExtra("map_long");
//                sport_address = data.getStringExtra("map_address");
//
//                System.out.println("=======sport_address====" + sport_address);
//                if (sport_address.toString().equalsIgnoreCase("")) {
//                } else {
//                    txtAddress.setText(sport_address);
//                }
//            }
//        } else if (requestCode == request_Code_Sport) {
//            sport_name = data.getStringExtra("sport_name");
//            sport_id = data.getStringExtra("sport_id");
//
//            System.out.println("=====sport_name=====" + sport_name);
//            if (sport_name.toString().equalsIgnoreCase("")) {
//                // txtSelectSport.setText("Select sport");
//            } else {
//                txtSelectSport.setText(sport_name);
//            }
//
//        } else if (requestCode == UtilityPro.CAMERA_CODE || requestCode == UtilityPro.GALLERY_CODE) {
//            System.out.println("=========requestCode======="+ requestCode);
//
//            Uri s = UtilityPro.handleImagePickerActivityResult(getActivity(),
//                    requestCode, resultCode, data);
//            System.out.println("============Uri============" + s);
//            if (s != null) {
//                imagePath = s;
//                String image_path = imagePath.toString();
//                imgEventImage.setImageURI(s);
//            }
//        }
//    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        if (requestCode == 0) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
//                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
//            }
//        }
//    }


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
                            UtilityPro.log("Appointment: responce =  "
                                    + response);
                            JSONObject responces = new JSONObject(response);
                            if (responces.getBoolean("status")) {
                                UtilityPro.toast(responces.getString("message"));
                                startActivity(new Intent(getActivity(), MainActivity.class));
                                getActivity().finish();

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


    public void sendEmail(final String body) {
        new Thread(new Runnable() {

            public void run() {

                try {

                    GMailSender sender = new GMailSender(

                            "valkeshpatel123@gmail.com",

                            "vp30221126vp");


                    // sender.addAttachment(Environment.getExternalStorageDirectory().getPath()+"/image.jpg");

                    sender.sendMail("Request for Meeting Appointment from Android", body,

                            "valkeshpatel123@gmail.com",

                            "rpahbarrie@gmail.com");


                    //

                } catch (Exception e) {

                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();

                }

            }

        }).start();

        edtName.setText("");
        edtNumber.setText("");
        edtEmail.setText("");
        edtEventDesc.setText("");
        txtPreferlabel.setText("Select time");
        txtDateLabel.setText("Select date");
        txtPatientlabel.setText("Select type");
        is_appointment_perefer_label = false;
        is_appointment_patient_type_label = false;
        is_appointment_date = false;
        showChangeLangDialog();
    }


    public void showChangeLangDialog() {

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialoglogout_dr, null);
        dialogBuilder.setView(dialogView);
        final AlertDialog b = dialogBuilder.create();
        dialogView.findViewById(R.id.btnDone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.dismiss();
            }
        });

        b.show();
    }


    public static class ConfirmLogoutDialogFragment extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            Bundle bundle = this.getArguments();

            String title_text = bundle.getString("TITLE_TEXT");
            String message_text = bundle.getString("MESSAGE_TEXT");

            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(title_text);
            builder.setMessage(message_text);
            // Create the AlertDialog object and return it
            return builder.create();
        }
    }

    private void showLogoutDialog() {
        SettingsFragment.ConfirmLogoutDialogFragment confirmDialogFragment = new SettingsFragment.ConfirmLogoutDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("TITLE_TEXT", "Success");
        bundle.putString("MESSAGE_TEXT", "We will get back as soon as possible!");
        confirmDialogFragment.setArguments(bundle);
        confirmDialogFragment.show(getActivity().getSupportFragmentManager(), SettingsFragment.ConfirmLogoutDialogFragment.class.toString());
    }
}