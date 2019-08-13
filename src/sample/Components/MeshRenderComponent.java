package sample.Components;

import javafx.scene.image.Image;
import sample.Mesh.Mesh;
import sample.SceneControl.GameObject;
import sample.SceneControl.GameSceneClass;
import sample.VectorMath.FVector;

@ExecuteInEditor
public class MeshRenderComponent extends ScriptableComponent implements java.io.Serializable{
    private Mesh mesh;
    private Image mainTexture;

    public void setMesh(Mesh mesh) {
        this.mesh = mesh;
    }

    public void setMainTexture(Image mainTexture) {
        this.mainTexture = mainTexture;
    }

    public MeshRenderComponent(Mesh mesh, Image mainTexture) {
        this.mesh = mesh;
        this.mainTexture = mainTexture;
    }

    @Override
    public void Update(double deltaTime)
    {
        getGameObject().scene.RenderQueue.add(this);
    }

    public Mesh getMesh()
    {
        return mesh;
    }
    public Image getMainTexture()
    {
        return mainTexture;
    }
}
