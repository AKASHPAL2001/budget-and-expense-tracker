package com.akashpal.budgettracker.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.akashpal.budgettracker.data.Budget;
import com.akashpal.budgettracker.data.BudgetRepository;
import com.akashpal.budgettracker.data.Expense;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private final BudgetRepository repository;
    private final LiveData<List<Budget>> budgets;
    private final LiveData<List<Expense>> expenses;

    public MainViewModel(@NonNull Application application) {
        super(application);
        repository = new BudgetRepository(application.getApplicationContext());
        budgets = repository.getBudgets();
        expenses = repository.getExpenses();
    }

    public LiveData<List<Budget>> getBudgets() {
        return budgets;
    }

    public LiveData<List<Expense>> getExpenses() {
        return expenses;
    }

    public void addBudget(String category, String label, double amount) {
        repository.addBudget(category, label, amount);
    }

    public void addExpense(String category, String label, double amount) {
        repository.addExpense(category, label, amount);
    }
}
