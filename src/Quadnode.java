
import java.util.LinkedList;

/** ********************************************************************************
 * Quadnode class.&nbsp;It contains two coordinates which are used to determine
 * its top-left corner (x1,y1) and its bottom-right corner (x2,y2) meant for
 * efficient calculation.&nbsp;The combination of these coordinates will be used
 * for measuring width, height and center points of the quadrants.
 *
 * @author Arash J. Farmand
 * @version 3.19
 * @since 2021-10-18
 ******************************************************************************** */
public class Quadnode {

   /**
    * Top left x coordinate
    */
   private int x1;
   /**
    * Top left y coordinate
    */
   private int y1;
   /**
    * Bottom right x coordinate
    */
   private int x2;
   /**
    * Bottom right y coordinate
    */
   private int y2;
   /**
    * Center x coordinate
    */
   private int cntr_x;
   /**
    * Center y coordinate
    */
   private int cntr_y;
   /**
    * Tree depth in which this Quadnode lies
    */
   private int tree_Depth;
   /**
    * North-West sub-Quadnode
    */
   private Quadnode nw;
   /**
    * North-East sub-Quadnode
    */
   private Quadnode ne;
   /**
    * South-West sub-Quadnode
    */
   private Quadnode sw;
   /**
    * South-East sub-Quadnode
    */
   private Quadnode se;
   /**
    * LinkedList of AABBs intersecting the boundaries of this Quadnode
    */
   private LinkedList<AABB> aabbs;

   /** *****************************************************************************
    * Constructor to create a default Quadtree node.&nbsp;Like an AABB it is made
    * up of two coordinate points to determine its top left and bottom right
    * corners.&nbsp;The center point (cntr_x, cntr_y) is also calculated when this
    * constructor is called.&nbsp;Quadnodes contain an (initially empty) list of
    * objects that intersect its boundaries.
    *
    * @param _x1 The left-most "x" coordinate of this quadnode.
    * @param _y1 The top-most "y" coordinate of this quadnode.
    * @param _x2 The right-most "x" coordinate of this quadnode.
    * @param _y2 The bottom-most "y" coordinate of this quadnode.
    * @param _tree_Depth The wanted tree depth of this quadnode.
    ******************************************************************************* */
   public Quadnode(int _x1, int _y1, int _x2, int _y2, int _tree_Depth) {
      x1 = _x1;
      y1 = _y1;
      x2 = _x2;
      y2 = _y2;
      cntr_x = (x1 + x2) / 2;
      cntr_y = (y1 + y2) / 2;
      tree_Depth = _tree_Depth;
      aabbs = new LinkedList<>();
   }

   /** *****************************************************************************
    * Return the LinkedList containing AABBs that this Quadnode is monitoring.
    *
    * @return the LinkedList "aabbs" containing AABBs that are being monitored by
    * this Quadnode.
    ***************************************************************************** */
   public LinkedList<AABB> get_AABBs() {
      return (aabbs);
   }

   /** *****************************************************************************
    * Return the "x" value of the center of this Quadnode.
    *
    * @return the "x" value of the center of this Quadnode.
    ***************************************************************************** */
   public int get_Cntr_X() {
      return (cntr_x);
   }

   /** *****************************************************************************
    * Return the "y" value of the center of this Quadnode.
    *
    * @return the "y" value of the center of this Quadnode.
    ***************************************************************************** */
   public int get_Cntr_Y() {
      return (cntr_y);
   }

   /** *****************************************************************************
    * Return the Quadnode which makes up the top-right sub-quadrant of this Quadnode.
    *
    * @return the Quadnode "ne" which makes up the top-right sub-quadrant of this
    * Quadnode.
    ***************************************************************************** */
   public Quadnode get_NE() {
      return (ne);
   }

   /** *****************************************************************************
    * Return the Quadnode which makes up the top-left sub-quadrant of this Quadnode.
    *
    * @return the Quadnode "nw" which makes up the top-left sub-quadrant of this
    * Quadnode.
    ***************************************************************************** */
   public Quadnode get_NW() {
      return (nw);
   }

   /** *****************************************************************************
    * Return the Quadnode which makes up the bottom-right sub-quadrant of this
    * Quadnode.
    *
    * @return the Quadnode "se" which makes up the bottom-right sub-quadrant of
    * this Quadnode.
    ***************************************************************************** */
   public Quadnode get_SE() {
      return (se);
   }

   /** *****************************************************************************
    * Return the Quadnode which makes up the bottom-left sub-quadrant of this
    * Quadnode.
    *
    * @return the Quadnode "sw" which makes up the bottom-left sub-quadrant of
    * this Quadnode.
    ***************************************************************************** */
   public Quadnode get_SW() {
      return (sw);
   }

   /** *****************************************************************************
    * Return current tree depth of this Quadnode.
    *
    * @return current tree depth of this Quadnode.
    ***************************************************************************** */
   public int get_Tree_Depth() {
      return (tree_Depth);
   }

   /** *****************************************************************************
    * Return the left-most X coordinate of this Quadnode.
    *
    * @return the left-most X coordinate of this Quadnode
    ***************************************************************************** */
   public int get_X1() {
      return (x1);
   }

   /** *****************************************************************************
    * Return the right-most X coordinate of this Quadnode.
    *
    * @return the right-most X coordinate of this Quadnode.
    ***************************************************************************** */
   public int get_X2() {
      return (x2);
   }

   /** *****************************************************************************
    * Return the top-most Y coordinate of this Quadnode.
    *
    * @return the top-most Y coordinate of this Quadnode.
    ***************************************************************************** */
   public int get_Y1() {
      return (y1);
   }

   /** *****************************************************************************
    * Return the bottom-most Y coordinate of this Quadnode.
    *
    * @return the bottom-most Y coordinate of this Quadnode.
    ***************************************************************************** */
   public int get_Y2() {
      return (y2);
   }

   /** *****************************************************************************
    * Set the upper left sub-Quadnode.
    *
    * @param _nw the Quadnode to set as the upper left sub-Quadnode.
    ***************************************************************************** */
   public void set_NW(Quadnode _nw) {
      nw = _nw;
   }

   /** *****************************************************************************
    * Set the upper right sub-Quadnode.
    *
    * @param _ne the Quadnode to set as the upper right sub-Quadnode.
    ***************************************************************************** */
   public void set_NE(Quadnode _ne) {
      ne = _ne;
   }

   /** *****************************************************************************
    * Set the lower left sub-Quadnode.
    *
    * @param _sw the Quadnode to set as the lower left sub-Quadnode.
    ***************************************************************************** */
   public void set_SW(Quadnode _sw) {
      sw = _sw;
   }

   /** *****************************************************************************
    * Set the lower right sub-Quadnode.
    *
    * @param _se the Quadnode to set as the lower left sub-Quadnode.
    ***************************************************************************** */
   public void set_SE(Quadnode _se) {
      se = _se;
   }
}
