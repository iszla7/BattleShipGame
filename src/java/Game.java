package src.java;

public class Game{
    private Coordinates coords;
    MapHandler myMap;
    MapHandler opponentMap;

    public Game(String mapFile){
        myMap = new MapHandler(mapFile);
        opponentMap = new MapHandler();
    }

    public void initializeCoordinates(Coordinates coords){
        this.coords = coords;
    }

    public void displayMyMap(){
        myMap.displayMyMap();
    }

    public boolean messageIsValid(String message){
        if(message.equals("ostatni zatopiony"))
            return true;
        String[] m = message.split(";");

        if(m.length != 2)
            return false;

        String command = m[0];
        String coordinates = m[1];
        int row = coordinates.charAt(0) - 'A';
        int column = Integer.parseInt(coordinates.substring(1)) - 1;

        return (command.equals("start") || command.equals("pudło") || command.equals("trafiony") || command.equals("trafiony zatopiony")) && (row >= 0 && row <= 9) && (column >= 0 && column <= 9);

    }

    private void processCommand(String command){
        if(command.equals("start"))
            return;
        switch(command){
            case "pudło" -> opponentMap.addWater(coords);
            case "trafiony", "trafiony zatopiony" -> opponentMap.addMast(coords);
            case "ostatni zatopiony" -> {
                opponentMap.addMast(coords);
                endGame(true);
            }
        }
    }

    public String processCoordinates(String coordinates){
        Coordinates toHit = new Coordinates(coordinates.charAt(0) - 'A', Integer.parseInt(coordinates.substring(1)) - 1);
        return myMap.hit(toHit);
    }

    public String processMessage(String message){
        String response = "";

        String[] temp = message.split(";");
        processCommand(temp[0]);

        if(temp.length > 1) {
            response = processCoordinates(temp[1]);

            if(!response.equals("ostatni zatopiony\n")){
                coords = CoordinatesGenerator.getCoordinates();
                response += ";" + ((char) (coords.x() + 'A')) + (coords.y() + 1) + "\n";
            }
        }

        return response;
    }

    public void endGame(boolean win){
        if(win){
            System.out.println("Wygrana");
            opponentMap.displayOpponentMap(true);
        }
        else{
            System.out.println("Przegrana");
            opponentMap.displayOpponentMap(false);
        }

        System.out.println();

        myMap.displayMyMap();
    }


}