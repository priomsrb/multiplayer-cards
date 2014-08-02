package com.mygdx.drops;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;

/**
 * Created by Shafqat on 28/07/2014.
 */
public class Card extends Actor {
    public int id;
    public static Texture baseTexture;
    public final int rank;
    public final int suit;
    public TextureRegion texture;
    public Vector2 touchPos;
    public int touchedBy;
    public GameClient client;

    public Card(int suit, int rank, float x, float y) {
        this.suit = suit;
        this.rank = rank;
        if(baseTexture == null) {
            baseTexture = new Texture(Gdx.files.internal("cards.png"));
        }
        texture = new TextureRegion(baseTexture, 150*(rank-1), 218*(suit-1), 150, 218);
        touchPos = new Vector2();
        touchedBy = -1;
        setBounds(x, y, texture.getRegionWidth(), texture.getRegionHeight());

        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Card card = Card.this;
                card.touchPos.set(x, y);
                card.touchedBy = pointer;
                card.toFront();
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Card card = Card.this;
                if (card.touchedBy == pointer) {
                    card.touchedBy = -1;
                    client.sendMessage(new CardMoved(card.id, card.getX(), card.getY()));
                }
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                Card card = Card.this;
                if (card.touchedBy == pointer) {
                    card.moveBy(x - card.touchPos.x,
                                y - card.touchPos.y);
                    client.sendUdpMessage(new CardMoved(card.id, card.getX(), card.getY()));
                }
            }
        });
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        batch.draw(texture, getX(), getY(), getOriginX(), getOriginY(),
                getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }

    public void receivedMessage(Message message) {
        if(message instanceof CardMoved) {
            CardMoved cardMoved = (CardMoved)message;
            System.out.println(cardMoved);
            MoveToAction action = new MoveToAction();
            action.setPosition(cardMoved.x, cardMoved.y);
            action.setDuration(0.3f);
            action.setInterpolation(Interpolation.pow2Out);
            addAction(action);
        }
    }
}
