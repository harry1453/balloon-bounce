package com.harrysoft.balloonbounce.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class Constants {

    public static final int ADVERTCOUNTER_LIMIT_BEFORE_AD = 5;

    public static final String TEMP_PREFERENCES_NAME = "tempPreferences";
    public static final String TEMP_PREFERENCES_KEY_SCORE = "score";

    public static final String AD_PREFERENCES_NAME = "adPreferences";
    public static final String AD_PREFERENCES_ADS_ENABLED_KEY = "adsEnabled";
    public static final String removeAdsPurchaseToken = "balloonBounceRemoveAds";

    public static final String SCORE_DATA_NAME = "scoreData";
    public static final String SCORE_DATA_NORMAL_HIGHSCORE_KEY = "highscore_normal";
    public static final String SCORE_DATA_EASY_HIGHSCORE_KEY = "highscore_easy";
    public static final String SCORE_DATA_HARD_HIGHSCORE_KEY = "highscore_hard";
    public static final String SCORE_DATA_REALISTIC_HIGHSCORE_KEY = "highscore_realistic";


    public static final String VERSION_TEXT = "Version 2.1";
    public static final String b64PublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAiB3azTiCsk99nTONXTlhBzaua+qi6A0mdJybspfrRBx7zae5NpiBIMSDrCqeu53X0TASAJFwQcnPsCpmO4isfN/HmXRek8GACZLA8vKqzFGD7NfAi3sMkFy+2RfeR5drJySdIk2yx75+FONe/2YzsxdDOR5P3hLdJM2hjtGphxqXNolvr0rsA6rMNHDDSvxHaWa+TGtDVGs9ebXK1X8YUVmeuqOMmC0XSyrFoBJ7vxYLVt5zWu1iqRlUyPDKg1NSDH97ws5xYJy8TklSLAOPeXUdSGeQL2weRO1ytAICJrRJAKZsxtZXglXU5BOBWEmh8cJApzF5qCFWAiKoivWAbwIDAQAB";

    public static final int APP_WIDTH = 960;
    public static final int APP_HEIGHT = 540;
    public static final int SCREEN_WIDTH = Gdx.graphics.getWidth();
    public static final int SCREEN_HEIGHT = Gdx.graphics.getHeight();
    public static final int WORLD_WIDTH = 20;
    public static final int WORLD_HEIGHT = 13;
    public static final String APP_TITLE = "Balloon bounce";
    public static final float WORLD_TO_SCREEN = SCREEN_WIDTH / WORLD_WIDTH;

    public static final String FONT_NAME = "roboto_bold.ttf";

    public static final float TRANSITION_DELAY = 1f;

    public static final class tutorial {
        public static final float TUTORIAL_DELAY = 2f;
        public static final String TUTORIAL_LEFT_TEXT = "Press left to bounce";
        public static final String TUTORIAL_RIGHT_TEXT = "Press right to ascend";
    }

    public static final class dimens {
        public static final int height = Constants.SCREEN_HEIGHT;
        public static final int width = Constants.SCREEN_WIDTH;
        public static final float buttonWidth = width / 4;
        public static final float buttonHeight = height / 7;
        public static final float padding = height / 25;
        public static final float buttonTextFontScale = 0.5f;
        public static final float textFontScale = 0.5f;
        public static final float buttonBottomPadding = height / 40;
        public static final float titleFontScale = 0.7f;
        public static final float mainTitleFontScale = 1f;
    }

    public static final class ads {
        public static final String adUnit_sample = "ca-app-pub-3940256099942544/6300978111";
    }

    public static final String BACKGROUND_IMAGE_PATH = "img/background.png";
    public static final String LAVA_IMAGE_PATH = "img/lava.png";
    public static final String CEILING_IMAGE_PATH = "img/ceiling.jpg";
    public static final String OBJECTS_ATLAS_PATH = "img/objects.txt";
    public static final String BALLOON_NORMAL_REGION_NAME = "balloon_normal";
    public static final String BALLOON_ASCENDING_REGION_NAME = "balloon_ascending";
    public static final String BALLOON_FALLING_REGION_NAME = "balloon_falling";
    public static final String BALLOON_HIT_REGION_NAME = "balloon_destroyed";
    public static final String BULLET_LEFT_REGION_NAME = "bullet_left";
    public static final String BULLET_RIGHT_REGION_NAME = "bullet_right";
    public static final String TRAMPOLINE_REGION_NAME = "trampoline";

    public static final Vector2 WORLD_GRAVITY = new Vector2(0, /*-10f*/0);

    public static final float GROUND_X = 0;
    public static final float GROUND_Y = 0;
    public static final float GROUND_WIDTH = 50f;
    public static final float GROUND_HEIGHT = 2f;
    public static float GROUND_DENSITY = 0f;

    public static final float CEILING_WIDTH = 50f;
    public static final float CEILING_HEIGHT = 1f;
    public static final float CEILING_X = 0;
    public static final float CEILING_Y = Constants.WORLD_HEIGHT - (Constants.CEILING_HEIGHT * 1.5f /* / 2*/);
    public static float CEILING_DENSITY = 0f;

    public static final float BALLOON_WIDTH = 2f;
    public static final float BALLOON_HEIGHT = 2f;
    public static float BALLOON_DENSITY = 0f;

    public static final Vector2 BALLOON_ASCENDING_VELOCITY = new Vector2(0, 5f);
    public static final Vector2 BALLOON_HOVERING_VELOCITY = new Vector2(0, 0);
    public static final Vector2 BALLOON_FALLING_VELOCITY = new Vector2(0, -10f);
    public static final float BALLOON_HIT_FALL_SPEED = -10f;
    public static final Vector2 BALLOON_BOUNCING_VELOCITY_1 = new Vector2(0, 3f);
    public static final Vector2 BALLOON_BOUNCING_VELOCITY_2 = new Vector2(0, 2f);
    public static final Vector2 BALLOON_BOUNCING_VELOCITY_3 = new Vector2(0, 1f);
    public static final float BALLOON_BOUNCING_SPEED_CHANGE_INTERVAL = 0.3f;
    public static final float BALLOON_NORMAL_GRAVITY_SCALE = 0f;

    public static final float TRAMPOLINE_WIDTH = 4f;
    public static final float TRAMPOLINE_HEIGHT = 3f;
    //public static final float TRAMPOLINE_X = (WORLD_WIDTH / 2) - (TRAMPOLINE_WIDTH / 2); // Middle of the screen
    public static final float TRAMPOLINE_Y = GROUND_Y; // Begins at the same height as the ground, 1f taller than the ground
    public static float TRAMPOLINE_DENSITY = 0f;

    public static final float BULLET_WIDTH = 0.5f;
    public static final float BULLET_HEIGHT = 0.5f;
    public static float BULLET_DENSITY = 0f;
    public static final float NEW_BULLET_DELAY = (float) 0.7;
    public static final float BULLET_SPEED_NORMAL = 5f;
    public static final float BULLET_SPEED_EASY = 6f;
    public static final float BULLET_SPEED_HARD = 4f;
    public static final float BULLET_SPEED_REALISTIC = 7f;
    public static final float BULLET_MAX_HEIGHT = CEILING_Y - BULLET_HEIGHT;

    public static final float GAME_OVER_DELAY = (float) 1; // From when the balloon is hit to when the game over screen is displayed
}
