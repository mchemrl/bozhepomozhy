package screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import static screens.Levels.buttonClickSound;

public class Instructions implements Screen {
    private Stage stage;
    private TextureAtlas atlas;
    private Skin skin;
    private TextButton instructionsButton;
    private TextButton settingButton; // Додаємо кнопку налаштувань
    BottomMenu bottomMenu;
    private Texture wTexture, aTexture, sTexture, dTexture;
    private Texture teeth1Texture, teeth2Texture, teeth3Texture, teeth4Texture, teeth5Texture; // Додаємо текстури teeth

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        if (Settings.musicDisabled) MainMenu.menuMusic.stop();

        atlas = new TextureAtlas("ui/levels/buttons.txt");
        skin = new Skin(Gdx.files.internal("ui/levels/levelsSkin.json"), atlas);

        // Завантаження текстур w.png, a.png, s.png, d.png, teeth
        wTexture = new Texture(Gdx.files.internal("img/w.png"));
        aTexture = new Texture(Gdx.files.internal("img/a.png"));
        sTexture = new Texture(Gdx.files.internal("img/s.png"));
        dTexture = new Texture(Gdx.files.internal("img/d.png"));
        teeth1Texture = new Texture(Gdx.files.internal("img/enemies/teeth1.png"));
        teeth2Texture = new Texture(Gdx.files.internal("img/enemies/teeth2.png"));
        teeth3Texture = new Texture(Gdx.files.internal("img/enemies/teeth3.png"));
        teeth4Texture = new Texture(Gdx.files.internal("img/enemies/teeth4.png"));
        teeth5Texture = new Texture(Gdx.files.internal("img/enemies/teeth5.png"));

        instructionsButton = new TextButton("", skin);
        instructionsButton.setSize(1000, 850);
        instructionsButton.setPosition(Gdx.graphics.getWidth() / 2 - instructionsButton.getWidth() / 2, Gdx.graphics.getHeight() / 2 - instructionsButton.getHeight() / 2 - 40);

        // Додаємо картинки w, a, s, d
        Image wImage = new Image(new TextureRegionDrawable(new TextureRegion(wTexture)));
        Image aImage = new Image(new TextureRegionDrawable(new TextureRegion(aTexture)));
        Image sImage = new Image(new TextureRegionDrawable(new TextureRegion(sTexture)));
        Image dImage = new Image(new TextureRegionDrawable(new TextureRegion(dTexture)));
        Image teethImage = new Image(new TextureRegionDrawable(new TextureRegion(teeth1Texture))); // Додаємо картинку teeth1.png

        float imageSize = 50;
        wImage.setSize(imageSize, imageSize);
        aImage.setSize(imageSize, imageSize);
        sImage.setSize(imageSize, imageSize);
        dImage.setSize(imageSize, imageSize);
        teethImage.setSize(imageSize, imageSize + 20);

        float buttonX = instructionsButton.getX();
        float buttonY = instructionsButton.getY();
        float buttonHeight = instructionsButton.getHeight();

        // Позиціонування картинок з більшими відступами
        float offsetY = 40; // зсув вниз на 40 пікселів
        float gap = 40; // більші відступи між кнопками

        wImage.setPosition(buttonX + 75, buttonY - 40 + buttonHeight - wImage.getHeight() - offsetY);
        aImage.setPosition(buttonX + 15, buttonY - 90 + buttonHeight - aImage.getHeight() - offsetY - gap);
        sImage.setPosition(buttonX + 75, buttonY - 90 + buttonHeight - sImage.getHeight() - offsetY - gap);
        dImage.setPosition(buttonX + 135, buttonY - 90 + buttonHeight - dImage.getHeight() - offsetY - gap);
        teethImage.setPosition(buttonX +85, buttonY - 140 + buttonHeight - teethImage.getHeight() - offsetY - 2 * gap);

        // Додаємо текст "press us to move your sprite"
        addLabelToButton("press us to move your sprite");



        Label titleLabel = new Label("Instructions", skin);
        titleLabel.setPosition(Gdx.graphics.getWidth() / 2 - titleLabel.getWidth() / 2, Gdx.graphics.getHeight() / 2 + instructionsButton.getHeight() / 2 + 20 - 40); // Зсув вниз на 40 пікселів

        // Додаємо кнопку налаштувань
        settingButton = new TextButton(" ", skin, "settingbutton");
        settingButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!Settings.soundDisabled) buttonClickSound.play();
                String previousScreen = "Instructions";
                ((Game) Gdx.app.getApplicationListener()).setScreen(new Settings(skin, previousScreen));
            }
        });

        // Розміщуємо кнопку налаштувань на екрані
        Table topRightTable = new Table();
        topRightTable.setFillParent(true);
        topRightTable.top().right();
        topRightTable.add(settingButton).width(70).height(70).pad(10); // Встановлюємо розмір тут

        stage.addActor(instructionsButton);
        stage.addActor(wImage);
        stage.addActor(aImage);
        stage.addActor(sImage);
        stage.addActor(dImage);
        stage.addActor(teethImage); // Додаємо teethImage на сцену
        stage.addActor(titleLabel);
        stage.addActor(topRightTable); // Додаємо таблицю з кнопкою налаштувань

        animateImagesInSequence(wImage, aImage, sImage, dImage, 20, 0.3f); // Запускаємо анімацію
        animateTeethImage(teethImage); // Запускаємо анімацію teethImage

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

    private void animateImagesInSequence(Image wImage, Image aImage, Image sImage, Image dImage, int distance, float duration) {
        wImage.addAction(Actions.sequence(
                Actions.moveBy(0, distance, duration),
                Actions.moveBy(0, -distance, duration),
                Actions.run(() -> aImage.addAction(Actions.sequence(
                        Actions.moveBy(0, distance, duration),
                        Actions.moveBy(0, -distance, duration),
                        Actions.run(() -> sImage.addAction(Actions.sequence(
                                Actions.moveBy(0, distance, duration),
                                Actions.moveBy(0, -distance, duration),
                                Actions.run(() -> dImage.addAction(Actions.sequence(
                                        Actions.moveBy(0, distance, duration),
                                        Actions.moveBy(0, -distance, duration)
                                )))
                        )))
                )))
        ));
    }

    private void animateTeethImage(Image teethImage) {
        teethImage.addAction(Actions.sequence(
                Actions.delay(0.5f), // Затримка перед початком анімації
                Actions.run(() -> teethImage.setDrawable(new TextureRegionDrawable(new TextureRegion(teeth2Texture)))), // Зміна на teeth2.png
                Actions.delay(0.5f), // Затримка перед наступною зміною
                Actions.run(() -> teethImage.setDrawable(new TextureRegionDrawable(new TextureRegion(teeth3Texture)))), // Зміна на teeth3.png
                Actions.delay(0.5f), // Затримка перед наступною зміною
                Actions.run(() -> teethImage.setDrawable(new TextureRegionDrawable(new TextureRegion(teeth4Texture)))), // Зміна на teeth4.png
                Actions.delay(0.5f), // Затримка перед наступною зміною
                Actions.run(() -> teethImage.setDrawable(new TextureRegionDrawable(new TextureRegion(teeth5Texture)))) // Зміна на teeth5.png
        ));
    }

    private void addLabelToButton(String labelText) {
        // Видаляємо попередні написи на кнопці
        instructionsButton.clearChildren();

        // Додаємо новий напис на кнопку з зсувом вниз на 200 пікселів
        Label label = new Label("", skin);
        instructionsButton.add(label).expand().top().right().padTop(150).padRight(60);

        // Паралельно з анімацією картинок змінюємо текст по одній літері
        new Thread(() -> {
            for (int i = 0; i <= labelText.length(); i++) {
                final int index = i;
                Gdx.app.postRunnable(() -> {
                    if (index <= labelText.length()) {
                        label.setText(labelText.substring(0, index));
                    }
                });
                try {
                    Thread.sleep(100); // Затримка в мілісекундах
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void addSlidingLabel(String labelText) {
        Label slidingLabel = new Label(labelText, skin);
        slidingLabel.setPosition(-slidingLabel.getWidth(), Gdx.graphics.getHeight() / 2 - instructionsButton.getHeight() / 2 - 60); // Початкова позиція поза екраном зліва

        stage.addActor(slidingLabel);

        // Анімація виїзду зліва направо
        slidingLabel.addAction(Actions.moveTo(Gdx.graphics.getWidth() / 2 - slidingLabel.getWidth() / 2, Gdx.graphics.getHeight() / 2 - instructionsButton.getHeight() / 2 - 60, 2.0f)); // Зсув вниз на 60 пікселів
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
        wTexture.dispose();
        aTexture.dispose();
        sTexture.dispose();
        dTexture.dispose();
        teeth1Texture.dispose();
        teeth2Texture.dispose();
        teeth3Texture.dispose();
        teeth4Texture.dispose();
        teeth5Texture.dispose();
    }
}
