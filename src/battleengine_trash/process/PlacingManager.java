package battleengine_trash.process;

import battleengine_trash.engine.Battle;
import config.GameConfiguration;
import engine.entity.boats.Boat;

/**
 * Class that manage the placing of the boats during the battle phase.
 * @author Gauthier Defrance
 * @version 0.2
 */
public class PlacingManager {

    private Battle battle;

    /**
     * Constructor of class that takes BoatToPlace as a parameters,
     * it's a Fleet of boats.
     * @param battle
     */
    public PlacingManager(Battle battle) {
        this.battle = battle;
    }

    /**
     * Méthode qui tente de sélectionner un bateau passé en paramètres.
     * Si le bateau était déjà sélectionné, le bateau reprend sa position hors map
     * et la valeur du bateau actuellement sélectionné passe à null.
     * @param selectedBoat Le bateau que vous essayez de sélectionner
     */
    public void selectBoat(Boat selectedBoat) {
        Boat currentBoat = this.battle.getPlacing().getCurrentBoat();
        if(selectedBoat!=null) {
            if (selectedBoat != currentBoat && this.battle.getPlacing().getBoatToPlace().getArrayListBoat().contains(selectedBoat)) {
                this.battle.getPlacing().setCurrentBoat(selectedBoat);
            } else if (currentBoat!=null){
                currentBoat.setPosition(GameConfiguration.DEFAULT_BOAT_POS_X, GameConfiguration.DEFAULT_BOAT_POS_Y);
                this.battle.getPlacing().setCurrentBoat(null);
            }
        }
    }

    /**
     * /!\ ne vérifie pas la collision avec les bateaux en jeu, à modifier
     *
     * Méthode vérifiant si le bateau actuellement sélectionné est plaçable.
     * @return Boolean
     */
    public boolean isPlacable() {
        Boat currentBoat = this.battle.getPlacing().getCurrentBoat();
        if (currentBoat==null) return false;
        return this.battle.getPlacing().getSpawnzone().isPlaceable(currentBoat);
    }


    /**
     * Méthode ajoutant le bateau actuellement sélectionné à la liste des bateaux en cours de placement.
     */
    public void placeBoat() {
        Boat currentBoat = this.battle.getPlacing().getCurrentBoat();
        this.battle.getPlacing().getBoatCurrentlyBeingPlaced().add(currentBoat);
        this.battle.getPlacing().getButtonPanel().remove(this.battle.getPlacing().getButtonHashMap().get(currentBoat));
        this.battle.getPlacing().getBoatToPlace().remove(currentBoat);
        this.battle.getPlacing().setCurrentBoat(null);
        this.battle.getPlacingJPanel().updateUI(); //update des boutons
    }

    /**
     * Méthode qui vide la liste des bateaux en cours de placement et les ajoute à la liste des bateaux placés.
     */
    public void confirmPlacing(){
        for(Boat boat : this.battle.getPlacing().getBoatCurrentlyBeingPlaced().getArrayListBoat()) {
            this.battle.getPlacing().getPlacedFleet().add(boat);
        }
        this.battle.getPlacing().getBoatCurrentlyBeingPlaced().getArrayListBoat().clear();
    }

    /**
     * Méthode qui vide la liste des bateaux en cours de placement et les ajoute à la liste des bateaux à placer.
     */
    public void cancelPlacing(){
        for(Boat boat : this.battle.getPlacing().getBoatCurrentlyBeingPlaced().getArrayListBoat()) {
            this.battle.getPlacing().getBoatToPlace().add(boat);
        }
        this.battle.getPlacing().getBoatCurrentlyBeingPlaced().getArrayListBoat().clear();
        this.battle.getPlacingJPanel().buttonPanelFleetAdding();
        this.battle.getPlacing().getSpawnzone().getPlacedList().getArrayListBoat().clear();
        this.battle.getPlacingJPanel().updateUI();
    }

}
