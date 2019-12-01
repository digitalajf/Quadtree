///////////////////////////////////////////////////////////////////////////////
// This class contains the frame and is responsible for animating and        //
// rendering the scene along with the Quadtree. The main method is here as   //
// well.                                                                     //
///////////////////////////////////////////////////////////////////////////////

/**
 *
 * @author Arash J. Farmand
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.Random;

public class Main extends JPanel {

	//////////////////////////////////////////////////////////////////////
	// Set up the width and height of the frame as well as the location //
	// of its top left corner so that the frame will be centered on the //
	// screen. We will also set up an array of "Sprite" objects to test //
	// the quadtree's behaviour with respect to these sprites.          //
	//////////////////////////////////////////////////////////////////////
	JFrame frame;
	int hght = (int) (Toolkit.getDefaultToolkit().getScreenSize().height * 0.8);
	int wdth = (int) (Toolkit.getDefaultToolkit().getScreenSize().width * 0.7);
	//int wdth = hght;
	int frame_x = (int) (Toolkit.getDefaultToolkit().getScreenSize().width / 2 - wdth / 2);
	int frame_y = (int) (Toolkit.getDefaultToolkit().getScreenSize().height / 2 - hght / 2);
	// a few "Sprites".
	short number_Of_sprites = 12;
	Sprite[] sprites;
	Quadtree quadtree;
	static boolean PAUSE = false;
	long screen_Refresh = 1000 / 60;

	public Main() {
		setLayout(null);
		setBackground(Color.DARK_GRAY);
		setPreferredSize(new Dimension(wdth, hght));

		// create the frame and add this JPanel to it
		frame = new JFrame("Quadtree Example");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocation(frame_x, frame_y);
		frame.setContentPane(this);
		frame.pack();
		frame.setVisible(true);

		/////////////////////////////////////////////////////////////////////////
		// Create the array of sprites and initialize them with random sizes,  //
		// locations, colors and velocities.                                   //
		/////////////////////////////////////////////////////////////////////////
		sprites = new Sprite[number_Of_sprites];
		for (int i = 0; i < sprites.length; i++) {
			sprites[i] = new Sprite();
			sprites[i].set_Color(new Random().nextFloat() * 0.3f, 0.3f + new Random().nextFloat() * 0.5f, 0.3f + new Random().nextFloat() * 0.5f);
			sprites[i].set_Size(40 + new Random().nextInt(getWidth() / 40), 40 + new Random().nextInt(getHeight() / 40));
			sprites[i].set_Velocity(new Random().nextInt(10) - 5, new Random().nextInt(10) - 5);
			sprites[i].relocate(1 + new Random().nextInt(wdth - 52), 1 + new Random().nextInt(hght - 52));
		}

		// Initiazlize the Quadtree
		quadtree = new Quadtree();

		// this will begin the looping process for this simulation
		update_Scene();
	}

	//////////////////////////////////////////////////////////////////////
	// Continuously loop rendering the screen until exit. This method   //
	// will update the square's locations, update the Quadtree and      //
	// refresh the screen by calling "repaint()".                       //
	//////////////////////////////////////////////////////////////////////
	private void update_Scene() {
		while (!PAUSE) {
			long time_Start = System.currentTimeMillis();

			update_square_locations();
			quadtree.rebuild(this, sprites);
			repaint();

			long time_Elapsed = System.currentTimeMillis() - time_Start;
			try {
				if (time_Elapsed < screen_Refresh) {
					Thread.sleep(screen_Refresh - time_Elapsed);
				}
			} catch (InterruptedException e) {
			}
		}
	}

	////////////////////////////////////////////////////////////////////////////
	// Iterate through the sprites array and tell them all to update their    //
	// locations. This method will move the sprites within the bounds defined //
	// by wdth and hght. If the sprite is moving out of bounds, reverse dx    //
	// or dy as needed so that the sprite travales away from the boundaries.  //
	////////////////////////////////////////////////////////////////////////////
	private void update_square_locations() {
		for (int i = 0; i < sprites.length; i++) {
			if (sprites[i].x2 >= wdth || sprites[i].x1 <= 0) {
				sprites[i].dx = -sprites[i].dx;
			}
			if (sprites[i].y2 >= hght || sprites[i].y1 <= 0) {
				sprites[i].dy = -sprites[i].dy;
			}
			sprites[i].relocate(sprites[i].dx, sprites[i].dy);
		}
	}

	//////////////////////////////////////////////////////////////////////
	// Paint the quadtree and all sprites based on the current location //
	// of the sprites on the screen.                                    //
	//////////////////////////////////////////////////////////////////////
	public void paint(Graphics g) {
		super.paint(g);
		for (int i = 0; i < sprites.length; i++) {
			sprites[i].paint(g);
		}
		quadtree.paint(g);
	}

	// main method
	public static void main(String[] args) {
		new Main();
	}
}
