package src.java;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Client{
    private final int port;
    private final Game game;
    private final String hostName;

    public Client(int port,String mapFile,String hostName){
        this.port = port;
        game = new Game(mapFile);
        this.hostName = hostName;
    }

    public void run(){
        try(Socket socket = new Socket(hostName,port);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8))){

            Coordinates coords = CoordinatesGenerator.getCoordinates();
            game.initializeCoordinates(coords);
            writer.write("start;" + ((char) (coords.x() + 'A')) + (coords.y() + 1) + "\n");
            writer.flush();
            game.displayMyMap();
            System.out.print("Sending message: " + "start;" + ((char) (coords.x() + 'A')) + (coords.y() + 1) + "\n");

            Mode mode = new Mode(game,socket,writer,reader);
            mode.run();

        }catch(IOException e) {
            e.printStackTrace();
        }
    }
}