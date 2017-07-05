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
                    Log.v("identity", String.valueOf(branch.isUserIdentified()));
                    linkData = referringParams.toString();
                    isIdentitySet = branch.isUserIdentified();
                    mATxtView = (TextView)findViewById(R.id.main_activity_textview);
                    mATxtView1 = (TextView)findViewById(R.id.main_activity_textview1);
                    mATxtView.setText(linkData );
                    mATxtView1.setText("UserIdentified: " + isIdentitySet);


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
                    branch.setIdentity("abcde", new Branch.BranchReferralInitListener() {
                        @Override
                        public void onInitFinished(JSONObject referringParams, BranchError error) {
                            if (error == null) {
                                Log.e("TEST_TEST", "init success");
                                String text = "Branch identity set to abcde";
                                Toast.makeText(MainActivity.this, text, Toast.LENGTH_LONG).show();
                            } else {
                                String text = "Branch identity set error";
                                Toast.makeText(MainActivity.this, text, Toast.LENGTH_LONG).show();
                                Log.e("TEST_TEST", "set identity error: " + error.toString());
                            }
                        }
                    });


    }
}
