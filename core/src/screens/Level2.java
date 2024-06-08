package screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import entities.*;
import com.badlogic.gdx.audio.Music;
import extensions.LevelMaker;
import extensions.Loader;
import extensions.Saver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Level2 implements Screen {
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private Player player;
    private Crab crab, crab2, crab3, crab4, crab5;
    private Teeth teeth;
    private Music mapMusic;
    private LevelMaker levelMaker;
    private Sound deathSound;
    List<Enemy> enemies = new ArrayList<>();
    private final Coins coins = new Coins();
    private int collectedCoins = 0;
    private Stage stage;
    private Label pointsLabel;
    private Texture pearlTexture;
    private ProgressLabel progressLabel;

    @Override
    public void show() {
        deathSound = Gdx.audio.newSound(Gdx.files.internal("sounds/death.ogg"));
        levelMaker = new LevelMaker("maps/nature1.tmx", 40*LevelMaker.SIZE, 18*LevelMaker.SIZE, 575, 591);

        mapMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/bonetrousle.wav"));
        levelMaker.setMusic(mapMusic);

        levelMaker.loadMap();

        map = new TmxMapLoader().load("maps/level2.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        camera = new OrthographicCamera();

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        progressLabel = new ProgressLabel();
        stage.addActor(progressLabel);

        player = new Player(new Sprite(new Texture("img/player.png")), (TiledMapTileLayer) map.getLayers().get(1));
        player.setPosition(player.getX() + 40 * 16, player.getY() + 18 * 16);

        crab = new Crab(new Sprite(new Texture("img/enemies/CrabMoving1.png")), (TiledMapTileLayer) map.getLayers().get(1), 0, 30);
        crab.setPosition(36*16,17*16);
        enemies.add(crab);
        crab2 = new Crab(new Sprite(new Texture("img/enemies/CrabMoving1.png")), (TiledMapTileLayer) map.getLayers().get(1), 0, 30);
        crab2.setPosition(39*16,27*16);
        enemies.add(crab2);
        crab3 = new Crab(new Sprite(new Texture("img/enemies/CrabMoving1.png")), (TiledMapTileLayer) map.getLayers().get(1), 0, 30);
        crab3.setPosition(43*16,30*16);
        enemies.add(crab3);
        crab4 = new Crab(new Sprite(new Texture("img/enemies/CrabMoving1.png")), (TiledMapTileLayer) map.getLayers().get(1), 30, 0);
        crab4.setPosition(22*16,28*16);
        enemies.add(crab4);
        crab5 = new Crab(new Sprite(new Texture("img/enemies/CrabMoving1.png")), (TiledMapTileLayer) map.getLayers().get(1), 30, 0);
        crab5.setPosition(23*16,38.5f*16);
        enemies.add(crab5);
        //coins.fill(map, 1);


        Gdx.input.setInputProcessor(player);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.431f, 0.8f, 0.788f, 1); //pretty blue colour
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.position.set(player.getX() + player.getWidth() / 2, player.getY() + player.getWidth() / 2, 0);
        camera.update();
        for (Enemy enemy : enemies) {
            enemy.update(delta);
        }

        renderer.setView(camera);
        renderer.render();
        renderer.getBatch().begin();
        player.draw(renderer.getBatch());

        Iterator<Coin> coinIterator = coins.getCoinRow().iterator();
        while (coinIterator.hasNext()) {
            Coin coin = coinIterator.next();
            if (coin.getBoundingRectangle().overlaps(player.getBoundingRectangle())) {
                coinIterator.remove();
                collectedCoins++;
                Saver.saveProgress(1 + Loader.loadProgress());
            } else {
                coin.draw(renderer.getBatch());
            }
        }
        for (Enemy enemy : enemies) {
            if(enemy.getBoundingRectangle().overlaps(player.getBoundingRectangle())) {
                if (enemy.isDeadly()) {
                    enemy.hit();
                    ((Game) Gdx.app.getApplicationListener()).setScreen(new Levels());
                }
            }
        }
        if (player.isCaught){
            System.out.println("You died");
            ((Game) Gdx.app.getApplicationListener()).setScreen(new Levels());
        }
        //crab.draw(renderer.getBatch());
        for (Enemy enemy : enemies) {
            enemy.draw(renderer.getBatch());
        }
        renderer.getBatch().end();
        levelMaker.checkWinCondition(player);

        progressLabel.updatePoints(collectedCoins);
        // Draw the stage
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width / 2;
        camera.viewportHeight = height / 2;
        camera.update();
        stage.getViewport().update(width, height, true); // Update the stage viewport
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        player.getTexture().dispose();

        crab.getTexture().dispose();
        crab2.getTexture().dispose();
        crab3.getTexture().dispose();
        crab4.getTexture().dispose();
        crab5.getTexture().dispose();
        coins.getTexture().dispose();
        progressLabel.dispose();
        stage.dispose();
        mapMusic.dispose();

    }
}
