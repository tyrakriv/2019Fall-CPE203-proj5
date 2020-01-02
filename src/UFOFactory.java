import processing.core.PApplet;
import processing.core.PImage;
import java.util.List;
import java.awt.*;
import java.util.Random;

public class UFOFactory {


    private static final Random rand = new Random();


    public static UfoNotFull createUfoNotFull(String id, int resourceLimit, Point position, int actionPeriod, int animationPeriod,
                      List<PImage> images)
    {
        Class kind = Asteroid.class;
        int type = rand.nextInt(3);
        if (type == 1)
        {
            kind = Meteor.class;
        }
        return new UfoNotFull(id, resourceLimit, position, actionPeriod, animationPeriod, images, kind);
    }

}
