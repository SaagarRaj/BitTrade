package com.srt.bittrade.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.srt.bittrade.R;

public class CoinViewHolder extends RecyclerView.ViewHolder {
    public ImageView coin_icon;
    public TextView coin_symbol , coin_name , coin_price , one_hour_change , twentyFour_hour_change , sevenDays_change ;
    public CoinViewHolder(@NonNull View itemView) {
        super(itemView);

        coin_icon = itemView.findViewById(R.id.coin_icon);
        coin_symbol = itemView.findViewById(R.id.coin_symbol);
        coin_name = itemView.findViewById(R.id.coin_name);
        coin_price = itemView.findViewById(R.id.price_usd_text);
        one_hour_change = itemView.findViewById(R.id.PercentChange1htext);
        twentyFour_hour_change = itemView.findViewById(R.id.PercentChange24htext);
        sevenDays_change = itemView.findViewById(R.id.PercentChange7dtext);
    }
}
