package com.harrysoft.balloonbounce;

import com.harrysoft.balloonbounce.screens.SplashScreen;
import com.harrysoft.balloonbounce.utils.HSGame;

public class BalloonBounce extends HSGame {

    public BalloonBounce(ActionResolver actionResolver) {
        super(actionResolver);
    }

	@Override
	public void create () {
		setScreen(new SplashScreen(this));
	}
}
