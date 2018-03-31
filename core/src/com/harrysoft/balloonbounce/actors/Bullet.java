package com.harrysoft.balloonbounce.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.harrysoft.balloonbounce.enums.BulletDirection;
import com.harrysoft.balloonbounce.users.BulletData;
import com.harrysoft.balloonbounce.users.BulletUserData;
import com.harrysoft.balloonbounce.utils.Constants;

public class Bullet extends GameActor {

    public Body body;
    public BulletData bulletData;
    public TextureRegion texture;

    public Bullet(Body body, BulletData data){
        super(body);
        bulletData = data;
        body.setLinearVelocity(bulletData.velocity);
        TextureAtlas textureAtlas = new TextureAtlas(Constants.OBJECTS_ATLAS_PATH);
        if (bulletData.direction == BulletDirection.LEFT){
            texture = textureAtlas.findRegion(Constants.BULLET_LEFT_REGION_NAME);
        }
        if (bulletData.direction == BulletDirection.RIGHT){
            texture = textureAtlas.findRegion(Constants.BULLET_RIGHT_REGION_NAME);
        }
    }

    @Override
    public BulletUserData getUserData() {
        return (BulletUserData) userData;
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        super.draw(batch, parentAlpha);

        batch.draw(texture, screenRectangle.x, screenRectangle.y + screenRectangle.height / 4, screenRectangle.width,
                screenRectangle.height * 3 / 4);
    }
}
