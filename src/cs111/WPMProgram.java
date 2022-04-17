package cs111;

import javax.sound.midi.Soundbank;
import java.io.*;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class WPMProgram {
    public WPMProgram() { }

    public static void main(String[] args) throws Exception {
        StringBuffer desiredMutableString = new StringBuffer("");
        int amountOfTime;
        int amountOfWords;
        String TIME = "time";
        String AMOUNT = "amount";

        System.out.println("Hi, welcome to type racer");
        TimeUnit.SECONDS.sleep(1);
        System.out.println("-time- or -amount- ?");
        Scanner scannedCategory = new Scanner(System.in);
        String category = scannedCategory.nextLine();

        if (Objects.equals(TIME, category)) {
            System.out.println("How much time do you want?");
            Scanner scannedAmount = new Scanner(System.in);
            amountOfTime = scannedAmount.nextInt();
            sleep();
            getStringForRace(50, desiredMutableString);
            getWPMByTime(String.valueOf(desiredMutableString), amountOfTime);
        } else if (Objects.equals(AMOUNT,category)) {
            System.out.println("How many words do you want?");
            Scanner scannedAmount = new Scanner(System.in);
            amountOfWords = scannedAmount.nextInt();
            sleep();
            getStringForRace(amountOfWords, desiredMutableString);
            getWPMByAmount(String.valueOf(desiredMutableString));
        }
    }

    public static String getRandomLineFromTheFile() throws Exception {
        File file = new File(
                "/Users/alexandreballenghien/Documents/WPM-TypeRacer/src/words.txt");
        final RandomAccessFile f = new RandomAccessFile(file, "r");
        final long randomLocation = (long) (Math.random() * f.length());
        f.seek(randomLocation);
        f.readLine();
        String randomLine = f.readLine();
        f.close();
        return randomLine;
    }

    public static void getStringForRace(int amount, StringBuffer desiredMutableString) throws Exception {
        StringJoiner desiredStringJoiner = new StringJoiner(" ");
        for (int i = 1; i <= amount; i++) {
            String line = getRandomLineFromTheFile();
            System.out.print(line + " ");
            desiredStringJoiner.add(line);
        }
        desiredMutableString.append(desiredStringJoiner);
        System.out.println();
    }

    public static void sleep() throws InterruptedException {
        System.out.println("3");
        TimeUnit.SECONDS.sleep(1);
        System.out.println("2");
        TimeUnit.SECONDS.sleep(1);
        System.out.println("1");
        TimeUnit.SECONDS.sleep(1);
    }

    public static void getWPMByTime(String desiredString, long time) throws InterruptedException {
        Scanner scan = new Scanner(System.in);
        Thread.sleep(time * 1000);
        String typedWords = scan.nextLine();

        int numChars = typedWords.length();
        int wpm = (int) ((((double) numChars / 5) / (double) time) * 60);
        System.out.println("Your speed is " + wpm + " wpm");
        if (typedWords.contains(desiredString)) {
            System.out.println("Your speed is " + wpm + " wpm");
        } else {
            System.out.println("Damn boi you no good");
        }
    }

    public static void getWPMByAmount(String desiredString) {
        Scanner scan = new Scanner(System.in);
        double start = LocalTime.now().toNanoOfDay();
        String typedWords = scan.nextLine();
        double end = LocalTime.now().toNanoOfDay();
        double elapsedTime = end - start;
        double seconds = elapsedTime / 1000000000.0;
        int numChars = typedWords.length();
        int wpm = (int) ((((double) numChars / 5 ) / seconds ) * 60 );
        if (Objects.equals(typedWords, desiredString)) {
            System.out.println("Your speed is " + wpm + " wpm");
        } else {
            System.out.println("Bro, you dog shit");
        }
    }
}
