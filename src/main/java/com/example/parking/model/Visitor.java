package com.example.parking.model;

public class Visitor extends Client {
    private String visitorId;
    private String visitingPurpose;

    public Visitor(String id, String name, String email, String visitorId, String visitingPurpose) {
        super(id, name, email);
        this.visitorId = visitorId;
        this.visitingPurpose = visitingPurpose;
    }

    public Visitor(String id, String name, String email) {
        //TODO Auto-generated constructor stub
    }

    public String getVisitorId() {
        return visitorId;
    }

    public String getVisitingPurpose() {
        return visitingPurpose;
    }

    @Override
    public String getType() {
        return "VISITOR";
    }
} 