

package com.dr.app.fragment;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.dr.app.LoginActivity;
import com.dr.app.R;
import com.dr.app.utility.Constants;
import com.dr.app.utility.Golly;
import com.dr.app.utility.Pref;
import com.dr.app.utility.UtilityPro;
import com.dr.app.widgets.CButtonViewOpenSans;
import com.dr.app.widgets.CEditTextViewjustsport;
import com.dr.app.widgets.CTextViewjustsport;
import com.dr.app.widgets.CircleImageView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class IsNotLoginProfileFragment extends Fragment implements View.OnClickListener  {
  private static final String ARG_ITEM = "item";

  private String item;
  Button btnSignup;

  public HashMap<Integer, File> files;
  public IsNotLoginProfileFragment() {}
  public static IsNotLoginProfileFragment newInstance(String item) {
    IsNotLoginProfileFragment fragment = new IsNotLoginProfileFragment();
    Bundle args = new Bundle();
    args.putString(ARG_ITEM, item);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    item = getArguments().getString(ARG_ITEM);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_not_login_profile, container, false);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    btnSignup = (Button) view.findViewById(R.id.btnSignup);
    btnSignup.setOnClickListener(this);
  }

  @Override
  public void onClick(View view) {
    if (view.getId() == R.id.btnSignup) {
      Intent login_intent = new Intent(getActivity(), LoginActivity.class);
      startActivity(login_intent);
      getActivity().finish();
    }
  }
}