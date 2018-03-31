package com.harrysoft.balloonbounce.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.harrysoft.balloonbounce.BalloonBounce;
import com.harrysoft.balloonbounce.utils.Constants;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = Constants.APP_HEIGHT;
		config.width = Constants.APP_WIDTH;
		config.title = Constants.APP_TITLE;
		new LwjglApplication(new BalloonBounce(new ActionResolverDesktop()), config);
	}
}
