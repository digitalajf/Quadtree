
/**
 * A simple class to represent the bounding box which encapsulates a 2D game
 * Sprite. An AABB (Axis-Aligned Bounding Box) is a simple rectangular box
 * containing 4 coordinates to determine its location, and dimensions. They are
 * used for fast minimum and maximum tests against other AABBs, and other
 * quad-based data structures.
 * 
 * @author Arash J. Farmand
 * @version 2.33
 * @date    2019-12-02
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
	
	// Constructor for AABB class with some default values.
	public AABB(){
		x1 = y1 = 0;
		x2 = y2 = 50;
		dx = dy = 0;
		wdth = hght = x2;
		nearby = null;
	}
	
   // move this AABB by delta_x, delta_y
	public void relocate(float delta_x, float delta_y) {
		x1 += delta_x;
		y1 += delta_y;
		x2 += delta_x;
		y2 += delta_y;
	}
	
	// toggle whether this AABB is near other AABBs
	public void set_Nearby(AABB nrby){
		nearby = nrby;
	}
	
	// set the size by changing x2,y2 to be w,h distance from x1,y1
	public void set_Size(float w, float h){
		x2 = x1 + w;
		y2 = y1 + h;
		wdth = x2 - x1;
		hght = y2 - y1;
	}
	
	// change the direction and speed of this AABB
	public void set_Velocity(float delta_x, float delta_y){
		dx = delta_x;
		dy = delta_y;
	}
}
