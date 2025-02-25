package battleengine_trash.process;

import engine.entity.boats.Fleet;

import javax.swing.*;


/**
 *
 * @author Gauthier Defrance
 * @version 0.1
 */
public class FightManager {
    private Fleet A;
    private Fleet B;

    public FightManager(Fleet A, Fleet B){
        this.A = A;
        this.B = B;
    }

    public Fleet getA() { return A;}
    public void setA(Fleet A) { this.A = A; }

    public Fleet getB() { return B;}
    public void setB(Fleet B) { this.B = B;}


}
