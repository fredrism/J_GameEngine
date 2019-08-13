package sample.Mesh;
import javafx.scene.shape.Path;
import sample.VectorMath.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class OBJReader {

    public static Mesh fromFile(String path)
    {
        List<String> l;
        try{
            l = readFile(path);
        }
        catch (IOException e)
        {
            System.out.println("Error opening mesh");
            return new Mesh(new FVector[0], new FVector[0], new FVector[0], new int[0], "error");
        }
        String name = "new mesh";
        List<FVector> tmpvertices = new ArrayList<FVector>();
        List<FVector> tmpnormals = new ArrayList<FVector>();
        List<FVector> tmpUVs = new ArrayList<FVector>();
        List<Integer> tmpTris = new ArrayList<Integer>();

        for(int i = 0; i < l.size(); i++)
        {
            switch (l.get(i).substring(0, 2))
            {
                case "o ":
                    name = l.get(i).split(" ")[1];
                    break;
                case "v ":
                    tmpvertices.add(FVector.Parse(l.get(i)));
                    break;
                case "vn":
                    tmpnormals.add(FVector.Parse(l.get(i)));
                    break;
                case "vt":
                    tmpUVs.add(FVector.Parse(l.get(i)));
                    break;
                case "f ":
                    String[] l0 = l.get(i).split(" ");
                    for(int o = 1; o < l0.length; o++)
                    {
                        String[] l1 = l0[o].split("/");
                        for(int p = 0; p < l1.length; p++)
                        {
                            int in = Integer.parseInt(l1[p]);
                            tmpTris.add(in-1);
                        }
                    }
                    break;

                default:
                    break;
            }
        }

        return new Mesh(tmpvertices.toArray(new FVector[0]), tmpnormals.toArray(new FVector[0]), tmpUVs.toArray(new FVector[0]), tmpTris.stream().mapToInt(i->i).toArray(), name);
    }

    private static List<String> readFile(String path)
            throws IOException
    {
        path = Paths.get("src/" + path).toAbsolutePath().toString();
        List<String> encoded = Files.readAllLines(Paths.get(path));
        return encoded;
    }
}
