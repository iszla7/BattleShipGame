package src.java.map;

import java.util.Random;

public enum Orientation3Mast{
    HORIZONTAL,VERTICAL,
    UP_LEFT_L,UP_RIGHT_L,LOW_LEFT_L,LOW_RIGHT_L;;

    private static final Random r = new Random();

    public static int[] randomOrientation(){
        Orientation3Mast[] orientations = values();
        Orientation3Mast o = orientations[r.nextInt(orientations.length)];

        //getting random positions for horizontal and vertical cases
        int x1 = (int) (Math.random() * 10);
        int y1 = (int) (Math.random() * 9);
        //getting random positions for all other cases
        int x2 = (int) (Math.random() * 9);
        int y2 = (int) (Math.random() * 9);

        return switch(o){
            case HORIZONTAL -> new int[]{x1,y1,x1,y1+1,x1,y1+2};
            case VERTICAL -> new int[]{y1,x1,y1+1,x1,y1+2,x1};
            case LOW_LEFT_L -> new int[]{x2,y2,x2+1,y2,x2+1,y2+1};
            case LOW_RIGHT_L -> new int[]{x2+1,y2,x2+1,y2+1,x2,y2+1};
            case UP_LEFT_L -> new int[]{x2,y2,x2+1,y2,x2,y2+1};
            case UP_RIGHT_L -> new int[]{x2,y2,x2,y2+1,x2+1,y2+1};
        };
    }
}