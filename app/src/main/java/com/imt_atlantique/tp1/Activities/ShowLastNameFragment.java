package com.imt_atlantique.tp1.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import com.imt_atlantique.tp1.R;

public class ShowLastNameFragment extends Fragment {

    private TextView lastnameView;
    private Button backToInput;

    private String msg;
    private static final String KEY_NAME  = "name";

    private View rootView;
    public static final String TAG = "ShowLastNameFrag";

    public ShowLastNameFragment() {

    }

    public ShowLastNameFragment(String msg) {
        this.msg = msg;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Lifecycle of ShowLastNameFragment", "onCreate method");
        if (savedInstanceState != null)
            this.msg = savedInstanceState.getString(ShowLastNameFragment.KEY_NAME);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.fragment_show,container,false);
        this.lastnameView = (TextView)this.rootView.findViewById(R.id.show_lastname);
        this.backToInput = (Button) this.rootView.findViewById(R.id.bt_to_input);
        this.backToInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity)getActivity();
                mainActivity.replaceWithInputFrag();
            }
        });
        this.setLastname();
        return this.rootView;
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString(ShowLastNameFragment.KEY_NAME, this.msg);
    }

    private void setLastname() {
        this.lastnameView.setText(this.msg);
    }
}
