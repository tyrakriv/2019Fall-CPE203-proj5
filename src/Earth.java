import processing.core.PImage;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class Earth extends AnimationEntity{

    private static final int ATLANTIS_ANIMATION_REPEAT_COUNT = 7;

    public Earth(String id, Point position, List<PImage> images)
    {
        super(id, position, 0, images, 0);
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        scheduler.unscheduleAllEvents(this);
        world.removeEntity(this);
    }
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore)
    {
        scheduler.scheduleEvent(this, new Animation(this, ATLANTIS_ANIMATION_REPEAT_COUNT), getAnimationPeriod());
    }
}

