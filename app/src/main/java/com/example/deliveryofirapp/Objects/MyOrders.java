package com.example.deliveryofirapp.Objects;

public class MyOrders {

    private String origin;
    private String destination;
    private String date;


    public String getDate() {
        return date;
    }

    public MyOrders(String origin, String destination, String date) {
        this.origin = origin;
        this.destination = destination;
        this.date = date;
    }

    public MyOrders() {
        this("", "", "");
    }

    public MyOrders setOrigin(String origin) {
        this.origin = origin;
        return this;
    }

    public MyOrders setDestination(String destination) {
        this.destination = destination;
        return this;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public MyOrders setDate(String date) {
        this.date = date;
        return this;
    }


}
