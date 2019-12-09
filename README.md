# Static Quadtree

#### compiled with JDK 13.0.1

<b>A data structure used for efficient approximation of coordinates on a plain. My version here is static meaning it is completely built from the tip down to the leaves during initialization in order to improve search performance at a tiny cost of extra memory requirements to store all quadnodes. Here are some of the characteristics:</b>
- pre-built Quadtree eliminates need to create new "Quadnode" objects in real-time
- pre-built Quadtree has pre-calculated quadnode centers to eliminate arithmetic during real-time search within tree
- exclusively designed to work with AABBs
- searches within quadrants occur only when 2 or more AABBs intersect the quadrant effectively culling needless extra searching.
- AABBs will reference each other as being "nearby" when discovered to share the same quadnode at maximum tree depth.

#### contains: 
- Quadtree.java
- AABB.java
- Tester.java (windowed simulator for testing - not needed for normal use.)

#### usage:
- Create array of AABB objects
- Create Quadtree: Quadtree(AABB[] ar, int wdth, int hght, int tree_Dpth, boolean square);
- Call aabb.relocate(dx,dy) and quadtree.update() whenever needed

## example usage:
```java
AABB[] aabbs = new AABB[5];

// make 5 AABBs and add to array with various initial values.
for(int i=1; i<aabbs.length+1; i++){
    aabbs[i-1] = new AABB();
    aabbs[i-1].set_Size(i*20, i*15);
    aabbs[i-1].set_Velocity(i*0.3f, i*0.4f);
    aabbs[i-1].relocate(i*200, i*125);
}

// parameters for Quadtree: AABB array, width, height, tree depth, square = false 
// means rectangular Quadtree
int wdth = 1000;
int hght = 600;
int tree_Depth = 5;
boolean square = false;

Quadtree qt = new Quadtree(aabbs, wdth, hght, tree_Depth, square);

// relocate, resize and change velocities of AABBs as needed and then call qt.update();
```
![Qt simulator](https://github.com/The-AJF/Images/blob/master/qt.png)
