package sample.Rendering;

import sample.Components.TransformClass;
import sample.VectorMath.FVector;
import sample.VectorMath.Matrix;

public class CameraClass {
    Matrix projectionMatrix;
    Matrix viewMatrix;
    Matrix viewportMatrix;
    TransformClass transform;
    double FocalLength, screen_width, screen_height;

    public CameraClass(double FocalLength, double width, double height)
    {
        this.FocalLength = FocalLength;
        screen_width = width;
        screen_height = height;
        transform = new TransformClass(null);

        double FOVRad = 1/(Math.tan((FocalLength/2) * (Math.PI/180.0)));
        viewportMatrix = Matrix.viewportMatrix(0,0,width, height, 200);
        projectionMatrix = Matrix.SimpleProjection(FOVRad, width, height);
    }

    public void UpdateMatrices()
    {
        transform.UpdateMatrices();
        viewMatrix = transform.getTransformMatrix().Inverse();
    }

    public FVector ToScreenCoord(FVector v)
    {
        FVector v0 = Matrix.MulPoint(v, projectionMatrix, false);
        v0 = Matrix.MulPoint(v0, viewportMatrix, true);
        return v0;
    }
}
