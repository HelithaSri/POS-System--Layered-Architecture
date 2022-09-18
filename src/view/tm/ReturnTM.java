package view.tm;

public class ReturnTM {
    private String orderId;
    private String itemCode;
    private String description;
    private int qty;
    private int discount;
    private double total;
    private double unitPrice;

    public ReturnTM() {
    }

    public ReturnTM(String orderId, String itemCode, String description, int qty, int discount, double total, double unitPrice) {
        this.setOrderId(orderId);
        this.setItemCode(itemCode);
        this.setDescription(description);
        this.setQty(qty);
        this.setDiscount(discount);
        this.setTotal(total);
        this.setUnitPrice(unitPrice);
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return "ReturnTM{" +
                "orderId='" + orderId + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", description='" + description + '\'' +
                ", qty=" + qty +
                ", discount=" + discount +
                ", total=" + total +
                ", unitPrice=" + unitPrice +
                '}';
    }
}
