package InterfaceInheritance;

import java.util.*;

interface Figure{
    public float CalculateArea();
    public float CalculateCircumference();
}

/**********************************************/

class Rectangle implements Figure {

    private float length;
    private float width;

    Rectangle(float length, float width){
        this.length = length;
        this.width = width;
    }


    public float CalculateArea(){
        return length*width;
    }


    public float CalculateCircumference(){
        return 2*length + 2*width;
    }

}

/*****************************************************/

class Circle implements Figure {

    private float radius;

    Circle(float radius) {
        this.radius = radius;
    }

    public float CalculateArea() {
        return (float) (Math.PI * radius * radius);
    }


    public float CalculateCircumference() {
        return (float) (Math.PI * radius * 2);
    }


    public static void main(String[] args) {
        // System.out.println("Hello World !!");

        Figure circle1 = new Circle((float) 12.93);
        Figure circle2 = new Circle((float) 14.63);
        Figure circle3 = new Circle((float) 11.14);

        Figure rectangle4 = new Rectangle((float) 12.93, (float) 21.21);
        Figure rectangle5 = new Rectangle((float) 18.23, (float) 11.21);
        Figure rectangle6 = new Rectangle((float) 42.03, (float) 29.01);

        Collection<Figure> figures = new ArrayList<Figure>();

        figures.add(circle1);
        figures.add(circle2);
        figures.add(circle3);

        figures.add(rectangle4);
        figures.add(rectangle5);
        figures.add(rectangle6);

        byte i = 0;

        for (Figure figure : figures){
            ++i;
            if(i<4){
                System.out.println("==================================");
                System.out.println("Pole koła #" + i +" = " + figure.CalculateArea());
                System.out.println("Obwód koła #" + i +" = " + figure.CalculateCircumference());
            }
            else{
                System.out.println("==================================");
                System.out.println("Pole prostokąta #" + i +" = " + figure.CalculateArea());
                System.out.println("Obwód prostokąta #" + i +" = " + figure.CalculateCircumference());
            }
        }
    }
}