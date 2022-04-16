package cs111;

import java.io.*;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class WPMProgram {

    public static void main(String[] args) throws Exception {

        System.out.println("Hi, welcome to type racer");
        TimeUnit.SECONDS.sleep(1);
        System.out.println("How many words do you want?");
        Scanner scannedAmount = new Scanner(System.in);
        int amount = scannedAmount.nextInt();

        /**
         * COUNTDOWN
         */
        System.out.println("3");
        TimeUnit.SECONDS.sleep(1);
        System.out.println("2");
        TimeUnit.SECONDS.sleep(1);
        System.out.println("1");
        TimeUnit.SECONDS.sleep(1);

        StringJoiner desiredString = new StringJoiner(" ");
        for (int i = 1; i <= amount; i++) {
            String line = getRandomLineFromTheFile(
                    "/Users/alexandreballenghien/Documents/WPM-TypeRacer/src/words.txt");
            System.out.print(line + " ");
            desiredString.add(line);
        }
        System.out.println();

        Scanner scan = new Scanner(System.in);
        double start = LocalTime.now().toNanoOfDay();

        String typedWords = scan.nextLine();

        double end = LocalTime.now().toNanoOfDay();

        double elapsedTime = end - start;
        double seconds = elapsedTime / 1000000000.0;
        int numChars = typedWords.length();

        int wpm = (int) ( ( ( (double) numChars / 5 ) / seconds ) * 60 );

        if (Objects.equals(typedWords, desiredString.toString())) {
            System.out.println("Your speed is " + wpm + " wpm");
        } else {
            System.out.println("Bro, you dog shit");
        }
    }

    public static String getRandomLineFromTheFile(String filePathWithFileName) throws Exception {

        File file = new File("/Users/alexandreballenghien/Documents/WPM-TypeRacer/src/words.txt");
        final RandomAccessFile f = new RandomAccessFile(file, "r");
        final long randomLocation = (long) (Math.random() * f.length());
        f.seek(randomLocation);
        f.readLine();
        String randomLine = f.readLine();
        f.close();
        return randomLine;
    }

    /**
    public static void htmlWriter(String string) {

        //String html = "<div><h1> This is test HTML</h1><p> This is a test paragraph for" + "our html page</p></div>";

        File f = new File("/Users/alexandreballenghien/Desktop");
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(f));
            bufferedWriter.write(string);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    **/

}
