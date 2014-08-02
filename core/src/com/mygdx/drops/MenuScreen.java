package com.mygdx.drops;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import java.io.IOException;

/**
 * Created by Shafqat on 29/07/2014.
 */
public class MenuScreen implements Screen {
    private Game game;
    private Stage stage;
    Skin skin;

    public MenuScreen(Game game) {
        this.game = game;
        stage = new Stage(new StretchViewport(768, 1280));
        Gdx.input.setInputProcessor(stage);

        setupSkin();

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
        ClientServerHelper.host = "192.168.43.1";
        final TextField ipAddressField = new TextField(ClientServerHelper.host, skin);
        table.add(ipAddressField).width(350);

        hostButton.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                ClientServerHelper.host = "127.0.0.1";
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

    private void setupSkin() {
        skin = new Skin();

        // Generate a 1x1 white texture and store it in the skin named "white".
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("white", new Texture(pixmap));

        // Store the default libgdx font under the name "default".
        skin.add("default", new BitmapFont());

        // Configure a TextButtonStyle and name it "default". Skin resources are stored by type, so this doesn't overwrite the font.
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
        textButtonStyle.font = skin.getFont("default");
        textButtonStyle.font.setScale(3);
        skin.add("default", textButtonStyle);

        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.background = skin.newDrawable("white", Color.DARK_GRAY);
        textFieldStyle.focusedBackground = skin.newDrawable("white", Color.GRAY);
        textFieldStyle.cursor = skin.newDrawable("white", Color.BLUE);
        textFieldStyle.selection = skin.newDrawable("white", Color.BLUE);
        textFieldStyle.fontColor = Color.WHITE;
        textFieldStyle.font = skin.getFont("default");
        textFieldStyle.font.setScale(3);
        skin.add("default", textFieldStyle);
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
        skin.dispose();
    }
}
