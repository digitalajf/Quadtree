
/**
 * <p>A simple class to represent the bounding box which encapsulates a 2D game
 * Sprite. An AABB (Axis-Aligned Bounding Box) is a simple rectangular box
 * containing 4 coordinates to determine its location, and dimensions. They are
 * used for fast minimum and maximum tests against other AABBs, and other
 * quad-based data structures</p> 
 * 
 * @author Arash J. Farmand
 * @version 2.35
 * @date    2019-12-04
 * @since   2019-11-24
 */
public class AABB {

	////////////////////////////////////////////////////////////////////////////////
	// The AABB is made up of 2 points which determine its top left (x1,y1) and   //
	// bottom right corner (x2,y2). Default posistion will be 0,0 and default size// 
	// using this model will be width=50, height=50, "nearby" will mark this AABB //
	// as being within close proximity to another AABB.                           //
	////////////////////////////////////////////////////////////////////////////////
	float x1, y1;
	float x2, y2;
	float wdth, hght;
	float dx, dy;
	AABB nearby;
	
	/*******************************************************************************
	 * <p>Constructor for AABB class with some default values</p>
	 ******************************************************************************/
	public AABB(){
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
	
   /*******************************************************************************
	 * <p>Move this AABB by delta_x, delta_y</p> 
	 *
	 * @param delta_x The delta value for which to increment the "x" position of
	 * this AABB
	 * @param delta_y The delta value for which to increment the "y" position of
	 * this AABB
	 ******************************************************************************/
	public void relocate(float delta_x, float delta_y) {
		x1 += delta_x;
		y1 += delta_y;
		x2 += delta_x;
		y2 += delta_y;
	}
	
	/*******************************************************************************
	 * <p>Toggle whether this AABB is near other AABBs</p>
	 *
	 * @param nrby The AABB to set as this AABB's nearby object
	 ******************************************************************************/
	public void set_Nearby(AABB nrby){
		nearby = nrby;
	}
	
	/*******************************************************************************
	 * <p>Set the size by changing x2,y2 to be w,h distance from x1,y1</p>
	 * 
	 * @param w The width value wanted for this AABB
	 * @param h The height value wanted for this AABB
	 ******************************************************************************/
	public void set_Size(float w, float h){
		x2 = x1 + w;
		y2 = y1 + h;
		wdth = x2 - x1;
		hght = y2 - y1;
	}
	
	/*******************************************************************************
	 * <p>change the direction and speed of this AABB</p>
	 *
	 * @param delta_x The x value for movement speed and direction
	 * @param delta_y The y value for movement speed and direction
	 ******************************************************************************/
	public void set_Velocity(float delta_x, float delta_y){
		dx = delta_x;
		dy = delta_y;
	}
}