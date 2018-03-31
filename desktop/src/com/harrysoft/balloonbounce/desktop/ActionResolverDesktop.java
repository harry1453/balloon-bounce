package com.harrysoft.balloonbounce.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.harrysoft.balloonbounce.ActionResolver;
import com.harrysoft.balloonbounce.utils.Constants;

public class ActionResolverDesktop implements ActionResolver {

    @Override @Deprecated
    public void showOrLoadInterstitialIfEnabled() {
        Preferences prefs = Gdx.app.getPreferences(Constants.AD_PREFERENCES_NAME);
        boolean adsEnabled = prefs.getBoolean(Constants.AD_PREFERENCES_ADS_ENABLED_KEY, true);
        if (adsEnabled){
            showOrLoadInterstitial();
        } else {
            System.out.println("(Not showing interstitial ad, disabled");
        }
    }

    @Override
    public boolean isInterstitialLoaded() {
        return true;
    }

    @Override
    public void cancelShowingInterstitial() {
        System.out.println("(Ad showing cancelled)");
    }

    @Override
    public void showOrLoadInterstitial() {
        System.out.println("(Interstitial Ad)");
    }

    @Override
    public void initialiseDisableAdsPayment() {
        System.out.println("(Disabling Ads)");
        Preferences prefs = Gdx.app.getPreferences(Constants.AD_PREFERENCES_NAME);
        prefs.putBoolean(Constants.AD_PREFERENCES_ADS_ENABLED_KEY, false);
        prefs.flush();
    }

    @Override
    public void initialiseRestorePurchases() {
        System.out.println("(Restoring purchases)");
    }
}
