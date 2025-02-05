package src.java;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class Mode {
    private final Socket socket;
    private final Game game;
    private String lastResponse;
    private final BufferedReader reader;
    private final BufferedWriter writer;

    public Mode(Game game,Socket socket,BufferedWriter writer,BufferedReader reader){
        this.game = game;
        this.socket = socket;
        lastResponse = "NO";
        this.writer = writer;
        this.reader = reader;
    }

    public void run() throws IOException {
        int count = 0;
        while(true) {

            try {
                socket.setSoTimeout(1000);

                //receiving message
                String message = reader.readLine();
                if (!game.messageIsValid(message)) {
                    count++;

                    if(count >= 3){
                        System.out.println("Błąd komunikacji");
                        break;
                    }

                    if(!lastResponse.equals("NO")) {
                        System.out.print("Sending message: " + lastResponse);
                        writer.write(lastResponse);
                        writer.flush();
                    }

                    continue;
                }

                //received message is valid
                System.out.println("Received message: " + message);
                count = 0;

                lastResponse = game.processMessage(message);

                //end game if last ship is sunken
                if (message.equals("ostatni zatopiony"))
                    break;

                System.out.print("Sending message: " + lastResponse);
                writer.write(lastResponse);
                writer.flush();

                //end game if last ship is sunken
                if(lastResponse.equals("ostatni zatopiony\n")) {
                    game.endGame(false);
                    break;
                }

            }catch(SocketTimeoutException e){
                count++;
                if(count >= 3){
                    System.out.println("Błąd komunikacji");
                    break;
                }

                if(!lastResponse.equals("NO")) {
                    System.out.print("Sending message: " + lastResponse);
                    writer.write(lastResponse);
                    writer.flush();
                }
            }
        }
    }

}