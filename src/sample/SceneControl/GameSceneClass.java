package sample.SceneControl;

import javafx.animation.AnimationTimer;
import sample.Physics.Physics;
import sample.Utils.Gizmos;
import sample.Components.CameraComponent;
import sample.Components.MeshRenderComponent;
import sample.Components.ScriptableComponent;
import sample.Rendering.RenderClass;

import java.util.ArrayList;

public class GameSceneClass implements java.io.Serializable{
    public ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();
    public ArrayList<ScriptableComponent> components = new ArrayList<ScriptableComponent>();
    public ArrayList<MeshRenderComponent> RenderQueue = new ArrayList<MeshRenderComponent>();
    public Physics physics = new Physics();
    public CameraComponent activeCamera;
    public String name;
    public final Gizmos gizmos = new Gizmos();
    public boolean isRunning;
    int counter = 0;

    private AnimationTimer timer = new AnimationTimer()
    {
        long prevNanoTime;
        public void handle(long currentNanoTime)
        {
            double timeDelta = (currentNanoTime-prevNanoTime)/1000000000.0;
            UpdateLoop(timeDelta);
            //System.out.println(1/timeDelta);
            prevNanoTime = currentNanoTime;
        }
    };

    public GameSceneClass()
    {

    }

    transient RenderClass renderClass;

    public GameSceneClass(RenderClass renderClass)
    {
        this.renderClass = renderClass;
    }

    public CameraComponent getActiveCamera()
    {
        return activeCamera;
    }

    public void setActiveCamera(CameraComponent camera)
    {
        activeCamera = camera;
        renderClass.setCamera(activeCamera);
    }

    public void SetRenderer(RenderClass renderClass)
    {
        this.renderClass = renderClass;
    }

    public void UpdateLoop(double deltaTime)
    {
        InputController.ProcessInput();
        RenderQueue.clear();
        for(int i = 0; i < components.size(); i++)
        {
            if(counter == Physics.Frequency)
            {
                physics.Step(deltaTime);
                counter = 0;
            }

            if(components.get(i).enabled())
            {
                components.get(i).Update(deltaTime);
            }
        }

        for(GameObject go : gameObjects)
        {
            go.getTransform().UpdateMatrices();
        }
        activeCamera.UpdateMatrices();
        Render();
        counter++;
    }

    protected void Render()
    {
        renderClass.ClearBuffers();
        for(int i = 0; i < RenderQueue.size(); i++)
        {
            renderClass.Render(RenderQueue.get(i));
        }
        gizmos.DrawGizmos(renderClass, activeCamera);

        renderClass.FlipBuffer();
    }

    public void Start()
    {
        for(ScriptableComponent c : components)
        {
            c.OnAwake();
        }
        isRunning = true;
        timer.start();
    }

    public void Pause()
    {
        isRunning = false;
        timer.stop();
    }

    public void ClearScene()
    {
        Pause();
        gameObjects.clear();
        components.clear();
        RenderQueue.clear();
        activeCamera = null;
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
