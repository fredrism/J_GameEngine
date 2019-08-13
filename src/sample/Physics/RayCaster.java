package sample.Physics;

import sample.Components.TransformClass;
import sample.Mesh.Triangle;
import sample.Utils.Tuple;
import sample.VectorMath.FVector;

public class RayCaster {

    private static double SignedTetraVolume(FVector a, FVector b, FVector c, FVector d)
    {
        return (1.0/6.0) * FVector.Dot(FVector.Cross(FVector.Sub(b,a), FVector.Sub(c, a)), FVector.Sub(d, a));
    }

    private static Tuple<Double, FVector> LineTriIntersect(Triangle tri, FVector q1, FVector q2)
    {
        double s1 = SignedTetraVolume(q1, tri.vertices[0],tri.vertices[1],tri.vertices[2]);
        double s2 = SignedTetraVolume(q2, tri.vertices[0],tri.vertices[1],tri.vertices[2]);

        if(Math.signum(s1) != Math.signum(s2))
        {
            double s3 = SignedTetraVolume(q1, q2, tri.vertices[0], tri.vertices[1]);
            double s4 = SignedTetraVolume(q1, q2, tri.vertices[1], tri.vertices[2]);
            double s5 = SignedTetraVolume(q1, q2, tri.vertices[2], tri.vertices[0]);
            if (Math.signum(s3) == Math.signum(s4) && Math.signum(s4) == Math.signum(s5)) {
                FVector n = FVector.Cross(FVector.Sub(tri.vertices[1], tri.vertices[0]), FVector.Sub(tri.vertices[2], tri.vertices[0]));
                double t = FVector.Dot(FVector.Sub(tri.vertices[0], q1), n) / FVector.Dot(FVector.Sub(q2, q1), n);
                FVector p = FVector.Add(q1, FVector.Mul(FVector.Sub(q2, q1), t));
                return new Tuple<Double, FVector>(t, p);
            }
        }
        return null;
    }

    protected static Tuple<Double, FVector> LineColliderIntersect(FVector q1, FVector q2, Collider collider)
    {
        Tuple<Double, FVector> closestHit = null;
        for(int i = 0; i < collider.getTriangleCount(); i++)
        {
            Tuple<Double, FVector> hit = LineTriIntersect(collider.getTriangle(i), q1, q2);
            if(hit != null)
            {
                if(closestHit != null)
                {
                    if(hit.a < closestHit.a)
                    {
                        closestHit = hit;
                    }
                }
                else
                {
                    closestHit = hit;
                }
            }
        }
        return closestHit;
    }
}
