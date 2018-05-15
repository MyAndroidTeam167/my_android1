package com.example.hp.farmapp.Company;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hp.farmapp.Login.MainActivity;
import com.example.hp.farmapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CompanyActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.comp_id)
    EditText comp_num;
    @BindView(R.id.submit_comp_id)
    Button submit_comp_id;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);
        context=this;
        ButterKnife.bind(this);
        init();

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        String value=pref.getString("key_comp_name",null);
        if(value!=null) {
            comp_num.setText(value);
        }

    }

    private void Set_id_for_future() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("key_comp_name", comp_num.getText().toString().trim()); // Storing string
        editor.commit();
    }

    private void init() {
        submit_comp_id.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.submit_comp_id:{
                Set_id_for_future();
                Intent intent =new Intent(context, MainActivity.class);
                startActivity(intent);
                finish();
                break;
            }
        }

    }
}
