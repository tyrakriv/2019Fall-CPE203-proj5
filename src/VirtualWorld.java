import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import processing.core.*;
import java.util.Random;
/*
VirtualWorld is our main wrapper
It keeps track of data necessary to use Processing for drawing but also keeps track of the necessary
components to make our world run (eventScheduler), the data in our world (WorldModel) and our
current view (think virtual camera) into that world (WorldView)
 */
public final class VirtualWorld
   extends PApplet
{
   private static final int TIMER_ACTION_PERIOD = 100;
   public static final Random rand = new Random();

   private static final int VIEW_WIDTH = 1280;
   private static final int VIEW_HEIGHT = 800;
   private static final int TILE_WIDTH = 32;
   private static final int TILE_HEIGHT = 32;
   private static final int WORLD_WIDTH_SCALE = 2;
   private static final int WORLD_HEIGHT_SCALE = 2;

   private static final int VIEW_COLS = VIEW_WIDTH / TILE_WIDTH;
   private static final int VIEW_ROWS = VIEW_HEIGHT / TILE_HEIGHT;
   private static final int WORLD_COLS = VIEW_COLS * WORLD_WIDTH_SCALE;
   private static final int WORLD_ROWS = VIEW_ROWS * WORLD_HEIGHT_SCALE;

   private static final String IMAGE_LIST_FILE_NAME = "imagelist";
   private static final String DEFAULT_IMAGE_NAME = "background_default";
   private static final int DEFAULT_IMAGE_COLOR = 0x808080;

   private static final String LOAD_FILE_NAME = "world.sav";

   private static final String FAST_FLAG = "-fast";
   private static final String FASTER_FLAG = "-faster";
   private static final String FASTEST_FLAG = "-fastest";
   private static final double FAST_SCALE = 0.5;
   private static final double FASTER_SCALE = 0.25;
   private static final double FASTEST_SCALE = 0.10;

   private static double timeScale = 1.0;

   private static ArrayList<Point> arr = new ArrayList<>();
   private static ArrayList<Point> arr2 = new ArrayList<>();

   private ImageStore imageStore;
   private WorldModel world;
   private WorldView view;
   private EventScheduler scheduler;

   private long next_time;

   public void settings()
   {
      size(VIEW_WIDTH, VIEW_HEIGHT);
   }

   /*
      Processing entry point for "sketch" setup.
   */
   public void setup()
   {
      this.imageStore = new ImageStore(createImageColored(TILE_WIDTH, TILE_HEIGHT, DEFAULT_IMAGE_COLOR));
      this.world = new WorldModel(WORLD_ROWS, WORLD_COLS, createDefaultBackground(imageStore));
      this.view = new WorldView(VIEW_ROWS, VIEW_COLS, this, world, TILE_WIDTH, TILE_HEIGHT);
      this.scheduler = new EventScheduler(timeScale);

      loadImages(IMAGE_LIST_FILE_NAME, imageStore, this);
      loadWorld(world, LOAD_FILE_NAME, imageStore);

      scheduleActions(world, scheduler, imageStore);

      next_time = System.currentTimeMillis() + TIMER_ACTION_PERIOD;
   }

   public void draw()
   {
      long time = System.currentTimeMillis();
      if (time >= next_time)
      {
         scheduler.updateOnTime(time);
         next_time = time + TIMER_ACTION_PERIOD;
      }
      view.drawViewport();
      Ship ship = Ship.getInstance1("ship", new Point(0, 0), 0, 0, null, new Point(0, 0), 0);
      Ship shipB = Ship.getInstance2("shipB", new Point(0, 0), 0, 0, null, new Point(0, 0), 0);
      drawScoreboard(ship.getResourcecount(), shipB.getResourcecount());
   }

   public void keyPressed()
   {
      int dx = 0;
      int dy = 0;
      int dxB = 0;
      int dyB = 0;
      Ship ship = Ship.getInstance1("ship", new Point(0, 0), 0, 0, null, new Point(0, 0), 0);
      Ship shipB = Ship.getInstance2("shipB", new Point(0, 0), 0, 0, null, new Point(0, 0), 0);
      switch (keyCode) {
         case 87:
            shipB.setImageIndex(0);
            dyB = -1;
            arr.add(new Point(0, dyB));
            shipB.setDirection(new Point(dxB, dyB));
            break;
         case 65:
            shipB.setImageIndex(2);
            dxB = -1;
            arr.add(new Point(dxB, 0));
            shipB.setDirection(new Point(dxB, dyB));
            break;
         case 83:
            shipB.setImageIndex(1);
            dyB = 1;
            arr.add(new Point(0, dyB));
            shipB.setDirection(new Point(dxB, dyB));
            break;
         case 68:
            shipB.setImageIndex(3);
            dxB = 1;
            arr.add(new Point(dxB, 0));
            shipB.setDirection(new Point(dxB, dyB));
            break;
         case 9:
            Point pos = new Point(shipB.getPosition().x, shipB.getPosition().y);
            Point last = new Point(0, -1);
            if (arr.size() > 0) {
               last = (arr.get(arr.size() - 1));
            }
            if (last.x == 0){
               pos.y = pos.y + last.y + last.y;
               pos.x = pos.x + last.x;
            }
            if (last.y == 0) {
               pos.x = pos.x + last.x + last.x;
               pos.y = pos.y + last.y;
            }
            Entity star = new Star("star",
                    pos, imageStore.getImageList("star"), 2,
                    50 + rand.nextInt(100), shipB.getDirection().x, shipB.getDirection().y,1);
            world.addEntity(star);
            ((ActivityEntity) star).scheduleActions(scheduler, world, imageStore);
            break;
      }
      if (key == CODED) {
         switch (keyCode) {
            case UP:
               dy = -1;
               arr2.add(new Point(0, dy));
               ship.setImageIndex(0);
               ship.setDirection(new Point(dx, dy));
               break;
            case DOWN:
               dy = 1;
               arr2.add(new Point(0, dy));
               ship.setImageIndex(1);
               ship.setDirection(new Point(dx, dy));
               break;
            case LEFT:
               dx = -1;
               arr2.add(new Point(dx, 0));
               ship.setImageIndex(2);
               ship.setDirection(new Point(dx, dy));
               break;
            case RIGHT:
               dx = 1;
               arr2.add(new Point(dx, 0));
               ship.setImageIndex(3);
               ship.setDirection(new Point(dx, dy));
               break;
            case SHIFT:
               Point pos = new Point(ship.getPosition().getX(), ship.getPosition().getY());
               Point last = new Point(0, -1);
               if (arr2.size() > 0) {
                  last = (arr2.get(arr2.size() - 1));
               }
               if (last.x == 0){
                  pos.y = pos.y + last.y + last.y;
                  pos.x = pos.x + last.x;
               }
               if (last.y == 0) {
                  pos.x = pos.x + last.x + last.x;
                  pos.y = pos.y + last.y;
               }
               Entity star = new Star("star",
                       pos, imageStore.getImageList("star"), 2,
                       50 + rand.nextInt(100), ship.getDirection().x, ship.getDirection().y,0);
               world.addEntity(star);
               ((ActivityEntity) star).scheduleActions(scheduler, world, imageStore);
               break;
         }
      }
      //Point pos = ship.getPosition();
      //Point newpos = new Point(dx + pos.x, dy + pos.y);
      //if (world.withinBounds(newpos) && !world.isOccupied(newpos))
      //{

         //ship.nextPosition(world, loc);
      Point loc = new Point(dx, dy);
      world.moveEntity(ship, ship.nextPosition(world, loc));
      Point locB = new Point(dxB, dyB);
      world.moveEntity(shipB, shipB.nextPosition(world, locB));
      //}
      //Point posB = shipB.getPosition();
      //Point newposB = new Point(dxB + posB.x, dyB + posB.y);
      //if (world.withinBounds(newposB) && !world.isOccupied(newposB))
      //{

     // }
   }

   public static Background createDefaultBackground(ImageStore imageStore)
   {
      return new Background(DEFAULT_IMAGE_NAME,
         imageStore.getImageList(DEFAULT_IMAGE_NAME));
   }

   public PImage createImageColored(int width, int height, int color)
   {
      PImage img = new PImage(width, height, RGB);
      img.loadPixels();
      for (int i = 0; i < img.pixels.length; i++)
      {
         img.pixels[i] = color;
      }
      img.updatePixels();
      return img;
   }

   public void loadImages(String filename, ImageStore imageStore, PApplet screen)
   {
      try
      {
         Scanner in = new Scanner(new File(filename));
         imageStore.loadImages(in, screen);
      }
      catch (FileNotFoundException e)
      {
         System.err.println(e.getMessage());
      }
   }

   public static void loadWorld(WorldModel world, String filename,
      ImageStore imageStore)
   {
      try
      {
         Scanner in = new Scanner(new File(filename));
         Parse.load(in, world, imageStore);
      }
      catch (FileNotFoundException e)
      {
         System.err.println(e.getMessage());
      }
   }

   public void scheduleActions(WorldModel world, EventScheduler scheduler, ImageStore imageStore)
   {
      for (Entity entity : world.getEntities()) {
         //Only start actions for entities that include action (not those with just animations)
         if (entity instanceof ActivityEntity) {
            if (((ActivityEntity)entity).getActionPeriod() > 0)
               ((ActivityEntity)entity).scheduleActions(scheduler, world, imageStore);
         }
      }
   }

   public static void parseCommandLine(String [] args)
   {
      for (String arg : args)
      {
         switch (arg)
         {
            case FAST_FLAG:
               timeScale = Math.min(FAST_SCALE, timeScale);
               break;
            case FASTER_FLAG:
               timeScale = Math.min(FASTER_SCALE, timeScale);
               break;
            case FASTEST_FLAG:
               timeScale = Math.min(FASTEST_SCALE, timeScale);
               break;
         }
      }
   }

   public static void main(String [] args)
   {
      parseCommandLine(args);
      PApplet.main(VirtualWorld.class);
   }

   public void drawScoreboard(int p1, int p2){
      fill(51, 255, 255);
      textSize(20);
      text("Player 1: " + p1, 1100, 50);

      fill(255, 0, 255);
      textSize(20);
      text("Player 2: " + p2, 1100, 90);
      Ship ship = Ship.getInstance1(null, null, 0, 0, null, null, 0);
      Ship shipB = Ship.getInstance2(null, null, 0, 0, null, null, 0);
      if ((!ship.getLife() || !shipB.getLife()) && (ship.getResourcecount() > shipB.getResourcecount()))
      {
         fill(51, 255, 255);
         textSize(100);
         text("Player 1 Wins", 300, 380);
      }
      else if ((!ship.getLife() || !shipB.getLife()) && (ship.getResourcecount() < shipB.getResourcecount()))
      {
         fill(255, 0, 255);
         textSize(100);
         text("Player 2 Wins", 300, 380);
      }
      else if ((!ship.getLife() || !shipB.getLife()))
      {
         textSize(100);
         text("You Lose!", 400, 380);
         fill(51, 255, 255);;
      }
   }
}
