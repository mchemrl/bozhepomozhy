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
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import extensions.Loader;
import extensions.Saver;

import static screens.Levels.buttonClickSound;

public class ShopScreen implements Screen {
    private Stage stage;
    private TextureAtlas atlas;
    private Skin skin;
    private TextButton spriteButton;
    private TextButton spriteButton2;
    private TextButton spriteButton3;
    private TextButton settingButton;
    private BottomMenu bottomMenu;
    private Texture hellokittyTexture;
    private Texture hellokittySmallTexture;
    private Texture helloworldTexture;
    private Texture helloworldSmallTexture;
    private Texture princessTexture;
    private Texture princessSmallTexture;
    private Image pearlImage;
    private Image helloKittyImage;
    private ProgressLabel progressLabel;
    private Label notEnoughPointsLabel;

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        if (Settings.musicDisabled) MainMenu.menuMusic.stop();

        atlas = new TextureAtlas("ui/levels/buttons.txt");
        skin = new Skin(Gdx.files.internal("ui/levels/levelsSkin.json"), atlas);

        hellokittyTexture = new Texture(Gdx.files.internal("img/hellokitty.png"));
        hellokittySmallTexture = new Texture(Gdx.files.internal("img/hellokittysmall.png"));
        helloworldTexture = new Texture(Gdx.files.internal("img/helloworld.png"));
        helloworldSmallTexture = new Texture(Gdx.files.internal("img/helloworldsmall.png"));
        princessTexture = new Texture(Gdx.files.internal("img/princess.png"));
        princessSmallTexture = new Texture(Gdx.files.internal("img/princesssmall.png"));

        spriteButton = new TextButton("", skin, "levelbutton");
        spriteButton2 = new TextButton("", skin, "levelbutton");
        spriteButton3 = new TextButton("", skin, "levelbutton");

        float buttonWidth = 300;
        float buttonHeight = 350;
        float buttonSpacing = 20; // Spacing between buttons
        float buttonY = Gdx.graphics.getHeight() / 2 - buttonHeight / 2;

        spriteButton.setSize(buttonWidth, buttonHeight);
        spriteButton.setPosition(Gdx.graphics.getWidth() / 2 + buttonWidth / 2 - buttonSpacing * 30.5f, buttonY); // Move left button to the left of the center button

        spriteButton2.setSize(buttonWidth, buttonHeight);
        spriteButton2.setPosition(Gdx.graphics.getWidth() / 2 - buttonWidth / 2, buttonY); // Center button remains in place

        spriteButton3.setSize(buttonWidth, buttonHeight);
        spriteButton3.setPosition(Gdx.graphics.getWidth() / 2 + buttonWidth / 2 + buttonSpacing * 0.5f, buttonY); // Move right button to the right

        helloKittyImage = setupButton(spriteButton, hellokittyTexture, "Free", null, "Hello Kitty");
        setupButton(spriteButton2, helloworldTexture, "20", new Texture(Gdx.files.internal("img/pearlLabel.png")), "Hello World");
        setupButton(spriteButton3, princessTexture, "40", new Texture(Gdx.files.internal("img/pearlLabel.png")), "Princess");

        stage.addActor(spriteButton);
        stage.addActor(spriteButton2);
        stage.addActor(spriteButton3);

        spriteButton.addListener(new PurchaseClickListener(0)); // Free item, cost is 0
        spriteButton2.addListener(new PurchaseClickListener(20));
        spriteButton3.addListener(new PurchaseClickListener(40));

        progressLabel = new ProgressLabel();
        progressLabel.updatePoints(Loader.loadProgress());
        stage.addActor(progressLabel);

        Label chooseFighterLabel = new Label("Choose Your Fighter", skin, "default");
        chooseFighterLabel.setPosition(Gdx.graphics.getWidth() / 2 - chooseFighterLabel.getWidth() / 2, Gdx.graphics.getHeight() / 2 + spriteButton.getHeight() / 2 + 20);
        stage.addActor(chooseFighterLabel);

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
                },
                () -> {
                    // Action for levels button
                },
                () -> {
                    // Action for instructions button
                }
        );

        animateImage();

        notEnoughPointsLabel = new Label("Collect more pearls to buy new sprite", skin, "default");
        notEnoughPointsLabel.setPosition(Gdx.graphics.getWidth() / 2 - notEnoughPointsLabel.getWidth() / 2, buttonY - 40);
        notEnoughPointsLabel.setVisible(false); // Initially hidden
        stage.addActor(notEnoughPointsLabel);
    }

    private class PurchaseClickListener extends ClickListener {
        private final int cost;

        public PurchaseClickListener(int cost) {
            this.cost = cost;
        }

        @Override
        public void clicked(InputEvent event, float x, float y) {
            int currentPoints = Loader.loadProgress();
            if (currentPoints >= cost) {
                Saver.saveProgress(currentPoints - cost); // Deduct the cost
                progressLabel.updatePoints(Loader.loadProgress()); // Update the progress label
                displayBoughtMessage();
                notEnoughPointsLabel.setVisible(false); // Hide the "not enough points" message if purchase is successful
            } else {
                displayNotEnoughPointsMessage();
            }
        }
    }

    private void displayBoughtMessage() {
        Label boughtLabel = new Label("Bought", skin, "default");
        boughtLabel.setPosition(Gdx.graphics.getWidth() / 2 - boughtLabel.getWidth() / 2, 50);
        stage.addActor(boughtLabel);
        boughtLabel.addAction(Actions.sequence(Actions.fadeIn(0.5f), Actions.delay(1.5f), Actions.fadeOut(0.5f), Actions.removeActor()));
    }

    private void displayNotEnoughPointsMessage() {
        notEnoughPointsLabel.setVisible(true);
        notEnoughPointsLabel.addAction(Actions.sequence(Actions.fadeIn(2f), Actions.delay(1.5f), Actions.fadeOut(0.5f), Actions.run(() -> notEnoughPointsLabel.setVisible(false))));
    }

    private Image setupButton(TextButton button, Texture texture, String label, Texture pearlTexture, String title) {
        Image image = new Image(new TextureRegionDrawable(new TextureRegion(texture)));
        image.setSize(200, 200);
        image.setPosition(button.getWidth() / 2 - image.getWidth() / 2, button.getHeight() / 2 - image.getHeight() / 2 + 20);
        button.addActor(image);

        Label titleLabel = new Label(title, skin, "default");
        titleLabel.setPosition(button.getWidth() / 2 - titleLabel.getWidth() / 2, image.getY() + image.getHeight() + 10);
        button.addActor(titleLabel);

        if (pearlTexture != null) {
            pearlImage = new Image(new TextureRegionDrawable(new TextureRegion(pearlTexture)));
            pearlImage.setSize(30, 30);
            pearlImage.setPosition(button.getWidth() / 2 - pearlImage.getWidth() / 2 - 15, 10);
            button.addActor(pearlImage);

            Label pearlLabel = new Label(label, skin, "default");
            pearlLabel.setPosition(pearlImage.getX() + pearlImage.getWidth() + 5, 10);
            button.addActor(pearlLabel);
        } else {
            Label buttonLabel = new Label(label, skin, "default");
            buttonLabel.setPosition(button.getWidth() / 2 - buttonLabel.getWidth() / 2, 10);
            button.addActor(buttonLabel);
        }

        return image;
    }

    private void animateImage() {
        helloKittyImage.addAction(Actions.forever(Actions.sequence(
                Actions.run(() -> helloKittyImage.setDrawable(new TextureRegionDrawable(new TextureRegion(hellokittyTexture)))),
                Actions.sizeTo(200, 200, 0.2f),
                Actions.delay(0.2f),
                Actions.run(() -> helloKittyImage.setDrawable(new TextureRegionDrawable(new TextureRegion(hellokittySmallTexture)))),
                Actions.sizeTo(200, 170, 0.2f),
                Actions.delay(0.2f)
        )));
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
        hellokittySmallTexture.dispose();
        helloworldTexture.dispose();
        helloworldSmallTexture.dispose();
        princessTexture.dispose();
        princessSmallTexture.dispose();
    }
}
