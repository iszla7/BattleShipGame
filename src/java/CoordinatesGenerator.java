package src.java;

public class CoordinatesGenerator {
    public static Coordinates getCoordinates(){
        int x = (int) (Math.random() * 10);
        int y = (int) (Math.random() * 10);
        return new Coordinates(x,y);
    }
}
