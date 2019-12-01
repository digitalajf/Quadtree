
/**
 * This class is used to test the quadtree. It contains the drawing canvas and is
 * responsible for animating and rendering the scene along with the quadtree. The
 * main method is here as well.
 *
 * @author Arash J. Farmand
 * @version 3.02
 * @date 2019-12-01
 * @since 2019-11-24
 */
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.Random;

public class Tester extends JPanel {

	// window components
	JFrame frame;
	JPanel menu_Bar;
	JPanel menu_Bar2;
	JButton about_Button;
	JButton plus_TD_Button;
	JButton minus_TD_Button;
	JButton plus_5_AABB_Button;
	JButton plus_20_AABB_Button;
	JButton minus_AABB_Button;
	JButton remove_AABBs_Button;
	JButton toggle_Square_Button;
	JButton grow_AABBs_Button;
	JButton shrink_AABBs_Button;

	int hght;
	int wdth;
	int x;
	int y;
	int menu_Bar_Height;
	short number_Of_AABBs;
	short max_Tree_Depth;
	long screen_Refresh;
	static boolean PAUSED;
	boolean square_Quadtree;
	Color quadtree_Color;
	Color aabb_Color;
	Color aabb_Nearby_Color;

	AABB[] aabbs;
	Quadtree quadtree;

	public Tester() {

		////////////////////////////////////////////////////////////////////////////////
		// Set up the width and height of this drawing canvas as well as the          //
		// location of its top left corner so that the drawing canvas will be         //
		// centered on the screen. A menu bar with some functionality to test the     //
		// Quadtree will be made here and the frame will be built around both the     //
		// canvas and the menu bar. We will set up an array of "AABB" objects to test //
		// the Quadtree's behaviour with respect to these AABBs. A maximum tree depth //
		// for the Quadtree, number of AABBs on screen, the game loop speed (and      //
		// refresh rate) are also configurable here.                                  //
		////////////////////////////////////////////////////////////////////////////////
		
		hght = (int) (Toolkit.getDefaultToolkit().getScreenSize().height * 0.8);
		wdth = (int) (Toolkit.getDefaultToolkit().getScreenSize().width * 0.7);
		x = ((int)(Toolkit.getDefaultToolkit().getScreenSize().width - wdth) / 2) - 70;
		y = ((int) (Toolkit.getDefaultToolkit().getScreenSize().height - hght) / 2);
		number_Of_AABBs = 20;
		max_Tree_Depth = 7;
		screen_Refresh = 1000 / 60;
		PAUSED = false;
		square_Quadtree = false;
		quadtree_Color = new Color(0.5f, 0.5f, 0.5f);
		aabb_Color = new Color(new Random().nextInt(50), 110 + new Random().nextInt(60), 110 + new Random().nextInt(60), 220);
		aabb_Nearby_Color = new Color(255, 0, 0, 220);

		// here we set up the array of AABBs with random values for each AABB.
		aabbs = new AABB[number_Of_AABBs];
		for (int i = 0; i < aabbs.length; i++) {
			aabbs[i] = new AABB();
			aabbs[i].set_Size(20 + new Random().nextInt(hght / 20), 20 + new Random().nextInt(hght / 20));
			aabbs[i].set_Velocity(3 - new Random().nextFloat() * 6, 3 - new Random().nextFloat() * 6);
			aabbs[i].relocate(1 + new Random().nextInt(wdth - (int) aabbs[i].x2 - (int) aabbs[i].x1 - 2), 1 + new Random().nextInt(hght - (int) aabbs[i].y2 - (int) aabbs[i].y1 - 2));
		}

		/////////////////////////////////////////////////////////////////////////////
		// Default variables for the simulation are above                          //
		/////////////////////////////////////////////////////////////////////////////
		
		// set the color and size of this JPanel for drawing
		setLayout(null);
		setBackground(Color.DARK_GRAY);
		setPreferredSize(new Dimension(wdth, hght));

		// the menubar with a set of tools to test the quadtree
		menu_Bar = new JPanel();
		menu_Bar.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		menu_Bar.setLayout(null);
		menu_Bar.setPreferredSize(new Dimension(100, 36));
		menu_Bar2 = new JPanel();
		menu_Bar2.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		menu_Bar2.setLayout(null);
		menu_Bar2.setPreferredSize(new Dimension(140, 100));

		// create the frame and add this JPanel to it. A number of buttons with
		// various functionality will be created to test the capabilities and
		// efficiency of the quadtree.
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocation(x, y-60);
		frame.setLayout(new BorderLayout());
		frame.add(BorderLayout.EAST, menu_Bar2);
		frame.add(BorderLayout.WEST, this);
			frame.add(BorderLayout.NORTH, menu_Bar);
		frame.addKeyListener(
				  new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					PAUSED = !PAUSED;
					about_Button.setEnabled(!PAUSED);
					plus_TD_Button.setEnabled(!PAUSED);
					minus_TD_Button.setEnabled(!PAUSED);
					plus_5_AABB_Button.setEnabled(!PAUSED);
					plus_20_AABB_Button.setEnabled(!PAUSED);
					minus_AABB_Button.setEnabled(!PAUSED);
					remove_AABBs_Button.setEnabled(!PAUSED);
					toggle_Square_Button.setEnabled(!PAUSED);
					grow_AABBs_Button.setEnabled(!PAUSED);
					shrink_AABBs_Button.setEnabled(!PAUSED);
				}
			}
		}
		);
		frame.pack();

		about_Button = new JButton("About");
		about_Button.setBackground(Color.WHITE);
		about_Button.addActionListener((ActionEvent e) -> {
			String about_String = "By Arash J. Farmand 2019"
					  + "\n\nsource files:\nAABB.java   -   Quadtree.java   -   Main.java"
					  + "\n\nThis simulator will test all functionality of both Quadtree.java and AABB.java. You may"
					  + "\npause the simulator at any time with <Space>. You may increase or decreasr the tree "
					  + "\ndepth. The higher the tree depth, the closer AABBs must be to each other in order to "
					  + "\ninvoke a positive proximity test result. The number of AABBs can be increased and "
					  + "\ndecreased or removed altogether. "
					  + "\n\nThe default shape of the Quadtree is rectangular and fills the entire coordinate space "
					  + "\nhowever you may choose to \"square\" the Quadtree as well. A square Quadtree will ensure "
					  + "\nall Quadnodes are also square which in turn will ensure that a positive proximity test "
					  + "\nwill always have the same probability whether AABBs are approaching each other from the "
					  + "\ntop\\bottom or left\\right. Rectangular Quadnodes on the other hand will result in more "
					  + "\nfrequent positive proximity results on the axis where Quadnodes have longer edges. A"
					  + "\nsquare Quadtree will have part of it extending beyond the coordinate space but there is "
					  + "\nno performance cost for this."
					  + "\n\nCompiled with JDK 13.0.1";
			JOptionPane.showMessageDialog(null, about_String, "Quadtree and AABB Simulation",
					  JOptionPane.INFORMATION_MESSAGE);
		});
		about_Button.setSize(124, 40);
		about_Button.setLocation(8, 8);
		about_Button.setFocusable(false);
		menu_Bar2.add(about_Button);
		
		toggle_Square_Button = new JButton("<html>Square<br/>Quadtree</html>");
		toggle_Square_Button.setBackground(Color.LIGHT_GRAY);
		toggle_Square_Button.addActionListener((ActionEvent e) -> {
			toggle_Square_Quadtree();
			update_Frame_Title();
		});
		toggle_Square_Button.setSize(124, 80);
		toggle_Square_Button.setLocation(about_Button.getX(), about_Button.getY()+about_Button.getHeight()+8);
		toggle_Square_Button.setFocusable(false);
		menu_Bar2.add(toggle_Square_Button);

		plus_TD_Button = new JButton("tree depth (+1)");
		plus_TD_Button.setBackground(Color.PINK);
		plus_TD_Button.addActionListener((ActionEvent e) -> {
			if (max_Tree_Depth < 10) {
				max_Tree_Depth++;
				quadtree.set_Max_Tree_Depth(max_Tree_Depth);
				update_Frame_Title();
			}
		});
		plus_TD_Button.setSize(120, 30);
		plus_TD_Button.setLocation(2, 2);
		plus_TD_Button.setFocusable(false);
		menu_Bar.add(plus_TD_Button);

		minus_TD_Button = new JButton("tree depth (-1)");
		minus_TD_Button.setBackground(Color.PINK);
		minus_TD_Button.addActionListener((ActionEvent e) -> {
			if (max_Tree_Depth > 1) {
				max_Tree_Depth--;
				quadtree.set_Max_Tree_Depth(max_Tree_Depth);
				update_Frame_Title();
			}
		});
		minus_TD_Button.setSize(120, 30);
		minus_TD_Button.setLocation(plus_TD_Button.getX() + plus_TD_Button.getWidth() + 2, 2);
		minus_TD_Button.setFocusable(false);
		menu_Bar.add(minus_TD_Button);

		plus_5_AABB_Button = new JButton("AABB (+5)");
		plus_5_AABB_Button.setBackground(Color.ORANGE);
		plus_5_AABB_Button.addActionListener((ActionEvent e) -> {
			add_AABB(5);
			update_Frame_Title();
		});
		plus_5_AABB_Button.setSize(100, 30);
		plus_5_AABB_Button.setLocation(minus_TD_Button.getX() + minus_TD_Button.getWidth() + 2, 2);
		plus_5_AABB_Button.setFocusable(false);
		menu_Bar.add(plus_5_AABB_Button);

		plus_20_AABB_Button = new JButton("AABB (+20)");
		plus_20_AABB_Button.setBackground(Color.ORANGE);
		plus_20_AABB_Button.addActionListener((ActionEvent e) -> {
			add_AABB(20);
			update_Frame_Title();
		});
		plus_20_AABB_Button.setSize(100, 30);
		plus_20_AABB_Button.setLocation(plus_5_AABB_Button.getX() + plus_5_AABB_Button.getWidth() + 2, 2);
		plus_20_AABB_Button.setFocusable(false);
		menu_Bar.add(plus_20_AABB_Button);

		minus_AABB_Button = new JButton("AABB (-1)");
		minus_AABB_Button.setBackground(Color.ORANGE);
		minus_AABB_Button.addActionListener((ActionEvent e) -> {
			remove_AABB();
			update_Frame_Title();
		});
		minus_AABB_Button.setSize(100, 30);
		minus_AABB_Button.setLocation(plus_20_AABB_Button.getX() + plus_20_AABB_Button.getWidth() + 2, 2);
		minus_AABB_Button.setFocusable(false);
		menu_Bar.add(minus_AABB_Button);

		remove_AABBs_Button = new JButton("no AABBs");
		remove_AABBs_Button.setBackground(Color.ORANGE);
		remove_AABBs_Button.addActionListener((ActionEvent e) -> {
			remove_All_AABBs();
			update_Frame_Title();
		});
		remove_AABBs_Button.setSize(100, 30);
		remove_AABBs_Button.setLocation(minus_AABB_Button.getX() + minus_AABB_Button.getWidth() + 2, 2);
		remove_AABBs_Button.setFocusable(false);
		menu_Bar.add(remove_AABBs_Button);
		
		grow_AABBs_Button = new JButton("grow AABBs");
		grow_AABBs_Button.setBackground(new Color(140,190,190));
		grow_AABBs_Button.addActionListener((ActionEvent e) -> {
			grow_AABBs(1.2f);
			update_Frame_Title();
		});
		grow_AABBs_Button.setSize(120, 30);
		grow_AABBs_Button.setLocation(remove_AABBs_Button.getX() + remove_AABBs_Button.getWidth() + 2, 2);
		grow_AABBs_Button.setFocusable(false);
		menu_Bar.add(grow_AABBs_Button);
		
		shrink_AABBs_Button = new JButton("shrink AABBs");
		shrink_AABBs_Button.setBackground(new Color(140,190,190));
		shrink_AABBs_Button.addActionListener((ActionEvent e) -> {
			grow_AABBs(0.8f);
			update_Frame_Title();
		});
		shrink_AABBs_Button.setSize(120, 30);
		shrink_AABBs_Button.setLocation(grow_AABBs_Button.getX() + grow_AABBs_Button.getWidth() + 2, 2);
		shrink_AABBs_Button.setFocusable(false);
		menu_Bar.add(shrink_AABBs_Button);

		// Initialize the Quadtree using this JPanel for size measurements
		quadtree = new Quadtree(aabbs, 0, 0, wdth, hght, max_Tree_Depth, square_Quadtree);

		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
		}

		update_Frame_Title();
		frame.setVisible(true);
		update_Scene();
	}

	/*******************************************************************************
	 * Create and add a new randomly generated AABB to the simulation.             *
	 ******************************************************************************/
	private void add_AABB(int n) {
		number_Of_AABBs += n;
		AABB[] temp_AABBs = new AABB[number_Of_AABBs];

		System.arraycopy(aabbs, 0, temp_AABBs, 0, aabbs.length);
		for (int i = n; i > 0; i--) {
			AABB new_AABB = new AABB();
			//new_AABB.set_Color((int) (new Random().nextInt(255) * 0.3f), (int) (85 + new Random().nextInt(255) * 0.5f), (int) (85 + new Random().nextInt(255) * 0.5f), 200);
			new_AABB.set_Size(20 + new Random().nextInt(hght / 20), 20 + new Random().nextInt(hght / 20));
			new_AABB.set_Velocity(3 - new Random().nextFloat() * 6, 3 - new Random().nextFloat() * 6);
			new_AABB.relocate(1 + new Random().nextInt(wdth - (int) new_AABB.x2 - (int) new_AABB.x1 - 2), 1 + new Random().nextInt(hght - (int) new_AABB.y2 - (int) new_AABB.y1 - 2));

			temp_AABBs[number_Of_AABBs - i] = new_AABB;
		}

		aabbs = temp_AABBs;
		quadtree.all_AABBs = aabbs;
		quadtree.set_Max_Tree_Depth(max_Tree_Depth);
	}
	
	/*******************************************************************************
	 * Increase the width and height of all AABBs by a factor "s".                 *
	 ******************************************************************************/
	private void grow_AABBs(float s){
		for (AABB aabb : aabbs){
			aabb.set_Size(aabb.wdth*s, aabb.hght*s);
		}
	}

	/*******************************************************************************
	 * Paint the quadtree and all AABBs based on the current location of the       *
	 * AABBs on the screen along with any messages that may be present.            *
	 ******************************************************************************/
	public void paint(Graphics g) {
		super.paint(g);
		paint_Quadtree(g);
		paint_AABBs(g);
		paint_Messages(g);
	}

	/*******************************************************************************
	 * Paint the quadtree and all AABBs based on the current location of the       *
	 * AABBs on the screen.                                                        *
	 * ****************************************************************************/
	private void paint_AABBs(Graphics g) {
		for (AABB aabb : aabbs) {
			if (aabb.nearby == null) {
				g.setColor(aabb_Color);
			} else {
				g.setColor(aabb_Nearby_Color);
			}
			g.fillRect((int) aabb.x1, (int) aabb.y1, (int) aabb.wdth, (int) aabb.hght);
		}
	}
	
	/*******************************************************************************
	 * Paint any required messages in the middle of the screen.                    *
	 ******************************************************************************/
	private void paint_Messages(Graphics g){
		if(PAUSED){
			g.setColor(new Color(0,0,0,127));
			g.fillRect(wdth/2-206, hght/2-24, 410, 60);
			g.setColor(Color.WHITE);
			g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 18));
			g.drawString("- Paused -", wdth/2-54, hght/2);
			g.drawString("Press <Space> to continue simulation", wdth/2-200, hght/2+20);
		}
	}

	/*******************************************************************************
	 * Paint each Quadnode that contains at least 1 object.                        *
	 ******************************************************************************/
	private void paint_Quadnode(Graphics g, Quadtree.Quadnode node) {
		if (node != null) {
			if (!node.aabbs.isEmpty()) {
				g.setColor(quadtree_Color);
				g.drawRect(node.x1, node.y1, node.x2 - node.x1, node.y2 - node.y1);
			}

			paint_Quadnode(g, node.nw);
			paint_Quadnode(g, node.ne);
			paint_Quadnode(g, node.sw);
			paint_Quadnode(g, node.se);
		}
	}

	/*******************************************************************************
	 * Paint the Quadtree by telling each node within its hierarchy to paint       *
	 * itself to the given graphics object.                                        *
	 * ****************************************************************************/
	private void paint_Quadtree(Graphics g) {
		paint_Quadnode(g, quadtree.root);
	}

	/*******************************************************************************
	 * Pause the simulation.                                                       *
	 * ****************************************************************************/
	public static void PAUSE() {
		PAUSED = true;
	}

	/*******************************************************************************
	 * This method will remove the last AABB in the "aabbs" array from the         *
	 * simulation.                                                                 *
	 ******************************************************************************/
	private void remove_AABB() {
		if (number_Of_AABBs > 0) {
			AABB[] temp_AABBs = new AABB[--number_Of_AABBs];

			System.arraycopy(aabbs, 0, temp_AABBs, 0, aabbs.length - 1);
			aabbs = temp_AABBs;
			quadtree.all_AABBs = aabbs;
		}
	}

	/*******************************************************************************
	 * This method will remove the last AABB in the aabbs array from the           *
	 * simulation.                                                                 *
	 ******************************************************************************/
	private void remove_All_AABBs() {
		number_Of_AABBs = 0;
		aabbs = new AABB[0];
		quadtree.all_AABBs = aabbs;
	}

	/*******************************************************************************
	 * Resume the simulation.                                                      *
	 * ****************************************************************************/
	public static void RESUME() {
		PAUSED = false;
	}
	
	/*******************************************************************************
	 * Toggle between whether this simulation should have a square or rectangular  *
	 * Quadtree. A rectangular Quadtree will fit the window component it lies in   *
	 * whereas a square Quadtree will have a portion of the bottom extending       *
	 * beyond the bottom of the window component. The Quadtree is rebuilt after    *
	 * this change.                                                                *
	 * ****************************************************************************/
	public void toggle_Square_Quadtree(){
		square_Quadtree = !square_Quadtree;
		if(square_Quadtree)
			toggle_Square_Button.setText("<html>Rectangulate<br/>Quadtree</html>");
		else
			toggle_Square_Button.setText("<html>Square<br/>Quadtree</html>");
		quadtree = new Quadtree(aabbs, 0, 0, wdth, hght, max_Tree_Depth, square_Quadtree);
	}
	
	/*******************************************************************************
	 * Update the information in the title of the frame based on the current       *
	 * state of the Quadtree and AABBs.                                            *
	 ******************************************************************************/
	private void update_Frame_Title(){
		String shape = square_Quadtree ? "Square" : "Rectangular";	
		frame.setTitle("Quadtree / AABB Simulation"
					  + "       |       Tree Depth: " + max_Tree_Depth
					  + "       |       AABBs on screen: " + aabbs.length
					  + "       |       Quadtree shape: " + shape
		           + "       |");
	}

	/*******************************************************************************
	 * Continuously loop rendering the screen until exit. This method will update  *
	 * the square's locations, update the Quadtree and refresh the screen by       *
	 * calling "repaint()".                                                        *
	 ******************************************************************************/
	private void update_Scene() {
		while (true) {
			if (!PAUSED) {
				long time_Start = System.currentTimeMillis();
				update_AABB_locations();
				quadtree.update();
				long time_Elapsed = System.currentTimeMillis() - time_Start;

				try {
					if (time_Elapsed < screen_Refresh) {
						Thread.sleep(screen_Refresh - time_Elapsed);
					}
				} catch (InterruptedException e) {
				}
			}
			repaint();
		}
	}

	/*******************************************************************************
	 * Iterate through the AABBs array and tell them all to update their           *
	 * locations. This method will move the AABBs within the bounds defined by     *
	 * wdth and hght. If the AABB is moving out of bounds, reverse dx or dy as     *
	 * needed so that the AABB travales away from the boundaries.                  *
	 * ****************************************************************************/
	private void update_AABB_locations() {
		for (int i = 0; i < aabbs.length; i++) {
			if (aabbs[i].x2 + aabbs[i].dx >= wdth || aabbs[i].x1 + aabbs[i].dx <= 0) {
				aabbs[i].dx = -aabbs[i].dx;
			}
			if (aabbs[i].y2 + aabbs[i].dy >= hght || aabbs[i].y1 + aabbs[i].dy <= 0) {
				aabbs[i].dy = -aabbs[i].dy;
			}
			aabbs[i].relocate(aabbs[i].dx, aabbs[i].dy);
		}
	}

	/*******************************************************************************
	 * main method                                                                 *
	 ******************************************************************************/
	public static void main(String[] args) {
		new Tester();
	}
}
