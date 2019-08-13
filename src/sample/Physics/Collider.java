package sample.Physics;

import sample.Components.RigidBodyComponent;
import sample.Mesh.Triangle;
import sample.VectorMath.FVector;

public abstract class Collider {

    public FVector getInertia()
    {
        return null;
    }

    public abstract int getTriangleCount();

    public abstract Triangle getTriangle(int i);

    public abstract double getSafeRadius();

    public void CalculateInertia(double mass, RigidBodyComponent rb)
    {

    }
}
