package sample.VectorMath;

public class FMath {
    public static FVector Mix(FVector v0, FVector v1, FVector v2, FVector factor)
    {
        return new FVector( v0.x*factor.x + v1.x*factor.y + v2.x*factor.z,
                v0.y*factor.x + v1.y*factor.y + v2.y*factor.z,
                v0.z*factor.x + v1.z*factor.y + v2.z*factor.z);
    }

    public static FVector Interpolate(FVector p, FVector v0, FVector v1, FVector v2)
    {
        double s = Heron(v0, v1, v2);
        double a = Heron(p, v1, v2);
        double b = Heron(p, v2, v0);
        double c = Heron(p, v0, v1);
        return new FVector(a/s, b/s, c/s);
    }

    public static double Heron(FVector A, FVector B, FVector C)
    {
        double a = FVector.Distance(A, B);
        double b = FVector.Distance(A, C);
        double c = FVector.Distance(B, C);
        double s = (a+b+c)/2;
        return Math.pow(Math.abs((s*(s-a)*(s-b)*(s-c))), 0.5);
    }
}
