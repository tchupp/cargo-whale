package com.cargowhale.docker.domain;

/**
 * Created by nick on 25/10/16.
 */
public class ChangeStatusResponse {
    private String name;

    public ChangeStatusResponse(){}

    public String getName(){
        return this.name;
    }

    public ChangeStatusResponse setName(String status){
        this.name = status;
        return this;
    }
}
