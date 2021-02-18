package com.srt.bittrade.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.srt.bittrade.Adapter.CoinAdapter;
import com.srt.bittrade.Interface.ILoadMore;
import com.srt.bittrade.MainActivity;
import com.srt.bittrade.Model.CoinModel;
import com.srt.bittrade.R;

import org.jetbrains.annotations.NotNull;
import org.xmlpull.v1.XmlPullParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HomeFragment extends Fragment {
    List<CoinModel> items = new ArrayList<>();
    CoinAdapter adapter;
    RecyclerView recyclerView;

    OkHttpClient client;
    Request request;

    SwipeRefreshLayout swipeRefreshLayout;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.activity_home_fragment,container,false);

       swipeRefreshLayout = view.findViewById(R.id.rootLayout);
       swipeRefreshLayout.post(new Runnable() {
           @Override
           public void run() {
               loadFirst10Coin(0);
               setUpAdapter();
           }
       });

       swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
           @Override
           public void onRefresh() {
               items.clear();
               loadFirst10Coin(0);
               
           }
       });

       recyclerView = view.findViewById(R.id.coinList);
       recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
       setUpAdapter();

       return view;
    }

    private void setUpAdapter() {
        adapter = new CoinAdapter(recyclerView , (Activity) getContext(), items);
        recyclerView.setAdapter(adapter);
        adapter.setiLoadMore(new ILoadMore() {
            @Override
            public void onLoadMore() {
               if(items.size() <=1000)
               {
                   loadNext10Coin(items.size());
               }
               else{
                   Toast.makeText(getContext(),"Max items is 1000",Toast.LENGTH_LONG).show();
               }
            }
        });
    }

    private void loadNext10Coin(int index) {
        String apiKey = "dea87623-e004-491f-87ee-c806787d2689";
        client = new OkHttpClient();
        request = new Request.Builder().url(String.format("https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest",index)).addHeader("X-CMC_PRO_API_KEY", apiKey).build();

        swipeRefreshLayout.setRefreshing(true);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String body = response.body().string();
                Gson gson = new Gson();

                final List<CoinModel> newItems = gson.fromJson(body,new TypeToken<List<CoinModel>>(){}.getType());

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        items.addAll(newItems);
                        adapter.setLoaded();
                        adapter.updateData(items);
                        swipeRefreshLayout.setRefreshing(false);


                    }
                });


            }
        });
    }

    private void loadFirst10Coin(int index) {

        String apiKey = "dea87623-e004-491f-87ee-c806787d2689";
        client = new OkHttpClient();
        request = new Request.Builder().url(String.format("https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest",index)).addHeader("X-CMC_PRO_API_KEY", apiKey).build();

        swipeRefreshLayout.setRefreshing(true);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String body = response.body().string();
                Gson gson = new Gson();

                final List<CoinModel> newItems = gson.fromJson(body,new TypeToken<List<CoinModel>>(){}.getType());

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.updateData(newItems);
                    }
                });

            }
        });
        if(swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(false);

    }

    public void onResume(){
        super.onResume();
        // Set title bar
        ((MainActivity) getActivity()).setActionBarTitle("Home");

    }
}
