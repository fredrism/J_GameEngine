package sample.Rendering;

import sample.Components.MeshRenderComponent;
import sample.Mesh.Triangle;

public interface IRenderClass {
    public void Render(MeshRenderComponent mrc);
    public void Show();
}
