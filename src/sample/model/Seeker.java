package sample.model;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import sample.conf.Const;

import java.util.Random;

public class Seeker extends Region {

    Vector2D location;
    Vector2D velocity;
    Vector2D acceleration;

    double maxForce = Const.SPRITE_MAX_FORCE;
    double maxSpeed = Const.SPRITE_MAX_SPEED*new Random().nextDouble()*10;

    // view dimensions
    double size;
    double radius;
    double angle;

    public Seeker(String text, Vector2D location, Vector2D velocity, Vector2D acceleration, double size) {
        this.location = location;
        this.velocity = velocity;
        this.acceleration = acceleration;
        this.size = size;
        // add view to this node
        Label a = new Label(text);
        a.setStyle("-fx-text-fill:BLACK; -fx-font-size: "+size+";");
        getChildren().add(a);
    }

    public void applyForce(Vector2D force) {
        acceleration.add(force);
    }

    public void move() {
        // set velocity depending on acceleration
        velocity.add(acceleration);

        // limit velocity to max speed
        velocity.limit(maxSpeed);

        // change location depending on velocity
        location.add(velocity);

        // angle: towards velocity (ie target)
        //angle = velocity.heading2D();

        // clear acceleration
        acceleration.multiply(0);
    }

    /**
     * Move sprite towards target
     */
    public void seek(Vector2D target) {
        Vector2D desired = Vector2D.subtract(target, location);

        // The distance is the magnitude of the vector pointing from location to target.
        double d = desired.magnitude();
        desired.normalize();

        // If we are closer than 100 pixels...
        if (d < Const.SPRITE_SLOW_DOWN_DISTANCE) {// ...set the magnitude according to how close we are.
            double m = map(d, 0, Const.SPRITE_SLOW_DOWN_DISTANCE, 0, maxSpeed);
            desired.multiply(m);
        }else {// Otherwise, proceed at maximum speed.
            desired.multiply(maxSpeed);
        }

        // The usual steering = desired - velocity
        Vector2D steer = Vector2D.subtract(desired, velocity);
        steer.limit(maxForce);

        applyForce(steer);

    }

    //Update node position
    public void display() {
        relocate(location.x - (size*5/2), location.y - (size/2));
        //setRotate(Math.toDegrees( angle));
    }

    public static double map(double value, double currentRangeStart, double currentRangeStop, double targetRangeStart, double targetRangeStop) {
        return targetRangeStart + (targetRangeStop - targetRangeStart) * ((value - currentRangeStart) / (currentRangeStop - currentRangeStart));
    }

}