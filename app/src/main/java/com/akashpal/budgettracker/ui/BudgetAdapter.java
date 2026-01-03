package com.akashpal.budgettracker.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.akashpal.budgettracker.R;
import com.akashpal.budgettracker.data.Budget;
import com.akashpal.budgettracker.databinding.ItemBudgetBinding;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import androidx.core.graphics.ColorUtils;

public class BudgetAdapter extends RecyclerView.Adapter<BudgetAdapter.BudgetViewHolder> {

    private final List<Budget> items = new ArrayList<>();
    private final NumberFormat currencyFormat;
    private final Context context;

    public BudgetAdapter(Context context) {
        this.context = context;
        currencyFormat = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
        currencyFormat.setCurrency(Currency.getInstance("INR"));
    }

    @NonNull
    @Override
    public BudgetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemBudgetBinding binding = ItemBudgetBinding.inflate(inflater, parent, false);
        return new BudgetViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BudgetViewHolder holder, int position) {
        Budget budget = items.get(position);
        boolean overBudget = budget.getSpent() > budget.getMonthlyLimit();
        double remaining = budget.getMonthlyLimit() - budget.getSpent();

        holder.binding.textCategoryLabel.setText(String.format(Locale.getDefault(), "%s • %s", budget.getCategory(), budget.getLabel()));
        holder.binding.textBudgetAmount.setText(String.format(Locale.getDefault(), "Budget: %s", currencyFormat.format(budget.getMonthlyLimit())));
        holder.binding.textSpent.setText(String.format(Locale.getDefault(), "Spent: %s", currencyFormat.format(budget.getSpent())));

        holder.binding.textStatus.setText(overBudget ? context.getString(R.string.over_budget) : context.getString(R.string.within_budget));
        holder.binding.textStatus.setTextColor(ContextCompat.getColor(context, overBudget ? R.color.md_theme_error : R.color.md_theme_success));
        holder.binding.textRemaining.setText(String.format(Locale.getDefault(), "Remaining: %s", currencyFormat.format(Math.max(remaining, 0))));

        int baseColor = ContextCompat.getColor(context, overBudget ? R.color.md_theme_error : android.R.color.white);
        int backgroundColor = overBudget ? ColorUtils.setAlphaComponent(baseColor, 32) : baseColor;
        holder.binding.getRoot().setCardBackgroundColor(backgroundColor);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setData(List<Budget> budgets) {
        items.clear();
        if (budgets != null) {
            items.addAll(budgets);
        }
        notifyDataSetChanged();
    }

    static class BudgetViewHolder extends RecyclerView.ViewHolder {
        final ItemBudgetBinding binding;

        BudgetViewHolder(ItemBudgetBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
