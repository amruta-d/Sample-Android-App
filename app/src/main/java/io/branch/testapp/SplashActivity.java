package io.branch.testapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import org.json.JSONObject;

import io.branch.referral.Branch;
import io.branch.referral.BranchError;

public class SplashActivity extends AppCompatActivity
{
    Branch.BranchReferralInitListener branchCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Log.d("XAPP", "Splash");

        branchCallback = new Branch.BranchReferralInitListener()
        {
            @Override
            public void onInitFinished(JSONObject referringParams, BranchError error)
            {
                Log.d("XAPP", "Branch init session");
                if (error == null)
                {
                    Log.d("XAPP", referringParams.toString());
                    // run different activities depending on the parameters
                    if (1==2){

                    }
                    else
                    {
                        // fallback to Main activity
                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }
                else
                {
                    Log.i("XAPP", error.getMessage());
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                finish();
            }
        };
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        Log.d("XAPP", "onStart");
        Branch branch = Branch.getInstance();
        branch.initSession(branchCallback);
    }
}
