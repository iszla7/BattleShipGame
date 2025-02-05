package src.java;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MapHandler {
    private int ship4Mast;
    private int ship3Mast;
    private int ship2Mast;
    private int ship1Mast;
    private int ships;
    private char[][] map;

    public MapHandler(){
        map = new char[10][10];
        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++)
                map[i][j] = '?';
        }
        ships = 10;
        ship1Mast = 4;
        ship2Mast = 3;
        ship3Mast = 2;
        ship4Mast = 1;
    }

    public MapHandler(String mapFile) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(mapFile));
            char[] temp = br.readLine().toCharArray();
            br.close();

            map = new char[10][10];
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++)
                    map[i][j] = temp[i * 10 + j];
            }
        }catch(IOException e){
            e.printStackTrace();
        }

        ships = 10;
        ship1Mast = 4;
        ship2Mast = 3;
        ship3Mast = 2;
        ship4Mast = 1;
    }

    public String hit(Coordinates coords){
        int x = coords.x();
        int y = coords.y();
        char c = map[x][y];
        return switch(c){
            case '.' -> {
                addNotHit(coords);
                yield "pudło";
            }
            case '#' ->{
                boolean sunken = addHit(coords);
                if(allAreSunken())
                    yield "ostatni zatopiony\n";
                if(sunken)
                    yield "trafiony zatopiony";
                yield "trafiony";
            }
            case '~' -> "pudło";
            case '@' -> {
                if(isSunken(coords))
                    yield "trafiony zatopiony";
                yield "trafiony";
            }

            default -> "";
        };
    }

    public void addWater(Coordinates coords){
        map[coords.x()][coords.y()] = '.';
    }

    public void addMast(Coordinates coords){
        map[coords.x()][coords.y()] = '#';
    }

    public void addNotHit(Coordinates coords){
        map[coords.x()][coords.y()] = '~';
    }

    public boolean addHit(Coordinates coords){
        map[coords.x()][coords.y()] = '@';

        List<Coordinates> ship = getAllMasts(coords);
        for(Coordinates c : ship){
            int x = c.x();
            int y = c.y();
            if(map[x][y] == '#')
                return false;
        }

        ships--;
        return true;
    }

    public boolean allAreSunken(){
        return ships == 0;
        //return ship1Mast == 0 && ship2Mast == 0 && ship3Mast == 0 && ship4Mast == 0;
    }

    public void displayMyMap(){
        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                System.out.print(map[i][j]);
            }
            System.out.println();
        }
    }

    public void displayOpponentMap(boolean win){
        if(win){
            for(int i=0;i<10;i++){
                for(int j=0;j<10;j++){
                    char c = switch(map[i][j]){
                        case '?' -> '.';
                        default -> map[i][j];
                    };
                    System.out.print(c);
                }
                System.out.println();
            }
        }else{
            for(int i=0;i<10;i++){
                for(int j=0;j<10;j++){
                    System.out.print(map[i][j]);
                }
                System.out.println();
            }
        }
    }

    public boolean isSunken(Coordinates coords){
        List<Coordinates> ship = getAllMasts(coords);
        for(Coordinates c : ship){
            int x = c.x();
            int y = c.y();
            if(map[x][y] == '#')
                return false;
        }
        return true;
    }

    public List<Coordinates> getAllMasts(Coordinates coords){
        List<Coordinates> res = new ArrayList<>();
        Queue<Coordinates> temp = new LinkedList<>();
        temp.add(coords);
        boolean[][] visited = new boolean[10][10];

        while(!temp.isEmpty()){
            Coordinates c = temp.poll();
            visited[c.x()][c.y()] = true;
            temp.addAll(getAllSurroundingMasts(c,visited));
            res.add(c);
        }

        return res;
    }

    public List<Coordinates> getAllSurroundingMasts(Coordinates c,boolean[][] visited){
        List<Coordinates> res = new ArrayList<>();
        int x = c.x();
        int y = c.y();
        if(x == 0 && y == 0){
            if((map[x][y + 1] == '#' || map[x][y + 1] == '@') && !visited[x][y + 1])
                res.add(new Coordinates(x,y + 1));

            if((map[x + 1][y] == '#' || map[x + 1][y] == '@') && !visited[x + 1][y])
                res.add(new Coordinates(x + 1,y));
        }else if(x == 0 && y == 9){
            if((map[x][y - 1] == '#' || map[x][y - 1] == '@') && !visited[x][y - 1])
                res.add(new Coordinates(x,y - 1));

            if((map[x + 1][y] == '#' || map[x + 1][y] == '@') && !visited[x + 1][y])
                res.add(new Coordinates(x + 1,y));
        }else if(x == 0){
            if((map[x][y - 1] == '#' || map[x][y - 1] == '@')  && !visited[x][y - 1])
                res.add(new Coordinates(x,y - 1));

            if((map[x][y + 1] == '#' || map[x][y + 1] == '@') && !visited[x][y + 1])
                res.add(new Coordinates(x,y + 1));

            if((map[x + 1][y] == '#' || map[x + 1][y] == '@') && !visited[x + 1][y])
                res.add(new Coordinates(x + 1,y));
        }else if(x == 9 && y == 0){
            if((map[x][y + 1] == '#' || map[x][y + 1] == '@') && !visited[x][y + 1])
                res.add(new Coordinates(x,y + 1));

            if((map[x - 1][y] == '#' || map[x - 1][y] == '@') && !visited[x - 1][y])
                res.add(new Coordinates(x - 1,y));
        }else if(x == 9 && y == 9){
            if((map[x][y - 1] == '#' || map[x][y - 1] == '@') && !visited[x][y - 1])
                res.add(new Coordinates(x,y - 1));

            if((map[x - 1][y] == '#' || map[x - 1][y] == '@') && !visited[x - 1][y])
                res.add(new Coordinates(x - 1,y));
        }else if(x == 9){
            if((map[x][y - 1] == '#' || map[x][y - 1] == '@') && !visited[x][y - 1])
                res.add(new Coordinates(x,y - 1));

            if((map[x - 1][y] == '#' || map[x - 1][y] == '@') && !visited[x - 1][y])
                res.add(new Coordinates(x - 1,y));

            if((map[x][y + 1] == '#' || map[x][y + 1] == '@') && !visited[x][y + 1])
                res.add(new Coordinates(x,y + 1));
        }else if(y == 0){
            if((map[x - 1][y] == '#' || map[x - 1][y] == '@') && !visited[x - 1][y])
                res.add(new Coordinates(x - 1,y));

            if((map[x][y + 1] == '#' || map[x][y + 1] == '@') && !visited[x][y + 1])
                res.add(new Coordinates(x,y + 1));

            if((map[x + 1][y] == '#' || map[x + 1][y] == '@') && !visited[x + 1][y])
                res.add(new Coordinates(x + 1,y));
        }else if(y == 9){
            if((map[x][y - 1] == '#' || map[x][y - 1] == '@') && !visited[x][y - 1])
                res.add(new Coordinates(x,y - 1));

            if((map[x - 1][y] == '#' || map[x - 1][y] == '@') && !visited[x - 1][y])
                res.add(new Coordinates(x - 1,y));

            if((map[x + 1][y] == '#' || map[x + 1][y] == '@') && !visited[x + 1][y])
                res.add(new Coordinates(x + 1,y));

        } else{
            if((map[x][y - 1] == '#' || map[x][y - 1] == '@') && !visited[x][y - 1])
                res.add(new Coordinates(x,y - 1));

            if((map[x - 1][y] == '#' || map[x - 1][y] == '@') && !visited[x - 1][y])
                res.add(new Coordinates(x - 1,y));

            if((map[x][y + 1] == '#' || map[x][y + 1] == '@') && !visited[x][y + 1])
                res.add(new Coordinates(x,y + 1));

            if((map[x + 1][y] == '#' || map[x + 1][y] == '@') && !visited[x + 1][y])
                res.add(new Coordinates(x + 1,y));
        }

        return res;
    }
}
