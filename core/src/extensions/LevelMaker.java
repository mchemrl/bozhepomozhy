package extensions;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import entities.Player;
import screens.Settings;
import screens.ShopScreen;
import screens.WinScreen;

public class LevelMaker {
    private TiledMap map;
    private String mapPath;
    private Player player;
    private int playerStartX;
    private int playerStartY;
    private int winCoordinateX;
    private int winCoordinateY;
    private Class<? extends Screen> currentLevelClass;
    private Class<? extends Screen> nextLevelClass;
    public static final int SIZE = 16;
    private Loader loader = new Loader();

    public LevelMaker(String mapPath, int playerStartX, int playerStartY, int winCoordinateX, int winCoordinateY,
                      Class<? extends Screen> currentLevelClass, Class<? extends Screen> nextLevelClass) {
        this.mapPath = mapPath;
        this.playerStartX = playerStartX;
        this.playerStartY = playerStartY;
        this.winCoordinateX = winCoordinateX;
        this.winCoordinateY = winCoordinateY;
        this.currentLevelClass = currentLevelClass;
        this.nextLevelClass = nextLevelClass;
    }

    public TiledMap loadMap() {
        this.map = new TmxMapLoader().load(mapPath);
        return this.map;
    }

    public Player createPlayer(TiledMapTileLayer collisionLayer) {
        String selectedHeroName = ShopScreen.getSelectedHeroName();
        Texture playerTexture;

        switch (selectedHeroName) {
            case "Hello Kitty":
                playerTexture = new Texture(Gdx.files.internal("img/hellokittysmall.png"));
                break;
            case "Hello World":
                playerTexture = new Texture(Gdx.files.internal("img/helloworldsmall.png"));
                break;
            case "Princess":
                playerTexture = new Texture(Gdx.files.internal("img/princesssmall.png"));
                break;
            default:
                playerTexture = new Texture(Gdx.files.internal("img/player.png")); // Default texture
                break;
        }

        this.player = new Player(new Sprite(playerTexture), collisionLayer);
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
        if (player.getX() == winCoordinateX && player.getY() == winCoordinateY) {
            ((Game) Gdx.app.getApplicationListener()).setScreen(new WinScreen(currentLevelClass, nextLevelClass));
        }
    }
}
