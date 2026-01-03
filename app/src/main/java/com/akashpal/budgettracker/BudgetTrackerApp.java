package com.akashpal.budgettracker;

import android.app.Application;

import com.akashpal.budgettracker.data.AppDatabase;

public class BudgetTrackerApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize database early so it is ready when activities/viewmodels need it
        AppDatabase.getInstance(this);
    }
}
