package com.imt_atlantique.tp1.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.imt_atlantique.tp1.R;
import com.imt_atlantique.tp1.User;

public class DisplayActivity extends AppCompatActivity {
    private TextView firstNameEdit;
    private TextView lastNameEdit;
    private TextView departmentEdit;

    private String firstName;
    private String lastName;
    private String department;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Lifecycle of DisplayActivity", "onCreate method");
        setContentView(R.layout.activity_display);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.firstNameEdit = (TextView)findViewById(R.id.edit_firstname_disp);
        this.lastNameEdit = (TextView)findViewById(R.id.edit_lastname_disp);
        this.departmentEdit = (TextView)findViewById(R.id.edit_department_disp);
        getValue();
        showValue();
    }

    private void getValue() {
        Intent intent = getIntent();
        User user = (User)intent.getParcelableExtra(InputInfoFragment.KEY_USER);
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.department = user.getDepartment();
    }

    private void showValue() {
        this.firstNameEdit.setText(this.firstName);
        this.lastNameEdit.setText(this.lastName);
        this.departmentEdit.setText(this.department);
    }
}
