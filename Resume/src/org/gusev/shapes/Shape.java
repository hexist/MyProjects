package org.gusev.shapes;

import java.util.DoubleSummaryStatistics;

/**
 * Created by Alexander on 09.04.2016.
 */
public abstract class Shape {

    private final Color color;

    public Shape(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public abstract double getArea();
}
