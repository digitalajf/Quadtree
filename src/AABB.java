
/** ********************************************************************************
 * An AABB (Axis-Aligned Bounding Box) is a simple rectangular box containing two
 * coordinates: the top left corner (x1,y1) and the bottom right corner
 * (x2,y2).&nbsp;These two coordinates are used to determine its location, and
 * dimensions.&nbsp;"Axis-Aligned" means that the edges determining its width and
 * height are always parallel to the coordinate space in which they lie in.
 *
 * @author Arash J. Farmand
 * @version 3.45
 * @since 2021-10-18
 ******************************************************************************** */
public class AABB {

   ////////////////////////////////////////////////////////////////////////////////
   // The AABB is made up of 2 points which determine its top left (x1,y1) and   //
   // bottom right corner (x2,y2). Default posistion will be 0,0 and default size// 
   // using this model will be width=50, height=50, "nearby" will mark this AABB //
   // as being within close proximity to another AABB.                           //
   ////////////////////////////////////////////////////////////////////////////////
   /**
    * Top left x coordinate.
    */
   private double x1;
   /**
    * Top left y coordinate.
    */
   private double y1;
   /**
    * Bottom right x coordinate.
    */
   private double x2;
   /**
    * Bottom right y coordinate.
    */
   private double y2;
   /**
    * Width of this AABB
    */
   private double wdth;
   /**
    * Height of this AABB
    */
   private double hght;
   /**
    * x direction of travel
    */
   private double dx;
   /**
    * y direction of travel
    */
   private double dy;
   /**
    * AABB that is determined to be nearby
    */
   private AABB nearby;

   /**
    * *****************************************************************************
    * Default Constructor for AABB class which will set the top left coordinates to 0,0 and bottom
    * right coordinates to 50,50.&nbsp;This means the width and height will be set to 50,50
    * respectively.
    *****************************************************************************
    */
   public AABB() {
      x1 = 0;
      y1 = 0;
      x2 = 50;
      y2 = 50;
      dx = 0;
      dy = 0;
      wdth = x2;
      hght = y2;
      nearby = null;
   }

   /**
    * *****************************************************************************
    * Constructor for AABB class with parameters to set location and size.
    *
    * @param _x1 The top left 'x' coordinate of this AABB.
    * @param _y1 The top left 'y' coordinate of this AABB.
    * @param _x2 The bottom right 'x' coordinate of this AABB.
    * @param _y2 The bottom right 'y' coordinate of this AABB.
    *****************************************************************************
    */
   public AABB(int _x1, int _y1, int _x2, int _y2) {
      x1 = _x1;
      y1 = _y1;
      x2 = _x2;
      y2 = _y2;
      dx = 0;
      dy = 0;
      wdth = x2 - x1;
      hght = y2 - y1;
      nearby = null;
   }

   /**
    * *****************************************************************************
    * Determine whether or not this AABB intersects with another AABB.
    *
    * @param aabb The AABB against which to check for intersection with this AABB.
    * @return true if this AABB intersects the given aabb otherwise return false.
    *****************************************************************************
    */
   public boolean collides_With(AABB aabb) {
      if (x1 > aabb.x2 || aabb.x1 > x2) {
         return (false);
      }

      if (y2 < aabb.y1 || aabb.y2 < y1) {
         return (false);
      }

      return (true);
   }

   /**
    * *****************************************************************************
    * Return the x velocity of this AABB.
    *
    * @return "dx" which is the x velocity of this AABB.
    *****************************************************************************
    */
   public double get_dx() {
      return (dx);
   }

   /**
    * *****************************************************************************
    * Return the y velocity of this AABB.
    *
    * @return "dy" which is the y velocity of this AABB.
    *****************************************************************************
    */
   public double get_dy() {
      return (dy);
   }

   /**
    * *****************************************************************************
    * Return the height of this AABB.
    *
    * @return height of this AABB.
    *****************************************************************************
    */
   public double get_Height() {
      return (hght);
   }

   /**
    * *****************************************************************************
    * Return the width of this AABB.
    *
    * @return width of this AABB.
    *****************************************************************************
    */
   public double get_Width() {
      return (wdth);
   }

   /**
    * *****************************************************************************
    * Return the left-most X coordinate of this AABB.
    *
    * @return "x1" which is the left-most X coordinate of this AABB.
    *****************************************************************************
    */
   public double get_X1() {
      return (x1);
   }

   /**
    * *****************************************************************************
    * Return the right-most X coordinate of this AABB.
    *
    * @return "x2" which is the right-most X coordinate of this AABB.
    *****************************************************************************
    */
   public double get_X2() {
      return (x2);
   }

   /**
    * *****************************************************************************
    * Return the top-most Y coordinate of this AABB.
    *
    * @return "y1" which is the top-most Y coordinate of this AABB.
    *****************************************************************************
    */
   public double get_Y1() {
      return (y1);
   }

   /**
    * *****************************************************************************
    * Return the bottom-most Y coordinate of this AABB.
    *
    * @return "y2" which is the bottom-most Y coordinate of this AABB.
    *****************************************************************************
    */
   public double get_Y2() {
      return (y2);
   }

   /**
    * *****************************************************************************
    * Return the AABB which is the AABB that has been marked as being near this AABB (if it exists).
    *
    * @return "nearby" which is the AABB that has been marked as being near this AABB (if it
    * exists).
    *****************************************************************************
    */
   public AABB get_Nearby() {
      return (nearby);
   }

   /**
    * *****************************************************************************
    * Move this AABB by delta_x, delta_y.
    *
    * @param delta_x The delta value for which to increment the "x" position of this AABB.
    * @param delta_y The delta value for which to increment the "y" position of this AABB.
    *****************************************************************************
    */
   public void relocate(double delta_x, double delta_y) {
      x1 += delta_x;
      y1 += delta_y;
      x2 += delta_x;
      y2 += delta_y;
   }

   /**
    * *****************************************************************************
    * Set the "x" velocity of this AABB to "_dx".
    *
    * @param _dx the required value to set the "x" velocity
    *****************************************************************************
    */
   public void set_dx(double _dx) {
      dx = _dx;
   }

   /**
    * *****************************************************************************
    * Set the "y" velocity of this AABB to "_dy".
    *
    * @param _dy the required value to set the "y" velocity
    *****************************************************************************
    */
   public void set_dy(double _dy) {
      dy = _dy;
   }

   /**
    * *****************************************************************************
    * Toggle whether this AABB is near other AABBs.
    *
    * @param nrby The AABB to set as this AABB's nearby object.
    *****************************************************************************
    */
   public void set_Nearby(AABB nrby) {
      nearby = nrby;
   }

   /**
    * *****************************************************************************
    * Set the size by changing x2,y2 to be w,h distance from x1,y1.
    *
    * @param w The width value wanted for this AABB.
    * @param h The height value wanted for this AABB.
    *****************************************************************************
    */
   public void set_Size(double w, double h) {
      x2 = x1 + w;
      y2 = y1 + h;
      wdth = x2 - x1;
      hght = y2 - y1;
   }

   /**
    * *****************************************************************************
    * change the direction and speed of this AABB.
    *
    * @param delta_x The x value for movement speed and direction.
    * @param delta_y The y value for movement speed and direction.
    *****************************************************************************
    */
   public void set_Velocity(double delta_x, double delta_y) {
      dx = delta_x;
      dy = delta_y;
   }
}
