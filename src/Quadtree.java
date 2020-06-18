
import java.util.*;

/**********************************************************************************
 * This is the Quadtree class, a data structure used in spatial partitioning
 *	into sub-quadrants used for fast approximation of coordinate points within 
 * its hierarchy.&nbsp;My version here is made from the top down as soon as it is
 * initialized through its constructor.&nbsp;An empty quadtree here has all AABB
 * lists initialized but empty.&nbsp;Recursive navigation down the hierarchy is
 * efficient in that only simple minimum and maximum boundary checks are
 * required.&nbsp;The maximum tree depth will determine the precision of proximity
 * testing between the AABBs.&nbsp;The quadtree is partitioned as NW,NE,SW,SE.
 *
 * @author Arash J. Farmand
 * @version 3.16
 * @since 2020-06-12
 *********************************************************************************/
public class Quadtree {

	Quadnode root;
	int max_Tree_Depth;
	AABB[] all_AABBs;

	/*******************************************************************************
	 * Quadnode class.&nbsp;It contains two coordinates which are used to determine
	 * its top-left corner (x1,y1) and its bottom-right corner (x2,y2) meant for
	 * efficient calculation.&nbsp;The combination of these coordinates will be 
	 * used for measuring width, height and center points of the quadrants.
	 *******************************************************************************/
	public class Quadnode {

		int x1, y1, x2, y2, cntr_x, cntr_y;
		int tree_Depth;
		Quadnode nw, ne, sw, se;
		LinkedList<AABB> aabbs;

		/****************************************************************************
		 * Constructor to create a default Quadtree node.&nbsp;Like an AABB it is made
		 * up of two coordinate points to determine its top left and bottom right
		 * corners.&nbsp;The center point (cntr_x, cntr_y) is also calculated when this
		 * constructor is called.&nbsp;Quadnodes contain an (initially empty) list of
		 * objects that intersect its boundaries. 
		 *
		 * @param _x1 The top left x position of this quadnode.
		 * @param _y1 The top left y position of this quadnode.
		 * @param _x2 The bottom right x position of this quadnode.
		 * @param _y2 The bottom right y position of this quadnode.
		 * @param _tree_Depth The wanted tree depth of this quadnode.
		 ***************************************************************************/
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
	}
	
	/*******************************************************************************
	 * Constructor for the Quadtree.&nbsp;Build the Quadtree from the top down by
	 * calling "build_Quadtree" to begin the process.&nbsp;If square is "true", before
	 * building the Quadnodes, size the root node so that it is square (both width
	 * and height will be the largest of the window's width and height).&nbsp;If the
	 * root Quadnode is not square, none of the Quadnodes will be square and ghost
	 * collisions may occur more frequently on the axis where Quadnodes are wider
	 * (or taller).&nbsp;A square quadtree in a rectangular screen or window means part
	 * of the Quadtree is off screen but performance should remain the same.
	 *
	 * @param aabb_Array The list of AABBs to track by this quadtree.            
	 * @param wdth The overall width of this quadtree.
	 * @param hght The overall height of this quadtree.
	 * @param mtd The maximum tree depth for this quadtree.
	 * @param square If set to "true" quadtree will have a square shape otherwise
	 * the quadtree will be rectangular.
	 ******************************************************************************/
	public Quadtree(AABB[] aabb_Array, int wdth, int hght, int mtd, boolean square) {
		all_AABBs = aabb_Array;
		max_Tree_Depth = mtd;
		
		reshape(wdth, hght, square);
	}
	
	/*******************************************************************************
    * Recursively create and return the Quadnodes until the Quadtree reaches
	 * the maximum allowed tree depth defined by "max_Tree_Depth".&nbsp;The Quadtree
	 * will be completed with empty AABB lists and ready to be used for fast
	 * recursive compartmentalization of AABBs provided to the Quadtree.
	 *
	 * @param _x1 The top left x coordinate of this quadtree.
	 * @param _y1 The top left y coordinate of this quadtree.
	 * @param _x2 The bottom right x coordinate of this quadtree.
	 * @param _y2 The bottom right y coordinate of this quadtree.
	 * @param td The tree depth required for the calling quadnode.
	 * @return A new Quadnode built from the given parameters.
    ******************************************************************************/
	public Quadnode build_Quadtree(int _x1, int _y1, int _x2, int _y2, int td) {
		Quadnode node = null;
		if (td <= max_Tree_Depth) {
			node = new Quadnode(_x1, _y1, _x2, _y2, td);
			node.nw = build_Quadtree(_x1, _y1, node.cntr_x, node.cntr_y, td + 1);
			node.ne = build_Quadtree(node.cntr_x, _y1, _x2, node.cntr_y, td + 1);
			node.sw = build_Quadtree(_x1, node.cntr_y, node.cntr_x, _y2, td + 1);
			node.se = build_Quadtree(node.cntr_x, node.cntr_y, _x2, _y2, td + 1);
		}
		return (node);
	}
	
	/****************************************************************************
    * Iterate through the given Quadnodes's AABB list and check to see where
	 * the AABBs lie in the given Quadnode's four sub-quadnodes and add it to
	 * that sub-quadnode.&nbsp;After having passed through the AABB list,
	 * recursively call this method against this quadnodes's sub-quadnodes that
	 * contain at least 2 items within its AABB list and continue this process
	 * until either the maximum tree depth has been reached or recursive calls
	 * do not present a situation which requires further searching.&nbsp;If the
	 * maximum tree depth is reached, all AABBs within the quadnode that
	 * reached the maximum tree depth are considered to be within close
	 * proximity to each other.
	 *
	 * @param node The node of interest to search for.
	 ***************************************************************************/
	public void insert(Quadnode node) {
		if (node.tree_Depth < max_Tree_Depth) {
			if (node.aabbs.size() > 1) {
				for (AABB aabb : node.aabbs) {
					if (aabb.y1 < node.cntr_y) {
						if (aabb.x1 < node.cntr_x) {
							if (node.nw != null) {
								node.nw.aabbs.add(aabb);
							}
						}
						if (aabb.x2 > node.cntr_x) {
							if (node.ne != null) {
								node.ne.aabbs.add(aabb);
							}
						}
					}
					if (aabb.y2 > node.cntr_y) {
						if (aabb.x1 < node.cntr_x) {
							if (node.sw != null) {
								node.sw.aabbs.add(aabb);
							}
						}
						if (aabb.x2 > node.cntr_x) {
							if (node.se != null) {
								node.se.aabbs.add(aabb);
							}
						}
					}
				}
				insert(node.nw);
				insert(node.ne);
				insert(node.sw);
				insert(node.se);
			}
		} else {
			node.aabbs.forEach((aabb) -> {
				set_Nearby(node.aabbs, aabb);
			});
		}
	}

	/*******************************************************************************
	 * Recursively iterate down the Quadtree and clear all AABB lists within
	 * the Quadnodes.
	 *
	 * @param node The node who's AABB list is to be cleared.
	 ******************************************************************************/
	private void reset_Quadnodes(Quadnode node) {
		if (node != null) {
			if (!node.aabbs.isEmpty()) {
				node.aabbs.clear();
			}
			reset_Quadnodes(node.nw);
			reset_Quadnodes(node.ne);
			reset_Quadnodes(node.sw);
			reset_Quadnodes(node.se);
		}
	}
	
	/*******************************************************************************
	 * Set this quadtree's shape to either square or rectangle.
	 *
	 * @param wdth The required width of this quadtree. If the value of "square"
	 * is "true", wdth will be adjusted so that its value is that of the largest of
	 * wdth and hght.
	 * @param hght The required height of this quadtree. If the value of "square"
	 * is "true", hght will be adjusted so that its value is that of the largest of
	 * wdth and hght.
	 * @param square Represents whether or not this quadtree is to be square.
	 * "false" would indicate that this quadtree is to be rectangular in shape.
	 ******************************************************************************/
	public void reshape(int wdth, int hght, boolean square){
		if(square)
			wdth = hght = wdth > hght ? wdth - 1 : hght - 1;
		root = build_Quadtree(0, 0, wdth, hght, 0);
	}

	/*******************************************************************************
	 * Set the maximum tree depth to "mtd".&nbsp;The quadtree must be rebuilt if the
	 * tree depth is changed.
	 * 
	 * @param mtd The maximum tree depth for this quadtree.
	 ******************************************************************************/
	public void set_Max_Tree_Depth(int mtd) {
		if (max_Tree_Depth != mtd) {
			max_Tree_Depth = (mtd < 1 || mtd > 10) ? max_Tree_Depth : mtd;
			root = build_Quadtree(root.x1, root.y1, root.x2, root.y2, 0);
			root.aabbs.addAll(Arrays.asList(all_AABBs));
		}
	}
	
	/*******************************************************************************
	 * Every AABB in the given aabb list will set their "nearby" AABB to
	 * "target".
	 *
	 * @param target The AABB which will be set as the "nearby" AABB for all items 
	 * in aabb_List.
	 * @param  aabb_List The AABB list who's contents is to have all their 
	 * "nearby" AABBs set to "target".
	 ******************************************************************************/
	private void set_Nearby(LinkedList<AABB> aabb_List, AABB target){						
		for(AABB aabb: aabb_List)
			aabb.set_Nearby(target);

		///////////////////////////////////////////////////////////////////////
		// At this point we can go to phase TWO of collision detection...    //
		///////////////////////////////////////////////////////////////////////
	}

	/*******************************************************************************
	 * Update the Quadtree by emptying the leaf Quadnodes' AABB lists and then
	 * inserting the AABBs one after another into the Quadtree.&nbsp;All AABB
	 * "nearby_AABBs" lists cleared until it is determined again that there are
	 * AABBs nearby as determined by the "insert" method.
	 *******************************************************************************/
	public void update() {
		reset_Quadnodes(root);
		root.aabbs.addAll(Arrays.asList(all_AABBs));
		for (AABB aabb : all_AABBs) {
			aabb.nearby = null;
		}
		insert(root);
	}
}
