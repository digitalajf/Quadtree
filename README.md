# Older Dynamic Quadtree

My original implementaion of Quadtree.java. This older implementation used Vectors to store objects meant for collision detection. The Quadnodes were created in real-time when at least two objects were found in a single quadrant. This dynamic system of creating sub-quadrants as necessary using Vectors to store objects proved to be inefficient (in Java) and the Quadtree became overwhelmed in little time even when using a small number of moving objects. To determine whether the dynamic nature of the quadtree or the use of Vectors was the culprit (or possibly both) will require further investigation in this branch. 

A "Sprite" class was used with this earlier version of Quadtree for testing. The Sprite class was simply an AABB with top left and bottom right corners defined by (x1,y1) and (x2,y2). The Sprite also had a boolean determining whether or not it was close to any other Sprites and the ability to paint itself to a "Graphics" object. 

The "Main" class is simply a platform to test the Quadtree using a JFrame and JPanel as a drawing canvas. "Main.java" would initialize the Quadtree and a number of Sprites and then go on to loop through the simulation by changing and updating the Sprite locations and the state of the Quadtree and finally rendering the scene in its current state.
