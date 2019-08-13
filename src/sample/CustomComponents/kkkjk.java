package sample.CustomComponents;

import javafx.scene.input.KeyCode;
import sample.Components.*;
import sample.Physics.Physics;
import sample.SceneControl.InputController;
import sample.VectorMath.FVector;

import java.awt.*;

public class kkkjk extends ScriptableComponent {
    private RigidBodyComponent rb;
    private FVector rcOffset = new FVector(0, -1, 0);

    @Override
    public void OnAwake() {
        rb = getGameObject().GetComponent(RigidBodyComponent.class);
    }

    @Override
    public void Update(double deltaTime) {
        if(InputController.getKey(KeyCode.SPACE))
        {
            rb.AddForce(new FVector(0, 2, 0));
        }

        FVector start = FVector.Add(getGameObject().getTransform().getPosition(), rcOffset);
        FVector end = FVector.Add(getGameObject().getTransform().getPosition(), FVector.Mul(rcOffset, 4));


        Physics.RayCastHit hit = getGameObject().scene.physics.RayCast(start, rcOffset, 4);
        if(hit != null)
        {
            getGameObject().scene.gizmos.DrawLine(start, end, Color.RED.getRGB());
        }
        else
        {
            getGameObject().scene.gizmos.DrawLine(start, end, Color.GREEN.getRGB());
        }
    }
}