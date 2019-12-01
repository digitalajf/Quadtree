# (static) Quadtree and AABBs

usage:
- Create array of AABB objects
- Create Quadtree using constructor Quadtree(....)
- Call "quadtree.update()" whenever needed

The most important change in this release in comparison to the original is that the Quadtree is made from top to bottom immediately. The root quadnode is made and then all sub-quadnodes are made until the requested tree depth has been reached. As a result of this new working model, quadnodes are not initialized in real-time and instead, simple minimum and maximum querries are made against objects to find which (ready made) quadnodes they intersect and where to continue checking within the tree hierarchy. This static model eliminates the real-time creation of "Quadnode" objects and multiple calculations done to identify their (x,y) centers. Instead, the data is already available beforehand.

Vectors storing objects have been replaced by LinkedLists resulting in improved efficiency iterating through object lists. This was intended once a viable release of this project was available.

The Quadtree can now be converted to be square in real-time. The Quadtree is rectangular by default and fills the entire coordinate space however you may choose to "square" the Quadtree as well. A square Quadtree will ensure all quadnodes are also square which in turn will ensure that a positive proximity test will always have the same probability whether AABBs are approaching each other from the top\bottom or left\right. Rectangular quadnodes on the other hand will result in more frequent positive proximity results on the axis where Quadnodes have longer edges. A square Quadtree will be partially extended beyond the coordinate space (to the South) but there should be no performance cost for this (subject to review).

"The Sprite class has been renamed to AABB (axis aligned bounding box) since this is what "Sprite.java" was in essence. "AABB.java" has been streamlined to contain only the members and methods it needs for its intended purpose so there is no "paint(Graphics g)" method nor does it contain a "Color" object to be used within the old "paint" method. Currently, an AABB contains:
- four values to determine its top left and bottom right coordinates (x1,y1) and (x2,y2)
- width and height (faster than checking x2-x1 or y2-y1
- direction and velocity stored in (dx,dy)
- a reference to an AABB that may be discovered to be within close proximity to itself.
- a number of methods used to access or change any of the above

The Simulator has been modified to fully outline the capabilities of the Quadtree in dealing with AABBs. It is now possible to add and remove AABBs, increase and decrease the tree depth and toggle between using a square or a rectangular one.
