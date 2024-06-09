package screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import entities.*;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.InputMultiplexer;
import extensions.LevelMaker;
import extensions.Loader;
import extensions.Saver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Level1 implements Screen {
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private Player player;
    private Music mapMusic;
    private LevelMaker levelMaker;
    List<Enemy> enemies = new ArrayList<>();
    private Teeth teeth, teeth2, teeth4, teeth3;
    private final Coins coins = new Coins();
    private int collectedCoins = 0;
    private Stage stage;
    private ProgressLabel progressLabel;
    private Sound deathSound;

    @Override
    public void show() {
        levelMaker = new LevelMaker("maps/nature1.tmx", 38 * LevelMaker.SIZE, 18 * LevelMaker.SIZE, 607, 719, Level1.class, Level2.class);

        mapMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/deathbyglamour.wav"));
        levelMaker.setMusic(mapMusic);
        deathSound = Gdx.audio.newSound(Gdx.files.internal("sounds/death.ogg"));

        map = levelMaker.loadMap();
        renderer = new OrthogonalTiledMapRenderer(map);
        camera = new OrthographicCamera();

        stage = new Stage();
        progressLabel = new ProgressLabel();
        stage.addActor(progressLabel);

        player = levelMaker.createPlayer((TiledMapTileLayer) map.getLayers().get(0));

        teeth = new Teeth(new Sprite(new Texture("img/enemies/teeth1.png")));
        teeth.setPosition(26 * 16, 20 * 16);
        enemies.add(teeth);
        teeth2 = new Teeth(new Sprite(new Texture("img/enemies/teeth1.png")));
        teeth2.setPosition(42 * 16, 23 * 16);
        enemies.add(teeth2);
        teeth3 = new Teeth(new Sprite(new Texture("img/enemies/teeth1.png")));
        teeth3.setPosition(38 * 16, 26 * 16);
        enemies.add(teeth3);
        teeth4 = new Teeth(new Sprite(new Texture("img/enemies/teeth1.png")));
        teeth4.setPosition(34 * 16, 41 * 16);
        enemies.add(teeth4);

        coins.fill(map, 1);

        InputMultiplexer inputMultiplexer = new InputMultiplexer(stage, player);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.431f, 0.8f, 0.788f, 1); //pretty blue colour
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.position.set(Math.max(camera.viewportWidth / 2, Math.min(player.getX() + player.getWidth() / 2, map.getProperties().get("width", Integer.class) * LevelMaker.SIZE - camera.viewportWidth / 2)),
                Math.max(camera.viewportHeight / 2, Math.min(player.getY() + player.getHeight() / 2, map.getProperties().get("height", Integer.class) * LevelMaker.SIZE - camera.viewportHeight / 2)),
                0);
        camera.update();

        renderer.setView(camera);
        renderer.render();
        renderer.getBatch().begin();
        player.draw(renderer.getBatch());

        Iterator<Coin> coinIterator = coins.getCoinRow().iterator();
        List<Coin> coinsToRemove = new ArrayList<>();
        while (coinIterator.hasNext()) {
            Coin coin = coinIterator.next();
            if (coin.getBoundingRectangle().overlaps(player.getBoundingRectangle())) {
                coinsToRemove.add(coin);
                collectedCoins++;
                Saver.saveProgress(1 + Loader.loadProgress());
            } else {
                coin.draw(renderer.getBatch());
            }
        }
        coins.getCoinRow().removeAll(coinsToRemove);

        for (Enemy enemy : enemies) {
            if (enemy.getBoundingRectangle().overlaps(player.getBoundingRectangle())) {
                if (enemy.isDeadly()) {
                    if (!Settings.soundDisabled)
                        deathSound.play();
                    ((Game) Gdx.app.getApplicationListener()).setScreen(new LoseScreen(Level1.class));
                }
            }
        }

        if (player.isCaught) {
            System.out.println("You died");
            ((Game) Gdx.app.getApplicationListener()).setScreen(new Levels());
        }

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
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        player.getTexture().dispose();
        deathSound.dispose();

        for (Enemy enemy : enemies) {
            enemy.getTexture().dispose();
        }

        coins.getTexture().dispose();
        mapMusic.dispose();
        progressLabel.dispose();
        stage.dispose();
    }
}
