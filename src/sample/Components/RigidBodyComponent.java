package sample.Components;

import sample.Mesh.OBJReader;
import sample.Physics.BoxCollider;
import sample.Physics.Collider;
import sample.Physics.MeshCollider;
import sample.VectorMath.FVector;
import sun.text.resources.es.FormatData_es_VE;

import java.awt.*;
import java.util.ArrayList;

public class RigidBodyComponent extends ScriptableComponent implements java.io.Serializable {
    public Collider collider;
    public FVector velocity = new FVector();
    public FVector position;
    FVector lastPosition;
    FVector rotation;
    FVector lastRotation;
    FVector angularVelocity = new FVector();
    FVector force = new FVector();
    FVector torque = new FVector();
    TransformClass transform;
    public boolean IsKinematic;
    public double mass = 1;
    public double drag = 0.001;
    public InterpolationMode mode = InterpolationMode.Interpolate;
    private double lastPhysicTime = 0.001;
    private double time;
    public ArrayList<RigidBodyComponent> collisions = new ArrayList<RigidBodyComponent>();

    public enum InterpolationMode {
        None, Interpolate, Extrapolate
    }

    @Override
    public void OnAwake() {
        transform = getGameObject().getTransform();
        collider = new BoxCollider();
        collider.CalculateInertia(mass, this);
        position = transform.getPosition();
        rotation = transform.getRotation();
        lastPosition = position;
        lastRotation = rotation;
        getGameObject().scene.physics.AddRigidBody(this);
    }

    @Override
    public void Update(double deltaTime) {
        if(collisions.size() == 0)
        {
            getGameObject().scene.gizmos.DrawCube(position, 1, Color.YELLOW.getRGB());
        }
        else
        {
            getGameObject().scene.gizmos.DrawCube(position, 1, Color.RED.getRGB());
        }

        if(!IsKinematic)
        {
            transform.setPosition(FVector.Lerp(lastPosition, position, time/lastPhysicTime));
            transform.Rotate(FVector.Mul(angularVelocity, time/lastPhysicTime));
            time += deltaTime;
        }
    }

    public void FixedUpdate(double deltaTime)
    {
        if(IsKinematic)
        {
            return;
        }
        //FVector dragForce = FVector.Mul(velocity, -drag);
        //force.AddLocal(FVector.Mul(dragForce, deltaTime));
        lastPosition = new FVector(position);
        lastRotation = new FVector(rotation);

        FVector linearAccel = FVector.Div(force, mass);
        velocity.AddLocal(FVector.Mul(linearAccel, 1));
        position.AddLocal(FVector.Mul(velocity, deltaTime));

        FVector angularAccel = new FVector();
        angularAccel.x = torque.x*collider.getInertia().x;
        angularAccel.y = torque.y*collider.getInertia().y;
        angularAccel.z = torque.z*collider.getInertia().z;
        angularVelocity.AddLocal(FVector.Mul(angularAccel, deltaTime));
        rotation.AddLocal(FVector.Mul(angularVelocity, deltaTime));
        //transform.setPosition(position);
        transform.setRotation(rotation);
        force = new FVector();
        torque = new FVector();
        lastPhysicTime = deltaTime;
        time = 0.00001;
    }

    public void AddForce(FVector force)
    {
        this.force.AddLocal(force);
    }

    public void AddVelocityChange(FVector change)
    {
        velocity.AddLocal(change);
    }

    public void OnCollisionEnter(ArrayList<RigidBodyComponent> other)
    {
        collisions.addAll(other);
    }
}