package entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;
import java.util.List;

public class Coins extends Sprite {

    public Animation<TextureRegion> animation;
    private float stateTime;
    private List<Coin> coinRow;

    public Coins(float minX, float maxX, float minY, float maxY) {
        super(new Texture("img/pearl.png"));
        coinRow = line(minX, maxX, minY, maxY);
    }

    private List<Coin> line(float x1, float x2, float y1, float y2) {
        List<Coin> coins = new ArrayList<>();
        for (float x = x1; x <= x2; x += 16) {
            for (float y = y1; y <= y2; y += 16) {
                coins.add(new Coin(x, y));
            }
        }
        return coins;
    }

    public void update(float delta) {
        //stateTime += delta;
        //float oldX = getX(), oldY = getY(), tileWidth = collisionLayer.getTileWidth(), tileHeight = collisionLayer.getTileHeight();
        //int pixelsToMove = (int) (speed * delta);
    }

    public void draw(Batch batch) {
        for (Coin coin : coinRow) {
            coin.draw(batch);
        }

//    public boolean isCollected() {
//        return false;
//    }

//    public void setCollected(boolean collected) {
//
//    }
//
//    public void setCollected(){
//


    }


}
