package engineTester;

import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import models.RawModel;
import models.TexturedModel;
import objConverter.ModelData;
import objConverter.OBJFileLoader;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.*;
import shaders.StaticShader;
import terrain.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainGameLoop {

    public static void main(String[] args) {

        DisplayManager.createDisplay();
        Loader loader = new Loader();

        // *********TERRAIN TEXTURE STUFF***********
        TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grassy3"));
        TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("dirt"));
        TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("pinkFlowers"));
        TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("mossPath256"));

        TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
        TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));
        Terrain terrain = new Terrain(0,-1,loader, texturePack, blendMap, "heightMap");
        // *****************************************

        TexturedModel tree = new TexturedModel(OBJLoader.loadObjModel("tree", loader), new ModelTexture(loader.loadTexture("tree")));
        TexturedModel grass = new TexturedModel(OBJLoader.loadObjModel("grassModel", loader), new ModelTexture(loader.loadTexture("grassTexture")));
        TexturedModel flower = new TexturedModel(OBJLoader.loadObjModel("grassModel", loader), new ModelTexture(loader.loadTexture("flower")));
        TexturedModel box = new TexturedModel(OBJLoader.loadObjModel("box", loader), new ModelTexture(loader.loadTexture("box")));
        TexturedModel bobble = new TexturedModel(OBJLoader.loadObjModel("lowPolyTree", loader), new ModelTexture(loader.loadTexture("lowPolyTree")));

        ModelTexture fernTexture = new ModelTexture(loader.loadTexture("fern"));

        TexturedModel fern = new TexturedModel(OBJLoader.loadObjModel("fern", loader), fernTexture);

        grass.getTexture().setUseFakeLighting(true);
        flower.getTexture().setUseFakeLighting(true);

        List<Entity> entities = new ArrayList<Entity>();
        Random random = new Random(676452);

        for (int i = 0; i < 100; i++) {
            if (i % 7 == 0) {
                float x = random.nextFloat() * 800 - 400;
                float z = random.nextFloat() * -600;
                float y = terrain.getHeightOfTerrain(x, z);

                entities.add(new Entity(bobble, new Vector3f(x, y, z), 0, random.nextFloat() * 360, 0, 0.9f));

                entities.add(new Entity(fern, new Vector3f(x, y, z), 0, random.nextFloat() * 360, 0, 1.5f));

                entities.add(new Entity(grass, new Vector3f(x, y, z), 0, 0, 0, 1.8f));

                entities.add(new Entity(flower, new Vector3f(x, y, z), 0, 0, 0, 2.3f));
            }

            if (i % 3 == 0) {
                float x = random.nextFloat() * 800 - 400;
                float z = random.nextFloat() * -600;
                float y = terrain.getHeightOfTerrain(x, z);

                entities.add(new Entity(tree, new Vector3f(x, y, z), 0, 0, 0, random.nextFloat() * 1 + 4));

                entities.add(new Entity(box, new Vector3f(x, y, z), 0, 0, 0, random.nextFloat() * 1 + 4));
            }
        }

        TexturedModel staticModel;
        staticModel = new TexturedModel(OBJLoader.loadObjModel("fern", loader), new ModelTexture(loader.loadTexture("fern")));
        staticModel.getTexture().setShineDamper(10);
        staticModel.getTexture().setReflectivity(1);
        staticModel.getTexture().setUseFakeLighting(true);
        for (int i = 0; i < 50; i++) {
            float x = random.nextFloat() * 800 - 400;
            float z = random.nextFloat() * -600;
            float y = terrain.getHeightOfTerrain(x, z);
            entities.add(new Entity(staticModel, new Vector3f(random.nextFloat() * 800 - 400, y, z), x, 0, 0, 1));
        }
        staticModel = new TexturedModel(OBJLoader.loadObjModel("tree", loader), new ModelTexture(loader.loadTexture("tree")));
        staticModel.getTexture().setShineDamper(10);
        staticModel.getTexture().setReflectivity(1);

        for (int i = 0; i < 200; i++) {
            float x = random.nextFloat() * 800 - 400;
            float z = random.nextFloat() * -600;
            float y = terrain.getHeightOfTerrain(x, z);
            entities.add(new Entity(staticModel, new Vector3f(x, y, z), 0, 0, 0, 9));
        }


        Light light = new Light(new Vector3f(20000, 20000, 2000), new Vector3f(1, 1, 1));


        MasterRenderer renderer = new MasterRenderer();


        RawModel avatar = OBJLoader.loadObjModel("person", loader);
        TexturedModel avatarModel = new TexturedModel(avatar, new ModelTexture(loader.loadTexture("playerTexture")));
        Player player = new Player(avatarModel, new Vector3f(100, 0, -50), 0, 0, 0, 1);
        Camera camera = new Camera(player);

        while (!Display.isCloseRequested()) {
            camera.move();
            player.move(terrain);
            renderer.processEntity(player);

            renderer.processTerrain(terrain);

            for (Entity entity : entities) {
                //entity.increaseRotation(0, 1, 0);
                renderer.processEntity(entity);
            }
            renderer.render(light, camera);
            DisplayManager.updateDisplay();
        }

        renderer.cleanUp();
        loader.cleanUp();

        DisplayManager.closeDisplay();
    }
}
