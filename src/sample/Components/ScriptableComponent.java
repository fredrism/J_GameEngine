package sample.Components;

import sample.SceneControl.GameObject;
import sample.SceneControl.GameSceneClass;

public abstract class ScriptableComponent implements java.io.Serializable{
    private boolean enabled;
    private GameObject gameObject;
    private GameSceneClass scene;

    public GameObject getGameObject()
    {
        return gameObject;
    }

    public boolean enabled()
    {
        return enabled;
    }

    public ScriptableComponent()
    {
    }

    public final void OnAddComponent(GameSceneClass scene, GameObject gameObject)
    {
        this.scene = scene;
        this.gameObject = gameObject;
        Enable();
        if(gameObject.scene.isRunning)
        {
            OnAwake();
        }
    }

    public void Update(double deltaTime)
    {

    }

    public void OnAwake()
    {

    }

    public final void Enable()
    {
        if(enabled)
        {
            return;
        }
        enabled = true;
        scene.components.add(this);
    }

    public final void Disable()
    {
        if(!enabled)
        {
            return;
        }
        enabled = false;
        scene.components.remove(this);
    }
}
