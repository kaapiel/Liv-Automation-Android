
package br.com.pontomobi.livelopontos.service.livelo.orders.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @SerializedName("stateAsString")
    private String stateAsString;

    @SerializedName("shippingGroups")
    private List<ShippingGroup> shippingGroups = new ArrayList<ShippingGroup>();

    @SerializedName("commerceItems")
    private List<CommerceItem> commerceItems = new ArrayList<CommerceItem>();

    @SerializedName("id")
    private String id;

    @SerializedName("priceInfo")
    private PriceInfo priceInfo;

    @SerializedName("paymentGroups")
    private List<PaymentGroup> paymentGroups = new ArrayList<PaymentGroup>();


    @SerializedName("customerData")
    private CustomerData customerData;

    @SerializedName("creationTime")
    private long creationTime;

    @SerializedName("lastModifiedTime")
    private long lastModifiedTime;

    @SerializedName("submittedDate")
    private OrderSubmittedTime submittedDate;

    /**
     * @return The stateAsString
     */
    public String getStateAsString() {
        return stateAsString;
    }

    /**
     * @param stateAsString The stateAsString
     */
    public void setStateAsString(String stateAsString) {
        this.stateAsString = stateAsString;
    }

    /**
     * @return The shippingGroups
     */
    public List<ShippingGroup> getShippingGroups() {
        return shippingGroups;
    }

    /**
     * @param shippingGroups The shippingGroups
     */
    public void setShippingGroups(List<ShippingGroup> shippingGroups) {
        this.shippingGroups = shippingGroups;
    }

    /**
     * @return The commerceItems
     */
    public List<CommerceItem> getCommerceItems() {
        return commerceItems;
    }

    /**
     * @param commerceItems The commerceItems
     */
    public void setCommerceItems(List<CommerceItem> commerceItems) {
        this.commerceItems = commerceItems;
    }

    /**
     * @return The id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return The priceInfo
     */
    public PriceInfo getPriceInfo() {
        return priceInfo;
    }

    /**
     * @param priceInfo The priceInfo
     */
    public void setPriceInfo(PriceInfo priceInfo) {
        this.priceInfo = priceInfo;
    }

    /**
     * @return The paymentGroups
     */
    public List<PaymentGroup> getPaymentGroups() {
        return paymentGroups;
    }

    /**
     * @param paymentGroups The paymentGroups
     */
    public void setPaymentGroups(List<PaymentGroup> paymentGroups) {
        this.paymentGroups = paymentGroups;
    }

    /**
     * @return The customerData
     */
    public CustomerData getCustomerData() {
        return customerData;
    }

    /**
     * @param customerData The customerData
     */
    public void setCustomerData(CustomerData customerData) {
        this.customerData = customerData;
    }

    /**
     * @return The creationTime
     */
    public long getCreationTime() {
        return creationTime;
    }

    /**
     * @param creationTime The creationTime
     */
    public void setCreationTime(long creationTime) {
        this.creationTime = creationTime;
    }

    /**
     * @return The lastModifiedTime
     */
    public long getLastModifiedTime() {
        return lastModifiedTime;
    }

    /**
     * @param lastModifiedTime The lastModifiedTime
     */
    public void setLastModifiedTime(long lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public OrderSubmittedTime getSubmittedDate() {
        return submittedDate;
    }

    public void setSubmittedDate(OrderSubmittedTime submittedDate) {
        this.submittedDate = submittedDate;
    }
}
