package com.example.models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ExchangeRate {

    @Id
    private String id;

    private String date;
    private String nominal;
    private String value;

    public ExchangeRate(){
    }

    public ExchangeRate(String id, String date, String nominal, String value){
        this.id = id;
        this.date = date;
        this.nominal = nominal;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNominal() {
        return nominal;
    }

    public void setNominal(String nominal) {
        this.nominal = nominal;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
