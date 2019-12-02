
/**
 * This is the Quadtree class, a data structure used in spatial partitioning
 *	into sub-quadrants used for fast approximation of coordinate points within 
 * its hierarchy. My version here is made from the top down as soon as it is
 * initialized through its constructor. An empty quadtree here has all AABB
 * lists initialized but empty. Recursive navigation down the hierarchy is
 * efficient in that only simple minimum and maximum boundary checks are
 * required. The maximum tree depth will determine the precision of proximity
 * testing between the AABBs. The quadtree would be the broad phase (phase ONE)
 * of collision detection. The quadtree is partitioned as NW,NE,SW,SE.
 *
 * @author Arash J. Farmand
 * @version 3.01
 * @date 2019-12-02
 * @since 2019-11-24
 */
import java.util.*;

public class Quadtree {

	Quadnode root;
	int max_Tree_Depth;
	AABB[] all_AABBs;

	////////////////////////////////////////////////////////////////////////////////
	// Quadnode class. It contains 4 coordinates which are used to determine its  //
	// top-left corner (x1,y1) and its bottom-right corner (x2,y2) meant for      //
	// efficient calculation. The combination of these coordinates will be used   //
	// for measuring width, height and center points of the quadrants.            //
	////////////////////////////////////////////////////////////////////////////////
	public class Quadnode {

		int x1, y1, x2, y2, cntr_x, cntr_y;
		int tree_Depth;
		Quadnode nw, ne, sw, se;
		LinkedList<AABB> aabbs;

		/////////////////////////////////////////////////////////////////////////////
		// Constructor to create a default Quadtree node. Like an AABB it is made  //
		// up of 4 coordinate points to determine its top left and bottom right    //
		// corners. The center point (cntr_x, cntr_y) is also calculated when this //
		// constructor is called. Quadnodes contain an (initially empty) list of   //
		// objects that intersect its boundaries.                                  //
		/////////////////////////////////////////////////////////////////////////////
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

		////////////////////////////////////////////////////////////////////////////////
		// Iterate through this Quadnodes's AABB list and check to see where the      //
		// given AABB lies in the Quadnode's four sub-quadnodes and add it to that    //
		// sub-quadnode. After haveing passed through the AABB list, recursively call //
		// this method against this quadnodes's sub-quadnodes that contain at least   //
		// 2 items within its AABB list and continue this process until either the    //
		// maximum tree depth has been reached or recursive calls do not present a    //
		// situation which requires further searching. If the maximum tree depth is   //
		// reached, all AABBs within the quadnode that reached the maximum tree depth //
		// are considered to be within close proximity to each other.                 //
		////////////////////////////////////////////////////////////////////////////////
		public void insert() {
			if (tree_Depth < max_Tree_Depth) {
				if (aabbs.size() > 1) {
					for (AABB aabb : aabbs) {
						if (aabb.y1 < cntr_y) {
							if (aabb.x1 < cntr_x) {
								if (nw != null) {
									nw.aabbs.add(aabb);
								}
							}
							if (aabb.x2 > cntr_x) {
								if (ne != null) {
									ne.aabbs.add(aabb);
								}
							}
						}
						if (aabb.y2 > cntr_y) {
							if (aabb.x1 < cntr_x) {
								if (sw != null) {
									sw.aabbs.add(aabb);
								}
							}
							if (aabb.x2 > cntr_x) {
								if (se != null) {
									se.aabbs.add(aabb);
								}
							}
						}
					}
					nw.insert();
					ne.insert();
					sw.insert();
					se.insert();
				}
			} else {
				for (AABB aabb : aabbs) {
					set_Nearby(this.aabbs, aabb);
				}
			}
		}
	}
	
	

	///////////////////////////////////////////////////////////////////////////////////
	// Constructor for the Quadtree. Build the Quadtree from the top down by calling //
	// "build_Quadtree" to begin the process. If square is "true", before building   //
	// the Quadnodes, size the root node so that it is square (both width and height //
	// will be the lrgest of the window's width and height). If the root Quadnode is //
	// not square, none of the Quadnodes will be square and ghost collisions may     //
	// occur more frequently on the axis where Quadnodes are wider (or taller). A    //
	// square quadtree in a rectangular screen or window means part of the Quadtree  //
	// is off screen but performance should remain the same.                         //
	///////////////////////////////////////////////////////////////////////////////////
	public Quadtree(AABB[] aabb_Array, int x, int y, int wdth, int hght, int mtd, boolean square) {
		all_AABBs = aabb_Array;
		max_Tree_Depth = mtd;
		
		if(square)
			wdth = hght = wdth > hght ? wdth - 1 : hght - 1;
		
		root = build_Quadtree(x, y, wdth, hght, 1);
		root.aabbs.addAll(Arrays.asList(aabb_Array));
	}

	///////////////////////////////////////////////////////////////////////////////////
	// Recursively iterate down the Quadtree and clear all AABB lists within the     //
	// Quadnodes.                                                                    //
	///////////////////////////////////////////////////////////////////////////////////
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
	 * Every AABB in the given aabb list will set their "nearby" AABB to "target". *
	 ******************************************************************************/
	private void set_Nearby(LinkedList<AABB> aabb_List, AABB target){						
		for(AABB aabb: aabb_List)
			aabb.set_Nearby(target);

		///////////////////////////////////////////////////////////////////////
		// At this point we can go to phase TWO of collision detection...    //
		///////////////////////////////////////////////////////////////////////
	}

///////////////////////////////////////////////////////////////////////////////////
// Recursively create and return the Quadnodes until the Quadtree reaches the    //
// maxium allowed tree depth defined by "max_Tree_Depth". The Quadtree will be   //
// completed with empty AABB lists and ready to be used for fast recursive       //
// compartmentalization of AABBs provided to the Quadtree.                       //
///////////////////////////////////////////////////////////////////////////////////
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

	////////////////////////////////////////////////////////////////////////////////
	// Set the maximum tree depth to "mtd". The quadtree must be rebuilt if the   //
	// tree depth is changed.                                                     //
	////////////////////////////////////////////////////////////////////////////////
	public void set_Max_Tree_Depth(int mtd) {
		if (max_Tree_Depth != mtd) {
			max_Tree_Depth = (mtd < 1 || mtd > 10) ? max_Tree_Depth : mtd;
			root = build_Quadtree(root.x1, root.y1, root.x2, root.y2, 1);
			root.aabbs.addAll(Arrays.asList(all_AABBs));
		}
	}

	////////////////////////////////////////////////////////////////////////////////
	// Update the Quadtree by emptying the leaf Quadnodes' AABB lists and then    //
	// inserting the AABBs one after another into the Quadtree. All AABB          //
	// "nearby_AABBs" lists cleared until it is determined again that there are   //
	// AABBs nearby as determined by the "insert" method.                         //
	////////////////////////////////////////////////////////////////////////////////
	public void update() {
		reset_Quadnodes(root);
		root.aabbs.addAll(Arrays.asList(all_AABBs));
		for (AABB aabb : all_AABBs) {
			aabb.nearby = null;
		}
		root.insert();
	}
}
