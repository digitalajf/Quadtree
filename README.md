# Quadtree ver 3.27

- <a href="mailto:ajf.digitals@gmail.com">report bugs/feature requests</a>

---

Quadtree.java will effectively determine when AABBs are in close proximity to each other with variable degrees of precision. The Quadtree and all Quadnodes are built during initialization. During runtime, the Quadtree will querry Quadnodes to see if there are at least two AABBs within the Quadnode's bounds. If at least two AABBs are found within the Quadnode's bounds, the search will continue deeper into the sub-Quadnodes otherwise, searching will be culled for the given Quadnode. If at least two AABBs intersect a Quadnode at the deepest tree depth, those AABBs will be marked as being in close proximity to each other.

---

### What's new since ver 3.16?
This revision has benn compiled with JDK 16.0.2. Changes were made to the UI (ProximityTester.java) with an added "Dark" theme, extra functionality and a number of bug fixes. The Quadnode class has been extracted from Quadtree.java where it was once an inner class and is now a stand-alone class. See CHANGE_LOG or "Issues" section for a comprehensive view.
    
### Some noteworthy characteristics:
- AABBs can be added or removed from the Quadtree
- Quadtree can be squared or rectangulated (square shape is recommended)
- tree depth can be increased or decreased allowing variability in what is considered "close proximity"
- Velocities of AABBs can be increased, decreased or averaged

### Folder breakdown:

<pre>
root
├───docs
│       quadtree_ver_3.27_javadocs.zip
│
├───extras
│       CHANGE_LOG
│       LICENSE
│       ProximityTester.java
│
└───src
        AABB.java
        CHANGE_LOG
        LICENSE
        Quadnode.java
        Quadtree.java</pre>

### Example Usage:
```java

// 5 AABBs created and added to the array with various initial values.
AABB[] aabbs = new AABB[5];
for(int i=0; i<aabbs.length; i++){
    aabbs[i] = new AABB();
    aabbs[i].set_Size((i+1)*20, (i+1)*15);
    aabbs[i].set_Velocity((i+1)*0.3f, (i+1)*0.4f);
    aabbs[i].relocate((i+1)*200, (i+1)*125);
}

// set up paramters for Quadtree constructor
int wdth = 1000;
int hght = 1000;
int tree_Depth = 5;

// create Quadtree using parameters from above
Quadtree qt = new Quadtree(aabbs, wdth, hght, tree_Depth);

// relocate, resize and change velocities of AABBs as needed and then call qt.update();
```
![Qt simulator](https://github.com/digitalAJF/Images/blob/master/Quadtree/qt.png)

<b>Older UI for ProximityTester.java (known as Tester.java at the time):</b>

![Qt simulator](https://github.com/digitalAJF/Images/blob/master/Quadtree/ui_old.png)
