import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class UfoNotFull extends Ufo {
    private Class kind;

    public UfoNotFull(String id, int resourceLimit, Point position, int actionPeriod, int animationPeriod,
                      List<PImage> images, Class kind)
    {
        super(id, resourceLimit, position, actionPeriod, animationPeriod, images);
        this.kind = kind;

    }


    protected void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Entity> notFullTarget = world.findNearest(this.getPosition(), this.kind);

        if (!notFullTarget.isPresent() ||
                !moveTo(world, notFullTarget.get(), scheduler) ||
                !transform(world, scheduler, imageStore))
        {
            scheduler.scheduleEvent(this, new Activity(this, world, imageStore), getActionPeriod());
        }

    }
    protected UfoFull transformHelper(WorldModel world, EventScheduler scheduler, ImageStore imageStore)
    {
        if (getResourceCount() >= getResourceLimit())
        {
            UfoFull ufo = new UfoFull(this.getId(), this.getResourceLimit(),
                    this.getPosition(), this.getActionPeriod(), this.getAnimationPeriod(),
                    this.getImages());
            /*world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(octo);
            octo.scheduleActions(scheduler, world, imageStore);
            return true;*/
            return ufo;
        }
        //return false;
        return null;
    }



}
