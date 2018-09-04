package com.example.a194_lab_1.healthy;

public class Weight {
    private String date;
    private String weight;
    private String status;

    public Weight (){}
    public Weight (String date, int weight, String status){
        this.date = date;
        this.weight = Integer.toString(weight);
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
