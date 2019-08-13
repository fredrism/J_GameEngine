package sample.Mesh;
import sample.VectorMath.*;

public class Triangle {
    public FVector[] vertices;
    public FVector[] normals;
    public FVector[] uvs;

    public Triangle()
    {
        vertices = new FVector[3];
        normals = new FVector[3];
        uvs = new FVector[3];
    }
}
