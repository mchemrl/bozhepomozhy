package screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import static screens.Levels.buttonClickSound;

public class WinScreen implements Screen {
    private Stage stage;

    private Label label;
    private TextButton tryAgainButton;
    private TextButton nextLevelButton;
    private TextButton backToMenuButton;
    private Skin skin;
    private TextureAtlas atlas;
    private Screen currentLevel;
    private Screen nextLevel;
    private Class<? extends Screen> currentLevelClass;
    private Class<? extends Screen> nextLevelClass;
    public WinScreen(Class<? extends Screen> currentLevelClass, Class<? extends Screen> nextLevelClass) {
        this.currentLevelClass = currentLevelClass;
        this.nextLevelClass = nextLevelClass;
    }

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        atlas = new TextureAtlas("ui/levels/buttons.txt");
        skin = new Skin(Gdx.files.internal("ui/levels/levelsSkin.json"), atlas);

        // Створення мітки "YOU WIN" і додавання її на сцену
        label = new Label("YOU WIN!", skin);
        label.setFontScale(5);
        label.setPosition(Gdx.graphics.getWidth() / 2 - 350, Gdx.graphics.getHeight() / 2 + 250);
        stage.addActor(label);

        // Створення кнопки "Try Again"
        tryAgainButton = new TextButton("Try Again", skin, "levelbutton");
        tryAgainButton.setSize(250, 100);
        tryAgainButton.setPosition(Gdx.graphics.getWidth() / 2 - tryAgainButton.getWidth() / 2, Gdx.graphics.getHeight() / 2 - 50);
        tryAgainButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!Settings.soundDisabled) buttonClickSound.play();
                ((Game) Gdx.app.getApplicationListener()).setScreen(currentLevel);
            }
        });
        stage.addActor(tryAgainButton);

        // Створення кнопки "Next Level"
        nextLevelButton = new TextButton("Next Level", skin, "levelbutton");
        nextLevelButton.setSize(250, 100);
        nextLevelButton.setPosition(Gdx.graphics.getWidth() / 2 - nextLevelButton.getWidth() / 2, Gdx.graphics.getHeight() / 2 - 200);
        nextLevelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!Settings.soundDisabled) buttonClickSound.play();
                if (currentLevel instanceof Level1) {
                    ((Game) Gdx.app.getApplicationListener()).setScreen(new Level2());
                } else if (currentLevel instanceof Level2) {
                    ((Game) Gdx.app.getApplicationListener()).setScreen(new Level3());
                }
                // Add additional levels as needed
            }
        });
        stage.addActor(nextLevelButton);

        // Створення кнопки "Back to Menu"
        backToMenuButton = new TextButton("Back to Menu", skin, "levelbutton");
        backToMenuButton.setSize(250, 100);
        backToMenuButton.setPosition(Gdx.graphics.getWidth() / 2 - backToMenuButton.getWidth() / 2, Gdx.graphics.getHeight() / 2 - 350);
        backToMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!Settings.soundDisabled) buttonClickSound.play();
                ((Game) Gdx.app.getApplicationListener()).setScreen(new Levels());
            }
        });
        stage.addActor(backToMenuButton);
    }

    @Override
    public void render(float delta) {
        // Очистка екрану та малювання сцени
        Gdx.gl.glClearColor(1, 1, 0, 1); // Встановлює колір фону на жовтий
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
        atlas.dispose();
    }
}
