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

public class Settings implements Screen {
    private Stage stage;
    private Skin skin;
    private Sound buttonClickSound;
    private String previousScreen;
    boolean musicDisabled = false;
    boolean soundDisabled = false;

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
        animateButtonText(soundButton, 3);
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
                }else{
                    animateButtonText(soundButton, 3);
                    soundDisabled = false;
                }
            }
        });

        musicButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                buttonClickSound.play();
                if (!musicDisabled) {
                    musicButton.clearActions();
                    musicDisabled = true;
                }else{
                    animateButtonText(musicButton, 3);
                    musicDisabled = false;
                }
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                buttonClickSound.play();
                if (previousScreen.equals("shop")){
                    ((Game) Gdx.app.getApplicationListener()).setScreen(new ShopScreen());
                }else if(previousScreen.equals("levels")){
                    ((Game) Gdx.app.getApplicationListener()).setScreen(new Levels());
                }else{
                    ((Game) Gdx.app.getApplicationListener()).setScreen(new ArcadeScreen());
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
