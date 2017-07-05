package io.branch.adeshmukh.myapplication;

import android.app.Application;
import android.util.Log;

import io.branch.referral.Branch;


/**
 * Created by adeshmukh on 5/8/17.
 */

public class CustomApplicationClass extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // initialize the Branch object
//        Branch.enableTestMode();
        Branch.getAutoInstance(this);
    }
}
