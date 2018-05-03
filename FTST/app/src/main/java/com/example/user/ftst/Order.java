package com.example.user.ftst;

public class Order {
    String id;
    String deadLine;
    String language;
    String direction;
    String pageCount;
    String price;

    Order(String _id, String _deadLine, String _language, String _direction, String _pageCount, String _price) {
        id = _id;
        deadLine = _deadLine;
        language = _language;
        direction = _direction;
        pageCount = _pageCount;
        price = _price;

    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(String deadLine) {
        this.deadLine = deadLine;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
