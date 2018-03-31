package com.harrysoft.balloonbounce.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.harrysoft.balloonbounce.screens.transitions.ScreenTransition;
import com.harrysoft.balloonbounce.screens.transitions.ScreenTransitionFade;
import com.harrysoft.balloonbounce.stages.OrthStage;
import com.harrysoft.balloonbounce.utils.HSGame;

public class SplashScreen extends HSScreen {

    private HSGame game;

    //private Texture harrySoftLogo = new Texture(Gdx.files.internal("img/hslogo.png"));
    private Texture CakePodLogo = new Texture(Gdx.files.internal("img/cakepodSplash.png"));
    private Image splashImage = new Image(CakePodLogo);
    private OrthStage stage = new OrthStage();

    public SplashScreen(HSGame game){
        this.game = game;
    }

    @Override
    public void show() {
        //splashImage.setSize(Gdx.graphics.getWidth() / 2.5f, Gdx.graphics.getHeight() / 4);
        splashImage.setSize(Gdx.graphics.getWidth() + 1, Gdx.graphics.getHeight() + 1);
        stage.addActor(splashImage);
        splashImage.setPosition(Gdx.graphics.getWidth() / 2 - splashImage.getWidth() / 2, Gdx.graphics.getHeight() / 2 - splashImage.getHeight() / 2);
        splashImage.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(0.5f), Actions.delay(2), Actions.fadeOut(0.5f), Actions.run(new Runnable() {
            @Override
            public void run() {
                //BalloonBounce.setScreenWithTransition(new MainMenu(game));
                //game.setScreen(new MainMenu(game));
                //ScreenTransition transition = ScreenTransitionSlide.init(1f, ScreenTransitionSlide.DOWN, true, Interpolation.linear);
                ScreenTransition transition = ScreenTransitionFade.init(0.6f);
                game.setScreen(new MainMenu(game), transition);
                //Reference.game.setScreen(Reference.menuScreen, transition);
            }
        })));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public InputProcessor getInputProcessor() {
        return stage;
    }
}
