package entity;

import game.AbstractStage;
import game.CollisionHandler;
import game.KeyPressHandler;
import interactable.ParentInteractable;
import util.Constants;
import util.Pair;

import java.awt.*;
import java.util.Arrays;

public class Player extends Entity {

    KeyPressHandler keyPressHandler;
    public CollisionHandler collisionHandler;
    AbstractStage stage;

    private final int screenCenterX;
    private final int screenCenterY;



    public Player(KeyPressHandler keyPressHandler, CollisionHandler collisionHandler, AbstractStage stage, int screenCenterX, int screenCenterY) {
        super(8, 32); // animation speed % total frames === 0 for smooth animations
        this.keyPressHandler = keyPressHandler;
        this.collisionHandler = collisionHandler;
        this.stage = stage;

        super.setX(3 * Constants.TILE_SIZE);
        super.setY(3 * Constants.TILE_SIZE);
        super.setWidth(Constants.TILE_SIZE);
        super.setHeight(Constants.TILE_SIZE);
        super.setSpeed(8);
        this.screenCenterX = screenCenterX - (Constants.TILE_SIZE / 2);
        this.screenCenterY = screenCenterY - (Constants.TILE_SIZE / 2);

        super.readSpriteImages("./assets/cat/", ".png");
        super.setHitBox(new Rectangle(20, 30, 20, 20));

    }

    public void drawPlayer(Graphics2D graphics2D) {
        // player is always in the center of the screen
        super.draw(graphics2D, this.screenCenterX, this.screenCenterY);
    }

    public void update() {
        // TICK SPRITE FRAME
        if (super.getFrameCounter() < super.getSpriteAnimationSpeed()) {
            super.setFrameCounter(super.getFrameCounter() + 1);
        } else {
            super.setFrameCounter(0);
        }
        super.setPreviousState(super.getCurrentState());

        // Item interaction

        for (int i = 0; i < stage.items.length; i++) {
            if (this.collisionHandler.isPlayerOnTop(this, stage.items[i])) {
                this.handleItemCollision(i);
            }
        }

        // MOVEMENT
        if (keyPressHandler.isMovementKeyPressed()) {
            if (keyPressHandler.rightPressed) {
                super.setDirection(Direction.RIGHT);
            }
            if (keyPressHandler.leftPressed) {
                super.setDirection(Direction.LEFT);
            }
            if (keyPressHandler.upPressed) {
                super.setDirection(Direction.UP);
            }
            if (keyPressHandler.downPressed) {
                super.setDirection(Direction.DOWN);
            }
            super.setCurrentState(State.WALK);

        } else {
            super.setCurrentState(State.IDLE);
        }


        int changeX = 0;
        int changeY = 0;
        if (keyPressHandler.rightPressed) {
            if (!this.collisionHandler.checkRightWallCollision(this) && !this.collisionHandler.checkRightItemCollision(this, true)) {
                changeX = changeX + super.getSpeed();
            }
        }
        if (keyPressHandler.leftPressed) {
            if (!this.collisionHandler.checkLeftWallCollision(this) && !this.collisionHandler.checkLeftItemCollision(this, true)) {
                changeX = changeX - super.getSpeed();
            }
        }

        if (keyPressHandler.upPressed) {
            if (!this.collisionHandler.checkUpCollision(this) && !this.collisionHandler.checkUpItemCollision(this, true)) {
                changeY = changeY - super.getSpeed();
            }
        }

        if (keyPressHandler.downPressed) {
            if (!this.collisionHandler.checkDownCollision(this) && !this.collisionHandler.checkDownItemCollision(this, true)) {
                changeY = changeY + super.getSpeed();
            }
        }

        // Normalize Movement
        if (changeX != 0 && changeY == 0) super.setX(super.getX() + changeX);
        if (changeX == 0 && changeY != 0) super.setY(super.getY() + changeY);
        if (changeX != 0 && changeY != 0) {
            // divide by 1.414 because it's a 45-45-90 triangle
            // angled movement is still a little faster since we round up for integers
            super.setX(super.getX() + (int) Math.ceil(changeX / 1.414));
            super.setY(super.getY() + (int) Math.ceil(changeY / 1.414));
        }

    }

    private void handleItemCollision(int index) {
        if (keyPressHandler.interactPressed) {
            this.stage.items[index].handleInteraction();
            this.stage.items[index] = null;
        }

    }

    // Needed for drawing of tiles
    public int getScreenCenterX() { return screenCenterX; }
    public int getScreenCenterY() { return screenCenterY; }

}
