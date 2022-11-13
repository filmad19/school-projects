package com.example.exa_204_bankaccountapp.bl;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exa_204_bankaccountapp.TransferActivity;
import com.example.exa_204_bankaccountapp.beans.Account;

import java.util.ArrayList;
import java.util.List;

public class AccountViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener{
    private ImageView ivImage;
    private TextView tvType;
    private TextView tvIsbn;
    private EditText tvBalance;
    private TextView tvAvalable;
    private Account acc;

    public static String[] ibans;

    public AccountViewHolder(@NonNull View itemView, ImageView ivImage, TextView tvType, TextView tvIsbn, EditText tvBalance, TextView tvAvalable) {
        super(itemView);
        this.ivImage = ivImage;
        this.tvType = tvType;
        this.tvIsbn = tvIsbn;
        this.tvBalance = tvBalance;
        this.tvAvalable = tvAvalable;

        itemView.setOnClickListener(this::onLongClick);
    }

    public ImageView getIvImage() {
        return ivImage;
    }

    public TextView getTvType() {
        return tvType;
    }

    public TextView getTvIsbn() {
        return tvIsbn;
    }

    public EditText getTvBalance() {
        return tvBalance;
    }

    public TextView getTvAvalable() {
        return tvAvalable;
    }

    public void setAcc(Account acc) {
        this.acc = acc;
    }

    public boolean onLongClick(View v){
        Intent intent = new Intent(v.getContext(), TransferActivity.class);
        intent.putExtra("acc", acc);
        intent.putExtra("type", tvType.getText().toString());
        intent.putExtra("ibans", ibans);

        Activity activity = (Activity)(v.getContext());
        activity.startActivityForResult(intent, 1);

        return true;
    }
}
