package com.example.exa_204_bankaccountapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.exa_204_bankaccountapp.beans.Account;
import com.example.exa_204_bankaccountapp.beans.GiroAccount;
import com.example.exa_204_bankaccountapp.beans.StudentAccount;

import java.util.Arrays;
import java.util.stream.Collectors;

public class TransferActivity extends AppCompatActivity {
    private TextView tvTypeTransfere;
    private TextView tvIbanTransfere;
    private EditText tvBalanceTransfere;
    private TextView tvAvailableTransfere;

    private AutoCompleteTextView acIbanTransfereTo;
    private EditText etBalanceTransfereTo;

    private Button btTransfere;


    Account acc;
    String type;
    double available;
    String[] ibans;
    double amount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);

        Intent intent = getIntent();

        acc = intent.getParcelableExtra("acc");
        type = intent.getStringExtra("type");
        if(acc instanceof GiroAccount){
            available = ((GiroAccount)acc).getAvailable();
        }else if(acc instanceof StudentAccount){
            available = acc.getBalance();
        }
        ibans = intent.getStringArrayExtra("ibans");

        tvTypeTransfere = findViewById(R.id.tvTypeTransfere);
        tvIbanTransfere = findViewById(R.id.tvIbanTransfere);
        tvBalanceTransfere = findViewById(R.id.tvBalanceTransfere);
        tvAvailableTransfere = findViewById(R.id.tvAvailableTransfere);

        acIbanTransfereTo = findViewById(R.id.acIbanTransfereTo);
        etBalanceTransfereTo = findViewById(R.id.etBalanceTransfereTo);

        btTransfere = findViewById(R.id.btTransfere);


//       SET DATA
        tvTypeTransfere.setText(type);
        tvIbanTransfere.setText(acc.getIban());
        setFormattedBalance();
        tvAvailableTransfere.setText(String.format("€ %.2f", available));

//        SET ADAPTER FOR AC
        ArrayAdapter<String> ibanAdapter = new ArrayAdapter<>(this, android.R.layout.select_dialog_item, ibans);
        acIbanTransfereTo.setAdapter(ibanAdapter);
        etBalanceTransfereTo.setText("€ 0.0");

//        SUBMIT BUTTON
        btTransfere.setEnabled(false);
        btTransfere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                intent.putExtra("acc", acc);
                intent.putExtra("iban", acIbanTransfereTo.getText().toString());
                intent.putExtra("amount", amount);

                setResult(2, intent);
                finish();
            }
        });

//        BALANCE CHANGED LISTENER
        etBalanceTransfereTo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(etBalanceTransfereTo.getText().length() > 0){
                    amount = Double.parseDouble(etBalanceTransfereTo.getText().toString().replace(" ", "").replace("€", ""));
                }else{
                    amount = 0;
                }
                setButtonEnabled();
            }
        });

//        IBAN CHANGED LISTENER
        acIbanTransfereTo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setButtonEnabled();
            }

            @Override
            public void afterTextChanged(Editable s) {
                setButtonEnabled();
            }
        });
    }

    private void setFormattedBalance(){
        if(acc.getBalance()-amount >= 0){
            tvBalanceTransfere.setTextColor(Color.parseColor("#4C9C50"));
        }else{
            tvBalanceTransfere.setTextColor(Color.RED);
        }
        tvBalanceTransfere.setText(String.format("€ %.2f", acc.getBalance()-amount));
    }

    private void setButtonEnabled(){
        if(amount > 0 && amount <= available && Arrays.stream(ibans).collect(Collectors.toList()).contains(acIbanTransfereTo.getText().toString())) {
            setFormattedBalance();
            btTransfere.setEnabled(true);
        }else{
            btTransfere.setEnabled(false);
        }
    }
}