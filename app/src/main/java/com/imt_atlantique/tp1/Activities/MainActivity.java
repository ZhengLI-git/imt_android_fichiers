package com.imt_atlantique.tp1.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.imt_atlantique.tp1.InputNumberCheck;
import com.imt_atlantique.tp1.R;
import com.imt_atlantique.tp1.User;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private Button validate;
    private Button addTele;
    private Button gotoNext;
    private EditText firstNameEdit;
    private EditText lastNameEdit;
    private EditText birthdayEdit;
    private EditText countryEdit;
    private EditText teleEdit;
    private Spinner departmentSpinn;

    private GridLayout gridTelephones;
    private Set<String> telephonesSet = new HashSet<String>();

    private SharedPreferences sp;

    private final String spFileName = "main_sp";
    private final String KEY_TELE = "telephones";
    private final String KEY_FIRSTNAME = "firstname";
    private final String KEY_LASTNAME = "lastname";
    private final String KEY_BIRTHDAY = "birthday";
    private final String KEY_COUNTRY = "country";
    private final String KEY_DEPARTMENT = "department";

    public static final String KEY_USER = "user";

    private final int defalutDepartmentID = 0;

    private String firstName;
    private String lastName;
    private String birthday;
    private String country;
    private String department;



    final Calendar myCalendar = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("Lifecycle MainActivity", "onCreate method");
    }
    @Override
    protected void onStart() {
        super.onStart();
        getDelegate().onStart();
        Log.i("Lifecycle MainActivity", "onStart method");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getDelegate().onStart();
        Log.i("Lifecycle MainActivity", "onRestart method");

    }

    @Override
    protected void onResume() {
        super.onResume();
        this.validate = (Button)findViewById(R.id.bt_validate);
        this.firstNameEdit = (EditText) findViewById(R.id.firstname_edit);
        this.lastNameEdit = (EditText) findViewById(R.id.lastname_edit);
        this.birthdayEdit = (EditText) findViewById(R.id.date_edit);
        this.countryEdit = (EditText) findViewById(R.id.country_edit);
        this.teleEdit = (EditText) findViewById(R.id.telephone_edit);
        this.addTele = (Button) findViewById(R.id.bt_add_tele);
        this.gotoNext = (Button) findViewById(R.id.gotoNext);
        this.gridTelephones = (GridLayout) findViewById(R.id.grid_list_telephones);
        this.sp = getSharedPreferences(this.spFileName, MODE_PRIVATE);
        this.departmentSpinn = (Spinner) findViewById(R.id.spinner);
        getTeleNumbersFromSP();
        showInfoFromSF();
        //showSavedInfo();
        if (!this.telephonesSet.isEmpty())
            showSavedTelephones();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
        this.birthdayEdit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(MainActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        this.validate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                validateAction();
            }
        });
        this.addTele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String telephone = teleEdit.getText().toString();
                addTelephone(telephone);
            }
        });
        this.gotoNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });
        Log.i("Lifecycle MainActivity", "onResume method");

    }

    @Override
    protected  void onPause() {
        super.onPause();
        Log.i("Lifecycle MainActivity", "onPause method");
        saveTeleNumbersInSP();
        saveInfoInSF();
    }
    @Override
    protected void onStop() {
        super.onStop();
        //getDelegate().onStop();
        Log.i("Lifecycle MainActivity", "onStop method");
        saveTeleNumbersInSP();

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.i("Lifecycle MainActivity", "onDestroy method");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        Log.i("Lifecycle MainActivity", "onSaveInstanceState method");
    }
    @Override
    public void onRestoreInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        Log.i("Lifecycle MainActivity", "onRestoreInstanceState method");
    }

    private void saveInfoInSF() {
        SharedPreferences.Editor editor = this.sp.edit();
        editor.putString(KEY_FIRSTNAME, this.firstNameEdit.getText().toString());
        editor.putString(KEY_LASTNAME, this.lastNameEdit.getText().toString());
        editor.putString(KEY_COUNTRY, this.countryEdit.getText().toString());
        editor.putString(KEY_BIRTHDAY, this.birthdayEdit.getText().toString());
        editor.putInt(KEY_DEPARTMENT, this.departmentSpinn.getSelectedItemPosition());
        editor.apply();
//        editor.putString(KEY_DEPARTMENT, this.departmentSpinn.getText().toString());
    }

    private void showInfoFromSF() {
        if (this.sp.contains(KEY_FIRSTNAME))
            this.firstNameEdit.setText(this.sp.getString(KEY_FIRSTNAME, ""));
        if (this.sp.contains(KEY_LASTNAME))
            this.lastNameEdit.setText(this.sp.getString(KEY_LASTNAME, ""));
        if (this.sp.contains(KEY_COUNTRY))
            this.countryEdit.setText(this.sp.getString(KEY_COUNTRY, ""));
        if (this.sp.contains(KEY_BIRTHDAY))
            this.birthdayEdit.setText(this.sp.getString(KEY_BIRTHDAY, ""));
        if (this.sp.contains(KEY_DEPARTMENT))
            this.departmentSpinn.setSelection(this.sp.getInt(KEY_DEPARTMENT,1));
    }

//    private void showSavedInfo() {
//        if (this.firstName != null)
//            this.firstNameEdit.setText(this.firstName);
//        if (this.lastName != null)
//            this.lastNameEdit.setText(this.lastName);
//        if (this.country != null)
//            this.countryEdit.setText(this.country);
//        if (this.birthday != null)
//            this.birthdayEdit.setText(this.birthday);
////        if (this.department != null)
////            this.dep.setText(this.firstName);
//    }

    public void validateAction() {
        Log.i("Lifecycle MainActivity", "validateAction");
        String textToShow = this.firstNameEdit.getText().toString()
                +" " +this.lastNameEdit.getText().toString()
                +" "+this.birthdayEdit.getText().toString()
                +" "+this.countryEdit.getText().toString();
        //Toast.makeText(getApplicationContext(), textToShow, Toast.LENGTH_LONG).show();
        if (textToShow.trim().isEmpty())
            textToShow = "Please complete your information";
        Snackbar.make(findViewById(R.id.main_constraint_layout), textToShow, Snackbar.LENGTH_LONG).show();

        User user = new User(this.firstNameEdit.getText().toString(), this.lastNameEdit.getText().toString(), this.departmentSpinn.getSelectedItem().toString());
        Intent intent = new Intent(this, DisplayActivity.class);
        intent.putExtra(KEY_USER, user);
        startActivity(intent);


    }

    private void updateLabel() {
        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        birthdayEdit.setText(sdf.format(myCalendar.getTime()));
    }

    public boolean resetAction(MenuItem item) {
        if (item.getTitle().equals("reset")) {
            this.firstNameEdit.setText("");
            this.lastNameEdit.setText("");
            this.birthdayEdit.setText("");
            this.countryEdit.setText("");
            this.teleEdit.setText("");
            this.gridTelephones.removeAllViews();
            this.departmentSpinn.setSelection(this.defalutDepartmentID);
            SharedPreferences.Editor editor = this.sp.edit();
            editor.remove(KEY_TELE);
            this.telephonesSet.clear();
        }
        return true;
    }

    public void addTelephone(final String telephone) {
        //final String telephone = this.teleEdit.getText().toString();
        if (!InputNumberCheck.checkFormat(telephone)) {
            Snackbar.make(findViewById(R.id.main_constraint_layout), R.string.err_badformat_number, Snackbar.LENGTH_LONG).show();
            return;
        }
        final TextView itemTele = new TextView(this);
        itemTele.setText(telephone);
        itemTele.setTextColor(Color.BLACK);
        telephonesSet.add(telephone);
        final Button delete = new Button(this);
        delete.setText("delete");
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gridTelephones.removeView(itemTele);
                gridTelephones.removeView(delete);
                telephonesSet.remove(telephone);
            }
        });

        gridTelephones.addView(itemTele);
        gridTelephones.addView(delete);
        this.teleEdit.setText("");
    }

    private void showSavedTelephones() {
        this.gridTelephones.removeAllViews();
        if (!this.telephonesSet.isEmpty()) {
            for (String telephone : this.telephonesSet) {
                addTelephone(telephone);
            }
        }
    }



    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {

            // 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）
            View v = getCurrentFocus();

            if (isShouldHideInput(v, ev)) {
                hideSoftInput(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private void saveTeleNumbersInSP() {
        SharedPreferences.Editor editor = this.sp.edit();
        editor.putStringSet(this.KEY_TELE, this.telephonesSet);
        editor.apply();
    }

    public boolean searchDepartmentAction(MenuItem item) {
        if (item.getTitle().equals("search")) {
            String depart = this.departmentSpinn.getSelectedItem().toString();
            Uri uri = Uri.parse("http://fr.wikipedia.org/?search="+depart);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);

            // Verify that the intent will resolve to an activity
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }
        return true;
    }

    public boolean shareDepartmentAction(MenuItem item) {
        Intent intent = new Intent(Intent.ACTION_SEND);
// The intent does not have a URI, so declare the "text/plain" MIME type
        intent.putExtra(Intent.EXTRA_TEXT, this.departmentSpinn.getSelectedItem().toString());
        intent.setType("text/plain");


        //use default app
//        if (intent.resolveActivity(getPackageManager()) != null) {
//            startActivity(intent);
//        }

// Create and start the chooser
        String title = getResources().getText(R.string.chooser_title).toString();
        Intent chooser = Intent.createChooser(intent, title);
       startActivity(chooser);
       return true;

    }


    private void getTeleNumbersFromSP() {
        this.telephonesSet = this.sp.getStringSet(this.KEY_TELE, new HashSet<String>());
    }
    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = { 0, 0 };
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 隐藏软键盘
     */
    private void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}