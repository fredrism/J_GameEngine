package sample.RecIO;

public class GlobalSettings {
    public static final String WorkingDirectory = "F:\\Users\\Fredrik\\Documents\\JGE\\";
    public static final String WorkingSceneName = "tempScene.ser";
    public static String FullScenePath()
    {
        return WorkingDirectory + WorkingSceneName;
    }

    public static String EntryScene = "";
}
