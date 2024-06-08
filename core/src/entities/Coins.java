package entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

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

    public void fill(TiledMap map, int num){
        TiledMapTileLayer layer = (TiledMapTileLayer)map.getLayers().get(num);
        for (int x = 0; x < layer.getWidth(); x++) {
            for (int y = 0; y < layer.getHeight(); y++) {
                TiledMapTileLayer.Cell cell = layer.getCell(x, y);
                if (cell != null && cell.getTile().getProperties().containsKey("forpearl")) {
                    coinRow.add(new Coin(x * layer.getTileWidth(), y * layer.getTileHeight()));
                }
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
