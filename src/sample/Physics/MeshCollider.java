package sample.Physics;

import sample.Components.RigidBodyComponent;
import sample.Mesh.Mesh;
import sample.Mesh.Triangle;
import sample.VectorMath.FVector;

public class MeshCollider extends Collider{
    public Mesh mesh;
    public RigidBodyComponent rb;
    private FVector inertia;
    private double sr = 0;

    public MeshCollider(Mesh mesh)
    {
        this.mesh = mesh;
    }

    @Override
    public void CalculateInertia(double mass, RigidBodyComponent rb)
    {
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
        return mesh.getTriangle(i);
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
