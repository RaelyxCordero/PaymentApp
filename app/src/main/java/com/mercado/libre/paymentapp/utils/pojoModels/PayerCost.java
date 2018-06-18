
package com.mercado.libre.paymentapp.utils.pojoModels;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by raelyx on 13/06/18.
 */

public class PayerCost {

    @SerializedName("installments")
    @Expose
    private double installments;
    @SerializedName("installment_rate")
    @Expose
    private double installmentRate;
    @SerializedName("discount_rate")
    @Expose
    private double discountRate;
    @SerializedName("labels")
    @Expose
    private List<String> labels = null;
    @SerializedName("installment_rate_collector")
    @Expose
    private List<String> installmentRateCollector = null;
    @SerializedName("min_allowed_amount")
    @Expose
    private int minAllowedAmount;
    @SerializedName("max_allowed_amount")
    @Expose
    private int maxAllowedAmount;
    @SerializedName("recommended_message")
    @Expose
    private String recommendedMessage;
    @SerializedName("installment_amount")
    @Expose
    private double installmentAmount;
    @SerializedName("total_amount")
    @Expose
    private double totalAmount;

    /**
     * No args constructor for use in serialization
     * 
     */
    public PayerCost() {
    }

    /**
     * 
     * @param minAllowedAmount
     * @param recommendedMessage
     * @param labels
     * @param totalAmount
     * @param installmentAmount
     * @param maxAllowedAmount
     * @param discountRate
     * @param installmentRateCollector
     * @param installmentRate
     * @param installments
     */
    public PayerCost(int installments, double installmentRate, int discountRate, List<String> labels, List<String> installmentRateCollector, int minAllowedAmount, int maxAllowedAmount, String recommendedMessage, int installmentAmount, int totalAmount) {
        super();
        this.installments = installments;
        this.installmentRate = installmentRate;
        this.discountRate = discountRate;
        this.labels = labels;
        this.installmentRateCollector = installmentRateCollector;
        this.minAllowedAmount = minAllowedAmount;
        this.maxAllowedAmount = maxAllowedAmount;
        this.recommendedMessage = recommendedMessage;
        this.installmentAmount = installmentAmount;
        this.totalAmount = totalAmount;
    }

    public double getInstallments() {
        return installments;
    }

    public void setInstallments(int installments) {
        this.installments = installments;
    }

    public double getInstallmentRate() {
        return installmentRate;
    }

    public void setInstallmentRate(double installmentRate) {
        this.installmentRate = installmentRate;
    }

    public double getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(int discountRate) {
        this.discountRate = discountRate;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public List<String> getInstallmentRateCollector() {
        return installmentRateCollector;
    }

    public void setInstallmentRateCollector(List<String> installmentRateCollector) {
        this.installmentRateCollector = installmentRateCollector;
    }

    public int getMinAllowedAmount() {
        return minAllowedAmount;
    }

    public void setMinAllowedAmount(int minAllowedAmount) {
        this.minAllowedAmount = minAllowedAmount;
    }

    public int getMaxAllowedAmount() {
        return maxAllowedAmount;
    }

    public void setMaxAllowedAmount(int maxAllowedAmount) {
        this.maxAllowedAmount = maxAllowedAmount;
    }

    public String getRecommendedMessage() {
        return recommendedMessage;
    }

    public void setRecommendedMessage(String recommendedMessage) {
        this.recommendedMessage = recommendedMessage;
    }

    public double getInstallmentAmount() {
        return installmentAmount;
    }

    public void setInstallmentAmount(int installmentAmount) {
        this.installmentAmount = installmentAmount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

}
