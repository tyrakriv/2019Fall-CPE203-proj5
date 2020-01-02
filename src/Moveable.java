import processing.core.PImage;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public abstract class Moveable extends AnimationEntity {
   PathingStrategy strategy;



    public Moveable(String id, Point position, int actionPeriod, int animationPeriod, List<PImage> images)
    {
        super(id, position, actionPeriod, images, animationPeriod);
        strategy = new AStarPathingStrategy();

    }

    protected abstract Point nextPosition(WorldModel world, Point destPos);

    boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler)
    {
        if (getPosition().adjacent(target.getPosition()))
        {
            if (this instanceof UfoNotFull)
            {
                ((UfoNotFull)this).setResourceCount(((UfoNotFull)this).getResourceCount() + 1);
            }

            return true;
        }
        else
        {
            Point nextPos = nextPosition(world, target.getPosition());

            if (!getPosition().equals(nextPos))
            {
                Optional<Entity> occupant = world.getOccupant(nextPos);
                if (occupant.isPresent())
                {
                    scheduler.unscheduleAllEvents(occupant.get());
                }

                world.moveEntity(this, nextPos);
            }
            return false;
        }
    }


    public static boolean neighbors(Point p1, Point p2)
    {
        return p1.x+1 == p2.x && p1.y == p2.y ||
                p1.x-1 == p2.x && p1.y == p2.y ||
                p1.x == p2.x && p1.y+1 == p2.y ||
                p1.x == p2.x && p1.y-1 == p2.y;
    }

}
