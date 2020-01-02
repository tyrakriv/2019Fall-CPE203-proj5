import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class Asteroid extends Moveable{

    private final String FIRE_KEY = "fire";

    public Asteroid(String id, Point position, int actionPeriod, int animationPeriod, List<PImage> images)
    {
        super(id, position, actionPeriod, animationPeriod, images);
    }

    protected Point nextPosition(WorldModel world, Point destPos)
    {
        List<Point> points = null;
        Optional<Entity> occupant = world.getOccupant(destPos);

//       PathingStrategy strategy = new SingleStepPathingStrategy();
        PathingStrategy strategy = new AsteroidPathingStrategy();

        if (!(occupant == null || world == null)) {
            points = strategy.computePath(getPosition(), destPos,
                    p -> world.withinBounds(p) && !world.isOccupied(p) && !(occupant.get() instanceof Meteor),
                    (p1, p2) -> neighbors(p1, p2),
                    //PathingStrategy.CARDINAL_NEIGHBORS);
                    //DIAGONAL_NEIGHBORS);
                    PathingStrategy.DIAGONAL_CARDINAL_NEIGHBORS);
        }
        if (points.size() == 0)
        {
            return getPosition();
        }
        else
        {
            return points.get(0);
        }
    }


    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Entity> asteroidTarget = world.findNearest(getPosition(), Ship.class);
        long nextPeriod = getActionPeriod();

        if (asteroidTarget.isPresent())
        {

            Point tgtPos = asteroidTarget.get().getPosition();

            if (moveTo(world, asteroidTarget.get(), scheduler))
            {
                Fire fire = new Fire(tgtPos, imageStore.getImageList(FIRE_KEY));

                world.addEntity(fire);
                nextPeriod += getActionPeriod();
                fire.scheduleActions(scheduler, world, imageStore);

                Ship shipA = Ship.getInstance1("shipA", tgtPos, 99999, 99999, imageStore.getImageList("ship"), new Point(0, 0),0);
                Ship shipB = Ship.getInstance2("shipB", tgtPos, 99999, 99999, imageStore.getImageList("shipB"), new Point(0, 0),0);
                if (shipA.getPosition().x == tgtPos.x)
                {
                    shipA.setLife(false);
                    //world.removeEntity(fire);
                   /* world.addEntity(shipA);
                    world.moveEntity(shipA, new Point(0, 0));*/
                }
                if (shipB.getPosition().x == tgtPos.x)
                {
                    shipB.setLife(false);
                    //world.removeEntity(fire);
                    /*world.addEntity(shipB);
                    world.moveEntity(shipB, new Point(0, 3));*/
                }
            }
        }
        scheduler.scheduleEvent(this, new Activity(this, world, imageStore),nextPeriod);
    }
}