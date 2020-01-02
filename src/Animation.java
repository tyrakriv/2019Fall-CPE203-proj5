import java.util.Random;

public class Animation implements Action{

    private AnimationEntity entity;
    private int repeatCount;

    public Animation(AnimationEntity entity, int repeatCount)
    {
        this.entity = entity;
        this.repeatCount = repeatCount;
    }

    public void executeAction(EventScheduler scheduler)
    {
        entity.nextImage();

        if (repeatCount != 1)
        {
            scheduler.scheduleEvent(entity, new Animation(entity, Math.max(repeatCount - 1, 0)),
                    entity.getAnimationPeriod());
        }
    }

}
