package com.harrysoft.balloonbounce.utils;

import com.badlogic.gdx.physics.box2d.Body;
import com.harrysoft.balloonbounce.enums.UserDataType;
import com.harrysoft.balloonbounce.users.UserData;

public class BodyUtils {
    public static boolean bodyInBounds(Body body) {
        UserData userData = (UserData) body.getUserData();

        switch (userData.getUserDataType()) {
            case BALLOON:
            case BULLET:
                return body.getPosition().x + userData.getWidth() / 2 > 0;
        }

        return true;
    }

    public static boolean bodyIsCertainActor(Body body, UserDataType type) {
        UserData userData = (UserData) body.getUserData();

        return userData != null && userData.getUserDataType() == (UserDataType) type;
    }

    public static boolean VerifyCollision(Body BodyA, Body BodyB, UserDataType TypeA, UserDataType TypeB){
        return ((bodyIsCertainActor(BodyA, TypeA) && BodyUtils.bodyIsCertainActor(BodyB, TypeB)) ||
                (BodyUtils.bodyIsCertainActor(BodyA, TypeB) && BodyUtils.bodyIsCertainActor(BodyB, TypeA)));
    }
}
