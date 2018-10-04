package engineTester;

import org.lwjgl.opengl.Display;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.RawModel;
import renderEngine.Renderer;
import shaders.StaticShader;

public class MainGameLoop {

    public static void main(String[] args) {

        // create main game display
        DisplayManager.createDisplay();

        Loader loader = new Loader();
        Renderer renderer = new Renderer();
        StaticShader shader = new StaticShader();

        // openGL expects verticles to be defined counter clockwise by default
        float[] vericles = {
                // left bottom triangle
                -0.5f, 0.5f, 0,  // V0
                -0.5f, -0.5f, 0, // V1
                0.5f, -0.5f, 0, // V2
                0.5f, 0.5f, 0, // V3
        };

        int[] indices = {
                0, 1, 3, // top left triangle (V0, V1, V3)
                3, 1, 2  // bottom right trangle (V3, V1, V2)
        };

        RawModel model = loader.loadToVAO(vericles, indices);

        // run until window is closed
        while(!Display.isCloseRequested()) {

            // game logic
            renderer.prepare();
            shader.start();
            renderer.render(model);
            shader.stop();
            DisplayManager.updateDisplay();
        }

        shader.cleanUp();
        loader.cleanUp();

        // close window and process after window is closed
        DisplayManager.closeDisplay();
    }
}
