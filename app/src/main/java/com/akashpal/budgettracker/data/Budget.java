package com.akashpal.budgettracker.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "budgets", indices = {@Index(value = {"category", "label"}, unique = true)})
public class Budget {
    @PrimaryKey(autoGenerate = true)
    private long id;

    @NonNull
    private String category;

    @NonNull
    private String label;

    @ColumnInfo(name = "monthly_limit")
    private double monthlyLimit;

    private double spent;

    public Budget(@NonNull String category, @NonNull String label, double monthlyLimit) {
        this.category = category;
        this.label = label;
        this.monthlyLimit = monthlyLimit;
        this.spent = 0;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NonNull
    public String getCategory() {
        return category;
    }

    public void setCategory(@NonNull String category) {
        this.category = category;
    }

    @NonNull
    public String getLabel() {
        return label;
    }

    public void setLabel(@NonNull String label) {
        this.label = label;
    }

    public double getMonthlyLimit() {
        return monthlyLimit;
    }

    public void setMonthlyLimit(double monthlyLimit) {
        this.monthlyLimit = monthlyLimit;
    }

    public double getSpent() {
        return spent;
    }

    public void setSpent(double spent) {
        this.spent = spent;
    }
}
