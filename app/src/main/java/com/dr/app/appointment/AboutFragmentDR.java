

package com.dr.app.appointment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dr.app.MainActivity;
import com.dr.app.R;
import com.dr.app.fragment.SearchFragment;
import com.dr.app.fragment.SuggestedSportFragment;


public class AboutFragmentDR extends Fragment implements View.OnClickListener, OnMapReadyCallback {
    private static final String ARG_ITEM = "item";
    private String item;

    public AboutFragmentDR() {
    }

    public static AboutFragmentDR newInstance(String item) {
        AboutFragmentDR fragment = new AboutFragmentDR();
        Bundle args = new Bundle();
        args.putString(ARG_ITEM, item);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // item = getArguments().getString(ARG_ITEM);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about_dr, container, false);
    }

    private LinearLayout lnActionMail, lnActionCall, lnActionLocation, lnActionChat;
    private ImageView imgShare;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        if (view != null) {

            lnActionMail = (LinearLayout) view.findViewById(R.id.lnActionMail);
            lnActionCall = (LinearLayout) view.findViewById(R.id.lnActionCall);
            lnActionLocation = (LinearLayout) view.findViewById(R.id.lnActionLocation);
            lnActionChat = (LinearLayout) view.findViewById(R.id.lnActionChat);
            imgShare = (ImageView) view.findViewById(R.id.imgShare);

            lnActionMail.setOnClickListener(this);
            lnActionCall.setOnClickListener(this);
            lnActionLocation.setOnClickListener(this);
            lnActionChat.setOnClickListener(this);
            imgShare.setOnClickListener(this);


        }
    }


    @Override
    public void onMapReady(GoogleMap map) {
        LatLng mapCenter = new LatLng(44.362938, -79.623731);

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(mapCenter, 13));

        // Flat markers will rotate when the map is rotated,
        // and change perspective when the map is tilted.
        map.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.add))
                .position(mapCenter)
                .flat(true)
                .rotation(245));

        CameraPosition cameraPosition = CameraPosition.builder()
                .target(mapCenter)
                .zoom(13)
                .bearing(90)
                .build();

        // Animate the change in camera view over 2 seconds
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition),
                2000, null);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.imgSearch:
                ((MainActivity) getActivity()).showFragment(SearchFragment.newInstance(""));
                break;
            case R.id.imgLogo:
                ((MainActivity) getActivity()).showFragment(SuggestedSportFragment.newInstance(""));
                break;
            case R.id.lnActionMail:
                ActionEmail();
                break;
            case R.id.lnActionCall:
                ActionCall();
                break;
            case R.id.lnActionLocation:
                ActionLocation();
                break;
            case R.id.lnActionChat:
                startActivity(ActionFacebook(getActivity()));
                break;
            case R.id.imgShare:
                ActionShare();
                break;
        }
    }

    private void ActionShare() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, "Share Via"));
    }

    private void ActionChat() {

    }

    private void ActionCall() {
        try
        {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:705 503-7724"));
            callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(callIntent);
        }
        catch (ActivityNotFoundException ex)
        {
           // Toast.makeText(getContext(),getResources().getString(R.string.toast_activity_not_found), Toast.LENGTH_SHORT).show();
        }
    }

    private void ActionEmail() {

        String to = "valkeshpatel123@gmail.com";
        String subject = "Connect with mail";
        String message = "Need help";


        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{to});
        email.putExtra(Intent.EXTRA_SUBJECT, subject);
        email.putExtra(Intent.EXTRA_TEXT, message);

        //need this to prompts email client only
        email.setType("message/rfc822");
        startActivity(Intent.createChooser(email, "Choose an Email client :"));

    }

    private void ActionLocation() {

        Uri gmmIntentUri = Uri.parse("geo:44.362938,-79.623731");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(mapIntent);
        }

    }

    public static Intent ActionFacebook (Activity context) {
        Log.i("valkesh", "Clicked");
        try {
            Log.i("valkesh", "Clicked 1");
            context.getPackageManager().getPackageInfo("com.facebook.katana", 0);
            return new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/rpahbarrie"));
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/rpahbarrie"));
        }
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        mMapView.onResume();
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        mMapView.onPause();
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        mMapView.onDestroy();
//    }
//
//    @Override
//    public void onLowMemory() {
//        super.onLowMemory();
//        mMapView.onLowMemory();
//    }


}