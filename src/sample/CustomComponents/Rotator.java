package sample.CustomComponents;

import sample.Components.ScriptableComponent;
import sample.VectorMath.FVector;

public class Rotator extends ScriptableComponent {
    public FVector rot = new FVector();
    @Override
    public void OnAwake() {

    }

    @Override
    public void Update(double deltaTime) {
        rot.y += deltaTime*90;
        rot.y %= 360;
        getGameObject().getTransform().setRotation(rot);
    }
}
