package com.imt_atlantique.tp1.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;


import android.os.Bundle;
import android.util.Log;

import com.imt_atlantique.tp1.R;


public class MainActivity extends AppCompatActivity {

    private InputInfoFragment inputInfoFragment;
    private ShowLastNameFragment showLastNameFragment;
    private FragmentManager fm = getSupportFragmentManager();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            this.inputInfoFragment = (InputInfoFragment) fm.getFragment(savedInstanceState, InputInfoFragment.TAG);
            this.showLastNameFragment = (ShowLastNameFragment) fm.getFragment(savedInstanceState, ShowLastNameFragment.TAG);
        }
        Log.i("Lifecycle MainActivity", "onCreate method");
    }
    @Override
    protected void onStart() {
        super.onStart();
        getDelegate().onStart();
        Log.i("Lifecycle MainActivity", "onStart method");
    }



    @Override
    protected void onResume() {
        super.onResume();
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (this.inputInfoFragment == null)
            this.inputInfoFragment = new InputInfoFragment();
        if (this.inputInfoFragment != null && !this.inputInfoFragment.isAdded()
                && this.showLastNameFragment == null )
            fragmentManager.beginTransaction().add(R.id.frag_display_container, this.inputInfoFragment, InputInfoFragment.TAG).commit();
//        if (this.showLastNameFragment != null && !this.showLastNameFragment.isAdded())
//            fragmentManager.beginTransaction().add(R.id.frag_display_container, this.showLastNameFragment, ShowLastNameFragment.TAG).commit();

        Log.i("Lifecycle MainActivity", "onResume method");

    }

    @Override
    protected  void onPause() {
        super.onPause();
        Log.i("Lifecycle MainActivity", "onPause method");
    }
    @Override
    protected void onStop() {
        super.onStop();
        //getDelegate().onStop();
        Log.i("Lifecycle MainActivity", "onStop method");

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.i("Lifecycle MainActivity", "onDestroy method");
    }



    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        Log.i("Lifecycle MainActivity", "onSaveInstanceState method");
        if (this.inputInfoFragment != null && this.inputInfoFragment.isAdded())
            fm.putFragment(outState, InputInfoFragment.TAG, this.inputInfoFragment);
        if (this.showLastNameFragment != null && this.showLastNameFragment.isAdded())
            fm.putFragment(outState, ShowLastNameFragment.TAG, this.showLastNameFragment);
    }
    @Override
    public void onRestoreInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        Log.i("Lifecycle MainActivity", "onRestoreInstanceState method");
    }

    public void replaceWithShowFrag(String name) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        boolean addedInput;
        boolean addedShow;
        if (this.inputInfoFragment != null) {
            addedInput = this.inputInfoFragment.isAdded();
            System.out.println(addedInput);
        }

        if (this.showLastNameFragment != null) {
            addedShow = this.showLastNameFragment.isAdded();
            System.out.println(addedShow);
        }
        this.showLastNameFragment = new ShowLastNameFragment(name);
        fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.frag_display_container, this.showLastNameFragment, ShowLastNameFragment.TAG).commit();
    }

    public void replaceWithInputFrag() {
        boolean addedInput;
        boolean addedShow;
        if (this.inputInfoFragment != null) {
            addedInput = this.inputInfoFragment.isAdded();
            System.out.println(addedInput);
        }

        if (this.showLastNameFragment != null) {
            addedShow = this.showLastNameFragment.isAdded();
            System.out.println(addedShow);
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.frag_display_container, this.inputInfoFragment, InputInfoFragment.TAG).commit();
    }

    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount() <= 0)//这里是取出我们返回栈存在Fragment的个数
            finish();
        else
            getSupportFragmentManager().popBackStack();

    }


}
