package com.example.exa_106_textfieldformatter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText text;
    private CheckBox bold;
    private CheckBox italic;
    private TextView sizeValue;
    private SeekBar sizeBar;
    private RadioGroup fonts;
    private RadioButton aclonica;
    private RadioButton atomicAge;
    private RadioButton bungeeShade;


//    override on create
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = findViewById(R.id.etText);
        bold = findViewById(R.id.cbBold);
        italic = findViewById(R.id.cbItalic);
        sizeValue = findViewById(R.id.etSize);
        sizeBar = findViewById(R.id.sbSize);
        fonts = findViewById(R.id.rgFonts);
        aclonica = findViewById(R.id.rbAclonica);
        atomicAge = findViewById(R.id.rbAtomicAge);
        bungeeShade = findViewById(R.id.rbBungeeShade);

//        change Style
        bold.setOnClickListener(this::onChangeFontStyle);
        italic.setOnClickListener(this::onChangeFontStyle);

//        change Size
        sizeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                onChangeFontSize(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

//        change font family
        fonts.setOnCheckedChangeListener(this::onChangeFont);
    }


    public void onChangeFontStyle(View v){
        Typeface font = Typeface.create(text.getTypeface(), Typeface.NORMAL);

        if(bold.isChecked() && !italic.isChecked()){
            font = Typeface.create(text.getTypeface(), Typeface.BOLD);
        }
        else if(!bold.isChecked() && italic.isChecked()){
            font = Typeface.create(text.getTypeface(), Typeface.ITALIC);
        }
        else if(bold.isChecked() && italic.isChecked()){
            font = Typeface.create(text.getTypeface(), Typeface.BOLD_ITALIC);
        }

        text.setTypeface(font);
    }

    public void onChangeFontSize(int value){
        sizeValue.setText(Integer.toString(value));
        text.setTextSize(value);
    }

    private void onChangeFont(RadioGroup radioGroup, int i) {
        if(aclonica.isChecked()){
            text.setTypeface(ResourcesCompat.getFont(getApplicationContext(),R.font.aclonica));
        }
        else if(atomicAge.isChecked()){
            text.setTypeface(ResourcesCompat.getFont(getApplicationContext(),R.font.atomic_age));
        }
        else if(bungeeShade.isChecked()){
            text.setTypeface(ResourcesCompat.getFont(getApplicationContext(),R.font.bungee_shade));
        }

        onChangeFontStyle(null);
    }
}