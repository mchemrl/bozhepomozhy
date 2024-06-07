package entities;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import org.w3c.dom.css.Rect;


public class Crab extends Enemy {
    private TiledMapTileLayer collisionLayer;
    private TweenManager tweenManager;
    private Vector2 velocity = new Vector2();
    private float speed = 60 * 2;
    // Animation variables
    private Animation<TextureRegion> animation;
    private float stateTime;
    private float counter = 0;

    public Crab(Sprite sprite, TiledMapTileLayer collisionLayer, int velocityX, int velocityY) {
        super(sprite);
        this.collisionLayer = collisionLayer;
        this.velocity.x = velocityX;
        this.velocity.y = velocityY;
        //one of them should be 0 !!!!!!!
        animate();
    }

    @Override
    public void hit() {
        TextureRegion[] frames = new TextureRegion[4];
        frames[0] = new TextureRegion(new Texture(Gdx.files.internal("img/enemies/Crab_Attack1.png")));
        frames[1] = new TextureRegion(new Texture(Gdx.files.internal("img/enemies/Crab_Attack2.png")));
        frames[2] = new TextureRegion(new Texture(Gdx.files.internal("img/enemies/Crab_Attack3.png")));
        frames[3] = new TextureRegion(new Texture(Gdx.files.internal("img/enemies/Crab_Attack4.png")));

        animation = new Animation<TextureRegion>(0.1f, frames);
    }
    @Override
    public boolean isDeadly() {
        return true;
    }

    @Override
    public Rectangle getBoundingRectangle() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

    public void animate() {
        TextureRegion[] frames = new TextureRegion[4];
        frames[0] = new TextureRegion(new Texture(Gdx.files.internal("img/enemies/CrabMoving1.png")));
        frames[1] = new TextureRegion(new Texture(Gdx.files.internal("img/enemies/CrabMoving2.png")));
        frames[2] = new TextureRegion(new Texture(Gdx.files.internal("img/enemies/CrabMoving3.png")));
        frames[3] = new TextureRegion(new Texture(Gdx.files.internal("img/enemies/CrabMoving4.png")));


        animation = new Animation<TextureRegion>(0.1f, frames);
        stateTime = 0f;
    }

    @Override
    public void draw(Batch batch) {
        update(Gdx.graphics.getDeltaTime());
        stateTime += Gdx.graphics.getDeltaTime();

        // Get the current frame of animation for the current stateTime
        TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);

        // Draw the current frame
        batch.draw(currentFrame, getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void update(float deltaTime) {
        float oldX = getX();
        float oldY = getY();
        float tileWidth = collisionLayer.getTileWidth(), tileHeight = collisionLayer.getTileHeight();
        float speedX = velocity.x;
        float speedY = velocity.y;
        setX(getX() + velocity.x * deltaTime);
        setY(getY() + velocity.y * deltaTime);

        boolean collisionX;
        if (velocity.x < 0) {
            collisionX = collisionLayer.getCell((int) ((getX()) / tileWidth), (int) ((getY() + getHeight() / 2) / tileHeight)).getTile().getProperties().containsKey("blocked");
        } else {
            collisionX = collisionLayer.getCell((int) ((getX() + getWidth()) / tileWidth), (int) ((getY() + getHeight() / 2) / tileHeight)).getTile().getProperties().containsKey("blocked");
        }

        if (collisionX) {
            setX(oldX);
            velocity.x = -velocity.x;
        }

        boolean collisionY;
        if (velocity.y < 0) {
            collisionY = collisionLayer.getCell((int) ((getX() + getWidth() / 2) / tileWidth), (int) ((getY()) / tileHeight)).getTile().getProperties().containsKey("blocked");
        } else {
            collisionY = collisionLayer.getCell((int) ((getX() + getWidth() / 2) / tileWidth), (int) ((getY() + getHeight()) / tileHeight)).getTile().getProperties().containsKey("blocked");
        }
        if (collisionY) {
            setY(oldY);
            velocity.y = -velocity.y;
        }

    }


    public void move(int direction) {
        if (direction == 1) {
            setX(getX() - 1);
        } else if (direction == 2) {
            setX(getX() + 1);
        }

    }


}