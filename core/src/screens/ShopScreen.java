package screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label; // Додаємо імпорт класу Label
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import extensions.Loader;

import static screens.Levels.buttonClickSound;

public class ShopScreen implements Screen {
    private Stage stage;
    private TextureAtlas atlas;
    private Skin skin;
    private TextButton spriteButton;
    private TextButton settingButton; // Додаємо кнопку налаштувань
    BottomMenu bottomMenu;
    private Texture hellokittyTexture;
    private ProgressLabel progressLabel;

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        if (Settings.musicDisabled) MainMenu.menuMusic.stop();

        atlas = new TextureAtlas("ui/levels/buttons.txt");
        skin = new Skin(Gdx.files.internal("ui/levels/levelsSkin.json"), atlas);

        // Завантаження текстури hellokitty.png
        hellokittyTexture = new Texture(Gdx.files.internal("img/hellokitty.png"));
        TextureRegionDrawable hellokittyDrawable = new TextureRegionDrawable(hellokittyTexture);

        spriteButton = new TextButton("", skin, "levelbutton");
        spriteButton.getStyle().up = hellokittyDrawable; // Встановлюємо зображення як фон кнопки
        spriteButton.setSize(300, 350);
        spriteButton.setPosition(Gdx.graphics.getWidth() / 2 - spriteButton.getWidth() / 2, Gdx.graphics.getHeight() / 2 - spriteButton.getHeight() / 2);

        spriteButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Додаємо дію для кнопки spriteButton, якщо потрібно
            }
        });

        stage.addActor(spriteButton);

        progressLabel = new ProgressLabel();
        progressLabel.updatePoints(Loader.loadProgress());
        stage.addActor(progressLabel);

        // Додаємо напис "Choose Your Fighter" вгорі над кнопкою
        Label chooseFighterLabel = new Label("Choose Your Fighter", skin, "default");
        chooseFighterLabel.setPosition(Gdx.graphics.getWidth() / 2 - chooseFighterLabel.getWidth() / 2, Gdx.graphics.getHeight() / 2 + spriteButton.getHeight() / 2 + 20);
        stage.addActor(chooseFighterLabel);

        // Додаємо кнопку налаштувань
        settingButton = new TextButton(" ", skin, "settingbutton");
        settingButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!Settings.soundDisabled) buttonClickSound.play();
                String previousScreen = "shop";
                ((Game) Gdx.app.getApplicationListener()).setScreen(new Settings(skin, previousScreen));
            }
        });

        Table topRightTable = new Table();
        topRightTable.setFillParent(true);
        topRightTable.top().right();
        topRightTable.add(settingButton).width(70).height(70).pad(10);

        stage.addActor(topRightTable);
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
                    // Action for instructions button
                    // Add your logic here
                }
        );
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(22, 1, 0, 0.7f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
        skin.dispose();
        hellokittyTexture.dispose();
    }
}
