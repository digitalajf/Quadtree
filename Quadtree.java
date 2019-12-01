///////////////////////////////////////////////////////////////////////////////
// This is the Quadtree class.                                               //
///////////////////////////////////////////////////////////////////////////////

/**
 *
 * @author Arash J. Farmand
 */
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import java.util.Vector;

public class Quadtree {

	Quadnode root;
	int max_Tree_Depth = 6;

	////////////////////////////////////////////////////////////////////////////
	// Quadnode class. It contains 4 coordinates which are used to determine  //
	// its top-left corner (x1,y1) and its bottom-right corner (x2,y2) meant  //
	// for efficient calculation. The combination of these coordinates will   //
	// be used for measuring width and height of the Quadnode also.           //
	////////////////////////////////////////////////////////////////////////////
	public class Quadnode {

		// location and dimensions
		int x1, y1, x2, y2, cntr_x, cntr_y;
		// a vector the holds only sprites within its bounds
		Vector<Sprite> sprites;
		// number of sprites in this Quadnode. default is 0.
		int tree_Depth;
		String label;
		Quadnode nw, ne, sw, se;

		// constructor to create a default Quadtree node
		public Quadnode(int _x1, int _y1, int _x2, int _y2, int td) {
			x1 = _x1;
			y1 = _y1;
			x2 = _x2;
			y2 = _y2;
			cntr_x = (x1 + x2) / 2;
			cntr_y = (y1 + y2) / 2;
			sprites = new Vector<Sprite>();
			tree_Depth = td;
			nw = ne = sw = se = null;
		}

		// add a sprite to be monitored by this Quadnode.
		public void add_Sprite(Sprite sq) {
			if (!sprites.contains(sq)) {
				sprites.addElement(sq);
			}
		}

		/////////////////////////////////////////////////////////////////////////////
		// Iterate down the Quadtree searching for at least 2 Sprites that         //
		// intersect any of the four zones (NW, SW, NE, SE) of this Quadnode. If   //
		// an intersection is discovered, create a new sub-Quadnode at that area   //
		// and add all Sprites within this area to the Quadnode for monitoring.    //
		// This method will recursively subdivide quadrants into exactly four new  //
		// child Quadnodes as necessary only. The subdivision process will stop    //
		// once the current Quadnode has reached the maximum tree depth as defined //
		// by "max_Tree_Depth". At this depth within the Quadtree, Sprites are     //
		// considered to be close enough to be marked for potential collision.     //
		/////////////////////////////////////////////////////////////////////////////
		private void subdivide() {
			if (tree_Depth < max_Tree_Depth) {
				if (sprites.size() > 1) {
					for (int i = 0; i < sprites.size(); i++) {
						Sprite sprite = sprites.elementAt(i);
						if (sprite.x1 <= cntr_x && sprite.y1 <= cntr_y) {
							nw = nw == null ? new Quadnode(x1, y1, cntr_x, cntr_y, tree_Depth + 1) : nw;
							nw.add_Sprite(sprite);
							nw.subdivide();
						}
						if (sprite.x1 <= cntr_x && sprite.y2 >= cntr_y) {
							sw = sw == null ? new Quadnode(x1, cntr_y, cntr_x, y2, tree_Depth + 1) : sw;
							sw.add_Sprite(sprite);
							sw.subdivide();
						}
						if (sprite.x2 >= cntr_x && sprite.y1 <= cntr_y) {
							ne = ne == null ? new Quadnode(cntr_x, y1, x2, cntr_y, tree_Depth + 1) : ne;
							ne.add_Sprite(sprite);
							ne.subdivide();
						}
						if (sprite.x2 >= cntr_x && sprite.y2 >= cntr_y) {
							se = se == null ? new Quadnode(cntr_x, cntr_y, x2, y2, tree_Depth + 1) : se;
							se.add_Sprite(sprite);
							se.subdivide();
						}
					}
				}
			} else {
				///////////////////////////////////////////////////////////////////////
				// The maximum tree depth has been reached which means this node's   //
				// Sprites are in proximity to each other.                           //
				///////////////////////////////////////////////////////////////////////
				for (int i = 0; i < sprites.size(); i++) {
					sprites.elementAt(i).set_Nearby(true);
				}
				//Main.PAUSE = true;
			}
		}

		// paint this Quadnode and tell child nodes to paint themselves
		public void paint(Graphics g) {
			g.drawRect(x1, y1, x2 - x1, y2 - y1);
			if (nw != null) {
				nw.paint(g);
			}
			if (sw != null) {
				sw.paint(g);
			}
			if (ne != null) {
				ne.paint(g);
			}
			if (se != null) {
				se.paint(g);
			}
		}
	}

	// paint this quadtree by telling all nodes to paint themselves
	public void paint(Graphics g) {
		g.setColor(new Color(0.6f, 0.6f, 0.6f));
		root.paint(g);
	}

	private void test(Quadnode node) {
		if (node != null) {
			System.out.println(node.sprites.size());
			if (node.nw != null) {
				test(node.nw);
			}
			if (node.sw != null) {
				test(node.sw);
			}
			if (node.ne != null) {
				test(node.ne);
			}
			if (node.se != null) {
				test(node.se);
			}
			if (node.nw == null && node.ne == null && node.sw == null && node.se == null) {
				System.out.println("");
			}
		}
	}

	/////////////////////////////////////////////////////////////////////////////
	// Rebuild the Quadtree from the top down by first creating the root       //
	// Quadnode and then resizing it so that it is square. If the root         //
	// Quadnode is not square, ghost collisions may occur more frequently on   //
	// the the axis where Quadnodes are wider (or taller). The sprites are     //
	// then added to the root Quadnode and the process of subdividing the      //
	// Quadtree begins from here.                                              //
	/////////////////////////////////////////////////////////////////////////////
	public void rebuild(JPanel cs, Sprite[] all_Objects) {
		int max_w = cs.getWidth() > cs.getHeight() ? cs.getWidth() : cs.getHeight();
		int max_h = cs.getHeight() > cs.getWidth() ? cs.getHeight() : cs.getWidth();
		root = new Quadnode(cs.getX(), cs.getY(), max_w - 1, max_h, 0);

		for (int i = 0; i < all_Objects.length; i++) {
			all_Objects[i].set_Nearby(false);
			root.add_Sprite(all_Objects[i]);
		}
		root.subdivide();
		//test(root);
		//Main.PAUSE = true;
	}
}
