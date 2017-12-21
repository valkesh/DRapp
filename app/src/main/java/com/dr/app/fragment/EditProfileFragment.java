

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.dr.app.R;
import com.dr.app.utility.Constants;
import com.dr.app.utility.Golly;
import com.dr.app.utility.Pref;
import com.dr.app.utility.Utility;
import com.dr.app.utility.UtilityPro;
import com.dr.app.widgets.CButtonViewOpenSans;
import com.dr.app.widgets.CEditTextViewjustsport;
import com.dr.app.widgets.CTextViewjustsport;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class EditProfileFragment extends Fragment implements View.OnClickListener {
    private static final String ARG_ITEM = "item";

    private String item;
    private CEditTextViewjustsport edtUsername, edtBio;
    private ImageView imgProfile;
    private TextView btnFemale, btnMale;
    private CButtonViewOpenSans btnCreateAccount;
    String isFemale = "-1";
    private ImageView imgFemale, imgMale, btnDone;
    private LinearLayout lnFemale, lnMale;

    String camera_image_path;
    boolean is_camera = false;
    Uri imagePath = null;
    public HashMap<Integer, File> files;
    ImageView btnBack;


    public EditProfileFragment() {
    }

    public static EditProfileFragment newInstance(String item) {
        EditProfileFragment fragment = new EditProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ITEM, item);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        item = getArguments().getString(ARG_ITEM);
        files = new HashMap<Integer, File>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        edtUsername = (CEditTextViewjustsport) view.findViewById(R.id.edtUsername);
        edtBio = (CEditTextViewjustsport) view.findViewById(R.id.edtBio);
        imgProfile = (ImageView) view.findViewById(R.id.imgProfile);
        btnFemale = (TextView) view.findViewById(R.id.btnFemale);
        btnMale = (CTextViewjustsport) view.findViewById(R.id.btnMale);
        lnFemale = (LinearLayout) view.findViewById(R.id.lnFemale);
        lnMale = (LinearLayout) view.findViewById(R.id.lnMale);
        btnDone = (ImageView) view.findViewById(R.id.btnDone);

        imgFemale = (ImageView) view.findViewById(R.id.imgFemale);
        imgMale = (ImageView) view.findViewById(R.id.imgMale);

        btnBack = (ImageView) view.findViewById(R.id.btnBack);
        lnFemale.setOnClickListener(this);
        lnMale.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnDone.setOnClickListener(this);

        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  selectImage();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        imgProfile.setEnabled(false);
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                    } else {
                        //  takePicture();
                        selectImage();
                    }
                } else {
                    selectImage();
                }
            }
        });

        Map<String, String> params = new HashMap<String, String>();
        params.put("user_id", Pref.getUserID());
        getProfileDetails(params);

    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnDone) {
            if (UtilityPro.isNetworkAvaliable(getActivity())) {
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
                UtilityPro.toast(getActivity().getString(R.string.please_check_your_internet_connection));
            }
        } else if (view.getId() == R.id.lnFemale) {
            isFemale = genderField(true);
        } else if (view.getId() == R.id.lnMale) {
            isFemale = genderField(false);
        } else if (view.getId() == R.id.btnBack) {
            getActivity().onBackPressed();
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

            btnFemale.setTextColor(this.getResources().getColor(R.color.color_black));
            btnMale.setTextColor(this.getResources().getColor(R.color.radioButton));
            imgFemale.setImageResource(R.drawable.ic_edit_female_on);
            imgMale.setImageResource(R.drawable.ic_edit_male_off);
            // btnMale.setBackgroundResource(R.drawable.ic_reg_male_off);
            isFemale = "1";

        } else {

            btnFemale.setTextColor(this.getResources().getColor(R.color.radioButton));
            btnMale.setTextColor(this.getResources().getColor(R.color.color_black));
            imgFemale.setImageResource(R.drawable.ic_edit_female_off);
            imgMale.setImageResource(R.drawable.ic_edit_male_on);
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
    private void callwebservice(Map<String, String> params, HashMap<Integer, File> files) {
        Golly.showDarkProgressDialog(getActivity());
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
                                Pref.setUserImage(json2.getString("user_image"));
                                Pref.setUserName(json2.getString("user_name"));
                                UtilityPro.toast(UtilityPro.getDataFromJson(responces, "message"));
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri s = UtilityPro.handleImagePickerActivityResult(getActivity(),
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

    // Facebook Login
    public void getProfileDetails(Map<String, String> params) {
        String Url = Constants.GET_PROFILE;
        Golly.showDarkProgressDialog(getActivity());
        Golly.FireDarkWebCall(Url, params, Request.Method.POST, "API_CALL GET PROFILE",
                new Golly.GollyListner() {
                    @Override
                    public void successResponce(String response, String TAG,
                                                Boolean FROM_CAHCE) {
                        try {
                            try {
                                JSONObject responce_obj = new JSONObject(response);
                                if (responce_obj.getBoolean("status")) {
                                    JSONObject json2 = responce_obj.getJSONObject("results");
//                                    if(UtilityPro.getDataFromJsonBoolean(json2,"is_update") == false){
                                    //Pref.setUserID(json2.getString("user_id"));
                                    edtUsername.setText(json2.getString("user_name"));
                                    edtBio.setText(json2.getString("user_bio"));

                                    if (json2.getInt("user_gender") == 1) {
                                        isFemale = genderField(true);
                                    } else {
                                        isFemale = genderField(false);
                                    }
                                    Pref.setUserImage(json2.getString("user_image"));
                                    Pref.setUserName(json2.getString("user_name"));
                                    Golly.imageLoader.displayImage(json2.getString("user_image"),
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