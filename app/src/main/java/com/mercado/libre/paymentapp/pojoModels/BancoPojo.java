package com.mercado.libre.paymentapp.pojoModels;

/**
 * Created by raelyx on 13/06/18.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BancoPojo {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("secure_thumbnail")
    @Expose
    private String secureThumbnail;
    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;
    @SerializedName("processing_mode")
    @Expose
    private String processingMode;
    @SerializedName("merchant_account_id")
    @Expose
    private Object merchantAccountId;

    /**
     * No args constructor for use in serialization
     *
     */
    public BancoPojo() {
    }

    /**
     *
     * @param id
     * @param secureThumbnail
     * @param thumbnail
     * @param name
     * @param merchantAccountId
     * @param processingMode
     */
    public BancoPojo(String id, String name, String secureThumbnail, String thumbnail, String processingMode, Object merchantAccountId) {
        super();
        this.id = id;
        this.name = name;
        this.secureThumbnail = secureThumbnail;
        this.thumbnail = thumbnail;
        this.processingMode = processingMode;
        this.merchantAccountId = merchantAccountId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecureThumbnail() {
        return secureThumbnail;
    }

    public void setSecureThumbnail(String secureThumbnail) {
        this.secureThumbnail = secureThumbnail;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getProcessingMode() {
        return processingMode;
    }

    public void setProcessingMode(String processingMode) {
        this.processingMode = processingMode;
    }

    public Object getMerchantAccountId() {
        return merchantAccountId;
    }

    public void setMerchantAccountId(Object merchantAccountId) {
        this.merchantAccountId = merchantAccountId;
    }

}

