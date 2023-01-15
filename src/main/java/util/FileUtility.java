/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.imageio.ImageIO;
import lombok.SneakyThrows;

/**
 *
 * @author Umman Hasan
 */
public class FileUtility
{

    @SneakyThrows
    private static void write(String fileName, String text, boolean append) {
        try ( BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, append));) {
            bw.write(text);
        }
    }

    @SneakyThrows
    public static void writeIntoFile(String fileName, String text) {
        write(fileName, text, false); // text silib uzerine yenisini yazir
    }

    @SneakyThrows
    public static void appendIntoFile(String fileName, String text) {
        write(fileName, text, true); // text silmeden ardiyca yenisini yazir
    }

    @SneakyThrows
    public static void writeToFile2(String fileName, byte[] data) {

        File file = new File(fileName);
        FileOutputStream fop = new FileOutputStream(file);

        fop.write(data);
        fop.flush();
        fop.close();

        System.out.println("Done");

    }

    @SneakyThrows
    public static String read(String fileName) {
        InputStream in = new FileInputStream(fileName);
        InputStreamReader r = new InputStreamReader(in);
        BufferedReader reader = new BufferedReader(r);

        String line = null;
        String result = "";
        while ((line = reader.readLine()) != null) {
            result += line + "\n";
        }
        return result;
    }

    @SneakyThrows
    public static byte[] readBytes(String fileName) {
        // File() closeable olmadigi ucun try-dan cole atiriq
        File file = new File(fileName);
        try ( FileInputStream fileInputStream = new FileInputStream(file);) {

            byte[] bytesArray = new byte[(int) file.length()];

            // read file into bytes[]
            fileInputStream.read(bytesArray);
            return bytesArray;
        }
    }

    @SneakyThrows
    public static void writeBytes(byte[] data, String fileName) {

        FileOutputStream fop = new FileOutputStream(fileName);

        // get the content in byte
        fop.write(data);
        fop.flush();
        fop.close();

        System.out.println("Done");

    }

    @SneakyThrows
    public static Object readFileDeserialize(String name) {
        Object obj = null;
        // name -den istifade ederek FileInputStream duzelir ve bunu gonderirik ObjectInputStream-e
        try ( ObjectInputStream in = new ObjectInputStream(new FileInputStream(name))) {
            obj = in.readObject();
        } finally {
            return obj;
        }

    }

// Serializable olmalidi mutleq obyektini gonderirik ve faylin adini gonderirik ki, bu fayla yaz
    @SneakyThrows
    public static void writeObjectToFile(Serializable object, String name) {

        try ( // faylin adindan FileOutputStream  duzeldir
                 FileOutputStream fout = new FileOutputStream(name); // fout-dan da bir ObjectOutputStream duzeldir
                  ObjectOutputStream oos = new ObjectOutputStream(fout);) {
            // ObjectOutputStream deyirik object-i write ele
            oos.writeObject(object);
        }

    }

    @SneakyThrows
    public static void writeBytesNio(byte[] data, String fileName) {
//        Path filePath = FileSystems.getDefault().getPath(fileName);// kohne method
        // fileName-den istifade ederek Path duzeldirik
        Path filePath = Paths.get(fileName);
        //  Files-in write() methodundan istifade ederek datani bura yaziriq
        Files.write(filePath, data);

    }

    @SneakyThrows
    public static byte[] readBytesNio(String fileName) {
        // fileName-den istifade ederek Path duzeldirik
        Path filePath = Paths.get(fileName);
        // byte-lari read edib gonderir geriye
        return Files.readAllBytes(filePath);
    }

    @SneakyThrows
    public static void download(String fromFile, String toFile) {
        // fromFile -dan bir URL duzelir website adinda
        URL website = new URL(fromFile);

        URL url = new URL(fromFile);

        URLConnection con = url.openConnection();
        con.setConnectTimeout(10000);
        con.setReadTimeout(10000);
        InputStream in = con.getInputStream();

        ReadableByteChannel rbc = Channels.newChannel(in);

        FileOutputStream fos = new FileOutputStream(toFile);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        fos.close();
        rbc.close();

    }

//    @SneakyThrows
//    public static byte[] byteArrayToImage(byte[] b) {
//
//        BufferedImage bImage = ImageIO.read(new File("paren-s-katanoi-art-3.png"));
//        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//
//        ImageIO.write(bImage, "png", bos);
//        byte[] data = bos.toByteArray();
//        ByteArrayInputStream bis = new ByteArrayInputStream(data);
//        BufferedImage bImage2 = ImageIO.read(bis);
//
//        ImageIO.write(bImage2, "jpg", new File("output.jpg"));
//        System.out.println("image created");
//        return null;
//
//    }
}
