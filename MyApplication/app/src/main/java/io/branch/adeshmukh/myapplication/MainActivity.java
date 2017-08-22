package io.branch.adeshmukh.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONObject;

import io.branch.indexing.BranchUniversalObject;
import io.branch.referral.Branch;
import io.branch.referral.BranchError;
import io.branch.referral.util.LinkProperties;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private String data, linkData;
    private boolean isIdentitySet;
    private Button btnFb;
    private TextView mATxtView, mATxtView1;
    Branch branch;

    @Override
    protected void onStart() {
        super.onStart();

        branch = Branch.getInstance();


        branch.initSession(new Branch.BranchReferralInitListener() {
            @Override
            public void onInitFinished(JSONObject referringParams, BranchError error) {
                if (error == null) {
                    Log.v("data",referringParams.toString());

                } else {
                    Log.i("MyApp", error.getMessage());
                }
            }
        }, this.getIntent().getData(), this);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnFb = (Button)findViewById(R.id.fb_button);

        btnFb.setOnClickListener(this);


    }

    @Override
    protected void onNewIntent(Intent intent) {
        this.setIntent(intent);
    }

    @Override
    public void onClick(View v) {
        
        Intent i = new Intent(this, RedActivity.class);
        startActivity(i);


    }
}
