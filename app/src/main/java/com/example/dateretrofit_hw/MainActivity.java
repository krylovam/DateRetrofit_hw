package com.example.dateretrofit_hw;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void onclick(View v) {
        RadioButton getDateBtn = (RadioButton) findViewById(R.id.rbDate);
        RadioButton getIpBtn = (RadioButton) findViewById(R.id.rbIp);
        String type = "";

        if (getIpBtn.isChecked())
        {
            type = "ip";
        }
        else if (getDateBtn.isChecked()){
            type = "date";
        }
        else {
            Toast.makeText(MainActivity.this, "Please choose the type of request", Toast.LENGTH_SHORT).show();
        }

        if (!type.equals("")) {
            final String finalType = type;
            App.getApi().getInfo(type).enqueue(new Callback<InfoModel>() {
                @Override
                public void onResponse(Call<InfoModel> call, Response<InfoModel> response) {
                    TextView textView = (TextView) findViewById(R.id.tvResult);
                    InfoModel info = response.body();
                    if (finalType == "ip") {
                        textView.setText("IP: " + info.getIp());
                    }
                    else {
                        textView.setText("Date: " + info.getDate() + "\nMilliseconds: " + info.getMilliSeconds() + "\nTime: " + info.getTime());
                    }
                }

                @Override
                public void onFailure(Call<InfoModel> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Something bad happened!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}
