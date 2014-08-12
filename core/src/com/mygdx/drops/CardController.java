package com.mygdx.drops;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.utils.Array;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.show;

/**
 * Created by Shafqat on 8/08/2014.
 */
public class CardController {
    GameScreen screen;
    public Array<Card> cardsInHand;
    private final float CARD_SPACING = 40;

    public CardController(GameScreen screen) {
        this.screen = screen;
    }

    public void dealCards(CardIdList cardIds) {
        for(int i = 0; i < screen.cards.size; i++) {
            Card card = screen.cards.get(i);
            card.setVisible(false);
        }
        screen.cardsInHand.clear();
        System.out.println("I was dealt: ");
        for(int i = 0; i < cardIds.size(); i++) {
            System.out.print(cardIds.get(i) + " ");
            Card card = screen.cards.get(cardIds.get(i));
            card.moveToCenter();
//            card.setVisible(true);
            screen.cardsInHand.add(card);
        }
        System.out.println();
        screen.cardController.arrangeCardsInHand();
    }

    public void arrangeCardsInHand() {
        cardsInHand = screen.cardsInHand;
        if(cardsInHand.size == 0) {
            return;
        }
        cardsInHand.sort();
        float cardWidth = cardsInHand.get(0).getWidth();
        float startX = screen.stage.getWidth() / 2.0f - cardsInHand.size / 2.0f * CARD_SPACING - cardWidth/2.0f;
        for(int i = 0; i < cardsInHand.size; i++) {
            float x = startX + i * CARD_SPACING;
            float y = 100;
            MoveToAction moveToAction = new MoveToAction();
            moveToAction.setPosition(x, y);
            moveToAction.setDuration(0.3f);
            moveToAction.setInterpolation(Interpolation.pow2Out);
            Action action = sequence(delay(i * 0.1f), show(), moveToAction);
            cardsInHand.get(i).addAction(action);
            cardsInHand.get(i).toFront();
        }
    }

}
