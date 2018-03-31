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

public class AboutScreen extends HSScreen {

    private HSGame game;
    private OrthStage stage = new OrthStage();
    private Table table = new Table();
    private StillBackground background;

    private Skin skin = new Skin(Gdx.files.internal("uiTexture/skins/uiSkinBigNew.json"), new TextureAtlas(Gdx.files.internal("uiTexture/skins/uiTexture.pack")));

    private TextButton exitButton = new TextButton("Main menu", skin);
    //private TextButton versionInfoButton = new TextButton("Version Info", skin);
    private TextButton instructionsButton = new TextButton("Instructions", skin);
    private Label titleLabel = new Label("About Balloon Bounce", skin);
    private Label copyRightLabel = new Label("Copyright CakePod Productions 2017", skin);
    private Label versionLabel = new Label("Version 2.0", skin);
    private Label madeByLabel = new Label("Developed by Harry Phillips", skin);
    private Label gameDesignLabel = new Label("Game Design by Harry Phillips", skin);

    public AboutScreen(HSGame game){
        this.game = game;
        background = new StillBackground(Constants.BACKGROUND_IMAGE_PATH);
    }
    @Override
    public void show() {
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenTransition transition = ScreenTransitionSlide.init(Constants.TRANSITION_DELAY, ScreenTransitionSlide.UP, true, Interpolation.pow3);
                game.setScreen(new MainMenu(game), transition);
            }
        });
        instructionsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenTransition transition = ScreenTransitionSlide.init(Constants.TRANSITION_DELAY, ScreenTransitionSlide.RIGHT, true, Interpolation.pow3);
                game.setScreen(new InstructionsScreen(game), transition);
            }
        });

        exitButton.getLabel().setFontScale(Constants.dimens.buttonTextFontScale);
        exitButton.getLabelCell().padBottom(Constants.dimens.buttonBottomPadding);
        instructionsButton.getLabel().setFontScale(Constants.dimens.buttonTextFontScale);
        instructionsButton.getLabelCell().padBottom(Constants.dimens.buttonBottomPadding);
        titleLabel.setFontScale(Constants.dimens.titleFontScale);
        versionLabel.setFontScale(Constants.dimens.textFontScale);
        copyRightLabel.setFontScale(Constants.dimens.textFontScale);
        madeByLabel.setFontScale(Constants.dimens.textFontScale);
        gameDesignLabel.setFontScale(Constants.dimens.textFontScale);

        table.add(titleLabel).padBottom(Constants.dimens.padding).row();
        table.add(copyRightLabel).padBottom(Constants.dimens.padding).row();
        table.add(madeByLabel).padBottom(Constants.dimens.padding).row();
        table.add(versionLabel).padBottom(Constants.dimens.padding).row();
        table.add(instructionsButton).size(Constants.dimens.buttonWidth, Constants.dimens.buttonHeight).padBottom(Constants.dimens.padding).row();
        table.add(exitButton).size(Constants.dimens.buttonWidth, Constants.dimens.buttonHeight).padBottom(Constants.dimens.padding).row();

        table.setFillParent(true);
        stage.addActor(background);
        stage.addActor(table);
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

    }

    @Override
    public InputProcessor getInputProcessor() {
        return stage;
    }
}
