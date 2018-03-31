package com.harrysoft.balloonbounce.users;

import com.harrysoft.balloonbounce.enums.UserDataType;

public class TrampolineUserData extends UserData {

    public TrampolineUserData(float width, float height){
        super(width, height);
        userDataType = UserDataType.TRAMPOLINE;
    }
}
