

package com.dr.app.appointment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.dr.app.R;
import com.dr.app.model.DoctorTimeModel;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeDemoFragmentDR extends Fragment implements View.OnClickListener, BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {
    private static final String ARG_ITEM = "item";

    private String item;
    private ListView listDoctorTime;
    private DoctorTimeAdapter doctorTimeAdapter;

    ArrayList<DoctorTimeModel> listDoctorTimeModel = new ArrayList<DoctorTimeModel>();


    //  String id_layout_one,id_layout_two, id_layout_three, id_layout_four, id_layout_five, id_layout_six = "0";
    public HomeDemoFragmentDR() {
    }

    public static HomeDemoFragmentDR newInstance(String item) {
        HomeDemoFragmentDR fragment = new HomeDemoFragmentDR();
        Bundle args = new Bundle();
        args.putString(ARG_ITEM, item);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // item = getArguments().getString(ARG_ITEM);

        listDoctorTimeModel.add(new DoctorTimeModel("Monday", "8:00am", "7:00pm", "to"));
        listDoctorTimeModel.add(new DoctorTimeModel("Tuesday", "8:00am", "7:00pm", "to"));
        listDoctorTimeModel.add(new DoctorTimeModel("Wednesday", "8:00am", "7:00pm", "to"));
        listDoctorTimeModel.add(new DoctorTimeModel("Thursday", "8:00am", "7:00pm", "to"));
        listDoctorTimeModel.add(new DoctorTimeModel("Friday", "8:00am", "7:00pm", "to"));
        listDoctorTimeModel.add(new DoctorTimeModel("Saturday", "9:00am", "2:00pm", "to"));
        listDoctorTimeModel.add(new DoctorTimeModel("Sunday", "Closed", "Closed", ""));


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_demo_homedr, container, false);
    }

    SliderLayout mDemoSlider;
    NestedScrollView ntScroll;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        if (view != null) {
            listDoctorTime = (ListView) view.findViewById(R.id.listDoctorTime);
            doctorTimeAdapter = new DoctorTimeAdapter(getActivity(), listDoctorTimeModel);
            listDoctorTime.setAdapter(doctorTimeAdapter);
            doctorTimeAdapter.notifyDataSetChanged();
//            Map<String, String> paramss = new HashMap<String, String>();
//            paramss.put("user_id", Pref.getUserID());
//            paramss.put("time_zone", UtilityPro.getTimezone());


            mDemoSlider = (SliderLayout) view.findViewById(R.id.slider);
            ntScroll = (NestedScrollView) view.findViewById(R.id.ntScroll);

//            HashMap<String,String> url_maps = new HashMap<String, String>();
//            url_maps.put("Hannibal", "http://static2.hypable.com/wp-content/uploads/2013/12/hannibal-season-2-release-date.jpg");
//            url_maps.put("Big Bang Theory", "http://tvfiles.alphacoders.com/100/hdclearart-10.png");
//            url_maps.put("House of Cards", "http://cdn3.nflximg.net/images/3093/2043093.jpg");
//            url_maps.put("Game of Thrones", "http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg");

            HashMap<String, Integer> file_maps = new HashMap<String, Integer>();
            file_maps.put("Vaccinations", R.drawable.slide1);
            file_maps.put("Spay", R.drawable.slide2);
            file_maps.put("Neuter", R.drawable.slide3);
//            file_maps.put("Game of Thrones", R.drawable.game_of_thrones);

            for (String name : file_maps.keySet()) {
                TextSliderView textSliderView = new TextSliderView(getActivity());
                // initialize a SliderLayout
                textSliderView
                        .description(name)
                        .image(file_maps.get(name))
                        .setScaleType(BaseSliderView.ScaleType.Fit)
                        .setOnSliderClickListener(this);

                //add your extra information
                textSliderView.bundle(new Bundle());
                textSliderView.getBundle()
                        .putString("extra", name);

                mDemoSlider.addSlider(textSliderView);
            }
            mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Fade);
            mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
            mDemoSlider.setCustomAnimation(new DescriptionAnimation());
            mDemoSlider.setDuration(4000);
            mDemoSlider.addOnPageChangeListener(this);
//            getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    ntScroll.scrollTo(0,0);
//                }
//            });


        }
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                ntScroll.scrollTo(0, 0);

            }
        }, 200);


    }


//    @Override
//    public void onDetailsListener(ArrayList<SportEventModel> mList, int pos) {
//        ((MainActivity) getActivity()).showFragment(EventMoreDetailsFragment.newInstance(String.valueOf(mList.get(pos).getId())));
//    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.imgSearch:
                break;
            case R.id.imgLogo:
                break;
        }
    }

    @Override
    public void onStop() {

        mDemoSlider.stopAutoCycle();

        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}