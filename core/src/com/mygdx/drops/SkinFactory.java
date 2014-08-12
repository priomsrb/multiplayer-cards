package com.mygdx.drops;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

/**
 * Created by Shafqat on 3/08/2014.
 */
public class SkinFactory {
    private static Skin skin;

    public static Skin getSkin() {
        if(skin == null) {
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
            textButtonStyle.down = skin.newDrawable("white", Color.GRAY);
            textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
            textButtonStyle.font = skin.getFont("default");
            textButtonStyle.font.setScale(3);
            textButtonStyle.pressedOffsetX = 3;
            textButtonStyle.pressedOffsetY = -3;
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
        return skin;
    }

    public static void dispose() {
        skin.dispose();
        skin = null;
    }
}
