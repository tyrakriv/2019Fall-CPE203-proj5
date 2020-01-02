import processing.core.PApplet;

import java.util.Scanner;

public class Parse {

    private PApplet screen;

    private static final String SHIP_KEY = "ship";
    private static final String SHIPB_KEY = "shipB";
    private static final int SHIP_NUM_PROPERTIES = 6;
    private static final int SHIP_ID = 1;
    private static final int SHIP_COL = 2;
    private static final int SHIP_ROW = 3;
    private static final int SHIP_ACTION_PERIOD = 4;
    private static final int SHIP_ANIMATION_PERIOD = 5;

    private static final String EARTH_KEY = "earth";


    private static final String STAR_KEY = "star";
    private static final int STAR_NUM_PROPERTIES = 7;
    private static final int STAR_ID = 1;
    private static final int STAR_COL = 2;
    private static final int STAR_ROW = 3;
    private static final int STAR_LIMIT = 4;
    private static final int STAR_ACTION_PERIOD = 5;
    private static final int STAR_ANIMATION_PERIOD = 6;

    private static final String DOWN_KEY = "down";
    private static final int DOWN_NUM_PROPERTIES = 4;
    private static final int DOWN_ID = 1;
    private static final int DOWN_COL = 2;
    private static final int DOWN_ROW = 3;


    private static final String UFO_KEY = "ufo";
    private static final String OBSTACLE_KEY = "obstacle";
        private static final String METEOR_KEY = "meteor";
    private static final String PLANET_KEY = "planet";
    private static final String BGND_KEY = "background";
    private static final int PROPERTY_KEY = 0;

    private static final int UFO_NUM_PROPERTIES = 7;
    private static final int UFO_ID = 1;
    private static final int UFO_COL = 2;
    private static final int UFO_ROW = 3;
    private static final int UFO_LIMIT = 4;
    private static final int UFO_ACTION_PERIOD = 5;
    private static final int UFO_ANIMATION_PERIOD = 6;

    private static final int OBSTACLE_NUM_PROPERTIES = 4;
    private static final int OBSTACLE_ID = 1;
    private static final int OBSTACLE_COL = 2;
    private static final int OBSTACLE_ROW = 3;

    private static final int EARTH_NUM_PROPERTIES = 4;
    private static final int EARTH_ID = 1;
    private static final int EARTH_COL = 2;
    private static final int EARTH_ROW = 3;

    private static final int METEOR_NUM_PROPERTIES = 5;
    private static final int METEOR_ID = 1;
    private static final int METEOR_COL = 2;
    private static final int METEOR_ROW = 3;
    private static final int METEOR_ACTION_PERIOD = 4;

    private static final int PLANET_NUM_PROPERTIES = 5;
    private static final int PLANET_ID = 1;
    private static final int PLANET_COL = 2;
    private static final int PLANET_ROW = 3;
    private static final int PLANET_ACTION_PERIOD = 4;

    private static final int BGND_NUM_PROPERTIES = 4;
    private static final int BGND_ID = 1;
    private static final int BGND_COL = 2;
    private static final int BGND_ROW = 3;
    //private UFOFactory UFOFactory = new UFOFactory();

    public static void load(Scanner in, WorldModel world, ImageStore imageStore)
    {
        int lineNumber = 0;
        while (in.hasNextLine())
        {
            try
            {
                if (!processLine(in.nextLine(), world, imageStore))
                {
                    System.err.println(String.format("invalid entry on line %d",
                            lineNumber));
                }
            }
            catch (NumberFormatException e)
            {
                System.err.println(String.format("invalid entry on line %d",
                        lineNumber));
            }
            catch (IllegalArgumentException e)
            {
                System.err.println(String.format("issue on line %d: %s",
                        lineNumber, e.getMessage()));
            }
            lineNumber++;
        }
    }

    private static boolean processLine(String line, WorldModel world,
                                      ImageStore imageStore)
    {
        String[] properties = line.split("\\s");
        if (properties.length > 0)
        {
            switch (properties[PROPERTY_KEY])
            {
                case EARTH_KEY:
                    return parseEarth(properties, world, imageStore);
                case STAR_KEY:
                    return parseStar(properties, world, imageStore);
                case DOWN_KEY:
                    return parseDown(properties, world, imageStore);
                case BGND_KEY:
                    return parseBackground(properties, world, imageStore);
                case UFO_KEY:
                    return parseUfo(properties, world, imageStore);
                case SHIP_KEY:
                    return parseShip(properties, world, imageStore);
                case SHIPB_KEY:
                    return parseShipB(properties, world, imageStore);
                case OBSTACLE_KEY:
                    return parseObstacle(properties, world, imageStore);
                case METEOR_KEY:
                    return parseMeteor(properties, world, imageStore);
                case PLANET_KEY:
                    return parsePlanet(properties, world, imageStore);
            }
        }

        return false;
    }

    private static boolean parseUfo(String [] properties, WorldModel world, ImageStore imageStore)
    {
        if (properties.length == UFO_NUM_PROPERTIES)
        {
            Point pt = new Point(Integer.parseInt(properties[UFO_COL]),
                    Integer.parseInt(properties[UFO_ROW]));
            UfoNotFull notfull = UFOFactory.createUfoNotFull(properties[UFO_ID],
                    Integer.parseInt(properties[UFO_LIMIT]),
                    pt,
                    Integer.parseInt(properties[UFO_ACTION_PERIOD]),
                    Integer.parseInt(properties[UFO_ANIMATION_PERIOD]),
                    imageStore.getImageList(UFO_KEY));
            world.tryAddEntity(notfull);
        }
        return properties.length == UFO_NUM_PROPERTIES;
    }

    private static boolean parseShip(String [] properties, WorldModel world, ImageStore imageStore)
    {
        if (properties.length == SHIP_NUM_PROPERTIES)
        {
            Point pt = new Point(Integer.parseInt(properties[SHIP_COL]),
                    Integer.parseInt(properties[SHIP_ROW]));
            Ship ship = Ship.getInstance1("ship", pt, Integer.parseInt(properties[SHIP_ACTION_PERIOD]), Integer.parseInt(properties[SHIP_ANIMATION_PERIOD]),  ImageStore.getImageList(SHIP_KEY), new Point(0, -1),0);
            world.tryAddEntity(ship);
        }
        return properties.length == SHIP_NUM_PROPERTIES;
    }
    private static boolean parseShipB(String [] properties, WorldModel world, ImageStore imageStore)
    {
        if (properties.length == SHIP_NUM_PROPERTIES)
        {
            Point pt = new Point(Integer.parseInt(properties[SHIP_COL]),
                    Integer.parseInt(properties[SHIP_ROW]));
            Ship shipB = Ship.getInstance2("shipB", pt, Integer.parseInt(properties[SHIP_ACTION_PERIOD]), Integer.parseInt(properties[SHIP_ANIMATION_PERIOD]), ImageStore.getImageList(SHIPB_KEY), new Point(0, -1), 0);
            world.tryAddEntity(shipB);
        }
        return properties.length == SHIP_NUM_PROPERTIES;
    }
    private static boolean parseStar(String [] properties, WorldModel world, ImageStore imageStore)
    {
        if (properties.length == STAR_NUM_PROPERTIES)
        {
            Point pt = new Point(Integer.parseInt(properties[STAR_COL]),
                    Integer.parseInt(properties[STAR_ROW]));
            Star star =  new Star(properties[STAR_ID],
                    pt, imageStore.getImageList(STAR_KEY),
                    Integer.parseInt(properties[STAR_ACTION_PERIOD]),
                    Integer.parseInt(properties[STAR_ANIMATION_PERIOD]), 0, 1, 2);
            world.tryAddEntity(star);
        }
        return properties.length == STAR_NUM_PROPERTIES;
    }
    private static boolean parseDown(String [] properties, WorldModel world, ImageStore imageStore)
    {
        if (properties.length == DOWN_NUM_PROPERTIES)
        {
            Point pt = new Point(
                    Integer.parseInt(properties[DOWN_COL]),
                    Integer.parseInt(properties[DOWN_ROW]));
            Entity entity =  new Down(properties[DOWN_ID],
                    pt, imageStore.getImageList(DOWN_KEY));
            world.tryAddEntity(entity);
        }
        return properties.length == DOWN_NUM_PROPERTIES;
    }

    private static boolean parseObstacle(String [] properties, WorldModel world, ImageStore imageStore)
    {
        if (properties.length == OBSTACLE_NUM_PROPERTIES)
        {
            Point pt = new Point(
                    Integer.parseInt(properties[OBSTACLE_COL]),
                    Integer.parseInt(properties[OBSTACLE_ROW]));
            Obstacle obstacle = new Obstacle(properties[OBSTACLE_ID],
                    pt, imageStore.getImageList(OBSTACLE_KEY));
            world.tryAddEntity(obstacle);
        }
        return properties.length == OBSTACLE_NUM_PROPERTIES;
    }

    private static boolean parseMeteor(String [] properties, WorldModel world, ImageStore imageStore)
    {
        if (properties.length == METEOR_NUM_PROPERTIES)
        {
            Point pt = new Point(Integer.parseInt(properties[METEOR_COL]),
                    Integer.parseInt(properties[METEOR_ROW]));
            Meteor meteor = new Meteor(properties[METEOR_ID],
                    pt, Integer.parseInt(properties[METEOR_ACTION_PERIOD]),
                    imageStore.getImageList(METEOR_KEY));
            world.tryAddEntity(meteor);
        }
        return properties.length == METEOR_NUM_PROPERTIES;
    }
    private static boolean parseEarth(String [] properties, WorldModel world, ImageStore imageStore)
    {
        if (properties.length == EARTH_NUM_PROPERTIES)
        {
            Point pt = new Point(Integer.parseInt(properties[EARTH_COL]),
                    Integer.parseInt(properties[EARTH_ROW]));
            Earth earth = new Earth(properties[EARTH_ID],
                    pt, imageStore.getImageList(EARTH_KEY));
            world.tryAddEntity(earth);
        }
        return properties.length == EARTH_NUM_PROPERTIES;
    }

    private static boolean parsePlanet(String [] properties, WorldModel world, ImageStore imageStore)
    {
        if (properties.length == PLANET_NUM_PROPERTIES)
        {
            Point pt = new Point(Integer.parseInt(properties[PLANET_COL]),
                    Integer.parseInt(properties[PLANET_ROW]));
            Planet planet = new Planet(properties[PLANET_ID],
                    pt,
                    Integer.parseInt(properties[PLANET_ACTION_PERIOD]),
                    imageStore.getImageList(PLANET_KEY));
            world.tryAddEntity(planet);
        }
        return properties.length == PLANET_NUM_PROPERTIES;
    }

    private static boolean parseBackground(String [] properties, WorldModel world, ImageStore imageStore)
    {
        if (properties.length == BGND_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[BGND_COL]), Integer.parseInt(properties[BGND_ROW]));
            String id = properties[BGND_ID];
            world.setBackground(pt, new Background(id, imageStore.getImageList(id)));
        }
        return properties.length == BGND_NUM_PROPERTIES;
    }

}
