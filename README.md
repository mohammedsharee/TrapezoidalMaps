# TRAPEZOIDAL MAPS REPRESENTING AS GERMAN CITIES

# Info
 Creates trapezoidal maps the "naive" way. More specifically, this
 function iterates through each shape, and each point of each shape,
 creating a TrapezoidLine for each point with an initially long length.
 Then, this length is minimized over all the x intersections for above the
 point. This is repeated for all the lines below the point.  
 
 # Input
 Clicking the right or middle mouse button will position a search point in the plane (blue). 
 If a search point has been placed, clicking the “Map" button will not only create a
 trapezoidal map (and accompanying search structure), but will run a point query on the given search point.
 The output of the query will appear after the tree output on the command-line and consists of the nodes 
 visited and the number of steps needed to locate the point (which will very, as the algorithm is random).
 
 # Output
 
  The output on the console gives the position of query point w.r.t tree structure 
  of segments(borders in this case) from the root with its coordinates.
 * Note: Before drawing segments on GUI read the instructions on console.
 
 Detailed discription of an algorithm is discussed in the power point slide of above commit.

 
 
