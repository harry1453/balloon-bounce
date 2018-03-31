package com.harrysoft.balloonbounce.users;

import com.harrysoft.balloonbounce.enums.UserDataType;

public class BalloonUserData extends UserData {

    public BalloonUserData(float width, float height){
        super(width, height);
        userDataType = UserDataType.BALLOON;
    }
}
