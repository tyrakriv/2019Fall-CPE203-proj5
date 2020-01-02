import java.util.List;

import processing.core.PImage;

/*
Entity ideally would includes functions for how all the entities in our virtual world might act...
 */

public abstract class Entity extends UFOFactory {
   private String id;
   private Point position;
   private List<PImage> images;
   private int imageIndex;

   public Entity(final String id, Point position, List<PImage> images)
   {
      this.id = id;
      this.position = position;
      this.images = images;
      this.imageIndex = 0;
   }

    public String getId() { return id; }
   public Point getPosition(){ return position;}
   public void setPosition(Point position) {
      this.position = position;
   }
   public List<PImage> getImages() { return images; }
   public int getImageIndex() { return imageIndex; }
   public void setImageIndex(int imageIndex) { this.imageIndex = imageIndex; }
   public PImage getCurrentImage() {
      return getImages().get(getImageIndex());

   }

}
