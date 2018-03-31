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

public class InstructionsScreen extends HSScreen {

    private HSGame game;
    private OrthStage stage;
    private Table table = new Table();
    private StillBackground background;

    private Skin skin = new Skin(Gdx.files.internal("uiTexture/skins/uiSkinBigNew.json"), new TextureAtlas(Gdx.files.internal("uiTexture/skins/uiTexture.pack")));

    private TextButton exitButton = new TextButton("Back", skin);
    private Label titleLabel = new Label("Instructions", skin);
    private Label leftInstructionsLabel = new Label("Tap the left side of the screen to bounce", skin);
    private Label bounceExplanationLabel = new Label("When you bounce, you drop and bounce off the trampoline", skin);
    private Label rightInstructionsLabel = new Label("Tap or hold the right side of the screen to ascend.", skin);

    public InstructionsScreen(HSGame game){
        this.game = game;
        stage = new OrthStage();
        background = new StillBackground(Constants.BACKGROUND_IMAGE_PATH);
    }

    @Override
    public void render(float deltaTime) {
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
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenTransition transition = ScreenTransitionSlide.init(Constants.TRANSITION_DELAY, ScreenTransitionSlide.LEFT, true, Interpolation.pow3);
                game.setScreen(new AboutScreen(game), transition);
            }
        });

        titleLabel.setFontScale(Constants.dimens.titleFontScale);
        leftInstructionsLabel.setFontScale(Constants.dimens.textFontScale);
        bounceExplanationLabel.setFontScale(Constants.dimens.textFontScale);
        rightInstructionsLabel.setFontScale(Constants.dimens.textFontScale);
        exitButton.getLabel().setFontScale(Constants.dimens.buttonTextFontScale);
        exitButton.getLabelCell().padBottom(Constants.dimens.buttonBottomPadding);

        table.add(titleLabel).padBottom(Constants.dimens.padding).row();
        table.add(leftInstructionsLabel).padBottom(Constants.dimens.padding).row();
        table.add(bounceExplanationLabel).padBottom(Constants.dimens.padding).row();
        table.add(rightInstructionsLabel).padBottom(Constants.dimens.padding).row();
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
    public InputProcessor getInputProcessor() {
        return stage;
    }
}
