package tween;
import com.badlogic.gdx.graphics.g2d.Sprite;
import aurelienribon.tweenengine.TweenAccessor;
public class SpriteAccessor implements TweenAccessor<Sprite>{
    @Override
    public int getValues(Sprite target, int tweenType, float[] returnValues) {
        return 0;
    }

    @Override
    public void setValues(Sprite target, int tweenType, float[] newValues) {
    }
}
