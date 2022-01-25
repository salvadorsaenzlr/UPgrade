/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.threading;

/**
 *
 * @author a838595
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;


public class DownloaderThread implements Runnable {

    String[] urlsList;

    public DownloaderThread(String[] urlsList) {
        this.urlsList = urlsList;
    }

    @Override
    public void run() {

        try {

            for(String urlString : urlsList) {

                URL url = new URL(urlString);
                String filename = urlString.substring(urlString.lastIndexOf("/") + 1).trim() + ".iso";
                BufferedReader reader =  new BufferedReader(new InputStreamReader(url.openStream()));
                BufferedWriter writer = new BufferedWriter(new FileWriter(filename));

                String line;
                URLConnection urlConnection=url.openConnection();
                urlConnection.connect();
                int file_s=urlConnection.getContentLength();



                while ((line = reader.readLine()) != null) {
                    writer.write(line);
                }
                System.out.println("Page downloaded to " + filename + "||"+file_s);

                writer.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String args[])
    {

        String[] urls1 = new String[]{"https://downloads-global.3cx.com/downloads/debian10iso/debian-amd64-netinst-3cx"};
        String[] urls2 = new String[]{"https://downloads-global.3cx.com/downloads/debian10iso/debian-amd64-netinst-3cx"};


        Thread downloaderOne = new Thread(new DownloaderThread(Arrays.copyOfRange(urls1, 0, urls1.length)));
        Thread downloaderTwo = new Thread(new DownloaderThread(Arrays.copyOfRange(urls2, 0, urls2.length)));

        try {

            long startTime = System.currentTimeMillis();
            downloaderOne.start();
            downloaderTwo.start();

            downloaderOne.join();
            downloaderTwo.join();
            long endTime = System.currentTimeMillis();

            System.out.println("Total time taken: " + (endTime - startTime) / 1000 + "s");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
