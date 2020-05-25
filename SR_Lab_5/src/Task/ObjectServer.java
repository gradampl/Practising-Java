package Task;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;

public class ObjectServer {
    ServerSocket clientConn;
    ArrayList<String> filesOnServer = new ArrayList<String>();

    //ObjectOutputStream out;
    //Object InputStream in;
    public ObjectServer(int port) {
        System.out.println("Server connecting to port  " + port);
        try {
            clientConn = new ServerSocket(port);
        } catch (Exception e) {
            System.out.println("Exception : " + e);
            System.exit(1);
        }
    }


    public static void main(String[] args) {
        int port = 65000;
        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (Exception e) {
                port = 3000;
            }
        }

        ObjectServer server = new ObjectServer(port);
        System.out.println("Server running on port  " + port);
        server.listen();
    }

    public void listen() {
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
                        if (filesOnServer.contains(name)) {
                            String toSend = path + name;
                            byte[] array = Files.readAllBytes(Paths.get(toSend));
                            outStream.writeObject(array);
                        } else {
                            outStream.writeObject("File " + name + " " +
                                    "DOES NOT exist on the server.");
                        }
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


//Result:
//
//        Server connecting to port  65000
//        Server running on port  65000
//        Waiting for clients . . .
//        Connection from 127.0.0.1
//        Got message  1
//        Got message  2
//        Got message  3
//        Got message  4
//        Successfully byte inserted
//        Done.
