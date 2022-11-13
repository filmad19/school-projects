package com.example.exa_201_zodiacsign.bl;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exa_201_zodiacsign.R;
import com.example.exa_201_zodiacsign.ZodiacSignsData;
import com.example.exa_201_zodiacsign.beans.ZodiacSign;

import java.time.MonthDay;
import java.util.ArrayList;
import java.util.List;

public class ZodiacSignAdapter extends RecyclerView.Adapter<ZodiacViewHolder> {
    private List<ZodiacSign> zodiacSignList = new ArrayList<>();

    public ZodiacSignAdapter() {
        for(ZodiacSignsData sign : ZodiacSignsData.values()){
            zodiacSignList.add(new ZodiacSign(sign.getName(), sign.getStartDate(), sign.getPictureID()));
        }
    }

    @NonNull
    @Override
    public ZodiacViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.zodiac_item, parent, false);
        TextView tvName = view.findViewById(R.id.tvName);
        TextView tvDate = view.findViewById(R.id.tvDate);
        ImageView tvImage = view.findViewById(R.id.ivPicture);

        return new ZodiacViewHolder(view, tvName, tvDate, tvImage);
    }

    @Override
    public void onBindViewHolder(@NonNull ZodiacViewHolder holder, int position) {
        ZodiacSign zodiacSign = zodiacSignList.get(position);
        ZodiacSign nextZodiacSign;

        if(position < zodiacSignList.size()-1){
            nextZodiacSign = zodiacSignList.get(position+1);
        }else{
            nextZodiacSign = zodiacSignList.get(0);
        }

        MonthDay zodiacSignEndDate = nextZodiacSign.getStartDate().withDayOfMonth(nextZodiacSign.getStartDate().getDayOfMonth()-1);

        holder.getTvName().setText(zodiacSign.getName());
        holder.getTvDate().setText(String.format(holder.getTvDate().getContext().getString(R.string.date_format), zodiacSign.formateDate(zodiacSign.getStartDate()), zodiacSign.formateDate(zodiacSignEndDate)));
        holder.getTvImage().setImageResource(zodiacSign.getPictureID());
    }

    @Override
    public int getItemCount() {
        return zodiacSignList.size();
    }
}
