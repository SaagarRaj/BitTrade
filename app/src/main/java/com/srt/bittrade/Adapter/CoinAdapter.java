package com.srt.bittrade.Adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.srt.bittrade.Interface.ILoadMore;
import com.srt.bittrade.Model.Datum;
import com.srt.bittrade.Model.Quote;
import com.srt.bittrade.Model.USD;
import com.srt.bittrade.Model._CoinModel;
import com.srt.bittrade.R;

import java.util.List;

public class CoinAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ILoadMore iLoadMore;
    boolean isLoading ;
    Activity activity;
    List<Datum> items;
    int visibleThreshold = 5 , lastVisible , totalItemCount;

    public CoinAdapter(RecyclerView recyclerView , Activity activity, final List<Datum> items) {
        this.activity = activity;
        this.items = items;

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisible = linearLayoutManager.findLastVisibleItemPosition();
                if(!isLoading && totalItemCount <= (lastVisible+visibleThreshold)){
                    if(iLoadMore != null)
                        iLoadMore.onLoadMore();

                    isLoading = true;


                }
            }
        });
    }

    public void setiLoadMore(ILoadMore iLoadMore) {
        this.iLoadMore = iLoadMore;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.coin_layout,parent,false);

        return new CoinViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Datum item = items.get(position);
        Quote quote = item.getQuote();
        USD usd = quote.getUSD() ;

        CoinViewHolder holderItem = (CoinViewHolder)holder;

        holderItem.coin_name.setText(item.getName());
        holderItem.coin_symbol.setText(item.getSymbol());
        holderItem.coin_price.setText(usd.getPrice().toString());
        holderItem.one_hour_change.setText(usd.getPercentChange1h()+"%");
        holderItem.twentyFour_hour_change.setText(usd.getPercentChange24h()+"%");
        holderItem.sevenDays_change.setText(usd.getPercentChange7d()+"%");

        //Load image

       Picasso.get().load(new StringBuilder("https://res.cloudinary.com/dxi90ksom/image/upload/").append(item.getSymbol().toLowerCase()).append(".png").toString()).into(holderItem.coin_icon);

       holderItem.one_hour_change.setTextColor(usd.getPercentChange1h().toString().contains("-")?
               Color.parseColor("#FF0000"):Color.parseColor("#32CD32"));

        holderItem.twentyFour_hour_change.setTextColor(usd.getPercentChange24h().toString().contains("-")?
                Color.parseColor("#FF0000"):Color.parseColor("#32CD32"));

        holderItem.sevenDays_change.setTextColor(usd.getPercentChange7d().toString().contains("-")?
                Color.parseColor("#FF0000"):Color.parseColor("#32CD32"));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setLoaded(){isLoading = false;}

    public void updateData(List<Datum> coinModels){
        this.items = coinModels;
        notifyDataSetChanged();
    }
}
