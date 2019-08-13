package sample.Mesh;
import sample.VectorMath.*;
import java.util.*;

public class Mesh {
    private Triangle[] triangles;
    public String name;


    public Mesh(FVector[] vertices, FVector[] normals, FVector[] UVs, int[] triangles, String name) {
        this.name = name;
        List<Triangle> tris = new ArrayList<Triangle>();

        for(int i = 0; i < triangles.length; i+=9)
        {
            Triangle t = new Triangle();
            try{
                for(int o = 0; o < 3; o++)
                {
                    t.vertices[o] = vertices[triangles[i+(3*o)]];
                    t.normals[o] = normals[triangles[i+(3*o)+2]];
                    t.uvs[o] = UVs[triangles[i+(3*o)+1]];
                }
                tris.add(t);
            }
            catch (ArrayIndexOutOfBoundsException e)
            {
                break;
            }
        }
        this.triangles = tris.toArray(new Triangle[0]);
    }

    public int GetTriangleCount()
    {
        return triangles.length;
    }

    public Triangle getTriangle(int tri)
    {
        return triangles[tri];
    }

    /*public VertexData getVertexData(int i, FVector v0, FVector v1, FVector v2, FVector point)
    {
        FVector weight = FMath.Interpolate(point, v0, v1, v2);
        VertexData v = new VertexData(FMath.Mix(UVs[triangles[i+1]], UVs[triangles[i+4]], UVs[triangles[i+7]], weight),
                                        FMath.Mix(normals[triangles[i+2]], normals[triangles[i+5]], normals[triangles[i+8]], weight));
        return v;
    }*/
}
