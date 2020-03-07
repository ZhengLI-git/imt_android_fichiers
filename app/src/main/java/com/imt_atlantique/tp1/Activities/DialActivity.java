package com.imt_atlantique.tp1.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.imt_atlantique.tp1.R;

public class DialActivity extends AppCompatActivity {

    private TextView tele;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dial);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.tele = (TextView)findViewById(R.id.dialing_number);
        Intent intent = getIntent();
        if (intent.hasExtra(InputInfoFragment.KEY_TELE))
            this.tele.setText(intent.getStringExtra(InputInfoFragment.KEY_TELE));
    }

}
