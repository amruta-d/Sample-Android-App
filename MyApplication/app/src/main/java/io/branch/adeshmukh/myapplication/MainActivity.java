package io.branch.adeshmukh.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import org.json.JSONObject;
import io.branch.referral.Branch;
import io.branch.referral.BranchError;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String data, linkData;
    private boolean branch_link;
    private boolean isIdentitySet;
    private Button btn1, btn2, btn3, btn4;
    private TextView mATxtView;
    Branch branch;

    @Override
    protected void onStart() {
        super.onStart();
        branch = Branch.getInstance();
        branch.initSession(new Branch.BranchReferralInitListener() {
            @Override
            public void onInitFinished(JSONObject referringParams, BranchError error) {
                if (error == null) {
                    Log.v("data", referringParams.toString());
                    linkData = referringParams.toString();
                    try {
                        branch_link = referringParams.getBoolean("+clicked_branch_link");
                        if(branch_link){
                            Intent intent = new Intent(MainActivity.this,RedActivity.class);
                            startActivity(intent);
                        } else {
                            System.out.println("not branch link");
                            Intent intent = new Intent(MainActivity.this,RedActivity.class);
                            startActivity(intent);
                        }



                    } catch (Exception e) {
                        e.printStackTrace();
                    }

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
        btn1 = (Button) findViewById(R.id.btn_1);
        btn2 = (Button) findViewById(R.id.btn_2);
        btn3 = (Button) findViewById(R.id.btn_3);
        btn4 = (Button) findViewById(R.id.btn_4);
        mATxtView = (TextView) findViewById(R.id.main_activity_textview);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);


    }

    @Override
    protected void onNewIntent(Intent intent) {
        this.setIntent(intent);
    }

    @Override
    public void onClick(View v) {
    }

}
