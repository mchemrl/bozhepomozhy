package entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Coins extends Sprite {

    public Animation<TextureRegion> animation;
    private float stateTime;
    private List<Coin> coinRow;

    public Coins() {
        super(new Texture(Gdx.files.internal("img/pearl.png")));
        coinRow = new ArrayList<>();
    }

    public void addRow(float x1, float x2, float y1, float y2) {
        for (float x = x1; x <= x2; x += 16) {
            for (float y = y1; y <= y2; y += 16) {
                coinRow.add(new Coin(x, y));
            }
        }
    }

    public void draw(Batch batch) {
        Iterator<Coin> iterator = coinRow.iterator();
        while (iterator.hasNext()) {
            Coin coin = iterator.next();
            coin.draw(batch);
        }
    }

    public List<Coin> getCoinRow() {
        return coinRow;
    }

    public void removeCoin(Coin coin) {
        coinRow.remove(coin);
    }
}
