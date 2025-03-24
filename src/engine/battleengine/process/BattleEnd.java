package engine.battleengine.process;

import engine.battleengine.data.Battle;
import engine.entity.boats.Boat;
import engine.entity.boats.Fleet;
import engine.trading.Inventory;
import engine.trading.Resource;

import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.Set;

public class BattleEnd {

    private Battle battle;

    public BattleEnd(Battle battle) {
        this.battle = battle;
    }

    /*---- WIN/LOSE COND ----*/
    public boolean playerLose(){
        return battle.getBoatsInBattleA().getArrayListBoat().isEmpty()&&battle.getLstBoatsToPlace().isEmpty()&&!battle.isInPlacingMode();
    }

    public boolean playerWin(){
        return battle.getBoatsInBattleB().getArrayListBoat().isEmpty();
    }

    public boolean playerTie(){
        return playerLose()&&playerWin();
    }
    /* ------------------- */


    public void actualizeOriginalFleet(){
        ArrayList<Boat> teamA = this.battle.getTeamAOriginal().getArrayListBoat();
        ArrayList<Boat> teamB = this.battle.getTeamBOriginal().getArrayListBoat();
        teamA.clear();
        teamB.clear();
        //On vide les listes originales

        if(playerLose()){
            //On ajoute les bateaux qui ont survécu du joueur ennemi
            teamB.addAll(this.battle.getBoatsInBattleB().getArrayListBoat());
        }

        if(playerWin()){
            //On ajoute les bateaux qui n'ont pas été détruit par le joueur
            teamA.addAll(this.battle.getLstBoatsToPlace());
            teamA.addAll(this.battle.getBoatsInBattleA().getArrayListBoat());
        }


    }

    public void transferInventory(Fleet from, Fleet to){
        Inventory bigInventory = new Inventory();

        for(Boat boat : from.getArrayListBoat()){
            for(Resource resource : boat.getInventory().getContent().keySet()){
                if(bigInventory.getContent().containsKey(resource)){
                    bigInventory.getContent().put(resource, bigInventory.getContent().get(resource)+ boat.getInventory().getContent().get(resource));
                } else {
                    bigInventory.getContent().put(resource, boat.getInventory().getContent().get(resource));
                }
            }
        }

        boolean allTransferred = true;
        // Maintenant, on veut transférer les ressources vers les bateaux de la flotte 'to'
        for (Boat boatTo : to.getArrayListBoat()) {
            // Vérifier l'espace disponible sur ce bateau
            int availableSpace = boatTo.getInventory().getCapacity() - boatTo.getInventory().getContent().values().stream().mapToInt(Integer::intValue).sum();

            // Parcourir chaque ressource dans le grand inventaire temporaire (Big)
            for (Resource resource : bigInventory.getContent().keySet()) {
                int quantityInBig = bigInventory.getContent().get(resource);

                if (quantityInBig > 0 && availableSpace > 0) {
                    // Déterminer combien de cette ressource on peut transférer vers le bateau actuel
                    int quantityToTransfer = Math.min(quantityInBig, availableSpace);

                    // Mettre à jour l'inventaire du bateau de destination
                    if (boatTo.getInventory().getContent().containsKey(resource)) {
                        boatTo.getInventory().getContent().put(resource, boatTo.getInventory().getContent().get(resource) + quantityToTransfer);
                    } else {
                        boatTo.getInventory().getContent().put(resource, quantityToTransfer);
                    }

                    // Mettre à jour la quantité restante dans le grand inventaire
                    bigInventory.getContent().put(resource, quantityInBig - quantityToTransfer);

                    // Mettre à jour l'espace disponible
                    availableSpace -= quantityToTransfer;

                    // Si toute la ressource a été transférée, on arrête de transférer cette ressource
                    if (bigInventory.getContent().get(resource) == 0) {
                        break;
                    }
                }
            }

            for (int qty : bigInventory.getContent().values()) {
                if (qty > 0) {
                    allTransferred = false;
                    break;
                }
            }

            if (allTransferred) {
                break;
            }
        }

    }
















}
