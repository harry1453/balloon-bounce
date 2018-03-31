package com.harrysoft.balloonbounce.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.harrysoft.balloonbounce.users.BalloonUserData;
import com.harrysoft.balloonbounce.utils.Constants;

public class Balloon extends GameActor {

    private boolean ascending;
    private boolean falling;
    private boolean hitCeiling;
    private boolean hitTrampoline;
    private boolean justAscended;
    private boolean bouncing;
    private boolean hitByBullet;
    private boolean effecting;

    private TextureRegion normalTexture, ascendingTexture, fallingTexture, hitTexture;

    public Balloon(Body body) {
        super(body);
        ascending = false;
        hitTrampoline = false;
        justAscended = false;
        hitCeiling = false;
        falling = false;
        bouncing = false;
        hitByBullet = false;
        effecting = false;
        body.setGravityScale(Constants.BALLOON_NORMAL_GRAVITY_SCALE);

        TextureAtlas textureAtlas = new TextureAtlas(Constants.OBJECTS_ATLAS_PATH);
        normalTexture = textureAtlas.findRegion(Constants.BALLOON_NORMAL_REGION_NAME);
        ascendingTexture = textureAtlas.findRegion(Constants.BALLOON_ASCENDING_REGION_NAME);
        fallingTexture = textureAtlas.findRegion(Constants.BALLOON_FALLING_REGION_NAME);
        hitTexture = textureAtlas.findRegion(Constants.BALLOON_HIT_REGION_NAME);
    }

    @Override
    public BalloonUserData getUserData() {
        return (BalloonUserData) userData;
    }

    public void ascend(){
        if (!hitByBullet && !hitCeiling && !falling && !hitTrampoline && !bouncing){
            body.setLinearVelocity(Constants.BALLOON_ASCENDING_VELOCITY);
            effecting = true;
            ascending = true;
            justAscended = true;
        }
    }

    public void hover(){
        /*if (!hitByBullet && justAscended && !hitCeiling && !hitTrampoline){
            justAscended = false;
            slowDownEffect();
            ascending = false;
            falling = false;
        }*/ // TODO Fix slowdown-after-ascend effect
        if (!hitByBullet && !falling && !bouncing){
            body.setLinearVelocity(Constants.BALLOON_HOVERING_VELOCITY);
            falling = false;
            ascending = false;
        }
    }

    public void fall(){
        if (!hitByBullet && !hitTrampoline && !ascending){
            body.setLinearVelocity(Constants.BALLOON_FALLING_VELOCITY);
            falling = true;
            justAscended = false;
            ascending = false;
            hitCeiling = false;
            bouncing = false;
        }
    }

    public void bounce(){
        if (!hitByBullet) {
            justAscended = false;
            falling = false;
            hitTrampoline = true;
            ascending = false;
            bouncing = false; // We can ascend from this point
            slowDownEffect();
        }
    }

    public void noLongerTouchingBullet(){
        if (hitByBullet){
            body.setLinearVelocity(new Vector2(body.getLinearVelocity().x, Constants.BALLOON_HIT_FALL_SPEED));
        }
    }

    public void hitCeiling(){
        hitCeiling = true;
    }

    public void noLongerOnCeiling(){
        hitCeiling = false;
    }

    public void noLongerOnTrampoline(){
        hitTrampoline = false;
    }

    public void hitByBullet(){
        hitByBullet = true;
        justAscended = false;
        bouncing = false;
        falling = false;
        body.setLinearVelocity(new Vector2(body.getLinearVelocity().x, Constants.BALLOON_HIT_FALL_SPEED));
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        super.draw(batch, parentAlpha);

        float x = screenRectangle.x - (screenRectangle.width * 0.3f);
        float y = screenRectangle.y - (screenRectangle.height * 0.05f);
        float width = screenRectangle.width * 1.6f;
        float height = screenRectangle.height * 1.25f;

        /*float x = screenRectangle.x - (screenRectangle.width * 0.5f);
        float y = screenRectangle.y;
        float width = screenRectangle.width * 1.5f;*/
        if (hitByBullet){
            batch.draw(hitTexture, x, y, width, height);
        } else if (ascending) { // ascending
            batch.draw(ascendingTexture, x, y, width, height);
        } else if (falling){ // falling
            batch.draw(fallingTexture, x, y, width, height);
        } else if (bouncing) { // bouncing
            batch.draw(normalTexture, x, y, width, height);
        } else { // Normal
            batch.draw(normalTexture, x, y, width, height);
        }
    }

    public void slowDownEffect(){
        effecting = true;
        if (!ascending && !justAscended && !falling && !hitByBullet){ // don't keep going if they decided to ascend early
            body.setLinearVelocity(Constants.BALLOON_BOUNCING_VELOCITY_1); // Start at speed 1, quite fast
            Timer.schedule(new Task() { // wait (interval) seconds
                @Override
                public void run() {
                    if (!ascending && !justAscended && !falling && !hitByBullet && effecting){ // don't keep going if they decided to ascend early
                        body.setLinearVelocity(Constants.BALLOON_BOUNCING_VELOCITY_2); // slow down to second speed after x seconds
                        Timer.schedule(new Task() { // wait (interval) seconds
                            @Override
                            public void run() {
                                if (!ascending && !justAscended && !falling && !hitByBullet && effecting){ // don't keep going if they decided to ascend early
                                    body.setLinearVelocity(Constants.BALLOON_BOUNCING_VELOCITY_3);
                                    Timer.schedule(new Task() { // wait x seconds
                                        @Override
                                        public void run() {
                                            if (!ascending && !justAscended && !falling && !hitByBullet && effecting){
                                                body.setLinearVelocity(Constants.BALLOON_HOVERING_VELOCITY);
                                            }
                                        }

                                    }, Constants.BALLOON_BOUNCING_SPEED_CHANGE_INTERVAL);

                                }}}, Constants.BALLOON_BOUNCING_SPEED_CHANGE_INTERVAL);
                    }}
            }, Constants.BALLOON_BOUNCING_SPEED_CHANGE_INTERVAL);
        }
    }
}
