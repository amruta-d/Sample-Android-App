package io.branch.adeshmukh.myapplication;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import io.branch.indexing.BranchUniversalObject;
import io.branch.referral.Branch;
import io.branch.referral.BranchError;
import io.branch.referral.SharingHelper;
import io.branch.referral.util.CommerceEvent;
import io.branch.referral.util.CurrencyType;
import io.branch.referral.util.LinkProperties;
import io.branch.referral.util.Product;
import io.branch.referral.util.ProductCategory;
import io.branch.referral.util.ShareSheetStyle;

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

//        branch.setRetryInterval(1000000);
//        branch.setRetryCount(100);

//        branch.initSession(new MyListener(), this.getIntent().getData(), this)
        branch.initSession(new Branch.BranchReferralInitListener() {
            @Override
            public void onInitFinished(JSONObject referringParams, BranchError error) {
                if (error == null) {
                    Log.v("data", referringParams.toString());
                    Log.v("identity", String.valueOf(branch.isUserIdentified()));
                    linkData = referringParams.toString();
                    Log.v("data",referringParams.toString());


                    try {
//
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
//
                    }


//                    if(branch_link) {
//                        branch.setIdentity("efgh");
//                        branch.userCompletedAction("Registration Success");
//                        Toast.makeText(getApplicationContext(), "Registration Success sent", Toast.LENGTH_SHORT).show();
//
//                        branch.loadRewards(new Branch.BranchReferralStateChangedListener() {
//                            @Override
//                            public void onStateChanged(boolean b, BranchError branchError) {
//                                int credits = branch.getCredits();
//                                Log.v("credits", Integer.toString(credits));
//                                branch.userCompletedAction("AwardCredits");
//                                Toast.makeText(getApplicationContext(), "AwardCredits sent", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                    }


//                    isIdentitySet = branch.isUserIdentified();


//                    try{
//
//                        data = referringParams.getString("color");
//                        if(data.equals("red")){
////                            Intent intent = new Intent(MainActivity.this,RedActivity.class);
////                            startActivity(intent);
//                        }
//                    } catch (Exception e){
//
//                    }

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
        switch (v.getId()) {
            case R.id.btn_1:
                long millis = System.currentTimeMillis() % 1000;
                Log.v("identity", Long.toString(millis));
                branch.setIdentity(Long.toString(millis));
                break;
            case R.id.btn_2:
                branch.logout();
                break;
            case R.id.btn_3:
                final BranchUniversalObject branchUniversalObject = new BranchUniversalObject()
                        .setCanonicalIdentifier("/1234")
                        .setTitle("Test for referral")
                        .setContentIndexingMode(BranchUniversalObject.CONTENT_INDEX_MODE.PUBLIC)
                        .setContentDescription("Your friend has invited you to check out my app!")
                        .setContentImageUrl("https://upload.wikimedia.org/wikipedia/commons/thumb/5/5a/Branch_Metrics_logo_color.png/1200px-Branch_Metrics_logo_color.png")
                        .addContentMetadata("var109", "abc")
                        .addContentMetadata("var2", "def");

                final LinkProperties linkProperties = new LinkProperties()
                        .setChannel("Facebook")
                        .setFeature("Sharing");

                ShareSheetStyle ss = new ShareSheetStyle(MainActivity.this, "Check this out!", "This stuff is awesome: ")
                .setCopyUrlStyle(ContextCompat.getDrawable(this, android.R.drawable.ic_menu_send), "Copy", "Added to clipboard")
                .setMoreOptionStyle(ContextCompat.getDrawable(this, android.R.drawable.ic_menu_search), "Show more")
                .addPreferredSharingOption(SharingHelper.SHARE_WITH.FACEBOOK)
                .addPreferredSharingOption(SharingHelper.SHARE_WITH.EMAIL)
                .addPreferredSharingOption(SharingHelper.SHARE_WITH.MESSAGE)
                .addPreferredSharingOption(SharingHelper.SHARE_WITH.HANGOUT)
                .setAsFullWidthStyle(true)
                .setSharingTitle("Share With");

        branchUniversalObject.showShareSheet(this, linkProperties,  ss,  new Branch.BranchLinkShareListener() {
            @Override
            public void onShareLinkDialogLaunched() {
            }
            @Override
            public void onShareLinkDialogDismissed() {
            }
            @Override
            public void onLinkShareResponse(String sharedLink, String sharedChannel, BranchError error) {
            }
            @Override
            public void onChannelSelected(String channelName) {
                linkProperties.setCampaign("testCamp");
                branchUniversalObject.addContentMetadata("var3", "ghi")
                        .addContentMetadata("var4","jkl");
            }
        });

//                branchUniversalObject.generateShortUrl(this, linkProperties, new Branch.BranchLinkCreateListener() {
//                    @Override
//                    public void onLinkCreate(String url, BranchError error) {
//                        if (error == null) {
//
//                            Log.v("url", url);
//
//                            Toast.makeText(MainActivity.this, url, Toast.LENGTH_LONG).show();
//                        } else {
//                            Log.v("url", url);
//                        }
//
//                    }
//                });
                break;
            case R.id.btn_4:
                createTestNotification();
//                branch.userCompletedAction("referAFriend");
//                test();
                break;
            default:
                break;


        }

//        branch.setIdentity("abcdefg");


        //Add details about each product associated with the purchase (optional)

//        Product product = new Product();
//        product.setName("foo-item-1");
//        product.setCategory(ProductCategory.CAMERA_AND_OPTICS);
//        product.setPrice(45.00);
//        product.setQuantity(1);

        //Create a list of products associated with the particular purchase (optional)

//        List<Product> productList = new ArrayList<Product>();
//        productList.add(product);

        //Create the commerce event

//        CommerceEvent commerceEvent = new CommerceEvent();
//        commerceEvent.setRevenue(1101.99);
//        commerceEvent.setCurrencyType(CurrencyType.USD);
//        commerceEvent.setTransactionID("TRANS-1111");
//        commerceEvent.setShipping(4.50);
//        commerceEvent.setTax(110.90);
//        commerceEvent.setAffiliation("AFF-ID-101");
//        commerceEvent.setProducts(productList);

        //Fire the commerce event
//        Branch.getInstance().sendCommerceEvent(commerceEvent, null, null);
//        branch.setIdentity("abcdefg");
//        final BranchUniversalObject branchUniversalObject = new BranchUniversalObject()
//                .setCanonicalIdentifier("movie")
//                .setTitle("Test for movie")
//                .setContentIndexingMode(BranchUniversalObject.CONTENT_INDEX_MODE.PUBLIC)
//                .setContentDescription("Your friend has invited you to check out my app!")
//                .setContentImageUrl("https://image.freepik.com/free-vector/bird-icon_23-2147507196.jpg")
//                .addContentMetadata("test", "abc")
//                .addContentMetadata("data", "Red Activity");
//
//        LinkProperties linkProperties = new LinkProperties()
//                .setChannel("Facebook")
//                .setFeature("Sharing")
//                .setAlias("adhellotest");
////                .addControlParameter("$twitter_title","hello")
////                .addControlParameter("$twitter_description","this is a test for twitter")
////                .addControlParameter("$twitter_card","player")
////                .addControlParameter("$twitter_player_width","1280")
////                .addControlParameter("$twitter_player_height","760")
////                .addControlParameter("$twitter_player","https://www.youtube.com/embed/LiP_3UOnyMI")
////                .addControlParameter("$android_deepview", "branch_default");
//
//        ShareSheetStyle ss = new ShareSheetStyle(MainActivity.this, "Check this out!", "This stuff is awesome: ")
//                .setCopyUrlStyle(ContextCompat.getDrawable(this, android.R.drawable.ic_menu_send), "Copy", "Added to clipboard")
//                .setMoreOptionStyle(ContextCompat.getDrawable(this, android.R.drawable.ic_menu_search), "Show more")
//                .addPreferredSharingOption(SharingHelper.SHARE_WITH.FACEBOOK)
//                .addPreferredSharingOption(SharingHelper.SHARE_WITH.EMAIL)
//                .addPreferredSharingOption(SharingHelper.SHARE_WITH.MESSAGE)
//                .addPreferredSharingOption(SharingHelper.SHARE_WITH.HANGOUT)
//                .setAsFullWidthStyle(true)
//                .setSharingTitle("Share With");
//
//        branchUniversalObject.showShareSheet(this, linkProperties,  ss,  new Branch.BranchLinkShareListener() {
//            @Override
//            public void onShareLinkDialogLaunched() {
//            }
//            @Override
//            public void onShareLinkDialogDismissed() {
//            }
//            @Override
//            public void onLinkShareResponse(String sharedLink, String sharedChannel, BranchError error) {
//            }
//            @Override
//            public void onChannelSelected(String channelName) {
//            }
//        });






//        branchUniversalObject.generateShortUrl(this, linkProperties, new Branch.BranchLinkCreateListener() {
//            @Override
//            public void onLinkCreate(String url, BranchError error) {
//                if (error == null) {
//
//                    Log.v("url",url);
//
//                    Toast.makeText(MainActivity.this,url,Toast.LENGTH_LONG).show();
//
//                    branch.setIdentity("abcde", new Branch.BranchReferralInitListener() {
//                        @Override
//                        public void onInitFinished(JSONObject referringParams, BranchError error) {
//                            if (error == null) {
//                                Log.e("TEST_TEST", "init success");
//                                String text = "Branch identity set to abcd";
//                                Toast.makeText(MainActivity.this, text, Toast.LENGTH_LONG).show();
//                            } else {
//                                String text = "Branch identity set error";
//                                Toast.makeText(MainActivity.this, text, Toast.LENGTH_LONG).show();
//                                Log.e("TEST_TEST", "set identity error: " + error.toString());
//                            }
//                        }
//                    });
//                    AppInviteContent content = new AppInviteContent.Builder()
//                            .setApplinkUrl(url)
//                            .setPreviewImageUrl("https://image.freepik.com/free-vector/bird-icon_23-2147507196.jpg")
//                            .build();
//                    AppInviteDialog.show(MainActivity.this, content);
//                }
//
//                else {
//                    Log.v("url",url);
//                }
//
//            }
//        });
//
//        Intent i = new Intent(this, RedActivity.class);
//        startActivity(i);

//        createTestNotification();


    }

    private void createTestNotification(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("branch","https://amrutademo.app.link/M6CqTF8A5F");
        intent.putExtra("$branch_force_new_session",true);
        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, PendingIntent.FLAG_UPDATE_CURRENT);


        Notification n  = new Notification.Builder(this)
                .setContentTitle("My App Notification test")
                .setContentText("Subject")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .addAction(R.mipmap.ic_launcher, "Call", pIntent)
                .addAction(R.mipmap.ic_launcher, "More", pIntent)
                .addAction(R.mipmap.ic_launcher, "And more", pIntent).build();


        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        notificationManager.notify(0, n);
    }

//    private void test() {
//        final String millis = Long.toString(System.currentTimeMillis() % 1000);
//        Log.v("identity", millis);
//        branch.setIdentity(millis, new Branch.BranchReferralInitListener() {
//            @Override
//            public void onInitFinished(JSONObject referringParams, BranchError error) {
//                if (!millis.equalsIgnoreCase("")) {
//                    branch.userCompletedAction("referAFriend");
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            branch.loadRewards(new Branch.BranchReferralStateChangedListener() {
//                                @Override
//                                public void onStateChanged(boolean changed, BranchError error) {
//                                    if (error == null) {
//                                        if (changed) {
//                                            int credits = branch.getCredits();
//                                            Log.e("credits", credits + "");
//                                            branch.redeemRewards(credits, new Branch.BranchReferralStateChangedListener() {
//                                                @Override
//                                                public void onStateChanged(boolean changed, BranchError error) {
//                                                    branch.logout(new Branch.LogoutStatusListener() {
//                                                        @Override
//                                                        public void onLogoutFinished(boolean loggedOut, BranchError error) {
//                                                            if (loggedOut) {
//                                                                Log.e("210", loggedOut + "");
//                                                                if (!millis.equalsIgnoreCase("")) {
//                                                                    Log.e("209", millis + "");
//                                                                    branch.setIdentity(millis, new Branch.BranchReferralInitListener() {
//                                                                        @Override
//                                                                        public void onInitFinished(JSONObject referringParams, BranchError error) {
//                                                                            Log.e("215", referringParams + "");
//                                                                            if (referringParams.toString().equalsIgnoreCase("{}")) {
//                                                                                Log.v("Signup", "signup completed successfully");
//                                                                            } else {
//                                                                                if (error == null) {
//                                                                                    branch.loadRewards(new Branch.BranchReferralStateChangedListener() {
//                                                                                        @Override
//                                                                                        public void onStateChanged(boolean changed, BranchError error) {
//                                                                                            if (error == null) {
//                                                                                                if (changed) {
//                                                                                                    int refreepoints = branch.getCredits();
//                                                                                                    Log.e("refreepoints", refreepoints + "");
//                                                                                                    branch.redeemRewards(refreepoints, new Branch.BranchReferralStateChangedListener() {
//                                                                                                        @Override
//                                                                                                        public void onStateChanged(boolean changed, BranchError error) {
//                                                                                                            if (error == null) {
//                                                                                                                if (changed) {
//
//                                                                                                                } else {
//
//                                                                                                                }
//                                                                                                            } else {
//                                                                                                                Log.e("255", "255");
//                                                                                                            }
//                                                                                                        }
//                                                                                                    });
//                                                                                                }
//                                                                                            } else {
//                                                                                                Log.e("265", error + "");
//                                                                                                Log.v("Signup", "signup completed successfully");
//                                                                                            }
//                                                                                        }
//                                                                                    });
//                                                                                } else {
//                                                                                    Log.e("268", error + "");
//                                                                                }
//                                                                            }
//                                                                        }
//                                                                    });
//                                                                } else {
//                                                                    Log.e("266", "error");
//                                                                }
//                                                            }
//                                                        }
//                                                    });
//                                                }
//                                            });
//                                        } else {
//                                            Log.e("271", error + "");
//                                        }
//                                    } else {
//                                        Log.e("274", error + "");
//                                    }
//                                }
//                            });
//                        }
//                    }, 10000);
//                } else {
//
//                }
//            }
//        });
//    }

}
