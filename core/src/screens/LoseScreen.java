package screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import extensions.Loader;

import static screens.Levels.buttonClickSound;

public class LoseScreen implements Screen {
    private Stage stage;
    private Skin skin;
    private TextureAtlas atlas;
    private Class<? extends Screen> retryLevelClass; // Type to store the level class for retry

    public LoseScreen(Class<? extends Screen> retryLevelClass) {
        this.retryLevelClass = retryLevelClass; // Set the retry level class
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        atlas = new TextureAtlas("ui/levels/buttons.txt");
        skin = new Skin(Gdx.files.internal("ui/levels/levelsSkin.json"), atlas);

        Label label = new Label("YOU LOSE!", skin);
        label.setFontScale(5);
        label.setPosition(Gdx.graphics.getWidth() / 2 - 350, Gdx.graphics.getHeight() / 2 + 250);

        TextButton tryAgainButton = new TextButton("Try Again", skin, "levelbutton");
        TextButton backToMenuButton = new TextButton("Back to Menu", skin, "levelbutton");

        tryAgainButton.setSize(250, 100);
        tryAgainButton.setPosition(Gdx.graphics.getWidth() / 2 - tryAgainButton.getWidth() / 2, Gdx.graphics.getHeight() / 2 - 50);

        backToMenuButton.setSize(250, 100);
        backToMenuButton.setPosition(Gdx.graphics.getWidth() / 2 - backToMenuButton.getWidth() / 2, Gdx.graphics.getHeight() / 2 - 200);

        tryAgainButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!Settings.soundDisabled) buttonClickSound.play();
                try {
                    // Restart the specific level that was last played
                    ((Game) Gdx.app.getApplicationListener()).setScreen(retryLevelClass.newInstance());
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });

        backToMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!Settings.soundDisabled) buttonClickSound.play();
                ((Game) Gdx.app.getApplicationListener()).setScreen(new Levels());
            }
        });

        stage.addActor(label);
        stage.addActor(tryAgainButton);
        stage.addActor(backToMenuButton);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 0, 1); // Set background color to yellow
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
        skin.dispose();
    }
}
