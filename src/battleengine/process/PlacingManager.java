package battleengine.process;


import battleengine.entity.Battle;
import engine.entity.boats.Boat;
import engine.entity.boats.Fleet;

/**
 * Manager de classe utile pour gérer la partie placement des {@link Fleet}.
 * @author Gauthier Defrance
 * @version 0.3
 */
public class PlacingManager {

    private Battle battle;
    
    public PlacingManager(Battle battle) {
        this.battle = battle;
    }

    /**
     * Méthode qui essaye de placer un {@link Boat} stocké en mémoire dans {@link Battle}.
     * Le {@link Boat} s'il peut être placé sera placé aux coordonnées
     * ou il se trouve actuellement.
     *
     * @return boolean renvoi un {@link Boolean} indiquant si l'opération a réussi ou non.
     */
    public boolean tryPlaceCurrentBoat() {
        if(this.tryPlaceBoat(this.battle.getCurrentBoat())){
            this.battle.setCurrentBoat(null);
            return true;
        }
        return false;
    }

    /**
     * Méthode qui essaye de placer un {@link Boat} donné en paramètre.
     * Le {@link Boat} s'il peut être placé sera placé aux coordonnées
     * ou il se trouve actuellement.
     *
     * @param boat le {@link Boat} à essayer de placer
     * @return boolean renvoi un {@link Boolean} indiquant si l'opération a réussi ou non.
     */
    public boolean tryPlaceBoat(Boat boat) {
        if(this.battle.getSpawnzone().isPlaceable(boat)){
            this.battle.getBoatsToPlace().remove(boat);
            this.battle.getBoatsCurrentlyBeingPlaced().add(boat);
            return true;
        }
        return false;
    }

    /**
     * Annule la phase de placement des bateaux en cours et retourne tous les bateaux
     * actuellement placés dans la liste des bateaux à placer.
     * Cette méthode réinitialise la phase de placement, permettant de recommencer
     * l'opération de placement si nécessaire.
     */
    public void cancelPlacing(){
        for(Boat boat : this.battle.getBoatsCurrentlyBeingPlaced().getArrayListBoat() ){
            this.battle.getBoatsToPlace().add(boat);
        }
        clearBoatsCurrentlyBeingPlaced();
    }

    /**
     * Confirme la bataille en ajoutant tous les bateaux actuellement placés par le joueur
     * à la liste des bateaux en bataille. Après cette opération, les bateaux en cours de
     * placement sont retirés de la phase de placement.
     */
    public void confirmBattle(){
        if(!this.battle.getBoatsCurrentlyBeingPlaced().getArrayListBoat().isEmpty()){
            for(Boat boat : this.battle.getBoatsCurrentlyBeingPlaced().getArrayListBoat() ){
                this.battle.getBoatsInBattleA().add(boat);
            }
            clearBoatsCurrentlyBeingPlaced();
        }
    }

    /**
     * Méthode vidant la liste des bateaux en cours de placement.
     */
    private void clearBoatsCurrentlyBeingPlaced(){
        this.battle.getBoatsCurrentlyBeingPlaced().getArrayListBoat().clear();
    }




}
