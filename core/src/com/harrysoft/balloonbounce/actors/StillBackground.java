package com.harrysoft.balloonbounce.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class StillBackground extends Actor {

    private TextureRegion backgroundTexture;
    private Rectangle backgroundArea;

    public StillBackground(String BackgroundImagePath){
        backgroundTexture = new TextureRegion(new Texture(Gdx.files.internal(BackgroundImagePath)));
        backgroundArea = new Rectangle(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        super.draw(batch, parentAlpha);
        batch.draw(backgroundTexture, backgroundArea.x, backgroundArea.y, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }
}
