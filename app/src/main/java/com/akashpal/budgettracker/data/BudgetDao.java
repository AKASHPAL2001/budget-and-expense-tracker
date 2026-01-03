package com.akashpal.budgettracker.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BudgetDao {
    @Query("SELECT * FROM budgets ORDER BY category ASC, label ASC")
    LiveData<List<Budget>> getBudgets();

    @Insert(onConflict = OnConflictStrategy.ABORT)
    long insert(Budget budget);

    @Update
    void update(Budget budget);

    @Query("UPDATE budgets SET spent = :spent WHERE id = :id")
    void updateSpent(long id, double spent);

    @Query("SELECT * FROM budgets WHERE category = :category AND label = :label LIMIT 1")
    Budget findBudget(String category, String label);
}
