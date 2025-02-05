package src.java.map;

import java.util.Random;

public enum Orientation2Mast{
    HORIZONTAL,VERTICAL;

    private static final Random r = new Random();

    public static int[] randomOrientation(){
        Orientation2Mast[] orientations = values();
        Orientation2Mast o = orientations[r.nextInt(orientations.length)];

        int x1 = (int) (Math.random() * 10);
        int y1 = (int) (Math.random() * 9);

        return switch(o){
            case HORIZONTAL -> new int[]{x1,y1,x1,y1+1};
            case VERTICAL -> new int[]{y1,x1,y1+1,x1};
        };
    }
}
