package com.mygdx.drops;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

public class CardGame extends Game {
    private String[] arg;
    private Screen screen;

    public CardGame() {
        this(new String[]{});
    }

    public CardGame(String[] arg) {
        this.arg = arg;
    }

    @Override
    public void create() {
        if(arg.length == 1 && arg[0].equals("server")) {
            screen = new GameScreen(true);
        } else if(arg.length == 1 && arg[0].equals("client")) {
            screen = new GameScreen(false);
        } else {
            screen = new MenuScreen(this);
        }
        setScreen(screen);
    }
}