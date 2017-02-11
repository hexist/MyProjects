package org.gusev.shapes;
/**
 * Created by Alexander on 09.04.2016.
 */
public class Triangle extends Shape{

    private final Point a;
    private final Point b;
    private final Point c;

    public Triangle(Point a, Point b, Point c, Color color){
        super(color);
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public double getArea(){
        return Math.abs((b.getX() - a.getX()) * (c.getY() - a.getY())
                - (c.getX() - a.getX()) * (b.getY() - a.getY()))/2;
    }

    @Override
    public String toString(){
        return "Triangle{" +
                "a = " + a +
                ", b = " + b +
                ", c = " + c +
                ", color - " + getColor() + "}";
    }
}
