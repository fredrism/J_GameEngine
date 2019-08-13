package sample.Physics;

import sample.Components.RigidBodyComponent;
import sample.Mesh.Mesh;
import sample.Mesh.OBJReader;
import sample.Mesh.Triangle;
import sample.VectorMath.FVector;

public class BoxCollider extends Collider {
    Mesh mesh;
    public RigidBodyComponent rb;
    private FVector inertia;
    private double sr = 0;

    @Override
    public void CalculateInertia(double mass, RigidBodyComponent rb)
    {
        this.rb = rb;
        mesh = new OBJReader().fromFile("sample/Resources/cube.obj");
        double width = 0, depth = 0, height = 0;
        for(int i = 0; i < mesh.GetTriangleCount(); i++)
        {
            for(int o = 0; o < 3; o++)
            {
                width = Math.max(Math.abs(mesh.getTriangle(i).vertices[o].x), width);
                depth = Math.max(Math.abs(mesh.getTriangle(i).vertices[o].y), depth);
                height = Math.max(Math.abs(mesh.getTriangle(i).vertices[o].z), height);
                sr = Math.max(sr, mesh.getTriangle(i).vertices[o].magnitude());
            }
        }
        inertia = new FVector();
        inertia.x = (1/12)*mass*(width*width+depth*depth);
        inertia.y = (1/12)*mass*(depth*depth+height*height);
        inertia.z = (1/12)*mass*(width*width+height*height);
    }

    public int getTriangleCount()
    {
        return mesh.GetTriangleCount();
    }

    public Triangle getTriangle(int i)
    {
        Triangle t = new Triangle();
        for(int h = 0; h < 3; h++)
        {
            t.vertices[h] = rb.getGameObject().getTransform().applyTransforms(mesh.getTriangle(i).vertices[h]);
        }
        return t;
    }

    public double getSafeRadius()
    {
        return sr;
    }

    public FVector getInertia()
    {
        return inertia;
    }
}
