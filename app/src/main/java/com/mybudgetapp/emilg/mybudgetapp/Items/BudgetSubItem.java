package com.mybudgetapp.emilg.mybudgetapp.Items;

public class BudgetSubItem {
    int Id;
     String Name;
     Double Value;
     Boolean Type;

    public BudgetSubItem(int id, String name, Double value, Boolean type) {
        Id = id;
        Name = name;
        Value = value;
        Type = type;
    }

    public BudgetSubItem() {

    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Double getValue() {
        return Value;
    }

    public void setValue(Double value) {
        Value = value;
    }

    public Boolean getType() {
        return Type;
    }

    public void setType(Boolean type) {
        Type = type;
    }
}
