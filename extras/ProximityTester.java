import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.Random;

/**********************************************************************************
 * This class is used to test the Quadtree.&nbsp;It contains the drawing canvas 
 * and is responsible for animating and rendering the scene along with the 
 * quadtree.&nbsp;There's a number of UI elements to allow testing various functions 
 * of the quadtree.
 *
 * @author Arash J. Farmand
 * @version 3.8
 * @since 2021-10-18
 *********************************************************************************/
public class ProximityTester extends JPanel{
   
   private JFrame frame;
   private MouseListener mouse_Listener;
   private Font uifont;
   private Color color_Gray_0;
   private Color color_Gray_1;
   private Color color_Gray_2;
   private Color color_QN_Outln;
   private Color color_QT_Bkgrnd;
	private Color color_Cmn_Quadnode;
	private Color color_AABB;
	private Color color_AABB_Nrby;
	private JPanel panel_Sidebar;
   private JPanel panel_Info;
   private JPanel panel_Shape;
   private JPanel panel_TD;
   private JPanel panel_AABB_Number;
   private JPanel panel_AABB_Size;
   private JPanel panel_AABB_Velocity;
   private JPanel panel_Color;
   private TitledBorder brdr_Panel_Info;
   private TitledBorder brdr_Panel_Shape;
   private TitledBorder brdr_Panel_TD;
   private TitledBorder brdr_Panel_AABB_Number;
   private TitledBorder brdr_Panel_AABB_Size;
   private TitledBorder brdr_Panel_AABB_Velocity;
   private TitledBorder brdr_Panel_AABB_Color;
	private JButton bttn_Lic;
	private JButton bttn_about;
	private JButton bttn_TD_Plus;
	private JButton bttn_TD_Minus;
	private JButton bttn_Add_5_AABBs;
	private JButton bttn_Add_20_AABBs;
	private JButton bttn_Add_100_AABBs;
	private JButton bttn_Rmv_1_AABBs;
   private JButton bttn_Rmv_5_AABBs;
   private JButton bttn_Rmv_20_AABBs;
   private JButton bttn_Rmv_100_AABBs;
	private JButton bttn_Rmv_AABBs;
   private JButton bttn_Vel_Plus_10;
   private JButton bttn_Vel_Plus_30;
   private JButton bttn_Vel_Minus_10;
   private JButton bttn_Vel_Minus_30;
   private JButton bttn_Vel_Average;
   private JPanel bttn_AABB_Color;
   private JPanel bttn_AABB_Nrby_Color;
   private JPanel bttn_QT_Bkgrnd_Color;
   private JPanel bttn_Cmn_AABB_Color;
   private JPanel bttn_QN_Outln_Color;
   private JLabel lbl_AABB_Color;
   private JLabel lbl_AABB_Nrby_Color;
   private JLabel lbl_QT_Bkgrnd_Color;
   private JLabel lbl_Cmn_AABB_Color;
   private JLabel lbl_QN_Outln_Color;
	private JRadioButton rBttn_Sqr_QT;
   private JRadioButton rBttn_Rct_QT;
   private ButtonGroup bttnGrp_QT_Shape;
	private JButton bttn_Grw_AABBs;
	private JButton bttn_Shrnk_AABBs;
   private Image img_Buffer;
	private Graphics g2D;
   
   private int wdth_Sidebar;
   private int wdth_Panel;
   private int gap_Vert_0;
   private int gap_Vert_1;
   private int gap_Vert_2;
   private int gap_Vert_3;
   private int gap_Vert_5;
   private int gap_Hor_0;
   private int gap_Hor_1;
   private int bttn_1_Wdth;
   private int bttn_2_Wdth;
	private int hght;
	private int wdth;
	private int x;
	private int y;
	private int number_Of_AABBs;
	private int max_Tree_Depth;
	private long screen_Refresh;
	private static boolean PAUSED;
	private boolean square_Quadtree;

	AABB[] aabbs;
	Quadtree quadtree;

	/*******************************************************************************
	 * Set up the width and height of this drawing canvas as well as the
	 * location of its top left corner so that the drawing canvas will be
	 * centered on the screen.&nbsp;A menu bar with some functionality to test the
	 * Quadtree will be made here and the frame will be built around both the
	 * canvas and the menu bar.&nbsp;We will set up an array of "AABB" objects to test
	 * the Quadtree's behavior with respect to these AABBs.&nbsp;A maximum tree depth
	 * for the Quadtree, number of AABBs on screen, the game loop speed (and
	 * refresh rate) are also configurable here.
	 ******************************************************************************/
	public ProximityTester() {
      wdth_Sidebar = 202;
      wdth_Panel = 186;
      hght = (int) (Toolkit.getDefaultToolkit().getScreenSize().height * 0.8);
		wdth = (int) (Toolkit.getDefaultToolkit().getScreenSize().width * 0.7);
		x = ((int) (Toolkit.getDefaultToolkit().getScreenSize().width - wdth - wdth_Sidebar) / 2);
		y = ((int) (Toolkit.getDefaultToolkit().getScreenSize().height - hght) / 2) - 30;
      gap_Vert_0 = 0;
      gap_Vert_1 = 4;
      gap_Vert_2 = 8;
      gap_Vert_3 = 16;
      gap_Vert_5 = 20;
      gap_Hor_0 = 0;
      gap_Hor_1 = 4;
      bttn_1_Wdth = 55;
      bttn_2_Wdth = 83;
      uifont = new Font("", Font.PLAIN, 11);
      color_Gray_0 = Color.LIGHT_GRAY.brighter();
      color_Gray_1 = Color.LIGHT_GRAY;
      color_Gray_2 = Color.DARK_GRAY.brighter();
      color_QN_Outln = new Color(0, 0, 0);
      color_QT_Bkgrnd = new Color(0.35f, 0.35f, 0.35f);
		color_Cmn_Quadnode = new Color(0.27f, 0.27f, 0.27f);
		color_AABB = new Color(new Random().nextInt(50), 110 + new Random().nextInt(60), 110 + new Random().nextInt(60), 220);
		color_AABB_Nrby = new Color(255, 0, 0, 220);
		number_Of_AABBs = 20;
		max_Tree_Depth = 5;
		screen_Refresh = 1000 / 60;
		PAUSED = false;
		square_Quadtree = true;

		init_AABBs();

		/////////////////////////////////////////////////////////////////////////////
		// Default variables for the simulation are above                          //
		/////////////////////////////////////////////////////////////////////////////
		// set the color and size of this JPanel for drawing
		setLayout(null);
		setBackground(color_QT_Bkgrnd);
		setPreferredSize(new Dimension(wdth, hght));
     
		// the side bar with a set of tools to test the quadtree
		panel_Sidebar = new JPanel();
      //panel_Sidebar.setBackground(color_Gray_3);
		panel_Sidebar.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		panel_Sidebar.setLayout(null);
		panel_Sidebar.setPreferredSize(new Dimension(wdth_Sidebar, 100));

      /////////////////////////////////////////////////////////////////////////////
		// create the mouse listener                                               //
      /////////////////////////////////////////////////////////////////////////////
      
      mouse_Listener = new MouseAdapter() {
         @Override
         public void mousePressed(MouseEvent event) {
            process_Mouse_Events(event);
         }

         @Override
         public void mouseReleased(MouseEvent event) {
            process_Mouse_Events(event);
         }

         @Override
         public void mouseEntered(MouseEvent event) {
            process_Mouse_Events(event);
         }

         @Override
         public void mouseExited(MouseEvent event) {
            process_Mouse_Events(event);
         }
      };
      
      /////////////////////////////////////////////////////////////////////////////
		// create the frame and add this JPanel to it. A number of buttons with    //
		// various functionality will be created to test the capabilities and      //
		// efficiency of the quadtree.                                             //
      /////////////////////////////////////////////////////////////////////////////
      
		frame = new JFrame(get_Graphics_Configuration());
      init_Look_And_Feel();  
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLayout(new BorderLayout());
		frame.add(BorderLayout.WEST, this);
		frame.add(BorderLayout.EAST, panel_Sidebar);
		frame.addKeyListener(
         new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
               if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                 toggle_Pause();
               }
            }
         }
		);
      frame.setLocation(x, y);
		frame.pack();
      
      brdr_Panel_Info = new TitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Information", TitledBorder.CENTER, TitledBorder.TOP, uifont, color_Gray_0);
      brdr_Panel_Shape = new TitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Quadtree Shape", TitledBorder.CENTER, TitledBorder.TOP, uifont, color_Gray_0);
      brdr_Panel_TD = new TitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Tree Depth", TitledBorder.CENTER, TitledBorder.TOP, uifont, color_Gray_0);
      brdr_Panel_AABB_Number = new TitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "# of AABBs", TitledBorder.CENTER, TitledBorder.TOP, uifont, color_Gray_0);
      brdr_Panel_AABB_Size = new TitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "AABB Size", TitledBorder.CENTER, TitledBorder.TOP, uifont, color_Gray_0);
      brdr_Panel_AABB_Velocity = new TitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "AABB Velocity", TitledBorder.CENTER, TitledBorder.TOP, uifont, color_Gray_0);
      brdr_Panel_AABB_Color= new TitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Quadtree Colors", TitledBorder.CENTER, TitledBorder.TOP, uifont, color_Gray_0);
      
      panel_Info = new JPanel();
      panel_Info.setSize(wdth_Panel, 81);
      panel_Info.setLocation(8, 8);
      panel_Info.setBorder(brdr_Panel_Info);
      panel_Info.setLayout(null);
      panel_Sidebar.add(panel_Info);

		bttn_Lic = new JButton("License");
      bttn_Lic.setFont(uifont);
		bttn_Lic.addActionListener((ActionEvent e) -> {
			show_License_Dialog();
		});
		bttn_Lic.setSize(panel_Info.getWidth()-20, 25);
		bttn_Lic.setLocation(10, gap_Vert_5);
		bttn_Lic.setFocusable(false);
		panel_Info.add(bttn_Lic);

		bttn_about = new JButton("About");
      bttn_about.setFont(uifont);
		bttn_about.addActionListener((ActionEvent e) -> {
         show_About_Dialog();
		});
		bttn_about.setSize(bttn_Lic.getWidth(), bttn_Lic.getHeight());
		bttn_about.setLocation(bttn_Lic.getX(), bttn_Lic.getY() + bttn_Lic.getHeight() + gap_Vert_0);
		bttn_about.setFocusable(false);
		panel_Info.add(bttn_about);	
            
      panel_Shape = new JPanel();
      panel_Shape.setSize(wdth_Panel, 77);
      panel_Shape.setLocation(panel_Info.getX(), panel_Info.getY()+panel_Info.getHeight() + gap_Vert_1);
      panel_Shape.setBorder(brdr_Panel_Shape);
      panel_Shape.setLayout(null);
      panel_Sidebar.add(panel_Shape);

		rBttn_Sqr_QT = new JRadioButton("Square");
      rBttn_Sqr_QT.setForeground(color_Gray_1);
      rBttn_Sqr_QT.setFont(uifont);
      rBttn_Sqr_QT.setToolTipText("<html>Change the shape of the Quadtree to square.<br><br>A square Quadtree "
      +"\nensures proximity test results<br>are consistent regardless of what direction the<br>AABBs are with "
      +"\nrespect to each other.</html>");
		rBttn_Sqr_QT.addActionListener((ActionEvent e) -> {
         set_Quadtree_To_Square();
		});
		rBttn_Sqr_QT.setSize(bttn_about.getWidth(), 25);
		rBttn_Sqr_QT.setLocation(bttn_Lic.getX(), gap_Vert_5);
		rBttn_Sqr_QT.setFocusable(false);
      rBttn_Sqr_QT.setSelected(true);
		panel_Shape.add(rBttn_Sqr_QT);
      
      rBttn_Rct_QT = new JRadioButton("Rectangular");
      rBttn_Rct_QT.setForeground(color_Gray_1);
       rBttn_Rct_QT.setFont(uifont);
      rBttn_Rct_QT.setToolTipText("<html>Change the shape of the Quadtree to rectangular.<br><br>This is generally "
      +"\nnot recommended if consistent<br>proximity test results are necessary.</html>");
		rBttn_Rct_QT.addActionListener((ActionEvent e) -> {
         set_Quadtree_To_Rectangular();
		});
		rBttn_Rct_QT.setSize(rBttn_Sqr_QT.getWidth(), 25);
		rBttn_Rct_QT.setLocation(rBttn_Sqr_QT.getX(), rBttn_Sqr_QT.getY() + rBttn_Sqr_QT.getHeight() + gap_Vert_0);
		rBttn_Rct_QT.setFocusable(false);
      rBttn_Rct_QT.setSelected(false);
		panel_Shape.add(rBttn_Rct_QT);
      
      bttnGrp_QT_Shape = new ButtonGroup();
      bttnGrp_QT_Shape.add(rBttn_Sqr_QT);
      bttnGrp_QT_Shape.add(rBttn_Rct_QT);

      panel_TD = new JPanel();
      panel_TD.setSize(wdth_Panel, 76);
      panel_TD.setLocation(8, panel_Shape.getY()+panel_Shape.getHeight() + gap_Vert_1);
      panel_TD.setBorder(brdr_Panel_TD);
      panel_TD.setLayout(null);
      panel_Sidebar.add(panel_TD);
      
		bttn_TD_Plus = new JButton("<html><center>Increase</center></html>");
      bttn_TD_Plus.setFont(uifont);
      bttn_TD_Plus.setToolTipText("<html>Increase the tree depth.<br><br>A higher tree depth means AABBs must be closer<br>together "
      +"\nin order to invoke a positive proximity<br>test. This in turn requires more operations by the<br>host system.</html>");
		bttn_TD_Plus.addActionListener((ActionEvent e) -> {
			change_Tree_Depth(1);
		});
		bttn_TD_Plus.setSize(bttn_2_Wdth, 45);
		bttn_TD_Plus.setLocation(bttn_Lic.getX(), gap_Vert_5);
		bttn_TD_Plus.setFocusable(false);
		panel_TD.add(bttn_TD_Plus);

		bttn_TD_Minus = new JButton("<html><center>Decrease</center></html>");
      bttn_TD_Minus.setFont(uifont);
      bttn_TD_Minus.setToolTipText("<html>Decrease the tree depth.<br><br>A lower tree depth means positive proximity tests<br>occur "
      +"\neven when AABBs are farther appart however<br>there are less operations required to achieve this<br>by the "
      +"\nhost system.</html>");
		bttn_TD_Minus.addActionListener((ActionEvent e) -> {
			change_Tree_Depth(-1);
		});
		bttn_TD_Minus.setSize(bttn_2_Wdth, bttn_TD_Plus.getHeight());
		bttn_TD_Minus.setLocation(bttn_TD_Plus.getX()+bttn_TD_Plus.getWidth() + gap_Hor_0, bttn_TD_Plus.getY() + gap_Vert_0);
		bttn_TD_Minus.setFocusable(false);
		panel_TD.add(bttn_TD_Minus);
      
      panel_AABB_Number = new JPanel();
      panel_AABB_Number.setSize(wdth_Panel, 163);
      panel_AABB_Number.setLocation(8, panel_TD.getY()+panel_TD.getHeight() + gap_Vert_1);
      panel_AABB_Number.setBorder(brdr_Panel_AABB_Number);
      panel_AABB_Number.setLayout(null);
      panel_Sidebar.add(panel_AABB_Number);

		bttn_Add_5_AABBs = new JButton("<html>+5</html>");
      bttn_Add_5_AABBs.setFont(uifont);
		bttn_Add_5_AABBs.addActionListener((ActionEvent e) -> {
			add_AABBs(5);
			update_Frame_Title();
		});
		bttn_Add_5_AABBs.setSize(bttn_1_Wdth, 25);
		bttn_Add_5_AABBs.setLocation(bttn_Lic.getX(), gap_Vert_5);
		bttn_Add_5_AABBs.setFocusable(false);
		panel_AABB_Number.add(bttn_Add_5_AABBs);

		bttn_Add_20_AABBs = new JButton("<html>+20</html>");
      bttn_Add_20_AABBs.setFont(uifont);
		bttn_Add_20_AABBs.addActionListener((ActionEvent e) -> {
			add_AABBs(20);
			update_Frame_Title();
		});
		bttn_Add_20_AABBs.setSize(bttn_Add_5_AABBs.getWidth(), bttn_Add_5_AABBs.getHeight());
		bttn_Add_20_AABBs.setLocation(bttn_Add_5_AABBs.getX()+bttn_Add_5_AABBs.getWidth() + gap_Hor_0, bttn_Add_5_AABBs.getY() + gap_Vert_0);
		bttn_Add_20_AABBs.setFocusable(false);
		panel_AABB_Number.add(bttn_Add_20_AABBs);
		
		bttn_Add_100_AABBs = new JButton("<html>+100</html>");
      bttn_Add_100_AABBs.setFont(uifont);
		bttn_Add_100_AABBs.addActionListener((ActionEvent e) -> {
			add_AABBs(100);
			update_Frame_Title();
		});
		bttn_Add_100_AABBs.setSize(bttn_Add_20_AABBs.getWidth(), bttn_Add_20_AABBs.getHeight());
		bttn_Add_100_AABBs.setLocation(bttn_Add_20_AABBs.getX() + bttn_Add_20_AABBs.getWidth() + gap_Hor_0, bttn_Add_20_AABBs.getY() + gap_Vert_0);
		bttn_Add_100_AABBs.setFocusable(false);
		panel_AABB_Number.add(bttn_Add_100_AABBs);
      
      JButton sep1 = new JButton();
      sep1.setEnabled(false);
		sep1.setSize(160, 2);
		sep1.setLocation(gap_Hor_1 + 10, bttn_Add_100_AABBs.getY() + bttn_Add_100_AABBs.getHeight() + gap_Vert_2);
		panel_AABB_Number.add(sep1);

		bttn_Rmv_1_AABBs = new JButton("<html>-1</html>");
      bttn_Rmv_1_AABBs.setFont(uifont);
		bttn_Rmv_1_AABBs.addActionListener((ActionEvent e) -> {
			remove_AABB(1);
			update_Frame_Title();
		});
		bttn_Rmv_1_AABBs.setSize(bttn_Add_100_AABBs.getWidth(), bttn_Add_100_AABBs.getHeight());
		bttn_Rmv_1_AABBs.setLocation(bttn_Add_5_AABBs.getX(), bttn_Add_100_AABBs.getY()+bttn_Add_100_AABBs.getHeight() + gap_Vert_3);
		bttn_Rmv_1_AABBs.setFocusable(false);
		panel_AABB_Number.add(bttn_Rmv_1_AABBs);
      
      bttn_Rmv_5_AABBs = new JButton("<html>-5</html>");
      bttn_Rmv_5_AABBs.setFont(uifont);
		bttn_Rmv_5_AABBs.addActionListener((ActionEvent e) -> {
			remove_AABB(5);
			update_Frame_Title();
		});
		bttn_Rmv_5_AABBs.setSize(bttn_Add_100_AABBs.getWidth(), bttn_Add_100_AABBs.getHeight());
		bttn_Rmv_5_AABBs.setLocation(bttn_Add_20_AABBs.getX() + gap_Hor_0, bttn_Rmv_1_AABBs.getY() + gap_Vert_0);
		bttn_Rmv_5_AABBs.setFocusable(false);
		panel_AABB_Number.add(bttn_Rmv_5_AABBs);
  
      bttn_Rmv_20_AABBs = new JButton("<html>-20</html>");
      bttn_Rmv_20_AABBs.setFont(uifont);
		bttn_Rmv_20_AABBs.addActionListener((ActionEvent e) -> {
			remove_AABB(20);
			update_Frame_Title();
		});
		bttn_Rmv_20_AABBs.setSize(bttn_Rmv_5_AABBs.getWidth(), bttn_Rmv_5_AABBs.getHeight());
		bttn_Rmv_20_AABBs.setLocation(bttn_Rmv_5_AABBs.getX() + bttn_Rmv_5_AABBs.getWidth() + gap_Hor_0, bttn_Rmv_5_AABBs.getY() + gap_Vert_0);
		bttn_Rmv_20_AABBs.setFocusable(false);
		panel_AABB_Number.add(bttn_Rmv_20_AABBs);
      
      bttn_Rmv_100_AABBs = new JButton("<html>-100</html>");
      bttn_Rmv_100_AABBs.setFont(uifont);
		bttn_Rmv_100_AABBs.addActionListener((ActionEvent e) -> {
			remove_AABB(100);
			update_Frame_Title();
		});
		bttn_Rmv_100_AABBs.setSize(bttn_Rmv_20_AABBs.getWidth(), bttn_Rmv_20_AABBs.getHeight());
		bttn_Rmv_100_AABBs.setLocation(bttn_Rmv_1_AABBs.getX(), bttn_Rmv_20_AABBs.getY() + bttn_Rmv_20_AABBs.getHeight() + gap_Vert_0);
		bttn_Rmv_100_AABBs.setFocusable(false);
		panel_AABB_Number.add(bttn_Rmv_100_AABBs);
      
      JButton sep2 = new JButton();
      sep2.setEnabled(false);
		sep2.setSize(160, 2);
		sep2.setLocation(gap_Hor_1 + 10, bttn_Rmv_100_AABBs.getY() + bttn_Rmv_100_AABBs.getHeight() + gap_Vert_2);
		panel_AABB_Number.add(sep2);

		bttn_Rmv_AABBs = new JButton("<html>Remove All</html>");
      bttn_Rmv_AABBs.setFont(uifont);
		bttn_Rmv_AABBs.addActionListener((ActionEvent e) -> {
			remove_All_AABBs();
			update_Frame_Title();
		});
		bttn_Rmv_AABBs.setSize(bttn_about.getWidth(), bttn_Rmv_1_AABBs.getHeight());
		bttn_Rmv_AABBs.setLocation(bttn_Rmv_100_AABBs.getX(), bttn_Rmv_100_AABBs.getY()+bttn_Rmv_100_AABBs.getHeight() + gap_Vert_3);
		bttn_Rmv_AABBs.setFocusable(false);
		panel_AABB_Number.add(bttn_Rmv_AABBs);
      
      panel_AABB_Size = new JPanel();
      panel_AABB_Size.setSize(wdth_Panel, 81);
      panel_AABB_Size.setLocation(panel_AABB_Number.getX(), panel_AABB_Number.getY() + panel_AABB_Number.getHeight() + gap_Vert_1);
      panel_AABB_Size.setBorder(brdr_Panel_AABB_Size);
      panel_AABB_Size.setLayout(null);
      panel_Sidebar.add(panel_AABB_Size);

		bttn_Grw_AABBs = new JButton("<html><center>Increase</center></html>");
      bttn_Grw_AABBs.setFont(uifont);
      bttn_Grw_AABBs.setToolTipText("<html>Grow the AABBs.<br><br>The number of operations by the host system<br>increases "
      +"\nas AABBs grow larger.</html>");
		bttn_Grw_AABBs.addActionListener((ActionEvent e) -> {
			grow_AABBs(5);
			update_Frame_Title();
		});
		bttn_Grw_AABBs.setSize(bttn_Rmv_AABBs.getWidth(), bttn_Rmv_AABBs.getHeight());
		bttn_Grw_AABBs.setLocation(bttn_Rmv_AABBs.getX(), gap_Vert_5);
		bttn_Grw_AABBs.setFocusable(false);
		panel_AABB_Size.add(bttn_Grw_AABBs);

		bttn_Shrnk_AABBs = new JButton("<html><center>Decrease</center></html>");
      bttn_Shrnk_AABBs.setFont(uifont);
      bttn_Shrnk_AABBs.setToolTipText("<html>Shrink the AABBs.<br><br>The number of operations by the host system<br>decreases "
      +"\nas AABBs grow smaller.</html>");
		bttn_Shrnk_AABBs.addActionListener((ActionEvent e) -> {
			grow_AABBs(-5);
			update_Frame_Title();
		});
		bttn_Shrnk_AABBs.setSize(bttn_Grw_AABBs.getWidth(), bttn_Grw_AABBs.getHeight());
		bttn_Shrnk_AABBs.setLocation(bttn_Grw_AABBs.getX(), bttn_Grw_AABBs.getY() + bttn_Grw_AABBs.getHeight()+gap_Vert_0);
		bttn_Shrnk_AABBs.setFocusable(false);
		panel_AABB_Size.add(bttn_Shrnk_AABBs);
      
      panel_AABB_Velocity = new JPanel();
      panel_AABB_Velocity.setSize(wdth_Panel, 106);
      panel_AABB_Velocity.setLocation(panel_AABB_Size.getX(), panel_AABB_Size.getY() + panel_AABB_Size.getHeight() + gap_Vert_1);
      panel_AABB_Velocity.setBorder(brdr_Panel_AABB_Velocity);
      panel_AABB_Velocity.setLayout(null);
      panel_Sidebar.add(panel_AABB_Velocity);
      
      bttn_Vel_Plus_10 = new JButton("<html><center>+10%</center></html>");
      bttn_Vel_Plus_10.setFont(uifont);
      bttn_Vel_Plus_10.setToolTipText("<html>Increase the velocity of all AABBs by 10%.<br><br>The maximum velocity of "
      +"\nAABBs are capped.</html>");
		bttn_Vel_Plus_10.addActionListener((ActionEvent e) -> {
			change_Velocity(1.1d);
		});
		bttn_Vel_Plus_10.setSize(bttn_TD_Plus.getWidth(), bttn_Shrnk_AABBs.getHeight());
		bttn_Vel_Plus_10.setLocation(bttn_Shrnk_AABBs.getX(), gap_Vert_5);
		bttn_Vel_Plus_10.setFocusable(false);
		panel_AABB_Velocity.add(bttn_Vel_Plus_10);
      
      bttn_Vel_Plus_30 = new JButton("<html><center>+30%</center></html>");
      bttn_Vel_Plus_30.setFont(uifont);
      bttn_Vel_Plus_30.setToolTipText("<html>Increase the velocity of all AABBs by 30%.<br><br>The maximum velocity of "
      +"\nAABBs are capped.</html>");
		bttn_Vel_Plus_30.addActionListener((ActionEvent e) -> {
			change_Velocity(1.3d);
		});
		bttn_Vel_Plus_30.setSize(bttn_Vel_Plus_10.getWidth(), bttn_Vel_Plus_10.getHeight());
		bttn_Vel_Plus_30.setLocation(bttn_Vel_Plus_10.getX()+bttn_Vel_Plus_10.getWidth() + gap_Hor_0, bttn_Vel_Plus_10.getY() + gap_Vert_0);
		bttn_Vel_Plus_30.setFocusable(false);
		panel_AABB_Velocity.add(bttn_Vel_Plus_30);  
      
      bttn_Vel_Minus_10 = new JButton("<html><center>-10%</center></html>");
      bttn_Vel_Minus_10.setFont(uifont);
      bttn_Vel_Minus_10.setToolTipText("<html>Decrease the velocity of all AABBs by 10%.<br><br>The minimum velocity of "
      +"\nAABBs are capped.</html>");
		bttn_Vel_Minus_10.addActionListener((ActionEvent e) -> {
			change_Velocity(0.9d);
		});
		bttn_Vel_Minus_10.setSize(bttn_Vel_Plus_30.getWidth(), bttn_Vel_Plus_30.getHeight());
		bttn_Vel_Minus_10.setLocation(bttn_Vel_Plus_10.getX(), bttn_Vel_Plus_30.getY()+ bttn_Vel_Plus_30.getHeight() + gap_Vert_0);
		bttn_Vel_Minus_10.setFocusable(false);
		panel_AABB_Velocity.add(bttn_Vel_Minus_10); 
      
      bttn_Vel_Minus_30 = new JButton("<html><center>-30%</center></html>");
      bttn_Vel_Minus_30.setFont(uifont);
      bttn_Vel_Minus_30.setToolTipText("<html>Decrease the velocity of all AABBs by 30%.<br><br>The minimum velocity of "
      +"\nAABBs are capped.</html>");
		bttn_Vel_Minus_30.addActionListener((ActionEvent e) -> {
			change_Velocity(0.7d);
		});
		bttn_Vel_Minus_30.setSize(bttn_Vel_Minus_10.getWidth(), bttn_Vel_Minus_10.getHeight());
		bttn_Vel_Minus_30.setLocation(bttn_Vel_Plus_30.getX() + gap_Hor_0, bttn_Vel_Minus_10.getY() + gap_Vert_0);
		bttn_Vel_Minus_30.setFocusable(false);
		panel_AABB_Velocity.add(bttn_Vel_Minus_30); 
      
      bttn_Vel_Average = new JButton("<html><center>Average</center></html>");
      bttn_Vel_Average.setFont(uifont);
      bttn_Vel_Average.setToolTipText("<html>Set the velocity of AABBs to that of the average of all AABBs</html>");
		bttn_Vel_Average.addActionListener((ActionEvent e) -> {       
         set_Velocity_To_Average();
		});
		bttn_Vel_Average.setSize(bttn_Shrnk_AABBs.getWidth(), bttn_Shrnk_AABBs.getHeight());
		bttn_Vel_Average.setLocation(bttn_Vel_Minus_10.getX(), bttn_Vel_Minus_10.getY() + bttn_Vel_Minus_10.getHeight()+gap_Vert_0);
		bttn_Vel_Average.setFocusable(false);
		panel_AABB_Velocity.add(bttn_Vel_Average);
      
      panel_Color = new JPanel();
      panel_Color.setSize(wdth_Panel, 175);
      panel_Color.setLocation(panel_AABB_Velocity.getX(), panel_AABB_Velocity.getY() + panel_AABB_Velocity.getHeight() + gap_Vert_1);
      panel_Color.setBorder(brdr_Panel_AABB_Color);
      panel_Color.setLayout(null);
      panel_Sidebar.add(panel_Color);
      
      lbl_AABB_Color = new JLabel("<html><center>AABBs</center></html>");
      lbl_AABB_Color.setFont(uifont);
      lbl_AABB_Color.setSize(144, 30);
		lbl_AABB_Color.setLocation(10, gap_Vert_3);
		lbl_AABB_Color.setFocusable(false);
		panel_Color.add(lbl_AABB_Color);
      
      bttn_AABB_Color = new JPanel();
      bttn_AABB_Color.setName("bttn_AABB_Color");
      bttn_AABB_Color.setFont(uifont);
      bttn_AABB_Color.setToolTipText("<html>Select a new color for AABBs</html>");
      bttn_AABB_Color.setBackground(color_AABB);
      bttn_AABB_Color.setBorder(BorderFactory.createEtchedBorder());
		bttn_AABB_Color.addMouseListener(mouse_Listener);
		bttn_AABB_Color.setSize(20, 20);
		bttn_AABB_Color.setLocation(lbl_AABB_Color.getX() + lbl_AABB_Color.getWidth(), lbl_AABB_Color.getY() + 5);
		bttn_AABB_Color.setFocusable(false);
		panel_Color.add(bttn_AABB_Color);
      
      lbl_AABB_Nrby_Color = new JLabel("<html><center>Nearby AABBs</center></html>");
      lbl_AABB_Nrby_Color.setFont(uifont);
      lbl_AABB_Nrby_Color.setSize(lbl_AABB_Color.getWidth(), 30);
		lbl_AABB_Nrby_Color.setLocation(lbl_AABB_Color.getX(), lbl_AABB_Color.getY() + lbl_AABB_Color.getHeight() + gap_Vert_0);
		lbl_AABB_Nrby_Color.setFocusable(false);
		panel_Color.add(lbl_AABB_Nrby_Color);
      
      bttn_AABB_Nrby_Color = new JPanel();
      bttn_AABB_Nrby_Color.setName("bttn_AABB_Nrby_Color");
      bttn_AABB_Nrby_Color.setFont(uifont);
      bttn_AABB_Nrby_Color.setToolTipText("<html>Select a new color for AABBs that are in close<br>proximity to each other</html>");
      bttn_AABB_Nrby_Color.setBackground(color_AABB_Nrby);
      bttn_AABB_Nrby_Color.setBorder(BorderFactory.createEtchedBorder());
		bttn_AABB_Nrby_Color.addMouseListener(mouse_Listener);
		bttn_AABB_Nrby_Color.setSize(20, 20);
		bttn_AABB_Nrby_Color.setLocation(lbl_AABB_Nrby_Color.getX() + lbl_AABB_Nrby_Color.getWidth(), lbl_AABB_Nrby_Color.getY() + 5);
		bttn_AABB_Nrby_Color.setFocusable(false);
		panel_Color.add(bttn_AABB_Nrby_Color);
      
      lbl_Cmn_AABB_Color = new JLabel("<html><center>Common AABB Quadnode</center></html>");
      lbl_Cmn_AABB_Color.setFont(uifont);
      lbl_Cmn_AABB_Color.setSize(lbl_AABB_Nrby_Color.getWidth(), 30);
		lbl_Cmn_AABB_Color.setLocation(lbl_AABB_Nrby_Color.getX(), lbl_AABB_Nrby_Color.getY() + lbl_AABB_Nrby_Color.getHeight() + gap_Vert_0);
		lbl_Cmn_AABB_Color.setFocusable(false);
		panel_Color.add(lbl_Cmn_AABB_Color);
      
      bttn_Cmn_AABB_Color = new JPanel();
      bttn_Cmn_AABB_Color.setName("bttn_Cmn_AABB_Color");
      bttn_Cmn_AABB_Color.setFont(uifont);
      bttn_Cmn_AABB_Color.setToolTipText("<html>Select a new color for Quadnodes containing<br>AABBs in close proximity to each other</html>");
      bttn_Cmn_AABB_Color.setBackground(color_Cmn_Quadnode);
      bttn_Cmn_AABB_Color.setBorder(BorderFactory.createEtchedBorder());
		bttn_Cmn_AABB_Color.addMouseListener(mouse_Listener);
		bttn_Cmn_AABB_Color.setSize(20, 20);
		bttn_Cmn_AABB_Color.setLocation(lbl_Cmn_AABB_Color.getX() + lbl_Cmn_AABB_Color.getWidth(), lbl_Cmn_AABB_Color.getY() + 5);
		bttn_Cmn_AABB_Color.setFocusable(false);
		panel_Color.add(bttn_Cmn_AABB_Color);
      
      lbl_QN_Outln_Color = new JLabel("<html><center>Quadnode Outline</center></html>");
      lbl_QN_Outln_Color.setFont(uifont);
      lbl_QN_Outln_Color.setSize(lbl_AABB_Nrby_Color.getWidth(), 30);
		lbl_QN_Outln_Color.setLocation(lbl_Cmn_AABB_Color.getX(), lbl_Cmn_AABB_Color.getY() + lbl_Cmn_AABB_Color.getHeight() + gap_Vert_0);
		lbl_QN_Outln_Color.setFocusable(false);
		panel_Color.add(lbl_QN_Outln_Color);
      
      bttn_QN_Outln_Color = new JPanel();
      bttn_QN_Outln_Color.setName("bttn_QN_Outln_Color");
      bttn_QN_Outln_Color.setFont(uifont);
      bttn_QN_Outln_Color.setToolTipText("<html>Select a new color for outline of Quadnodes</html>");
      bttn_QN_Outln_Color.setBackground(color_QN_Outln);
      bttn_QN_Outln_Color.setBorder(BorderFactory.createEtchedBorder());
		bttn_QN_Outln_Color.addMouseListener(mouse_Listener);
		bttn_QN_Outln_Color.setSize(20, 20);
		bttn_QN_Outln_Color.setLocation(lbl_QN_Outln_Color.getX() + lbl_QN_Outln_Color.getWidth(), lbl_QN_Outln_Color.getY() + 5);
		bttn_QN_Outln_Color.setFocusable(false);
		panel_Color.add(bttn_QN_Outln_Color);
      
      lbl_QT_Bkgrnd_Color = new JLabel("<html><center>Quadtree Background</center></html>");
      lbl_QT_Bkgrnd_Color.setFont(uifont);
      lbl_QT_Bkgrnd_Color.setSize(lbl_AABB_Nrby_Color.getWidth(), 30);
		lbl_QT_Bkgrnd_Color.setLocation(lbl_QN_Outln_Color.getX(), lbl_QN_Outln_Color.getY() + lbl_QN_Outln_Color.getHeight() + gap_Vert_0);
		lbl_QT_Bkgrnd_Color.setFocusable(false);
		panel_Color.add(lbl_QT_Bkgrnd_Color);
      
      bttn_QT_Bkgrnd_Color = new JPanel();
      bttn_QT_Bkgrnd_Color.setName("bttn_QT_Bkgrnd_Color");
      bttn_QT_Bkgrnd_Color.setFont(uifont);
      bttn_QT_Bkgrnd_Color.setToolTipText("<html>Select a new color for the background of the<br>Quadtree</html>");
      bttn_QT_Bkgrnd_Color.setBackground(color_QT_Bkgrnd);
      bttn_QT_Bkgrnd_Color.setBorder(BorderFactory.createEtchedBorder());
		bttn_QT_Bkgrnd_Color.addMouseListener(mouse_Listener);
		bttn_QT_Bkgrnd_Color.setSize(20, 20);
		bttn_QT_Bkgrnd_Color.setLocation(lbl_QT_Bkgrnd_Color.getX() + lbl_QT_Bkgrnd_Color.getWidth(), lbl_QT_Bkgrnd_Color.getY() + 5);
		bttn_QT_Bkgrnd_Color.setFocusable(false);
		panel_Color.add(bttn_QT_Bkgrnd_Color);
      
		// Initialize the Quadtree using this JPanel for size measurements
		quadtree = new Quadtree(aabbs, wdth, hght, max_Tree_Depth, square_Quadtree);

		//System.setProperty("sun.java2d.transaccel", "True");
		//System.setProperty("sun.java2d.trace", "timestamp,log,count");
		//System.setProperty("sun.java2d.opengl", "True");
		//System.setProperty("sun.java2d.d3d", "True");
		//System.setProperty("sun.java2d.ddforcevram", "True");
      
      set_Square_Quadtree(square_Quadtree);
		update_Frame_Title();
      SwingUtilities.updateComponentTreeUI(frame);
		frame.setVisible(true);
		update_Scene();
	}

	/*******************************************************************************
	 * Create and add "n" new randomly generated AABBs to the simulation.
	 *
	 * @param n The number of new AABBs to add to the simulation.
	 ******************************************************************************/
	private void add_AABBs(int n) {
		number_Of_AABBs += n;
		AABB[] temp_AABBs = new AABB[number_Of_AABBs];

		System.arraycopy(aabbs, 0, temp_AABBs, 0, aabbs.length);
		for (int i = n; i > 0; i--) {
			AABB new_AABB = new AABB();
			new_AABB.set_Size(20 + new Random().nextInt(hght / 20), 20 + new Random().nextInt(hght / 20));
			new_AABB.set_Velocity(3 - new Random().nextFloat() * 6, 3 - new Random().nextFloat() * 6);
			new_AABB.relocate(1 + new Random().nextInt(wdth - (int) new_AABB.get_X2() - (int) new_AABB.get_X1() - 2), 1 + new Random().nextInt(hght - (int) new_AABB.get_Y2() - (int) new_AABB.get_Y1() - 2));

			temp_AABBs[number_Of_AABBs - i] = new_AABB;
		}

		aabbs = temp_AABBs;
		quadtree.set_All_AABBs(aabbs);
	}
   
   /*******************************************************************************
	 * Change the background color of the given component using a color selection 
    * dialog and return the selected color.
    * 
    * @param comp Component who's color is to be changed
    * @param color Default color selected in color chooser dialog
    * @param title Title of the color chooser dialog
    * @return The color selected by the user
	 ******************************************************************************/
   private Color change_Color(JPanel comp, Color color, String title){
      Color temp_Color = color;
      color = JColorChooser.showDialog(frame, title, color);
      
      if(color == null)
         color = temp_Color;
      
      if(comp != null)
         comp.setBackground(color);
      
      return color;
   }
   
   /*******************************************************************************
	 * Increase or decrease the tree depth by "num". If the tree depth will be
    * larger than 10 or smaller than 1, do nothing and return.
	 *
	 * @param num The number the tree depth should be increased or decreased by
	 ******************************************************************************/
   private void change_Tree_Depth(int num){
      if(max_Tree_Depth + num > 10 || max_Tree_Depth + num < 1)
         return;
      
      toggle_Pause();
	   max_Tree_Depth += num;
	   quadtree.set_Max_Tree_Depth(max_Tree_Depth);
      toggle_Pause();
	   update_Frame_Title();
   }
   
   /*******************************************************************************
	 * Increase or decrease the velocity of all the AABBs by a multiplier defined
    * by vel. In the event that the required velocity for any given AABB would be 
    * greater than 12.0d or less than 0.03d, do not alter the velocity of that 
    * particular AABB.
	 *
	 * @param vel multiplyer used to change the velocity
	 ******************************************************************************/
   private void change_Velocity(double vel){
      for (int i=0; i<aabbs.length; i++) {
         if (Math.abs(aabbs[i].get_dx() * vel) > 0.1d && Math.abs(aabbs[i].get_dy() * vel) > 0.1d) {
            if (Math.abs(aabbs[i].get_dx() * vel) < 12.0d && Math.abs(aabbs[i].get_dy() * vel) < 12.0d) {
               aabbs[i].set_Velocity(aabbs[i].get_dx() * vel, aabbs[i].get_dy() * vel);
            }
         }
      }
   }
	
	/*******************************************************************************
	 * Return this monitor's graphics configuration to be used with the 
	 * frame.
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
	 * Increase the width and height of all AABBs by a factor "s".
	 *
	 * @param s A scalar value for which to grow AABBs.
	 ******************************************************************************/
	private void grow_AABBs(double s) {
		for (int i=0; i<aabbs.length; i++) {
			aabbs[i].set_Size(aabbs[i].get_Width() * s, aabbs[i].get_Height() * s);
		}
	}
   
   /*******************************************************************************
	 * Increase the width and height of all AABBs by "s" in both width and height.
	 *
	 * @param s The amount for which to grow AABBs in both width and height.
	 ******************************************************************************/
   private void grow_AABBs(int s) {
		for (int i=0; i<aabbs.length; i++) {
         if(aabbs[i].get_Width() + s > 2 && aabbs[i].get_Height() + s> 2)
            aabbs[i].set_Size(aabbs[i].get_Width() + s, aabbs[i].get_Height() + s);
		}
	}
   
   /*******************************************************************************
	 * Initialize AABBs with some random values and add them to the AABB array.
	 ******************************************************************************/
   private void init_AABBs(){
		aabbs = new AABB[number_Of_AABBs];
		for (int i = 0; i < aabbs.length; i++) {
			aabbs[i] = new AABB();
			aabbs[i].set_Size(20 + new Random().nextInt(hght / 20), 20 + new Random().nextInt(hght / 20));
			aabbs[i].set_Velocity(3 - new Random().nextFloat() * 6, 3 - new Random().nextFloat() * 6);
			aabbs[i].relocate(1 + new Random().nextInt(wdth - (int) aabbs[i].get_X2() - (int) aabbs[i].get_X1() - 2), 1 + new Random().nextInt(hght - (int) aabbs[i].get_Y2() - (int) aabbs[i].get_Y1() - 2));
		}
   }
   
   /*******************************************************************************
	 * Initialize the look and feel of this frame.
	 ******************************************************************************/
   private void init_Look_And_Feel(){
      try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
         UIManager.put("nimbusBase", Color.BLACK);
         UIManager.put("nimbusBlueGrey", Color.DARK_GRAY);
         UIManager.put("control", Color.DARK_GRAY);
         UIManager.put("info", Color.DARK_GRAY); 
         UIManager.put("textForeground", Color.WHITE);
		} catch (Exception e) {
		}
   }

	/*******************************************************************************
	 * Paint the quadtree and all AABBs based on the current location of the
	 * AABBs on the screen along with any messages that may be present.
	 *
	 * @param g The graphics object to paint to.
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
			g2D.dispose();
			
			// copy image buffer to screen
			g.drawImage(img_Buffer, 0, 0, null);
		}
	}

	/*******************************************************************************
	 * Paint the quadtree and all AABBs based on the current location of the
	 * AABBs on the screen.
	 *
	 * @param g The graphics object to paint to.
	 * ****************************************************************************/
	private void paint_AABBs(Graphics g) {
		for (int i=0; i<aabbs.length; i++) {
			if (aabbs[i].get_Nearby() == null) {
				g.setColor(color_AABB);
			} else {
				g.setColor(color_AABB_Nrby);
			}
			g.fillRect((int) aabbs[i].get_X1(), (int) aabbs[i].get_Y1(), (int) aabbs[i].get_Width(), (int) aabbs[i].get_Height());
		}
	}

	/*******************************************************************************
	 * Paint any required messages in the middle of the screen.
	 *
	 * @param g The graphics object to paint to.
	 ******************************************************************************/
	private void paint_Messages(Graphics g) {
		if (PAUSED) {
			g.setColor(new Color(0, 0, 0, 180));
			g.fillRoundRect(wdth / 2 - 206, hght / 2 - 24, 412, 60, 20,20);
         g.setColor(color_Gray_2);
         g.drawRoundRect(wdth / 2 - 206, hght / 2 - 24, 412, 60, 20,20);
         g.setColor(color_Gray_0);
			g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 18));
			g.drawString("- Paused -", wdth / 2 - 54, hght / 2);
			g.drawString("Press <Space> to continue simulation", wdth / 2 - 200, hght / 2 + 20);
		}
	}

	/*******************************************************************************
	 * Paint each Quadnode that contains at least 1 object.
	 *
	 * @param g The graphics object to paint to.
	 * @param node The node to paint.
	 ******************************************************************************/
	private void paint_Quadnode(Graphics g, Quadnode node) {
		if (node.get_Tree_Depth()<max_Tree_Depth) {
			if (node.get_AABBs().size() > 1) {
				if(node.get_Tree_Depth() == max_Tree_Depth-1){
					g.setColor(color_Cmn_Quadnode);
					g.fillRect(node.get_X1(), node.get_Y1(), node.get_X2() - node.get_X1(), node.get_Y2() - node.get_Y1());
				}
				g.setColor(color_QN_Outln);
				g.drawRect(node.get_X1(), node.get_Y1(), node.get_X2() - node.get_X1(), node.get_Y2() - node.get_Y1());
			}

			paint_Quadnode(g, node.get_NW());
			paint_Quadnode(g, node.get_NE());
			paint_Quadnode(g, node.get_SW());
			paint_Quadnode(g, node.get_SE());
		}
	}

	/*******************************************************************************
	 * Paint the Quadtree by telling each node within its hierarchy to paint
	 * itself to the given graphics object.
	 *
	 * @param g The graphics object to paint to.
	 ******************************************************************************/
	private void paint_Quadtree(Graphics g) {
		paint_Quadnode(g, quadtree.get_Root());
	}
   
   /*******************************************************************************
	 * Pause the the simulation. This method will also disable or enable the UI
    * elements as necessary. The "License" and "About" buttons are not disabled.
	 ******************************************************************************/
   private void toggle_Pause(){
      PAUSED = !PAUSED;
      rBttn_Sqr_QT.setEnabled(!PAUSED);
      rBttn_Rct_QT.setEnabled(!PAUSED);
		bttn_TD_Plus.setEnabled(!PAUSED);
		bttn_TD_Minus.setEnabled(!PAUSED);
		bttn_Add_5_AABBs.setEnabled(!PAUSED);
		bttn_Add_20_AABBs.setEnabled(!PAUSED);
		bttn_Add_100_AABBs.setEnabled(!PAUSED);
		bttn_Rmv_1_AABBs.setEnabled(!PAUSED);
      bttn_Rmv_5_AABBs.setEnabled(!PAUSED);
      bttn_Rmv_20_AABBs.setEnabled(!PAUSED);
      bttn_Rmv_100_AABBs.setEnabled(!PAUSED);
		bttn_Rmv_AABBs.setEnabled(!PAUSED);
		rBttn_Sqr_QT.setEnabled(!PAUSED);
		bttn_Grw_AABBs.setEnabled(!PAUSED);
		bttn_Shrnk_AABBs.setEnabled(!PAUSED);
      bttn_Vel_Plus_10.setEnabled(!PAUSED);
      bttn_Vel_Plus_30.setEnabled(!PAUSED);
      bttn_Vel_Minus_10.setEnabled(!PAUSED);
      bttn_Vel_Minus_30.setEnabled(!PAUSED);
      bttn_Vel_Average.setEnabled(!PAUSED);
      bttn_AABB_Color.setEnabled(!PAUSED);
   }
   
   /*******************************************************************************
	 * Process all mouse events.
    *
    * @param event Mouse Event to be processed
	 ******************************************************************************/
   private void process_Mouse_Events(MouseEvent event) {
      if(event.getComponent().getName().equals("bttn_AABB_Color")) {
         if(event.getID() == MouseEvent.MOUSE_RELEASED)
            color_AABB = change_Color(bttn_AABB_Color, color_AABB, "Select AABB Color");
      }
      else if(event.getComponent().getName().equals("bttn_AABB_Nrby_Color")) {
         if(event.getID() == MouseEvent.MOUSE_RELEASED)
            color_AABB_Nrby = change_Color(bttn_AABB_Nrby_Color, color_AABB_Nrby, "Select Nearby AABB Color");
      }
      else if(event.getComponent().getName().equals("bttn_QT_Bkgrnd_Color")) {
         if(event.getID() == MouseEvent.MOUSE_RELEASED)
            color_QT_Bkgrnd = change_Color(bttn_QT_Bkgrnd_Color, color_QT_Bkgrnd, "Select Quadtree Background Color");
            setBackground(color_QT_Bkgrnd);
      }
      else if(event.getComponent().getName().equals("bttn_QN_Outln_Color")) {
         if(event.getID() == MouseEvent.MOUSE_RELEASED)
            color_QN_Outln = change_Color(bttn_QN_Outln_Color, color_QN_Outln, "Select Quadnode Outline Color");
      }
      else if(event.getComponent().getName().equals("bttn_Cmn_AABB_Color")) {
         if(event.getID() == MouseEvent.MOUSE_RELEASED)
            color_Cmn_Quadnode = change_Color(bttn_Cmn_AABB_Color, color_Cmn_Quadnode, "Select Common AABB Quadnode Color");
      }
   }

	/*******************************************************************************
	 * This method will remove a number of AABBs from the simulation. 
    *
    * @param num The number of AABBs to remove from the simulation
	 ******************************************************************************/
	private void remove_AABB(int num) {
		if (number_Of_AABBs - num >= 0){
         number_Of_AABBs-=num;
			AABB[] temp_AABBs = new AABB[number_Of_AABBs];
			System.arraycopy(aabbs, 0, temp_AABBs, 0, number_Of_AABBs);
			aabbs = temp_AABBs;
			quadtree.set_All_AABBs(aabbs);
		}
	}

	/*******************************************************************************
	 * Remove all AABBs from the AABB array and rebuild the Quadtree.
	 ******************************************************************************/
	private void remove_All_AABBs() {
		number_Of_AABBs = 0;
		aabbs = new AABB[0];
		quadtree = new Quadtree(aabbs, wdth, hght, max_Tree_Depth, square_Quadtree);
	}
   
   /*******************************************************************************
	 * Change the shape of the Quadtree to Rectangular.
	 ******************************************************************************/
   private void set_Quadtree_To_Rectangular(){
      square_Quadtree = false;
		set_Square_Quadtree(square_Quadtree);
		update_Frame_Title();
   }
   
   /*******************************************************************************
	 * Change the shape of the Quadtree to Square.
	 ******************************************************************************/
   private void set_Quadtree_To_Square(){
      square_Quadtree = true;
		set_Square_Quadtree(square_Quadtree);
		update_Frame_Title();
   }

	/*******************************************************************************
	 * Toggle between a square or rectangular Quadtree.&nbsp;A rectangular Quadtree 
	 * will fit the window component it lies in whereas a square Quadtree will have
	 * a portion of the bottom extending beyond the bottom of the window 
	 * component.&nbsp;The Quadtree must be rebuilt after this change thus 
	 * "quadtree.reshape(wdth, hght, square_Quadtree)" is called at the end of this
	 * method.
    * 
    * @param  square whether to set the Quadtree shape to square (true) or not
    * (false).
	 * ****************************************************************************/
	public void set_Square_Quadtree(boolean square) {  
      int quadtree_wdth = wdth;
      int quadtree_hght = hght; 
      
      if(square)
			quadtree_wdth = quadtree_hght = wdth > hght ? wdth - 1 : hght - 1;
		quadtree.reshape(quadtree_wdth, quadtree_hght);
	}
   
   /*******************************************************************************
	 * Set all AABB velocities to the average velocity.
	 ******************************************************************************/
   public void set_Velocity_To_Average(){
      // average dx and dy and hypotenuse for all AABBs
      double avg_dx = 0.0d;
      double avg_dy = 0.0d;
      double avg_c;
         
      for (int i=0; i<aabbs.length; i++) {
         avg_dx += Math.abs(aabbs[i].get_dx());
         avg_dy += Math.abs(aabbs[i].get_dy());
      }

      avg_dx /= (double)number_Of_AABBs;
      avg_dx = Math.round((avg_dx * 100000) / 100000); // rounded to 5 decimal places
      
      avg_dy /= (double)number_Of_AABBs;
      avg_dy = Math.round((avg_dy * 100000) / 100000);
          
      avg_c = Math.hypot(avg_dx,  avg_dy);
      
      for (int i=0; i<aabbs.length; i++) {
         // hypotenuse of current AABB
         double c = Math.hypot(aabbs[i].get_dx(), aabbs[i].get_dy());
         
         // ratio of hypotenuse to average hypotenuse rounded to 5 decimal places
         double rto_dx_to_c = avg_c / c;
         rto_dx_to_c = Math.round((rto_dx_to_c * 100000) / 100000);
         
         // do not adjust velocities if the ratio is very close to "1"
         if(rto_dx_to_c > 1.00001d || rto_dx_to_c < 0.99999d)
            aabbs[i].set_Velocity(aabbs[i].get_dx() * rto_dx_to_c, aabbs[i].get_dy() * rto_dx_to_c);
      }
   }
   
   /*******************************************************************************
	 * Create a pop-up Dialog displaying License Information.
	 ******************************************************************************/
   private void show_About_Dialog(){
      String about_String = "By Arash J. Farmand 2021"
		+ "\n\nsource files:\nAABB.java - Quadtree.java - Quadnode.java - ProximityTester.java "
		+ "\n\nThis simulator will test all functionality of Quadtree.java, Quadnode.java and "
      + "\nAABB.java. You may pause the simulator at any time with <Space>. You may increase "
      + "\nor decreasr the tree depth. The higher the tree depth, the closer AABBs must be "
      + "\nto each other in order to invoke a positive proximity test result. The number of "
      + "\nAABBs can be increased and decreased or removed altogether. "
		+ "\n\nThe default shape of the Quadtree is square and fills the entire coordinate space "
		+ "\nhowever you may choose to \"square\" the Quadtree as well. A square Quadtree will ensure "
		+ "\nall Quadnodes are also square which in turn will ensure that a positive proximity test "
		+ "\nwill always have the same probability whether AABBs are approaching each other vertically"
		+ "\nor horizontally. Rectangular Quadnodes on the other hand will result in more "
		+ "\nfrequent positive proximity results on the axis where Quadnodes have longer edges. A"
		+ "\nsquare Quadtree will have part of it extending beyond the coordinate space but there is "
		+ "\nno performance cost for this."
		+ "\n\nCompiled with JDK 16.0.2";
		JOptionPane.showMessageDialog(null, about_String, "Quadtree Proximity Tester",
      JOptionPane.INFORMATION_MESSAGE);
   }
   
   /*******************************************************************************
	 * Create a pop-up Dialog displaying License Information.
	 ******************************************************************************/
   private void show_License_Dialog(){
      String about_String = "The MIT License (MIT)"
		+ "\n\nCopyright 2021 Arash J. Farmand"
		+ "\n\nPermission is hereby granted, free of charge, to any person obtaining a copy of"
		+ "\nthis software and associated documentation files (AABB.java, Quadtree.java, "
      + "\nQuadnode.java and ProximityTester.java), to deal in the Software without "
      + "\nrestriction, including without limitation the rights to use, copy, modify, merge, "
      + "\npublish, distribute, sublicense, and/or sell copies of the Software, and to "
      + "\npermit persons to whom the Software is furnished to do so, subject to the "
      + "\nfollowing conditions:"
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
   }

	/*******************************************************************************
	 * Update the information in the title of the frame based on the current
	 * state of the Quadtree and AABBs.
	 ******************************************************************************/
	private void update_Frame_Title() {
		String shape = square_Quadtree ? "Square" : "Rectangular";
		frame.setTitle("Proximity Test Simulator ver 3.8"
				  + "       |       Tree Depth: " + max_Tree_Depth
				  + "       |       AABBs on screen: " + aabbs.length
				  + "       |       Quadtree shape: " + shape
				  + "       |");
	}

	/*******************************************************************************
	 * Continuously loop rendering the screen until exit.&nbsp;This method will
	 * update the square's locations, update the Quadtree and refresh the screen
	 * by calling "repaint()".&nbsp;All of this is done roughly 60 times per 
	 * second.
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
	 * Iterate through the AABBs array and tell them all to update their
	 * locations.&nbsp;This method will move the AABBs within the bounds defined by
	 * wdth and hght.&nbsp;If the AABB is moving out of bounds, reverse dx or dy as
	 * needed so that the AABB travels away from the boundaries.
	 ******************************************************************************/
	private void update_AABB_locations() {
		for (int i=0; i<aabbs.length; i++) {
			if (aabbs[i].get_X2() + aabbs[i].get_dx() >= wdth || aabbs[i].get_X1() + aabbs[i].get_dx() <= 0) {
				aabbs[i].set_dx(-aabbs[i].get_dx());
			}
			if (aabbs[i].get_Y2() + aabbs[i].get_dy() >= hght || aabbs[i].get_Y1() + aabbs[i].get_dy() <= 0) {
				aabbs[i].set_dy(-aabbs[i].get_dy());
			}
			aabbs[i].relocate(aabbs[i].get_dx(), aabbs[i].get_dy());
		}
	}

	/*******************************************************************************
	 * main method.
	 ******************************************************************************/
	public static void main(String[] args) {
		new ProximityTester();
	}
}
