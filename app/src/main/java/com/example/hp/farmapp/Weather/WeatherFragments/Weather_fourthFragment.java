package com.example.hp.farmapp.Weather.WeatherFragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hp.farmapp.CalendarPackage.Adapter.RecyclerTouchListener;
import com.example.hp.farmapp.CalendarPackage.CalendarTask.FarmActionReplyActivity;
import com.example.hp.farmapp.CalendarPackage.CalendarTask.GetterSetter.Taskdata;
import com.example.hp.farmapp.CalendarPackage.CalendarTask.ShowTaskViewPagerActivity;
import com.example.hp.farmapp.CalendarPackage.CalendarTask.TaskRecyclerViewAdapter;
import com.example.hp.farmapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link Weather_thirdFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Weather_fourthFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    String dateall,idall,activityall,activitydescriptionall,activity_imgall,is_doneall;

    public static final String ARG_DATE = "ARG_DATE";
    private static final String ARG_ID = "ARG_ID";
    private static final String ARG_ACTIVITY = "ARG_ACTIVITY";
    private static final String ARG_ACTIVITY_DESCRIPTION = "ARG_ACTIVITY_DESCRIPTION";
    private static final String ARG_ACTIVITY_IMG = "ARG_ACTIVITY_IMG";
    private static final String ARG_ISDONE = "ARG_ISDONE";

    private RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;
    TaskRecyclerViewAdapter mAdapter;
    Context context;


    // TODO: Rename and change types of parameters
    private String[] mdate;
    private String[] mid;
    private String[] mactivity;
    private String[] mactivitydescription;
    private String[] mactivityimg;
    private String[] misdone;


    public Weather_fourthFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     *
     * @return A new instance of fragment Wheather_thirdFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Weather_fourthFragment newInstance(String[] mdate,String[] mid,String[] mactivity,String[] mactivitydescription,String[] mactivityimg,String[] mis_done) {
        Weather_fourthFragment fragment = new Weather_fourthFragment();
        Bundle args = new Bundle();
        args.putStringArray(ARG_DATE,mdate);
        args.putStringArray(ARG_ID,mid);
        args.putStringArray(ARG_ACTIVITY,mactivity);
        args.putStringArray(ARG_ACTIVITY_DESCRIPTION,mactivitydescription);
        args.putStringArray(ARG_ACTIVITY_IMG,mactivityimg);
        args.putStringArray(ARG_ISDONE,mis_done);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mdate=getArguments().getStringArray(ARG_DATE);
            mid=getArguments().getStringArray(ARG_ID);
            mactivity=getArguments().getStringArray(ARG_ACTIVITY);
            mactivitydescription=getArguments().getStringArray(ARG_ACTIVITY_DESCRIPTION);
            mactivityimg=getArguments().getStringArray(ARG_ACTIVITY_IMG);
            misdone=getArguments().getStringArray(ARG_ISDONE);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_weather_fourth, container, false);
        final List<Taskdata> taskdatums=new ArrayList<>();
        Taskdata taskdatum=new Taskdata();
        context=getActivity();

        if(mdate!=null) {
            for (int i = 0; i < mdate.length; i++) {
                taskdatum = new Taskdata();
                taskdatum.setTaskDate(mdate[i]);
                taskdatum.setTaskTitle(mactivity[i]);
                taskdatum.setTaskDescription(mactivitydescription[i]);
                taskdatum.setImgBgLink(mactivityimg[i]);
                taskdatum.setIsDone(misdone[i]);
                taskdatum.setTaskId(mid[i]);
                taskdatums.add(taskdatum);
            }
        }
        mRecyclerView = (RecyclerView)view.findViewById(R.id.recycler_view_pending);
        //mprogressDialog.dismiss();
        mAdapter = new TaskRecyclerViewAdapter(taskdatums,context);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        mLayoutManager = new LinearLayoutManager(context);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(context, mRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                //Toast.makeText(getApplicationContext()," "+taskdatums.get(position), Toast.LENGTH_SHORT).show();
                Taskdata taskdata=taskdatums.get(position);
//                                        Toast.makeText(getApplicationContext(),"Description ->"+taskdata.getTaskDescription()+", Id ->"+taskdata.getTaskId(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, FarmActionReplyActivity.class);
                intent.putExtra("id",taskdata.getTaskId());
                intent.putExtra("task_date",taskdata.getTaskDate());
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        // Inflate the layout for this fragment
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
  /*  public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }*/

  /*  @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    *//**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     *//*
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }*/
}
