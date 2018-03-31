package com.harrysoft.balloonbounce;

public interface ActionResolver {
    public void showOrLoadInterstitial();
    public void showOrLoadInterstitialIfEnabled();
    public boolean isInterstitialLoaded();
    public void cancelShowingInterstitial();
    public void initialiseDisableAdsPayment();
    public void initialiseRestorePurchases();
}
