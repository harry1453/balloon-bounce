package com.harrysoft.balloonbounce.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.harrysoft.balloonbounce.actors.Balloon;
import com.harrysoft.balloonbounce.actors.Bullet;
import com.harrysoft.balloonbounce.actors.Ceiling;
import com.harrysoft.balloonbounce.actors.Ground;
import com.harrysoft.balloonbounce.actors.Score;
import com.harrysoft.balloonbounce.actors.StillBackground;
import com.harrysoft.balloonbounce.actors.Trampoline;
import com.harrysoft.balloonbounce.actors.Tutorial;
import com.harrysoft.balloonbounce.enums.DifficultyType;
import com.harrysoft.balloonbounce.enums.UserDataType;
import com.harrysoft.balloonbounce.screens.GameOverScreen;
import com.harrysoft.balloonbounce.screens.MainMenu;
import com.harrysoft.balloonbounce.screens.transitions.ScreenTransition;
import com.harrysoft.balloonbounce.screens.transitions.ScreenTransitionFade;
import com.harrysoft.balloonbounce.utils.BodyUtils;
import com.harrysoft.balloonbounce.utils.BulletUtils;
import com.harrysoft.balloonbounce.utils.Constants;
import com.harrysoft.balloonbounce.utils.HSGame;
import com.harrysoft.balloonbounce.utils.WorldUtils;

public class GameStage extends Stage implements ContactListener {

    private static final int VIEWPORT_WIDTH = Constants.SCREEN_WIDTH;
    private static final int VIEWPORT_HEIGHT = Constants.SCREEN_HEIGHT;

    private HSGame game;

    private Task createBulletTask;

    private DifficultyType difficulty;

    private World world;
    private Ground ground;
    private Ceiling ceiling;
    private Balloon balloon;
    private Trampoline trampoline;
    private Score scoreActor;
    private TextButton exitButton;

    private boolean aboutToLeave = false;
    private boolean aboutToLeaveToMenu = false;

    private boolean exitButtonPressed = false;

    private final float TIME_STEP = 1 / 300f;
    private float accumulator = 0f;
    private int score = 0;

    private OrthographicCamera camera;
    private Vector3 touchPoint;

    private Rectangle screenRightSide;
    private Rectangle screenLeftSide;

    public GameStage(HSGame game, DifficultyType difficulty) {
        super(new ScalingViewport(Scaling.stretch, VIEWPORT_WIDTH, VIEWPORT_HEIGHT,
                new OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_HEIGHT)));
        this.difficulty = difficulty;
        setUpWorld();
        setUpCamera();
        setUpFixedMenu();
        setUpTutorial();
        setUpTouchControlAreas();
        this.game = game;
        createBulletTask = new Task() {
            @Override
            public void run() {
                createNewBullet();
                if (!aboutToLeave){
                    score++;
                    scoreActor.setScore(score);
                }
            }
        };
        Timer.schedule(createBulletTask
                , Constants.tutorial.TUTORIAL_DELAY + Constants.NEW_BULLET_DELAY     //    (delay)
                , Constants.NEW_BULLET_DELAY     //    (repeat exery x seconds)
        );
    }

    private void setUpWorld(){
        world = WorldUtils.createWorld();
        world.setContactListener(this);
        addActor(new StillBackground(Constants.BACKGROUND_IMAGE_PATH));
        setUpGround();
        setUpCeiling();
        setUpBalloon();
        setUpTrampoline();
    }

    private void setUpFixedMenu(){
        setUpScore();
        setUpExitButton();
    }

    private void setUpTutorial(){
        setUpLeftTutorial();
        setUpRightTutorial();
    }

    private void setUpLeftTutorial() {
        float width = getCamera().viewportHeight / 4;
        float x = getCamera().viewportWidth / 4 - width / 2;
        Rectangle leftTutorialBounds = new Rectangle(x, getCamera().viewportHeight * 9 / 20, width,
                width);
        addActor(new Tutorial(leftTutorialBounds, Constants.TRAMPOLINE_REGION_NAME,
                Constants.tutorial.TUTORIAL_LEFT_TEXT));
    }

    private void setUpRightTutorial() {
        float width = getCamera().viewportHeight / 4;
        float x = getCamera().viewportWidth * 3 / 4 - width / 2;
        Rectangle rightTutorialBounds = new Rectangle(x, getCamera().viewportHeight * 9 / 20, width,
                width);
        addActor(new Tutorial(rightTutorialBounds, Constants.BALLOON_ASCENDING_REGION_NAME,
                Constants.tutorial.TUTORIAL_RIGHT_TEXT));
    }

    private void setUpCamera() {
        camera = new OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0f);
        camera.setToOrtho(false, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        camera.update();
    }

    private void setUpGround(){
        ground = new Ground(WorldUtils.createGround(world));
        addActor(ground);
    }

    private void setUpCeiling(){
        ceiling = new Ceiling(WorldUtils.createCeiling(world));
        addActor(ceiling);
    }

    private void setUpBalloon(){
        balloon = new Balloon(WorldUtils.createBalloon(world));
        addActor(balloon);
    }

    private void setUpTrampoline(){
        trampoline = new Trampoline(WorldUtils.createTrampoline(world));
        addActor(trampoline);
    }

    private void createNewBullet(){
        Bullet bullet = BulletUtils.createBullet(world, difficulty);
        addActor(bullet);
    }

    @Override
    public void act(float delta){
        super.act(delta);
            Array<Body> bodies = new Array<Body>(world.getBodyCount());
            world.getBodies(bodies);
            // Fixed timestep
            accumulator += delta;

            for (Body body: bodies){
                update(body);
            }

            while (accumulator >= delta){
                world.step(TIME_STEP, 6, 2);
                accumulator -= TIME_STEP;
            }
    }

    public void update(Body body){
        if (!BodyUtils.bodyInBounds(body)) {
            removeBody(body);
        }
    }

    private void setUpTouchControlAreas(){
        touchPoint = new Vector3();
        screenLeftSide = new Rectangle(0, 0, getCamera().viewportWidth / 2, getCamera().viewportHeight);
        screenRightSide = new Rectangle(getCamera().viewportWidth / 2, 0, getCamera().viewportWidth / 2, getCamera().viewportHeight);
    }

    private void setUpScore() {
        Rectangle scoreBounds = new Rectangle(getCamera().viewportWidth * 47 / 64,
                getCamera().viewportHeight * 57 / 64, getCamera().viewportWidth / 4,
                getCamera().viewportHeight / 8);
        scoreActor = new Score(scoreBounds);
        addActor(scoreActor);
    }

    private void setUpExitButton(){
        //float x = getCamera().viewportHeight - (getCamera().viewportWidth * 47 / 64);
        //float y = getCamera().viewportHeight - (getCamera().viewportHeight * 57 / 64);
        //float width = getCamera().viewportHeight - (getCamera().viewportWidth / 4);
        //float height = getCamera().viewportHeight - (getCamera().viewportHeight / 8);
        float x = getCamera().viewportWidth - (getCamera().viewportWidth * 40 / 41.5f);
        float y = getCamera().viewportHeight * 57 / 69;
        float width = getCamera().viewportWidth / 14;
        float height = getCamera().viewportHeight / 10;
        Skin skin = new Skin(Gdx.files.internal("uiTexture/skins/uiSkinBigNew.json"), new TextureAtlas(Gdx.files.internal("uiTexture/skins/uiTexture.pack")));
        exitButton = new TextButton("Exit", skin);
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                exitButtonPressed = true;
                aboutToLeaveToMenu = true;
                ScreenTransition transition = ScreenTransitionFade.init(0.5f);
                game.setScreen(new MainMenu(game), transition);
            }
        });
        exitButton.getLabel().setFontScale(Constants.dimens.buttonTextFontScale * 0.75f);
        exitButton.getLabelCell().padBottom(Constants.dimens.buttonBottomPadding * 0.75f);
        //exitButton.setBounds(x, y, width, height);
        //skin.dispose();
        Table table = new Table();
        table.add(exitButton).size(width, height).row();
        table.setBounds(x, y, width, height);
        addActor(table);
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button){
        translateScreenToWorldCoordinates(x, y);

        if (screenLeftSide.contains(touchPoint.x, touchPoint.y) && !exitButtonPressed){
            if (!exitButtonPressed){
                balloon.fall();
            }
        }

        if (screenRightSide.contains(touchPoint.x, touchPoint.y)){
            balloon.ascend();
        }

        return super.touchDown(x, y, pointer, button);
    }

    @Override
    public boolean touchUp(int x, int y, int pointer, int button){
        translateScreenToWorldCoordinates(x, y);

        if (screenRightSide.contains(touchPoint.x, touchPoint.y)){
            balloon.hover();
        }

        return super.touchUp(x, y, pointer, button);
    }

    private void translateScreenToWorldCoordinates(int x, int y) {
        getCamera().unproject(touchPoint.set(x, y, 0));
    }

    @Override
    public void beginContact(Contact contact) {
        Body a = contact.getFixtureA().getBody();
        Body b = contact.getFixtureB().getBody();

        if (BodyUtils.VerifyCollision(a, b, UserDataType.BALLOON, UserDataType.CEILING)) {
            balloon.hitCeiling();
        } else if (BodyUtils.VerifyCollision(a, b, UserDataType.BALLOON, UserDataType.TRAMPOLINE)) {
            balloon.bounce();
        } else if (BodyUtils.VerifyCollision(a, b, UserDataType.BALLOON, UserDataType.BULLET)){
            if (!aboutToLeave) {
                aboutToLeave = true;
                balloon.hitByBullet();
                Gdx.input.setInputProcessor(null); // Stop taking input
                Timer.schedule(new Task() {
                    @Override
                    public void run() {
                        launchGameOverScreen();
                    }
                }, Constants.GAME_OVER_DELAY);
            }
        }
    }

    private void launchGameOverScreen() {
        if (!aboutToLeaveToMenu){
            Preferences prefs = Gdx.app.getPreferences(Constants.TEMP_PREFERENCES_NAME);
            prefs.putInteger(Constants.TEMP_PREFERENCES_KEY_SCORE, score);
            prefs.flush();
            ScreenTransition transition = ScreenTransitionFade.init(0.75f);
            game.setScreen(new GameOverScreen(game, difficulty), transition);
        }
    }

    @Override
    public void endContact(Contact contact) {
        Body a = contact.getFixtureA().getBody();
        Body b = contact.getFixtureB().getBody();

        if (BodyUtils.VerifyCollision(a, b, UserDataType.BALLOON, UserDataType.CEILING)){
            balloon.noLongerOnCeiling();
        }

        if (BodyUtils.VerifyCollision(a, b, UserDataType.BALLOON, UserDataType.TRAMPOLINE)){
            balloon.noLongerOnTrampoline();
        }

        if (BodyUtils.VerifyCollision(a, b, UserDataType.BALLOON, UserDataType.BULLET)){
            balloon.noLongerTouchingBullet();
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    /*@Override
    public void dispose(){
        world.clearForces();
        world.setContactListener(null);
        Array<Body> bodies = new Array<Body>(world.getBodyCount());
        world.getBodies(bodies);
        for (Body body: bodies){
            removeBody(body);
        }
        bodies.clear();
        world.dispose();
        renderer.dispose();
        clear();
        getBatch().dispose();
        super.dispose();
    }*/

    private void removeBody(Body body){
        body.setActive(false);
        world.destroyBody(body);
    }

    public void pause(){
        createBulletTask.cancel();
    }

    public void resume(){
        Timer.schedule(createBulletTask
                , Constants.NEW_BULLET_DELAY     //    (delay)
                , Constants.NEW_BULLET_DELAY     //    (repeat exery x seconds)
        );
    }
}
