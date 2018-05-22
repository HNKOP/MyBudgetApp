package com.mybudgetapp.emilg.mybudgetapp.Items;

public class BudgetItem {
    String budgetname;
    Double budgetcount;
    String budgettype;
    int id;
    public BudgetItem(int id, String budgetname, String budgettype, Double budgetcount) {
        this.id = id;
        this.budgetname = budgetname;
        this.budgettype = budgettype; //предварительно список приглашенных
        this.budgetcount = budgetcount;
    }
    public BudgetItem() {

    }

    public String getBudgetname() {
        return budgetname;
    }

    public void setBudgetname(String budgetname) {
        this.budgetname = budgetname;
    }

    public Double getBudgetcount() {
        return budgetcount;
    }

    public void setBudgetcount(Double budgetcount) {
        this.budgetcount = budgetcount;
    }

    public String getBudgettype() {
        return budgettype;
    }

    public void setBudgettype(String budgettype) {
        this.budgettype = budgettype;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
