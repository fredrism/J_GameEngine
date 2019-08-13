package sample.Utils;

import sample.Components.CameraComponent;
import sample.Rendering.RenderClass;
import sample.VectorMath.FVector;

import java.util.ArrayList;

public class Gizmos {
    private ArrayList<FVector> edges = new ArrayList<FVector>();
    private ArrayList<Integer> colors = new ArrayList<Integer>();

    public void DrawGizmos(RenderClass r, CameraComponent c)
    {
        if(colors.size() == 0)
        {
            return;
        }
        for(int i = 0; i < colors.size(); i++)
        {
            FVector a = c.ToViewCoord(edges.get(2*i));
            FVector b = c.ToViewCoord(edges.get(2*i+1));
            if(a.z < 0 || b.z < 0)
            {
                continue;
            }
            a = c.ToScreenCoord(a);
            b = c.ToScreenCoord(b);
            r.Line((int)a.x, (int)a.y, (int)b.x, (int)b.y, colors.get(i));
        }
        edges.clear();
        colors.clear();
    }

    public void DrawLine(FVector start, FVector end, int color)
    {
        edges.add(start);
        edges.add(end);
        colors.add(color);
    }

    public void DrawCube(FVector center, double size, int color)
    {
        FVector c0 = new FVector(size+center.x,size+center.y,size+center.z);
        FVector c1 = new FVector(-size+center.x,size+center.y,size+center.z);
        FVector c2 = new FVector(size+center.x,-size+center.y,size+center.z);
        FVector c3 = new FVector(-size+center.x,-size+center.y,size+center.z);

        DrawLine(c0, c1, color);
        DrawLine(c1, c3, color);
        DrawLine(c2, c0, color);
        DrawLine(c2, c3, color);

        FVector c4 = new FVector(size+center.x,size+center.y,-size+center.z);
        FVector c5 = new FVector(-size+center.x,size+center.y,-size+center.z);
        FVector c6 = new FVector(size+center.x,-size+center.y,-size+center.z);
        FVector c7 = new FVector(-size+center.x,-size+center.y,-size+center.z);

        DrawLine(c4, c5, color);
        DrawLine(c5, c7, color);
        DrawLine(c4, c6, color);
        DrawLine(c6, c7, color);

        DrawLine(c0, c4, color);
        DrawLine(c1, c5, color);
        DrawLine(c2, c6, color);
        DrawLine(c3, c7, color);
    }


}
