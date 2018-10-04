package engineTester;

import entities.Entity;
import models.RawModel;
import models.TexturedModel;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.Renderer;
import shaders.StaticShader;
import textures.ModelTexture;

public class MainGameLoop {

    public static void main(String[] args) {

        // create main game display
        DisplayManager.createDisplay();

        Loader loader = new Loader();
        StaticShader shader = new StaticShader();
        Renderer renderer = new Renderer(shader);

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

        float[] textureCoords = {
                0, 0, // V0
                0, 1, // V1
                1, 1, // V2
                1, 0  // V3
        };

        RawModel model = loader.loadToVAO(vericles, textureCoords, indices);

        TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("image")));


        Entity entity = new Entity(staticModel, new Vector3f(0, 0, -1), 0, 0, 0, 1);

        // run until window is closed
        while(!Display.isCloseRequested()) {

            entity.increasePosition(0, 0, -0.1f);

            // game logic
            renderer.prepare();
            shader.start();
            renderer.render(entity, shader);
            shader.stop();
            DisplayManager.updateDisplay();
        }

        shader.cleanUp();
        loader.cleanUp();

        // close window and process after window is closed
        DisplayManager.closeDisplay();
    }
}
