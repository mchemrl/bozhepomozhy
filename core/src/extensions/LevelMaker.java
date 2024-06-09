package extensions;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import entities.Player;
import screens.*;

import java.awt.*;

import static com.badlogic.gdx.Gdx.app;

public class LevelMaker {
    private TiledMap map;
    private String mapPath;
    private Player player;
    private int playerStartX;
    private int playerStartY;
    private int winCoordinateX;
    private int winCoordinateY;
    private Screen currentScreen;
    public static final int SIZE = 16;
    private Loader loader = new Loader();


    public LevelMaker(String mapPath, int playerStartX, int playerStartY, int winCoordinateX, int winCoordinateY) {
        this.mapPath = mapPath;
        this.playerStartX = playerStartX;
        this.playerStartY = playerStartY;
        this.winCoordinateX = winCoordinateX;
        this.winCoordinateY = winCoordinateY;
    }

    public TiledMap loadMap() {
        this.map = new TmxMapLoader().load(mapPath);
        return this.map;
    }

    public Player createPlayer(TiledMapTileLayer collisionLayer) {
        this.player = new Player(new Sprite(new Texture("img/player.png")), collisionLayer);
        this.player.setPosition(playerStartX, playerStartY);
        return this.player;
    }

    public void setMusic(Music mapMusic) {
        if (!Settings.musicDisabled) {
            mapMusic.setVolume(0.15f);
            mapMusic.play();
            mapMusic.setLooping(true);
        }

    }


    public void checkWinCondition(Player player) {

        if (player.getX() == winCoordinateX && player.getY() == winCoordinateY)
            ((Game) Gdx.app.getApplicationListener()).setScreen(new WinScreen(new Level1()));
            if (currentScreen instanceof Level1) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new WinScreen(new Level1()));
            } else if (currentScreen instanceof Level2) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new WinScreen(new Level2()));
            }
    }
}