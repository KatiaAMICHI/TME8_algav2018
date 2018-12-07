package algorithms;

import java.awt.Point;
import java.util.ArrayList;

import supportGUI.Circle;
import supportGUI.Line;

import java.util.ListIterator;

import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;



public class DefaultTeam {

  // calculDiametre: ArrayList<Point> --> Line
  //   renvoie une pair de points de la liste, de distance maximum.
  public Line calculDiametre(ArrayList<Point> points) {
    if (points.size()<3) {
      return null;
    }

    Point p=points.get(0);
    Point q=points.get(1);

    /*******************
     * PARTIE A ECRIRE *
     *******************/
    return new Line(p,q);
  }

  // calculDiametreOptimise: ArrayList<Point> --> Line
  //   renvoie une pair de points de la liste, de distance maximum.
  public Line calculDiametreOptimise(ArrayList<Point> points) {
    if (points.size()<3) {
      return null;
    }

    Point p=points.get(1);
    Point q=points.get(2);

    /*******************
     * PARTIE A ECRIRE *
     *******************/
    return new Line(p,q);
  }

  // calculCercleMin: ArrayList<Point> --> Circle
  //   renvoie un cercle couvrant tout point de la liste, de rayon minimum.
  public Circle calculCercleMin(ArrayList<Point> points) {
    if (points.isEmpty()) {
      return null;
    }

    Point center=points.get(0);
    int radius=100;

    /*******************
     * PARTIE A ECRIRE *
     *******************/
    return new Circle(center,radius);
  }
  
  // < 0 : p à gauche a (ab)
  // = 0 : p appartient à (ab)
  // > 0 : p à droite a (ab)
  double prodVec(Point a, Point b, Point p){
	  return (b.x - a.x) * (p.y - a.y) - (b.y - a.y) * (p.x - a.x);
  }
  

  static private int distanceSquare(Point p, Point q)
  {
	  return (q.x - p.x)*(q.x - p.x) + (q.y - p.y)*(q.y - p.y);
  }
  
  private boolean isBetweenOrder(int a, double x, int b)
  {
	  return a <= x && x <= b;
  }
	  
  private boolean isBetween(int a, int x, int b)
  {
	  return (Math.abs(x - a) <= Math.abs(b - a));
  }
  

  public ArrayList<Point> enveloppeConvexeNaif(ArrayList<Point> points){
	  ArrayList<Point> enveloppe = new ArrayList<Point>();
	  
	  for(Point p : points){
		  for(Point q : points){
			  if(p.equals(q))
				  continue;
			  Point r = p;
			  for(Point rr : points){
				  if(prodVec(p, q, rr) != 0){
					  r = rr;
					  break;
				  }
			  }
			  if(r.equals(p)){
				  enveloppe.add(p);
				  enveloppe.add(q);
				  continue;
			  }
			  double prod = prodVec(p, q, r);
			  boolean meme_cote = true;
			  for(Point s : points){
				  if(prod * prodVec(p, q, s) < 0)
				  {
					  meme_cote = false;
					  break;
				  }
			  }
			  if(meme_cote){
				  enveloppe.add(p);
				  enveloppe.add(q);
			  }
		  }
	  }
	  return enveloppe;
  }

  boolean isInsideOrientedRectangle(Point top, Point bottom, Point left, Point right, int x, int y) {
	  	  
	if (x<=left.x)   return false;
	if (x>=right.x)  return false;
	if (y>=top.y)    return false;
	if (y<=bottom.y) return false;
 
	if (x<=bottom.x && y<=left.y)
		if ( (y-bottom.y)*(bottom.x-left.x) <= (left.y-bottom.y)*(bottom.x-x) ) return false;
 
	if (x>=bottom.x && y<=right.y)
		if ( (y-bottom.y)*(right.x-bottom.x) <= (right.y-bottom.y)*(x-bottom.x) ) return false;
 
	if (x>=top.x && y>=right.y)
		if ( (top.y-y)*(right.x-top.x) <= (top.y-right.y)*(x-top.x) ) return false;
 
	if (x<=top.x && y>=left.y)
		if ( (top.y-y)*(top.x-left.x) <= (top.y-left.y)*(top.x-x) ) return false;
 
	return true;
	
  }
  
  public ArrayList<Point> filtrerPointsAlignes(ArrayList<Point> points){

	  
	  ArrayList<Point> tmp = new ArrayList<Point>();
	  
	  for(int i  = 0; i < points.size(); i++){
		  Point p = points.get(i);
		  tmp.clear();
		  for(int j  = 0; j < points.size(); j++){
			  Point q = points.get(j);
			  if(p.getX() == q.getX()) {
				  tmp.add(q);
			  }
		  }
		  if(tmp.size()<=2)
			  continue;
		  Point maxX=tmp.get(0), minX=tmp.get(0);
		  for(int j  = 0; j < tmp.size(); j++) {
			  Point t = tmp.get(j);
			  if(t.getY() > maxX.getY()) {
				  maxX = t;
			  }
			  if(t.getY() < minX.getY()) {
				  minX = t;
			  }
				 
		  }
		  System.out.println(maxX.y + " | " + minX.y);
		  for(int j  = 0; j < tmp.size(); j++) {
			  Point t = tmp.get(j);
			  if(t.y <maxX.y && t.y>minX.y) {
				  points.remove(t);
			  }
		  }
		
	  }
	  	  
	  System.out.println("[filtre] len Point : " + points.size());
	  return points ;
  }

 
  public ArrayList<Point> filtrerPointsAlignesAkl(ArrayList<Point> points){
	  ArrayList<Point> result = new ArrayList<Point>();
	  
	  int west = points.get(0).x,east = points.get(0).x;
	  int north= points.get(0).y, south = points.get(0).y;
	  Point pw=points.get(0),pe=points.get(0),pn=points.get(0),ps=points.get(0);
	  
	  for(int i  = 1; i < points.size(); i++){
		  Point p =points.get(i); 
		  if(p.x < west) {
			  pw = p;
			  west=pw.x;
		  }
		  if(p.x > east) {
			  pe = p;
			  east=pe.x;
		  }
		  if(p.y < north) {
			  pn = p;
			  north=pn.y;
		  }
		  if(p.y > south) {
			  ps = p;
			  south=ps.y;
		  }
		  
	  }
	  
	  result.add(pw);
	  result.add(pe);
	  result.add(pn);
	  result.add(ps);
	  
	  for(int i  = 1; i < points.size(); i++){
		  Point c = points.get(i);
		  if(isInsideOrientedRectangle(ps, pn, pw ,pe, c.x, c.y)){
			  points.remove(c);
		  }
	  }
	  System.out.println("[Akl] len points  : "+ points.size());
	  return points;

  }
  
  // enveloppeConvexe: ArrayList<Point> --> ArrayList<Point>
  //   renvoie l'enveloppe convexe de la liste.
  public ArrayList<Point> enveloppeConvexe(ArrayList<Point> points){
    if (points.size()<3) {
      return null;
    }

    ArrayList<Point> enveloppe = new ArrayList<Point>();

    // naif
    points = filtrerPointsAlignesAkl((ArrayList<Point>)points.clone());
    System.out.println("1 nb points : " + points.size());
    //points = filtrerPointsAlignes((ArrayList<Point>)points.clone());
    //System.out.println("2 nb points : " + points.size());

	enveloppe = enveloppeConvexeNaif(points);
     
    return enveloppe;
  }
 

}
