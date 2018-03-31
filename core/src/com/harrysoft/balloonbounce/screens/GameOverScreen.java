package com.harrysoft.balloonbounce.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
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
import com.harrysoft.balloonbounce.screens.transitions.ScreenTransitionFade;
import com.harrysoft.balloonbounce.screens.transitions.ScreenTransitionSlide;
import com.harrysoft.balloonbounce.stages.OrthStage;
import com.harrysoft.balloonbounce.utils.Constants;
import com.harrysoft.balloonbounce.utils.HSGame;

public class GameOverScreen extends HSScreen {

    private HSGame game;

    private OrthStage stage = new OrthStage();
    private Table table = new Table();
    private StillBackground background;

    private DifficultyType difficulty;

    private String highscoreKey;

    private Skin skin = new Skin(Gdx.files.internal("uiTexture/skins/uiSkinBigNew.json"), new TextureAtlas(Gdx.files.internal("uiTexture/skins/uiTexture.pack")));

    private TextButton playButton = new TextButton("Play again", skin), exitButton = new TextButton("Main menu", skin);
    private Label title = new Label("Game over!", skin), scoreLabel, highscoreLabel, newHighscoreLabel;

    public GameOverScreen(HSGame game, DifficultyType difficulty){
        this.game = game;
        this.difficulty = difficulty;
        background = new StillBackground(Constants.BACKGROUND_IMAGE_PATH);
        switch (difficulty){
            case EASY:
                highscoreKey = Constants.SCORE_DATA_EASY_HIGHSCORE_KEY;
                break;
            case NORMAL:
                highscoreKey = Constants.SCORE_DATA_NORMAL_HIGHSCORE_KEY;
                break;
            case HARD:
                highscoreKey = Constants.SCORE_DATA_HARD_HIGHSCORE_KEY;
                break;
            case REALISTIC:
                highscoreKey = Constants.SCORE_DATA_REALISTIC_HIGHSCORE_KEY;
                break;
            default:
                highscoreKey = "";
                throw new NullPointerException("Could not determine difficulty from GameOverScreen");
        }
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
        showAdIfNeeded(); // Start loading the ad asap
        boolean newHighscore;
        Preferences prefs = Gdx.app.getPreferences(Constants.TEMP_PREFERENCES_NAME);
        int score = prefs.getInteger(Constants.TEMP_PREFERENCES_KEY_SCORE);
        prefs.remove(Constants.TEMP_PREFERENCES_KEY_SCORE);
        prefs = Gdx.app.getPreferences(Constants.SCORE_DATA_NAME);
        int highscore = prefs.getInteger(highscoreKey);
        if (score > highscore){
            newHighscore = true;
            highscore = score;
            prefs.putInteger(highscoreKey, highscore).flush();
            newHighscoreLabel = new Label("New Highscore!", skin);
        } else {
            newHighscore = false;
        }
        highscoreLabel = new Label("Highscore: " + Integer.toString(highscore), skin);
        scoreLabel = new Label("Score: " + Integer.toString(score), skin);
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.getActionResolver().cancelShowingInterstitial();
                ScreenTransition transition = ScreenTransitionFade.init(Constants.TRANSITION_DELAY);
                game.setScreen(new GameScreen(game, difficulty), transition);
                //game.setScreen(new GameScreen(game, difficulty), transition);
            }
        });
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.getActionResolver().cancelShowingInterstitial();
                ScreenTransition transition = ScreenTransitionSlide.init(Constants.TRANSITION_DELAY, ScreenTransitionSlide.RIGHT, true, Interpolation.pow3);
                game.setScreen(new MainMenu(game), transition);
            }
        });

        title.setFontScale(Constants.dimens.titleFontScale);
        scoreLabel.setFontScale(Constants.dimens.textFontScale);
        highscoreLabel.setFontScale(Constants.dimens.textFontScale);
        playButton.getLabel().setFontScale(Constants.dimens.buttonTextFontScale);
        playButton.getLabelCell().padBottom(Constants.dimens.buttonBottomPadding);
        exitButton.getLabel().setFontScale(Constants.dimens.buttonTextFontScale);
        exitButton.getLabelCell().padBottom(Constants.dimens.buttonBottomPadding);

        table.add(title).padBottom(Constants.dimens.padding * 1.5f).row();
        table.add(scoreLabel).padBottom(Constants.dimens.padding).row();
        table.add(highscoreLabel).padBottom(Constants.dimens.padding).row();
        if (newHighscore){
            newHighscoreLabel.setFontScale(Constants.dimens.textFontScale);
            table.add(newHighscoreLabel).padBottom(Constants.dimens.padding).row();
        }
        table.add(playButton).size(Constants.dimens.buttonWidth, Constants.dimens.buttonHeight).padBottom(Constants.dimens.padding).row();
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
        /*Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                table.clear();
                table.clearChildren();
                background.clear();
                background.clearListeners();
                stage.unfocusAll();
                stage.dispose();
                skin.dispose();
            }

        }, 1);*/
        super.dispose();
        skin.dispose();
    }

    @Override
    public InputProcessor getInputProcessor() {
        return stage;
    }

    private void showAdIfNeeded(){
        game.increaseAdvertCounter();
        if (game.getAdvertCounter() == Constants.ADVERTCOUNTER_LIMIT_BEFORE_AD) // if the advert counter is at 5, load an ad!
        {
            //game.getActionResolver().showOrLoadInterstitialIfEnabled();
            Preferences prefs = Gdx.app.getPreferences(Constants.AD_PREFERENCES_NAME);
            boolean adsEnabled = prefs.getBoolean(Constants.AD_PREFERENCES_ADS_ENABLED_KEY, true);
            if (adsEnabled) {
                game.getActionResolver().showOrLoadInterstitial();
            }
            game.setAdvertCounter(0);

        }
    }
}