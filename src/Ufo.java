import processing.core.PImage;

import java.util.List;

public abstract class Ufo extends Moveable {

    private int resourceLimit;
    private int resourceCount;

    public Ufo(String id, int resourceLimit, Point position, int actionPeriod, int animationPeriod, List<PImage> images)
    {
        super(id, position, actionPeriod, animationPeriod, images);
        this.resourceLimit = resourceLimit;
    }

    public int getResourceLimit() { return resourceLimit; }
    public int getResourceCount() { return resourceCount; }
    public void setResourceCount(int resourceCount) { this.resourceCount = resourceCount; }

    //protected abstract boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore);

    protected Point nextPosition(WorldModel world, Point destPos)
    {
        List<Point> points = null;

//        PathingStrategy strategy = new SingleStepPathingStrategy();
//          PathingStrategy strategy = new AStarPathingStrategy();

        points = strategy.computePath(getPosition(), destPos,
                    p -> world.withinBounds(p) && !world.isOccupied(p),
                    (p1, p2) -> neighbors(p1, p2),
                    PathingStrategy.CARDINAL_NEIGHBORS);
                    //DIAGONAL_NEIGHBORS);
                    //PathingStrategy.DIAGONAL_CARDINAL_NEIGHBORS);
        if (points.size() == 0)
        {
            return getPosition();
        }
        else
            {
            return points.get(0);
            }
        }

    public boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore)
    {
        Ufo ufo = transformHelper(world, scheduler, imageStore);
        if (ufo != null)
        {
            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(ufo);
            ufo.scheduleActions(scheduler, world, imageStore);
            return true;
        }
        return false;
        }

    protected abstract Ufo transformHelper(WorldModel world, EventScheduler scheduler, ImageStore imageStore);

}