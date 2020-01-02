/*
Action: ideally what our various entities might do in our virutal world
 */

public interface Action {
   void executeAction(EventScheduler scheduler);
   //WorldModel getWorld();


   /*public void executeAction(EventScheduler scheduler) {
      switch (this.kind) {
         case ACTIVITY:
            executeActivityAction(scheduler);
            break;

         case ANIMATION:
            executeAnimationAction(scheduler);
            break;
      }
   }*/
}
