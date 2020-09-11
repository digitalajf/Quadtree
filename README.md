# Quadtree ver 3.16

#### compiled with JDK 13.0.1

<b>Quadtree.java will effectively determine when AABBs are "close enough" to be examined for collisions. My version here is static meaning it is completely built from the tip down to the leaves during initialization in order to improve search performance. 
    
After the Quadtree and all Quadnodes have been built, Quadtree.java will simply querry Quadnodes to see if there are at lease two AABBs that intersect it. If an intersection with at least two AABBs is found, the search will continue deeper into the sub-Quadnodes otherwise, searching will be culled for the given Quadnode. If at least two AABBs intersect any given Quadnode at the deepest tree depth, those AABBs will be marked as being in close proximity to each other.
    
Some noteworthy characteristics:</b>
- exclusively designed to work with AABBs (AABB.java included)
- AABBs will reference each other as being "nearby" when discovered to intersect the same quadnode at maximum tree depth
- AABBs can be added or removed from the Quadtree in real-time
- Quadtree can be squared or rectangulated in real-time
- tree depth can be increased or decreased in real-time allowing variability in what is considered "close proximity"

#### contains: 
1. Quadtree.java
2. AABB.java
3. Tester.java (windowed simulator for testing - not needed for normal use)
4. javadoc.zip

## example usage:
```java

// make 5 AABBs and add to the array with various initial values.
AABB[] aabbs = new AABB[5];
for(int i=1; i<aabbs.length+1; i++){
    aabbs[i-1] = new AABB();
    aabbs[i-1].set_Size(i*20, i*15);
    aabbs[i-1].set_Velocity(i*0.3f, i*0.4f);
    aabbs[i-1].relocate(i*200, i*125);
}

// set up paramters for Quadtree constructor
int wdth = 1000;
int hght = 600;
int tree_Depth = 6;
boolean is_square = false; //"false" means rectangular Quadtree

// create Quadtree using parameters from above
Quadtree qt = new Quadtree(aabbs, wdth, hght, tree_Depth, is_square);

// relocate, resize and change velocities of AABBs as needed and then call qt.update();
```
![Qt simulator](https://github.com/The-AJF/Images/blob/master/qt.png)
