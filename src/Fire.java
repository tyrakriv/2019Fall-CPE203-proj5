import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class Fire extends AnimationEntity{
    private static final String FIRE_ID = "fire";
    private static final int FIRE_ACTION_PERIOD = 1100;
    private static final int FIRE_ANIMATION_PERIOD = 100;
    private static final int FIRE_ANIMATION_REPEAT_COUNT = 10;

    public Fire(Point position, List<PImage> images) {
        super(FIRE_ID, position, FIRE_ACTION_PERIOD, images, FIRE_ANIMATION_PERIOD);
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        world.removeEntity(this);
    }

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore)
    {
        scheduler.scheduleEvent(this, new Activity(this, world, imageStore), this.getActionPeriod());
        scheduler.scheduleEvent(this, new Animation(this, FIRE_ANIMATION_REPEAT_COUNT), this.getAnimationPeriod());
    }




}