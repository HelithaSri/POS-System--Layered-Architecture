package dto;

import entity.OrderDetail;

import java.util.ArrayList;

public class OrderDto {
    private String orderId;
    private String orderDate;
    private String customerId;
    private ArrayList<OrderDetail> orderDetailDtoDtos;

    public OrderDto() {
    }

    public OrderDto(String orderId, String orderDate, String customerId, ArrayList<OrderDetail> orderDetailDtoDtos) {
        this.setOrderId(orderId);
        this.setOrderDate(orderDate);
        this.setCustomerId(customerId);
        this.setOrderDetails(orderDetailDtoDtos);
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public ArrayList<OrderDetail> getOrderDetails() {
        return orderDetailDtoDtos;
    }

    public void setOrderDetails(ArrayList<OrderDetail> orderDetailDtoDtos) {
        this.orderDetailDtoDtos = orderDetailDtoDtos;
    }

    @Override
    public String toString() {
        return "OrderDto{" +
                "orderId='" + orderId + '\'' +
                ", orderDate='" + orderDate + '\'' +
                ", customerId='" + customerId + '\'' +
                ", orderDetailDtoDtos=" + orderDetailDtoDtos +
                '}';
    }
}
