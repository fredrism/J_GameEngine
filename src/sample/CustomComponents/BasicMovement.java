package sample.CustomComponents;

import javafx.scene.input.KeyCode;
import sample.Components.*;
import sample.SceneControl.InputController;
import sample.VectorMath.FVector;

import java.awt.*;

public class BasicMovement extends ScriptableComponent{
    TransformClass transform;
    FVector position;
    FVector rotation;
    @Override
    public void OnAwake() {
        transform = super.getGameObject().getTransform();
        position = new FVector(0, 2, 0);
        rotation = new FVector();
    }

    @Override
    public void Update(double deltaTime) {
        if(InputController.getKey(KeyCode.W))
        {
            transform.Move(FVector.Mul(transform.getForwardVector(),deltaTime*20));
        }
        if(InputController.getKey(KeyCode.S))
        {
            transform.Move(FVector.Mul(transform.getForwardVector(),-deltaTime*20));
        }
        if(InputController.getKey(KeyCode.A))
        {
            transform.Rotate(new FVector(0, deltaTime*50, 0));
        }
        if(InputController.getKey(KeyCode.D))
        {
             transform.Rotate(new FVector(0, -deltaTime*50, 0));
        }
        if(InputController.getKey(KeyCode.SPACE))
        {
            transform.Move(new FVector(0, deltaTime*20, 0));
        }
        if(InputController.getKey(KeyCode.SHIFT))
        {
            transform.Move(new FVector(0, -deltaTime*20, 0));
        }
    }
}