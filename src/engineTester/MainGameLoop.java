package engineTester;

import entities.Camera;
import entities.Entity;
import entities.Light;
import models.RawModel;
import models.TexturedModel;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.*;
import shaders.StaticShader;
import terrain.Terrain;
import textures.ModelTexture;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainGameLoop {

    public static void main(String[] args) {

        // create main game display
        DisplayManager.createDisplay();

        Loader loader = new Loader();

        RawModel model = OBJLoader.loadObjModel("tree", loader);

        TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("tree")));

        ModelTexture texture = staticModel.getTexture();
        texture.setShineDamper(10);
        texture.setReflectivity(1);

        /*Entity entity = new Entity(staticModel, new Vector3f(0, 0, -50), 0, 0, 0, 1);*/

        Light light = new Light(new Vector3f(20000,20000,2000),new Vector3f(1,1,1));

        Terrain terrain = new Terrain(0, 0, loader, new ModelTexture(loader.loadTexture("grass")));
        Terrain terrain2 = new Terrain(1, 0, loader, new ModelTexture(loader.loadTexture("grass")));
        TexturedModel grass = new TexturedModel(OBJLoader.loadObjModel("grassModel", loader), new ModelTexture(loader.loadTexture("grassTexture")));
        TexturedModel fern = new TexturedModel(OBJLoader.loadObjModel("fern", loader), new ModelTexture(loader.loadTexture("fern")));


        grass.getTexture().setHasTransparancy(true);
        grass.getTexture().setUseFakeLighting(true);
        fern.getTexture().setHasTransparancy(true);
        fern.getTexture().setUseFakeLighting(true);

        Camera camera = new Camera();

        MasterRenderer renderer = new MasterRenderer();

        Random random = new Random();

        List<Entity> entities = new ArrayList<Entity>();
        for(int i = 0; i < 100; i++) {
            entities.add(new Entity(staticModel, new Vector3f(random.nextFloat()*800 - 400,0,random.nextFloat() * -600),0,0,0,3));
            entities.add(new Entity(grass, new Vector3f(random.nextFloat()*800 - 400,0,random.nextFloat() * -600),0,0,0,3));
            entities.add(new Entity(fern, new Vector3f(random.nextFloat()*800 - 400,0,random.nextFloat() * -600),0,0,0,3));
        }

        // run until window is closed
        while(!Display.isCloseRequested()) {

            camera.move();


            renderer.processTerrain(terrain);
            renderer.processTerrain(terrain2);

            //renderer.processEntity(entity);
            for(Entity entity : entities){
                renderer.processEntity(entity);
            }

            // game logic
            renderer.render(light, camera);
            DisplayManager.updateDisplay();
        }

        // clean up
        renderer.cleanUp();
        loader.cleanUp();

        // close window and process after window is closed
        DisplayManager.closeDisplay();
    }
}
