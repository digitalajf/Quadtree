# Older Dynamic Quadtree

My original implementaion of Quadtree.java. This older implementation used Vectors to store objects meant for collision detection. The Quadnodes were created in real-time when at least two objects were found in a single quadrant. This dynamic system of creating sub-quadrants as necessary using Vectors to store objects proved to be inefficient (in Java) and the Quadtree became overwhelmed in little time even when using a small number of moving objects. To determine whether the dynamic nature of the quadtree or the use of Vectors was the culprit (or possibly both) will require further investigation in this branch. 

