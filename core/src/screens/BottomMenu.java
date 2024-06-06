package screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

public class BottomMenu {
    private Stage stage;
    private Table table;
    private TextButton shopButton;
    private TextButton levelsButton;
    private TextButton instructionsButton;
    private Actor currentAnimatedActor;
    private Sound buttonClickSound;

    public BottomMenu(Stage stage, Skin skin, Runnable shopAction, Runnable levelsAction, Runnable instructionsAction) {
        this.stage = stage;
        this.shopButton = new TextButton("Shop", skin);
        this.levelsButton = new TextButton("Levels", skin);
        this.instructionsButton = new TextButton("Instructions", skin);

        shopButton.getLabel().setAlignment(Align.center);
        levelsButton.getLabel().setAlignment(Align.center);
        instructionsButton.getLabel().setAlignment(Align.center);

        shopButton.getLabelCell().padBottom(20);
        levelsButton.getLabelCell().padBottom(20);
        instructionsButton.getLabelCell().padBottom(20);

        table = new Table(skin);
        table.setFillParent(true);
        table.center().bottom();

        buttonClickSound = Gdx.audio.newSound(Gdx.files.internal("sounds/select.ogg"));

        shopButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!Settings.soundDisabled) {
                    buttonClickSound.play();
                }
                ((Game) Gdx.app.getApplicationListener()).setScreen(new ShopScreen());
                updateAnimation(shopButton.getLabel());
                shopAction.run();
            }
        });

        levelsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!Settings.soundDisabled) {
                    buttonClickSound.play();
                }
                ((Game) Gdx.app.getApplicationListener()).setScreen(new Levels());
                updateAnimation(levelsButton.getLabel());
                levelsAction.run();
            }
        });

        instructionsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!Settings.soundDisabled) {
                    buttonClickSound.play();
                }
                ((Game) Gdx.app.getApplicationListener()).setScreen(new Instructions());
                updateAnimation(instructionsButton.getLabel());
                instructionsAction.run();
            }
        });

        table.add(shopButton).expandX().fillX().height(85).pad(3);
        table.add(levelsButton).expandX().fillX().height(85).pad(3);
        table.add(instructionsButton).expandX().fillX().height(85).pad(3);

        stage.addActor(table);

        // Initially animate the correct button based on the current screen
        updateInitialAnimation();
    }

    private void animateButtonText(Actor actor, int distance) {
        actor.addAction(Actions.forever(Actions.sequence(
                Actions.moveBy(0, distance, 0.3f),
                Actions.moveBy(0, -distance, 0.3f)
        )));
    }

    private void updateAnimation(Actor actor) {
        if (currentAnimatedActor != null) {
            stopAnimation(currentAnimatedActor);
        }
        animateButtonText(actor, 7);
        currentAnimatedActor = actor;
    }

    private void stopAnimation(Actor actor) {
        actor.clearActions();
    }

    private void updateInitialAnimation() {
        Game app = (Game) Gdx.app.getApplicationListener();
        if (app.getScreen() instanceof ShopScreen) {
            updateAnimation(shopButton.getLabel());
        } else if (app.getScreen() instanceof Levels) {
            updateAnimation(levelsButton.getLabel());
        } else if (app.getScreen() instanceof Instructions) {
            updateAnimation(instructionsButton.getLabel());
        }
    }

    public void update(float delta) {
        stage.act(delta);
        stage.draw();
    }
}
