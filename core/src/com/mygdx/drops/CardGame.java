package com.mygdx.drops;

import com.badlogic.gdx.Game;

public class CardGame extends Game {
    GameScreen gameScreen;

    @Override
    public void create() {
        gameScreen = new GameScreen();
        setScreen(gameScreen);
    }
}