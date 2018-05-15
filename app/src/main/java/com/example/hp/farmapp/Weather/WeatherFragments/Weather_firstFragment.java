package com.example.hp.farmapp.Weather.WeatherFragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hp.farmapp.R;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link Weather_firstFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Weather_firstFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String ARG_CITYSTATECOUNTRY = "ARG_CITYSTATECOUNTRY";
    public static final String ARG_OBSERVATION_TIME = "ARG_OBSERVATION_TIME";
    public static final String ARG_DAY = "ARG_DAY";
    public static final String ARG_ICON = "ARG_ICON";
    public static final String ARG_WIND_STRING = "ARG_WIND_STRING";
    public static final String ARG_RELATIVEHUMIDITY = "ARG_RELATIVEHUMIDITY";
    public static final String ARG_CONDITION = "ARG_CONDITION";
    private static final String ARG_TEMP_HIGH = "ARG_TEMP_HIGH";
    private static final String ARG_TEMP_LOW = "ARG_TEMP_LOW";
    public static final String ARG_TEMP_TODAY_IN_CEL = "ARG_TEMP_TODAY_IN_CEL";
    public static final String ARG_PAGE = "ARG_PAGE";
    public static final String ARG_DAY_NUM = "ARG_DAY_NUM";
    public static final String ARG_MONTH = "ARG_MONTH";
    public static final String ARG_YEAR = "ARG_YEAR";


    // TODO: Rename and change types of parameters
    private String mcitystatecount;
    private String mobservation_time;
    private String mday;
    private String micon;
    private String mwind_string;
    private String mrel_humidity;
    private String mcondition;
    private String mtmphigh;
    private String mtmplow;
    private String mtmp_today;
    private String mday_num;
    private String mmonth;
    private String myear;

    private int mPage;





    // private OnFragmentInteractionListener mListener;

    public Weather_firstFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param  //Parameter 1.
     * @return A new instance of fragment Wheather_firstFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Weather_firstFragment newInstance(String mtmphigh, String mtmplow, String mday, String mcondition, String micon, String mwind_string, String mtmp_today, String mrel_humidity, String mcitystatecount, String mobservation_time, int mPage, String mday_num, String mmonth, String myear) {
        Weather_firstFragment fragment = new Weather_firstFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CITYSTATECOUNTRY, mcitystatecount);
        args.putString(ARG_OBSERVATION_TIME, mobservation_time);
        args.putString(ARG_DAY, mday);
        args.putString(ARG_ICON, micon);
        args.putString(ARG_CONDITION, mcondition);
        args.putString(ARG_WIND_STRING, mwind_string);
        args.putString(ARG_RELATIVEHUMIDITY, mrel_humidity);
        args.putString(ARG_TEMP_HIGH, mtmphigh);
        args.putString(ARG_TEMP_LOW, mtmplow);
        args.putString(ARG_TEMP_TODAY_IN_CEL, mtmp_today);
        args.putString(ARG_DAY_NUM, mday_num);
        args.putString(ARG_MONTH, mmonth);
        args.putString(ARG_YEAR, myear);

        args.putInt(ARG_PAGE, mPage);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mtmphigh = getArguments().getString(ARG_TEMP_HIGH);
            mtmplow = getArguments().getString(ARG_TEMP_LOW);
            mday = getArguments().getString(ARG_DAY);
            mcondition = getArguments().getString(ARG_CONDITION);
            micon = getArguments().getString(ARG_ICON);
            mwind_string = getArguments().getString(ARG_WIND_STRING);
            mtmp_today = getArguments().getString(ARG_TEMP_TODAY_IN_CEL);
            mrel_humidity = getArguments().getString(ARG_RELATIVEHUMIDITY);
            mobservation_time=getArguments().getString(ARG_OBSERVATION_TIME);
            mcitystatecount = getArguments().getString(ARG_CITYSTATECOUNTRY);
            mday_num=getArguments().getString(ARG_DAY_NUM);
            mmonth=getArguments().getString(ARG_MONTH);
            myear=getArguments().getString(ARG_YEAR);
            mPage = getArguments().getInt(ARG_PAGE);

        }
    }
/*
    public static Wheather_firstFragment newInstance(String text){
        Wheather_firstFragment f = new Wheather_firstFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);
        f.setArguments(b);

        return f;
    }*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this
        View view=inflater.inflate(R.layout.fragment_weather_first, container, false);

        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        ImageView imageView=(ImageView)view.findViewById(R.id.iconforwheather);
        TextView location=(TextView)view.findViewById(R.id.location);
        TextView last_updated=(TextView)view.findViewById(R.id.last_updated);
        TextView day=(TextView)view.findViewById(R.id.tv_day);
        TextView condition=(TextView)view.findViewById(R.id.tvcondition);
        TextView rel_hum=(TextView)view.findViewById(R.id.tvrel_humidity);
        TextView low_temp=(TextView)view.findViewById(R.id.temp_low);
        TextView high_temp=(TextView)view.findViewById(R.id.temp_high);
        TextView wind_string=(TextView)view.findViewById(R.id.tv_wind_string);

        tvTitle.setTextSize(50);
        tvTitle.setText(mtmp_today+"\u00B0"+"C");
        location.setText(mcitystatecount);
        last_updated.setText(mobservation_time);
        day.setText(mday+",  "+mday_num+"/"+mmonth+"/"+myear);
        condition.setText(mcondition);
        rel_hum.setText(mrel_humidity);
        low_temp.setText(mtmplow+"°C");
        high_temp.setText(mtmphigh+"°C");
        Uri uri = Uri.parse(micon);
        wind_string.setText(mwind_string);

        Picasso.with(imageView.getContext()).load(uri).into(imageView);


        return   view;  }

    // TODO: Rename method, update argument and hook method into UI event
/*
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
*/

   /* @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/
/*

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
*/



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
   /* public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }*/
}
