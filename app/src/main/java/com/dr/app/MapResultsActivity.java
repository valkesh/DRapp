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


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.text.ICUCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;

import com.dr.app.adapter.GooglePlaceAPIAdapter;
import com.dr.app.utility.CustomAutoCompleteTextView;
import com.dr.app.utility.GPSTracker;
import com.dr.app.utility.Golly;
import com.dr.app.utility.PlaceJSONParser;
import com.dr.app.utility.Pref;
import com.dr.app.utility.ProgressWheel;
import com.dr.app.utility.Utility;
import com.dr.app.utility.UtilityPro;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.dr.app.R.id.map;

public class MapResultsActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

    Button btnCancleMap, btnOkMap;
    private GoogleMap mMap;
    GPSTracker gps;

    double latitude = 0.0;
    double longitude = 0.0;
    String address = "";
    CustomAutoCompleteTextView address_auto;
    List<String> suggestion_list_id = new ArrayList<String>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_results);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);
        btnCancleMap = (Button) findViewById(R.id.btnCancleMap);
        btnOkMap = (Button) findViewById(R.id.btnOkMap);

        btnCancleMap.setOnClickListener(this);
        btnOkMap.setOnClickListener(this);
        address_auto = (CustomAutoCompleteTextView) findViewById(R.id.address);
        gps = new GPSTracker(MapResultsActivity.this);
        if (gps.canGetLocation()) { // gps enabled} // return boolean true/false

            gps.getLatitude(); // returns latitude
            gps.getLongitude(); // returns longitude

            System.out.println("======gps.getLatitude() MapResultsActivity========" + gps.getLatitude());
            System.out.println("======gps.getLongitude() MapResultsActivity========" + gps.getLongitude());

        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }

//    new GoogleApiClient.Builder(MapResultsActivity.this)
//            .addApi(LocationServices.API)
//            .addConnectionCallbacks(this)
//            .addOnConnectionFailedListener(this)
//            .build();


        address_auto.setThreshold(3);
        address_auto.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

                if (Utility.isNetworkAvaliable(MapResultsActivity.this) == true) {
                    placesTask = new PlacesTask();
                    placesTask.execute(s.toString());
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });

        address_auto.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @SuppressWarnings("static-access")
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {

                // GeocodingLocation locationAddress = new GeocodingLocation();
                // locationAddress.getAddressFromLocation(address.getText()
                // .toString(), getActivity(), new GeocoderHandler());
                try {

                    View view = MapResultsActivity.this.getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                    address = address_auto.getText().toString();
                    GetLatLng getter = new GetLatLng();
                    getter.execute(suggestion_list_id.get(arg2));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    Marker mMarker;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng sydney = new LatLng(gps.getLatitude(), gps.getLongitude());
//    LatLng sydney = new LatLng(-34, 151);

        MarkerOptions markerOptions = new MarkerOptions().position(sydney);
//    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_small));
//    mMarker = mMap.addMarker(markerOptions);

        //  mMarker.setDraggable(true);
        mMarker = mMap.addMarker(markerOptions.title("").draggable(true));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.getMaxZoomLevel();
        mMap.setMyLocationEnabled(true);
        CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(sydney, 14);
        mMap.animateCamera(yourLocation);
        mMap.getUiSettings().setAllGesturesEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);

        latitude = gps.getLatitude();
        longitude = gps.getLongitude();

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {
                // TODO Auto-generated method stub
                latitude = point.latitude;
                longitude = point.longitude;
                if (mMarker != null) {
//          drawMarkerWithCircle(point);
                    updateMarkerWithCircle(point);
                }
//        else {
//          mMap.clear();
//          drawMarkerWithCircle(point);
//          // updateMarkerWithCircle(point);
//        }

                System.out.println("======gps.getLatitude() ========" + latitude);
                System.out.println("======gps.getLongitude() ========" + longitude);

                CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(point, 14);
                AsyncTaskRunner asyncTaskRunner = new AsyncTaskRunner(String.valueOf(latitude), String.valueOf(longitude), MapResultsActivity.this, false);
                asyncTaskRunner.execute("");
                //mMap.animateCamera(yourLocation);
                //  address = UtilityPro.GetAddress(MapResultsActivity.this, latitude, longitude);
//        edtAddress.setText(address);
            }
        });


        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {
                latitude = marker.getPosition().latitude;
                longitude = marker.getPosition().longitude;
                updateMarkerWithCircle(marker.getPosition());
                System.out.println("==========map drag============");
            }

            @Override
            public void onMarkerDrag(Marker marker) {
                latitude = marker.getPosition().latitude;
                longitude = marker.getPosition().longitude;
                updateMarkerWithCircle(marker.getPosition());
            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                latitude = marker.getPosition().latitude;
                longitude = marker.getPosition().longitude;
                updateMarkerWithCircle(marker.getPosition());
                AsyncTaskRunner asyncTaskRunner = new AsyncTaskRunner(String.valueOf(latitude), String.valueOf(longitude), MapResultsActivity.this, false);
                asyncTaskRunner.execute("");
            }
        });
        AsyncTaskRunner asyncTaskRunner = new AsyncTaskRunner(String.valueOf(latitude), String.valueOf(longitude), MapResultsActivity.this, false);
        asyncTaskRunner.execute("");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnCancleMap:
                resultIntent(false);
                break;
            case R.id.btnOkMap:
                if (address_auto.getText().toString().length() != 0) {
                    resultIntent(true);
                } else {
                    AsyncTaskRunner asyncTaskRunner = new AsyncTaskRunner(String.valueOf(latitude), String.valueOf(longitude), MapResultsActivity.this, true);
                    asyncTaskRunner.execute("");
                }
                break;
        }
    }

    public void resultIntent(boolean flag) {
        Intent data = new Intent();
        data.putExtra("map_result", flag);
        data.putExtra("map_lat", latitude + "");
        data.putExtra("map_long", longitude + "");
        data.putExtra("map_address", address);
        setResult(RESULT_OK, data);
        finish();
    }


    private void updateMarkerWithCircle(LatLng position) {
        mMarker.setPosition(position);
        CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(position, 14);
        mMap.animateCamera(yourLocation);
    }

    private void drawMarkerWithCircle(LatLng position) {
        MarkerOptions markerOptions = new MarkerOptions().position(position);
//        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.address));
        mMarker = mMap.addMarker(markerOptions);
    }

    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        String latitude = "0.0";
        String longitude = "0.0";
        Context con;
        boolean is_closed = false;

        public AsyncTaskRunner(String latitude, String longitude, Context con, boolean is_closed) {
            this.con = con;
            this.latitude = latitude;
            this.longitude = longitude;
            this.is_closed = is_closed;
        }

        @Override
        protected String doInBackground(String... params) {

            System.out.println("====latitude========" + latitude);
            System.out.println("====longitude========" + longitude);

            address = UtilityPro.GetAddress(con, Double.parseDouble(latitude), Double.parseDouble(longitude));

            return "";
        }

        @Override
        protected void onPostExecute(String result) {
            Golly.hideDarkProgressDialog();
            System.out.println("====address========" + address);
//            if (address.trim().length() == 0) {
            address_auto.setText(address);


            if (is_closed) {
                resultIntent(true);
            }

//            } else {
//                resultIntent(true);
//            }
        }

        @Override
        protected void onPreExecute() {
            Golly.showDarkProgressDialogAct(con);
        }

        @Override
        protected void onProgressUpdate(String... text) {

        }
    }

    PlacesTask placesTask;
    ParserTask parserTask;

    // Fetches all places from GooglePlaces AutoComplete Web Service
    private class PlacesTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... place) {
            // For storing data from web service
            String data = "";

            // Obtain browser key from https://code.google.com/apis/console
            String key = "key=AIzaSyDuHbXqUk0T_CdQoJHVqUz58Dp5qJgwIhE";

            String input = "";

            try {
                input = "input=" + URLEncoder.encode(place[0], "utf-8");
                // input = "input=" + place[0];
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }

            // place type to be searched
            // String types = "types=address";
            // String types = "types=geocode";

            // Sensor enabled
            // String sensor = "sensor=false";

            // Building the parameters to the web service
            // String parameters = input + "&" + types + "&" + sensor + "&" +
            // key;

            // Output format
            String output = "json";
//            String google_key = key;
            String google_key = getString(R.string.google_place_api_key);
            // Building the url to the web service

            String url = "https://maps.googleapis.com/maps/api/place/autocomplete/json?"
                    + input
                    + "&types=establishment&location="
                    + latitude
                    + ","
                    + longitude + "&radius=5000000&key=" + google_key;

            // String url =
            // "https://maps.googleapis.com/maps/api/place/autocomplete/json?"
            // + input
            // + "&types=establishment&location="
            // + latitude
            // + ","
            // + longtitude
            // + "&radius=5000000&key=AIzaSyA77Uw-7unpQKdsEo6xCAYBpFjiCChD4W8";

            // String url =
            // "https://maps.googleapis.com/maps/api/place/autocomplete/"
            // + output + "?" + parameters;
            url = url.replaceAll(" ", "%20");

            UtilityPro.errorLogTrace("google map url", url, true);

            try {
                // Fetching the data from web service in background
                data = downloadUrl(url);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            // Creating ParserTask
            parserTask = new ParserTask();

            // Starting Parsing the JSON string returned by Web Service
            parserTask.execute(result);
        }
    }

    /**
     * A class to parse the Google Places in JSON format
     */
    private class ParserTask extends
            AsyncTask<String, Integer, List<HashMap<String, String>>> {

        JSONObject jObject;

        @Override
        protected List<HashMap<String, String>> doInBackground(
                String... jsonData) {

            List<HashMap<String, String>> places = null;

            PlaceJSONParser placeJsonParser = new PlaceJSONParser();

            try {
                jObject = new JSONObject(jsonData[0]);

                // Getting the parsed data as a List construct
                places = placeJsonParser.parse(jObject);
                UtilityPro.errorLogTrace("google map place",
                        "" + places.size(), true);

            } catch (Exception e) {
                Log.d("Exception", e.toString());
            }
            return places;
        }

        String locationAddress;

        @Override
        protected void onPostExecute(List<HashMap<String, String>> result) {

            String[] from = new String[]{"description"};

            UtilityPro.errorLogTrace("google map place parse description", ""
                    + from.length, true);

            int[] to = new int[]{android.R.id.text1};
            suggestion_list_id = new ArrayList<>();
            // Creating a SimpleAdapter for the AutoCompleteTextView
            try {
                // SimpleAdapter adapter = new SimpleAdapter(getActivity(),
                // result, android.R.layout.simple_list_item_1, from, to);

                ArrayList<String> places = new ArrayList<String>();

                for (int i = 0; i < result.size(); i++) {
                    places.add(result.get(i).get("description"));
                    suggestion_list_id.add(result.get(i).get("_id"));
                }
                GooglePlaceAPIAdapter adapter = new GooglePlaceAPIAdapter(
                        MapResultsActivity.this, places, null);


                System.out.println("===== Map adapter=========" + adapter.getCount());
                // Setting the adapter
                address_auto.setAdapter(adapter);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * A method to download json data from url
     */
    @SuppressLint("LongLogTag")
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    iStream));

            // BufferedReader br = new BufferedReader(
            // new InputStreamReader(iStream, "iso-8859-1"), 8);

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.d("Exception while downloading url", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }


    class GetLatLng extends AsyncTask<String, Void, StringBuilder> {

        ProgressDialog pdlg;

        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected StringBuilder doInBackground(String... strings) {
            try {
                String address = strings[0];
                address = address.replaceAll(" ", "%20");
                HttpURLConnection conn = null;
                StringBuilder jsonResults = new StringBuilder();
                String googleMapUrl = "https://maps.googleapis.com/maps/api/place/details/json?placeid=" + address + "&key=AIzaSyBj-qNsiTxEyFgqCEGL0GKCIRIjGh5MGic";
                Log.d("valkesh", "URL = " + googleMapUrl);
                URL url = new URL(googleMapUrl);
                conn = (HttpURLConnection) url.openConnection();
                InputStreamReader in = new InputStreamReader(
                        conn.getInputStream());
                int read;
                char[] buff = new char[1024];
                while ((read = in.read(buff)) != -1) {
                    jsonResults.append(buff, 0, read);
                }
                String a = "";
                return jsonResults;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;

        }

        protected void onPostExecute(StringBuilder result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            try {
                if (result != null) {
                    JSONObject jsonObj = null;
                    try {
                        jsonObj = new JSONObject(result.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    JSONObject resultJsonObj = jsonObj.getJSONObject("result");

                    // Extract the Place descriptions from the results
                    // resultList = new
                    // ArrayList<String>(resultJsonArray.length());

				/*	JSONObject before_geometry_jsonObj = resultJsonArray
                            .getJSONObject(0);*/

                    JSONObject geometry_jsonObj = resultJsonObj
                            .getJSONObject("geometry");

                    JSONObject location_jsonObj = geometry_jsonObj
                            .getJSONObject("location");

                    String lat_helper = location_jsonObj.getString("lat");
                    double lat = Double.valueOf(lat_helper);

                    String lng_helper = location_jsonObj.getString("lng");
                    double lng = Double.valueOf(lng_helper);

                    System.out.println("=====lat" + lat + "====lng==" + lng);
                    locationAddress = "" + lat + "," + "" + lng;


//                    Marker m = new Marker(locationAddress);
                    LatLng newLatlong = new LatLng(lat, lng);
                    updateMarkerWithCircle(newLatlong);

                }

            } catch (JSONException e) {
                e.printStackTrace();

            }
        }
    }

    String locationAddress;

    public class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {

            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    break;
                default:
                    locationAddress = null;
            }

            if (locationAddress == null) {
                return;
            }
            updateMarkerWithCircle(mMarker.getPosition());

            // address.setText(locationAddress);
        }
    }
}