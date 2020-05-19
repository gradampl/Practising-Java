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
                    65000);
            while (!finished) {
                System.out.println("\n\n 1 .  Ktora  godzina ?");
                System.out.println(" 2 .  Suma  l i c z b  w  t a b l i c y ");
                System.out.println(" 3 .  Modyfikacja tekstu ");
                System.out.println(" 4 .  Przesyłanie pliku ");
                System.out.println(" 0 .  Koniec ");

                char c = scan.nextLine().charAt(0);
                switch (c) {
                    case '1':
                        Date d = (Date) client.sendMessage(1, null, "");
                        System.out.println("Server: " + d);
                        break;
                    case '2':
                        int[] data = new int[10];
                        for (int i = 0; i < 10; i++) {
                            data[i] = i + 1;
                        }
                        Integer response = (Integer) client.sendMessage(2, data,
                                "");
                        System.out.println("Server: " + response);
                        break;
                    case '3':
                        System.out.print("Wpisz  t e k s t :  ");
                        String str = scan.nextLine();
                        String r = (String) client.sendMessage(3, str, "");
                        System.out.println("Server: " + r);
                        break;
                    case '4':
                        System.out.println("Jaki plik chcesz wysłać? Podaj pełną ścieżkę dostępu.");
                        String toSend = scan.nextLine();
                        System.out.println("Jak plik ma się nazywać na serwerze?");
                        String name = scan.nextLine();

                        byte[] array = Files.readAllBytes(Paths.get(toSend));
                        System.out.print("Server says: " + (String) client.sendMessage(4, array, name));
                        break;

                    case '0':
                        System.out.print("Server says: " + (String) client.sendMessage(0, null, ""));
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

//Result:
//
//        1 .  Ktora  godzina ?
//        2 .  Suma  l i c z b  w  t a b l i c y
//        3 .  Modyfikacja tekstu
//        4 .  Przesyłanie pliku
//        0 .  Koniec
//        1
//        Server: Tue May 19 21:27:38 CEST 2020
//
//
//        1 .  Ktora  godzina ?
//        2 .  Suma  l i c z b  w  t a b l i c y
//        3 .  Modyfikacja tekstu
//        4 .  Przesyłanie pliku
//        0 .  Koniec
//        2
//        Server: 55
//
//
//        1 .  Ktora  godzina ?
//        2 .  Suma  l i c z b  w  t a b l i c y
//        3 .  Modyfikacja tekstu
//        4 .  Przesyłanie pliku
//        0 .  Koniec
//        3
//        Wpisz  t e k s t :  dadad
//        Server: DADAD
//
//
//        1 .  Ktora  godzina ?
//        2 .  Suma  l i c z b  w  t a b l i c y
//        3 .  Modyfikacja tekstu
//        4 .  Przesyłanie pliku
//        0 .  Koniec
//        4
//        Jaki plik chcesz wysłać? Podaj pełną ścieżkę dostępu.
//        E:\\Adam\\klient\\test_1.txt
//        Jak plik ma się nazywać na serwerze?
//        renia
//        Server says: File copied to the server as renia
//
//        1 .  Ktora  godzina ?
//        2 .  Suma  l i c z b  w  t a b l i c y
//        3 .  Modyfikacja tekstu
//        4 .  Przesyłanie pliku
//        0 .  Koniec
//        0
//        Server says: bye
//        Process finished with exit code 0
