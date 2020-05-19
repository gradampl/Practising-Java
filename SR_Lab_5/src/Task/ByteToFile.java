// Taken from:
// https://www.geeksforgeeks.org/convert-byte-array-to-file-using-java/


package Task;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class ByteToFile {
    // Path of a file
    static String FILEPATH = "";
    static File file = new File(FILEPATH);

    // Method which write the bytes into a file
    static void writeByte(byte[] bytes) {
        try {

            // Initialize a pointer
            // in file using OutputStream
            OutputStream
                    os
                    = new FileOutputStream(file);

            // Starts writing the bytes in it
            os.write(bytes);
            System.out.println("Successfully"
                    + " byte inserted");

            // Close the file
            os.close();
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }
}
