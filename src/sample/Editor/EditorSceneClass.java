package sample.Editor;

import sample.Components.CameraComponent;
import sample.Components.ExecuteInEditor;
import sample.Components.MeshRenderComponent;
import sample.Components.ScriptableComponent;
import sample.SceneControl.GameObject;
import sample.SceneControl.GameSceneClass;
import sample.SceneControl.InputController;

import java.util.ArrayList;

public class EditorSceneClass extends GameSceneClass {
    boolean IsInEditMode;

    public EditorSceneClass(GameSceneClass scene)
    {
        gameObjects = scene.gameObjects;
        components = scene.components;
        activeCamera = scene.activeCamera;
    }

    @Override
    public void UpdateLoop(double deltaTime)
    {
        InputController.ProcessInput();
        RenderQueue.clear();
        for(int i = 0; i < components.size(); i++)
        {
            if(components.get(i).enabled() && (!IsInEditMode || components.get(i).getClass().isAnnotationPresent(ExecuteInEditor.class)))
            {
                components.get(i).Update(deltaTime);
            }
        }

        super.Render();
    }
}
