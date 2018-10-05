package entities;

import javafx.scene.input.KeyEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

public class Camera {

    private Vector3f position = new Vector3f(0,5,0);
    private float pitch = 10;
    private float yaw = 180;
    private float speed;

    public Camera()
    {

        this.speed = 1f;

    }

    public void move()
    {

        yaw =  - (Display.getWidth() - Mouse.getX() / 2);
        pitch =  (Display.getHeight() / 2) - Mouse.getY();

        if (pitch >= 90)
        {

            pitch = 90;

        }
        else if (pitch <= -90)
        {

            pitch = -90;

        }

        if (Keyboard.isKeyDown(Keyboard.KEY_W )|| Keyboard.isKeyDown(Keyboard.KEY_UP))
        {

            position.z += -(float)Math.cos(Math.toRadians(yaw)) * speed;
            position.x += (float)Math.sin(Math.toRadians(yaw)) * speed;

        }
        else if (Keyboard.isKeyDown(Keyboard.KEY_S) || Keyboard.isKeyDown(Keyboard.KEY_DOWN))
        {
            position.z -= -(float)Math.cos(Math.toRadians(yaw)) * speed;
            position.x -= (float)Math.sin(Math.toRadians(yaw)) * speed;


        }

        if (Keyboard.isKeyDown(Keyboard.KEY_D) || Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
        {

            position.z += (float)Math.sin(Math.toRadians(yaw)) * speed;
            position.x += (float)Math.cos(Math.toRadians(yaw)) * speed;

        }
        else if (Keyboard.isKeyDown(Keyboard.KEY_A) || Keyboard.isKeyDown(Keyboard.KEY_LEFT))
        {

            position.z -= (float)Math.sin(Math.toRadians(yaw)) * speed;
            position.x -= (float)Math.cos(Math.toRadians(yaw)) * speed;

        }
    }

    public Vector3f getPosition() {
        return position;
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }
}