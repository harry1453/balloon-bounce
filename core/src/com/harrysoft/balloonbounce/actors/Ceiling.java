package com.harrysoft.balloonbounce.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.harrysoft.balloonbounce.users.CeilingUserData;
import com.harrysoft.balloonbounce.utils.Constants;

public class Ceiling extends GameActor {

    private final TextureRegion textureRegion;
    private int speed = 0;

    public Ceiling(Body body) {
        super(body);
        textureRegion = new TextureRegion(new Texture(Gdx.files.internal(Constants.CEILING_IMAGE_PATH)));
    }

    @Override
    public CeilingUserData getUserData() {
        return (CeilingUserData) userData;
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        super.draw(batch, parentAlpha);
        batch.draw(textureRegion, screenRectangle.x, screenRectangle.y + screenRectangle.height / 4, screenRectangle.width,
                screenRectangle.height * 3 / 4);
    }
}

