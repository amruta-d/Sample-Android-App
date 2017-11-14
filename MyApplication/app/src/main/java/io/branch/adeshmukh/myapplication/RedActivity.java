package io.branch.adeshmukh.myapplication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import io.branch.referral.Branch;

public class RedActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red);
        textView = (TextView) findViewById(R.id.red_text);
        textView.setText("red");
    }

    @Override
    protected void onResume() {
        super.onResume();
        String data = Branch.getInstance().getLatestReferringParams().toString();

        Log.v("link data",data);
    }
}
