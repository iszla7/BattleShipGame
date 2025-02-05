package src.java.map;

import java.util.Random;

public enum Orientation4Mast{
    HORIZONTAL,VERTICAL,
    H_UP_LEFT_L,H_UP_RIGHT_L,H_LOW_LEFT_L,H_LOW_RIGHT_L,
    V_UP_LEFT_L,V_UP_RIGHT_L,V_LOW_LEFT_L,V_LOW_RIGHT_L,
    VS,V_MIRRORED_S,
    HS,H_MIRRORED_S,
    SQUARE;

    private static final Random r = new Random();

    public static int[] randomOrientation(){
        Orientation4Mast[] orientations = values();
        Orientation4Mast o = orientations[r.nextInt(orientations.length)];

        //random position for horizontal and vertical cases
        int x1 = (int) (Math.random() * 10);
        int y1 = (int) (Math.random() * 7);
        //getting random positions for square case
        int x2 = (int) (Math.random() * 9);
        int y2 = (int) (Math.random() * 9);
        //getting random positions for all other cases
        int x3 = (int) (Math.random() * 9);
        int y3 = (int) (Math.random() * 8);

        return switch(o){
            case HORIZONTAL -> new int[]{x1,y1,x1,y1+1,x1,y1+2,x1,y1+3};
            case VERTICAL -> new int[]{y1,x1,y1+1,x1,y1+2,x1,y1+3,x1};
            case SQUARE -> new int[]{x2,y2,x2,y2+1,x2+1,y2,x2+1,y2+1};
            case H_UP_LEFT_L -> new int[]{x3,y3,x3+1,y3,x3,y3+1,x3,y3+2};
            case H_UP_RIGHT_L -> new int[]{x3,y3,x3,y3+1,x3,y3+2,x3+1,y3+2};
            case H_LOW_LEFT_L -> new int[]{x3,y3,x3+1,y3,x3+1,y3+1,x3+1,y3+2};
            case H_LOW_RIGHT_L -> new int[]{x3+1,y3,x3+1,y3+1,x3+1,y3+2,x3,y3+2};
            case V_UP_LEFT_L -> new int[]{y3,x3,y3+1,x3,y3+2,x3,y3,x3+1};
            case V_UP_RIGHT_L -> new int[]{y3,x3,y3,x3+1,y3+1,x3+1,y3+2,x3+1};
            case V_LOW_LEFT_L -> new int[]{y3,x3,y3+1,x3,y3+2,x3,y3+2,x3+1};
            case V_LOW_RIGHT_L -> new int[]{y3+2,x3,y3+2,x3+1,y3+1,x3+1,y3,x3+1};
            case VS -> new int[]{y3,x3,y3+1,x3,y3+1,x3+1,y3+2,x3+1};
            case V_MIRRORED_S -> new int[]{y3+2,x3,y3+1,x3,y3+1,x3+1,y3,x3+1};
            case HS -> new int[]{x3+1,y3,x3+1,y3+1,x3,y3+1,x3,y3+2};
            case H_MIRRORED_S -> new int[]{x3,y3,x3,y3+1,x3+1,y3+1,x3+1,y3+2};
        };
    }
}