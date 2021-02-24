package com.srt.bittrade.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;
import com.srt.bittrade.Interface.ILoadMore;
import com.srt.bittrade.MainActivity;
import com.srt.bittrade.Model.Datum;
import com.srt.bittrade.Model.Quote;
import com.srt.bittrade.Model.USD;
import com.srt.bittrade.Model._CoinModel;
import com.srt.bittrade.R;

import java.util.HashMap;
import java.util.List;

import static com.google.android.material.internal.ContextUtils.getActivity;

public class CoinAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ILoadMore iLoadMore;
    boolean isLoading ;
    Activity activity;
    List<Datum> items;
    FirebaseAuth mAuth;
    FirebaseFirestore fstore;

    int visibleThreshold = 5 , lastVisible , totalItemCount;

    public CoinAdapter(RecyclerView recyclerView , Activity activity, final List<Datum> items) {
        this.activity = activity;
        this.items = items;
        mAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

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
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        Datum item = items.get(position);
        Quote quote = item.getQuote();
        final USD usd = quote.getUSD() ;

        final CoinViewHolder holderItem = (CoinViewHolder)holder;

        holderItem.coin_name.setText(item.getName());
        holderItem.coin_symbol.setText(item.getSymbol());
        holderItem.coin_price.setText(usd.getPrice().toString());
        holderItem.one_hour_change.setText(usd.getPercentChange1h()+"%");
        holderItem.twentyFour_hour_change.setText(usd.getPercentChange24h()+"%");
        holderItem.sevenDays_change.setText(usd.getPercentChange7d()+"%");

        holderItem.btn_sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sell(usd);

                Log.e("sell","clicked sell button");
            }
        });

        holderItem.btn_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buy(usd);
                Log.e("sell","clicked buy button");
            }
        });
        //Load image

       Picasso.get().load(new StringBuilder("https://res.cloudinary.com/dxi90ksom/image/upload/").append(item.getSymbol().toLowerCase()).append(".png").toString()).into(holderItem.coin_icon);

       holderItem.one_hour_change.setTextColor(usd.getPercentChange1h().toString().contains("-")?
               Color.parseColor("#FF0000"):Color.parseColor("#32CD32"));

        holderItem.twentyFour_hour_change.setTextColor(usd.getPercentChange24h().toString().contains("-")?
                Color.parseColor("#FF0000"):Color.parseColor("#32CD32"));

        holderItem.sevenDays_change.setTextColor(usd.getPercentChange7d().toString().contains("-")?
                Color.parseColor("#FF0000"):Color.parseColor("#32CD32"));
    }

    private void buy(final USD usd) {

        String userID = mAuth.getCurrentUser().getUid();
        final DocumentReference documentReference = fstore.collection("users").document(userID);
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                double  Wallet = 0, buy;
                if(documentSnapshot != null){
                    Log.e("output", documentSnapshot.getString("Fname") );
                    Log.e("output", String.valueOf(documentSnapshot.getDouble("Wallet")));
                    Wallet  = documentSnapshot.getDouble("Wallet");
                }
                buy = usd.getPrice();
                if(Wallet >= buy){
                    final double newWallet = Wallet - buy ;
                    HashMap<String, Object > user = new HashMap<>();
                    user.put("Wallet",newWallet);
                    documentReference.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(activity ,"Coin bought , Wallet balance :" +newWallet , Toast.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(activity , e.getMessage() , Toast.LENGTH_LONG).show();
                        }
                    });
                }
                else{
                    Toast.makeText(activity ,"Not sufficient funds " , Toast.LENGTH_LONG).show();
                }

            }
        });
    }



    private void sell(final USD usd) {
        String userID = mAuth.getCurrentUser().getUid();
        final DocumentReference documentReference = fstore.collection("users").document(userID);
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                double  Wallet = 0, buy;
                if(documentSnapshot != null) {
                    Log.e("output", documentSnapshot.getString("Fname"));
                    Log.e("output", String.valueOf(documentSnapshot.getDouble("Wallet")));
                    Wallet = documentSnapshot.getDouble("Wallet");

                    buy = usd.getPrice();
                    final double newWallet = buy + Wallet;
                    HashMap<String, Object> user = new HashMap<>();
                    user.put("Wallet", newWallet);
                    documentReference.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(activity, "Coin sold , Wallet balance : "+newWallet, Toast.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(activity, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

                }else{
                    Toast.makeText(activity , "Error",Toast.LENGTH_LONG).show();
                }

            }
        });


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
