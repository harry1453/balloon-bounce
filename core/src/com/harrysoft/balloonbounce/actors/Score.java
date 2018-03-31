package com.harrysoft.balloonbounce.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import com.harrysoft.balloonbounce.utils.FontUtils;

public class Score extends Actor {

    private int score;
    private Rectangle bounds;
    private BitmapFont font;

    public Score(Rectangle bounds) {
        this.bounds = bounds;
        setWidth(bounds.width);
        setHeight(bounds.height);
        score = 0;
        font = FontUtils.getSmallFont();
        font.setColor(Color.GREEN);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        font.draw(batch, Integer.toString(score), bounds.x, bounds.y, bounds.getWidth(), Align.center, false);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int newScore){
        score = newScore;
    }

}
