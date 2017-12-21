package com.dr.app;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.android.volley.Request;


import com.dr.app.utility.Constants;
import com.dr.app.utility.Golly;
import com.dr.app.utility.Pref;
import com.dr.app.utility.Utility;
import com.dr.app.utility.UtilityPro;
import com.dr.app.widgets.CButtonViewOpenSans;
import com.dr.app.widgets.CEditTextViewjustsport;
import com.dr.app.widgets.CTextViewjustsport;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;

import android.widget.ImageView;
import android.widget.LinearLayout;

public class RegistrationProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private CEditTextViewjustsport edtUsername, edtBio;
    private ImageView imgProfile;
    private CTextViewjustsport btnFemale, btnMale;
    private CButtonViewOpenSans btnCreateAccount;
    String isFemale = "-1";
    private ImageView imgFemale, imgMale;
    private LinearLayout lnFemale, lnMale;

    String camera_image_path;
    boolean is_camera = false;
    Uri imagePath = null;
    public HashMap<Integer, File> files;
    ImageLoader imageLoader = null;
    DisplayImageOptions options;
    ImageView btnBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.sign_up_bg, getBaseContext().getTheme()));
        } else {
            getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.sign_up_bg));
        }
        setContentView(R.layout.activity_registration_profile);
        files = new HashMap<Integer, File>();
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(RegistrationProfileActivity.this));
      /*  options = new DisplayImageOptions.Builder()
                .displayer(new RoundedBitmapDisplayer(50))
                .showImageOnLoading(R.drawable.profile_image)
                .showImageForEmptyUri(R.drawable.profile_image)
                .showImageOnFail(R.drawable.profile_image).cacheInMemory(true)
                .cacheOnDisk(true).considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565).build();*/


        edtUsername = (CEditTextViewjustsport) findViewById(R.id.edtUsername);
        edtBio = (CEditTextViewjustsport) findViewById(R.id.edtBio);
        imgProfile = (ImageView) findViewById(R.id.imgProfile);
        btnFemale = (CTextViewjustsport) findViewById(R.id.btnFemale);
        btnMale = (CTextViewjustsport) findViewById(R.id.btnMale);
        lnFemale = (LinearLayout) findViewById(R.id.lnFemale);
        lnMale = (LinearLayout) findViewById(R.id.lnMale);

        imgFemale = (ImageView) findViewById(R.id.imgFemale);
        imgMale = (ImageView) findViewById(R.id.imgMale);

        btnCreateAccount = (CButtonViewOpenSans) findViewById(R.id.btnCreateAccount);
        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnCreateAccount.setOnClickListener(this);
        lnFemale.setOnClickListener(this);
        lnMale.setOnClickListener(this);
        btnBack.setOnClickListener(this);


        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(RegistrationProfileActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        imgProfile.setEnabled(false);
                        ActivityCompat.requestPermissions(RegistrationProfileActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                    } else {
                        //  takePicture();
                        selectImage();
                    }
                } else {
                    selectImage();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnCreateAccount) {
            if (UtilityPro.isNetworkAvaliable(RegistrationProfileActivity.this)) {
                if (validationField()) {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("user_id", Pref.getUserID());
                    params.put("user_name", edtUsername.getText().toString());
                    params.put("user_gender", isFemale);
                    params.put("user_bio", edtBio.getText().toString());
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
                    }
                    callwebservice(params, files);
//                    logInWebCall(params);
                }
            } else {
                UtilityPro.toast(RegistrationProfileActivity.this.getString(R.string.please_check_your_internet_connection));
            }
        } else if (view.getId() == R.id.lnFemale) {
            isFemale = genderField(true);
        } else if (view.getId() == R.id.lnMale) {
            isFemale = genderField(false);
        } else if (view.getId() == R.id.btnBack) {
            Intent reg_intent = new Intent(RegistrationProfileActivity.this, RegistrationConfirmActivity.class);
            startActivity(reg_intent);
            finish();
        }
    }


    private boolean validationField() {
        if (edtUsername.getText().toString().equalsIgnoreCase("") || edtUsername.getText().toString().length() < 0) {
            UtilityPro.toast("Please enter your name.");
            return false;
        }
        if (isFemale.equalsIgnoreCase("-1")) {
            UtilityPro.toast("Please select your gender.");
            return false;
        }
        if (edtBio.getText().toString().equalsIgnoreCase("") || edtBio.getText().toString().length() < 0) {
            UtilityPro.toast("Please enter your bio.");
            return false;
        }

        return true;
    }

    private String genderField(boolean ismaleField) {
        if (ismaleField) {
            btnFemale.setTextColor(this.getResources().getColor(R.color.colorWhite));
            btnMale.setTextColor(this.getResources().getColor(R.color.radioButton));
            imgFemale.setImageResource(R.drawable.ic_reg_female_on);
            imgMale.setImageResource(R.drawable.ic_reg_male_off);
            // btnMale.setBackgroundResource(R.drawable.ic_reg_male_off);
            isFemale = "1";

        } else {

            btnFemale.setTextColor(this.getResources().getColor(R.color.radioButton));
            btnMale.setTextColor(this.getResources().getColor(R.color.colorWhite));
            imgFemale.setImageResource(R.drawable.ic_reg_female_off);
            imgMale.setImageResource(R.drawable.ic_reg_male_on);
            isFemale = "0";
        }
        return isFemale;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                imgProfile.setEnabled(true);

            }
        }
    }


    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(RegistrationProfileActivity.this);
        builder.setTitle("Profile Image");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    is_camera = true;
                    camera_image_path = Utility.getOutputFilePath("");
                    UtilityPro.selectImageFromCameranew(RegistrationProfileActivity.this,
                            camera_image_path, false);

                } else if (items[item].equals("Choose from Library")) {
                    is_camera = false;
                    UtilityPro.selectImageFromGalary(RegistrationProfileActivity.this,
                            "profile_galary_image.jpg", false);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri s = UtilityPro.handleImagePickerActivityResult(RegistrationProfileActivity.this,
                requestCode, resultCode, data);
        if (s != null) {
            imagePath = s;
            String image_path = imagePath.toString();
            imgProfile.setImageURI(s);

            /*if (image_path == null) {
            } else {
                imageLoader.displayImage(image_path,
                        imgProfile, Golly.options_profile, new SimpleImageLoadingListener() {
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
            }*/
        }
    }

    // Edit or registration process
    private void callwebservice(Map<String, String> params, HashMap<Integer, File> files) {
        Golly.showDarkProgressDialog(RegistrationProfileActivity.this);
        Golly.multipartStringRequest("user_image", Constants.BASE_URL + Constants.UPDATE_PROFILE,
                params, files, Request.Method.POST,
                "profile post", new Golly.GollyListner() {
                    @Override
                    public void successResponce(String response, String TAG,
                                                Boolean FROM_CAHCE) {
                        try {
                            UtilityPro.log(" save profile data : responce =  "
                                    + response);
                            JSONObject responces = new JSONObject(response);
                            if (responces.getBoolean("status")) {
                                JSONObject json2 = responces.getJSONObject("results");
                                Pref.setIsLogin(true);
                                Pref.setUserName(json2.getString("user_name"));
                                Pref.setUserImage(json2.getString("user_image"));


                                Intent reg_intent = new Intent(RegistrationProfileActivity.this, MainActivity.class);
//                                Intent reg_intent = new Intent(RegistrationProfileActivity.this ,DashboardActivity.class);
                                startActivity(reg_intent);
                                finish();

//                                responces = responces.getJSONObject("result");
//                                Pref.setUserID(responces.getString("token"));

                                // use for chat
//                                Pref.setChatUserId(responces
//                                        .getString("chat user_name"));
//                                Pref.setChatUserPassword(responces
//                                        .getString("chat password"));
//
//                                Intent serviceIntent = new Intent(
//                                        getBaseContext(), XMPPService.class);

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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent reg_intent = new Intent(RegistrationProfileActivity.this, RegistrationConfirmActivity.class);
            startActivity(reg_intent);
            finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

}
