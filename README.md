# Quadtree ver 3.27

- <a href="mailto:ajf.digitals@gmail.com">report bugs/feature requests</a>

#### compiled with JDK 16.0.2

This revision makes changes to the UI with an added "Dark" theme, extra functionality and a number of bug fixes. The Quadnode class has been extracted from Quadtree.java where it was an inner class and is now and stand-alone class.

<b>Quadtree.java will effectively determine when AABBs are in close proximity to each other with variable degrees of precision.
    
The Quadtree and all Quadnodes are built during initialization. During runtime, the Quadtree will querry Quadnodes to see if there are at least two AABBs within the Quadnode's bounds. If at least two AABBs are found within the Quadnode's bounds, the search will continue deeper into the sub-Quadnodes otherwise, searching will be culled for the given Quadnode. If at least two AABBs intersect a Quadnode at the deepest tree depth, those AABBs will be marked as being in close proximity to each other.
    
Some noteworthy characteristics:</b>
- AABBs can be added or removed from the Quadtree
- Quadtree can be squared or rectangulated in real time
- tree depth can be increased or decreased allowing variability in what is considered "close proximity"
- Velocities of AABBs can be increased or decreased

#### contains: 
1. Quadtree.java
2. Quadnode.java
3. AABB.java
5. javadoc.zip

#### extras folder contains:
1. ProximityTester.java (windowed simulator for testing - not needed for normal use)

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
![Qt simulator](https://github.com/digitalAJF/Images/blob/master/Quadtree/qt.png)

<b>Older UI for ProximityTester.java</b>

![Qt simulator](https://github.com/digitalAJF/Images/blob/master/Quadtree/ui_old.png)
