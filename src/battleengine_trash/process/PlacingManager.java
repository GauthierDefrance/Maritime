package battleengine_trash.process;

import battleengine_trash.engine.SpawnZone;
import config.GameConfiguration;
import engine.entity.boats.Boat;
import engine.entity.boats.Fleet;

/**
 * Class that manage the placing of the boats during the battle phase.
 * @author Gauthier Defrance
 * @version 0.1
 */
public class PlacingManager {

    private Fleet BoatToPlace;
    private Fleet BoatCurrentlyBeingPlaced;
    private Fleet PlacedFleet;  //à peut être enlever

    private Boat currentBoat;

    private SpawnZone spawnzone;
    private SpawnZone spawnzoneEnnemy;

    /**
     * Constructor of class that takes BoatToPlace as a parameters,
     * it's a Fleet of boats.
     * @param BoatToPlace
     */
    public PlacingManager(Fleet BoatToPlace) {

        this.spawnzone = SpawnZoneFactory.buildDefaultSpawnZone();
        this.spawnzoneEnnemy = SpawnZoneFactory.buildDefaultEnnemySpawnZone();

        this.BoatToPlace = BoatToPlace;
        this.BoatCurrentlyBeingPlaced = new Fleet();
        this.PlacedFleet = new Fleet();

    }

    public SpawnZone getSpawnzone() { return spawnzone; }
    public SpawnZone getSpawnzoneEnnemy() { return spawnzoneEnnemy; }

    public Boat getCurrentBoat() { return currentBoat; }
    public Fleet getBoatToPlace() { return BoatToPlace; }
    public Fleet getPlacedFleet() { return PlacedFleet; }
    public Fleet getBoatCurrentlyBeingPlaced() { return BoatCurrentlyBeingPlaced; }

    public void setCurrentBoat(Boat currentBoat) { this.currentBoat = currentBoat; }
    public void setBoatToPlace(Fleet BoatToPlace) { this.BoatToPlace = BoatToPlace; }


    /**
     * Méthode qui tente de sélectionner un bateau passé en paramètres.
     * Si le bateau était déjà sélectionné, le bateau reprend sa position hors map
     * et la valeur du bateau actuellement sélectionné passe à null.
     * @param selectedBoat Le bateau que vous essayez de sélectionner
     */
    public void selectBoat(Boat selectedBoat) {
        if(selectedBoat!=null && selectedBoat!=currentBoat && BoatToPlace.getArrayListBoat().contains(selectedBoat)) {
            this.currentBoat = selectedBoat;
        }
        else {
            currentBoat.setPosition(GameConfiguration.DEFAULT_BOAT_POS_X,GameConfiguration.DEFAULT_BOAT_POS_Y);
            this.currentBoat = null;
        }
    }

    /**
     * /!\ ne vérifie pas la collision avec les bateaux en jeu, à modifier
     *
     * Méthode vérifiant si le bateau actuellement sélectionné est plaçable.
     * @return Boolean
     */
    public boolean isPlacable() {
        if (currentBoat==null) return false;
        return spawnzone.isPlaceable(currentBoat);
    }


    /**
     * Méthode ajoutant le bateau actuellement sélectionné à la liste des bateaux en cours de placement.
     */
    public void placeBoat() {
        BoatCurrentlyBeingPlaced.add(currentBoat);
        BoatToPlace.remove(currentBoat);
        this.currentBoat = null;
    }

    /**
     * Méthode qui vide la liste des bateaux en cours de placement et les ajoute à la liste des bateaux placés.
     */
    public void confirmPlacing(){
        for(Boat boat : BoatCurrentlyBeingPlaced.getArrayListBoat()) {
            PlacedFleet.add(boat);
        }
        BoatCurrentlyBeingPlaced.getArrayListBoat().clear();
    }

    /**
     * Méthode qui vide la liste des bateaux en cours de placement et les ajoute à la liste des bateaux à placer.
     */
    public void cancelPlacing(){
        for(Boat boat : BoatCurrentlyBeingPlaced.getArrayListBoat()) {
            BoatToPlace.add(boat);
        }
        BoatCurrentlyBeingPlaced.getArrayListBoat().clear();
    }

}
