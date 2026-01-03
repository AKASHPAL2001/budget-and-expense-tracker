package com.akashpal.budgettracker.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.akashpal.budgettracker.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MainViewModel viewModel;
    private BudgetAdapter budgetAdapter;
    private ExpenseAdapter expenseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        setupRecyclerViews();
        setupActions();
        observeData();
    }

    private void setupRecyclerViews() {
        budgetAdapter = new BudgetAdapter(this);
        binding.budgetRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.budgetRecyclerView.setAdapter(budgetAdapter);
        binding.budgetRecyclerView.addItemDecoration(new SpacingItemDecoration(12));

        expenseAdapter = new ExpenseAdapter(this);
        binding.expenseRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.expenseRecyclerView.setAdapter(expenseAdapter);
        binding.expenseRecyclerView.addItemDecoration(new SpacingItemDecoration(8));
    }

    private void setupActions() {
        binding.btnAddBudget.setOnClickListener(v -> {
            String category = textFromInput(binding.inputBudgetCategory);
            String label = textFromInput(binding.inputBudgetLabel);
            double amount = parseDouble(binding.inputBudgetAmount.getText());

            if (validateBudgetInputs(category, label, amount)) {
                viewModel.addBudget(category, label, amount);
                Toast.makeText(this, "Budget saved", Toast.LENGTH_SHORT).show();
                clearBudgetInputs();
            }
        });

        binding.btnAddExpense.setOnClickListener(v -> {
            String category = textFromInput(binding.inputExpenseCategory);
            String label = textFromInput(binding.inputExpenseLabel);
            double amount = parseDouble(binding.inputExpenseAmount.getText());

            if (validateExpenseInputs(category, label, amount)) {
                viewModel.addExpense(category, label, amount);
                Toast.makeText(this, "Expense added", Toast.LENGTH_SHORT).show();
                clearExpenseInputs();
            }
        });
    }

    private void observeData() {
        viewModel.getBudgets().observe(this, budgets -> {
            budgetAdapter.setData(budgets);
            binding.emptyBudgets.setVisibility(budgets == null || budgets.isEmpty() ? View.VISIBLE : View.GONE);
        });

        viewModel.getExpenses().observe(this, expenses -> {
            expenseAdapter.setData(expenses);
            binding.emptyExpenses.setVisibility(expenses == null || expenses.isEmpty() ? View.VISIBLE : View.GONE);
        });
    }

    private boolean validateBudgetInputs(String category, String label, double amount) {
        boolean valid = true;
        if (TextUtils.isEmpty(category)) {
            binding.inputBudgetCategory.setError("Category required");
            valid = false;
        }
        if (TextUtils.isEmpty(label)) {
            binding.inputBudgetLabel.setError("Label required");
            valid = false;
        }
        if (amount <= 0) {
            binding.inputBudgetAmount.setError("Enter a valid amount");
            valid = false;
        }
        return valid;
    }

    private boolean validateExpenseInputs(String category, String label, double amount) {
        boolean valid = true;
        if (TextUtils.isEmpty(category)) {
            binding.inputExpenseCategory.setError("Category required");
            valid = false;
        }
        if (TextUtils.isEmpty(label)) {
            binding.inputExpenseLabel.setError("Label required");
            valid = false;
        }
        if (amount <= 0) {
            binding.inputExpenseAmount.setError("Enter a valid amount");
            valid = false;
        }
        return valid;
    }

    private void clearBudgetInputs() {
        binding.inputBudgetCategory.setText("");
        binding.inputBudgetLabel.setText("");
        binding.inputBudgetAmount.setText("");
    }

    private void clearExpenseInputs() {
        binding.inputExpenseCategory.setText("");
        binding.inputExpenseLabel.setText("");
        binding.inputExpenseAmount.setText("");
    }

    private String textFromInput(android.widget.EditText editText) {
        return editText.getText() != null ? editText.getText().toString().trim() : "";
    }

    private double parseDouble(CharSequence text) {
        try {
            return Double.parseDouble(text != null ? text.toString() : "0");
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
