package com.example.exa_204_bankaccountapp.bl;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exa_204_bankaccountapp.R;
import com.example.exa_204_bankaccountapp.beans.Account;
import com.example.exa_204_bankaccountapp.beans.GiroAccount;
import com.example.exa_204_bankaccountapp.beans.StudentAccount;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AccountAdapter extends RecyclerView.Adapter<AccountViewHolder> {
    private List<Account> accountList;
    private List<Account> originalList;

    public AccountAdapter(List<Account> accountList) {
        this.accountList = accountList;
        this.originalList = accountList;
    }

    @NonNull
    @Override
    public AccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.account_item, parent, false);

        ImageView image = view.findViewById(R.id.ivImage);
        TextView type = view.findViewById(R.id.tvType);
        TextView isbn = view.findViewById(R.id.tvIban);
        EditText balance = view.findViewById(R.id.tvBalance);
        TextView available = view.findViewById(R.id.tvAvailable);

        return new AccountViewHolder(view, image, type, isbn, balance, available);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountViewHolder holder, int position) {
        Account acc = accountList.get(position);

        if(acc instanceof GiroAccount) {
            holder.getTvType().setText("Giro-Account");
            holder.getIvImage().setImageResource(R.drawable.ic_baseline_attach_money_24);
            GiroAccount ga = (GiroAccount) acc;
            holder.getTvAvalable().setText(ga.getFormattedAvailable());
        }else if(acc instanceof StudentAccount){
            holder.getTvType().setText("Student-Account");
            holder.getIvImage().setImageResource(R.drawable.ic_baseline_school_24);
            holder.getTvAvalable().setText(acc.getFormattedBalance());
        }

        holder.getTvIsbn().setText(acc.getIban());
        holder.getTvBalance().setText(acc.getFormattedBalance());
        if(acc.getBalance() >= 0){
            holder.getTvBalance().setTextColor(Color.parseColor("#4C9C50"));
        }else{
            holder.getTvBalance().setTextColor(Color.parseColor("#FF0000"));
        }
        holder.getTvIsbn().setText("IBAN: " + acc.getIban());

        holder.setAcc(acc);

        if(position == 0){
            holder.ibans = new String[originalList.size()];

            for(int i = 0;i < holder.ibans.length;i++){
                holder.ibans[i] = originalList.get(i).getIban();
            }
        }
    }

    @Override
    public int getItemCount() {
        return accountList.size();
    }

    public void sortList(){
        accountList.sort(Comparator.comparing(Account::getIban));
        notifyDataSetChanged();
    }

    public void filterAccount(MenuItem item){
        switch (item.getItemId()){
            case R.id.miStudents: accountList = originalList.stream().filter(e -> e instanceof StudentAccount).collect(Collectors.toList());break;
            case R.id.miGiro: accountList = originalList.stream().filter(e -> e instanceof GiroAccount).collect(Collectors.toList());break;
            case R.id.miAll: accountList = originalList.stream().filter(e -> e instanceof Account).collect(Collectors.toList());
        }
        sortList();
    }

    public void transfereMoney(Account givenAccount, String toIban, double amount){
        Account toAccount = originalList.stream().filter(a -> a.getIban().equals(toIban)).collect(Collectors.toList()).get(0);
        Account fromAccount = originalList.stream().filter(a -> a.getIban().equals(givenAccount.getIban())).collect(Collectors.toList()).get(0);

        fromAccount.setBalance(fromAccount.getBalance() - amount);
        toAccount.setBalance(toAccount.getBalance() + amount);
        notifyDataSetChanged();
    }
}
