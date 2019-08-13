package sample.Components;
import sample.SceneControl.GameObject;
import sample.VectorMath.*;

import java.util.ArrayList;

public class TransformClass implements java.io.Serializable{
    private Matrix posMatrix;
    private Matrix rotMatrix;
    private Matrix scaleMatrix;
    private Matrix transformMatrix;

    public Matrix getTransformMatrix()
    {
        return transformMatrix;
    }

    public FVector getPosition() {
        return position;
    }

    public void setPosition(FVector position) {
        this.position = position;
    }

    public FVector getRotation() {
        return rotation;
    }

    public void setRotation(FVector rotation) {
        this.rotation = rotation;
    }

    public FVector getScale() {
        return scale;
    }

    public void setScale(FVector scale) {
        this.scale = scale;
    }

    private FVector position = new FVector();
    private FVector rotation = new FVector();
    private FVector scale = new FVector(1,1,1);
    private GameObject parent;
    public ArrayList<GameObject> children;
    private GameObject gameobject;

    public TransformClass(GameObject owner)
    {
        gameobject = owner;
        children = new ArrayList<GameObject>();
    }

    public void setParent(GameObject parent)
    {
        if(parent == null)
        {
            gameobject.scene.gameObjects.add(gameobject);
        }

        if(this.parent != null)
        {
            parent.getTransform().children.remove(gameobject);
        }
        else
        {
            gameobject.scene.gameObjects.remove(gameobject);
        }
        this.parent = parent;
        parent.getTransform().children.add(gameobject);
    }
    public GameObject getParent()
    {
        return parent;
    }

    public void UpdateMatrices()
    {
        rotation = new FVector(rotation.x%360, rotation.y%360, rotation.z%360);
        FVector r = new FVector(Math.toRadians(rotation.x), Math.toRadians(rotation.y), Math.toRadians(rotation.z));

        posMatrix = Matrix.TranslateMatrix(position.x, position.y, position.z);
        rotMatrix = Matrix.RotationMatrix(r.x, r.y, r.z);
        scaleMatrix = Matrix.ScaleMatrix(scale.x, scale.y, scale.z);
        transformMatrix = Matrix.MulHuh(Matrix.MulHuh(scaleMatrix, rotMatrix), posMatrix);
        if(parent != null)
        {
            transformMatrix = Matrix.MulHuh(transformMatrix, parent.getTransform().transformMatrix);
        }


        for(GameObject go : children)
        {
            go.getTransform().UpdateMatrices();
        }
    }

    public FVector applyTransforms(FVector point)
    {
        if(transformMatrix == null)
        {
            UpdateMatrices();
        }
        return Matrix.MulPoint(point, transformMatrix, true);
    }

    public FVector getForwardVector()
    {
        return Matrix.MulPoint(new FVector(0,0,1), rotMatrix, true);
    }

    public void Move(FVector delta)
    {
        position = FVector.Add(position, delta);
    }

    public void Rotate(FVector delta)
    {
        rotation = FVector.Add(rotation, delta);
    }
}
