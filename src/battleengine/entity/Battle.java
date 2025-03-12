package battleengine.entity;
import battleengine.tools.SpawnZoneFactory;
import battleengine.tools.DeepCopy;
import engine.entity.boats.Boat;
import engine.entity.boats.Fleet;

/**
 * Classe de donnée, stockant les données relative à un combat.
 * @author Gauthier Defrance
 * @version 0.3
 */
public class Battle {
    //---- Les flottes original ----
    private Fleet originalA;
    private Fleet originalB;

    //---- Les flottes en jeux ----
    private Fleet teamA;
    private Fleet teamB;

    //---- Les flottes en bataille ----
    private Fleet BoatsInBattleA;
    private Fleet BoatsInBattleB;

    //---- Les flottes en cours de placements ----
    private Fleet BoatsToPlace;
    private Fleet BoatsCurrentlyBeingPlaced;

    //---- Le bateau actuellement sélectionné ----
    private Boat currentBoat;

    //---- Les zones de spawn ----
    private SpawnZone spawnzone;
    private SpawnZone spawnzoneEnnemy;

    //---- Boolean état du jeu ----
    private boolean isInPlacingMode;

    /**
     * Constructeur de la classe Battle, prend en paramètres deux flottes qui
     * participeront à la bataille.
     *
     * @param fleetA La première flotte impliquée dans la bataille. Il s'agit
     *               d'un objet de type {@link Fleet}, représentant l'une des
     *               forces combattantes dans le jeu.
     * @param fleetB La deuxième flotte impliquée dans la bataille. Il s'agit
     *               également d'un objet de type {@link Fleet}, représentant
     *               l'autre force combattante dans le jeu.
     */
    public Battle(Fleet fleetA, Fleet fleetB) {
        //---- Sauvegarde de la référence des flottes originales ----
        this.originalA = fleetA;
        this.originalB = fleetB;

        //---- Copie profonde des références dans un nouvel objet pour ne pas modif l'original ----
        this.teamA = DeepCopy.copyFleet(fleetA);
        this.teamB = DeepCopy.copyFleet(fleetB);

        //---- Listes des bateaux à placer et en cours de placement ----
        this.BoatsToPlace = this.teamA;
        this.BoatsCurrentlyBeingPlaced = new Fleet();

        //---- Listes des bateaux en train de se combattre ----
        this.BoatsInBattleA= new Fleet();
        this.BoatsInBattleB = new Fleet();

        //---- Les zones de spawn allié et ennemi ----
        this.spawnzone = SpawnZoneFactory.buildDefaultSpawnZone();
        this.spawnzoneEnnemy = SpawnZoneFactory.buildDefaultEnnemySpawnZone();

        //---- Boolean indiquant le mode de jeu actuel ----
        this.isInPlacingMode = true;

    }

    //---- Getters ----

    /**
     * Retourne la flotte originale de l'équipe A.
     *
     * @return la flotte originale de l'équipe A.
     */
    public Fleet getOriginalA() { return originalA; }

    /**
     * Retourne la flotte originale de l'équipe B.
     *
     * @return la flotte originale de l'équipe B.
     */
    public Fleet getOriginalB() { return originalB; }

    /**
     * Retourne la flotte de l'équipe A actuellement en jeu.
     *
     * @return la flotte de l'équipe A en jeu.
     */
    public Fleet getTeamA() { return teamA; }

    /**
     * Retourne la flotte de l'équipe B actuellement en jeu.
     *
     * @return la flotte de l'équipe B en jeu.
     */
    public Fleet getTeamB() { return teamB; }

    /**
     * Retourne la flotte des bateaux en bataille pour l'équipe A.
     *
     * @return la flotte des bateaux en bataille pour l'équipe A.
     */
    public Fleet getBoatsInBattleA() { return BoatsInBattleA; }

    /**
     * Retourne la flotte des bateaux en bataille pour l'équipe B.
     *
     * @return la flotte des bateaux en bataille pour l'équipe B.
     */
    public Fleet getBoatsInBattleB() { return BoatsInBattleB; }

    /**
     * Retourne la flotte des bateaux à placer pour l'utilisateur.
     *
     * @return la flotte des bateaux à placer.
     */
    public Fleet getBoatsToPlace() { return BoatsToPlace; }

    /**
     * Retourne la flotte des bateaux actuellement en cours de placement.
     *
     * @return la flotte des bateaux en cours de placement.
     */
    public Fleet getBoatsCurrentlyBeingPlaced() { return BoatsCurrentlyBeingPlaced; }

    /**
     * Retourne le bateau actuellement sélectionné ou en jeu.
     *
     * @return le bateau actuellement sélectionné.
     */
    public Boat getCurrentBoat() { return currentBoat; }

    /**
     * Retourne la zone de spawn de l'utilisateur.
     *
     * @return la zone de spawn de l'utilisateur.
     */
    public SpawnZone getSpawnzone() { return spawnzone; }

    /**
     * Retourne la zone de spawn de l'ennemi.
     *
     * @return la zone de spawn de l'ennemi.
     */
    public SpawnZone getSpawnzoneEnnemy() { return spawnzoneEnnemy; }

    /**
     * Retourne si le mode de placement est activé (true) ou non (false).
     *
     * @return true si le mode de placement est activé, sinon false.
     */
    public boolean isInPlacingMode() { return isInPlacingMode; }

    //---- Setters ----
    /**
     * Définit la flotte originale de l'équipe A.
     *
     * @param fleet la flotte à définir pour l'équipe A.
     */
    public void setOriginalA(Fleet fleet) { this.originalA = fleet; }

    /**
     * Définit la flotte originale de l'équipe B.
     *
     * @param fleet la flotte à définir pour l'équipe B.
     */
    public void setOriginalB(Fleet fleet) { this.originalB = fleet; }

    /**
     * Définit la flotte de l'équipe A actuellement en jeu.
     *
     * @param fleet la flotte à définir pour l'équipe A.
     */
    public void setTeamA(Fleet fleet) { this.teamA = fleet; }

    /**
     * Définit la flotte de l'équipe B actuellement en jeu.
     *
     * @param fleet la flotte à définir pour l'équipe B.
     */
    public void setTeamB(Fleet fleet) { this.teamB = fleet; }

    /**
     * Définit la flotte des bateaux en bataille pour l'équipe A.
     *
     * @param fleet la flotte des bateaux en bataille pour l'équipe A.
     */
    public void setBoatsInBattleA(Fleet fleet) { this.BoatsInBattleA = fleet; }

    /**
     * Définit la flotte des bateaux en bataille pour l'équipe B.
     *
     * @param fleet la flotte des bateaux en bataille pour l'équipe B.
     */
    public void setBoatsInBattleB(Fleet fleet) { this.BoatsInBattleB = fleet; }

    /**
     * Définit la flotte des bateaux à placer pour l'utilisateur.
     *
     * @param fleet la flotte des bateaux à placer.
     */
    public void setBoatsToPlace(Fleet fleet) { this.BoatsToPlace = fleet; }

    /**
     * Définit la flotte des bateaux actuellement en cours de placement.
     *
     * @param fleet la flotte des bateaux actuellement en cours de placement.
     */
    public void setBoatsCurrentlyBeingPlaced(Fleet fleet) { this.BoatsCurrentlyBeingPlaced = fleet; }

    /**
     * Définit le bateau actuellement sélectionné ou en jeu.
     *
     * @param boat le bateau à définir comme bateau actuel.
     */
    public void setCurrentBoat(Boat boat) { this.currentBoat = boat; }

    /**
     * Définit la zone de spawn de l'utilisateur.
     *
     * @param spawnzone la zone de spawn à définir pour l'utilisateur.
     */
    public void setSpawnzone(SpawnZone spawnzone) { this.spawnzone = spawnzone; }

    /**
     * Définit la zone de spawn de l'ennemi.
     *
     * @param spawnzone la zone de spawn à définir pour l'ennemi.
     */
    public void setSpawnzoneEnnemy(SpawnZone spawnzone) { this.spawnzoneEnnemy = spawnzone; }

    /**
     * Définit si le mode de placement est activé ou non.
     *
     * @param bool true pour activer le mode placement, false pour le désactiver.
     */
    public void setPlacingMode(boolean bool) { this.isInPlacingMode = bool; }
}