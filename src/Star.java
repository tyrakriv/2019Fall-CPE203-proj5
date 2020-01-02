import processing.core.PApplet;
import processing.core.PImage;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class Star extends Moveable{

    public static final Random rand = new Random();
    public final int dsx;
    public final int dsy;
    public int p1;
    public int p2;
    public int shiptrack;

    public Star(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod, int dsx, int dsy, int shiptrack) {
        super(id, position, actionPeriod, animationPeriod, images);
        this.dsx = dsx;
        this.dsy = dsy;
        this.shiptrack = shiptrack;
    }

    public int getP1() {
        return p1;
    }

    public int getP2() {
        return p2;
    }

    public Point nextPosition(WorldModel world, Point destPos) {
        Point update = new Point(getPosition().x + dsx, getPosition().y + dsy);
        setPosition(update);
        if(!world.withinBounds(update))
        {
            world.removeEntity(this);
        }
        else if (!(world.getOccupant(update).equals(Optional.empty()))) {
            System.out.println(world.getOccupant(update).get().getClass());
//            if (world.getOccupant(update).get().getClass() == Meteor.class){
                world.removeEntity(this);
//            }
        }

            return update;
    }

    public void executeActivity(WorldModel world,
                                ImageStore imageStore, EventScheduler scheduler)
//    {
//        Optional<Entity> target = world.findNearest(this.getPosition(), Ship.class);
//
//        if (target.isPresent() &&
//                moveTo(world, target.get(), scheduler))
//        {
////            world.removeEntity(this);
//
//            System.out.println("reach");
////            System.out.println("new point " + this.getPosition());
////            Ship ship = Ship.getInstance2("shipB", this.getPosition(), 4,
////                    5, ImageStore.getImageList("shipB"), new Point(0, -1));
////            world.addEntity(ship);
//        }
//            scheduler.scheduleEvent(this,
//                    new Activity(this, world, imageStore),
//                    this.getActionPeriod());
//
//    }

    {
        Optional<Entity> downTarget = world.findNearest(this.getPosition(), Ship.class);

        if (downTarget.isPresent() &&
                moveTo(world, downTarget.get(), scheduler))
        {
//            ((ActivityEntity)downTarget.get()).scheduleActions(scheduler, world, imageStore);

            Ship ship = Ship.getInstance1("ship", new Point(0, 0), 0, 0, null, new Point(0, 0), 0);
            Ship shipB = Ship.getInstance2("shipB", new Point(0, 0), 0, 0, null, new Point(0, 0), 0);
            if (this.shiptrack == 0) {
                ship.setResourcecount(1);            //transform to unfull
            }
            else {
                shipB.setResourcecount(1);
            }
        }
            scheduler.scheduleEvent(this,
                    new Activity(this, world, imageStore),
                    this.getActionPeriod());
    }
}