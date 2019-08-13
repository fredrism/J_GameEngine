package sample.SceneControl;

import sample.Components.ScriptableComponent;
import sample.Components.TransformClass;
import sample.SceneControl.GameSceneClass;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

public class GameObject implements java.io.Serializable{
    public String name = "Schlock";
    private boolean enabled;
    private TransformClass transform = new TransformClass(this);

    private ArrayList<ScriptableComponent> components = new ArrayList<ScriptableComponent>();

    public GameSceneClass scene;


    public GameObject(GameSceneClass scene)
    {
        this.scene = scene;
        scene.gameObjects.add(this);
    }


    public TransformClass getTransform()
    {
        return transform;
    }

    public <T extends ScriptableComponent> void AddComponent(T component)
    {
        for(int i = 0; i < components.size(); i++)
        {
            if(components.get(i).getClass() == component.getClass())
            {
                throw new IllegalStateException("Component already attached to this object");
            }
        }
        components.add(component);
        component.OnAddComponent(scene, this);
    }

    public <T extends ScriptableComponent> void RemoveComponent(T component)
    {
        scene.components.remove(component);
        components.remove(component);
        component = null;
    }

    public <T extends ScriptableComponent> void RemoveComponent(Class<T> component)
    {
        for(int i = 0; i < components.size(); i++)
        {
            if(components.get(i).getClass() == component)
            {
                RemoveComponent(components.get(i));
                return;
            }
        }
    }

    public <T  extends ScriptableComponent> T GetComponent(Class<T> component)
    {
        for(int i = 0; i < components.size(); i++) {
            if (components.get(i).getClass() == component) {
                return component.cast(components.get(i));
            }
        }
        return null;
    }

    public String WriteComponents()
    {
        String s = "";
        for(int i = 0; i < components.size(); i++)
        {
            s += components.get(i).getClass().toString() +", ";
        }
        return s;
    }
}
