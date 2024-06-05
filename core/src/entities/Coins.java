package entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Coins  extends Sprite {

    public Animation<TextureRegion> animation;
    private float stateTime;

    public Coins(float min, float max) {
        super(new Texture("img/pearl.png"));
        animate();
        line(min,max);
    }

    private void line(float min, float max) {
        for (float i=min; i<=max; i++){

        }
    }

    public void animate(){
        TextureRegion[] frames = new   TextureRegion[2];
        frames[0] = new TextureRegion(new Texture(Gdx.files.internal("img/pearl.png")));
        frames[1] = new TextureRegion(new Texture(Gdx.files.internal("img/pearl2.png")));
        animation = new Animation<TextureRegion>(0.2f,frames);
        stateTime = 0f;
    }

    public void update(float delta) {
        //stateTime += delta;
        //float oldX = getX(), oldY = getY(), tileWidth = collisionLayer.getTileWidth(), tileHeight = collisionLayer.getTileHeight();
        //int pixelsToMove = (int) (speed * delta);
    }

    public void draw() {
        //update(Gdx.graphics.getDeltaTime());
        //stateTime += Gdx.graphics.getDeltaTime();
        //TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);
        //batch.draw(currentFrame, getX(), getY(), getWidth(), getHeight());
    }

    public boolean isCollected() {
        return false;
    }

    public void setCollected(boolean collected) {

    }

    public void setCollected(){

    }


}
