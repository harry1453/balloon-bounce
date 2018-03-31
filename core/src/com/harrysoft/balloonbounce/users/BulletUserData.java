package com.harrysoft.balloonbounce.users;

import com.harrysoft.balloonbounce.enums.UserDataType;

public class BulletUserData extends UserData{

    public BulletUserData(float width, float height){
        super(width, height);
        userDataType = UserDataType.BULLET;
    }
}
