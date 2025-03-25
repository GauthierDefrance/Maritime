package engine.battleengine.utilities;

import config.GameConfiguration;
import engine.battleengine.data.Battle;
import engine.entity.boats.*;
import engine.graph.GraphPoint;

import java.awt.*;
import java.util.HashMap;

/**
 * @author Gauthier Defrance
 * @version 0.1
 */
public final class DeepCopy {
    /**
     * Classe permettant de faire une copie profonde totalement différente et sans aucune références partagées
     * @param input une fleet remplies d'élements.
     * @return Fleet une fleet qui est une copie de celle passée en paramètres.
     */
    public static Fleet copyFleet(Fleet input, HashMap<Boat,Boat> OriginalToCopyMap) {
        if (input==null|| input.getArrayListBoat().isEmpty()) return null;
        Fleet output = new Fleet();
        Boat tmp = new Standard("init","none", new GraphPoint(new Point(-1000,-1000),null));
        for(Boat boat : input.getArrayListBoat()){
            switch (boat.getClass().getName()) {
                case "engine.entity.boats.Standard": {
                    //Il est extrêmement improbable qu'on est besoin de modifier durant la bataille le nom ou la couleur du bateau.
                    //Mais on ne prend aucun risque ici pour le moment.
                    tmp = new Standard(new String(boat.getName()),new String(boat.getColor()), new GraphPoint(new Point(-1000,-1000),null));
                    break;
                }
                case "engine.entity.boats.Fodder": {
                    tmp = new Fodder(new String(boat.getName()),new String(boat.getColor()), new GraphPoint(new Point(-1000,-1000),null));
                    break;
                }
                case "engine.entity.boats.Merchant": {
                    tmp = new Merchant(new String(boat.getName()),new String(boat.getColor()), new GraphPoint(new Point(-1000,-1000),null));
                    break;
                }
                case "engine.entity.boats.Military": {
                    tmp = new Military(new String(boat.getName()),new String(boat.getColor()), new GraphPoint(new Point(-1000,-1000),null));
                    break;
                }
                default: {
                    System.err.println(boat.getClass().getName()+" non reconnu par la deepcopy !");
                    continue;
                }
            }
            tmp.setNextGraphPoint(null);
            tmp.setOldGraphPoint(null);
            output.add(tmp);
            OriginalToCopyMap.put(tmp,boat);
        }
        return output;
    }
}
