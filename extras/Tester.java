
/**
 * <p>This class is used to test the quadtree. It contains the drawing canvas and is
 * responsible for animating and rendering the scene along with the quadtree.
 *
 * @author Arash J. Farmand
 * @version 3.40
 * @date 2019-12-18
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
	JPanel side_Bar;
	JButton license_Bttn;
	JButton about_Bttn;
	JButton plus_TD_Bttn;
	JButton minus_TD_Bttn;
	JButton plus_5_Bttn;
	JButton plus_20_Bttn;
	JButton plus_100_Bttn;
	JButton minus_AABB_Bttn;
	JButton remove_AABBs_Bttn;
	JButton toggle_Square_Bttn;
	JButton grow_AABBs_Bttn;
	JButton shrink_AABBs_Bttn;

	int hght;
	int wdth;
	int x;
	int y;
	int menu_Bar_Height;
	int number_Of_AABBs;
	int max_Tree_Depth;
	long screen_Refresh;
	static boolean PAUSED;
	boolean square_Quadtree;
	Image img_Buffer;
	Graphics g2D;
	Color quadtree_Color;
	Color common_Quadnode_Color;
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
		x = ((int) (Toolkit.getDefaultToolkit().getScreenSize().width - wdth) / 2) - 70;
		y = ((int) (Toolkit.getDefaultToolkit().getScreenSize().height - hght) / 2);
		number_Of_AABBs = 20;
		max_Tree_Depth = 6;
		screen_Refresh = 1000 / 60;
		PAUSED = false;
		square_Quadtree = false;
		quadtree_Color = new Color(0.5f, 0.5f, 0.5f);
		common_Quadnode_Color = new Color(0.17f, 0.17f, 0.17f);
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

		// the side bar with a set of tools to test the quadtree
		side_Bar = new JPanel();
		side_Bar.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		side_Bar.setLayout(null);
		side_Bar.setPreferredSize(new Dimension(160, 100));

		// create the frame and add this JPanel to it. A number of buttons with
		// various functionality will be created to test the capabilities and
		// efficiency of the quadtree.
		frame = new JFrame(get_Graphics_Configuration());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocation(x, y - 60);
		frame.setLayout(new BorderLayout());
		frame.add(BorderLayout.WEST, this);
		frame.add(BorderLayout.EAST, side_Bar);
		frame.addKeyListener(
				  new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					PAUSED = !PAUSED;
					plus_TD_Bttn.setEnabled(!PAUSED);
					minus_TD_Bttn.setEnabled(!PAUSED);
					plus_5_Bttn.setEnabled(!PAUSED);
					plus_20_Bttn.setEnabled(!PAUSED);
					plus_100_Bttn.setEnabled(!PAUSED);
					minus_AABB_Bttn.setEnabled(!PAUSED);
					remove_AABBs_Bttn.setEnabled(!PAUSED);
					toggle_Square_Bttn.setEnabled(!PAUSED);
					grow_AABBs_Bttn.setEnabled(!PAUSED);
					shrink_AABBs_Bttn.setEnabled(!PAUSED);
				}
			}
		}
		);
		frame.pack();

		license_Bttn = new JButton("License");
		license_Bttn.setBackground(Color.WHITE);
		license_Bttn.addActionListener((ActionEvent e) -> {
			String about_String = "The MIT License (MIT)"
					  + "\n\nCopyright 2019 Arash J. Farmand"
					  + "\n\nPermission is hereby granted, free of charge, to any person obtaining a copy of"
					  + "\nthis software and associated documentation files (Quadtree.java, AABB.java and"
					  + "\nTester.java), to deal in the Software without restriction, including without"
					  + "\nlimitation the rights to use, copy, modify, merge, publish, distribute,"
					  + "\nsublicense, and/or sell copies of the Software, and to permit persons to whom"
					  + "\nthe Software is furnished to do so, subject to the following conditions:"
					  + "\n\nThe above copyright notice and this permission notice shall be included in all"
					  + "\ncopies or substantial portions of the Software."
					  + "\n\nTHE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR"
					  + "\nIMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS"
					  + "\nFOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR"
					  + "\nCOPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER"
					  + "\nIN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN"
					  + "\nCONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.";
			JOptionPane.showMessageDialog(null, about_String, "Quadtree and AABB Simulation",
					  JOptionPane.INFORMATION_MESSAGE);
		});
		license_Bttn.setSize(144, 40);
		license_Bttn.setLocation(8, 8);
		license_Bttn.setFocusable(false);
		side_Bar.add(license_Bttn);

		about_Bttn = new JButton("About");
		about_Bttn.setBackground(Color.WHITE);
		about_Bttn.addActionListener((ActionEvent e) -> {
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
		about_Bttn.setSize(144, 40);
		about_Bttn.setLocation(8, license_Bttn.getY() + license_Bttn.getHeight()+ 4);
		about_Bttn.setFocusable(false);
		side_Bar.add(about_Bttn);	
		
		JSeparator sep1 = new JSeparator();
		sep1.setSize(160, 5);
		sep1.setLocation(0, about_Bttn.getY()+about_Bttn.getHeight()+14);
		side_Bar.add(sep1);

		toggle_Square_Bttn = new JButton("<html>Square<br/>Quadtree</html>");
		toggle_Square_Bttn.setBackground(Color.LIGHT_GRAY);
		toggle_Square_Bttn.addActionListener((ActionEvent e) -> {
			toggle_Square_Quadtree();
			update_Frame_Title();
		});
		toggle_Square_Bttn.setSize(144, 80);
		toggle_Square_Bttn.setLocation(about_Bttn.getX(), about_Bttn.getY() + about_Bttn.getHeight() + 30);
		toggle_Square_Bttn.setFocusable(false);
		side_Bar.add(toggle_Square_Bttn);

		plus_TD_Bttn = new JButton("<html><center>Tree Depth<br/>+1</center></html>");
		plus_TD_Bttn.setBackground(Color.PINK);
		plus_TD_Bttn.addActionListener((ActionEvent e) -> {
			if (max_Tree_Depth < 10) {
				max_Tree_Depth++;
				quadtree.set_Max_Tree_Depth(max_Tree_Depth);
				update_Frame_Title();
			}
		});
		plus_TD_Bttn.setSize(70, 60);
		plus_TD_Bttn.setLocation(8, toggle_Square_Bttn.getY()+toggle_Square_Bttn.getHeight()+4);
		plus_TD_Bttn.setFocusable(false);
		side_Bar.add(plus_TD_Bttn);

		minus_TD_Bttn = new JButton("<html><center>Tree Depth<br/>-1</center></html>");
		minus_TD_Bttn.setBackground(Color.PINK);
		minus_TD_Bttn.addActionListener((ActionEvent e) -> {
			if (max_Tree_Depth > 1) {
				max_Tree_Depth--;
				quadtree.set_Max_Tree_Depth(max_Tree_Depth);
				update_Frame_Title();
			}
		});
		minus_TD_Bttn.setSize(70, 60);
		minus_TD_Bttn.setLocation(plus_TD_Bttn.getX()+plus_TD_Bttn.getWidth()+4, plus_TD_Bttn.getY());
		minus_TD_Bttn.setFocusable(false);
		side_Bar.add(minus_TD_Bttn);
		
		JSeparator sep2 = new JSeparator();
		sep2.setSize(160, 5);
		sep2.setLocation(0, minus_TD_Bttn.getY()+minus_TD_Bttn.getHeight()+14);
		side_Bar.add(sep2);

		plus_5_Bttn = new JButton("AABB (+5)");
		plus_5_Bttn.setBackground(Color.ORANGE);
		plus_5_Bttn.addActionListener((ActionEvent e) -> {
			add_AABBs(5);
			update_Frame_Title();
		});
		plus_5_Bttn.setSize(144, 30);
		plus_5_Bttn.setLocation(8, minus_TD_Bttn.getY()+minus_TD_Bttn.getHeight()+30);
		plus_5_Bttn.setFocusable(false);
		side_Bar.add(plus_5_Bttn);

		plus_20_Bttn = new JButton("AABB (+20)");
		plus_20_Bttn.setBackground(Color.ORANGE);
		plus_20_Bttn.addActionListener((ActionEvent e) -> {
			add_AABBs(20);
			update_Frame_Title();
		});
		plus_20_Bttn.setSize(144, 30);
		plus_20_Bttn.setLocation(8, plus_5_Bttn.getY()+plus_5_Bttn.getHeight()+4);
		plus_20_Bttn.setFocusable(false);
		side_Bar.add(plus_20_Bttn);
		
		plus_100_Bttn = new JButton("AABB (+100)");
		plus_100_Bttn.setBackground(Color.ORANGE);
		plus_100_Bttn.addActionListener((ActionEvent e) -> {
			add_AABBs(100);
			update_Frame_Title();
		});
		plus_100_Bttn.setSize(144, 30);
		plus_100_Bttn.setLocation(8, plus_20_Bttn.getY()+plus_20_Bttn.getHeight()+4);
		plus_100_Bttn.setFocusable(false);
		side_Bar.add(plus_100_Bttn);

		minus_AABB_Bttn = new JButton("AABB (-1)");
		minus_AABB_Bttn.setBackground(Color.ORANGE);
		minus_AABB_Bttn.addActionListener((ActionEvent e) -> {
			remove_AABB();
			update_Frame_Title();
		});
		minus_AABB_Bttn.setSize(144, 30);
		minus_AABB_Bttn.setLocation(8, plus_100_Bttn.getY()+plus_100_Bttn.getHeight()+4);
		minus_AABB_Bttn.setFocusable(false);
		side_Bar.add(minus_AABB_Bttn);

		remove_AABBs_Bttn = new JButton("Remove AABBs");
		remove_AABBs_Bttn.setBackground(Color.ORANGE);
		remove_AABBs_Bttn.addActionListener((ActionEvent e) -> {
			remove_All_AABBs();
			update_Frame_Title();
		});
		remove_AABBs_Bttn.setSize(144, 30);
		remove_AABBs_Bttn.setLocation(8, minus_AABB_Bttn.getY()+minus_AABB_Bttn.getHeight()+4);
		remove_AABBs_Bttn.setFocusable(false);
		side_Bar.add(remove_AABBs_Bttn);
		
		JSeparator sep3 = new JSeparator();
		sep3.setSize(160, 5);
		sep3.setLocation(0, remove_AABBs_Bttn.getY()+remove_AABBs_Bttn.getHeight()+14);
		side_Bar.add(sep3);

		grow_AABBs_Bttn = new JButton("<html><center>Grow<br/>AABBs</center></html>");
		grow_AABBs_Bttn.setBackground(new Color(140, 190, 190));
		grow_AABBs_Bttn.addActionListener((ActionEvent e) -> {
			grow_AABBs(1.2f);
			update_Frame_Title();
		});
		grow_AABBs_Bttn.setSize(70, 60);
		grow_AABBs_Bttn.setLocation(8, remove_AABBs_Bttn.getY()+remove_AABBs_Bttn.getHeight()+30);
		grow_AABBs_Bttn.setFocusable(false);
		side_Bar.add(grow_AABBs_Bttn);

		shrink_AABBs_Bttn = new JButton("<html><center>Shrink<br/>AABBs</center></html>");
		shrink_AABBs_Bttn.setBackground(new Color(140, 190, 190));
		shrink_AABBs_Bttn.addActionListener((ActionEvent e) -> {
			grow_AABBs(0.8f);
			update_Frame_Title();
		});
		shrink_AABBs_Bttn.setSize(70, 60);
		shrink_AABBs_Bttn.setLocation(grow_AABBs_Bttn.getX() + grow_AABBs_Bttn.getWidth() + 4, grow_AABBs_Bttn.getY());
		shrink_AABBs_Bttn.setFocusable(false);
		side_Bar.add(shrink_AABBs_Bttn);

		// Initialize the Quadtree using this JPanel for size measurements
		quadtree = new Quadtree(aabbs, wdth, hght, max_Tree_Depth, square_Quadtree);

		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
		}

		//System.setProperty("sun.java2d.transaccel", "True");
		//System.setProperty("sun.java2d.trace", "timestamp,log,count");
		//System.setProperty("sun.java2d.opengl", "True");
		//System.setProperty("sun.java2d.d3d", "True");
		//System.setProperty("sun.java2d.ddforcevram", "True");
		update_Frame_Title();
		frame.setVisible(true);
		update_Scene();
	}

	/*******************************************************************************
	 * <p>Create and add "n" new randomly generated AABBs to the simulation.</p>
	 *
	 * @param n The number of new AABBs to add to the simulation
	 ******************************************************************************/
	private void add_AABBs(int n) {
		number_Of_AABBs += n;
		AABB[] temp_AABBs = new AABB[number_Of_AABBs];

		System.arraycopy(aabbs, 0, temp_AABBs, 0, aabbs.length);
		for (int i = n; i > 0; i--) {
			AABB new_AABB = new AABB();
			new_AABB.set_Size(20 + new Random().nextInt(hght / 20), 20 + new Random().nextInt(hght / 20));
			new_AABB.set_Velocity(3 - new Random().nextFloat() * 6, 3 - new Random().nextFloat() * 6);
			new_AABB.relocate(1 + new Random().nextInt(wdth - (int) new_AABB.x2 - (int) new_AABB.x1 - 2), 1 + new Random().nextInt(hght - (int) new_AABB.y2 - (int) new_AABB.y1 - 2));

			temp_AABBs[number_Of_AABBs - i] = new_AABB;
		}

		aabbs = temp_AABBs;
		quadtree.all_AABBs = aabbs;
	}
	
	/*******************************************************************************
	 * <p>Return this monitor's graphics configuration to be used with the 
	 * frame.</p>
	 ******************************************************************************/
	private GraphicsConfiguration get_Graphics_Configuration(){
		Component focusOwner = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
       if (focusOwner != null) {
           Window w = SwingUtilities.getWindowAncestor(focusOwner);
           if (w != null) {
               return w.getGraphicsConfiguration();
           } else {
               for(Frame f : Frame.getFrames()) {
                   if("NbMainWindow".equals(f.getName())) {
                       return f.getGraphicsConfiguration();
                   }
               }
           }
       }

       return(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration());
	}

	/*******************************************************************************
	 * <p>Increase the width and height of all AABBs by a factor "s".</p>
	 *
	 * @param s A scalar value for which to grow AABBs
	 ******************************************************************************/
	private void grow_AABBs(float s) {
		for (AABB aabb : aabbs) {
			aabb.set_Size(aabb.wdth * s, aabb.hght * s);
		}
	}

	/*******************************************************************************
	 * <p>Paint the quadtree and all AABBs based on the current location of the
	 * AABBs on the screen along with any messages that may be present.</p>
	 *
	 * @param g The graphics object to paint to
	 ******************************************************************************/
	public void paint(Graphics g) {
		if (img_Buffer == null)
			img_Buffer = createImage(wdth, hght);
		else{
			g2D = img_Buffer.getGraphics();
			super.paint(g2D);
			paint_Quadtree(g2D);
			paint_AABBs(g2D);
			paint_Messages(g2D);
			//g2D.dispose();
			
			// copy image buffer to screen
			g.drawImage(img_Buffer, 0, 0, null);
		}
	}

	/*******************************************************************************
	 * <p>Paint the quadtree and all AABBs based on the current location of the
	 * AABBs on the screen.</p>
	 *
	 * @param g The graphics object to paint to
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
	 * <p>Paint any required messages in the middle of the screen.</p>
	 *
	 * @param g The graphics object to paint to
	 ******************************************************************************/
	private void paint_Messages(Graphics g) {
		if (PAUSED) {
			g.setColor(new Color(0, 0, 0, 127));
			g.fillRect(wdth / 2 - 206, hght / 2 - 24, 410, 60);
			g.setColor(Color.WHITE);
			g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 18));
			g.drawString("- Paused -", wdth / 2 - 54, hght / 2);
			g.drawString("Press <Space> to continue simulation", wdth / 2 - 200, hght / 2 + 20);
		}
	}

	/*******************************************************************************
	 * <p>Paint each Quadnode that contains at least 1 object.</p>
	 *
	 * @param g The graphics object to paint to
	 * @param node The node to paint
	 ******************************************************************************/
	private void paint_Quadnode(Graphics g, Quadtree.Quadnode node) {
		if (node.tree_Depth<max_Tree_Depth) {
			if (node.aabbs.size() > 1) {
				if(node.tree_Depth == max_Tree_Depth-1){
					g.setColor(common_Quadnode_Color);
					g.fillRect(node.x1, node.y1, node.x2 - node.x1, node.y2 - node.y1);
				}
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
	 * <p>Paint the Quadtree by telling each node within its hierarchy to paint
	 * itself to the given graphics object.</p>
	 *
	 * @param g The graphics object to paint to
	 ******************************************************************************/
	private void paint_Quadtree(Graphics g) {
		paint_Quadnode(g, quadtree.root);
	}

	/*******************************************************************************
	 * <p>This method will remove the last AABB in the "aabbs" array from the
	 * simulation.</p>
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
	 * <p>This method will remove the last AABB in the aabbs array from the
	 * simulation.</p>
	 ******************************************************************************/
	private void remove_All_AABBs() {
		number_Of_AABBs = 0;
		aabbs = new AABB[0];
		quadtree = new Quadtree(aabbs, wdth, hght, max_Tree_Depth, square_Quadtree);
	}

	/*******************************************************************************
	 * <p>Toggle between whether this simulation should have a square or
	 * rectangular Quadtree. A rectangular Quadtree will fit the window component
	 * it lies in whereas a square Quadtree will have a portion of the bottom
	 * extending beyond the bottom of the window component. The Quadtree is
	 * rebuilt after this change.</p>
	 * ****************************************************************************/
	public void toggle_Square_Quadtree() {
		square_Quadtree = !square_Quadtree;
		if (square_Quadtree) {
			toggle_Square_Bttn.setText("<html>Rectangulate<br/>Quadtree</html>");
		} else {
			toggle_Square_Bttn.setText("<html>Square<br/>Quadtree</html>");
		}
		quadtree.reshape(wdth, hght, square_Quadtree);
	}

	/*******************************************************************************
	 * <p>Update the information in the title of the frame based on the current
	 * state of the Quadtree and AABBs.</p>
	 ******************************************************************************/
	private void update_Frame_Title() {
		String shape = square_Quadtree ? "Square" : "Rectangular";
		frame.setTitle("Quadtree / AABB Simulation"
				  + "       |       Tree Depth: " + max_Tree_Depth
				  + "       |       AABBs on screen: " + aabbs.length
				  + "       |       Quadtree shape: " + shape
				  + "       |");
	}

	/*******************************************************************************
	 * <p>Continuously loop rendering the screen until exit. This method will
	 * update the square's locations, update the Quadtree and refresh the screen
	 * by calling "repaint()". All of this is done roughly 60 times per 
	 * second.</p>
	 ******************************************************************************/
	private void update_Scene() {
		while (true) {
			if(!PAUSED){
				long time_Start = System.currentTimeMillis();
				update_AABB_locations();
				quadtree.update();
				long time_Elapsed = System.currentTimeMillis() - time_Start;

				try {
					if (time_Elapsed < screen_Refresh) {
						Thread.sleep(screen_Refresh - time_Elapsed);
					}
				} catch (InterruptedException e) {}
			}
			repaint();
		}
	}

	/*******************************************************************************
	 * <p>Iterate through the AABBs array and tell them all to update their
	 * locations. This method will move the AABBs within the bounds defined by
	 * wdth and hght. If the AABB is moving out of bounds, reverse dx or dy as
	 * needed so that the AABB travels away from the boundaries.</p>
	 ******************************************************************************/
	private void update_AABB_locations() {
		for (AABB aabb : aabbs) {
			if (aabb.x2 + aabb.dx >= wdth || aabb.x1 + aabb.dx <= 0) {
				aabb.dx = -aabb.dx;
			}
			if (aabb.y2 + aabb.dy >= hght || aabb.y1 + aabb.dy <= 0) {
				aabb.dy = -aabb.dy;
			}
			aabb.relocate(aabb.dx, aabb.dy);
		}
	}

	/*******************************************************************************
	 * <p>main method</p>
	 ******************************************************************************/
	public static void main(String[] args) {
		new Tester();
	}
}
