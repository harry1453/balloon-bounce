package com.harrysoft.balloonbounce.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class FontUtils {

    public static BitmapFont getSmallFont(){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(Constants.FONT_NAME));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 36;
        BitmapFont smallFont = generator.generateFont(parameter);
        smallFont.setColor(.21f, .22f, .21f, 1f);
        generator.dispose();
        return smallFont;
    }

    public static BitmapFont getSmallestFont(){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(Constants.FONT_NAME));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 24;
        BitmapFont smallFont = generator.generateFont(parameter);
        smallFont.setColor(.21f, .22f, .21f, 1f);
        generator.dispose();
        return smallFont;
    }
}
