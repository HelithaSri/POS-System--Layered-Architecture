package view.tm;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class CartTM extends RecursiveTreeObject<CartTM> {
    private String code;
    private String description;
    private int qty;
    private double unitPrice;
    private int discount;
    private double price;
    private double totalPrice;

    public CartTM() {
    }

    public CartTM(String code, String description, int qty, double unitPrice, int discount, double price, double totalPrice) {
        this.setCode(code);
        this.setDescription(description);
        this.setQty(qty);
        this.setUnitPrice(unitPrice);
        this.setDiscount(discount);
        this.setPrice(price);
        this.setTotalPrice(totalPrice);
    }

    public CartTM(String code, String description, int qty, double unitPrice, int discount, double totalPrice) {
        this.code = code;
        this.description = description;
        this.qty = qty;
        this.unitPrice = unitPrice;
        this.discount = discount;
        this.totalPrice = totalPrice;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "CartTM{" +
                "code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", qty=" + qty +
                ", unitPrice=" + unitPrice +
                ", discount=" + discount +
                ", price=" + price +
                ", totalPrice=" + totalPrice +
                '}';
    }
}