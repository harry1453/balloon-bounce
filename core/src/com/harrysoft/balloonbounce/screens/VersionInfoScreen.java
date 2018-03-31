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
import com.badlogic.gdx.utils.Timer;
import com.harrysoft.balloonbounce.actors.StillBackground;
import com.harrysoft.balloonbounce.screens.transitions.ScreenTransition;
import com.harrysoft.balloonbounce.screens.transitions.ScreenTransitionSlide;
import com.harrysoft.balloonbounce.stages.OrthStage;
import com.harrysoft.balloonbounce.utils.Constants;
import com.harrysoft.balloonbounce.utils.HSGame;

@Deprecated
public class VersionInfoScreen extends HSScreen {

    private HSGame game;
    private OrthStage stage = new OrthStage();
    private Table table = new Table();
    private StillBackground background;

    private Skin skin = new Skin(Gdx.files.internal("uiTexture/skins/uiSkinBigNew.json"), new TextureAtlas(Gdx.files.internal("uiTexture/skins/uiTexture.pack")));

    private TextButton exitButton = new TextButton("Back", skin);
    private TextButton removeAdsButton = new TextButton("Remove Ads", skin);
    private TextButton restorePurchasesButton = new TextButton("Restore Purchases", skin);
    private Label titleLabel = new Label("Version Info", skin);
    private Label versionLabel = new Label(Constants.VERSION_TEXT, skin);
    private Label adsEnabledLabel = null; // Disable for now, define later

    public VersionInfoScreen(HSGame game){
        this.game = game;
        background = new StillBackground(Constants.BACKGROUND_IMAGE_PATH);
    }
    @Override
    public void show() {
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenTransition transition = ScreenTransitionSlide.init(Constants.TRANSITION_DELAY, ScreenTransitionSlide.UP, true, Interpolation.pow3);
                game.setScreen(new AboutScreen(game), transition);
            }
        });
        removeAdsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.getActionResolver().initialiseDisableAdsPayment();
                refreshAdsEnabledLabel();
            }
        });
        restorePurchasesButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.getActionResolver().initialiseRestorePurchases();
            }
        });

        refreshAdsEnabledLabel();

        exitButton.getLabel().setFontScale(Constants.dimens.buttonTextFontScale);
        removeAdsButton.getLabel().setFontScale(Constants.dimens.buttonTextFontScale);
        restorePurchasesButton.getLabel().setFontScale(Constants.dimens.buttonTextFontScale);
        titleLabel.setFontScale(Constants.dimens.titleFontScale);
        versionLabel.setFontScale(Constants.dimens.textFontScale);
        adsEnabledLabel.setFontScale(Constants.dimens.textFontScale);

        table.add(titleLabel).padBottom(Constants.dimens.padding).row();
        table.add(versionLabel).padBottom(Constants.dimens.padding).row();
        //table.add(adsEnabledLabel).padBottom(Constants.dimens.padding).row(); // Not really necessary anymore...
        //table.add(removeAdsButton).size(Constants.dimens.buttonWidth, Constants.dimens.buttonHeight).padBottom(Constants.dimens.padding).row(); // TODO FIX THIS!!!
        //table.add(restorePurchasesButton).size(Constants.dimens.buttonWidth, Constants.dimens.buttonHeight).padBottom(Constants.dimens.padding).row();
        table.add(exitButton).size(Constants.dimens.buttonWidth, Constants.dimens.buttonHeight).padBottom(Constants.dimens.padding).row();

        table.setFillParent(true);
        stage.addActor(background);
        stage.addActor(table);

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                refreshAdsEnabledLabel();
            }
        }, 0.5f, 0.5f);
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

    private void refreshAdsEnabledLabel(){
        boolean adsEnabled = Gdx.app.getPreferences(Constants.AD_PREFERENCES_NAME).getBoolean(Constants.AD_PREFERENCES_ADS_ENABLED_KEY, true);
        if (adsEnabled){
            adsEnabledLabel = new Label("Ads enabled", skin);
        } else {
            adsEnabledLabel = new Label("Ads disabled", skin);
        }
    }
}
