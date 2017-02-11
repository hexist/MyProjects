package org.gusev.shapes;

public class Main {

    public static void main(String[] args) {

        Circle circle = new Circle(new Point(0, 1), 5.6, Color.RED);
        Square square = new Square(new Point(0, 1), 5, Color.BLACK);
        Triangle triangle = new Triangle(new Point(0, 1), new Point(0, 2.3), new Point(1.5, 3.3), Color.WHITE);

        Object object =  square;
        if(object instanceof Square){
            System.out.println("true");
        }
        square = (Square) object;

        Shape[] shapes = {circle, square, triangle};
        printArrayElements(shapes);


        Shape maxShape = findShapeWithMaxArea(shapes);
        System.out.println("Shape with max area(" + maxShape.getArea() + "): " + maxShape);

    }

    private static void printArrayElements(Object[] objects){
        for(int i = 0; i < objects.length; i++){
            System.out.println(i + ": " + objects[i]);
        }

    }
    private static Shape findShapeWithMaxArea(Shape[] shapes){
        Shape maxShape = null;
        double maxArea = Double.NEGATIVE_INFINITY;
        for(Shape shape: shapes){
            double area = shape.getArea();
            if(area > maxArea){
                maxArea = area;
                maxShape = shape;
            }
        }
        return maxShape;
    }
}
