/*
 * Copyright (C) 2016 JetRadar
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dr.app;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.dr.app.utility.Constants;
import com.dr.app.utility.GPSTracker;
import com.dr.app.utility.Utility;
import com.dr.app.utility.UtilityPro;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class SearchResultsActivity extends FragmentActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener, DatePickerDialog.OnDateSetListener {


    LinearLayout lnSelectSport, lnLocation;
    ImageView imgBack;
    TextView txtSun, txtMon, txtTue, txtWed, txtThu, txtFri, txtSat, txtKm, txtSelectSport, txtStartDate, txtEndDate, txtAddress;
    SeekBar seekDistance;
    Button txtApplyFilter, txtClearFilter;
    int request_sport = 100;
    int request_Code_Map = 300;

    int seek_values = -1;
    boolean is_filter_Apply = false;

    String start_date = "";
    String end_date = "";
    String location_address = "";
    String location_latitude = "";
    String location_longtitude = "";

    boolean is_end_date = false;


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */


    private GoogleApiClient client;
    String sport_id = "";
    String sport_name = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_results);
        seek_values = Utility.getAppPrefInt(this, Constants.FILTER_DISTANCE);

        lnSelectSport = (LinearLayout) findViewById(R.id.lnSelectSport);
        lnLocation = (LinearLayout) findViewById(R.id.lnLocation);
        seekDistance = (SeekBar) findViewById(R.id.seekDistance);
        imgBack = (ImageView) findViewById(R.id.imgBack);
        txtSun = (TextView) findViewById(R.id.txtSun);
        txtMon = (TextView) findViewById(R.id.txtMon);
        txtTue = (TextView) findViewById(R.id.txtTue);
        txtWed = (TextView) findViewById(R.id.txtWed);
        txtThu = (TextView) findViewById(R.id.txtThu);
        txtFri = (TextView) findViewById(R.id.txtFri);
        txtSat = (TextView) findViewById(R.id.txtSat);
        txtKm = (TextView) findViewById(R.id.txtKm);
        txtSelectSport = (TextView) findViewById(R.id.txtSelectSport);
        txtStartDate = (TextView) findViewById(R.id.txtStartDate);
        txtEndDate = (TextView) findViewById(R.id.txtEndDate);
        txtAddress = (TextView) findViewById(R.id.txtAddress);


        txtApplyFilter = (Button) findViewById(R.id.txtApplyFilter);
        txtClearFilter = (Button) findViewById(R.id.txtClearFilter);

        txtApplyFilter.setOnClickListener(this);
        txtClearFilter.setOnClickListener(this);
        seekDistance.setOnSeekBarChangeListener(this);
        lnSelectSport.setOnClickListener(this);
        txtSun.setOnClickListener(this);
        txtMon.setOnClickListener(this);
        txtTue.setOnClickListener(this);
        txtWed.setOnClickListener(this);
        txtThu.setOnClickListener(this);
        txtFri.setOnClickListener(this);
        txtSat.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        lnLocation.setOnClickListener(this);
        txtStartDate.setOnClickListener(this);
        txtEndDate.setOnClickListener(this);
        txtAddress.setOnClickListener(this);

        // Set PreSelected Values
        if (seek_values == -1) {
            seekDistance.setProgress(1);
        } else {
            seekDistance.setProgress(seek_values);
        }

        if (!Utility.getAppPrefString(SearchResultsActivity.this, Constants.FILTER_DAYS).equalsIgnoreCase("")) {
            List<String> daysList = Arrays.asList(Utility.getAppPrefString(this, Constants.FILTER_DAYS).split(","));
            setPreLayout(daysList);
        }

        if (Utility.getAppPrefString(SearchResultsActivity.this, Constants.FILTER_SPORT_ID).equalsIgnoreCase("")) {
            txtSelectSport.setText("SELECT SPORT");
        } else {
            txtSelectSport.setText(Utility.getAppPrefString(this, Constants.FILTER_SPORT));
            sport_id = Utility.getAppPrefString(SearchResultsActivity.this, Constants.FILTER_SPORT_ID);
            sport_name = Utility.getAppPrefString(SearchResultsActivity.this, Constants.FILTER_SPORT);
        }
        if (Utility.getAppPrefString(SearchResultsActivity.this, Constants.FILTER_START_DATES).equalsIgnoreCase("") && Utility.getAppPrefString(SearchResultsActivity.this, Constants.FILTER_END_DATES).equalsIgnoreCase("")) {
            txtStartDate.setText("START DATE");
            txtEndDate.setText("END DATE");
        } else {
            start_date = Utility.getAppPrefString(SearchResultsActivity.this, Constants.FILTER_START_DATES);
            end_date = Utility.getAppPrefString(SearchResultsActivity.this, Constants.FILTER_END_DATES);
            txtStartDate.setText(start_date);
            txtEndDate.setText(end_date);
        }

        if (Utility.getAppPrefString(SearchResultsActivity.this, Constants.FILTER_LOCATION).equalsIgnoreCase("")) {
            txtAddress.setText("SELECT YOUR LOCATION");
        } else {
            location_address = Utility.getAppPrefString(SearchResultsActivity.this, Constants.FILTER_LOCATION);
            location_latitude = Utility.getAppPrefString(SearchResultsActivity.this, Constants.FILTER_LOCATION_LATITUDE);
            location_longtitude = Utility.getAppPrefString(SearchResultsActivity.this, Constants.FILTER_LOCATION_LONGTITUDE);
            txtAddress.setText(Utility.getAppPrefString(SearchResultsActivity.this, Constants.FILTER_LOCATION));
        }

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void setPreLayout(List<String> daysList) {
        for (int i = 0; i < daysList.size(); i++) {
            selectDays(Integer.parseInt(daysList.get(i).toString()));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lnSelectSport:
                Intent intent = new Intent(this, SportResultsActivity.class);
                startActivityForResult(intent, request_sport);
                break;
            case R.id.txtSun:
                selectDays(0);
                break;
            case R.id.txtMon:
                selectDays(1);
                break;
            case R.id.txtTue:
                selectDays(2);
                break;
            case R.id.txtWed:
                selectDays(3);
                break;
            case R.id.txtThu:
                selectDays(4);
                break;
            case R.id.txtFri:
                selectDays(5);
                break;
            case R.id.txtSat:
                selectDays(6);
                break;
            case R.id.txtClearFilter:
                clearFilter();
                break;
            case R.id.txtApplyFilter:
                applyFilter();
                break;
            case R.id.imgBack:
                resultIntent(0);
                break;
            case R.id.lnLocation:

                break;
            case R.id.txtStartDate:
                is_end_date = false;
                openDateDialog();
                break;
            case R.id.txtEndDate:
                is_end_date = true;
                if (start_date.trim().length() != 0) {
                    openEndDateDialog(start_year, start_Month, start_dayOfMonths);
                } else {
                    UtilityPro.toast("Please select start Date first.");
                }
                break;
            case R.id.txtAddress:
                openAddressMap();
                break;
        }
    }

    // Switch for days button
    boolean is_txtSun = false;
    boolean is_txtMon = false;
    boolean is_txtTue = false;
    boolean is_txtWed = false;
    boolean is_txtThu = false;
    boolean is_txtFri = false;
    boolean is_txtSat = false;

    public void selectDays(int days) {

        if (days == 0) {
            if (is_txtSun == false) {
                is_txtSun = true;
                txtSun.setBackgroundResource(R.drawable.date_selected);
            } else {
                is_txtSun = false;
                txtSun.setBackgroundResource(R.drawable.date_circle);
            }
        } else if (days == 1) {
            if (is_txtMon == false) {
                is_txtMon = true;
                txtMon.setBackgroundResource(R.drawable.date_selected);
            } else {
                is_txtMon = false;
                txtMon.setBackgroundResource(R.drawable.date_circle);
            }
        } else if (days == 2) {
            if (is_txtTue == false) {
                is_txtTue = true;
                txtTue.setBackgroundResource(R.drawable.date_selected);
            } else {
                is_txtTue = false;
                txtTue.setBackgroundResource(R.drawable.date_circle);
            }
        } else if (days == 3) {
            if (is_txtWed == false) {
                is_txtWed = true;
                txtWed.setBackgroundResource(R.drawable.date_selected);
            } else {
                is_txtWed = false;
                txtWed.setBackgroundResource(R.drawable.date_circle);
            }

        } else if (days == 4) {
            if (is_txtThu == false) {
                is_txtThu = true;
                txtThu.setBackgroundResource(R.drawable.date_selected);
            } else {
                is_txtThu = false;
                txtThu.setBackgroundResource(R.drawable.date_circle);
            }

        } else if (days == 5) {
            if (is_txtFri == false) {
                is_txtFri = true;
                txtFri.setBackgroundResource(R.drawable.date_selected);
            } else {
                is_txtFri = false;
                txtFri.setBackgroundResource(R.drawable.date_circle);
            }
        } else if (days == 6) {
            if (is_txtSat == false) {
                is_txtSat = true;
                txtSat.setBackgroundResource(R.drawable.date_selected);
            } else {
                is_txtSat = false;
                txtSat.setBackgroundResource(R.drawable.date_circle);
            }
        } else {
            is_txtSun = false;
            txtSun.setBackgroundResource(R.drawable.date_circle);
            is_txtMon = false;
            txtMon.setBackgroundResource(R.drawable.date_circle);
            is_txtTue = false;
            txtTue.setBackgroundResource(R.drawable.date_circle);
            is_txtWed = false;
            txtWed.setBackgroundResource(R.drawable.date_circle);
            is_txtThu = false;
            txtThu.setBackgroundResource(R.drawable.date_circle);
            is_txtFri = false;
            txtFri.setBackgroundResource(R.drawable.date_circle);
            is_txtSat = false;
            txtSat.setBackgroundResource(R.drawable.date_circle);
        }
    }


    boolean is_clear;

    public void clearFilter() {
        seek_values = -1;
        is_filter_Apply = false;
        seekDistance.setProgress(seek_values);

        end_date = "";
        start_date = "";


        sport_id = "";
        sport_name = "";
        txtSelectSport.setText("SELECT SPORT");
        txtStartDate.setText("START DATE");
        txtEndDate.setText("END DATE");
        is_clear = true;
        location_address = "";
        location_latitude = "";
        location_longtitude = "";
        txtAddress.setText("SELECT YOUR LOCATION");
        selectDays(102);
        Utility.writeSharedPreferencesInt(this, Constants.FILTER_DISTANCE, seek_values);
        Utility.writeSharedPreferences(this, Constants.FILTER_DAYS, "");
        Utility.writeSharedPreferences(this, Constants.FILTER_SPORT_ID, "");
        Utility.writeSharedPreferences(this, Constants.FILTER_SPORT, "");
        Utility.writeSharedPreferences(this, Constants.FILTER_START_DATES, "");
        Utility.writeSharedPreferences(this, Constants.FILTER_END_DATES, "");

        Utility.writeSharedPreferences(this, Constants.FILTER_LOCATION, "");
        Utility.writeSharedPreferences(this, Constants.FILTER_LOCATION_LATITUDE, "");
        Utility.writeSharedPreferences(this, Constants.FILTER_LOCATION_LONGTITUDE, "");

        //resultIntent(1);
    }

    int filter_count = 0;

    public void applyFilter() {

        date_no = "";
        filter_count = 0;
        date_no = dateValidate();
        is_clear = false;
        if (seek_values == -1) {
            Utility.writeSharedPreferencesInt(this, Constants.FILTER_DISTANCE, -1);
        } else {
            Utility.writeSharedPreferencesInt(this, Constants.FILTER_DISTANCE, seek_values);
        }

        Utility.writeSharedPreferences(this, Constants.FILTER_DAYS, date_no);
        Utility.writeSharedPreferences(this, Constants.FILTER_SPORT_ID, sport_id);
        Utility.writeSharedPreferences(this, Constants.FILTER_SPORT, sport_name);
        Utility.writeSharedPreferences(this, Constants.FILTER_LOCATION, location_address);
        Utility.writeSharedPreferences(this, Constants.FILTER_LOCATION_LATITUDE, location_latitude);
        Utility.writeSharedPreferences(this, Constants.FILTER_LOCATION_LONGTITUDE, location_longtitude);
        Utility.writeSharedPreferences(this, Constants.FILTER_START_DATES, start_date);
        Utility.writeSharedPreferences(this, Constants.FILTER_END_DATES, end_date);
        if (isValidateDate()) {
            resultIntent(2);
        }
    }

    private String dateValidate() {
        String value = "";
        if (is_txtSun) {
            value = addStringvalue("0");
        }
        if (is_txtMon) {
            value = addStringvalue("1");
        }
        if (is_txtTue) {
            value = addStringvalue("2");
        }
        if (is_txtWed) {
            value = addStringvalue("3");
        }
        if (is_txtThu) {
            value = addStringvalue("4");
        }
        if (is_txtFri) {
            value = addStringvalue("5");
        }
        if (is_txtSat) {
            value = addStringvalue("6");
        }
        return value;
    }

    String date_no = "";

    private String addStringvalue(String value) {
        if (date_no.equalsIgnoreCase("") && date_no.toString().length() == 0) {
            date_no = value;
        } else {
            date_no = date_no + "," + value;
        }
        return date_no;
    }

    public void resultIntent(int flag) {
        Intent data = new Intent();
//        data.putExtra("is_filter", flag);
//        data.putExtra("is_clear", is_clear);
//        data.putExtra("date_no", date_no);
//        data.putExtra("seek_values", seek_values + "");
//        data.putExtra("sport_id", sport_id + "");
//        data.putExtra("sport_name", sport_name);
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        if (i == 0) {
            seek_values = -1;
            txtKm.setText("0 KM");
        } else {
            seek_values = i;
            txtKm.setText(String.valueOf(seek_values) + " KM");
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == request_Code_Map) {
            if (resultCode == SearchResultsActivity.this.RESULT_OK) {
                location_latitude = data.getStringExtra("map_lat");
                location_longtitude = data.getStringExtra("map_long");
                location_address = data.getStringExtra("map_address");
                System.out.println("=======sport_address====" + location_address);
                if (location_address.toString().equalsIgnoreCase("")) {
                } else {
                    txtAddress.setText(location_address);
                }
            }
        } else if (requestCode == request_sport) {
            if (resultCode == this.RESULT_OK) {
                sport_name = data.getStringExtra("sport_name");
                sport_id = data.getStringExtra("sport_id");
                if (sport_name.toString().equalsIgnoreCase("")) {
                } else {
                    txtSelectSport.setText(sport_name);
                }
            }
        }
    }

//    @Override
//    public void onBackPressed() {
//       // resultIntent(0);
//    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (Integer.parseInt(Build.VERSION.SDK) > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            resultIntent(0);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("SearchResults Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    public void openDateDialog() {
        Calendar now = Calendar.getInstance();

        DatePickerDialog dpd = DatePickerDialog.newInstance(
                (DatePickerDialog.OnDateSetListener) SearchResultsActivity.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
//    dpd.setDisabledDays(dates);
        dpd.setMinDate(now);
        dpd.show(SearchResultsActivity.this.getFragmentManager(), "Datepickerdialog");
        dpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                Log.d("DatePicker", "Dialog was cancelled");
            }
        });
    }

    public void openEndDateDialog(int start_year,
                                  int start_Month,
                                  int start_dayOfMonths) {
        Calendar now = Calendar.getInstance();

        now.set(start_year, start_Month, start_dayOfMonths);

        DatePickerDialog dpd = DatePickerDialog.newInstance(
                (DatePickerDialog.OnDateSetListener) SearchResultsActivity.this,
                start_year,
                start_Month,
                start_dayOfMonths
        );
//    dpd.setDisabledDays(dates);
        dpd.setMinDate(now);
        dpd.show(SearchResultsActivity.this.getFragmentManager(), "Datepickerdialog");
        dpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                Log.d("DatePicker", "Dialog was cancelled");
            }
        });
    }


    int start_year;
    int start_Month;
    int start_dayOfMonths;

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        System.out.println("======year========" + year);
        System.out.println("======monthOfYear========" + monthOfYear + 1);
        System.out.println("======dayOfMonth========" + dayOfMonth);

        monthOfYear = monthOfYear + 1;
        String plusMonth = String.valueOf(monthOfYear).length() == 1 ? "0" + monthOfYear : String.valueOf(monthOfYear);
        String dayOfMonths = String.valueOf(dayOfMonth).length() == 1 ? "0" + dayOfMonth : String.valueOf(dayOfMonth);

        if (is_end_date == false) {
            start_date = year + "-" + plusMonth + "-" + dayOfMonths;
            txtStartDate.setText(start_date);
            start_year = year;
            start_Month = monthOfYear;
            start_dayOfMonths = dayOfMonth;
        } else {
            end_date = year + "-" + plusMonth + "-" + dayOfMonths;
            txtEndDate.setText(end_date);
        }
    }

    public boolean isValidateDate() {
        boolean isValid = false;
        if (start_date.trim().length() != 0 && !start_date.equalsIgnoreCase("")) {
            if (end_date.trim().length() != 0) {
                isValid = true;
            } else {
                UtilityPro.toast("Please select End Date.");
                isValid = false;
            }
        } else {
            isValid = true;
        }
        return isValid;
    }


    //Map Functionality

    public void checkGps() {
        GPSTracker gps = new GPSTracker(SearchResultsActivity.this);
        if (gps.canGetLocation()) { // gps enabled} // return boolean true/false

            gps.getLatitude(); // returns latitude
            gps.getLongitude(); // returns longitude

            Intent intent = new Intent(SearchResultsActivity.this, MapResultsActivity.class);
            startActivityForResult(intent, request_Code_Map);
        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }
    }

    public void openAddressMap() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(SearchResultsActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(SearchResultsActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(SearchResultsActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 0);

                return;
            } else {
                checkGps();
            }
        } else {
            checkGps();
        }
    }
}
