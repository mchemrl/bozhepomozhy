package screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import entities.Crab;
import entities.Enemy;
import entities.Player;
import com.badlogic.gdx.audio.Music;
import entities.Teeth;
import extensions.LevelMaker;

import java.util.ArrayList;
import java.util.List;

public class Level1 implements Screen {
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private Player player;
    private Crab crab;
    private Music mapMusic;
    private LevelMaker levelMaker;
    List<Enemy> enemies = new ArrayList<>();
    private Crab crab2;
    private Teeth teeth, teeth2, teeth4,teeth3;

    @Override
    public void show() {
        levelMaker = new LevelMaker("maps/nature1.tmx", 40*LevelMaker.SIZE, 18*LevelMaker.SIZE, 607, 719);

        mapMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/deathbyglamour.wav"));
        levelMaker.setMusic(mapMusic);

        map = levelMaker.loadMap();
        renderer = new OrthogonalTiledMapRenderer(map);
        camera = new OrthographicCamera();

        player = levelMaker.createPlayer((TiledMapTileLayer) map.getLayers().get(0));

        crab = new Crab(new Sprite(new Texture("img/enemies/CrabMoving1.png")), (TiledMapTileLayer) map.getLayers().get(0), 40, 0);
        crab.setPosition(crab.getX()+27*LevelMaker.SIZE, crab.getY() + LevelMaker.SIZE*21);
        enemies.add(crab);
//        crab2 = new Crab(new Sprite(new Texture("img/enemies/CrabMoving1.png")), (TiledMapTileLayer) map.getLayers().get(0), 40, 0);
//        crab2.setPosition(crab2.getX()+37*LevelMaker.SIZE, crab2.getY() + LevelMaker.SIZE*31);
//        enemies.add(crab2);

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

        Gdx.input.setInputProcessor(player);
    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.431f, 0.8f, 0.788f, 1); //pretty blue colour
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.position.set(player.getX() + player.getWidth()/2, player.getY() + player.getWidth()/2, 0);
        camera.update();

        crab.update(delta);
//        crab2.update(delta);

        renderer.setView(camera);
        renderer.render();
        renderer.getBatch().begin();
        player.draw(renderer.getBatch());

        for (Enemy enemy : enemies) {
            if(enemy.getBoundingRectangle().overlaps(player.getBoundingRectangle())) {
                if (enemy.isDeadly()) {
                    System.out.println("You died");
                    ((Game) Gdx.app.getApplicationListener()).setScreen(new Levels());
                }
            }
        }
        for (Enemy enemy : enemies) {
            enemy.draw(renderer.getBatch());
        }
        renderer.getBatch().end();

        levelMaker.checkWinCondition(player);
        System.out.println(player.getX());
        System.out.println(player.getY());
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width/2;
        camera.viewportHeight = height/2;
        camera.update();
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
//        crab2.getTexture().dispose();
        teeth.getTexture().dispose();
        teeth2.getTexture().dispose();
        teeth3.getTexture().dispose();
        teeth4.getTexture().dispose();
        mapMusic.dispose();
    }
}
