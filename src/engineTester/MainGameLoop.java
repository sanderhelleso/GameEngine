package engineTester;

import org.lwjgl.opengl.Display;
import renderEngine.DisplayManager;

public class MainGameLoop {

    public static void main(String[] args) {

        // create main game display
        DisplayManager.createDisplay();

        // run until window is closed
        while(!Display.isCloseRequested()) {

            // game logic
            //render
            DisplayManager.updateDisplay();
        }

        // close window and process after window is closed
        DisplayManager.closeDisplay();
    }
}
