package com.harrysoft.balloonbounce.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.harrysoft.balloonbounce.users.BalloonUserData;
import com.harrysoft.balloonbounce.users.CeilingUserData;
import com.harrysoft.balloonbounce.users.GroundUserData;
import com.harrysoft.balloonbounce.users.TrampolineUserData;

public class WorldUtils {

    public static World createWorld(){
        return new World(Constants.WORLD_GRAVITY, true);
    }

    public static Body createGround(World world){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(new Vector2(Constants.GROUND_X, Constants.GROUND_Y));
        Body body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(Constants.GROUND_WIDTH / 2, Constants.GROUND_HEIGHT / 2);
        body.createFixture(shape, Constants.GROUND_DENSITY);
        body.setUserData(new GroundUserData(Constants.GROUND_WIDTH, Constants.GROUND_HEIGHT));
        shape.dispose();
        return body;
    }

    public static Body createCeiling(World world){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(new Vector2(Constants.CEILING_X, Constants.CEILING_Y));
        Body body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(Constants.CEILING_WIDTH / 2, Constants.CEILING_HEIGHT / 2);
        body.createFixture(shape, Constants.CEILING_DENSITY);
        shape.dispose();
        body.setUserData(new CeilingUserData(Constants.CEILING_WIDTH, Constants.CEILING_HEIGHT));
        return body;
    }

    public static Body createBalloon(World world){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(new Vector2(Constants.WORLD_WIDTH / 2, Constants.WORLD_HEIGHT / 2));
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(Constants.BALLOON_WIDTH / 2, Constants.BALLOON_HEIGHT / 2);
        Body body = world.createBody(bodyDef);
        body.createFixture(shape, Constants.BALLOON_DENSITY);
        body.resetMassData();
        shape.dispose();
        body.setUserData(new BalloonUserData(Constants.BALLOON_WIDTH, Constants.BALLOON_HEIGHT));
        return body;
    }

    public static Body createTrampoline(World world){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(new Vector2(Constants.WORLD_WIDTH / 2, Constants.GROUND_X / 2));
        Body body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(Constants.TRAMPOLINE_WIDTH / 2, Constants.TRAMPOLINE_HEIGHT / 2);
        body.createFixture(shape, Constants.TRAMPOLINE_DENSITY);
        shape.dispose();
        body.setUserData(new TrampolineUserData(Constants.TRAMPOLINE_WIDTH, Constants.TRAMPOLINE_HEIGHT));
        return body;
    }
}
