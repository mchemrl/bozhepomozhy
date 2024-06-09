package screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import extensions.Loader;
import extensions.Saver;

public class Settings implements Screen {
    private Stage stage;
    private Skin skin;
    private Sound buttonClickSound;
    private String previousScreen;
    public static boolean musicDisabled = false;
    public static boolean soundDisabled = false;

    public Settings(Skin skin, String previousScreen) {
        this.skin = skin;
        this.stage = new Stage(new ScreenViewport());
        this.previousScreen = previousScreen;
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        TextButton soundButton = new TextButton("Sound", skin);
        TextButton musicButton = new TextButton("Music", skin);

        // Анімація для тексту кнопок
        if(!soundDisabled)
        animateButtonText(soundButton, 3);

        if(!musicDisabled)
        animateButtonText(musicButton, 3);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        buttonClickSound = Gdx.audio.newSound(Gdx.files.internal("sounds/select.ogg"));

        TextButton backButton = new TextButton("Back", skin, "levelbutton");

        soundButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                buttonClickSound.play();
                if (!soundDisabled) {
                    soundButton.clearActions();
                    soundDisabled = true;
                    //Levels.buttonClickSound.setVolume(0,0);
                    //buttonClickSound.setVolume(0,0);
                }else{
                    animateButtonText(soundButton, 3);
                    soundDisabled = false;
                    //Levels.buttonClickSound.setVolume(1,1);
                   // buttonClickSound.setVolume(0,1);
                }
                Saver.saveSettings(musicDisabled, soundDisabled, Loader.loadLevelSettings());
            }
        });

        musicButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                buttonClickSound.play();
                if (!musicDisabled) {
                    musicButton.clearActions();
                    musicDisabled = true;
                    MainMenu.menuMusic.stop();
                }else{
                    animateButtonText(musicButton, 3);
                    musicDisabled = false;
                    MainMenu.menuMusic.play();
                }
                Saver.saveSettings(musicDisabled, soundDisabled, Loader.loadLevelSettings());
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!soundDisabled) buttonClickSound.play();
                if (previousScreen.equals("shop")){
                    ((Game) Gdx.app.getApplicationListener()).setScreen(new ShopScreen());
                }else if(previousScreen.equals("levels")){
                    ((Game) Gdx.app.getApplicationListener()).setScreen(new Levels());
                }else{
                    ((Game) Gdx.app.getApplicationListener()).setScreen(new Instructions());
                }
            }
        });

        table.center();
        table.add(soundButton).padBottom(20).row();
        table.add(musicButton).padBottom(20).row();
        table.add(backButton).padBottom(20).row();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(22, 1, 0, 0.7f);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    private void animateButtonText(TextButton button, int distance) {
        button.addAction(Actions.forever(Actions.sequence(
                Actions.moveBy(0, distance, 0.3f),
                Actions.moveBy(0, -distance, 0.3f)
        )));
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
    }
}
