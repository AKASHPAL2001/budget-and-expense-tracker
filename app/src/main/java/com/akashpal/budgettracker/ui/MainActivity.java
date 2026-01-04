package com.akashpal.budgettracker.ui;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.akashpal.budgettracker.databinding.ActivityMainBinding;
import com.akashpal.budgettracker.data.Expense;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MainViewModel viewModel;
    private final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        setupActions();
        observeData();
    }

    private void setupActions() {
        binding.btnAddExpense.setOnClickListener(v ->
                Toast.makeText(this, "Add expense action", Toast.LENGTH_SHORT).show());

        binding.btnAddIncome.setOnClickListener(v ->
                Toast.makeText(this, "Add income action", Toast.LENGTH_SHORT).show());

        binding.periodChip.setOnClickListener(v ->
                Toast.makeText(this, "Change period", Toast.LENGTH_SHORT).show());
    }

    private void observeData() {
        viewModel.getExpenses().observe(this, this::updateExpenseSummary);
    }

    private void updateExpenseSummary(List<Expense> expenses) {
        double totalExpense = 0;
        int count = 0;
        if (expenses != null) {
            count = expenses.size();
            for (Expense e : expenses) {
                totalExpense += e.getAmount();
            }
        }

        double totalIncome = 0; // income tracking not yet implemented in data layer
        int incomeCount = 0;

        double netWorth = totalIncome - totalExpense;

        binding.textExpenseAmount.setText(currencyFormat.format(totalExpense));
        binding.textExpenseTransactions.setText(transactionLabel(count));
        binding.textExpenseSecondary.setText(transactionLabel(count));

        binding.textIncomeAmount.setText(currencyFormat.format(totalIncome));
        binding.textIncomeTransactions.setText(transactionLabel(incomeCount));
        binding.textIncomeSecondary.setText(transactionLabel(incomeCount));

        binding.textNetWorth.setText(currencyFormat.format(netWorth));
    }

    private String transactionLabel(int count) {
        return count == 1 ? "1 transaction" : count + " transactions";
    }
}
