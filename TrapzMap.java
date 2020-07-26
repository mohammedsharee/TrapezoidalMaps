/*	References used 
 1) https://github.com/malloc47/TrapezoidalMap/blob/master/TrapezoidalMapDriver.java
 *2) https://coderoad.ru/46070592/%D0%9F%D0%BE%D1%81%D1%82%D1%80%D0%BE%D0%B5%D0%BD%D0%B8%D0%B5-%D0%B4%D0%B5%D1%80%D0%B5%D0%B2%D0%B0-%D0%BF%D0%BE%D0%B8%D1%81%D0%BA%D0%B0-%D0%B2-Java
 * 
 * PROJECT FOR 1.5 CREDITS
 * TITLE: TRAPEZOIDAL MAPS REPRESENTING AS GERMAN CITIES
 * 
 * SUBMITTED BY: 
 * AUTHORS: MOHAMMED SHAREEF   119492
 *          NADEEM AHMAD       119371
 * 
 *
 * Creates trapezoidal maps the "naive" way. More specifically, this
 * function iterates through each shape, and each point of each shape,
 * creating a TrapezoidLine for each point with an initially long length.
 * Then, this length is minimized over all the x intersections for above the
 * point. This is repeated for all the lines below the point.  
 * 
 * Output: The output on the console gives the position of query point w.r.t tree structure of segments(borders in this case) from the root with its coordinates.
 * Note: Before drawing segments on GUI read the instructions on console.
 * 
*/
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

public class TrapzMap {	
	
	public static class MapLine {
	  private Point start;
	  private Point end;
	  private int length;
	  private boolean up;	
	  
		public MapLine() {
			length = 0;
			start = new Point();
	        end = new Point();
		}
		
		public MapLine(Point start, int length, boolean up) {
			super();
			this.start = start;
			this.length = length;
	        this.up = up;
	        int  newY;
          if (up==true) {
	    newY= this.start.y - length;
       } else {
    	   newY =this.start.y + length;
      }
          this.end = new Point(this.start.x, newY);
		}
	  
	  public MapLine(Point start, Point end) {
	    this.start = start;
	    this.end = end;
	    this.length = Math.abs(start.y - end.y);
	    this.up = (start.y - end.y > 0);
	  }

		public MapLine(int x1, int y1, int x2, int y2) {
	    this.start = new Point(x1,y1);
	    this.end = new Point(x2,y2);
	    this.length = Math.abs(y1 - y2);
	    this.up = (y1 - y2 > 0);
	  }
		
		public MapLine(int x, int y, int length, boolean up) {
			super();
			this.start = new Point(x,y);
			int  newY;
			  if (up==true) {
				    newY= this.start.y - length;
			       } else {
			    	   newY =this.start.y + length;
			      }
			  this.end = new Point(x,newY);
			this.length = length;
	      this.up = up;
		}
		public int getLength() {
			return length;
		}
		public void setLength(int length) {
			this.length = length;
			int  newY;
			  if (up==true) {
	        	  
				    newY= this.start.y - length;
				   
			       } else {
			    	   
			    	   newY =this.start.y + length;
			      }
			this.end.y= newY;
		}
		
		public Point getStart() {
			return start;
		}
		
		public Point getEnd() {
			return end;
		}
		
		public void setStart(Point start) {
			this.start = start;
		}
		
		public void setEnd(Point end) {
			this.end = end;
	    this.length = Math.abs(start.y - end.y);
	    this.up = (start.y - end.y > 0);
		}	
	  public void setUp(boolean up) {
	    this.up = up;
	    int  newY;
		  if (up==true) {
      	  
			    newY= this.start.y - length;
			   
		       } else {
		    	   
		    	   newY =this.start.y + length;
		      }
		  this.end.y = newY;
	   }
	  public boolean getUp() {
	    return this.up;
	  }
		
		public boolean equals(MapLine t) {
			return (t.start.equals(this.start) && 
	        t.length==this.length && 
	        t.getUp() == this.up);
		}

	  public void view(Graphics draw) {
	    draw.setColor(Color.blue);
	    if(up) {
	      draw.drawLine(start.x,start.y,start.x,start.y - length);
	    }
	    else {
	      draw.drawLine(start.x,start.y,start.x,start.y + length);
	    } 
	  }
	}
	public static class Figure {
	  private List<Point> Coordinates; 
	  public int index = 0;
	  public Figure() {
	    this.Coordinates = new ArrayList<Point>();
	  }
	  public Figure(List<Point> points) {
	    this.Coordinates = points;
	  }
		public int PointsNum(){
			return Coordinates.size();
		}		
		public List<Point> getPoints() {
			return Coordinates;
		}
		public void setPoints(List<Point> points) {
			this.Coordinates = points;
		}
	  public Point getFirst() {
	    return Coordinates.get(0);
	  }
	  public Point getLast() {
	    return Coordinates.get(Coordinates.size() -1);
	  } 	
	  
	  public boolean up(Point p) {
	    if(holds(p)) {
	    	return false;
	    }
	    int p1x = getFirst().x;
	    int p2x = getLast().x;
	    int p1y = getFirst().y;
	    int p2y = getLast().y;
	    return (p.y < approxLine(p1x,p1y,p2x,p2y,p.x));
	  }
	  public boolean down(Point p) {
	    if(holds(p)) {
	    	return false;
	    }
	    int p1x = getFirst().x;
	    int p2x = getLast().x;
	    int p1y = getFirst().y;
	    int p2y = getLast().y;
	    return (p.y > approxLine(p1x,p1y,p2x,p2y,p.x));
	  }
	  private float approxLine(int x1, int y1, int x2, int y2, int x){
		  float n= ((((y2-y1)/(x2-x1))*(x-x1))+y1);
		return n;
	  }		
	  private boolean consistsOf(Point p, Point q, Point r) {
		  if ((p.x > q.x && p.x < r.x ) || (p.x > r.x && p.x < q.x)) {
			  return true;
		  }else
		return false;
	  }
	  
	  public boolean holds(Point point) {
	    return Coordinates.contains(point);
	  }
	  public int intersect(Point point) {
	    int p1x = getFirst().x;
	    int p1y = getFirst().y;
	    int p2x = getLast().x;
	    int p2y = getLast().y;
	    int round =  Math.round( approxLine(p1x,p1y,p2x,p2y,point.x));
	    return round;
	  }
	  
	  public float crossing(MapLine t,int max) {
		   float min = max;
		    Iterator<Point> it = Coordinates.iterator();
		    while(it.hasNext()) {
		      Point pt = it.next();
		      if(Coordinates.indexOf(pt) > 0){
		        Point prevpt = Coordinates.get(Coordinates.indexOf(pt)-1);
		        if(consistsOf(t.getStart(),pt,prevpt)) {
		          min = Math.min(min,(float)(pt.y - prevpt.y)/(pt.x-prevpt.x)*(t.getStart().x-prevpt.x)+prevpt.y);
		        }
		      }
		    }
		    return min;
		  }


		public boolean equals(Figure s) {
			if (s==null) {
				return Coordinates==null;
			} else {
				return s.getPoints().equals(Coordinates);
			}
		}

	  public void draw(Graphics g) {
	    for(int i = 0; i<Coordinates.size(); i++) {
	      g.setColor(Color.red);
	      String [] names= {"Berlin","Bayern (Bavaria)","Niedersachsen (Lower Saxony)","Baden-Württemberg","Rheinland-Pfalz (Rhineland-Palatinate)",
	    		   "Sachsen (Saxony)","Thüringen (Thuringia)","Nordrhein-Westfalen (North Rhine-Westphalia)","Sachsen-Anhalt (Saxony-Anhalt)","Brandenburg",
	    		   "Mecklenburg-Vorpommern","Hamburg","Schleswig-Holstein","Saarland","Bremen"};      
	      g.drawString((names[index])+" ("+Integer.toString(Coordinates.get(i).x)+" , "+Integer.toString(Coordinates.get(i).y)+")",Coordinates.get(i).x, Coordinates.get(i).y);	      
	      Coordinates.get(i).draw(g);
	      if(i>0) Coordinates.get(i).drewSegment(g,Coordinates.get(i-1));
	    }
	  }
	}
	
	public static class  faceSide {
		  public boolean combined = false;
		  public vertices n = null;	  
		  public boolean choosen = false;
		  public Figure north;
		  public Figure south;
		  public Point leftP;
		  public Point rightP;
		  private int index = 0;
		  public faceSide topLeft;
		  public faceSide bottomLeft;
		  public faceSide topRight;
		  public faceSide bottomRight;
	  
		  public faceSide() {
		    this.north = null;
		    this.south = null;
		    this.leftP = null;
		    this.rightP = null;

		    this.topLeft = null;
		    this.bottomLeft = null;
		    this.topRight = null;
		    this.bottomRight = null;
		  }

		  public faceSide(Figure upperPart, Figure bottomPart, Point leftPoint, Point rightPoint) {
		    this.north = upperPart;
		    this.south = bottomPart;
		    this.leftP = leftPoint;
		    this.rightP = rightPoint;

		    this.topLeft = null;
		    this.bottomLeft = null;
		    this.topRight = null;
		    this.bottomRight = null;
		  }

		  public faceSide(Figure upperPart, Figure bottomPart, Point leftPoint, Point rightPoint, faceSide upperLeft, faceSide lowerLeft, faceSide upperRight, faceSide lowerRight) {
		    this.north = upperPart;
		    this.south = bottomPart;
		    this.leftP = leftPoint;
		    this.rightP = rightPoint;
		    this.topLeft = upperLeft;
		    this.bottomLeft = lowerLeft;
		    this.topRight = upperRight;
		    this.bottomRight = lowerRight;
		  }

		  public int getIndex() {
		    return index;
		  }

		  public void setIndex(int index) {
		    this.index = index;
		  }

		  public Figure getUpperPart(){
		    return north;
		  }
		  public Figure getBottomPart() {
		    return south;
		  }
		  public Point getLeftPoint() {
		    return leftP;
		  }
		  public Point getRightPoint() {
		    return rightP;
		  }
		  public void setTop(Figure top) {
		    this.north = top;
		  }
		  public void setBottom(Figure bottom) {
		    this.south = bottom;
		  }
		  public void setLeft(Point leftp) {
		    this.leftP = leftp;
		  }
		  public void setRight(Point rightp) {
		    this.rightP = rightp;
		  }

		  public void setNeighbors(faceSide upperLeft, faceSide lowerLeft, faceSide upperRight, faceSide lowerRight) {
		    this.topLeft = upperLeft;
		    this.bottomLeft = lowerLeft;
		    this.topRight = upperRight;
		    this.bottomRight = lowerRight;
		  }

		  public List<faceSide> getNeighbors() {
		    List<faceSide> neighbors = new ArrayList<faceSide>();
		    if(topLeft != null) neighbors.add(topLeft);
		    if(bottomLeft != null) neighbors.add(bottomLeft);
		    if(topRight != null) neighbors.add(topRight);
		    if(bottomRight != null) neighbors.add(bottomRight);
		    return neighbors;
		  } 
		  
		  public void start(Graphics visuals, int wd, int ht) {
		    Point left = new Point(0,0);
		    Point right = new Point(wd,ht);
		    if(leftP!=null) {
		      left.x = leftP.x;
		      left.y = leftP.y;
		    }
		    if(rightP!=null) {
		      right.x = rightP.x;
		      right.y = rightP.y;
		    }		    
		    if(left.x > right.x) {
		    	int p = left.x;
		    	int q = left.y;
		    	left.x = right.x;
		    	left.y = right.y;
		    	right.x = p;
		    	right.y = q;    	
		    }
     	      if(north==null) {
     	    	  right.y = 0;
     	      }
		      if(south==null) { 
		    	  left.y = ht;
		      }
		      if(choosen) {
		    	  visuals.setColor(Color.green);
		    	  int x= (right.x+left.x)/2;
		    	  int y = (right.y+left.y)/2;
		          visuals.drawString("" + this.index,x,y);
		          visuals.setColor(Color.blue);
		      }

		      if(north!=null) {
		        visuals.drawLine(left.x,left.y,left.x,north.intersect(left));
		        visuals.drawLine(right.x,right.y,right.x,north.intersect(right));
		      }
		      else {
		        visuals.drawLine(left.x,left.y,left.x,0);
		        visuals.drawLine(right.x,right.y,right.x,0);
		      }
		      if(south!=null) {
		        visuals.drawLine(left.x,left.y,left.x,south.intersect(left));
		        visuals.drawLine(right.x,right.y,right.x,south.intersect(right));
		      }
		      else {
		        visuals.drawLine(left.x,left.y,left.x,ht);
		        visuals.drawLine(right.x,right.y,right.x,ht);
		      }
		  }

		  private boolean isEmpty() {
			  if(north==null && south==null && leftP==null && rightP==null) {
				  return true;
			  }
		  
			return false;
		  }

		  public boolean equals(faceSide outer) {
		    return outer==null ? isEmpty() : ( (north==null ? outer.north==null : north.equals(outer.north)) &&
		        (south==null ? outer.south==null : south.equals(outer.south)) &&
		        (leftP==null ? outer.leftP==null : leftP.equals(outer.leftP)) &&
		        (rightP==null ? outer.rightP==null : rightP.equals(outer.rightP)) &&
		        topLeft==outer.topLeft &&
		        bottomLeft==outer.bottomLeft &&
		        topRight==outer.topRight &&
		        bottomRight==outer.bottomRight);
		  }
		}
	public static class PanelTrapezoid extends JPanel implements MouseListener{
	   
	    private Set<Figure> figures = new HashSet<Figure>();
	  
	    private List<Point> points = new ArrayList<Point>();

	    private Set<MapLine> segments = new HashSet<MapLine>();

	    private Set<faceSide> sides = new HashSet<faceSide>();

	    private boolean shapeMode = false;

	    private int index = 0;
	    
	    public Point queryPoint = null; 
    
	    public PanelTrapezoid() {
	      super();
	    
	      this.addMouseListener(this);
	    }

	    public Set<Figure> getFigures() {
	      return figures; 
	    }

	    public void setSegments(Set<MapLine> l) {
	      this.segments = l;
	    }

	    public void setFaces(Set<faceSide> f) {
	      this.sides = f;
	      this.repaint();
	    }

	  	public void paintComponent(Graphics h) {
		    super.paintComponent(h);
	      Iterator<Figure> p = figures.iterator();
	      while(p.hasNext()) {
	        p.next().draw(h);
	      }

	      h.setColor(Color.red);
	      Iterator<Point> q = points.iterator();
	      while(q.hasNext()) {  
	        q.next().draw(h);
	      }
	     
	      h.setColor(Color.blue);
	      if(queryPoint != null) {
	    	  String str = Integer.toString(queryPoint.x)+" , "+Integer.toString(queryPoint.y);
	    	  int x = queryPoint.x;
	    	  int y = queryPoint.y;
	    	  h.drawString(str,x,y);
	    	  queryPoint.draw(h);
	      }

	      Iterator<MapLine> l = segments.iterator();
	      while(l.hasNext()) {
	        l.next().view(h);
	      }

	    }
	    public void cancel() {
	      points.clear();
	      segments.clear();
	      sides.clear();
	      this.repaint();
	    }
	    public void clearAll() {
	      points.clear();
	      figures.clear();
	      segments.clear();
	      sides.clear();
	      queryPoint = null;
	      index=0;
	      this.repaint();
	    }
	    public void mousePressed (MouseEvent e) {
	    }
	    public void mouseReleased (MouseEvent e) {
	    }
	    public void mouseEntered (MouseEvent e) {
	    }
	    public void mouseExited (MouseEvent e) {
	    }
	    public void mouseClicked (MouseEvent e) {
	      if(!shapeMode && e.getButton() == MouseEvent.BUTTON1) {
	        points.add(new Point(e.getX(),e.getY()));
	        shapeMode = true;
	      }
	      else if(shapeMode && e.getButton() == MouseEvent.BUTTON1) {
	        shapeMode = false;
	        points.add(new Point(e.getX(),e.getY()));
	        List<Point> copyList = new ArrayList<Point>(points);
	        Figure newShape = new Figure(copyList);
	        newShape.index = index++;
	        figures.add(newShape);
	        points.clear();
	      }
	      else if(e.getButton() == MouseEvent.BUTTON3 || e.getButton() == MouseEvent.BUTTON2) {
	        queryPoint = new Point(e.getX(),e.getY());
	        
	      }
	      else {
	        points.add(new Point(e.getX(),e.getY()));
	      }
	      this.repaint();
	    }

	  }
	public static class Point {

		public int x;
		public int y;
		
		public Point() {
			this.x = 0;
			this.y = 0;
		}
		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		public boolean equals(Point point) {
			return (point.x == this.x && point.y == this.y);
		}

	  public boolean right(Point point) {
	    return (this.x >= point.x);
	  }

	  public void draw(Graphics visuals) {
	    visuals.fillOval(x-5,y-5,6,6);
	  }

	  public String print() {
	    return "("+this.x+","+this.y+")";
	  }

	  public void drewSegment(Graphics g, Point other) {
	    g.setColor(Color.black);
	    g.drawLine(x,y,other.x,other.y);
	  }
		
	}
	public static class vertices {
		String [] states= {"Berlin","Bayern (Bavaria)","Niedersachsen (Lower Saxony)","Baden-Württemberg","Rheinland-Pfalz (Rhineland-Palatinate)",
				   "Sachsen (Saxony)","Thüringen (Thuringia)","Nordrhein-Westfalen (North Rhine-Westphalia)","Sachsen-Anhalt (Saxony-Anhalt)","Brandenburg",
				   "Mecklenburg-Vorpommern","Hamburg","Schleswig-Holstein","Saarland","Bremen"};

		  public static final int POINT = 0;
		  public static final int LINE = 1;
		  public static final int CHILDREN = 2;
		  private int copy;
		  public Point p = null;
		  public Figure q = null;
		  public faceSide f = null; 
		  public vertices rootNode = null;
		  public vertices leftNode = null;
		  public vertices rightNode = null;

		  public vertices() {
		  }

		  public vertices(Point p) {
		    this.p = p;
		    this.copy = POINT;
		  }

		  public vertices(Figure s) {
		    this.q = s;
		    this.copy = LINE;
		  }

		  public vertices(faceSide t) {
		    this.f = t;
		    this.copy = CHILDREN;
		    t.n = this;
		  }

		  public vertices(int s) {
		    this.copy = s;
		  }

		  public void setT(int s) {
		    this.copy = s;
		  }

		  public int getT() {
		    return this.copy;
		  }

		  public String display() {
		    String result;
		    if(copy == POINT) {
		        result = "Cordinates of the point:(longitude"+" "+p.x+", latitude"+" "+p.y+")";
		      }
		      else if(copy == LINE) {
		        result = "Sharing Border of the state:"+states[q.index];
		      }
		    else {
		      result = "T:"+f.getIndex();
		    } 
		    return result;
		  }

		  public vertices getNext(Point p) {
		
		    if(copy == POINT) {
		    	if(p.right(this.p)) {
		    		return rightNode;
		    	}else {
		    		return leftNode;
		    	}
		    } 
		    else if(copy == LINE) {
		    	if(q.up(p)) {
		    		return leftNode;
		    	}else {
		    		return rightNode;
		    	}
		    }
		    return null;
		  }

		  public boolean isLeftNode(vertices other) {
			  
			  if(other==null && this.p==null && this.q==null && this.f==null) 
				  return true;
			return true;
		  }

		  public boolean equals(vertices other) {

		    boolean initial = false;

		    if(other==null && this.p==null && this.q==null && this.f==null)
		      return true;
		    else if(other==null)
		      return false;
		    else{
		      if(this.p != null && other.p != null && this.p.equals(other.p) || (this.q != null && other.q != null && this.q.equals(other.q)) || (this.f != null && other.f != null && this.f.equals(other.f)))
		    	  initial = true;
		      if(this.copy != other.getT()) initial = false;

		      return initial; 
		    }
		  }

		}
	public static class Tree {
		  
		  private vertices root = null;

		  public Tree() {
		  }

		  public Tree(vertices parent) {
		    this.root = parent;
		  }

		  public faceSide retrieve(Point p) {

		    vertices newNode = root;
		    
		    while(newNode.getT() != vertices.CHILDREN) {
		      newNode = newNode.getNext(p);
		    }
		    return newNode.f;
		  }


		  public void preTraverseGraph(vertices p) {
		    String h = "";
		    int d = treeHeight(p);
		    for(int x = 1; x<d; x++) {
		      h = h + " ";
		    }
		    System.out.println(h + p.display());

		    if (p.leftNode != null) {
		    	preTraverseGraph(p.leftNode);
		    }
		    if (p.rightNode != null) {
		    	preTraverseGraph(p.rightNode);
		    }
		  }
		  public void currentTraTree(vertices p) {
			    if (p.leftNode != null) {
			    	currentTraTree(p.leftNode);
			    }
			    String s = "";
			    int d = treeHeight(p);
			    for(int x = 1; x<d; x++) {
			      s = s + " ";
			    }
			    System.out.println(s + p.display());
			    if (p.rightNode != null) {
			    	currentTraTree(p.rightNode);
			    }
		  }
		  
		  public void postorderTraverseTree(vertices p) {
			    if (p.leftNode != null) {
			    	postorderTraverseTree(p.leftNode);
			    }
			    if (p.rightNode != null) {
			    	postorderTraverseTree(p.rightNode);
			    }
			    String h = "";
			    int d = treeHeight(p);
			    for(int x = 1; x<d; x++) {
			      h = h + " ";
			    }
			    System.out.println(h + p.display());
		}
		  
		  public void traverseTree() {
			System.out.println("PreArranged Nodes:");
		    preTraverseGraph(root);
		    System.out.println("Inordered Nodes:");
		    currentTraTree(root);
		    System.out.println("PostArranged Nodes:");
		    postorderTraverseTree(root);
		    System.out.println("Search Tree Output Complete");
		    
		  }

		  private int treeHeight(vertices n) {
		    vertices step = n;
		    int distance = 0;
		    do {
		      distance++;
		      step = step.rootNode;
		    } while(step != null);

		    return distance;
		  }

		  public vertices callVertex(Point p) {

			System.out.println("Retrieving point=("+p.x+","+p.y+")");
			  
		    vertices nextNode = root;
		    int steps = 0;
		    String s = "";
		    while(nextNode.getT() != vertices.CHILDREN) {
		      System.out.println(s+nextNode.display());
		      nextNode = nextNode.getNext(p);
		      steps++;
		      s=s+" ";
		    }
		    System.out.println("Steps: "+steps);
		    return nextNode;
		  }

		  public void add(vertices n) {
		    if(root == null)
		      root = n;
		  }

		}
	private Set<Figure> shapes = new HashSet<Figure>();

	private Tree copy;

	public TrapzMap() {
		super();
		this.shapes = new HashSet<Figure>();
	}

	public TrapzMap(Set<Figure> shapes) {
		super();
		this.shapes = shapes;
	}

	public void setShape(Set<Figure> shapes) {
		this.shapes = shapes;
	}

	@SuppressWarnings("unchecked")
	public Set<faceSide> incrementalMap(int height, int width) {
		Set<faceSide> faces = new HashSet<faceSide>();

		Set<Figure> lines = ((Set<Figure>) ((HashSet<Figure>) shapes).clone());

		faceSide nilFace = new faceSide();
		faces.add(nilFace);

		copy = new Tree();
		copy.add(new vertices(nilFace));
		while (lines.size() > 0) {
			Figure seg = randomShape(lines);
			lines.remove(seg);
			List<faceSide> overlapingFaces = FollowLine(seg);
			Point a = seg.getFirst();
			Point b = seg.getLast();
			if (overlapingFaces.size() == 1) {
				faceSide d = overlapingFaces.get(0);
				faces.remove(d);
				faceSide p = new faceSide(d.north, d.south, d.leftP, a);
				faceSide q = new faceSide(d.north, seg, a, b);
				faceSide r = new faceSide(seg, d.south, a, b);
				faceSide s = new faceSide(d.north, d.south, b,d.rightP);
				p.setNeighbors(d.topLeft, d.bottomLeft, q, r);
				q.setNeighbors(p, p, s, s);
				r.setNeighbors(p, p, s, s);
				s.setNeighbors(q, r, d.topRight, d.bottomRight);
				if(d.topLeft != null) {
					d.topLeft.topRight = p;
					d.topLeft.bottomRight = p;
				}
				if(d.bottomLeft != null) {
					d.bottomLeft.topRight = p;
					d.bottomLeft.bottomRight = p;
				}
				if(d.topRight != null) {
					d.topRight.topLeft = s;
					d.topRight.bottomLeft = s;
				}
				if(d.bottomRight != null) {
					d.bottomRight.topLeft = s;
					d.bottomRight.bottomLeft = s;
				}
				faces.add(p);
				faces.add(s);
				faces.add(q);
				faces.add(r);
				vertices subRoot = d.n;
				subRoot.f = null;
				subRoot.setT(vertices.POINT);
				subRoot.p = a;
				subRoot.leftNode = new vertices(p);
				subRoot.leftNode.rootNode = subRoot;
				subRoot.rightNode = new vertices(b);
				subRoot.rightNode.rootNode = subRoot;
				subRoot.rightNode.rightNode = new vertices(s);
				subRoot.rightNode.rightNode.rootNode = subRoot.rightNode;
				subRoot.rightNode.leftNode = new vertices(seg);
				subRoot.rightNode.leftNode.rootNode = subRoot.rightNode;
				subRoot.rightNode.leftNode.leftNode = new vertices(q);
				subRoot.rightNode.leftNode.leftNode.rootNode = subRoot.rightNode.leftNode;
				subRoot.rightNode.leftNode.rightNode = new vertices(r);
				subRoot.rightNode.leftNode.rightNode.rootNode = subRoot.rightNode.leftNode;
			} else {
				int i = 0;
				Set<faceSide> newFaces = new HashSet<faceSide>();
				faceSide Upper = null;
				faceSide Lower = null;
				for (faceSide d : overlapingFaces) {
					
					if (i == 0) {
						faces.remove(d);
						faceSide x = new faceSide(d.north, d.south,d.leftP, a);
						faceSide y = new faceSide(d.north, seg, a,d.rightP);
						faceSide z = new faceSide(seg, d.south, a,d.rightP);
						x.setNeighbors(d.topLeft, d.bottomLeft, y, z);
						y.setNeighbors(x, x, null, null);
						z.setNeighbors(x, x, null, null);
						if(d.topLeft!=null) {
								d.topLeft.topRight = x;
							d.topLeft.bottomRight = x;
						}
						if(d.bottomLeft!=null) {
								d.bottomLeft.bottomRight = x;
							d.bottomLeft.topRight = x;
													}									
						faces.add(x);
						newFaces.add(y);
						newFaces.add(z);

						Upper = y;
						Lower = z;

						vertices subRoot = d.n; 

						subRoot.f = null;
						subRoot.setT(vertices.POINT);
						subRoot.p = a;
						subRoot.leftNode = new vertices(x);
						subRoot.leftNode.rootNode = subRoot;
						subRoot.rightNode = new vertices(seg);
						subRoot.rightNode.rootNode = subRoot;
						subRoot.rightNode.leftNode = new vertices(y);
						subRoot.rightNode.leftNode.rootNode = subRoot.rightNode;
						subRoot.rightNode.rightNode = new vertices(z);
						subRoot.rightNode.rightNode.rootNode = subRoot.rightNode;
						for(faceSide e : faces) {
							if(!faces.contains(e.topLeft)) {
								e.topLeft=null;
							}
							if(!faces.contains(e.bottomLeft)) {
								e.bottomLeft=null;
							}
							if(!faces.contains(e.topRight)) {
								e.topRight=null;
							}
							if(!faces.contains(e.bottomRight)) {
								e.bottomRight=null;
							}
						}

					}
					else if (i == overlapingFaces.size() - 1) {
						faces.remove(d);

						faceSide p = new faceSide(d.north, seg,d.leftP, b);
						faceSide q = new faceSide(seg, d.south,d.leftP, b);
						faceSide r = new faceSide(d.north, d.south, b,d.rightP);

						p.setNeighbors(Upper, Upper, r, r);
						q.setNeighbors(Lower, Lower, r, r);
						r.setNeighbors(p, q, d.topRight, d.bottomRight);

						Upper.topRight = p;
						Upper.bottomRight = p;
						Lower.topRight = q;
						Lower.bottomRight = q;
						
						if(d.topRight!=null) {
								d.topRight.topLeft = r;
							d.topRight.bottomLeft = r;
						}
						if(d.bottomRight!=null) {
								d.bottomRight.bottomLeft = r;
							d.bottomRight.topLeft = r;
						}
						faces.add(r);
						newFaces.add(p);
						newFaces.add(q);
						vertices subRoot = d.n; 
						subRoot.f = null;
						subRoot.setT(vertices.POINT);
						subRoot.p = b;
						subRoot.rightNode = new vertices(r);
						subRoot.rightNode.rootNode = subRoot;
						subRoot.leftNode = new vertices(seg);
						subRoot.leftNode.rootNode = subRoot;
						subRoot.leftNode.leftNode = new vertices(p);
						subRoot.leftNode.leftNode.rootNode = subRoot.leftNode;
						subRoot.leftNode.rightNode = new vertices(q);
						subRoot.leftNode.rightNode.rootNode = subRoot.leftNode;
						for(faceSide e : faces) {
							if(!faces.contains(e.topLeft)) e.topLeft=null;
							if(!faces.contains(e.bottomLeft)) e.bottomLeft=null;
							if(!faces.contains(e.topRight)) e.topRight=null;
							if(!faces.contains(e.bottomRight)) e.bottomRight=null;
						}
					}
					else {
						faces.remove(d);
						faceSide A = new faceSide(d.north, seg,d.leftP, d.rightP);
						faceSide B = new faceSide(seg, d.south,d.leftP, d.rightP);
						A.setNeighbors(Upper, Upper, null, null);
						B.setNeighbors(Lower, Lower, null, null);
						Upper.topRight = A;
						Upper.bottomRight = A;
						Lower.topRight = B;
						Lower.bottomRight = B;				
						Upper = A;
						Lower = B;
						newFaces.add(A);
						newFaces.add(B);
						vertices subRoot = d.n;
						subRoot.f = null;
						subRoot.setT(vertices.LINE);
						subRoot.q = seg;
						subRoot.leftNode = new vertices(A);
						subRoot.leftNode.rootNode = subRoot;
						subRoot.rightNode = new vertices(B);
						subRoot.rightNode.rootNode = subRoot;
						for(faceSide e : faces) {
							if(!faces.contains(e.topLeft)) e.topLeft=null;
							if(!faces.contains(e.bottomLeft)) e.bottomLeft=null;
							if(!faces.contains(e.topRight)) e.topRight=null;
							if(!faces.contains(e.bottomRight)) e.bottomRight=null;
						}						
					}
					i++;
				}
				boolean allMerged = false;
				while (!allMerged) {
					for (faceSide d : newFaces) {
						if (d.rightP != null&& !d.rightP.equals(a)&& !d.rightP.equals(b)&& ((d.north != null && (d.north.up(d.rightP))) || (d.south != null && (d.south.down(d.rightP))))) {
							faceSide next = d.topRight;
							d.topRight = next.topRight;
							d.bottomRight = next.bottomRight;
							if ((d.north != null && (d.north.up(d.rightP)))) {
								d.topRight.bottomLeft = d;
							}
							else {
								d.topRight.topLeft = d;
							}
							d.rightP = next.rightP;
							if (next.n.rootNode.isLeftNode(next.n))
								next.n.rootNode.leftNode = d.n;
							else
								next.n.rootNode.rightNode = d.n;
							newFaces.remove(next);
							break;
						} else {
							d.combined = true;
						}
					}
					allMerged = true;
					for (faceSide d : newFaces) {
						if (!d.combined)
							allMerged = false;
					}
				}
				for (faceSide d : newFaces) {
					d.combined = false;
					faces.add(d);
				}
			}
		}
		
		int i = 0;

		for (faceSide f : faces) {
			f.setIndex(i);
			i++;
		}

		copy.traverseTree();
		return faces;
	}

	public void retrievePoint(Point p) {
		copy.callVertex(p).f.choosen = true;
	}

	private List<faceSide> FollowLine(Figure s) {
		List<faceSide> traversed = new ArrayList<faceSide>();
		Point p = s.getFirst();
		Point q = s.getLast();
		faceSide start = copy.retrieve(p);
		traversed.add(start);
		faceSide j = start;
		while (j!=null && (j.rightP != null && q.right(j.rightP))) {
			if (s.up(j.rightP))
				j = j.bottomRight;
			else
				j = j.topRight;
			if(j!=null)traversed.add(j);
		}
		return traversed;
	}
	private Figure randomShape(Set<Figure> segments) {
		Random rand = new Random();
		int n = rand.nextInt(segments.size());
		int i = 0;
		for (Figure seg : segments) {
			if (i == n)
				return seg;
			i = i + 1;
		}
		return null;
	}
	
	public Set<MapLine> Map(int height, int width) {
		Set<MapLine> trapezoidMap = new HashSet<MapLine>();
		Figure boundary = new Figure();
		Figure secondBoundary = new Figure();
		boundary.getPoints().add(new Point(0, 0));
		boundary.getPoints().add(new Point(width, 0));
		secondBoundary.getPoints().add(new Point(0, height));
		secondBoundary.getPoints().add(new Point(width, height));
		shapes.add(boundary);
		shapes.add(secondBoundary);
		Iterator<Figure> s = shapes.iterator();
		while (s.hasNext()) {
			Figure sh = s.next();
			if (sh.equals(boundary) || sh.equals(secondBoundary))
				continue;
			Iterator<Point> p = sh.getPoints().iterator();
			while (p.hasNext()) {
				Point pt = p.next();
				MapLine t = new MapLine(pt, height + 1, true);
				MapLine t2 = new MapLine(pt, height + 1, false);
				Iterator<Figure> s2 = shapes.iterator();
				while (s2.hasNext()) {
					Figure sh2 = s2.next();
					if (t.getStart().y - sh2.crossing(t, height + 1) > 0) {
						t.setLength(Math.min((int) (t.getStart().y - sh2
								.crossing(t, height + 1)), t.getLength()));
					}
					else if (t2.getStart().y - sh2.crossing(t2, height + 1) < 0) {
						t2.setLength(Math.min((int) Math.abs(t2.getStart().y
								- sh2.crossing(t2, height + 1)), t2
								.getLength()));
					}
				}
				if (t.getLength() < height) {
					trapezoidMap.add(t);
				}
				if (t2.getLength() < height) {
					trapezoidMap.add(t2);
				}
			}
		}
		shapes.remove(boundary);
		shapes.remove(secondBoundary);
		return trapezoidMap;
	}
	
	public static class TrapezoidMap implements ActionListener{
		
	  	private JPanel buttonPanel = new JPanel(new GridLayout(0,1));
	  	private PanelTrapezoid draw = new PanelTrapezoid();
	  	private JButton Map = new JButton("Map");
	  	private JButton back = new JButton("Back");
	    private JButton restart = new JButton("Restart");
	    
	    private JFrame frame = new JFrame("GERMANY STATES AS TRAPEZOIDS");

	    public TrapezoidMap() {
	        this.createAndShowGUI();
	    }

	    private void createAndShowGUI() {
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);      
	        buttonPanel = new JPanel(new GridLayout(0, 1));
	        draw.setPreferredSize(new Dimension(600, 600));
	        buttonPanel.add(Map,BorderLayout.SOUTH);
	        buttonPanel.add(back,BorderLayout.SOUTH);
	        buttonPanel.add(restart,BorderLayout.SOUTH);
	        
	        back.addActionListener(this);
	        restart.addActionListener(this);
	        Map.addActionListener(this);
	        frame.getContentPane().add(buttonPanel,BorderLayout.SOUTH);
	        frame.getContentPane().add(draw,BorderLayout.CENTER);
	        
	        frame.pack();
	        frame.setVisible(true);
	    }

	    public void actionPerformed(ActionEvent e) {
	      if (e.getSource()==back) {
	        draw.cancel(); 
	      }
	      else if(e.getSource()==restart) {
	        draw.clearAll();
	      }
	    else if(e.getSource()==Map) {
	        TrapzMap t = new TrapzMap(draw.getFigures());
	        draw.setSegments(t.Map(draw.getHeight(),draw.getWidth()));
	        draw.setFaces(t.incrementalMap(draw.getHeight(),draw.getWidth()));
	        if(draw.queryPoint != null) t.retrievePoint(draw.queryPoint);
	      }  
	   }

		public static  void main(String[] args) {
			 System.out.println("Welcome to the Trapezoidal Map Demo");
			 System.out.println("Please avoid Crossing segments it cannot be handeled.");
			 System.out.println("If the segments are more than the number of States of Germany, it will cause the exception.");
			 
	            System.out.println("--------functionalities are listed below---------------------------------------");
	            System.out.println("To draw points of segments graphically for a trapezoidal map use left-click of mouse,");
	            System.out.println("To place a point to be retrieved use right-click of mouse   ");
	            System.out.println("To erase the points or the segments use Clear Button");
	            System.out.println("To clear all the points and segments use Clear All Button");
	            System.out.println("To get the surrounding points and segments of a retrieval point in console use Map Button ");
	           
	            System.out.println("Close when finished");
	                    
	         javax.swing.SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	              TrapezoidMap driver = new TrapezoidMap();
	            }
	        });
		}
	}
	
}