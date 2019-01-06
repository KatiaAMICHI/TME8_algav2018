package algorithms;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Vector;
import java.util.Iterator;
import java.util.ListIterator;

import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

import supportGUI.Circle;
import supportGUI.Line;

public class DefaultTeam {


	class PaireAntipodale{
		public PaireAntipodale(Point p, Point q){
			  this.p = p;
			  this.q = q;
		  }
		  public Point p;
		  public Point q;
		  
	}

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

  // calculDiametremise: ArrayList<Point> --> Line
  //   renvoie une pair de points de la liste, de distance maximum.
  public Line calculDiametreOptimise(ArrayList<Point> points) {
	  System.out.println("calculDiametreOptimal");
	  if (points.size()<3)
		  return null;
	  ArrayList<Point> enveloppe = enveloppeConvexeJarvis(points);
	  ArrayList<PaireAntipodale> antipodales = antipodales(enveloppe);
	  PaireAntipodale diam = diametre(antipodales);
	  return new Line(diam.p, diam.q);
  }
  
  private ArrayList<Point> enveloppeConvexeJarvis(ArrayList<Point> points){
	  System.out.println("[Javis] IN : " + points.size() );
	  ArrayList<Point> enveloppe = new ArrayList<>();
	  
	  
	  Iterator<Point> sp_it = points.iterator();
	  Point p = sp_it.next();
	  while(sp_it.hasNext()){
		  Point sp = sp_it.next();
		  if(sp.x < p.x)
			  p = sp;
	  }
	  
	  Point first = p;
	  
	  do{
		  enveloppe.add(p);
		  Point q = null;
		  for(Point sq : points){
			  if(!p.equals(sq))
			  {
				  q = sq;
				  break;
			  }
		  }
		  if(q == null)
			  return enveloppe;
		  for(Point sq : points){
			  double prod = prodVec(p, q, sq);
			  if(prod > 0)
				  continue;
			  else if(prod == 0){
				  if(distanceSquare(p, sq) > distanceSquare(p, q))
					  q = sq;
			  }
			  else
				  q = sq;
		  }
		  p = q;
	  } while(!p.equals(first));
	  
	  System.out.println("[Jarvis] OUT : " + enveloppe.size());
	  return enveloppe;
  }
  
  static private int square(int x){
	  return x * x;
  }
  
  static private int distanceSquare(Point p, Point q){
	  return square(q.x - p.x) + square(q.y - p.y);
  }

  
  PaireAntipodale diametre(ArrayList<PaireAntipodale> antipodales) {
	  System.out.println("diametre");
	  double dist_sq_diam = 0;
	  PaireAntipodale diam = null;
		
	  for(PaireAntipodale paire : antipodales){
		double dist_sq_paire = distanceSquare(paire.p, paire.q);
		
		if(dist_sq_diam < dist_sq_paire){
			dist_sq_diam = dist_sq_paire;
			diam = paire;
			
		}
		
	  }
	  return diam;
  }
  private double distanceSquareOrtho(Point p, Point q, Point s){
	  return Math.abs((q.x - p.x) * (s.y - p.y) -  ( q.y - p.y) * (s.x - p.x));
	  
//	  System.out.println("[distanceSquareOrtho] : " + (q.x - p.x));
//	  double a = (q.y - p.y);
//	  double b = -1;
//	  double c = p.y - a * p.x;
//	  return square(a*s.x + b*s.y + c) / distanceSquare(p, q);
  }

  ArrayList<PaireAntipodale> antipodales(ArrayList<Point> enveloppe){
	  ArrayList<PaireAntipodale> anti = new ArrayList<>();
    
	  int k = 1;
	  Point p = enveloppe.get(0);
	  Point q = enveloppe.get(enveloppe.size() - 1);
	  
	  double dk = distanceSquareOrtho(p, q, enveloppe.get(k));
	  double dkk = distanceSquareOrtho(p, q, enveloppe.get(k + 1));
	  while(k < enveloppe.size() - 1 && dk < dkk){
		  k += 1;
		  dk = distanceSquareOrtho(p, q, enveloppe.get(k));
		  dkk = distanceSquareOrtho(p, q, enveloppe.get(k + 1));
	  }
    
	  int i = 0;
    
	  while(i <= k && k + 1 < enveloppe.size()){
		  p = enveloppe.get(i);
		  q = enveloppe.get(i + 1);
		  dk = distanceSquareOrtho(p, q, enveloppe.get(k));
		  dkk = distanceSquareOrtho(p, q, enveloppe.get(k + 1));
		  while(k + 1 < enveloppe.size() && dk < dkk){
			  anti.add(new PaireAntipodale(p, enveloppe.get(k)));
			  k += 1;
		  }
		  anti.add(new PaireAntipodale(p, enveloppe.get(k)));
		  i += 1;
	  }
	  return anti;
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
  private double prodVec(Point a, Point b, Point p){
	  return (b.x - a.x) * (p.y - a.y) - (b.y - a.y) * (p.x - a.x);
  }

  
  public double scalarProduct(Point p , Point q) {
      return p.x * q.x + p.y * q.y;
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
	  System.out.println("filtrerPointsAlignes");
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
		  Point maxY=tmp.get(0), minY=tmp.get(0);
		  for(int j  = 0; j < tmp.size(); j++) {
			  Point t = tmp.get(j);
			  if(t.getY() > maxY.getY()) {
				  maxY = t;
			  }
			  if(t.getY() < minY.getY()) {
				  minY = t;
			  }
		  }
		  for(int j  = 0; j < tmp.size(); j++) {
			  Point t = tmp.get(j);
			  if(t.y <maxY.y && t.y>minY.y) {
				  points.remove(t);
			  }
		  }
	  }
	  	 
	  for(int i  = 0; i < points.size(); i++){
		  Point p = points.get(i);
		  tmp.clear();
		  for(int j  = 0; j < points.size(); j++){
			  Point q = points.get(j);
			  if(p.getY() == q.getY()) {
				  tmp.add(q);
			  }
		  }
		  if(tmp.size()<=2)
			  continue;
		  Point maxX=tmp.get(0), minX=tmp.get(0);
		  for(int j = 0; j < tmp.size(); j++) {
			  Point t = tmp.get(j);
			  if(t.getX() > maxX.getX()) {
				  maxX = t;
			  }
			  if(t.getY() < minX.getY()) {
				  minX = t;
			  }
		  }
		  for(int j  = 0; j < tmp.size(); j++) {
			  Point t = tmp.get(j);
			  if(t.x < maxX.x && t.x > minX.x) {
				  points.remove(t);
			  }
		  }
	  }
	  
	  System.out.println("[filtre] len Point : " + points.size());
	  return points ;
  }
  
  private ArrayList<Point> rectangleMin(ArrayList<Point> points)
  {
	ArrayList<Point> enveloppe = enveloppeConvexeJarvis(points);
	
    ArrayList<PaireAntipodale> antipodales = antipodales(enveloppe);
	
    PaireAntipodale diam = diametre(antipodales);
    
    ArrayList<Point> rectangle = new ArrayList<>();

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
    
    double mdiam = (diam.q.y - diam.p.y) / (diam.q.x - diam.p.x);
    
    
    rectangle.add(new Point(diam.p.x,pn.y));
    rectangle.add(new Point(diam.q.x ,pn.y));
    rectangle.add(new Point(diam.q.x,ps.y));
    rectangle.add(new Point(diam.p.x,ps.y));

    return rectangle;
  }

  Point intersection(double m1, double p1, double m2, double p2)
  {
	  double x = m1;
	  double y = p2;
	  return new Point((int)x, (int)y);
  }

  public ArrayList<Point> filtrerPointsAlignesAkl(ArrayList<Point> points){	  
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

    // points = filtrerPointsAlignes((ArrayList<Point>)points.clone());
    // points = filtrerPointsAlignesAkl((ArrayList<Point>)points.clone());
    //points = filtrerPointsAlignes((ArrayList<Point>)points.clone());
	points = enveloppeConvexeJarvis(points);
    enveloppe =  rectangleMin(points);
	
	return points;
  }
  
}
