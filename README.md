# Quadtree ver 3.27

- <a href="mailto:ajf.digitals@gmail.com">report bugs/feature requests</a>

---

Quadtree.java will effectively and efficiently determine when AABBs are in close proximity to each other with variable degrees of precision. The Quadtree and all Quadnodes are built during initialization. During runtime, the Quadtree will querry Quadnodes to see if there are at least two AABBs within the Quadnode's bounds. If at least two AABBs are found, the search will continue deeper into the sub-Quadnodes otherwise, searching will be culled for the given Quadnode. If at least two AABBs intersect a Quadnode at the deepest tree depth, those AABBs are considered as being in close proximity to each other.

---
    
### Some noteworthy characteristics (in real time):
- AABBs can be added or removed from the Quadtree
- Quadtree can be squared or rectangulated (square shape is recommended)
- Tree depth can be increased or decreased allowing variability in what is considered "close proximity"
- Velocities of AABBs can be increased, decreased or averaged

### Folder breakdown:

<pre>
root
|   LICENSE
│
├───docs
│       quadtree_ver_3.27_javadocs.zip
│
├───extras
│       CHANGE_LOG
│       LICENSE
│       <ins><b>ProximityTester.java</b></ins>
│
└───src
        <ins><b>AABB.java</b></ins>
        CHANGE_LOG
        LICENSE
        <ins><b>Quadnode.java</b></ins>
        <ins><b>Quadtree.java</b></ins>
</pre>

### Example Usage:
```java

// 5 AABBs created and added to the array with various initial values.
AABB[] aabbs = new AABB[5];
for(int i=0; i<aabbs.length; i++){
    aabbs[i] = new AABB();
    aabbs[i].set_Size(150, 100);
    aabbs[i].set_Velocity((i+1)*0.3f, (i+1)*0.4f);
    aabbs[i].relocate(new Random().nextInt(850), new Random().nextInt(900));
}

// intialize width, height and tree depth
int width = 1000;
int height = 1000;
int tree_Depth = 5;

// create Quadtree using AABB array, width height and tree depth
Quadtree qtree = new Quadtree(aabbs, width, height, tree_Depth);

// relocate, resize and change velocities of AABBs as needed and then call qtree.update();
```
![Qt simulator](https://github.com/digitalAJF/Images/blob/master/Quadtree/qt.png)

<b>Older UI for ProximityTester.java (previously "Tester.java"):</b>

![Qt simulator](https://github.com/digitalAJF/Images/blob/master/Quadtree/ui_old.png)

