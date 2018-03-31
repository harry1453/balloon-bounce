package com.harrysoft.balloonbounce.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.harrysoft.balloonbounce.actors.StillBackground;
import com.harrysoft.balloonbounce.screens.transitions.ScreenTransition;
import com.harrysoft.balloonbounce.screens.transitions.ScreenTransitionSlide;
import com.harrysoft.balloonbounce.stages.OrthStage;
import com.harrysoft.balloonbounce.utils.Constants;
import com.harrysoft.balloonbounce.utils.HSGame;

public class MainMenu extends HSScreen {

    private HSGame game;

    private OrthStage stage = new OrthStage();
    private Table table = new Table();
    private StillBackground background;

    private Skin skin = new Skin(Gdx.files.internal("uiTexture/skins/uiSkinBigNew.json"), new TextureAtlas(Gdx.files.internal("uiTexture/skins/uiTexture.pack")));

    private TextButton playButton = new TextButton("Play", skin), exitButton = new TextButton("Exit", skin), aboutButton = new TextButton("About", skin);
    private Label title = new Label("Balloon bounce", skin);

    public MainMenu(HSGame game){
        this.game = game;
        background = new StillBackground(Constants.BACKGROUND_IMAGE_PATH);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //Same way we moved here from the Splash Screen
                //We set it to new Splash because we got no other screens
                //otherwise you put the screen there where you want to go
                ScreenTransition transition = ScreenTransitionSlide.init(Constants.TRANSITION_DELAY, ScreenTransitionSlide.LEFT, true, Interpolation.pow3);
                game.setScreen(new DifficultySelectScreen(game), transition);
                //Reference.resetGameScreen();
                //Reference.game.setScreen(Reference.gameScreen, transition);
            }
        });
        aboutButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenTransition transition = ScreenTransitionSlide.init(Constants.TRANSITION_DELAY, ScreenTransitionSlide.DOWN, true, Interpolation.pow3);
                game.setScreen(new AboutScreen(game), transition);
            }
        });
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        title.setFontScale(Constants.dimens.mainTitleFontScale);
        playButton.getLabel().setFontScale(Constants.dimens.buttonTextFontScale);
        playButton.getLabelCell().padBottom(Constants.dimens.buttonBottomPadding);
        aboutButton.getLabel().setFontScale(Constants.dimens.buttonTextFontScale);
        aboutButton.getLabelCell().padBottom(Constants.dimens.buttonBottomPadding);
        exitButton.getLabel().setFontScale(Constants.dimens.buttonTextFontScale);
        exitButton.getLabelCell().padBottom(Constants.dimens.buttonBottomPadding);

        //title.setSize(titleWidth, titleHeight);
        table.add(title).padBottom(Constants.dimens.padding * 2).row();
        table.add(playButton).size(Constants.dimens.buttonWidth, Constants.dimens.buttonHeight).padBottom(Constants.dimens.padding).row();
        table.add(aboutButton).size(Constants.dimens.buttonWidth, Constants.dimens.buttonHeight).padBottom(Constants.dimens.padding).row();
        table.add(exitButton).size(Constants.dimens.buttonWidth, Constants.dimens.buttonHeight).padBottom(Constants.dimens.padding).row();

        table.setFillParent(true);
        stage.addActor(background);
        stage.addActor(table);
    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }

    @Override
    public InputProcessor getInputProcessor() {
        return stage;
    }
}