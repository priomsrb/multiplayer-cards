package com.mygdx.drops;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;

/**
 * Created by Shafqat on 29/07/2014.
 */
public class MenuScreen implements Screen {
    private Game game;
    private Stage stage;

    public MenuScreen(Game game) {
        this.game = game;
        stage = new Stage(new StretchViewport(768, 1280));
        Gdx.input.setInputProcessor(stage);

        setupGui();
    }

    private void setupGui() {
        Skin skin = SkinFactory.getSkin();

        Table table = new Table();
        table.debug(); // turn on all debug lines (table, cell, and widget)
        table.setFillParent(true);
        table.top();
        stage.addActor(table);

        TextButton hostButton = new TextButton("Host Game", skin);
        table.add(hostButton).pad(100);
        table.row();

        TextButton joinButton = new TextButton("Join Game", skin);
        table.add(joinButton);
        table.row().pad(10);
        ClientServerHelper.host = "127.0.0.1";
        final TextField ipAddressField = new TextField(ClientServerHelper.host, skin);
        table.add(ipAddressField).width(350);

        Array<String> myIps = ClientServerHelper.getMyIpAddresses();
        for(int i = 0; i < myIps.size; i++) {
            table.row().pad(10);
            String text;
            if(i == 0) {
                text = "My IP: " + myIps.get(i);
            } else {
                text = "My IP #" + (i + 1)  + ": " + myIps.get(i);
            }
            table.add(new Label(text, skin));
        }

        hostButton.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
//                ClientServerHelper.host = "127.0.0.1";
                ClientServerHelper.host = ipAddressField.getText();
                MenuScreen.this.game.setScreen(new GameScreen(true));
            }
        });

        joinButton.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                ClientServerHelper.host = ipAddressField.getText();
                MenuScreen.this.game.setScreen(new GameScreen(false));
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
//        Table.drawDebug(stage); // draw any enabled debug lines
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
        stage.dispose();
        SkinFactory.dispose();
    }
}
