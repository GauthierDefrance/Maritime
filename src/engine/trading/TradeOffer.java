package engine.trading;

import java.util.Random;

public class TradeOffer {
    private Resource proposedResource;
    private Resource targetedResource;
    private int proposedNumber;
    private int targetedNumber;
    private int relationship;

    private TradeOffer(int relationship) {
        this.relationship = relationship;
    }

    public TradeOffer create(int relationship) {
        return new TradeOffer(relationship);
    }

    // Getters

    public Resource getProposed() {
        return proposedResource;
    }

    public Resource getTargetedResource() {
        return targetedResource;
    }

    public int getProposedNumber() {
        return proposedNumber;
    }

    public int getTargetedNumber() {
        return targetedNumber;
    }

    public int getRelationship() {
        return relationship;
    }

    // Setters

    public void setProposed(Resource proposed) {
        this.proposedResource = proposed;
    }

    public void setTargetedResource(Resource targetedResource) {
        this.targetedResource = targetedResource;
    }

    public void setProposedNumber(int proposedNumber) {
        this.proposedNumber = proposedNumber;
    }

    public void setTargetedNumber(int targetedNumber) {
        this.targetedNumber = targetedNumber;
    }

    public void setRelationship(int relationship) {
        this.relationship = relationship;
    }

    // Utilities

    /**
     * Calculate the offered value of a side in a trade
     * @param resource Object designated by the trade
     * @return indicative value of the offer
     */
    private int offeredValue(Resource resource, int correspondingNumber) {
        return resource.getValue() * correspondingNumber;
    }

    /**
     * Calculate the ratio between the value of proposed and targeted resources
     * @return ratio
     */
    public double getRatio() {
        return (double) offeredValue(proposedResource, proposedNumber) / offeredValue(targetedResource, targetedNumber);
    }
}
