package com.harrysoft.balloonbounce.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.harrysoft.balloonbounce.users.TrampolineUserData;
import com.harrysoft.balloonbounce.utils.Constants;

public class Trampoline extends GameActor {

    boolean bouncing;

    TextureRegion texture;

    public Trampoline(Body body) {
        super(body);
        bouncing = false;
        TextureAtlas textureAtlas = new TextureAtlas(Constants.OBJECTS_ATLAS_PATH);
        texture = textureAtlas.findRegion(Constants.TRAMPOLINE_REGION_NAME);
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        super.draw(batch, parentAlpha);

        batch.draw(texture, screenRectangle.x, ((screenRectangle.y + screenRectangle.height / 4) - screenRectangle.height * 0.00f), screenRectangle.width,
                screenRectangle.height * 3 / 4);
    }

    @Override
    public TrampolineUserData getUserData() {
        return (TrampolineUserData) userData;
    }

    public void bounce(){
        bouncing = true;
    }

    public void bounceFinished(){
        bouncing = false;
    }

    public boolean isBouncing(){
        return bouncing;
    }
}
