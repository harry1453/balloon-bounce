package com.harrysoft.balloonbounce.screens;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;

public abstract class HSScreen implements Screen {

    /*protected HSGame game;

    public HSScreen(HSGame game) {
        this.game = game;
    }*/

    public abstract void render (float deltaTime);
    public abstract void resize (int width, int height);
    public abstract void show ();
    public abstract void hide ();
    public abstract void pause ();
    public void resume () {
    }
    public void dispose () {
    }
    public abstract InputProcessor getInputProcessor ();
}
