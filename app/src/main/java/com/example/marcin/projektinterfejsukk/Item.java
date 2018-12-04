package com.example.marcin.projektinterfejsukk;

import java.util.ArrayList;

public class Item
{
    private String category;
    private String name;
    protected ArrayList<Integer> images;
    private double price;
    private String description;

    public Item(String category, String name, double price, String description, ArrayList<Integer> images)
    {
        this.category = category;
        this.name = name;
        this.price = price;
        this.description = description;
        this.images = images;

    }

    public String getCategory()
    {
        return category;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public ArrayList<Integer> getImages()
    {
        return images;
    }

    public void setImages(ArrayList<Integer> images)
    {
        this.images = images;
    }

    public double getPrice()
    {
        return price;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }
}
