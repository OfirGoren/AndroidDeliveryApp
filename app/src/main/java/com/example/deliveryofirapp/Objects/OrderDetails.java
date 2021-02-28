package com.example.deliveryofirapp.Objects;

public class OrderDetails {


    private String name;
    private String number;
    private String origin;
    private String destination;
    private String iconName;
    private String date;
    private String id;


    public OrderDetails(String name, String number, String origin, String destination, String iconName, String date, String id) {
        this.iconName = iconName;
        this.number = number;
        this.name = name;
        this.origin = origin;
        this.destination = destination;
        this.date = date;
        this.id = id;

    }

    public OrderDetails() {
        this("", "", "", "", "", "", "");
    }


    public String getId() {
        return id;
    }

    public OrderDetails setId(String id) {
        this.id = id;
        return this;
    }


    public String getIconName() {

        return this.iconName;
    }

    public OrderDetails setIconName(String iconName) {

        this.iconName = iconName;
        return this;
    }

    public String getNumber() {
        return number;
    }

    public OrderDetails setNumber(String number) {
        this.number = number;
        return this;
    }

    public String getOrigin() {
        return origin;
    }

    public OrderDetails setOrigin(String origin) {
        this.origin = origin;
        return this;
    }

    public String getDestination() {
        return destination;
    }

    public OrderDetails setDestination(String destination) {
        this.destination = destination;
        return this;
    }


    public String getName() {
        return name;
    }

    public OrderDetails setName(String name) {
        this.name = name;
        return this;
    }

    public String getDate() {
        return date;
    }

    public OrderDetails setDate(String date) {
        this.date = date;
        return this;
    }
}
