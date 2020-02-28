package com.imt_atlantique.tp1.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.imt_atlantique.tp1.R;

public class EditFirstNameActivity extends AppCompatActivity {
    private TextView firstnameView;
    private Button edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Lifecycle of EditFirstNameActivity", "onCreate method");
        setContentView(R.layout.activity_edit_firstname);

    }

    @Override
    protected void onResume() {
        super.onResume();
        this.firstnameView = (TextView)findViewById(R.id.edit_firstnm_editActivity);
        this.edit = (Button)findViewById(R.id.BT_editactivity);
        setDefaultFirstNM();
        this.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendNmToMainActivity();
            }
        });
    }

    private void setDefaultFirstNM() {
        Intent intent = getIntent();
        if (intent.hasExtra(MainActivity.KEY_FIRSTNAME))
            this.firstnameView.setText(intent.getStringExtra(MainActivity.KEY_FIRSTNAME));
    }

    private void sendNmToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(MainActivity.KEY_FIRSTNAME,this.firstnameView.getText().toString());
        startActivity(intent);
    }
}
