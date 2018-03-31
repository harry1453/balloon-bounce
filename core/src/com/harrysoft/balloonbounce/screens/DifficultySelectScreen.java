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
import com.harrysoft.balloonbounce.enums.DifficultyType;
import com.harrysoft.balloonbounce.screens.transitions.ScreenTransition;
import com.harrysoft.balloonbounce.screens.transitions.ScreenTransitionSlide;
import com.harrysoft.balloonbounce.stages.OrthStage;
import com.harrysoft.balloonbounce.utils.Constants;
import com.harrysoft.balloonbounce.utils.HSGame;

public class DifficultySelectScreen extends HSScreen{

    private HSGame game;

    private OrthStage stage = new OrthStage();
    private Table table = new Table();
    private StillBackground background;

    private Skin skin = new Skin(Gdx.files.internal("uiTexture/skins/uiSkinBigNew.json"), new TextureAtlas(Gdx.files.internal("uiTexture/skins/uiTexture.pack")));

    private TextButton easyButton = new TextButton("Easy", skin), normalButton = new TextButton("Normal", skin), hardButton = new TextButton("Hard", skin), realisticButton = new TextButton("Realistic", skin), exitButton = new TextButton("Back", skin);
    private Label title = new Label("Select difficulty", skin);

    public DifficultySelectScreen(HSGame game){
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
        easyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //Same way we moved here from the Splash Screen
                //We set it to new Splash because we got no other screens
                //otherwise you put the screen there where you want to go
                ScreenTransition transition = ScreenTransitionSlide.init(Constants.TRANSITION_DELAY, ScreenTransitionSlide.LEFT, true, Interpolation.pow3);
                game.setScreen(new GameScreen(game, DifficultyType.EASY), transition);
                //Reference.resetGameScreen();
                //Reference.game.setScreen(Reference.gameScreen, transition);
            }
        });
        normalButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //Same way we moved here from the Splash Screen
                //We set it to new Splash because we got no other screens
                //otherwise you put the screen there where you want to go
                ScreenTransition transition = ScreenTransitionSlide.init(Constants.TRANSITION_DELAY, ScreenTransitionSlide.LEFT, true, Interpolation.pow3);
                game.setScreen(new GameScreen(game, DifficultyType.NORMAL), transition);
                //Reference.resetGameScreen();
                //Reference.game.setScreen(Reference.gameScreen, transition);
            }
        });
        hardButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //Same way we moved here from the Splash Screen
                //We set it to new Splash because we got no other screens
                //otherwise you put the screen there where you want to go
                ScreenTransition transition = ScreenTransitionSlide.init(Constants.TRANSITION_DELAY, ScreenTransitionSlide.LEFT, true, Interpolation.pow3);
                game.setScreen(new GameScreen(game, DifficultyType.HARD), transition);
                //Reference.resetGameScreen();
                //Reference.game.setScreen(Reference.gameScreen, transition);
            }
        });
        realisticButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //Same way we moved here from the Splash Screen
                //We set it to new Splash because we got no other screens
                //otherwise you put the screen there where you want to go
                ScreenTransition transition = ScreenTransitionSlide.init(Constants.TRANSITION_DELAY, ScreenTransitionSlide.LEFT, true, Interpolation.pow3);
                game.setScreen(new GameScreen(game, DifficultyType.REALISTIC), transition);
                //Reference.resetGameScreen();
                //Reference.game.setScreen(Reference.gameScreen, transition);
            }
        });
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenTransition transition = ScreenTransitionSlide.init(Constants.TRANSITION_DELAY, ScreenTransitionSlide.RIGHT, true, Interpolation.pow3);
                game.setScreen(new MainMenu(game), transition);
            }
        });

        title.setFontScale((float) (Constants.dimens.mainTitleFontScale * 0.75));
        easyButton.getLabel().setFontScale(Constants.dimens.buttonTextFontScale);
        easyButton.getLabelCell().padBottom(Constants.dimens.buttonBottomPadding);
        normalButton.getLabel().setFontScale(Constants.dimens.buttonTextFontScale);
        normalButton.getLabelCell().padBottom(Constants.dimens.buttonBottomPadding);
        hardButton.getLabel().setFontScale(Constants.dimens.buttonTextFontScale);
        hardButton.getLabelCell().padBottom(Constants.dimens.buttonBottomPadding);
        realisticButton.getLabel().setFontScale(Constants.dimens.buttonTextFontScale);
        realisticButton.getLabelCell().padBottom(Constants.dimens.buttonBottomPadding);
        exitButton.getLabel().setFontScale(Constants.dimens.buttonTextFontScale);
        exitButton.getLabelCell().padBottom(Constants.dimens.buttonBottomPadding);

        //title.setSize(titleWidth, titleHeight);
        table.add(title).padBottom(Constants.dimens.padding * 2).row();
        table.add(easyButton).size(Constants.dimens.buttonWidth, Constants.dimens.buttonHeight).padBottom(Constants.dimens.padding).row();
        table.add(normalButton).size(Constants.dimens.buttonWidth, Constants.dimens.buttonHeight).padBottom(Constants.dimens.padding).row();
        table.add(hardButton).size(Constants.dimens.buttonWidth, Constants.dimens.buttonHeight).padBottom(Constants.dimens.padding).row();
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
