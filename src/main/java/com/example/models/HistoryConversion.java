package com.example.models;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class HistoryConversion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    String historyRec;

    public HistoryConversion(){
    }

    public HistoryConversion(String date, String cur1, String value1, String cur2, String value2){
        this.historyRec = date + " - " + cur1 + " " + value1 + " = " + cur2 + " " + value2;
    }

    public String getHistoryRec() {
        return historyRec;
    }

    public void setHistoryRec(String history) {
        this.historyRec = history;
    }
}
