package src.java;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Server{
    private final int port;
    private final Game game;

    public Server(int port,String mapFile){
        this.port = port;
        game = new Game(mapFile);
    }

    public void run(){
        try(ServerSocket serverSocket = new ServerSocket(port)){
            Socket socket = serverSocket.accept();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));

            Mode mode = new Mode(game,socket,writer,reader);
            game.displayMyMap();
            mode.run();
        }catch(IOException e) {
            e.printStackTrace();
        }
    }
}
