package screens;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import tween.ActorAccessor;

import java.sql.Time;

public class MainMenu implements Screen {
    private Stage stage;
    private Table table;
    private TextButton buttomExit, buttonPlay;
    private Label heading;
    private Skin skin;
    private TextureAtlas atlas;
    private TweenManager tweenManager;
    private Sound buttonClickSound;
    private Sound buttonHoverSound;

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        atlas = new TextureAtlas("ui/mainmenu/button.txt");
        skin = new Skin(Gdx.files.internal("ui/mainmenu/menuSkin.json"), atlas);

        table = new Table(skin);
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        buttonClickSound = Gdx.audio.newSound(Gdx.files.internal("sounds/buttonclicked.ogg"));
        buttonHoverSound = Gdx.audio.newSound(Gdx.files.internal("sounds/buttonhover.ogg"));

        buttomExit = new TextButton("EXIT", skin);
        buttomExit.getLabel().setFontScale(2.0f);
        buttomExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                buttonClickSound.play();
                Gdx.app.exit();
            }
        });
        buttomExit.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                buttonHoverSound.play();
                buttomExit.getLabel().setFontScale(2.2f);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                buttonHoverSound.play();
                buttomExit.getLabel().setFontScale(2.0f);
            }
        });
        buttomExit.pad(20);

        buttonPlay = new TextButton("PLAY", skin);
        buttonPlay.getLabel().setFontScale(2.0f);

        buttonPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                buttonClickSound.play();
                ((Game) Gdx.app.getApplicationListener()).setScreen(new Levels());
            }
        });
        buttonPlay.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                buttonHoverSound.play();
                buttonPlay.getLabel().setFontScale(2.2f);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                buttonHoverSound.play();
                buttonPlay.getLabel().setFontScale(1.8f);
            }
        });

        buttonPlay.pad(20);

        heading = new Label("Main Menu", skin);
        heading.setFontScale(3);

        table.add(heading).spaceBottom(50).row();
        table.add(buttonPlay).spaceBottom(10).row();
        table.add(buttomExit);
        //table.debug();//remove later
        stage.addActor(table);

        tweenManager = new TweenManager();
        Tween.registerAccessor(Actor.class, new ActorAccessor());
        //heading color change
        Timeline.createSequence().beginSequence()
                .push(Tween.to(heading, ActorAccessor.RGB, 0.5f).target(0, 0, 0))
                .push(Tween.to(heading, ActorAccessor.RGB, 0.5f).target(1, .65f, 1))
                .push(Tween.to(heading, ActorAccessor.RGB, 0.5f).target(1, 1, 1))
                .end().repeat(Tween.INFINITY, 0).start(tweenManager);
        //heading and buttons fade-in
        Timeline.createSequence().beginSequence()
                .push(Tween.set(buttonPlay, ActorAccessor.ALPHA).target(0))
                .push(Tween.set(buttomExit, ActorAccessor.ALPHA).target(0))
                .push(Tween.to(buttonPlay, ActorAccessor.ALPHA, .25f).target(1))
                .push(Tween.to(buttomExit, ActorAccessor.ALPHA, .25f).target(1))
                .push(Tween.to(heading, ActorAccessor.ALPHA, 1).target(1))
                .end().start(tweenManager);

        //table fade-in
        Tween.from(table, ActorAccessor.ALPHA, 0.5f).target(0).start(tweenManager);
        Tween.from(table, ActorAccessor.Y, 0.5f).target(Gdx.graphics.getHeight()/8).start(tweenManager);
    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);

        tweenManager.update(delta);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        table.invalidateHierarchy();
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
        stage.dispose();
        atlas.dispose();
        skin.dispose();
        buttonClickSound.dispose();
        buttonHoverSound.dispose();
    }
}
