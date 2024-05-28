package entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.Input.Keys;

public class Player extends Sprite implements InputProcessor {

    private Vector2 velocity = new Vector2();
    private float speed = 700;
    private float moveTime = 0.2f;
    private float timeSiceLastMove = 0;
    private TiledMapTileLayer collisionLayer;

    public Player(Sprite sprite, TiledMapTileLayer collisionLayer) {
        super(sprite);
        this.collisionLayer = collisionLayer;
    }

    @Override
    public void draw(Batch batch) {
        update(Gdx.graphics.getDeltaTime());
        super.draw(batch);
    }

    public void update(float delta) {
        float oldX = getX(), oldY = getY(), tileWidth = collisionLayer.getTileWidth(), tileHeight = collisionLayer.getTileHeight();
        int pixelsToMove = (int) (speed * delta);

        for (int i = 0; i < pixelsToMove; i++) {
            if (velocity.x != 0) {
                setX(getX() + Math.signum(velocity.x));

                boolean collisionX;
                if (velocity.x < 0) {
                    collisionX = collisionLayer.getCell((int) ((getX()) / tileWidth), (int) ((getY() + getHeight() / 2) / tileHeight)).getTile().getProperties().containsKey("blocked");
                } else {
                    collisionX = collisionLayer.getCell((int) ((getX() + getWidth()) / tileWidth), (int) ((getY() + getHeight() / 2) / tileHeight)).getTile().getProperties().containsKey("blocked");
                }

                if (collisionX) {
                    setX(oldX);
                    velocity.x = 0;
                    break;
                }

                oldX = getX();
            }
            if (velocity.y != 0) {
                setY(getY() + Math.signum(velocity.y));

                boolean collisionY;
                if (velocity.y < 0) {
                    collisionY = collisionLayer.getCell((int) ((getX() + getWidth() / 2) / tileWidth), (int) ((getY()) / tileHeight)).getTile().getProperties().containsKey("blocked");
                } else {
                    collisionY = collisionLayer.getCell((int) ((getX() + getWidth() / 2) / tileWidth), (int) ((getY() + getHeight()) / tileHeight)).getTile().getProperties().containsKey("blocked");
                }

                if (collisionY) {
                    setY(oldY);
                    velocity.y = 0;
                    break;
                }

                oldY = getY();
            }
        }
    }
    //TODO when the player is moving you can't press any other keys
    @Override
    public boolean keyDown(int keycode) {
        switch (keycode){
            case Keys.W:
                velocity.y = speed;
                break;
            case Keys.A:
                velocity.x = -speed;
                checkCollision();
                break;
            case Keys.S:
                velocity.y = -speed;
                break;
            case Keys.D:
                velocity.x = speed;
                break;
        }
        return true;
    }
    private boolean isTileBlocked(float x, float y) {
        TiledMapTileLayer.Cell cell = collisionLayer.getCell((int) (x / collisionLayer.getTileWidth()), (int) (y / collisionLayer.getTileHeight()));
        return cell != null && cell.getTile().getProperties().containsKey("blocked");
    }

    private void checkCollision() {
        float tileWidth = collisionLayer.getTileWidth();
        float tileHeight = collisionLayer.getTileHeight();

        // Check the tile that is right next to the player
        boolean nextRightTileBlocked = collisionLayer.getCell((int) ((getX() + getWidth() + 8) / tileWidth), (int) ((getY() + getHeight() / 2) / tileHeight)).getTile().getProperties().containsKey("blocked");
        boolean nextLeftTileBlocked = collisionLayer.getCell((int) ((getX() - 8) / tileWidth), (int) ((getY() + getHeight() / 2) / tileHeight)).getTile().getProperties().containsKey("blocked");

        // If the next right tile is not blocked, move the player one pixel to the right
        if (!nextRightTileBlocked) {
            for (int i = 0; i < 8; i++) {
                nextRightTileBlocked = collisionLayer.getCell((int) ((getX() + getWidth() + 1) / tileWidth), (int) ((getY() + getHeight() / 2) / tileHeight)).getTile().getProperties().containsKey("blocked");
                setX(getX() + 1);
                if (nextRightTileBlocked) {
                    break;
                }
            }
        }

        // If the next left tile is not blocked, move the player one pixel to the left
        if (!nextLeftTileBlocked) {
            for (int i = 0; i < 8; i++) {
                nextLeftTileBlocked = collisionLayer.getCell((int) ((getX() - 1) / tileWidth), (int) ((getY() + getHeight() / 2) / tileHeight)).getTile().getProperties().containsKey("blocked");
                setX(getX() - 1);
                if (nextLeftTileBlocked) {
                    break;
                }
            }
        }
    }
    private float lerp(float point1, float point2, float alpha) {
        return point1 + alpha * (point2 - point1);
    }

    @Override
    public boolean keyUp(int keycode) {
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public TiledMapTileLayer getCollisionLayer() {
        return collisionLayer;
    }

    public void setCollisionLayer(TiledMapTileLayer collisionLayer) {
        this.collisionLayer = collisionLayer;
    }
}