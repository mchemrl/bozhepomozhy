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


public class Enemy extends Sprite {


    public boolean isDeadly;

    public Enemy(Sprite sprite ) {
        super(sprite);
    }

    public boolean isDeadly() {
        return true;
    }

    public void draw(Batch batch) {
        update(Gdx.graphics.getDeltaTime());
    }

    public void hit() {
        // Do nothing
    }

    public Rectangle getBoundingRectangle() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

    public void update(float delta) {
        // Move the enemy
        setX(getX() + delta * 60);
    }

}