package sample.SceneControl;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import sample.VectorMath.FVector;

import java.security.Key;
import java.util.ArrayList;

public class InputController {
    public static ArrayList<KeyEvent> keyEvent = new ArrayList<KeyEvent>();
    private static FVector INTERNAL_mouseposition = new FVector();

    private static FVector currentMousePosition;
    private static FVector lastMousePosition;

    public static void setMousePosition(FVector mp)
    {
        INTERNAL_mouseposition = mp;
    }

    public static FVector mouseDelta()
    {
        return FVector.Sub(currentMousePosition, lastMousePosition);
    }

    public static boolean getKey(KeyCode key) {
        return mkey[key.ordinal()];
    }

    public static boolean getKeyDown(KeyCode key) {
        return keydown[key.ordinal()];
    }

    public static boolean getKeyUp(KeyCode key) {
        return keyup[key.ordinal()];
    }

    private static boolean[] mkey;
    private static boolean[] keydown;
    private static boolean[] keyup;

    public InputController()
    {
        int size = KeyCode.values().length;
        mkey = new boolean[size];
        keydown = new boolean[size];
        keyup = new boolean[size];
        currentMousePosition = new FVector();
        INTERNAL_mouseposition = new FVector();
    }

    public static void ProcessInput()
    {
        lastMousePosition = currentMousePosition;
        currentMousePosition = INTERNAL_mouseposition;
        for(int i = 0; i < mkey.length; i++)
        {
            keydown[i] = false;
            keyup[i] = false;
        }

        while(keyEvent.size() > 0)
        {
            KeyEvent k = keyEvent.get(0);
            keyEvent.remove(0);

            if(keyEvent == null)
            {
                return;
            }

            if(k.getEventType() == KeyEvent.KEY_PRESSED && !mkey[k.getCode().ordinal()])
            {
                SetKeyDown(k.getCode());
            }
            if(k.getEventType() == KeyEvent.KEY_RELEASED)
            {
                SetKeyUp(k.getCode());
            }
            k = null;
        }
    }

    static void SetKeyDown(KeyCode key)
    {
        mkey[key.ordinal()] = true;
        keydown[key.ordinal()] = true;
    }

    static void SetKeyUp(KeyCode key)
    {
        mkey[key.ordinal()] = false;
        keyup[key.ordinal()] = true;
    }
}
