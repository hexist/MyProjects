package org.gusev.shapes;

/**
 * Created by Alexander on 09.04.2016.
 */
public class Circle extends Shape{
    private final Point center;
    private final double radius;

    public Circle(Point center, double radius, Color color){
        super(color);
        this.center = center;
        this.radius = radius;
    }

    public Point getCenter(){
        return center;
    }

    public double getRadius(){
        return radius;
    }

    @Override
    public double getArea(){
        return Math.PI* radius * radius;
    }

    @Override
    public String toString(){
        return "Circle{" +
                "center = " + center +
                ", radius = " + radius +
                ", color - " + getColor() + "}";
    }
}
