package com.imt_atlantique.tp1.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.imt_atlantique.tp1.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class SecondActivity extends AppCompatActivity {
    private int times;
    private final int defalutTimes = 0;
    private TextView timesView;
    static String KEY_TIMES = "times";
    static String TIMES_FILE = "times";

    private SharedPreferences sp ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Lifecycle of SecondActivity", "onCreate method");
        setContentView(R.layout.activity_second);
        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_TIMES))
        this.times = savedInstanceState.getInt(KEY_TIMES);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("Lifecycle of SecondActivity", "onResume method");
        sp = getSharedPreferences(TIMES_FILE, MODE_PRIVATE);
        this.timesView = (TextView) findViewById(R.id.times);
        if (this.times != 0) {
            this.timesView.setText("" + this.times);
            return;
        }
       getTimesInSF();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("Lifecycle of SecondActivity", "onPause method");
        saveTimesInSF();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("Lifecycle of SecondActivity", "onDestroy method");
        saveTimesInSF();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("Lifecycle of SecondActivity", "onRestart method");
        getTimesInSF();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("Lifecycle of SecondActivity", "onDestroy method");
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        Log.i("Lifecycle of SecondActivity", "onSaveInstanceState method");
        outState.putInt(KEY_TIMES, this.times);

    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i("Lifecycle of SecondActivity", "onRestoreInstanceState method");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_second, menu);
        return true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            this.times ++;
            this.timesView.setText(""+this.times);
        }
        return super.dispatchTouchEvent(ev);
    }


    public boolean resetAction(MenuItem item){
        this.times = this.defalutTimes;
        this.timesView.setText(""+times);
        saveTimesInSF();
        return true;
    }

    private void saveTimesInSF() {
        SharedPreferences.Editor editor = this.sp.edit();
        editor.putInt(KEY_TIMES, this.times);
        editor.apply();
    }
    private void saveTimesInTxt() {
        File file = new File(getApplicationContext().getFilesDir(), TIMES_FILE);
        try {
            if (!file.exists())
                file.createNewFile();
            writeFile(TIMES_FILE, "" + this.times);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeFile(String fileName,String writestr) throws IOException{
        try{

            FileOutputStream fout =openFileOutput(fileName, MODE_PRIVATE);

            byte [] bytes = writestr.getBytes();

            fout.write(bytes);

            fout.close();
        }

        catch(Exception e){
            e.printStackTrace();
        }
    }


    private void getTimesInSF() {
        this.times = this.sp.getInt(KEY_TIMES, this.defalutTimes);
        this.timesView.setText(""+this.times);
    }

    private void getTimesInTxT() {
        File file = new File(getApplicationContext().getFilesDir(), TIMES_FILE);
        if (file.exists()) {
            try {
                String t = readFile(TIMES_FILE);
                this.timesView.setText(t);
                this.times = Integer.valueOf(t);
            }  catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String readFile(String fileName) throws IOException{
        String res="";
        try{
            FileInputStream fin = openFileInput(fileName);
            int length = fin.available();
            byte [] buffer = new byte[length];
            fin.read(buffer);
            res = new String(buffer);
            fin.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return res;
    }


}
