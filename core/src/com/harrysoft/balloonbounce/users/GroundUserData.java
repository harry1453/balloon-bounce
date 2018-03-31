package com.harrysoft.balloonbounce.users;

import com.harrysoft.balloonbounce.enums.UserDataType;

public class GroundUserData extends UserData{

    public GroundUserData(float width, float height) {
        super(width, height);
        userDataType = UserDataType.GROUND;
    }
}
