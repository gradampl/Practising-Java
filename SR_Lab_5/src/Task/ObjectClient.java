package Task;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Optional;
import java.util.Scanner;

public class ObjectClient {
    Socket serverConn;
    ObjectInputStream inStream = null;
    ObjectOutputStream outStream = null;

    public ObjectClient(String host, int port) throws UnknownHostException, IOException {
        serverConn = new Socket(host, port);
        outStream = new ObjectOutputStream(serverConn.getOutputStream());
        inStream = new ObjectInputStream(serverConn.getInputStream());
    }

    public Object sendMessage(int message_id, Object
            message, String name) throws Exception {

        outStream.writeInt(message_id);
        outStream.writeObject(message);
        outStream.writeUTF(name);
        outStream.flush();
        Object response = inStream.readObject();
        return response;
    }

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        boolean finished = false;

        try {
            ObjectClient client = new ObjectClient("localhost",
                    65000+1);
            while (!finished) {
                System.out.println("\n\n 1 .  Send a file to the server");
                System.out.println(" 2 .  Check if the file is on the server");
                System.out.println(" 3 .  Check if it is exactly the same file");
                System.out.println(" 4 .  Get your file back from the server");
                System.out.println(" 0 .  Quit\n");

                String path = "E:\\Adam\\klient\\";
                char c = scan.nextLine().charAt(0);
                switch (c) {
                    case '1':
                        System.out.println("Jaki plik chcesz wysłać?");
                        String toSend = scan.nextLine();
                        System.out.println("Jak plik ma się nazywać na serwerze?");
                        String name = scan.nextLine();

                        byte[] array = Files.readAllBytes(Paths.get(path+toSend));
                        System.out.print("Server says: " + (String) client.sendMessage(1, array, name));
                        break;

                    case '2':
                        System.out.println("Podaj nazwę pliku, a sprawdzę, czy jest taki na serwerze.");
                        String toCheck = scan.nextLine();
                        System.out.print("Server says: " +
                                (String) client.sendMessage(2, null, toCheck));
                        break;

                    case '3':
                        System.out.println("Podaj nazwę pliku i nazwę jego kopii na serwerze," +
                                " a ja sprawdzę, czy są to identyczne pliki.");
                        System.out.println("Najpierw nazwa pliku na Twoim komputerze: ");
                        String yourFile = scan.nextLine();
                        System.out.println("Teraz nazwa kopii na serwerze: ");
                        String yourCopy = scan.nextLine();
                        File file = new File(path + yourFile);
                        byte[] ctrlClient = ControlSum.MD5.checksum(file);
                        System.out.print("Server says: " +
                                (String) client.sendMessage(3, ctrlClient, yourCopy));
                        break;

                    case '4':
                        System.out.println("Podaj nazwę kopii, a ja pobiorę ją z serwera.");
                        String toGet = scan.nextLine();
                        System.out.println("Pod jaką nazwą mam ją zapisać?");
                        String toSave = scan.nextLine();
                        String serverSays = (String)client.sendMessage(4,null,toGet);
                        if(serverSays!=""){
                            System.out.println(serverSays);
                            break;
                        }
                        else{
                            Object serverResponse = client.sendMessage(4,null,toGet);
                            byte[] array1 = (byte[]) serverResponse;
                            ByteToFile.FILEPATH = path+toSave;
                            ByteToFile.file = new File(ByteToFile.FILEPATH);
                            ByteToFile.writeByte(array1);
                            break;
                        }

                    case '0':
                        System.out.print("Server says: " + (String) client.sendMessage(0, null, ""));
                        client.serverConn.close();
                        finished = true;
                        break;

                    default:
                        System.out.print("Nie ma takiej opcji.");
                }
            }
        } catch (Exception e) {
            System.out.println("Exception " + e);
            e.printStackTrace();
        }
    }
}