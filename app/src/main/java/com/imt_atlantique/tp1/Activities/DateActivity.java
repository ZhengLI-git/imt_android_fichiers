package com.imt_atlantique.tp1.Activities;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.imt_atlantique.tp1.R;

import java.util.Calendar;
import java.util.Locale;

public class DateActivity extends AppCompatActivity {
    final Calendar myCalendar = Calendar.getInstance();

    public static final String KEY_RESULT_CODE = "result code";
    private String birthday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);
        Log.i("Lifecycle DateActivity", "onCreate method");
    }

    @Override
    protected void onResume() {
        super.onResume();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
                backToMainActivity();
            }


        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(DateActivity.this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (which == DialogInterface.BUTTON_NEGATIVE) {
                    processCancel();
                }
            }
        });
        datePickerDialog.show();

    }

    private void updateLabel() {
        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        this.birthday = sdf.format(myCalendar.getTime());
    }

    private void backToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(this.KEY_RESULT_CODE, true);
        intent.putExtra(MainActivity.KEY_BIRTHDAY, this.birthday);
        startActivity(intent);
    }

    private void processCancel() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(this.KEY_RESULT_CODE, false);
        startActivity(intent);
    }
}
