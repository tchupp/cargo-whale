package com.cargowhale.docker.domain;

public class ChangeStatusRequest {
    private String status;

    public ChangeStatusRequest(){}

    public String getStatus(){
        return this.status;
    }

    public ChangeStatusRequest setStatus(String status){
        this.status = status;
        return this;
    }
}
