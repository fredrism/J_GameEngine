package sample.Components;

import sample.Components.*;
import sample.VectorMath.FVector;
import sample.VectorMath.Matrix;

public class CameraComponent extends ScriptableComponent implements java.io.Serializable {
    private Matrix projectionMatrix;
    private Matrix viewMatrix;
    private Matrix viewportMatrix;
    private TransformClass transform;
    private double FocalLength, screen_width, screen_height;

    public CameraComponent(double FocalLength, double width, double height)
    {
        this.FocalLength = FocalLength;
        screen_width = width;
        screen_height = height;

        double FOVRad = 1/(Math.tan((FocalLength/2) * (Math.PI/180.0)));
        viewportMatrix = Matrix.viewportMatrix(0,0,width, height, 200);
        projectionMatrix = Matrix.SimpleProjection(FOVRad, width, height);
    }

    public void UpdateMatrices()
    {
        transform.UpdateMatrices();
        viewMatrix = transform.getTransformMatrix().Inverse();
    }

    public FVector ToViewCoord(FVector v)
    {
        FVector v0 = Matrix.MulPoint(v, viewMatrix, false);
        return v0;
    }

    public FVector ToScreenCoord(FVector v)
    {
        FVector v0 = Matrix.MulPoint(v, projectionMatrix, false);
        v0 = Matrix.MulPoint(v0, viewportMatrix, true);
        return v0;
    }

    @Override
    public void OnAwake() {
        transform = super.getGameObject().getTransform();
    }

    @Override
    public void Update(double deltaTime) {
    }
}