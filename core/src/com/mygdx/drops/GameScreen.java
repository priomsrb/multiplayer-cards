package com.mygdx.drops;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.drops.Messages.RequestShuffle;

import java.io.IOException;

/**
 * Created by Shafqat on 29/07/2014.
 */
public class GameScreen implements Screen {
    public Array<Card> cards;
    public Array<Card> cardsInHand;
    public Stage stage;
    public GameClient client;
    public GameServer server;
    public CardController cardController;

    public GameScreen(boolean isServer) {
        try {
            if(isServer) {
                server = new GameServer(this);
            }
            client = new GameClient(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        stage = new Stage(new StretchViewport(768, 1280));
        Gdx.input.setInputProcessor(stage);

        setupCards();
        setupGui();
    }

    private void setupCards() {
        cards = new Array<Card>();
        cardsInHand = new Array<Card>();
        for(int i = 0; i < 26; i++) {
            int suit = i / 13 + 1;
            int rank = i % 13 + 1;
            Card card = new Card(suit, rank, 60 + i * 40, 100);
            card.id = i;
            card.client = client;
            card.setVisible(false);
            cards.add(card);
            stage.addActor(card);
        }
        cardController = new CardController(this);
    }

    private void setupGui() {
        Skin skin = SkinFactory.getSkin();

        Table table = new Table();
        table.debug(); // turn on all debug lines (table, cell, and widget)
        table.setFillParent(true);
        table.top();
        stage.addActor(table);

        TextButton shuffleButton = new TextButton("Shuffle", skin);
        table.add(shuffleButton).pad(50);

        shuffleButton.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                requestShuffle();
            }
        });
    }

    public void requestShuffle() {
        client.sendMessage(new RequestShuffle());
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
        SkinFactory.dispose();
    }

}
