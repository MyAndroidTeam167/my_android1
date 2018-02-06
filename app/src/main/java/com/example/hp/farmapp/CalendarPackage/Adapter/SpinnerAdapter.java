
/**
 * Created by hp on 03-01-2018.
 */

package com.example.hp.farmapp.CalendarPackage.Adapter;

        import android.content.Context;
        import android.graphics.Color;
        import android.support.annotation.NonNull;
        import android.support.annotation.Nullable;
        import android.util.Log;
        import android.view.Gravity;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.BaseAdapter;
        import android.widget.TextView;

        import com.example.hp.farmapp.CalendarPackage.CalendarTask.GetterSetter.Taskdata;
        import com.example.hp.farmapp.CalendarPackage.NavGetterSetter.SpinnerData;
        import com.example.hp.farmapp.R;

        import java.util.List;

/**
 * Created by user on 2/1/18.
 */

public class SpinnerAdapter extends BaseAdapter{

    Context context;
    List<SpinnerData> SpinnerDatums;
    //    private final int resourceId;
    LayoutInflater flater;

    public SpinnerAdapter(Context applicationContext,List<SpinnerData> SpinnerDatums) {
        this.context = applicationContext;
        this.SpinnerDatums = SpinnerDatums;
        flater = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return SpinnerDatums.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

   /* @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
      //  TextView txt = new TextView(MainActivity.this);
       *//* TextView txt = (TextView) convertView.findViewById(R.id.text11);
        txt.setPadding(16, 16, 16, 16);
        txt.setTextSize(18);
        txt.setGravity(Gravity.CENTER_VERTICAL);
        txt.setTextColor(Color.parseColor("#000000"));
        SpinnerData spinnerData = SpinnerDatums.get(position);
        txt.setText(spinnerData.getItem_name());
        return  txt;*//*

    }*/
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = flater.inflate(R.layout.spinner_item, null);
        TextView names = (TextView) view.findViewById(R.id.text11);
        names.setGravity(Gravity.CENTER);
        names.setPadding(16, 16, 16, 16);
        names.setTextSize(16);
       // names.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.new_drop_down, 0);
        SpinnerData spinnerData = SpinnerDatums.get(i);
        names.setText(spinnerData.getItem_name());
        return view;
    }
}
