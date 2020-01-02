import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class UfoFull extends Ufo {

    public UfoFull(String id, int resourceLimit, Point position, int actionPeriod, int animationPeriod,
                   List<PImage> images)
    {
        super(id, resourceLimit, position, actionPeriod, animationPeriod, images);
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Entity> fullTarget = world.findNearest(this.getPosition(), Earth.class);

        if (fullTarget.isPresent() &&
                moveTo(world, fullTarget.get(), scheduler))
        {
            //at atlantis trigger animation
            ((AnimationEntity)fullTarget.get()).scheduleActions(scheduler, world, imageStore);

            //transform to unfull
            transform(world, scheduler, imageStore);
        }
        else
        {
            scheduler.scheduleEvent(this, new Activity(this, world, imageStore),
                    this.getActionPeriod());
        }
    }

    protected UfoNotFull transformHelper(WorldModel world, EventScheduler scheduler, ImageStore imageStore)
    {
        UfoNotFull ufo = UFOFactory.createUfoNotFull(this.getId(), this.getResourceLimit(), this.getPosition(),
                this.getActionPeriod(), this.getAnimationPeriod(),
                this.getImages());
        return ufo;


    }


}
