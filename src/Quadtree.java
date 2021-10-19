
import java.util.*;

/** ********************************************************************************
 * This is the Quadtree class, a data structure used in spatial partitioning into
 * sub-quadrants used for fast approximation of coordinate points within its
 * hierarchy.&nbsp;My version here is made from the top down as soon as it is
 * initialized through its constructor.&nbsp;An empty quadtree here has all AABB
 * lists initialized but empty.&nbsp;Recursive navigation down the hierarchy is
 * efficient in that only simple minimum and maximum boundary checks are required.
 * &nbsp;The maximum tree depth will determine the precision of proximity testing
 * between the AABBs.&nbsp;The quadtree is partitioned as NW,NE,SW,SE.
 *
 * @author Arash J. Farmand
 * @version 3.19
 * @since 2021-10-18
 ******************************************************************************** */
public class Quadtree {

   /**
    * Quadnode at the very top of this Quadtree
    */
   private Quadnode root;
   /**
    * Value representing the maximum depth that this Quadtree can search
    */
   private int max_Tree_Depth;
   /**
    * Array containing all AABBs
    */
   private AABB[] all_AABBs;

   /** *****************************************************************************
    * Constructor for the Quadtree.&nbsp;Build the Quadtree from the top down by
    * calling "build_Quadtree" to begin the process.&nbsp;If square is "true",
    * before building the Quadnodes, size the root node so that it is square (both
    * width and height will be the largest of the window's width and
    * height).&nbsp;If the root Quadnode is not square, none of the Quadnodes will
    * be square and ghost collisions may occur more frequently on the axis where
    * Quadnodes are wider (or taller).&nbsp;A square quadtree in a rectangular
    * screen or windowmeans part of the Quadtree is off screen but performance
    * should remain the same.
    *
    * @param aabb_Array The list of AABBs to track by this quadtree.
    * @param wdth The overall width of this quadtree.
    * @param hght The overall height of this quadtree.
    * @param mtd The maximum tree depth for this quadtree.
    * @param square If set to "true" quadtree will have a square shape otherwise
    * the quadtree will be rectangular.
    ***************************************************************************** */
   public Quadtree(AABB[] aabb_Array, int wdth, int hght, int mtd, boolean square) {
      all_AABBs = aabb_Array;
      max_Tree_Depth = mtd;

      reshape(wdth, hght);
   }

   /** *****************************************************************************
    * Recursively create and return the Quadnodes until the Quadtree reaches the
    * maximum allowed tree depth defined by "max_Tree_Depth".&nbsp;The Quadtree
    * will be completed with empty AABB lists and ready to be used for fast
    * recursive compartmentalization of AABBs provided to the Quadtree.
    *
    * @param _x1 The top left x coordinate of this quadtree.
    * @param _y1 The top left y coordinate of this quadtree.
    * @param _x2 The bottom right x coordinate of this quadtree.
    * @param _y2 The bottom right y coordinate of this quadtree.
    * @param td The tree depth required for the calling quadnode.
    * @return A new Quadnode built from the given parameters.
    ***************************************************************************** */
   public Quadnode build_Quadtree(int _x1, int _y1, int _x2, int _y2, int td) {
      Quadnode node = null;
      if (td <= max_Tree_Depth) {
         node = new Quadnode(_x1, _y1, _x2, _y2, td);
         node.set_NW(build_Quadtree(_x1, _y1, node.get_Cntr_X(), node.get_Cntr_Y(), td + 1));
         node.set_NE(build_Quadtree(node.get_Cntr_X(), _y1, _x2, node.get_Cntr_Y(), td + 1));
         node.set_SW(build_Quadtree(_x1, node.get_Cntr_Y(), node.get_Cntr_X(), _y2, td + 1));
         node.set_SE(build_Quadtree(node.get_Cntr_X(), node.get_Cntr_Y(), _x2, _y2, td + 1));
      }
      return (node);
   }

   /** *****************************************************************************
    * Return the AABB array containing all AABBs in this Quadtree.
    *
    * @return the AABB array containing all AABBs in this Quadtree.
    ***************************************************************************** */
   public AABB[] get_All_AABBs() {
      return (all_AABBs);
   }

   /** *****************************************************************************
    * Return the root Quadnode of this Quadtree.
    *
    * @return the root Quadnode of this Quadtree.
    ***************************************************************************** */
   public Quadnode get_Root() {
      return (root);
   }

   /** *****************************************************************************
    * Iterate through the given Quadnodes's AABB list and check to see where the
    * AABBs lie in the given Quadnode's four sub-quadnodes and add it to that
    * sub-quadnode.&nbsp;After having passed through the AABB list, recursively
    * call this method against this quadnodes's sub-quadnodes that contain at
    * least 2 items within its AABB list and continue this process until either
    * the maximum tree depth has been reached or recursive calls do not present a
    * situation which requires further searching.&nbsp;If the maximum tree depth
    * is reached, all AABBs within the quadnode that reached the maximum tree
    * depth are considered to be within close proximity to each other.
    *
    * @param node The node of interest to search for.
    ***************************************************************************** */
   public void insert(Quadnode node) {
      LinkedList<AABB> aabbs = node.get_AABBs();
      if (node.get_Tree_Depth() < max_Tree_Depth) {
         if (node.get_AABBs().size() > 1) {
            for (AABB aabb : aabbs) {
               if (aabb.get_Y1() < node.get_Cntr_Y()) {
                  if (aabb.get_X1() < node.get_Cntr_X()) {
                     if (node.get_NW() != null) {
                        node.get_NW().get_AABBs().add(aabb);
                     }
                  }
                  if (aabb.get_X2() > node.get_Cntr_X()) {
                     if (node.get_NE() != null) {
                        node.get_NE().get_AABBs().add(aabb);
                     }
                  }
               }
               if (aabb.get_Y2() > node.get_Cntr_Y()) {
                  if (aabb.get_X1() < node.get_Cntr_X()) {
                     if (node.get_SW() != null) {
                        node.get_SW().get_AABBs().add(aabb);
                     }
                  }
                  if (aabb.get_X2() > node.get_Cntr_X()) {
                     if (node.get_SE() != null) {
                        node.get_SE().get_AABBs().add(aabb);
                     }
                  }
               }
            }
            insert(node.get_NW());
            insert(node.get_NE());
            insert(node.get_SW());
            insert(node.get_SE());
         }
      } else {
         aabbs.forEach((aabb) -> {
            set_Nearby(aabbs, aabb);
         });
      }
   }

   /** *****************************************************************************
    * Recursively iterate down the Quadtree and clear all AABB lists within the
    * Quadnodes.
    *
    * @param node The node who's AABB list is to be cleared.
    ***************************************************************************** */
   private void reset_Quadnodes(Quadnode node) {
      if (node != null) {
         if (!node.get_AABBs().isEmpty()) {
            node.get_AABBs().clear();
         }
         reset_Quadnodes(node.get_NW());
         reset_Quadnodes(node.get_NE());
         reset_Quadnodes(node.get_SW());
         reset_Quadnodes(node.get_SE());
      }
   }

   /** *****************************************************************************
    * Set this quadtree's shape to either square or rectangle.
    *
    * @param wdth The required width of this quadtree. If the value of "square" is
    * "true", wdth will be adjusted so that its value is that of the largest of
    * wdth and hght.
    * @param hght The required height of this quadtree. If the value of "square"
    * is "true", hght will be adjusted so that its value is that of the largest
    * of wdth and hght.
    ***************************************************************************** */
   public void reshape(int wdth, int hght) {
      root = build_Quadtree(0, 0, wdth, hght, 0);
   }

   /** *****************************************************************************
    * Set the "all_AABBs" array to "_AABBs".
    *
    * @param _AABBs Set the "all_AABBS" array to the given array
    ***************************************************************************** */
   public void set_All_AABBs(AABB[] _AABBs) {
      all_AABBs = _AABBs;
   }

   /** *****************************************************************************
    * Set the maximum tree depth to "mtd".&nbsp;The quadtree must be rebuilt if
    * the tree depth is changed.
    *
    * @param mtd The maximum tree depth for this quadtree.
    ***************************************************************************** */
   public void set_Max_Tree_Depth(int mtd) {
      if (max_Tree_Depth != mtd) {
         max_Tree_Depth = (mtd < 1 || mtd > 10) ? max_Tree_Depth : mtd;
         root = build_Quadtree(root.get_X1(), root.get_Y1(), root.get_X2(), root.get_Y2(), 0);
         root.get_AABBs().addAll(Arrays.asList(all_AABBs));
      }
   }

   /** *****************************************************************************
    * Every AABB in the given aabb list will set their "nearby" AABB to "target".
    *
    * @param target The AABB which will be set as the "nearby" AABB for all items
    * in aabb_List. @param aabb_List The AABB list who's contents is to have all
    * their "nearby" AABBs set to "target".
    ***************************************************************************** */
   private void set_Nearby(LinkedList<AABB> aabb_List, AABB target) {
      for (AABB aabb : aabb_List) {
         aabb.set_Nearby(target);
      }

      ///////////////////////////////////////////////////////////////////////
      // At this point we can go to phase TWO of collision detection...    //
      ///////////////////////////////////////////////////////////////////////
   }

   /** *****************************************************************************
    * Update the Quadtree by emptying the leaf Quadnodes' AABB lists and then
    * inserting the AABBs one after another into the Quadtree.&nbsp;All AABB
    * "nearby_AABBs" lists cleared until it is determined again that there are
    * AABBs nearby as determined by the "insert" method.
    ***************************************************************************** */
   public void update() {
      reset_Quadnodes(root);
      root.get_AABBs().addAll(Arrays.asList(all_AABBs));
      for (AABB aabb : all_AABBs) {
         aabb.set_Nearby(null);
      }
      insert(root);
   }
}
