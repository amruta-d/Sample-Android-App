package io.branch.testapp;

import android.app.Application;

import io.branch.referral.Branch;

/**
 * Created by adeshmukh on 1/9/18.
 */

public class CustomApplicationClass extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // initialize the Branch object
//        Branch.enableTestMode();

//        Branch.enableLogging();
//        Branch.getInstance().setDebug();
//        Branch.getAutoTestInstance(this);
        Branch.getAutoInstance(this);
    }
}

