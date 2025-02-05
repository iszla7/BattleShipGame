package src.java.map;

public interface BattleshipGenerator {

    String generateMap();

    static BattleshipGenerator defaultInstance() {
        return new MapGenerator();
    }

}

class MapGenerator implements BattleshipGenerator{
    String s;

    MapGenerator(){
        s="";
        for(int i=0;i<100;i++)
            s+=".";
    }

    @Override
    public String generateMap(){
        place4MastShip();

        for(int i=0;i<2;i++)
            place3MastShip();

        for(int i=0;i<3;i++)
            place2MastShip();

        for(int i=0;i<4;i++)
            place1MastShip();

        return s;
    }

    public void place1MastShip(){
        boolean placed=false;
        int x1=0;
        int y1=0;
        while(!placed){
            x1 = (int) (Math.random()*10);
            y1 = (int) (Math.random()*10);
            if(canBePlaced(x1,y1))
                placed=true;
        }
        s=s.substring(0,x1*10+y1)+"#"+s.substring(x1*10+y1+1);
    }

    public void place2MastShip(){
        boolean placed=false;
        int[] coordinates;
        while(!placed) {
            coordinates = Orientation2Mast.randomOrientation();

            if(canBePlaced(coordinates[0],coordinates[1]) && canBePlaced(coordinates[2],coordinates[3])) {
                placed = true;
                s=s.substring(0,coordinates[0]*10+coordinates[1])+"#"+s.substring(coordinates[0]*10+coordinates[1]+1);
                s=s.substring(0,coordinates[2]*10+coordinates[3])+"#"+s.substring(coordinates[2]*10+coordinates[3]+1);
            }
        }
    }

    public void place3MastShip(){
        boolean placed=false;
        int[] coordinates;
        while(!placed) {
            coordinates = Orientation3Mast.randomOrientation();

            if(canBePlaced(coordinates[0],coordinates[1]) && canBePlaced(coordinates[2],coordinates[3]) && canBePlaced(coordinates[4],coordinates[5])) {
                placed = true;
                s=s.substring(0,coordinates[0]*10+coordinates[1])+"#"+s.substring(coordinates[0]*10+coordinates[1]+1);
                s=s.substring(0,coordinates[2]*10+coordinates[3])+"#"+s.substring(coordinates[2]*10+coordinates[3]+1);
                s=s.substring(0,coordinates[4]*10+coordinates[5])+"#"+s.substring(coordinates[4]*10+coordinates[5]+1);
            }

        }
    }

    public void place4MastShip(){
        boolean placed=false;
        int[] coordinates;
        while(!placed) {
             coordinates = Orientation4Mast.randomOrientation();

            if(canBePlaced(coordinates[0],coordinates[1]) && canBePlaced(coordinates[2],coordinates[3]) && canBePlaced(coordinates[4],coordinates[5]) && canBePlaced(coordinates[6],coordinates[7])) {
                placed = true;
                s=s.substring(0,coordinates[0]*10+coordinates[1])+"#"+s.substring(coordinates[0]*10+coordinates[1]+1);
                s=s.substring(0,coordinates[2]*10+coordinates[3])+"#"+s.substring(coordinates[2]*10+coordinates[3]+1);
                s=s.substring(0,coordinates[4]*10+coordinates[5])+"#"+s.substring(coordinates[4]*10+coordinates[5]+1);
                s=s.substring(0,coordinates[6]*10+coordinates[7])+"#"+s.substring(coordinates[6]*10+coordinates[7]+1);
            }

        }
    }

    public boolean canBePlaced(int x,int y){
        if(s.charAt(x*10+y)=='.' && notAdjacentToShip(x,y))
            return true;
        return false;
    }

    public boolean notAdjacentToShip(int x,int y){
        if(noLeftAdjacency(x,y) && noRightAdjacency(x,y) && noUpperAdjacency(x,y) && noLowerAdjacency(x,y) && noUpperLeftAngleAdjacency(x,y) && noLowerLeftAngleAdjacency(x,y) && noUpperRightAngleAdjacency(x,y) && noLowerRightAngleAdjacency(x,y))
            return true;
        return false;
    }

    public boolean noLeftAdjacency(int x,int y){
        if(x==0 || (y<9 && s.charAt((x-1)*10+y) == '.') || (y==9 && s.charAt((x-1)*10+y) == '.'))
            return true;
        return false;
    }

    public boolean noRightAdjacency(int x,int y){
        if(x==9 || (y<9 && s.charAt((x+1)*10+y) == '.') || (y==9 && s.charAt((x+1)*10+y) == '.'))
            return true;
        return false;
    }

    public boolean noUpperAdjacency(int x,int y){
        if(y==0 || (x<9 && s.charAt(x*10+y-1) == '.') || (x==9 && s.charAt(x*10+y-1) == '.'))
            return true;
        return false;
    }

    public boolean noLowerAdjacency(int x,int y){
        if(y==9 || (x<9 && s.charAt(x*10+y+1) == '.') || (x==9 && s.charAt(x*10+y+1) == '.'))
            return true;
        return false;
    }

    public boolean noUpperLeftAngleAdjacency(int x,int y){
        if(x==0 || y==0 || (s.charAt((x-1)*10+y-1) == '.'))
            return true;
        return false;
    }

    public boolean noUpperRightAngleAdjacency(int x,int y){
        if(x==9 || y==0 || (s.charAt((x+1)*10+y-1) == '.'))
            return true;
        return false;
    }

    public boolean noLowerLeftAngleAdjacency(int x,int y){
        if(x==0 || y==9 || (s.charAt((x-1)*10+y+1) == '.'))
            return true;
        return false;
    }

    public boolean noLowerRightAngleAdjacency(int x,int y){
        if(x==9 || y==9 || (s.charAt((x+1)*10+y+1) == '.'))
            return true;
        return false;
    }

}
