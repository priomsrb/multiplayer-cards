package com.mygdx.drops;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.utils.Array;

import java.util.Comparator;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import static java.lang.Thread.sleep;

/**
 * Created by Shafqat on 8/08/2014.
 */
public class CardController {
    GameScreen screen;
    Comparator<Card> cardComparator;
    private final float DEFAULT_CARD_SPACING = 40;

    public CardController(GameScreen screen) {
        this.screen = screen;
        cardComparator = new CardScumRankComparator();
    }

    public void dealCards(CardIdList cardIds) {
        for(int i = 0; i < screen.cards.size; i++) {
            Card card = screen.cards.get(i);
            // We reveal the cards in hand during the animation
            card.setVisible(false);
            card.state = CardState.HIDDEN;
        }
        screen.cardsInHand.clear();
        System.out.println("I was dealt: ");
        for(int i = 0; i < cardIds.size(); i++) {
            System.out.print(cardIds.get(i) + " ");
            Card card = screen.cards.get(cardIds.get(i));
            card.moveToCenter();
            screen.cardsInHand.add(card);
            card.state = CardState.IN_HAND;
        }
        System.out.println();
        screen.cardController.animateCardDealing();
    }

    private void animateCardDealing() {
        Array<Card> cardsInHand = screen.cardsInHand;
        if(cardsInHand.size == 0) {
            return;
        }
        cardsInHand.sort(cardComparator);
        for(int i = 0; i < cardsInHand.size; i++) {
            float x = getCardXPositionInHand(i);
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

    public void arrangeCardsInHand() {
        Array<Card> cardsInHand = screen.cardsInHand;
        if(cardsInHand.size == 0) {
            return;
        }
        cardsInHand.sort(cardComparator);
        for(int i = 0; i < cardsInHand.size; i++) {
            Card card = cardsInHand.get(i);
            // Don't arrange cards that are currently being dragged
            if(card.touchedBy == -1) {
                float x = getCardXPositionInHand(i);
                float y = 100;
                MoveToAction moveToAction = new MoveToAction();
                moveToAction.setPosition(x, y);
                moveToAction.setDuration(0.3f);
                moveToAction.setInterpolation(Interpolation.pow2Out);
                card.addAction(moveToAction);
                card.toFront();
            }
        }
    }

    private float getCardXPositionInHand(int index) {
        Array<Card> cardsInHand = screen.cardsInHand;
        float cardWidth = cardsInHand.get(0).getWidth();
        float cardSpacing = DEFAULT_CARD_SPACING / (cardsInHand.size/13.0f);
        float startX = screen.stage.getWidth() / 2.0f - (cardsInHand.size - 1) / 2.0f * cardSpacing - cardWidth/2.0f;
        float x = startX + index * cardSpacing;
        return x;
    }

    public void cardMovedToOpponentHand(int cardId) {
        Card card = screen.cards.get(cardId);
        card.state = CardState.HIDDEN;
        float x = screen.stage.getWidth() / 2 - card.getWidth() / 2;
        float y = screen.stage.getHeight() + card.getHeight();
        MoveToAction moveToAction = new MoveToAction();
        moveToAction.setPosition(x, y);
        moveToAction.setDuration(0.3f);
        moveToAction.setInterpolation(Interpolation.pow2Out);
        card.addAction(sequence(moveToAction, hide()));
    }

    public void discardCards() {
        Array<Card> cardsInPlay = new Array<Card>();
        for(int i = 0; i < screen.cards.size; i++) {
            Card card = screen.cards.get(i);
            if(card.state == CardState.IN_PLAY) {
                cardsInPlay.add(card);
            }
        }

        cardsInPlay.sort(new Comparator<Card>() {
            // Sort by descending z-index
            @Override
            public int compare(Card o1, Card o2) {
                if(o1.getZIndex() < o2.getZIndex()) {
                    return 1;
                } else if(o1.getZIndex() == o2.getZIndex()) {
                    return 0;
                } else {
                    return -1;
                }
            }
        });

        float destX = screen.stage.getWidth();
        float destY = screen.stage.getHeight() / 2;
        for(int i = 0; i < cardsInPlay.size; i++) {
            Card card = cardsInPlay.get(i);
            card.state = CardState.HIDDEN;
            MoveToAction moveToAction = new MoveToAction();
            moveToAction.setPosition(destX, destY);
            moveToAction.setDuration(0.3f);
            moveToAction.setInterpolation(Interpolation.pow2Out);
            card.addAction(sequence(delay(i * 0.05f), moveToAction, hide()));
        }
    }
}
