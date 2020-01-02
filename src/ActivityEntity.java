import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public abstract class ActivityEntity extends Entity {

    private int actionPeriod;
    public ActivityEntity(String id, Point position, int actionPeriod, List<PImage> images)
    {
        super(id, position, images);
        this.actionPeriod = actionPeriod;
    }



    public int getActionPeriod() { return actionPeriod; }

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore)
    {
        scheduler.scheduleEvent(this, new Activity(this, world, imageStore), getActionPeriod());

    }

    protected abstract void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler);





}
