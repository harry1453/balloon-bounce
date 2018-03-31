package com.harrysoft.balloonbounce.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.harrysoft.balloonbounce.enums.DifficultyType;
import com.harrysoft.balloonbounce.stages.GameStage;
import com.harrysoft.balloonbounce.utils.HSGame;

public class GameScreen extends HSScreen {

    private HSGame game;
    private GameStage stage;

    public GameScreen(HSGame game, DifficultyType difficulty){
        this.game = game;
        stage = new GameStage(game, difficulty);
        if (!game.getActionResolver().isInterstitialLoaded()){ // If the interstitial isn't loaded
            game.getActionResolver().showOrLoadInterstitial();
            game.getActionResolver().cancelShowingInterstitial(); // Effectively load the ad
        }
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
            // Clear the screen
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            // Update the stage
            stage.draw();
            stage.act(delta);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        stage.pause();
    }

    @Override
    public void resume() {
        stage.resume();
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public InputProcessor getInputProcessor() {
        return stage;
    }
}
