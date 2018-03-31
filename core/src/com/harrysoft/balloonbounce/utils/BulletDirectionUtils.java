package com.harrysoft.balloonbounce.utils;

import com.harrysoft.balloonbounce.enums.BulletDirection;

import java.util.Random;

public class BulletDirectionUtils {

    public static BulletDirection getRandomBulletDirection() {
        RandomEnum<BulletDirection> randomEnum = new RandomEnum<BulletDirection>(BulletDirection.class);
        return randomEnum.random();
    }

    private static class RandomEnum<E extends Enum> {

        private static final Random RND = new Random();
        private final E[] values;

        public RandomEnum(Class<E> token) {
            values = token.getEnumConstants();
        }

        public E random() {
            return values[RND.nextInt(values.length)];
        }
    }
}
