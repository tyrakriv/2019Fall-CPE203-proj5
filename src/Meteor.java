import processing.core.PImage;

import java.util.List;
import java.util.Random;

public class Meteor extends ActivityEntity{

    private AsteroidFactory asteroidFactory = new AsteroidFactory();


    public Meteor(String id, Point position, int actionPeriod, List<PImage> images)
    {
        super(id, position, actionPeriod, images);
    }

    protected void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Point pos = this.getPosition();
        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);
        Asteroid asteroid = asteroidFactory.create(this, pos, imageStore);

        world.tryAddEntity(asteroid);
        asteroid.scheduleActions(scheduler, world, imageStore);
    }





}
