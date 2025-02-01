package marine_trash.map;
import java.util.ArrayList;

public class Map {
    private ArrayList<Pointt> points;
    public Map() {
        points = new ArrayList<Pointt>();
    }

    //getters
    public void addPoint(int ID, String NAME, int x, int y){points.add(new Pointt(ID,NAME,x,y));}
    public void addPoint(Pointt p){points.add(p);}

    public Pointt getPoint(int ID){return points.get(ID);}
    public ArrayList<Pointt> getPoints(){return points;}

    //setters
    public void remPoint(int ID){points.remove(ID);}
    public void setPoints(ArrayList<Pointt> points){this.points = points;}


}
