package com.mygdx.drops;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;

/**
 * Created by Shafqat on 29/07/2014.
 */
public class GameScreen implements Screen {
    public Array<Card> cards;
    private Stage stage;

    public GameScreen() {
        stage = new Stage(new StretchViewport(768, 1280));
        cards = new Array<Card>();
        for(int i = 0; i < 13; i++) {
            Card card = new Card(2, i + 1, 60 + i * 40, 100);
            cards.add(card);
            stage.addActor(card);
        }

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void show() {

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
        if (Card.baseTexture != null) {
            Card.baseTexture.dispose();
        }
        stage.dispose();
    }
}
