package sample.Rendering;
import javafx.scene.Camera;
import javafx.scene.image.Image;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;
import java.awt.color.*;
import javafx.scene.image.PixelWriter;
import sample.Components.CameraComponent;
import sample.Components.MeshRenderComponent;
import sample.Components.TransformClass;
import sample.Mesh.Mesh;
import sample.Mesh.OBJReader;
import sample.Mesh.Triangle;
import sample.VectorMath.FVector;
import sample.VectorMath.Vec2i;
import sample.VectorMath.Vec3i;

import java.math.*;
import java.util.Random;

public class RenderClass {
    PixelWriter pxwrt;
    double width;
    double height;
    int iwidth, iheight;
    DepthMap zBuffer;
    CameraComponent camera;
    int[] pixelBuffer;
    int ClearColor;


    public RenderClass(PixelWriter px, double width, double height)
    {
        this.pxwrt = px;
        this.width = width;
        this.height = height;
        zBuffer = new DepthMap((int)width, (int)height);
        iwidth = (int)width;
        iheight = (int)height;
        pixelBuffer = new int[iwidth*iheight];
        ClearColor = java.awt.Color.BLACK.getRGB();
    }

    public void setCamera(CameraComponent camera)
    {
        this.camera = camera;
    }

    public void ClearBuffers()
    {
        zBuffer.Clear();

        for(int i = 0; i < pixelBuffer.length; i++)
        {
            pixelBuffer[i] = ClearColor;
        }
    }

    public void Render(MeshRenderComponent mrc)
    {
        if(camera == null)
        {
            return;
        }

        PixelReader textureReader = mrc.getMainTexture().getPixelReader();

        double textureWidth = mrc.getMainTexture().getWidth();
        double textureHeight = mrc.getMainTexture().getHeight();

        Mesh m = mrc.getMesh();
        TransformClass meshTransform = mrc.getGameObject().getTransform();

        for(int i = 0; i < m.GetTriangleCount(); i++)
        {
            Triangle t = m.getTriangle(i);
            FVector sv0 = camera.ToViewCoord(meshTransform.applyTransforms(t.vertices[0]));
            FVector sv1 = camera.ToViewCoord(meshTransform.applyTransforms(t.vertices[1]));
            FVector sv2 = camera.ToViewCoord(meshTransform.applyTransforms(t.vertices[2]));

            FVector v0 = camera.ToScreenCoord(sv0);
            FVector v1 = camera.ToScreenCoord(sv1);
            FVector v2 = camera.ToScreenCoord(sv2);

            if(sv0.z <= 0 || sv1.z <= 0 || sv2.z <= 0)
            {
                continue;
            }
            FillTriangleInline(new FVector[]{v0, v1, v2}, t.uvs, t.normals, textureReader, textureWidth, textureHeight);
        }
    }

    public void FlipBuffer()
    {
        pxwrt.setPixels(0,0,iwidth, iheight, PixelFormat.getIntArgbInstance(), pixelBuffer, 0, iwidth);
    }


    void FillTriangleInline(FVector pts[], FVector[] UVs, FVector[] normals , PixelReader pxr, double tex_width, double tex_height)
    {
        /*FVector bbmin = new FVector(Double.MAX_VALUE, Double.MAX_VALUE, 0);
        FVector bbmax = new FVector(-Double.MAX_VALUE, -Double.MAX_VALUE, 0);*/
        int bbminx = Integer.MAX_VALUE;
        int bbminy = Integer.MAX_VALUE;
        int bbmaxx = -Integer.MAX_VALUE;
        int bbmaxy = -Integer.MAX_VALUE;
        double clampx = width-1;
        double clampy = height-10;

        for(int i = 0; i < 3; i++)
        {
            bbminx = (int)Math.max(0.0, Math.min(bbminx, pts[i].x));
            bbminy = (int)Math.max(0.0, Math.min(bbminy, pts[i].y));

            bbmaxx = (int)Math.min(clampx, Math.max(bbmaxx, pts[i].x));
            bbmaxy = (int)Math.min(clampy, Math.max(bbmaxy, pts[i].y));
        }

        int px = 0;
        int py = 0;

        double pUVx;
        double pUVy;

        double Ax;
        double Ay;
        double Az;
        double Bx;
        double By;
        double Bz;

        double Cx;
        double Cy;
        double Cz;

        double bc_screenX;
        double bc_screenY;
        double bc_screenZ;

        for(px = bbminx; px <= (bbmaxx+1); px++)
        {
            for(py = bbminy; py <= (bbmaxy+1); py++)
            {
                Ax = pts[2].x-pts[0].x;
                Ay = pts[1].x - pts[0].x;
                Az = pts[0].x -px;
                Bx = pts[2].y-pts[0].y;
                By = pts[1].y-pts[0].y;
                Bz = pts[0].y-py;

                Cx = Ay*Bz - Az*By;
                Cy = Az*Bx - Ax*Bz;
                Cz = Ax*By - Ay*Bx;


                if(Math.abs(Cz) < 1)
                {
                    bc_screenX = -1;
                    bc_screenY = 1;
                    bc_screenZ = 1;
                }
                else
                {
                    bc_screenX = 1.0-(Cx+Cy)/Cz;
                    bc_screenY = Cy/Cz;
                    bc_screenZ = Cx/Cz;
                }

                if(bc_screenX<0 || bc_screenY<0 || bc_screenZ<0)
                {
                    continue;
                }
                double z = pts[0].z*bc_screenX + pts[1].z*bc_screenY + pts[2].z*bc_screenZ;
                if(zBuffer.TestDepth(px, py, z))
                {
                    pUVx = UVs[0].x*bc_screenX + UVs[1].x*bc_screenY + UVs[2].x*bc_screenZ;
                    pUVy = UVs[0].y*bc_screenX + UVs[1].y*bc_screenY + UVs[2].y*bc_screenZ;
                    pixelBuffer[px+((iheight-2-py)*iwidth)] = pxr.getArgb((int)(pUVx*tex_width), (int)((1-pUVy)*tex_height));
                }
            }
        }

    }

    @Deprecated
    void FillTriangle(FVector pts[], FVector[] UVs, PixelReader pxr, double tex_width, double tex_height)
    {
        FVector bbmin = new FVector(Double.MAX_VALUE, Double.MAX_VALUE, 0);
        FVector bbmax = new FVector(-Double.MAX_VALUE, -Double.MAX_VALUE, 0);
        double clampx = width-1;
        double clampy = height-10;

        for(int i = 0; i < 3; i++)
        {
            bbmin.x = Math.max(0.0, Math.min(bbmin.x, pts[i].x));
            bbmin.y = Math.max(0.0, Math.min(bbmin.y, pts[i].y));

            bbmax.x = Math.min(clampx, Math.max(bbmax.x, pts[i].x));
            bbmax.y = Math.min(clampy, Math.max(bbmax.y, pts[i].y));
        }
        Vec2i p = new Vec2i(0,0);
        FVector pointUV = new FVector();
        for(p.x = (int)bbmin.x; p.x <= (int)(bbmax.x+1); p.x++)
        {
            for(p.y = (int)bbmin.y; p.y <= (int)(bbmax.y+1); p.y++)
            {
                FVector bc_screen = fastbarycentric(pts, p);

                if(bc_screen.x<0 || bc_screen.y<0 || bc_screen.z<0)
                {
                    continue;
                }
                double z = pts[0].z*bc_screen.x + pts[1].z*bc_screen.y + pts[2].z*bc_screen.z;
                if(zBuffer.TestDepth(p.x, p.y, z))
                {
                    pointUV.x = UVs[0].x*bc_screen.x + UVs[1].x*bc_screen.y + UVs[2].x*bc_screen.z;
                    pointUV.y = UVs[0].y*bc_screen.x + UVs[1].y*bc_screen.y + UVs[2].y*bc_screen.z;
                    pixelBuffer[p.x+((iheight-2-p.y)*iwidth)] = pxr.getArgb((int)(pointUV.x*tex_width), (int)((1-pointUV.y)*tex_height));
                }
            }
        }

    }

    @Deprecated
    FVector fastbarycentric(FVector[] pts, Vec2i p)
    {
        double Ax = pts[2].x-pts[0].x;
        double Ay = pts[1].x - pts[0].x;
        double Az = pts[0].x -p.x;
        double Bx = pts[2].y-pts[0].y;
        double By = pts[1].y-pts[0].y;
        double Bz = pts[0].y-p.y;

        double Cx = Ay*Bz - Az*By;
        double Cy = Az*Bx - Ax*Bz;
        double Cz = Ax*By - Ay*Bx;

        if(Math.abs(Cz) < 1)
        {
            return new FVector(-1, 1, 1);
        }
        return new FVector(1.0-(Cx+Cy)/Cz, Cy/Cz, Cx/Cz);
    }

    @Deprecated
    FVector barycentric(FVector[] pts, FVector p)
    {
        FVector u = FVector.Cross(new FVector(pts[2].x-pts[0].x, pts[1].x - pts[0].x, pts[0].x -p.x), new FVector(pts[2].y-pts[0].y, pts[1].y-pts[0].y, pts[0].y-p.y));
        if(Math.abs(u.z) < 1)
        {
            return new FVector(-1, 1, 1);
        }
        return new FVector(1.-(u.x+u.y)/u.z, u.y/u.z, u.x/u.z);
    }

    @Deprecated
    void ScanTriangle(Vec2i v0, Vec2i v1, Vec2i v2, Color color)
    {
        if(v0.y > v1.y)
        {
            Vec2i tv = v1;
            v1 = v0;
            v0 = tv;
        }
        if(v0.y > v2.y)
        {
            Vec2i tv = v2;
            v2 = v0;
            v0 = tv;
        }
        if(v1.y > v2.y)
        {
            Vec2i tv = v1;
            v2 = v1;
            v1 = tv;
        }
        int total_height = v2.y-v0.y;
        for(int i = 0; i <= total_height; i++)
        {
            boolean second_half = (i > v1.y-v0.y || v1.y==v0.y);
            int segment_height = (second_half) ? v2.y-v1.y : v1.y-v0.y;

            float alpha = (float)(i)/total_height;
            float beta = (float)(i - (second_half ? v1.y-v0.y : 0))/segment_height;

            int ax = (int)(v0.x + (v2.x-v0.x)*alpha);
            int ay = (int)(v0.y + (v2.y-v0.y)*alpha);
            int bx = second_half ? (int)(v1.x+(v2.x-v1.x)*beta) : (int)(v0.x + (v1.x-v0.x)*beta);
            int by = second_half ? (int)(v1.y+(v2.y-v1.y)*beta) : (int)(v0.y + (v1.y-v0.y)*beta);

            if(ax > bx)
            {
                int tx = bx;
                bx = ax;
                ax = tx;

                int ty = by;
                by = ay;
                ay = ty;
            }

            for(int j = ax; j <= bx; j++)
            {
                pxwrt.setColor(j, v0.y+i, color);
            }

        }
    }

    public void Line(int x0, int y0, int x1, int y1, int aRGB)
    {
        if(0 > x0 || 0 > y0 || 0 > x1 || 0 > y1 || x0 > iwidth || y0 > iheight-2 || x1 > iwidth || y1 > iheight-2)
        {
            return;
        }

        boolean steep = false;
        if(Math.abs(x0-x1) < Math.abs(y0-y1))
        {
            int tx = x0;
            x0 = y0;
            y0 = tx;

            tx = x1;
            x1 = y1;
            y1 = tx;

            steep = true;
        }

        if(x0>x1)
        {
            int x2 = x1;
            x1 = x0;
            x0 = x2;

            int y2 = y1;
            y1 = y0;
            y0 = y2;
        }

        for(int x = x0; x <= x1; x++)
        {
            float t = (x-x0)/(float)(x1-x0);
            int y = (int)(y0*(1f-t)+y1*t);
            if(steep)
            {
                pixelBuffer[y+((iheight-2-x)*iwidth)] = aRGB;
            }
            else
            {
                pixelBuffer[x+((iheight-2-y)*iwidth)] = aRGB;
            }
        }
    }
}
