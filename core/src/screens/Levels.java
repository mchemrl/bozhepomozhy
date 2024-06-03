package screens;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import tween.ActorAccessor;

public class Levels implements Screen {
    private Stage stage;
    private Table table;
    private TextButton settingButton;
    private TextButton level1Button;
    private TextButton level2Button;
    private TextButton level3Button;
    private Label heading1;
    private Label heading2;
    private Skin skin;
    private TextureAtlas atlas;
    private TweenManager tweenManager;
    public static Sound buttonClickSound;
    private boolean level1Passed;
    private boolean level2Passed;
    private BottomMenu bottomMenu;

    @Override
    public void show() {
        stage = new Stage();

        Gdx.input.setInputProcessor(stage);

        atlas = new TextureAtlas("ui/levels/buttons.txt");
        skin = new Skin(Gdx.files.internal("ui/levels/levelsSkin.json"), atlas);

        table = new Table(skin);
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        buttonClickSound = Gdx.audio.newSound(Gdx.files.internal("sounds/select.ogg"));

        heading1 = new Label("MAP", skin, "map");
        heading1.setColor(1, 1, 1, 1);
        heading2 = new Label("STAGEBASED MOD", skin, "mod");
        heading1.setFontScale(.8f);
        heading2.setFontScale(.5f);

        level1Button = new TextButton("1", skin, "levelbutton");
        level1Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MainMenu.menuMusic.stop();
                if(!Settings.soundDisabled) buttonClickSound.play();
                ((Game) Gdx.app.getApplicationListener()).setScreen(new Level1());
            }
        });

        level2Button = new TextButton("2", skin, "levelbutton");
        level2Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!Settings.soundDisabled) buttonClickSound.play();
                MainMenu.menuMusic.stop();
                ((Game) Gdx.app.getApplicationListener()).setScreen(new Level2());
            }
        });

        level3Button = new TextButton("3", skin, "levelbutton");
        level3Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!Settings.soundDisabled) buttonClickSound.play();
                MainMenu.menuMusic.stop();
                ((Game) Gdx.app.getApplicationListener()).setScreen(new Level3());
            }
        });

        settingButton = new TextButton(" ", skin, "settingbutton");
        settingButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!Settings.soundDisabled) buttonClickSound.play();
                String previousScreen = "levels";
                ((Game) Gdx.app.getApplicationListener()).setScreen(new Settings(skin, previousScreen));
            }
        });

        bottomMenu = new BottomMenu(stage, skin,
                () -> {
                    // Action for shop button
                    // Add your logic here
                },
                () -> {
                    // Action for levels button
                    // Add your logic here
                },
                () -> {
                    // Action for arcade button
                    // Add your logic here
                }
        );

        if(Settings.musicDisabled) MainMenu.menuMusic.stop();

        table.add(heading1).colspan(3).spaceBottom(10).row();
        table.add(heading2).colspan(3).spaceBottom(30).row();
        table.add(level1Button).width(100).height(50).pad(20);
        table.add(level2Button).width(100).height(50).pad(20);
        table.add(level3Button).width(100).height(50).pad(20);


        Table topRightTable = new Table();
        topRightTable.setFillParent(true);
        topRightTable.top().right();
        topRightTable.add(settingButton).width(70).height(70).pad(10); // Set the size here

        stage.addActor(table);
        stage.addActor(topRightTable);

        tweenManager = new TweenManager();
        Tween.registerAccessor(Actor.class, new ActorAccessor());
        Tween.from(level1Button, ActorAccessor.ALPHA, 0.5f).target(0).start(tweenManager);
        Tween.from(level2Button, ActorAccessor.ALPHA, 0.5f).target(0).start(tweenManager);
        Tween.from(level3Button, ActorAccessor.ALPHA, 0.5f).target(0).start(tweenManager);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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
    }
}
