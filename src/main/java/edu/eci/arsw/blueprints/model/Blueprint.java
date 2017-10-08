
package edu.eci.arsw.blueprints.model;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


public class Blueprint {

    private String author;
    
    private List<Point> points=Collections.synchronizedList(new ArrayList());
    
    private String name;
            
    public Blueprint(String author,String name,Point[] pnts){
        this.author=author;
        this.name=name;
        points=Arrays.asList(pnts);
    }
         
    public Blueprint(String author, String name){
        this.name=name;
        points=Collections.synchronizedList(new ArrayList());
    }

    public Blueprint() {
    }    
    
    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }
    
    public List<Point> getPoints() {
        return points;
    }
    
    public void addPoint(Point p){
        this.points.add(p);
    }

    @Override
    public String toString() {
        String puntos="";
        for (int i = 0; i < points.size(); i++) {
            Point punto=points.get(i);
            if(i!= 0){
                puntos = puntos + ",";
            }
            puntos=puntos+"{\"x\":"+punto.getX()+",\"y\":"+punto.getY()+"}";
            
        }
        return "{" + "\"author\":\"" + author + "\",\"points\":["+puntos+"],\"name\":\"" + name + "\"}";
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Blueprint other = (Blueprint) obj;
        if (!Objects.equals(this.author, other.author)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (this.points.size()!=other.points.size()){
            return false;
        }
        for (int i=0;i<this.points.size();i++){
            if (this.points.get(i)!=other.points.get(i)){
                return false;
            }
        }
        
        return true;
    }
    
    
    
}
