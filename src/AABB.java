
/**********************************************************************************
 * An AABB (Axis-Aligned Bounding Box) is a simple rectangular box
 * containing two coordinates (top left corner and bottom right corner to
 * determine its location, and dimensions.&nbsp;"Axis-Aligned" means that the
 * edges determining its width and height are always parallel to the
 * coordinate space in which they lie in.
 * 
 * @author Arash J. Farmand
 * @version 2.42
 * @since   2020-06-12
 *********************************************************************************/
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
	 * Constructor for AABB class which will set the top left coordinates to 
	 * 0,0 and bottom right coordinates to 50,50.&nbsp;This means the width and height
	 * will be set to 50,50 respectively.
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
	 * Constructor for AABB class with parameters to set location and size.
	 * 
	 * @param _x1 The top left 'x' coordinate of this AABB.
	 * @param _y1 The top left 'y' coordinate of this AABB.
	 * @param _x2 The bottom right 'x' coordinate of this AABB.
	 * @param _y2 The bottom right 'y' coordinate of this AABB.
	 ******************************************************************************/
	public AABB(int _x1, int _y1, int _x2, int _y2){
		x1 = _x1;
		y1 = _y1;
		x2 = _x2;
		y2 = _y2;
		dx = 0;
		dy = 0;
		wdth = x2-x1;
		hght = y2-y1;
		nearby = null;
	}
	
	/*******************************************************************************
	 * Determine whether or not this AABB intersects with another AABB.
	 *
	 * @param aabb The AABB against which to check for intersection with this AABB.
	 * @return true if this AABB intersects the given aabb otherwise return
	 * false.
	 ******************************************************************************/
	public boolean collides_With(AABB aabb){
		if(x1 > aabb.x2 || aabb.x1 > x2)
		  return(false);
	
		if(y2 < aabb.y1 || aabb.y2 < y1)
			return(false);

		return(true);
	}
	
   /*******************************************************************************
	 * Move this AABB by delta_x, delta_y.
	 *
	 * @param delta_x The delta value for which to increment the "x" position of
	 * this AABB.
	 * @param delta_y The delta value for which to increment the "y" position of
	 * this AABB.
	 ******************************************************************************/
	public void relocate(float delta_x, float delta_y) {
		x1 += delta_x;
		y1 += delta_y;
		x2 += delta_x;
		y2 += delta_y;
	}
	
	/*******************************************************************************
	 * Toggle whether this AABB is near other AABBs.
	 *
	 * @param nrby The AABB to set as this AABB's nearby object.
	 ******************************************************************************/
	public void set_Nearby(AABB nrby){
		nearby = nrby;
	}
	
	/*******************************************************************************
	 * Set the size by changing x2,y2 to be w,h distance from x1,y1.
	 * 
	 * @param w The width value wanted for this AABB.
	 * @param h The height value wanted for this AABB.
	 ******************************************************************************/
	public void set_Size(float w, float h){
		x2 = x1 + w;
		y2 = y1 + h;
		wdth = x2 - x1;
		hght = y2 - y1;
	}
	
	/*******************************************************************************
	 * change the direction and speed of this AABB.
	 *
	 * @param delta_x The x value for movement speed and direction.
	 * @param delta_y The y value for movement speed and direction.
	 ******************************************************************************/
	public void set_Velocity(float delta_x, float delta_y){
		dx = delta_x;
		dy = delta_y;
	}
}