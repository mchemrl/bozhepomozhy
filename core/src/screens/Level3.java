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

public class Level3 implements Screen {
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private Player player;
    private Crab crab, crab2, crab3, crab4, crab5;
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
    private Teeth teeth, teeth2, teeth4, teeth3, teeth5;

    @Override
    public void show() {
        deathSound = Gdx.audio.newSound(Gdx.files.internal("sounds/death.ogg"));
        levelMaker = new LevelMaker("maps/level3.tmx", 40*LevelMaker.SIZE, 18*LevelMaker.SIZE, 847, 480, Level3.class, Level3.class);

        mapMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/megalovania.wav"));
        levelMaker.setMusic(mapMusic);

        map = levelMaker.loadMap();

        renderer = new OrthogonalTiledMapRenderer(map);
        camera = new OrthographicCamera();

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        progressLabel = new ProgressLabel();
        stage.addActor(progressLabel);


        player = new Player(new Sprite(new Texture("img/player.png")), (TiledMapTileLayer) map.getLayers().get(1));
        player.setPosition(player.getX() + 56 * 16, player.getY() + 20 * 16);

        Texture crabTexture = new Texture("img/enemies/CrabMoving1.png");

        crab = new Crab(new Sprite(crabTexture), (TiledMapTileLayer) map.getLayers().get(1), 30, 0);
        crab.setPosition(42 * 16, 21 * 16);
        enemies.add(crab);

        crab2 = new Crab(new Sprite(crabTexture), (TiledMapTileLayer) map.getLayers().get(1), 30, 0);
        crab2.setPosition(43 * 16, 34  * 16);
        enemies.add(crab2);

        crab3 = new Crab(new Sprite(crabTexture), (TiledMapTileLayer) map.getLayers().get(1), 0, 30);
        crab3.setPosition(46 * 16, 37 * 16);
        enemies.add(crab3);

        crab4 = new Crab(new Sprite(crabTexture), (TiledMapTileLayer) map.getLayers().get(1), 0, 30);
        crab4.setPosition(44.5f * 16, 44* 16);
        enemies.add(crab4);

        crab5 = new Crab(new Sprite(crabTexture), (TiledMapTileLayer) map.getLayers().get(1), 0, 30);
        crab5.setPosition(51.5f * 16, 45 * 16);
        enemies.add(crab5);

        teeth = new Teeth(new Sprite(new Texture("img/enemies/teeth1.png")));
        teeth.setPosition(42 * 16, 19 * 16);
        enemies.add(teeth);
        teeth2 = new Teeth(new Sprite(new Texture("img/enemies/teeth1.png")));
        teeth2.setPosition(52 * 16, 34 * 16);
        enemies.add(teeth2);
        teeth3 = new Teeth(new Sprite(new Texture("img/enemies/teeth1.png")));
        teeth3.setPosition(55 * 16, 41 * 16);
        enemies.add(teeth3);
        teeth4 = new Teeth(new Sprite(new Texture("img/enemies/teeth1.png")));
        teeth4.setPosition(42 * 16, 45 * 16);
        enemies.add(teeth4);
        teeth5 = new Teeth(new Sprite(new Texture("img/enemies/teeth1.png")));
        teeth5.setPosition(40 * 16, 30 * 16);
        enemies.add(teeth5);


       coins.fill(map, 2);

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
                    if(!Settings.soundDisabled)
                    deathSound = Gdx.audio.newSound(Gdx.files.internal("sounds/death.ogg"));
                    //enemy.hit();
                    System.out.println(player.getX());
                    System.out.println(player.getY());
                    System.out.println(enemy.getX()+enemy.getWidth()+ " enemy "+ enemy.getY()+enemy.getHeight());
                    ((Game) Gdx.app.getApplicationListener()).setScreen(new LoseScreen(Level3.class));
                }
            }
        }
        if (player.isCaught){
            System.out.println("You died");
            ((Game) Gdx.app.getApplicationListener()).setScreen(new LoseScreen(Level3.class));
        }
        //crab.draw(renderer.getBatch());
        for (Enemy enemy : enemies) {
            enemy.draw(renderer.getBatch());
        }
        renderer.getBatch().end();

        levelMaker.checkWinCondition(player);
        progressLabel.updatePoints(collectedCoins);

        System.out.println(player.getX());
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
        enemies.forEach(enemy -> enemy.getTexture().dispose());
        mapMusic.dispose();
        coins.getTexture().dispose();
        progressLabel.dispose();
        stage.dispose();
    }
}
