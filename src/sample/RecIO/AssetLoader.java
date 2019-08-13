package sample.RecIO;

import sample.SceneControl.GameObject;
import sample.SceneControl.GameSceneClass;

import java.io.*;

public class AssetLoader {
    public static void SaveScene(GameSceneClass scene, String path)
    {
        try {
            FileOutputStream fileOut = new FileOutputStream(path);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(scene);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    private static GameSceneClass LoadScene(String path)
    {
        GameSceneClass e = null;
        try {
            FileInputStream fileIn = new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            e = (GameSceneClass) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
            return null;
        } catch (ClassNotFoundException c) {
            c.printStackTrace();
            return null;
        }
        return e;
    }

    public static void LoadAdditive(GameSceneClass mainScene, String newScenePath, boolean overrideActiveCamera)
    {
        GameSceneClass newScene = LoadScene(newScenePath);
        mainScene.components.addAll(newScene.components);
        for(GameObject go : newScene.gameObjects)
        {
            go.scene = mainScene;
            mainScene.gameObjects.add(go);
        }
        if(overrideActiveCamera && newScene.activeCamera != null)
        {
            mainScene.setActiveCamera(newScene.activeCamera);
        }
    }

    public static void LoadNewScene(GameSceneClass mainScene, String newScene)
    {
        mainScene.ClearScene();
        LoadAdditive(mainScene, newScene, true);
    }
}
