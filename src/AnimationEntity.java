import processing.core.PImage;

import java.util.List;

public abstract class AnimationEntity extends ActivityEntity{

    private int animationPeriod;


    public AnimationEntity(String id, Point position, int actionPeriod, List<PImage> images, int animationPeriod)
    {
        super(id, position, actionPeriod, images);
        this.animationPeriod = animationPeriod;

    }
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore)
    {
        super.scheduleActions(scheduler, world, imageStore);
        scheduler.scheduleEvent(this, new Animation((AnimationEntity)this,0), ((AnimationEntity)this).getAnimationPeriod());
    }

    public int getAnimationPeriod() { return this.animationPeriod; }
    public void nextImage() { this.setImageIndex((this.getImageIndex() + 1) % this.getImages().size()); }

}
