package com.srt.bittrade.Adapter;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.srt.bittrade.R;
import com.srt.bittrade.fragment.HomeFragment;

public class CoinViewHolder extends RecyclerView.ViewHolder {
    public ImageView coin_icon;
    Button btn_buy , btn_sell;
    public TextView coin_symbol , coin_name , coin_price , one_hour_change , twentyFour_hour_change , sevenDays_change ;
    public CoinViewHolder(@NonNull View itemView) {
        super(itemView);
        btn_buy = itemView.findViewById(R.id.Button_Buy);
        btn_sell = itemView.findViewById(R.id.Button_Sell);
        coin_icon = itemView.findViewById(R.id.coin_icon);
        coin_symbol = itemView.findViewById(R.id.coin_symbol);
        coin_name = itemView.findViewById(R.id.coin_name);
        coin_price = itemView.findViewById(R.id.price_usd_text);
        one_hour_change = itemView.findViewById(R.id.PercentChange1htext);
        twentyFour_hour_change = itemView.findViewById(R.id.PercentChange24htext);
        sevenDays_change = itemView.findViewById(R.id.PercentChange7dtext);
    }
}
