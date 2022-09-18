package entity;

public class OrderDetail {
    private String itemCode;
    private String orderId;
    private int orderQty;
    private double discount;

    public OrderDetail() {
    }

    public OrderDetail(String itemCode, String orderId, int orderQty, double discount) {
        this.setItemCode(itemCode);
        this.setOrderId(orderId);
        this.setOrderQty(orderQty);
        this.setDiscount(discount);
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(int orderQty) {
        this.orderQty = orderQty;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}
