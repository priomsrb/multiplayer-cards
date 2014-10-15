package com.mygdx.drops;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by Shafqat on 12/08/2014.
 */
public class Zone extends Actor {
    public static Texture baseTexture;

    public Zone(float x, float y, float width, float height) {
        setBounds(x, y, width, height);
        if(baseTexture == null) {
            baseTexture = new Texture(Gdx.files.internal("zone.png"));
        }
    }

    public boolean contains(float x, float y) {
        if(x >= getX() && x <= getX() + getWidth() &&
                y >= getY() && y <= getY() + getHeight()) {
            return true;
        }
        return false;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        batch.draw(baseTexture, getX(), getY(), getOriginX(), getOriginY(),
                getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation(), 0, 0, baseTexture.getWidth(), baseTexture.getHeight(), false, false);
    }
}
