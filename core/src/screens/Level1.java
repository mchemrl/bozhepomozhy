package screens;

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
import entities.Enemy;
import entities.Player;
import com.badlogic.gdx.audio.Music;
import extensions.LevelMaker;

public class Level1 implements Screen {
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private Player player;
private final int SIZE = 16;
    private Enemy crab;
    private Music mapMusic;
    private LevelMaker levelMaker;
    @Override
    public void show() {
        levelMaker = new LevelMaker();
        mapMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/deathbyglamour.wav"));

        if(!Settings.musicDisabled){
        mapMusic.setVolume(0.15f);
        mapMusic.play();
        mapMusic.setLooping(true);}

        map = new TmxMapLoader().load("maps/nature1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        camera = new OrthographicCamera();

        player = new Player(new Sprite(new Texture("img/player.png")), (TiledMapTileLayer) map.getLayers().get(0));
        player.setPosition(player.getX()+40*SIZE, player.getY()+18*SIZE);

        crab = new Enemy(new Sprite(new Texture("img/enemies/CrabMoving1.png")), (TiledMapTileLayer) map.getLayers().get(0), 40, 0);
        crab.setPosition(crab.getX()+27*SIZE, crab.getY() + SIZE*21);

        Gdx.input.setInputProcessor(player);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.431f, 0.8f, 0.788f, 1); //pretty blue colour
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.position.set(player.getX() + player.getWidth()/2, player.getY() + player.getWidth()/2, 0);
        camera.update();

        crab.update(delta);

        renderer.setView(camera);
        renderer.render();
        renderer.getBatch().begin();
        player.draw(renderer.getBatch());

        crab.draw(renderer.getBatch());
        renderer.getBatch().end();

        levelMaker.win(player, 607, 719);
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
    }


}
