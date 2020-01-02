import java.util.Random;

public class Activity implements Action{

    /*
Action: ideally what our various entities might do in our virutal world
 */
    private ActivityEntity entity;
    private WorldModel world;
    private ImageStore imageStore;
    private int repeatCount = 0;

    public Activity(ActivityEntity entity, WorldModel world,
                  ImageStore imageStore)
    {
        this.entity = entity;
        this.world = world;
        this.imageStore = imageStore;
        this.repeatCount = repeatCount;
    }

    public WorldModel getWorld() {
        return world;
    }

    public ImageStore getImageStore() {
        return imageStore;
    }

    public void executeAction(EventScheduler scheduler)
    {
        entity.executeActivity(world, imageStore, scheduler);
    }




}
