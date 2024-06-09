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
    private TextButton backToMenuButton;
    private Skin skin;
    private TextureAtlas atlas;
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

        // Set the label text based on the completed level
        String labelText = currentLevelClass == Level3.class ? "CONGRATULATIONS!" : "YOU WIN!";
        label = new Label(labelText, skin);
        label.setFontScale(5);
        if (label.textEquals("YOU WIN!")){
            label.setPosition(Gdx.graphics.getWidth() / 2 - 350, Gdx.graphics.getHeight() / 2 + 250);
        }else{
            label.setPosition(Gdx.graphics.getWidth() / 2 -800, Gdx.graphics.getHeight() / 2 + 250);
        }

        stage.addActor(label);

        tryAgainButton = new TextButton("Try Again", skin, "levelbutton");
        tryAgainButton.setSize(250, 100);
        tryAgainButton.setPosition(Gdx.graphics.getWidth() / 2 - tryAgainButton.getWidth() / 2, Gdx.graphics.getHeight() / 2 - 50);
        tryAgainButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!Settings.soundDisabled) buttonClickSound.play();
                try {
                    ((Game) Gdx.app.getApplicationListener()).setScreen(currentLevelClass.newInstance());
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
        stage.addActor(tryAgainButton);

        backToMenuButton = new TextButton("Back to Menu", skin, "levelbutton");
        backToMenuButton.setSize(250, 100);
        backToMenuButton.setPosition(Gdx.graphics.getWidth() / 2 - backToMenuButton.getWidth() / 2, Gdx.graphics.getHeight() / 2 - 200);
        backToMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!Settings.soundDisabled) buttonClickSound.play();
                ((Game) Gdx.app.getApplicationListener()).setScreen(new Levels());
            }
        });
        stage.addActor(backToMenuButton);

        // Remove the next level button if the current level is Level3
        if (currentLevelClass != Level3.class) {
            TextButton nextLevelButton = new TextButton("Next Level", skin, "levelbutton");
            nextLevelButton.setSize(250, 100);
            nextLevelButton.setPosition(Gdx.graphics.getWidth() / 2 - nextLevelButton.getWidth() / 2, Gdx.graphics.getHeight() / 2 - 200);
            nextLevelButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (!Settings.soundDisabled) buttonClickSound.play();
                    try {
                        ((Game) Gdx.app.getApplicationListener()).setScreen(nextLevelClass.newInstance());
                    } catch (InstantiationException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            });
            stage.addActor(nextLevelButton);
        }
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
        atlas.dispose();
    }
}
