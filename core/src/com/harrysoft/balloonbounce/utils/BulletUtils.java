package com.harrysoft.balloonbounce.utils;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.harrysoft.balloonbounce.actors.Bullet;
import com.harrysoft.balloonbounce.enums.DifficultyType;
import com.harrysoft.balloonbounce.users.BulletData;
import com.harrysoft.balloonbounce.users.BulletUserData;

public class BulletUtils {

    private static Body createBulletBody(World world, BulletData data){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(data.x, data.y);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(Constants.BULLET_WIDTH / 2, Constants.BULLET_HEIGHT / 2);
        Body body = world.createBody(bodyDef);
        body.createFixture(shape, Constants.BULLET_DENSITY);
        body.resetMassData();
        body.setLinearVelocity(data.velocity);
        shape.dispose();
        body.setUserData(new BulletUserData(Constants.BULLET_WIDTH, Constants.BULLET_HEIGHT));
        return body;
    }

    public static Bullet createBullet(World world, DifficultyType difficulty){
        BulletData data = new BulletData(Constants.WORLD_WIDTH, Constants.BULLET_MAX_HEIGHT, difficulty);
        Body body = createBulletBody(world, data);
        Bullet bullet = new Bullet(body, data);
        return bullet;
    }
}
