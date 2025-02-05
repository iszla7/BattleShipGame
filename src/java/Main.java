package src.java;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        //parsing arguments
        Map<String,String> arguments = new HashMap<>();
        for(int i=0;i<args.length - 1;i++){
            if(args[i].startsWith("-"))
                arguments.put(args[i],args[i+1]);
        }

        int port = Integer.parseInt(arguments.get("-port"));
        String mapFile = arguments.get("-map");
        if(arguments.get("-mode").equals("client")){
            String hostName = arguments.get("-host");
            Client client = new Client(port,mapFile,hostName);
            client.run();
        }else{
            Server server = new Server(port, mapFile);
            server.run();
        }
    }
}