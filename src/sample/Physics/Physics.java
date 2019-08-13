package sample.Physics;

import sample.Components.RigidBodyComponent;
import sample.Components.TransformClass;
import sample.SceneControl.GameObject;
import sample.SceneControl.GameSceneClass;
import sample.Utils.Tuple;
import sample.VectorMath.FVector;
import sun.text.resources.es.FormatData_es_VE;

import java.util.ArrayList;
import java.util.Collections;

public class Physics {
    public ArrayList<RigidBodyComponent> rigidBodies = new ArrayList<RigidBodyComponent>();
    public static final int Frequency = 2;
    public FVector gravity = new FVector(0, -1*Frequency, 0);

    public void AddRigidBody(RigidBodyComponent rb)
    {
        rigidBodies.add(rb);
    }

    public void Step(double deltaTime)
    {
        for(RigidBodyComponent rb : rigidBodies)
        {
            rb.collisions.clear();
            rb.AddForce(gravity);
            for(RigidBodyComponent rb2 : rigidBodies)
            {
                if(rb2 != rb)
                {
                    Tuple<Double, FVector> hit = DoCollisionTest(rb, rb2);
                    if(hit != null)
                    {
                        rb.velocity = new FVector(0, 4, 0);
                    }
                }
            }
            rb.FixedUpdate(deltaTime*Frequency);
        }
    }

    public Tuple<Double, FVector> DoCollisionTest(RigidBodyComponent a, RigidBodyComponent b)
    {
        Tuple<Double, FVector> hit = null;
        for(int i = 0; i < a.collider.getTriangleCount(); i++)
        {
            FVector p0 = a.collider.getTriangle(i).vertices[0];
            FVector p1 = a.collider.getTriangle(i).vertices[1];
            FVector p2 = a.collider.getTriangle(i).vertices[2];

            hit = RayCaster.LineColliderIntersect(p0, p1, b.collider);
            if(hit != null)
            {
                return hit;
            }
            hit = RayCaster.LineColliderIntersect(p1, p2, b.collider);
            if(hit != null)
            {
                return hit;
            }
            hit = RayCaster.LineColliderIntersect(p2, p0, b.collider);
            if(hit != null)
            {
                return hit;
            }
        }
        return null;
    }

    public class RayCastHit
    {
        public final GameObject other;
        public final FVector point;
        public final double distance;


        public RayCastHit(GameObject other, FVector point, double distance) {
            this.other = other;
            this.point = point;
            this.distance = distance;
        }
    }

    public RayCastHit RayCast(FVector start, FVector direction)
    {
        return RayCast(start, direction, 99999999);
    }

    public RayCastHit RayCast(FVector start, FVector direction, double MaxDistance)
    {
        double m_dist = MaxDistance*MaxDistance;
        FVector q2 = FVector.Add(start, direction);
        int rb = 0;
        Tuple<Double, FVector> closest = null;

        double dist = 999999999;
        for(int i = 0; i < rigidBodies.size(); i++)
        {
            if(true)//FVector.Distance(rigidBodies.get(i).position, start)-rigidBodies.get(i).collider.getSafeRadius() < MaxDistance)
            {
                Tuple<Double, FVector> hit = RayCaster.LineColliderIntersect(start, q2, rigidBodies.get(i).collider);
                if(hit != null)
                {
                    double n_dist = FVector.SqrDistance(hit.b, start);
                    if(hit.a >= 0 && n_dist < m_dist && n_dist < dist)
                    {
                        rb = i;
                        closest = hit;
                        dist = n_dist;
                    }
                }
            }
        }

        if(closest == null)
        {
            return null;
        }
        else
        {
            return new RayCastHit(rigidBodies.get(rb).getGameObject(), closest.b, Math.sqrt(dist));
        }
    }

    public RayCastHit LineCast(FVector start, FVector end, Collider collider)
    {
        Tuple<Double, FVector> hit = RayCaster.LineColliderIntersect(start, end, collider);

        if(hit != null)
        {
            double dist = FVector.SqrDistance(hit.b, start);
            return new RayCastHit(null, hit.b, Math.sqrt(dist));
        }
        return null;
    }
}
