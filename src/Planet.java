import processing.core.PImage;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class Planet extends ActivityEntity{

    private final String METEOR_ID_PREFIX = "meteor -- ";
    private final int METEOR_CORRUPT_MIN = 20000;
    private final int METEOR_CORRUPT_MAX = 30000;
    private final String METEOR_KEY = "meteor";
    private static final Random rand = new Random();

    public Planet(String id, Point position, int actionPeriod,
                  List<PImage> images)
    {
        super(id, position, actionPeriod, images);
    }

    protected void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Point> openPt = world.findOpenAround(getPosition());

        if (openPt.isPresent())
        {
            Meteor meteor = new Meteor(METEOR_ID_PREFIX + getId(), openPt.get(),
                    METEOR_CORRUPT_MIN + rand.nextInt(METEOR_CORRUPT_MAX - METEOR_CORRUPT_MIN),
                    imageStore.getImageList(METEOR_KEY));
            world.addEntity(meteor);
            meteor.scheduleActions(scheduler, world, imageStore);
        }
        scheduler.scheduleEvent(this, new Activity(this, world, imageStore), (this).getActionPeriod());
    }


}