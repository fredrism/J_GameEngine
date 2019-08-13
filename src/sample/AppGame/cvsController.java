package sample.AppGame;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.Camera;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import sample.Components.CameraComponent;
import sample.Components.MeshRenderComponent;
import sample.Components.RigidBodyComponent;
import sample.CustomComponents.BasicMovement;
import sample.CustomComponents.Rotator;
import sample.CustomComponents.kkkjk;
import sample.Mesh.Mesh;
import sample.Mesh.OBJReader;
import sample.SceneControl.GameObject;
import sample.Rendering.RenderClass;
import sample.SceneControl.GameSceneClass;
import sample.SceneControl.InputController;
import sample.VectorMath.FVector;

import java.io.*;


public class cvsController {
    @FXML
    private AnchorPane backdrop;

    @FXML
    private ImageView viewport;

    @FXML
    void OnMouseClicked(MouseEvent event) {

    }

    @FXML
    void OnMouseMoved(MouseEvent event) {
        InputController.setMousePosition(new FVector((event.getSceneX()/backdrop.getWidth()) +0.5, (event.getSceneY()/backdrop.getHeight())+0.5, 0));
    }

    @FXML
    void OnMouseReleased(MouseEvent event) {

    }

    public void initialize()
    {
        WritableImage image = new WritableImage(640,400);
        viewport.setImage(image);
        RenderClass r = new RenderClass(image.getPixelWriter(), 640, 400);
        InputController input = new InputController();
        final GameSceneClass scene = new GameSceneClass(r);

        GameObject p = new GameObject(scene);
        p.AddComponent(new MeshRenderComponent(OBJReader.fromFile("C:\\Users\\Fredrik\\IdeaProjects\\Backup\\SWAPP\\src\\sample\\Resources\\sphere.obj"),
                new Image("file:C:\\Users\\Fredrik\\IdeaProjects\\Backup\\SWAPP\\src\\sample\\Resources\\Grunge-Background-010.png")));
        p.getTransform().setPosition(new FVector(0,4,0));
        p.AddComponent(new RigidBodyComponent());
        p.AddComponent(new kkkjk());

        GameObject q = new GameObject(scene);
        q.AddComponent(new MeshRenderComponent(OBJReader.fromFile("SWAPP\\src\\sample\\Resources\\cuboid2.obj"),
                new Image("file:SWAPP\\src\\sample\\Resources\\UVGrid.png")));
        q.getTransform().setPosition(new FVector(0.25,0,0));
        q.AddComponent(new RigidBodyComponent());
        q.GetComponent(RigidBodyComponent.class).IsKinematic = true;

        GameObject cp = new GameObject(scene);
        cp.AddComponent(new Rotator());
        GameObject go = new GameObject(scene);
        go.getTransform().setParent(cp);
        go.AddComponent(new CameraComponent(100, 640, 400));
        go.getTransform().setPosition(new FVector(0,3,-8));

        /*GameObject gm = new GameObject(scene);
        gm.AddComponent(new MeshRenderComponent(OBJReader.fromFile("C:\\Users\\Fredrik\\IdeaProjects\\Backup\\SWAPP\\src\\sample\\Resources\\tera2.obj"),
                                                new Image("file:C:\\Users\\Fredrik\\IdeaProjects\\Backup\\SWAPP\\src\\sample\\Resources\\Grunge-Background-010.png")));
        gm.getTransform().setPosition(new FVector(0,0,0));*/
        scene.setActiveCamera(go.GetComponent(CameraComponent.class));
        scene.SetRenderer(r);
        scene.Start();
    }
}

