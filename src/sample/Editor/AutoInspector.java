package sample.Editor;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import sample.SceneControl.GameObject;

import java.util.ArrayList;


public class AutoInspector {
    public final double itemHeight = 50;
    public final double width = 200;
    public final double height = 400;
    public final double x = 440;
    public final double y = 25;
    ArrayList<Node> items = new ArrayList<Node>();
    public void DrawInspector(AnchorPane backdrop, GameObject go)
    {
        AddPane(backdrop);

        TextField namefield = new TextField(go.name);
        AddItem(namefield, "Name");
        namefield.textProperty().addListener((obs, oldText, newText) -> {
            go.name = newText;
            System.out.println(go.name);
        });

        for(Node n : items)
        {
            backdrop.getChildren().add(n);
        }
    }

    private void AddPane(AnchorPane bg)
    {
        Pane inspector = new Pane();
        bg.getChildren().add(inspector);
        inspector.setLayoutX(x);
        inspector.setLayoutY(y);
        inspector.setPrefWidth(width);
        inspector.setPrefHeight(height);
        inspector.setStyle("-fx-background-color: #bcbdc0;");
    }

    private void AddItem(Node item, String ident)
    {
        double newX = x+ident.length()*10;
        double newY = (items.size()+1)*itemHeight;
        Label l = new Label(ident + ": ");
        l.setLayoutX(x);
        l.setLayoutY(newY);
        item.setLayoutX(newX);
        item.setLayoutY(newY);
        items.add(l);
        items.add(item);
    }

    /*public Node[] DrawInspector(Object o, Pane pane) throws IllegalAccessException, ClassNotFoundException, NoSuchMethodException,
            InvocationTargetException, InstantiationException, PrivilegedActionException
    {
        Field[] fields = o.getClass().getFields();
        Node[] items = new Node[fields.length];
        for (Field field : fields) {
            if(field.isAccessible())
            {
                continue;
            }
            if (field.getType() == String.class) {
                TextField tf = new TextField((String)field.get(o));
                pane.getChildren().add(tf);
            }
            if (field.getType() == Double.TYPE) {

            }
            if (field.getType() == Integer.TYPE) {

            }
            if (field.getType() == Boolean.TYPE) {

            } else {
                if (field.getType().getSuperclass() == ScriptableComponent.class) {

                }
            }
        }
        return null;
    }*/
}
