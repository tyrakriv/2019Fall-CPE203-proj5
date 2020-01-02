import processing.core.PImage;

import java.util.List;
import java.util.Random;

public class AsteroidFactory {
    private static final String ASTEROID_KEY = "Asteroid";
    private static final String ASTEROID_ID_SUFFIX = " -- asteroid";
    private static final int ASTEROID_PERIOD_SCALE = 6;
    private static final int ASTEROID_ANIMATION_MIN = 50;
    private static final int ASTEROID_ANIMATION_MAX = 150;

    private static final Random rand = new Random();
    private static int counter;

    public Asteroid create(Meteor meteor, Point pos, ImageStore imageStore) {
        AsteroidFactory.counter += 1;
        Asteroid asteroid = new Asteroid(meteor.getId() + ASTEROID_ID_SUFFIX, pos,
                meteor.getActionPeriod() / ASTEROID_PERIOD_SCALE + counter,
                ASTEROID_ANIMATION_MIN + counter,
                imageStore.getImageList(ASTEROID_KEY));
        return asteroid;
    }
}


