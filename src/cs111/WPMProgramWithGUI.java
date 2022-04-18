package cs111;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.io.File;
import java.io.RandomAccessFile;

import java.util.*;

import javax.swing.*;

public final class WPMProgramWithGUI implements ActionListener, KeyListener {

    private static StringBuffer desiredMutableString = new StringBuffer();
    private static JTextField textField;
    private static JLabel wordsToWrite;
    private static JTextField type;
    private static JLabel wpm;

    private static double startTime;
    private static double endTime;

    private static boolean first = true;

    public WPMProgramWithGUI() { }

    public static void main(String[] args) {

        Font f1 = new Font(Font.SANS_SERIF,  Font.BOLD, 15);
        Font f2 = new Font(Font.DIALOG, Font.PLAIN, 30);
        Font f3 = new Font(Font.SANS_SERIF, Font.ITALIC, 25);
        Font f4 = new Font(Font.SANS_SERIF,  Font.BOLD, 28);

        JPanel panel = new JPanel();
        JFrame frame = new JFrame();
        frame.setSize(500,500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        panel.setLayout(null);

        JLabel greetings = new JLabel("Welcome to type racer");
        greetings.setBounds(400,110,1000,30);
        panel.add(greetings);
        greetings.setFont(f2);

        JLabel label = new JLabel("How many words?");
        label.setBounds(300,160,200,25);
        panel.add(label);
        label.setFont(f1);

        JLabel wordsToType = new JLabel("Words to type :");
        wordsToType.setBounds(300,250,200,25);
        panel.add(wordsToType);
        wordsToType.setFont(f1);

        textField = new JTextField();
        textField.setBounds(450,160,200,25);
        panel.add(textField);

        wordsToWrite = new JLabel("");
        wordsToWrite.setBounds(440,250,1000,70);
        panel.add(wordsToWrite);
        wordsToWrite.setFont(f3);

        JButton start = new JButton("Start");
        start.setBounds(400,190,60,20);
        panel.add(start);
        start.addActionListener(new WPMProgramWithGUI());

        JLabel countdown = new JLabel("");
        countdown.setBounds(40,170,20,20);
        panel.add(countdown);

        JLabel typeHere = new JLabel("Type Here");
        typeHere.setBounds(300,400,100,25);
        panel.add(typeHere);
        typeHere.setFont(f1);

        JLabel yourWpm = new JLabel("Your WPM : ");
        yourWpm.setBounds(350,500,400,25);
        yourWpm.setFont(f4);
        panel.add(yourWpm);

        wpm = new JLabel("");
        wpm.setBounds(500, 500, 400, 25);
        panel.add(wpm);
        wpm.setFont(f4);

        type = new JTextField(20);
        type.setBounds(400,400,300,25);
        panel.add(type);
        type.addActionListener(e -> {
            String desired = String.valueOf(desiredMutableString);
            getWPMByAmount(desired, startTime, endTime);

        });
        type.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    endTime = System.currentTimeMillis();
                }
                if (first){
                    first = false;
                    startTime = System.currentTimeMillis();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        JButton resetButton = new JButton("Reset");
        resetButton.setBounds(470,190,60,20);
        panel.add(resetButton);
        resetButton.addActionListener(e -> {
            type.setText("");
            wordsToWrite.setText("");
            desiredMutableString = new StringBuffer();
            wpm.setText("");
            first = true;
        });

        panel.setBackground(Color.DARK_GRAY);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Scanner textScan = new Scanner(textField.getText());
        int amount = textScan.nextInt();
        try {
            getStringForRace(amount, desiredMutableString);
        } catch (Exception ex) {
            ex.printStackTrace();
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
            desiredStringJoiner.add(line);
        }
        desiredMutableString.append(desiredStringJoiner);
        wordsToWrite.setText(String.valueOf(desiredMutableString));
    }

    public static void getWPMByAmount(String desiredString, double startTime, double endTime) {
        System.out.println("startTime is " + startTime/ 1_000.0 + " and endTime is " + endTime/ 1_000.0);
        Scanner scan = new Scanner(type.getText());
        String typedWords = scan.nextLine();
        double elapsedTime = endTime - startTime;
        double seconds = elapsedTime / 1_000.0;

        int numChars = typedWords.length();
        int foundWpm = (int) ((((double) numChars / 5) / seconds) * 60);
        if (Objects.equals(typedWords, desiredString)) {
            wpm.setText(" " + foundWpm);
        } else {
            wpm.setText("Either not finished typing or you made a mistake");
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
    
}
