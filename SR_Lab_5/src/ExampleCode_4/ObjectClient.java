package ExampleCode_4;

import ExampleCode.SocketClient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;
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
            message) throws Exception {

        outStream.writeInt(message_id);
        outStream.writeObject(message);
        outStream.flush();
        Object response = inStream.readObject();
        return response;
    }

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        boolean finished = false;

        try {
            ObjectClient client = new ObjectClient("localhost",
                    65000);
            while (!finished) {
                System.out.println("\n\n1 .  Ktora  godzina ?");
                System.out.println(" 2 .  Suma  l i c z b  w  t a b l i c y ");
                System.out.println(" 3 .  Modyfikacja tekstu ");
                System.out.println(" 0 .  Koniec ");

                char c = scan.nextLine().charAt(0);
                switch (c) {
                    case '1':
                        Date d = (Date) client.sendMessage(1, null);
                        System.out.println("Server: " + d);
                        break;
                    case '2':
                        int[] data = new int[10];
                        for (int i = 0; i < 10; i++) {
                            data[i] = i + 1;
                        }
                        Integer response = (Integer) client.sendMessage(2, data);
                        System.out.println("Server: " + response);
                        break;
                    case '3':
                        System.out.print("Wpisz  t e k s t :  ");
                        String str = scan.nextLine();
                        String r = (String) client.sendMessage(3,
                                str);
                        System.out.println("Server: " + r);
                        break;
                    case '0':
                        System.out.print("Server says: " + (String) client.sendMessage(0, null));
                        client.serverConn.close();
                        finished = true;
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println("Exception " + e);
            e.printStackTrace();
        }
    }

}
