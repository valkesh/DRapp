

package com.dr.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.dr.app.R;

public class ItemFragment extends Fragment {
  private static final String ARG_ITEM = "item";

  private String item;

  public ItemFragment() {}

  public static ItemFragment newInstance(String item) {
    ItemFragment fragment = new ItemFragment();
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
    return inflater.inflate(R.layout.fragment_item, container, false);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//    setUpActionBar();

    TextView textView = (TextView) view.findViewById(R.id.text);
    textView.setText(item);

    textView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
      }
    });
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        getActivity().onBackPressed();
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @SuppressWarnings("ConstantConditions")
  private void setUpActionBar() {
//    ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
//    actionBar.setDisplayHomeAsUpEnabled(true);
//    actionBar.setTitle(item);
//    setHasOptionsMenu(true);
  }
}