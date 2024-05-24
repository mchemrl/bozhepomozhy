package screens;

import aurelienribon.tweenengine.*;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import tween.SpriteAccessor;

import static aurelienribon.tweenengine.Timeline.createSequence;

public class Splash implements Screen {
    private Sprite splash;
    private SpriteBatch batch;
    private TweenManager tweenManager;
    @Override
    public void show() {
        batch = new SpriteBatch();
        tweenManager = new TweenManager();
        Tween.registerAccessor(Sprite.class, new SpriteAccessor())  ;

        Texture splashTexture = new Texture("img/splash.png");
        splash = new Sprite(splashTexture);
        splash.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Timeline.createSequence()
                .push(Tween.set(splash, SpriteAccessor.ALPHA).target(0))
                .push(Tween.to(splash, SpriteAccessor.ALPHA, 2).target(1))
                .push(Tween.to(splash, SpriteAccessor.ALPHA, 1).target(0))
                .setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
                    }
                })
                .start(tweenManager);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);
        tweenManager.update(delta);
        batch.begin();
        splash.draw(batch);
        splash.draw(batch);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        splash.getTexture().dispose();
    }
}
