package com.harrysoft.balloonbounce.users;

import com.harrysoft.balloonbounce.enums.UserDataType;

public class CeilingUserData extends UserData{

    public CeilingUserData(float width, float height){
        super(width, height);
        userDataType = UserDataType.CEILING;
    }
}
