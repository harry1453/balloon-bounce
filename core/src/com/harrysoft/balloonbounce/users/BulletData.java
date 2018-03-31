package com.harrysoft.balloonbounce.users;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.harrysoft.balloonbounce.enums.BulletDirection;
import com.harrysoft.balloonbounce.enums.DifficultyType;
import com.harrysoft.balloonbounce.utils.BulletDirectionUtils;
import com.harrysoft.balloonbounce.utils.Constants;

public class BulletData {

    public BulletDirection direction;

    public float y;
    public float x;

    public Vector2 velocity;

    public BulletData(float width, float maxHeight, DifficultyType difficulty){
        float bulletSpeed;
        switch (difficulty){
            case EASY:
                bulletSpeed = Constants.BULLET_SPEED_EASY;
                break;
            case NORMAL:
                bulletSpeed = Constants.BULLET_SPEED_NORMAL;
                break;
            case HARD:
                bulletSpeed = Constants.BULLET_SPEED_HARD;
                break;
            case REALISTIC:
                bulletSpeed = Constants.BULLET_SPEED_REALISTIC;
                break;
            default:
                bulletSpeed = Constants.BULLET_SPEED_NORMAL;
                break;
        }
        direction = BulletDirectionUtils.getRandomBulletDirection();
        y = MathUtils.random(Constants.TRAMPOLINE_Y + Constants.TRAMPOLINE_HEIGHT,  maxHeight); // y is a random value between the top of the screen and the ceiling
        switch(direction){
            case RIGHT: // If the bullet is going right, start it on the left and make it travel to the right
                x = 0f;
                velocity = new Vector2(bulletSpeed, 0f);
                break;

            case LEFT: // If the bullet is going left, start it on the right and make it travel to the left
                x = width;
                velocity = new Vector2(-bulletSpeed, 0f);
                break;
        }
    }
}
