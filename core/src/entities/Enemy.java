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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import tween.ActorAccessor;
import tween.SpriteAccessor;

public class Enemy extends Sprite {


    private TiledMapTileLayer collisionLayer;
    private TweenManager tweenManager;

    private Vector2 velocity = new Vector2();
    private float speed = 60*2, gravity = 60*1.8f;
    // Animation variables
    private Animation<TextureRegion> animation;
    private float stateTime;
    public Enemy(Sprite sprite) {
        super(sprite);
        animate();
    }



        public void animate(){
        TextureRegion[] frames = new TextureRegion[4];
        frames[0] = new TextureRegion(new Texture(Gdx.files.internal("img/enemies/CrabMoving1.png")));
        frames[1] = new TextureRegion(new Texture(Gdx.files.internal("img/enemies/CrabMoving2.png")));
        frames[2] = new TextureRegion(new Texture(Gdx.files.internal("img/enemies/CrabMoving3.png")));
        frames[3] = new TextureRegion(new Texture(Gdx.files.internal("img/enemies/CrabMoving4.png")));

        animation = new Animation<TextureRegion>(0.1f, frames);
        stateTime = 0f;
    }

    public void draw(Batch batch) {
        update(Gdx.graphics.getDeltaTime());
        stateTime += Gdx.graphics.getDeltaTime();

        // Get the current frame of animation for the current stateTime
        TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);

        // Draw the current frame
        batch.draw(currentFrame, getX(), getY(), getWidth(), getHeight());
    }

    private void update(float deltaTime) {
        velocity.y -= gravity * deltaTime;
        if(velocity.y > speed){
            velocity.y = speed;
        }else if(velocity.y < -speed){
            velocity.y = -speed;
        }
        setX(getX() + velocity.x * deltaTime);
        setY(getY() + velocity.y * deltaTime);

    }


    public void move(int direction){
        if(direction == 1){
            setX(getX() - 1);
        }else if(direction == 2){
            setX(getX() + 1);
        }

    }


}