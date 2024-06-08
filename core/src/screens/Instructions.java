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
    private TextButton settingButton;
    private BottomMenu bottomMenu;
    private Texture wTexture, aTexture, sTexture, dTexture;
    private Texture teeth1Texture, teeth2Texture, teeth3Texture, teeth4Texture, teeth5Texture;
    private Texture crabMoving1Texture, crabMoving2Texture, crabMoving3Texture, crabMoving4Texture;
    private Texture pearlTexture;


    private Texture stoneTexture;

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        if (Settings.musicDisabled) MainMenu.menuMusic.stop();

        atlas = new TextureAtlas("ui/levels/buttons.txt");
        skin = new Skin(Gdx.files.internal("ui/levels/levelsSkin.json"), atlas);

        // Завантаження текстур
        wTexture = new Texture(Gdx.files.internal("img/w.png"));
        aTexture = new Texture(Gdx.files.internal("img/a.png"));
        sTexture = new Texture(Gdx.files.internal("img/s.png"));
        dTexture = new Texture(Gdx.files.internal("img/d.png"));
        teeth1Texture = new Texture(Gdx.files.internal("img/enemies/teeth1.png"));
        teeth2Texture = new Texture(Gdx.files.internal("img/enemies/teeth2.png"));
        teeth3Texture = new Texture(Gdx.files.internal("img/enemies/teeth3.png"));
        teeth4Texture = new Texture(Gdx.files.internal("img/enemies/teeth4.png"));
        teeth5Texture = new Texture(Gdx.files.internal("img/enemies/teeth5.png"));
        crabMoving1Texture = new Texture(Gdx.files.internal("img/enemies/crabMoving1.png"));
        crabMoving2Texture = new Texture(Gdx.files.internal("img/enemies/crabMoving2.png"));
        crabMoving3Texture = new Texture(Gdx.files.internal("img/enemies/crabMoving3.png"));
        crabMoving4Texture = new Texture(Gdx.files.internal("img/enemies/crabMoving4.png"));
        pearlTexture = new Texture(Gdx.files.internal("img/pearl.png"));
        stoneTexture = new Texture(Gdx.files.internal("maps/stone.psd"));

        // Додавання кнопки інструкцій
        instructionsButton = new TextButton("", skin);
        instructionsButton.setSize(1000, 850);
        instructionsButton.setPosition(Gdx.graphics.getWidth() / 2 - instructionsButton.getWidth() / 2, Gdx.graphics.getHeight() / 2 - instructionsButton.getHeight() / 2 - 40);

        Label titleLabel = new Label("Instructions", skin);
        titleLabel.setPosition(Gdx.graphics.getWidth() / 2 - titleLabel.getWidth() / 2, Gdx.graphics.getHeight() / 2 + instructionsButton.getHeight() / 2 + 20 - 40);

        settingButton = new TextButton(" ", skin, "settingbutton");
        settingButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!Settings.soundDisabled) buttonClickSound.play();
                String previousScreen = "Instructions";
                ((Game) Gdx.app.getApplicationListener()).setScreen(new Settings(skin, previousScreen));
            }
        });

        Table topRightTable = new Table();
        topRightTable.setFillParent(true);
        topRightTable.top().right();
        topRightTable.add(settingButton).width(70).height(70).pad(10);

        stage.addActor(instructionsButton);
        stage.addActor(titleLabel);
        stage.addActor(topRightTable);

        bottomMenu = new BottomMenu(stage, skin,
                () -> {},
                () -> {},
                () -> {}
        );

        // Відображення інструкцій
        showWImage();
    }


    private void showWImage() {
        Image wImage = new Image(new TextureRegionDrawable(new TextureRegion(wTexture)));
        wImage.setSize(50, 50);
        wImage.setPosition(instructionsButton.getX() + 75, instructionsButton.getY() + instructionsButton.getHeight() - wImage.getHeight() - 80);
        stage.addActor(wImage);

        animateImage(wImage, () -> showAImage());
    }

    private void showAImage() {
        Image aImage = new Image(new TextureRegionDrawable(new TextureRegion(aTexture)));
        aImage.setSize(50, 50);
        aImage.setPosition(instructionsButton.getX() + 15, instructionsButton.getY() + instructionsButton.getHeight() - aImage.getHeight() - 90 - 40);
        stage.addActor(aImage);

        animateImage(aImage, () -> showSImage());
    }

    private void showSImage() {
        Image sImage = new Image(new TextureRegionDrawable(new TextureRegion(sTexture)));
        sImage.setSize(50, 50);
        sImage.setPosition(instructionsButton.getX() + 75, instructionsButton.getY() + instructionsButton.getHeight() - sImage.getHeight() - 90 - 40);
        stage.addActor(sImage);

        animateImage(sImage, () -> showDImage());
    }

    private void showDImage() {
        Image dImage = new Image(new TextureRegionDrawable(new TextureRegion(dTexture)));
        dImage.setSize(50, 50);
        dImage.setPosition(instructionsButton.getX() + 135, instructionsButton.getY() + instructionsButton.getHeight() - dImage.getHeight() - 90 - 40);
        stage.addActor(dImage);

        animateImage(dImage, () -> showFirstLabel());
    }



    private void showFirstLabel() {
        addLabelToButton("press us to move your sprite", 150, 220, () -> showTeethImage());
    }
    private void showTeethImage() {
        Image teethImage = new Image(new TextureRegionDrawable(new TextureRegion(teeth1Texture)));
        teethImage.setSize(50, 70);
        teethImage.setPosition(instructionsButton.getX() + 85, instructionsButton.getY() + instructionsButton.getHeight() - teethImage.getHeight() - 140 - 40 - 80);
        stage.addActor(teethImage);

        animateTeethImage(teethImage, () -> showSecondLabel());
    }
    private void showSecondLabel() {
        addLabelToButton("you can touch me if i sleep,", 280, 220, () -> showThirdLabel());
    }

    private void showThirdLabel() {
        addLabelToButton("but when i wake up run away", 310, 220, () -> showCrabImage());
    }
    private void showFourthLabel() {
        addLabelToButton("Never touch me, otherwise you die", 425, 220, () -> showStoneImage());
    }

    private void showPearlImageAndLabel() {
        Image pearlImage = new Image(new TextureRegionDrawable(new TextureRegion(pearlTexture)));
        pearlImage.setSize(50, 50);
        pearlImage.setPosition(instructionsButton.getX() + 80, instructionsButton.getY() + 200);
        stage.addActor(pearlImage);

        animateImage(pearlImage, () -> showPearlLabel());
    }

    private void showPearlLabel() {
        addLabelToButton("collect us to have additional", 600, 220, () -> showPearlLabel2());
    }
    private void showPearlLabel2() {
        addLabelToButton("opportunities", 630, 220, () -> showHaveFun());
    }
    private void showHaveFun(){
        addLabelToButton("have fun!", 750, 400, ()-> showGoodLuck());
    }
    private void showGoodLuck(){
        addLabelToButton("good luck!", 800, 400, ()->{});
    }
    private void showStoneImage() {
        Image stoneImage = new Image(new TextureRegionDrawable(new TextureRegion(stoneTexture)));
        stoneImage.setSize(50, 50);
        stoneImage.setPosition(instructionsButton.getX() + 80, instructionsButton.getY() + 300);
        stage.addActor(stoneImage);

        animateImage(stoneImage, () -> showStoneLabel());
    }
    private void showStoneLabel() {
        addLabelToButton("i`ll stab you to you death", 510, 220, () -> showStoneLabel2());
    }
    private void showStoneLabel2() {
        addLabelToButton("if you crash into me", 540, 220, () ->showPearlImageAndLabel());
    }


    private void animateImage(Image image, Runnable onComplete) {
        int distance = 20;
        float duration = 0.2f;

        image.addAction(Actions.sequence(
                Actions.moveBy(0, distance, duration),
                Actions.moveBy(0, -distance, duration),
                Actions.run(onComplete)
        ));
    }

    private void animateTeethImage(Image teethImage, Runnable onComplete) {
        teethImage.addAction(Actions.sequence(
                Actions.delay(0.2f),
                Actions.run(() -> teethImage.setDrawable(new TextureRegionDrawable(new TextureRegion(teeth2Texture)))),
                Actions.delay(0.2f),
                Actions.run(() -> teethImage.setDrawable(new TextureRegionDrawable(new TextureRegion(teeth3Texture)))),
                Actions.delay(0.2f),
                Actions.run(() -> teethImage.setDrawable(new TextureRegionDrawable(new TextureRegion(teeth4Texture)))),
                Actions.delay(0.2f),
                Actions.run(() -> teethImage.setDrawable(new TextureRegionDrawable(new TextureRegion(teeth5Texture)))),
                Actions.run(onComplete)
        ));
    }
    private void showCrabImage() {
        Image crabImage = new Image(new TextureRegionDrawable(new TextureRegion(crabMoving1Texture)));
        crabImage.setSize(70, 70);
        crabImage.setPosition(instructionsButton.getX() +70, instructionsButton.getY() + instructionsButton.getHeight() - crabImage.getHeight() - 140 - 40 - 200);
        stage.addActor(crabImage);

        animateCrabImage(crabImage, () -> showFourthLabel());
    }

    private void animateCrabImage(Image crabImage, Runnable onComplete) {
        crabImage.addAction(Actions.sequence(
                Actions.delay(0.2f),
                Actions.run(() -> crabImage.setDrawable(new TextureRegionDrawable(new TextureRegion(crabMoving2Texture)))),
                Actions.delay(0.2f),
                Actions.run(() -> crabImage.setDrawable(new TextureRegionDrawable(new TextureRegion(crabMoving3Texture)))),
                Actions.delay(0.2f),
                Actions.run(() -> crabImage.setDrawable(new TextureRegionDrawable(new TextureRegion(crabMoving4Texture)))),
                Actions.run(onComplete)
        ));
    }

    private void addLabelToButton(String labelText, float y, float x, Runnable onComplete) {
        Label label = new Label("", skin);
        label.setPosition(instructionsButton.getX() + x, instructionsButton.getY() + instructionsButton.getHeight() - label.getHeight() - y);
        stage.addActor(label);

        new Thread(() -> {
            for (int i = 0; i <= labelText.length(); i++) {
                final int index = i;
                Gdx.app.postRunnable(() -> {
                    if (index <= labelText.length()) {
                        label.setText(labelText.substring(0, index));
                    }
                });
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Gdx.app.postRunnable(onComplete);
        }).start();
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
        crabMoving1Texture.dispose();
        crabMoving2Texture.dispose();
        crabMoving3Texture.dispose();
        crabMoving4Texture.dispose();
        pearlTexture.dispose();
    }
}
