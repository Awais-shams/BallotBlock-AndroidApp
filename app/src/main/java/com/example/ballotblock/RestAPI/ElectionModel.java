package com.example.ballotblock.RestAPI;

public class ElectionModel {
    private int id;
    private String uuid, name, startDate, endDate, designation, status, contractAddress, deployTxHash;
    private String organizationName;

//    Constructor
    public ElectionModel(int id, String uuid, String name, String startDate, String endDate, String designation, String status, String contractAddress, String deployTxHash, String organizationName) {
        this.id = id;
        this.uuid = uuid;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.designation = designation;
        this.status = status;
        this.contractAddress = contractAddress;
        this.deployTxHash = deployTxHash;
        this.organizationName = organizationName;
    }

//    Getter Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContractAddress() {
        return contractAddress;
    }

    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }

    public String getDeployTxHash() {
        return deployTxHash;
    }

    public void setDeployTxHash(String deployTxHash) {
        this.deployTxHash = deployTxHash;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }
}
