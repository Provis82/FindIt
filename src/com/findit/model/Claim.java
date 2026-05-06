package com.findit.model;

public class Claim {
    private int id;
    private int itemId;
    private int claimantId;
    private String proofAnswer;
    private String status;

    public Claim() {}

    public Claim(int itemId, int claimantId, String proofAnswer) {
        this.itemId = itemId;
        this.claimantId = claimantId;
        this.proofAnswer = proofAnswer;
        this.status = "pending";
    }

    // Getters
    public int getId() { return id; }
    public int getItemId() { return itemId; }
    public int getClaimantId() { return claimantId; }
    public String getProofAnswer() { return proofAnswer; }
    public String getStatus() { return status; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setItemId(int itemId) { this.itemId = itemId; }
    public void setClaimantId(int claimantId) { this.claimantId = claimantId; }
    public void setProofAnswer(String proofAnswer) { this.proofAnswer = proofAnswer; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "Claim #" + id + " | Item: " + itemId + " | Status: " + status;
    }
}