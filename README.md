# Static Quadtree

A data structure used for efficient approximation of coordinates on a plain. My version here is static meaning it is completely built from the tip down to the leaves during initialization. Only simple minimum and maximum querries are made against AABBs (Axis-Aligned Bounding Boxes) to find which (ready made) quadnodes they intersect and to focus on within the sub-quadnodes. This static model eliminates both the real-time creation of Quadnode objects and the calculations needed to identify their vertical/horizontal centers. 

The Quadtree will look for at least 2 AABBs that share the same leaf quadnode marking each as being in close proximity to the other.

contains: 
- Quadtree.java
- AABB.java
- Tester.java (windowed simulator for testing - not needed for normal use.)

usage:
- Create array of AABB objects
- Create Quadtree: Quadtree(AABB[] ar, int x, int y, int wdth, int hght, int tree_Dpth, boolean square);
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

// parameters for Quadtree: AABB array, top left (x,y) position, width, height,
// tree depth, square = false means rectangular Quadtree
int x = y = 0;
int wdth = 1000;
int hght = 600;
int tree_Depth = 5;
boolean square = false;

Quadtree qt = new Quadtree(aabbs, x, y, wdth, hght, tree_Depth, square);

// relocate, resize and change velocities of AABBs as needed and then call qt.update();
```
![Qt simulator](https://github.com/The-AJF/Images/blob/master/qt.png)
