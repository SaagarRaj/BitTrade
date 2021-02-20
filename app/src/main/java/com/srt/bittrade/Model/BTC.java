
package com.srt.bittrade.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BTC {

    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("volume_24h")
    @Expose
    private Integer volume24h;
    @SerializedName("percent_change_1h")
    @Expose
    private Integer percentChange1h;
    @SerializedName("percent_change_24h")
    @Expose
    private Integer percentChange24h;
    @SerializedName("percent_change_7d")
    @Expose
    private Integer percentChange7d;
    @SerializedName("market_cap")
    @Expose
    private Integer marketCap;
    @SerializedName("last_updated")
    @Expose
    private String lastUpdated;

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getVolume24h() {
        return volume24h;
    }

    public void setVolume24h(Integer volume24h) {
        this.volume24h = volume24h;
    }

    public Integer getPercentChange1h() {
        return percentChange1h;
    }

    public void setPercentChange1h(Integer percentChange1h) {
        this.percentChange1h = percentChange1h;
    }

    public Integer getPercentChange24h() {
        return percentChange24h;
    }

    public void setPercentChange24h(Integer percentChange24h) {
        this.percentChange24h = percentChange24h;
    }

    public Integer getPercentChange7d() {
        return percentChange7d;
    }

    public void setPercentChange7d(Integer percentChange7d) {
        this.percentChange7d = percentChange7d;
    }

    public Integer getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(Integer marketCap) {
        this.marketCap = marketCap;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

}
