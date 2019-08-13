package sample.VectorMath;

public class FVector implements java.io.Serializable{
    public double x,y,z,w;

    public FVector(double x, double y, double z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = 1;
    }

    public FVector()
    {

    }

    public FVector(FVector Clone)
    {
        x = Clone.x;
        y = Clone.y;
        z = Clone.z;
        w = Clone.w;
    }

    public FVector(double x, double y, double z, double w)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public double magnitude()
    {
        return Math.sqrt(x*x+y*y+z*z+w*w);
    }

    public FVector normalized()
    {
        return FVector.Div(this, magnitude());
    }

    //__Operators

    public static FVector Add(FVector A, FVector B)
    {
        return new FVector(A.x+B.x, A.y+B.y, A.z+B.z, A.w);
    }

    public void AddLocal(FVector B)
    {
        x+=B.x;
        y+=B.y;
        z+=B.z;
    }

    public static FVector Sub(FVector A, FVector B)
    {
        return new FVector(A.x-B.x, A.y-B.y, A.z-B.z, A.w);
    }

    public static FVector Mul(FVector A, double B)
    {
        return new FVector(A.x*B, A.y*B, A.z*B, A.w*B);
    }

    public void MulLocal(double B)
    {
        x *= B;
        y *= B;
        z *= B;
    }

    public static FVector Div(FVector A, double B)
    {
        return new FVector(A.x/B, A.y/B, A.z/B, A.w/B);
    }

    public static double Dot(FVector A, FVector B)
    {
        return A.x*B.x + A.y*B.y + A.z*B.z;
    }

    public static FVector Cross(FVector A, FVector B)
    {
        FVector C = new FVector();
        C.x = A.y *B.z - A.z*B.y;
        C.y = A.z*B.x - A.x*B.z;
        C.z = A.x*B.y - A.y*B.x;
        return C;
    }

    public static double Distance(FVector A, FVector B)
    {
        return Math.sqrt((B.x-A.x)*(B.x-A.x) + (B.y-A.y)*(B.y-A.y));
    }

    public static double SqrDistance(FVector A, FVector B)
    {
        return (B.x-A.x)*(B.x-A.x) + (B.y-A.y)*(B.y-A.y);
    }


    public static FVector Parse(String s)
    {
        String[] l = s.split(" ");
        double x = Double.parseDouble(l[1]);
        double y = Double.parseDouble(l[2]);
        double z = (l.length == 3) ? 0 : Double.parseDouble(l[3]);
        return new FVector(x,y,z);
    }

    public static boolean PointInTri(FVector point, FVector v0, FVector v1, FVector v2)
    {
        double x = point.x;
        double y = point.y;
        double x1 = v0.x;
        double x2 = v1.x;
        double x3 = v2.x;
        double y1 = v0.y;
        double y2 = v1.y;
        double y3 = v2.y;
        double a = ((y2 - y3) * (x - x3) + (x3 - x2) * (y - y3)) / ((y2 - y3) * (x1 - x3) + (x3 - x2) * (y1 - y3));
        double b = ((y3 - y1) * (x - x3) + (x1 - x3) * (y - y3)) / ((y2 - y3) * (x1 - x3) + (x3 - x2) * (y1 - y3));
        double c = 1-a-b;
        if(0 <= a && a <= 1 && 0 <= b && b <= 1 && 0 <= c && c <= 1)
        {
            return true;
        }
        return false;
    }

    public static FVector Lerp(FVector A, FVector B, double t)
    {
        return new FVector((1-t)*A.x + t*B.x, (1-t)*A.y + t*B.y, (1-t)*A.z + t*B.z);
    }

    public String toString()
    {
        return String.valueOf(x) + ", " +String.valueOf(y) + ", " + String.valueOf(z) + ", " + String.valueOf(w);
    }
}

