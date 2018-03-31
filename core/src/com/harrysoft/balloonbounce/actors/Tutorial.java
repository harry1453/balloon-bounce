package com.harrysoft.balloonbounce.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.utils.Align;
import com.harrysoft.balloonbounce.utils.Constants;
import com.harrysoft.balloonbounce.utils.FontUtils;

public class Tutorial extends Actor {

    private TextureRegion textureRegion;
    private Rectangle bounds;
    private BitmapFont font;
    private String text;

    public Tutorial(Rectangle bounds, String assetsId, String text) {
        this.bounds = bounds;
        this.text = text;
        TextureAtlas textureAtlas = new TextureAtlas(Constants.OBJECTS_ATLAS_PATH);
        textureRegion = textureAtlas.findRegion(assetsId);
        SequenceAction sequenceAction = new SequenceAction();
        sequenceAction.addAction(Actions.fadeOut(0.5f, Interpolation.fade));
        sequenceAction.addAction(Actions.delay(Constants.tutorial.TUTORIAL_DELAY));
        sequenceAction.addAction(Actions.fadeOut(0.5f, Interpolation.fade));
        sequenceAction.addAction(Actions.removeActor());
        addAction(sequenceAction);
        font = FontUtils.getSmallestFont();
        font.setColor(Color.GREEN);
        setWidth(bounds.width);
        setHeight(bounds.height);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(textureRegion, bounds.x, bounds.y, bounds.width, bounds.height);
        font.draw(batch, text, bounds.x, bounds.y, bounds.width, Align.center, true);
    }
}
