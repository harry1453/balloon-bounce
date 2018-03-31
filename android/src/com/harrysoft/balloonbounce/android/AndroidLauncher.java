package com.harrysoft.balloonbounce.android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.harrysoft.balloonbounce.ActionResolver;
import com.harrysoft.balloonbounce.BalloonBounce;
import com.harrysoft.balloonbounce.android.util.IabHelper;
import com.harrysoft.balloonbounce.android.util.IabResult;
import com.harrysoft.balloonbounce.android.util.Inventory;
import com.harrysoft.balloonbounce.android.util.Purchase;
import com.harrysoft.balloonbounce.utils.Constants;

public class AndroidLauncher extends AndroidApplication implements ActionResolver {

    private static final String AD_UNIT_ID = "ca-app-pub-8103279338781511/1683392985";
    private static final String ITEM_SKU = " balloon_bounce_remove_ads";
    private IabHelper purchaseHelper;
    protected View gameView;
    private boolean adLoaded;
    private InterstitialAd interstitialAd;
    private boolean adShowingCancelled = false;

    private boolean showAdWhenLoaded = false;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useImmersiveMode = true;
        config.hideStatusBar = true; // To Support old devices
        //initialize(new BalloonBounce(), config);
        // Do the stuff that initialize() would do for you
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

        RelativeLayout layout = new RelativeLayout(this);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        layout.setLayoutParams(params);

        //AdView admobView = createAdView();
        //layout.addView(admobView);
        View gameView = createGameView(config);
        layout.addView(gameView);

        setContentView(layout);
        //startAdvertising(admobView);
        interstitialAd = new InterstitialAd(this);
        adLoaded = false;
        interstitialAd.setAdUnitId(AD_UNIT_ID);
        interstitialAd.setAdListener(new AdListener() {
        @Override
        public void onAdLoaded() {
            adLoaded = true;
            if (!adShowingCancelled && showAdWhenLoaded){
                interstitialAd.show();
            }
        }
        @Override
        public void onAdClosed() {
            AdRequest interstitialRequest = new AdRequest.Builder().addTestDevice("90194AA3D0254B8CC21A7DE3BDE1CBB4").build();
            interstitialAd.loadAd(interstitialRequest); // Reload the ad
            showAdWhenLoaded = false;
        }
        });
        purchaseHelper = new IabHelper(this, Constants.b64PublicKey);

        purchaseHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                if (!result.isSuccess()) {
                    Toast.makeText(getApplicationContext(), "In-app Billing setup failed: " + result, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private View createGameView(AndroidApplicationConfiguration cfg) {
        gameView = initializeForView(new BalloonBounce(this), cfg);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        gameView.setLayoutParams(params);
        return gameView;
    }

    @Override
    public void showOrLoadInterstitial() {
        adShowingCancelled = false;
        showAdWhenLoaded = true;
        try {
            runOnUiThread(new Runnable() {
                public void run() {
                    if (interstitialAd.isLoaded()) {
                        interstitialAd.show();
                    } else {
                        AdRequest interstitialRequest = new AdRequest.Builder().addTestDevice("90194AA3D0254B8CC21A7DE3BDE1CBB4").build();
                        interstitialAd.loadAd(interstitialRequest);
                    }
                    //
                }
            });
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "ERROR: Cannot load adverts", Toast.LENGTH_LONG).show();
        }
    }

    @Deprecated
    @Override
    public void showOrLoadInterstitialIfEnabled() {
        Preferences prefs = Gdx.app.getPreferences(Constants.AD_PREFERENCES_NAME);
        boolean adsEnabled = prefs.getBoolean(Constants.AD_PREFERENCES_ADS_ENABLED_KEY, true);
        if (adsEnabled){
            Toast.makeText(getApplicationContext(), "Ads are enabled, showing ad", Toast.LENGTH_SHORT).show();
            showOrLoadInterstitial();
        } else {
            Toast.makeText(getApplicationContext(), "Ads disabled", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean isInterstitialLoaded() {
        return adLoaded;
    }

    @Override
    public void cancelShowingInterstitial() {
        adShowingCancelled = true;
    }

    @Override
    public void initialiseDisableAdsPayment() {
        purchaseHelper.launchPurchaseFlow(this, ITEM_SKU, 10001, mPurchaseFinishedListener, Constants.removeAdsPurchaseToken);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (!purchaseHelper.handleActivityResult(requestCode,
                resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener
            = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result,
                                          Purchase purchase)
        {
            if (result.isFailure()) {
                // Handle error
                return;
            }
            else if (purchase.getSku().equals(ITEM_SKU)) {
                //consumeItem();
                setAdsAsDisabled();
            }

        }
    };

    private void setAdsAsDisabled() {
        Preferences prefs = Gdx.app.getPreferences(Constants.AD_PREFERENCES_NAME);
        prefs.putBoolean(Constants.AD_PREFERENCES_ADS_ENABLED_KEY, false);
        prefs.flush();
    }

    @Override
    public void initialiseRestorePurchases() {
        purchaseHelper.queryInventoryAsync(mGotInventoryListener);
    }

    IabHelper.QueryInventoryFinishedListener mGotInventoryListener
            = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result,
                                             Inventory inventory) {

            if (result.isFailure()) {
                // handle error here
            }
            else {
                // does the user have the premium upgrade?
                boolean adsDisabled = inventory.hasPurchase(ITEM_SKU);
                Preferences prefs = Gdx.app.getPreferences(Constants.AD_PREFERENCES_NAME);
                prefs.putBoolean(Constants.AD_PREFERENCES_ADS_ENABLED_KEY, !adsDisabled);
                prefs.flush();
            }
        }
    };
}
