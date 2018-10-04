package engineTester;

import org.lwjgl.opengl.Display;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.RawModel;
import renderEngine.Renderer;

public class MainGameLoop {

    public static void main(String[] args) {

        // create main game display
        DisplayManager.createDisplay();

        Loader loader = new Loader();
        Renderer renderer = new Renderer();

        // openGL expects verticles to be defined counter clockwise by default
        float[] vericles = {
                // left bottom triangle
                -0.5f, 0.5f, 0f,
                -0.5f, -0.5f, 0f,
                0.5f, -0.5f, 0f,

                // right top triangle
                0.5f, -0.5f, 0f,
                0.5f, 0.5f, 0f,
                -0.5f, 0.5f, 0f,
        };

        RawModel model = loader.loadToVAO(vericles);

        // run until window is closed
        while(!Display.isCloseRequested()) {
            renderer.prepare();

            // game logic
            renderer.render(model);

            //render
            DisplayManager.updateDisplay();
        }

        loader.cleanUp();

        // close window and process after window is closed
        DisplayManager.closeDisplay();
    }
}
