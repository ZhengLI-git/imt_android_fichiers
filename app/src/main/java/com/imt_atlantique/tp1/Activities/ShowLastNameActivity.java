package com.imt_atlantique.tp1.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.imt_atlantique.tp1.R;

public class ShowLastNameActivity extends AppCompatActivity {

    private TextView lastnameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Lifecycle of ShowLastNameActivity", "onCreate method");
        setContentView(R.layout.activity_show);

    }

    @Override
    protected void onResume() {
        super.onResume();
        this.lastnameView = (TextView) findViewById(R.id.show_lastname);
        setLastname();
    }

    private void setLastname() {
        Intent intent = getIntent();
        if (intent.hasExtra(MainActivity.KEY_LASTNAME))
            this.lastnameView.setText(intent.getStringExtra(MainActivity.KEY_LASTNAME));
    }
}
