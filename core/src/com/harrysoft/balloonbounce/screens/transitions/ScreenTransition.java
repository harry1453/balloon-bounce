package com.harrysoft.balloonbounce.screens.transitions;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface ScreenTransition {
    public void render (SpriteBatch batch, Texture currScreen, Texture nextScreen, float alpha);

    public float getDuration ();
}