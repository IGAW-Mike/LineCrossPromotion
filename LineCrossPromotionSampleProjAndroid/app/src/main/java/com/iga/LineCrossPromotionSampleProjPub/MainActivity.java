package com.iga.LineCrossPromotionSampleProjPub;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.igaworks.adbrix.line.InitInterstitialEventListener;
import com.igaworks.adbrix.line.ShowInterstitialEventListener;
import com.igaworks.adpopcorn.v2.core.AdSpotError;
import com.igaworks.adpopcorn.v2.core.IgawAdSpotListener;
import com.line.crosspromotion.LineCrossPromotion;

public class MainActivity extends AppCompatActivity {

    private String LOG_TAG = "LINE_BVT";
    Button showInterstitialBtn, getOfferwallAdInfoBtn, showOfferwallBtn, signUpBtn, signInBtn;
    String logMessage = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(false);

        makeViewComponent();

        /**
         * Common Function
         *     1. startApplication
         *         Call this api in onCreate method.
         *     2. startSession
         *         Call this api in onResume Method.
         *     3. endSession
         *         Call this api in onPause Method.
         **/

        // INITIALIZE LINE CROSS PROMOTION SDK
        LineCrossPromotion.startApplication(MainActivity.this);

        /**
         * Publisher Function
         *
         *     0. Set Encrypted Line UserId
         *         Set Encrypted Line UserId before load AdInfo
         *
         *     1. Interstitial Ad
         *         1.1. Init Interstitial SDK
         *             Call this api to init InterstitialAd function at once before call the showInterstitial API.
         *         1.2. Show Interstitial Ad
         *             Call this api to show InterstitialAd and if there are available Ads, the Ads will be shown to User.
         *
         *     2. Offerwall Ad
         *         2.1. Get Offerwall Ad Info
         *             Call this api to get OfferwallAd info at every time before call the showOfferwall api.
         *         2.2. Show Offerwall Ad
         *             Call this api to show Offerwall Ads to User
         *
         **/

        // SET ENCRYPTED LINE USER ID
        LineCrossPromotion.setUserId("INPUT_ENCRYPTED_LINE_USER_ID");

        // INITIALIZE INTERSTITIAL SDK AND IMPLEMENT InitInterstitialAdListener
        LineCrossPromotion.initInterstitialAd(MainActivity.this, registerInitInterstitialAdEventListener());

        // SHOW INTERSTITIAL AD AND IMPLEMENT ShowInterstitialAdListener
        showInterstitialBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LineCrossPromotion.showInterstitialAd(MainActivity.this, "INPUT_YOUR_AD_SPACE_KEY", registerInterstitialEventListener());
            }
        });

        // GET OFFERWALL AD INFO AND IMPLEMENT IgawAdSpotListener
        getOfferwallAdInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LineCrossPromotion.getOfferwall(MainActivity.this, "INPUT_YOUR_AD_SPOT_KEY", registerIgawAdSpotListener());
            }
        });

        // SHOW OFFERWALL AD
        showOfferwallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LineCrossPromotion.showOfferwall(MainActivity.this, "INPUT_YOUR_AD_SPOT_KEY");
            }
        });


        /**
         * Advertiser UnLock Function
         *     1. Sign Up Event
         *     2. Sign In Event
         **/

        // MAKE UNLOCK CALL WHEN SIGN_UP EVENT OCCURRED
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LineCrossPromotion.unlock(LineCrossPromotion.Event.SIGN_UP);
            }
        });

        // MAKE UNLOCK CALL WHEN SIGN_IN EVENT OCCURRED
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LineCrossPromotion.unlock(LineCrossPromotion.Event.SIGN_IN);
            }
        });
    }


    /**
     *   Implementation of InitInterstitial EventListener
     **/
    public InitInterstitialEventListener registerInitInterstitialAdEventListener(){
        InitInterstitialEventListener listener = new InitInterstitialEventListener() {
            @Override
            public void OnInitSuccess() {
                // Called when the initialize is succeeded.
                logMessage = "InitCrossPromotion InitSuccess";
                Log.d(LOG_TAG, logMessage);
            }

            @Override
            public void OnInitFail(String errorMsg) {
                // Called with error messages when the initialize is failed.
                logMessage = "InitCrossPromotion OnInitFail" +
                        "\nerrorMessage : " + errorMsg ;
                Log.d(LOG_TAG, logMessage);

            }
        };
        return listener;
    }


    /**
     *   Implementation of ShowInterstitial EventListener
     **/
    public ShowInterstitialEventListener registerInterstitialEventListener(){
        ShowInterstitialEventListener listener = new ShowInterstitialEventListener() {
            @Override
            public void OnShowInterstitialSuccess(String adSpaceKey) {
                // Called when the interstitialAd showing is succeeded.
                logMessage = "OnShowInterstitialSuccess" +
                        "\nadSpaceKey : " + adSpaceKey;
                Log.d(LOG_TAG, logMessage);
            }

            @Override
            public void OnShowInterstitialFailure(String adSpaceKey, String errorMsg) {
                // Called with error messages when the interstitialAd showing is failed.
                logMessage = "OnShowInterstitialFailure" +
                        "\nadSpaceKey : " + adSpaceKey +
                        "\nerrorMessage : " + errorMsg ;
                Log.d(LOG_TAG, logMessage);
            }

            @Override
            public void OnCloseInterstitial(String adSpaceKey) {
                // Called when the interstitialAd is closed.
                logMessage = "OnCloseInterstitial" +
                        "\nadSpaceKey : " + adSpaceKey;
                Log.d(LOG_TAG, logMessage);
            }
        };
        return listener;
    }


     /**
     *   Implementation of IgawAdSpotListener
     **/


    public IgawAdSpotListener registerIgawAdSpotListener(){
        IgawAdSpotListener listener = new IgawAdSpotListener() {
            @Override
            public void OnGetContentsSuccess(String adSpotKey, int contentsType, int adType) {
                // Called when getting offerwall contents is succeeded.
                logMessage = "OnGetContentsSuccess" +
                        "\nadSpotKey : " + adSpotKey +
                        "\ncontentType : " + contentsType +
                        "\nadType : " + adType;
                Log.d(LOG_TAG, logMessage);
            }

            @Override
            public void OnGetContentsFailure(String adSpotKey, AdSpotError adSpotError) {
                // Called with error info when getting offerwall content is failed.
                logMessage = "OnGetContentsFailure"+
                        "\nadSpotKey : " + adSpotKey +
                        "\nerrorCode : " + adSpotError.getErrorCode() +
                        "\nerrorMsg : " + adSpotError.getErrorMessage();
                Log.d(LOG_TAG, logMessage);
            }

            @Override
            public void OnShowContentsSuccess(String adSpotKey) {
                // Called when offerwall contents showing is succeeded.
                logMessage = "OnShowContentsSuccess" +
                        "\nadSpotKey : " + adSpotKey;
                Log.d(LOG_TAG, logMessage);
            }

            @Override
            public void OnShowContentsFailure(String adSpotKey, AdSpotError adSpotError) {
                // Called with error info when offerwall contents showing is failed.
                logMessage = "OnShowContentsFailure" +
                        "\nadSpotKey : " + adSpotKey +
                        "\nerrorCode : " + adSpotError.getErrorCode() +
                        "\nerrorMsg : " + adSpotError.getErrorMessage();
                Log.d(LOG_TAG, logMessage);
            }

            @Override
            public void OnCloseContents(String adSpotKey) {
                // Called when offerwall is closed.
                logMessage = "OnCloseContents" +
                        "\nadSpotKey : " + adSpotKey;
                Log.d(LOG_TAG,logMessage);
            }
        };
        return  listener;

    }

    // CALL startSession api
    @Override
    protected void onResume() {
        super.onResume();
        LineCrossPromotion.startSession(MainActivity.this);
    }

    // CALL endSession api
    @Override
    protected void onPause() {
        super.onPause();
        LineCrossPromotion.endSession();
    }

    public void makeViewComponent(){

        showInterstitialBtn = (Button)findViewById(R.id.showInterstitialBtn);
        getOfferwallAdInfoBtn = (Button)findViewById(R.id.getOfferwalAdInfoBtn);
        showOfferwallBtn = (Button)findViewById(R.id.showOfferwallBtn);

        signUpBtn = (Button)findViewById(R.id.signUpBtn);
        signInBtn = (Button)findViewById(R.id.signInBtn);

    }


}
