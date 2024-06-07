package entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public  class Coin extends Sprite {

    private Animation<TextureRegion> animation;
    private float stateTime;

    public Coin(float x, float y) {
        super(new Texture(Gdx.files.internal("img/pearl.png")));
        setPosition(x, y);
        animate();
    }

    public void animate(){
        TextureRegion[] frames = new   TextureRegion[2];
        frames[0] = new TextureRegion(new Texture(Gdx.files.internal("img/pearl.png")));
        frames[1] = new TextureRegion(new Texture(Gdx.files.internal("img/pearl2.png")));
        animation = new Animation<TextureRegion>(0.2f,frames);
        stateTime = 0f;
    }

    public void draw(Batch batch) {
        stateTime += Gdx.graphics.getDeltaTime();

        TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);
        batch.draw(currentFrame, getX(), getY(), getWidth(), getHeight());
    }

    public Rectangle getBoundingRectangle() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

}
