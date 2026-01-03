package com.akashpal.budgettracker.data;

import android.content.Context;
import android.text.TextUtils;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BudgetRepository {
    private final BudgetDao budgetDao;
    private final ExpenseDao expenseDao;
    private final ExecutorService executorService;

    public BudgetRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        this.budgetDao = db.budgetDao();
        this.expenseDao = db.expenseDao();
        this.executorService = Executors.newSingleThreadExecutor();
    }

    public androidx.lifecycle.LiveData<java.util.List<Budget>> getBudgets() {
        return budgetDao.getBudgets();
    }

    public androidx.lifecycle.LiveData<java.util.List<Expense>> getExpenses() {
        return expenseDao.getExpenses();
    }

    public void addBudget(String category, String label, double amount) {
        executorService.execute(() -> {
            if (TextUtils.isEmpty(category) || TextUtils.isEmpty(label) || amount <= 0) {
                return;
            }
            Budget existing = budgetDao.findBudget(category, label);
            if (existing == null) {
                budgetDao.insert(new Budget(category.trim(), label.trim(), amount));
            } else {
                existing.setMonthlyLimit(amount);
                budgetDao.update(existing);
            }
        });
    }

    public void addExpense(String category, String label, double amount) {
        executorService.execute(() -> {
            if (TextUtils.isEmpty(category) || TextUtils.isEmpty(label) || amount <= 0) {
                return;
            }
            long now = System.currentTimeMillis();
            expenseDao.insert(new Expense(category.trim(), label.trim(), amount, now));
            Budget budget = budgetDao.findBudget(category, label);
            if (budget != null) {
                double newSpent = budget.getSpent() + amount;
                budgetDao.updateSpent(budget.getId(), newSpent);
            }
        });
    }
}
