package com.akashpal.budgettracker.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.akashpal.budgettracker.data.Expense;
import com.akashpal.budgettracker.databinding.ItemExpenseBinding;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder> {

    private final List<Expense> items = new ArrayList<>();
    private final NumberFormat currencyFormat;
    private final SimpleDateFormat dateFormat;

    public ExpenseAdapter(Context context) {
        currencyFormat = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
        currencyFormat.setCurrency(Currency.getInstance("INR"));
        dateFormat = new SimpleDateFormat("dd MMM, hh:mm a", Locale.getDefault());
    }

    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemExpenseBinding binding = ItemExpenseBinding.inflate(inflater, parent, false);
        return new ExpenseViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position) {
        Expense expense = items.get(position);
        holder.binding.textExpenseTitle.setText(String.format(Locale.getDefault(), "%s • %s", expense.getCategory(), expense.getLabel()));
        holder.binding.textExpenseAmount.setText(currencyFormat.format(expense.getAmount()));
        holder.binding.textExpenseDate.setText(dateFormat.format(new Date(expense.getTimestamp())));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setData(List<Expense> expenses) {
        items.clear();
        if (expenses != null) {
            items.addAll(expenses);
        }
        notifyDataSetChanged();
    }

    static class ExpenseViewHolder extends RecyclerView.ViewHolder {
        final ItemExpenseBinding binding;

        ExpenseViewHolder(ItemExpenseBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
