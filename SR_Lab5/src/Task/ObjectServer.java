package Task;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;

public class ObjectServer extends Thread {
    ServerSocket clientConn;
    ArrayList<String> filesOnServer = new ArrayList<String>();
    private int serverID;

    public ObjectServer(int port, int serverID) {
        this.serverID = serverID;
        System.out.println("Server " + serverID + " connecting to port  " + port);
        try {
            clientConn = new ServerSocket(port);
        } catch (Exception e) {
            System.out.println("Exception : " + e);
            System.exit(1);
        }
    }

    public void run() {
        try {
            System.out.println("Waiting for clients . . . ");
            while (true) {
                Socket clientReq = clientConn.accept();
                System.out.println("Connection from "
                        + clientReq.getInetAddress().getHostName());
                serviceClient(clientReq);
            }
        } catch (IOException e) {
            System.out.println("Exception: " + e);
        }
    }


    public static void main(String[] args) {
        int port = 65000;
        final int SERVERS_NUM = 2;

        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (Exception e) {
                port = 3000;
            }
        }

        ObjectServer[] servers = new ObjectServer[SERVERS_NUM+1];

        for(int i = 1; i<=SERVERS_NUM; i++){
            servers[i] = new ObjectServer(port+i,i);
            System.out.println("Server " +i+" running on port  " + port+i);
            servers[i].start();
        }

    }

//    public void listen() {
//        try {
//            System.out.println("Waiting for clients . . . ");
//            while (true) {
//                Socket clientReq = clientConn.accept();
//                System.out.println("Connection from "
//                        + clientReq.getInetAddress().getHostName());
//                serviceClient(clientReq);
//            }
//        } catch (IOException e) {
//            System.out.println("Exception: " + e);
//        }
//    }


    public void serviceClient(Socket s) throws IOException {
        ObjectOutputStream outStream;
        ObjectInputStream inStream;


        try {
            outStream = new ObjectOutputStream(s.getOutputStream());
            inStream = new ObjectInputStream(s.getInputStream());
            int message_id;
            Object message = null;
            String name = null;
            boolean finished = false;

            while (!finished) {
                message_id = inStream.readInt();
                System.out.println("Got message  " + message_id);
                message = inStream.readObject();
                name = inStream.readUTF();
                String path = "E:\\Adam\\serwer\\";


                switch (message_id) {

                    case 1:
                        if (!filesOnServer.contains(name)) {
                            byte[] array = (byte[]) message;
                            ByteToFile.FILEPATH = path + name;
                            ByteToFile.file = new File(ByteToFile.FILEPATH);
                            ByteToFile.writeByte(array);
                            outStream.writeObject("File copied to the server as " + name);
                            filesOnServer.add(name);
                        } else {
                            outStream.writeObject("File " + name + " " +
                                    "already exists on the server. " +
                                    "Please choose another name.");
                        }
                        outStream.flush();
                        break;
                    case 2:
                        if (!filesOnServer.contains(name)) {
                            outStream.writeObject("File " + name + " " +
                                    "DOES NOT exist on the server.");
                        } else {
                            outStream.writeObject("File " + name + " " +
                                    "already exists on the server.");
                        }
                        outStream.flush();
                        break;
                    case 3:
                        if (filesOnServer.contains(name)) {
                            byte[] ctrlClient = (byte[]) message;
                            File file = new File(path + name);
                            byte[] ctrl = ControlSum.MD5.checksum(file);
                            boolean fileDiffers = false;

                            if (ctrl.length != ctrlClient.length) {
                                outStream.writeObject("File " + name + " " +
                                        "IS NOT the same as yours.");
                                fileDiffers = true;
                            } else {
                                for (int i = 0; i <= ctrlClient.length - 1; i++) {
                                    if (ctrlClient[i] != ctrl[i]) {
                                        outStream.writeObject("File " + name + " " +
                                                "IS NOT the same as yours.");
                                        fileDiffers = true;
                                        break;
                                    }
                                }
                            }
                            if (!fileDiffers) {
                                outStream.writeObject("File " + name + " " +
                                        "is an EXACT COPY of your file.");
                            }
                        } else {
                            outStream.writeObject("File " + name + " " +
                                    "DOES NOT exist on the server.");
                        }
                        outStream.flush();
                        break;

                    case 4:
                        byte[] array = Files.readAllBytes(Paths.get(path+name));
                        outStream.writeObject(array);
                        outStream.flush();
                        break;

                    case 0:
                        outStream.writeObject("bye");
                        finished = true;
                        break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Done.");
    }
}
