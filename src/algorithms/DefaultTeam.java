package algorithms;

import java.awt.Point;
import java.util.ArrayList;

import supportGUI.Circle;
import supportGUI.Line;

import java.util.ListIterator;



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
  
  static private int square(int x)
  {
	  return x * x;
  }
  
  static private int distanceSquare(Point p, Point q)
  {
	  return square(q.x - p.x) + square(q.y - p.y);
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

  
  private ArrayList<Point> filtrerPointsAlignes(ArrayList<Point> points){
	  for(Point p : points){
		  System.out.println("loop");
		  for(Point q : points){
			  System.out.println("end first loop5");
			  if(p.equals(q))
				  continue;
			  if(p.x == q.x){
				  System.out.println("end first loop 4");
				  ListIterator<Point> pm = points.listIterator();
				  while(pm.hasNext()){
					  Point m = pm.next();
					  if(m.equals(p))
						  continue;
					  if(m.equals(q))
						  continue;
					  if(isBetween(p.y, m.y, q.y)) {
						  pm.remove();
					  }
						  
				  }
				  System.out.println("end first loop 3");
			  }
			  System.out.println("Apres if p.x==q.x");

			  
			  if(p.y == q.y){
				  System.out.println("end first loop");
				  ListIterator<Point> pm = points.listIterator();
				  while(pm.hasNext())
				  {
					  Point m = pm.next();
					  if(m.equals(p))
						  continue;
					  if(m.equals(q))
						  continue;
					  if(isBetween(p.x, m.x, q.x)) {
						  pm.remove();
					  }
						  
				  }
				  System.out.println("end first loop 2");
			  }
			  System.out.println("Apres if p.y==q.y");
		  }
	  }
	  System.out.println("[filtre] len Point : " + points.size());
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
    points = filtrerPointsAlignes(points);
	enveloppe = enveloppeConvexeNaif(points);

    return enveloppe;
  }

  

}
