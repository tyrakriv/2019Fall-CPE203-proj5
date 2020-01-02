import processing.core.PImage;

import java.util.*;

public class Ship extends AnimationEntity {
    private static Ship ship;
    private static Ship shipB;
    private Point direction;
    private int Resourcecount;
    private boolean life;

    private Ship(String id, Point position, int actionPeriod, int animationPeriod, List<PImage> images, Point direction, int Resourcecount)
    {
        super("ship", position, actionPeriod, images, animationPeriod);
        this.direction = direction;
        this.Resourcecount = Resourcecount;
        this.life = true;
    }

    public void setLife(boolean life) {
        this.life = life;
    }
    public boolean getLife() {
        return life;
    }

    public static Ship getInstance1(String id, Point position, int actionPeriod, int animationPeriod, List<PImage> images, Point direction, int Resourcecount)
    {
        if (ship == null)
        {
            ship = new Ship(id, position, actionPeriod, animationPeriod, images, direction, Resourcecount);
        }
        return ship;
    }
    public static Ship getInstance2(String id, Point position, int actionPeriod, int animationPeriod, List<PImage> images, Point direction, int Resourcecount)
    {
        if (shipB == null)
        {
            shipB = new Ship(id, position, actionPeriod, animationPeriod, images, direction, Resourcecount);
        }
        return shipB;
    }

    public int getResourcecount() {
        return Resourcecount;
    }

    public void setResourcecount(int Resourcecount){
        this.Resourcecount += Resourcecount;
    }

    protected Point nextPosition(WorldModel world, Point nextPos) {
        Point pos = this.getPosition();
        Point newpos = new Point(nextPos.x + pos.x, nextPos.y + pos.y);
        if (!world.isOccupied(newpos) && world.withinBounds(newpos) && !world.isOccupied(newpos))
        {
            return newpos;
        }
        return pos;
    }
    public Point getDirection() {
        return this.direction;
    }

    public void setDirection(Point direction) {
        this.direction = direction;
    }


    protected void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        long nextPeriod = getActionPeriod();
        scheduler.scheduleEvent(this, new Activity(this, world, imageStore), nextPeriod);
    }
}