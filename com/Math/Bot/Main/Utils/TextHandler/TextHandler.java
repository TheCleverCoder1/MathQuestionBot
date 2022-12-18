package com.Math.Bot.Main.Utils.TextHandler;

import com.Math.Bot.Main.BotPanel;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

// Optimized Stage 3
public class TextHandler {
    // BotPanel
    public static BotPanel bp;

    // PathToText
    public static String PathToText = "D:/java codes/MathQuestionBot/src/com/Math/Bot/Main/Utils/TextHandler/Texts";

    // Readers
    private static byte numOfReaders;
    private static BufferedReader[] readers;

    // Writers
    private static byte numOfWriters;
    private static BufferedWriter[] writers;

    // Constants
    public static final String NULL = "NuLL!";
    public static final byte BUTTON = 0;
    public static final byte CONFIG = 1;
    public static final byte TEXTBOX = 2;

    // ButtonDivision
    private static String[][][] buttons; // This is a list of buttons in a specific state
                                       // Like { {state 0 button 1 {info 1, info 2}, state 0 button 2 {info 1, info 2}}, {state 1 button 1 {info 1, info 2}, state 1 button 2 {info 1, info 2}} }
    public static final byte lengthOfTheButtonInformation = 7;

    // TextBoxDivision
    private static String[][][] textBoxes;
    public static final byte lengthOfScreenTextBoxInformation = 8;


    // Initializing
    public static void init(){
        TextHandler.bp = BotPanel.bp;
        initializeReadersAndWriters();
        loadButtonString();
        loadTextBoxString();
    }
    private static void initializeReadersAndWriters(){
        // Initializing Readers and Writers
        numOfReaders = numOfWriters = 3;
        readers = new BufferedReader[numOfReaders];
        writers = new BufferedWriter[numOfWriters];

        // File Array
        String[] files = {
                "/Buttons.txt",
                "/Config.txt",
                "/TextBox.txt"
        };

        // Loading Readers and Writers in the array.
        for (byte idx = 0; idx < numOfReaders; idx++){
            setReaderAtIdx(PathToText + files[idx], idx);
            setWriterAtIdx(PathToText + files[idx], idx);
        }
    }
    private static void setReaderAtIdx(String filePath, byte idx){
        try{
            readers[idx] = new BufferedReader(new FileReader(filePath));
        }
        catch (IOException e){
            e.printStackTrace();
            System.out.println("Error in loading reader for the path \"" + filePath + "\"");
        }
    }
    private static void setWriterAtIdx(String filePath, byte idx){
        try{
            writers[idx] = new BufferedWriter(new FileWriter(filePath, true));
        }
        catch (IOException e){
            e.printStackTrace();
            System.out.println("Error in loading writer for the path \"" + filePath + "\"");
        }
    }

    // Text Formatting
    public static void appendText(byte fileConstant, String text){
        try {
            writers[fileConstant].write(text);
            writers[fileConstant].flush();
        }catch (IOException e){
            e.printStackTrace();
            System.out.println("Not able to append text to the fileConstant = " + fileConstant + " text = \"" + text + "\"");
        }
    }
    public static void appendText(String fileName, String text){
        try {
            byte idx = getConstantByFileName(fileName);
            writers[idx].write(text);
            writers[idx].flush();
        }catch (IOException e){
            e.printStackTrace();
            System.out.println("Not able to append text to the fileName = \"" + fileName + "\" text = \"" + text + "\"");
        }
    }
    private static byte getConstantByFileName(String fileName){
        switch (fileName){
            case "Buttons.txt", "Button.txt", "Button", "Buttons" -> {
                return BUTTON;
            }
            case "Config.txt", "Config" -> {
                return CONFIG;
            }
            default -> throw new IllegalArgumentException("String FileName = \"" + fileName + "\" is not a file in Texts which is settled in the BufferedReader or BufferedWriter");
        }
    }

    // Reading Data from the Text File
    public static boolean isReady(byte fileConstant){
        try {
            return readers[fileConstant].ready();
        }catch (IOException e){
            e.printStackTrace();
        }
        return false;
    }
    public static boolean isReady(String fileName){
        try{
            readers[getConstantByFileName(fileName)].ready();
        }catch (IOException e){
            e.printStackTrace();
        }
        return false;
    }
    public static void reset(byte fileConstant){
        try {
            readers[fileConstant].reset();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void reset(String fileName){
        try{
            readers[getConstantByFileName(fileName)].reset();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public static String readLine(byte fileConstant){
        try{
            return readers[fileConstant].readLine();
        }catch (IOException e){
            e.printStackTrace();
        }
        return NULL;
    }
    public static String readLine(String fileName){
        try{
            return readers[getConstantByFileName(fileName)].readLine();
        }catch (IOException e){
            e.printStackTrace();
        }
        return NULL;
    }
    public static String[] readAllLines(byte fileConstant){
        ArrayList<String> lines = new ArrayList<>();
        while(isReady(fileConstant))
            lines.add(readLine(fileConstant));
        String linesString = lines.toString();
        return linesString.substring(1, linesString.length() - 1).split(", ");
    }
    public static String[] readAllLines(String fileName){
        ArrayList<String> lines = new ArrayList<>();
        while(isReady(fileName))
            lines.add(readLine(fileName));
        return lines.toString().split("\n");
    }

    // Button.txt ( x  y  width  height  state  imgTag )
    public static void addButton(short[] dimensions, byte state, String img, String buttonTag){
        // Creating the String using StringBuilder
        StringBuilder sb = new StringBuilder(dimensions[0]); sb.append(" ");
        sb.append(dimensions[1]); sb.append(" ");
        sb.append(dimensions[2]); sb.append(" ");
        sb.append(dimensions[3]); sb.append(" ");
        sb.append(state); sb.append(" ");
        sb.append(img); sb.append(" ");
        sb.append(buttonTag); sb.append(" ");

        // Now appending and saving the info in the button.txt file
        appendText(BUTTON, sb.toString());
    }
    private static void loadButtonString(){
        // Loading all lines in button.txt
        String[] allButtons = readAllLines(BUTTON);
        // Number of states
        byte numOfStates = bp.numOfStates;
        // array of arrayList which will hold buttons
        /*
         The array is of size numOfStates because we can't have more than that states.
         Then we have a arrayList because we don't know the number of buttons in a state.
         a arrayList is of String[] because we know the number of it.
         */
        ArrayList<String[]>[] arrayOfArrayListOfStringArray = new ArrayList[numOfStates];

        // Loading array list in them
        for (byte i = 0; i < numOfStates; i++)
            arrayOfArrayListOfStringArray[i] = new ArrayList<>();

        // Loading buttons in the aOfList
        for (byte i = 0; i < allButtons.length; i++){
            String[] info = allButtons[i].split(" ");
            if (info.length == lengthOfTheButtonInformation) {
                byte state = Byte.parseByte(info[4]);
                arrayOfArrayListOfStringArray[state].add(info);
            }else{
                throw new IllegalArgumentException("The length of the information couldn't be more or less than" + lengthOfTheButtonInformation +
                        "It is at row " + i + " and it reads:- \"" + allButtons[i] + "\"");
            }
        }

        // Converting ArrayList to String[] i.e. buttons
        // Step 1: Loading buttons to the num Of States
        buttons = new String[numOfStates][][];

        // Step 2: Iterating through the states and withdrawing the Arraylist from the arrayOfArrayListOfStringArray and initializing the buttons'
        //       : list of that particular state and initializing it to the String[size of arrayListOfArray]
        //       :                                                                [Size of all information i.e. 7]
        for (byte state = 0; state < numOfStates; state++){
            // Doing Step 2
            ArrayList<String[]> arrayListOfStringArray = arrayOfArrayListOfStringArray[state];
            buttons[state] = new String[arrayListOfStringArray.toArray().length][lengthOfTheButtonInformation];

            // Step 3: Iterating through the buttons and initializing it
            for (byte button = 0; button < buttons[state].length; button++){
                buttons[state][button] = (String[]) arrayListOfStringArray.toArray()[button];
            }
        }
    }
    public static String[][] getButtonOfState(byte state){
        return buttons[state];
    }

    // TextBox.txt
    public static void addTextBox(short[] pos, float fontSize, String fontTag, byte displayMode, String color, byte state, boolean isPassword){
        // Creating the String using StringBuilder
        StringBuilder sb = new StringBuilder(pos[0]); sb.append(' ');
        sb.append(pos[1]); sb.append(' ');
        sb.append(fontSize); sb.append(' ');
        sb.append(fontTag); sb.append(' ');
        sb.append(state); sb.append(' ');
        sb.append(displayMode); sb.append(' ');
        sb.append(color); sb.append(' ');
        sb.append(isPassword);
        // Now appending and saving the info in the button.txt file
        appendText(TEXTBOX, sb.toString());
    }
    private static void loadTextBoxString(){
        // Loading all lines in TextBox.txt
        String[] allTextBoxes = readAllLines(TEXTBOX);
        // Number of states
        byte numOfStates = bp.numOfStates;
        // array of arrayList which will hold text box
        /*
         The array is of size numOfStates because we can't have more than that states.
         Then we have a arrayList because we don't know the number of buttons in a state.
         a arrayList is of String[] because we know the number of it.
         */
        ArrayList<String[]>[] arrayOfArrayListOfStringArray = new ArrayList[numOfStates];

        // Loading array list in them
        for (byte i = 0; i < numOfStates; i++)
            arrayOfArrayListOfStringArray[i] = new ArrayList<>();

        // Loading text box in the aOfList
        for (byte i = 0; i < allTextBoxes.length; i++){
            String[] info = allTextBoxes[i].split(" ");
            if (Objects.equals(allTextBoxes[i], "")) break;
            if (info.length == lengthOfScreenTextBoxInformation) {
                byte state = Byte.parseByte(info[4]);
                arrayOfArrayListOfStringArray[state].add(info);
            }
            else{
                throw new IllegalArgumentException("The length of the information couldn't be more or less than " + lengthOfScreenTextBoxInformation +
                        ". It is at row " + i + " and it reads:- \"" + allTextBoxes[i] + "\"");
            }
        }

        // Converting ArrayList to String[] i.e. text boxes
        // Step 1: Loading text box to the num Of States
        textBoxes = new String[numOfStates][][];

        // Step 2: Iterating through the states and withdrawing the Arraylist from the arrayOfArrayListOfStringArray and initializing the textboxes'
        //       : list of that particular state and initializing it to the String[size of arrayListOfArray]
        for (byte state = 0; state < numOfStates; state++){
            // Doing Step 2
            ArrayList<String[]> arrayListOfStringArray = arrayOfArrayListOfStringArray[state];
            textBoxes[state] = new String[arrayListOfStringArray.toArray().length][lengthOfScreenTextBoxInformation];

            // Step 3: Iterating through the buttons and initializing it
            for (byte textbox = 0; textbox < textBoxes[state].length; textbox++){
                textBoxes[state][textbox] = (String[]) arrayListOfStringArray.toArray()[textbox];
            }
        }
    }
    public static String[][] getScreenTextBoxOfState(byte state){
        return textBoxes[state];
    }

    // Character Checking
    public static boolean isCharAlphabet(char Char){
        switch (String.valueOf(Char).toLowerCase()){
            case "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q",
                    "r", "s", "t", "u", "v", "w", "x", "y", "z" -> {
                return true;
            }
        }
        return false;
    }
    public static boolean isCharSymbol(char Char){
        switch (Char){
            case '~', '`', '!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '-', '_', '+', '=', '{', '}',
                    '[', ']', '\\', '|', ';', ':', '"', '\'', '<', '>', ',', '.', '/', '?' -> {
                return true;
            }
        }
        return false;
    }
    public static boolean isCharNumber(char Char){
        switch (Char){
            case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> {
                return true;
            }
        }
        return false;
    }
    public static boolean isCharSpace(char Char) {
        return Char == ' ';
    }

    public static boolean doesTextContainsNumber(String text) {
        return text.contains("1") || text.contains("2") || text.contains("3") || text.contains("4") || text.contains("5")
               || text.contains("6") || text.contains("7") || text.contains("8") || text.contains("9") || text.contains("0");
    }
    public static boolean doesTextContainsCapitalAlphabets(String text) {
        return text.contains("A") || text.contains("B") | text.contains("C") || text.contains("D") || text.contains("E")
                || text.contains("F") || text.contains("G") || text.contains("H") || text.contains("I") || text.contains("J")
                || text.contains("K") || text.contains("L") || text.contains("M") || text.contains("N") || text.contains("O")
                || text.contains("P") || text.contains("Q") || text.contains("R") || text.contains("S") || text.contains("T")
                || text.contains("U") || text.contains("V") || text.contains("W") || text.contains("X") || text.contains("Y")
                || text.contains("Z");
    }
    public static boolean doesTextContainsSmallAlphabets(String text) {
        return text.contains("a") || text.contains("b") | text.contains("c") || text.contains("d") || text.contains("e")
                || text.contains("f") || text.contains("g") || text.contains("h") || text.contains("i") || text.contains("j")
                || text.contains("k") || text.contains("l") || text.contains("m") || text.contains("n") || text.contains("o")
                || text.contains("p") || text.contains("q") || text.contains("r") || text.contains("s") || text.contains("t")
                || text.contains("u") || text.contains("v") || text.contains("w") || text.contains("x") || text.contains("y")
                || text.contains("z");
    }
    public static boolean doesTextContainsSpace(String text){
        return text.contains(" ");
    }
    public static boolean doesTextContainsSymbols(String text) {
        return text.contains("~") || text.contains("`") || text.contains("!") || text.contains("@") || text.contains("#")
                || text.contains("$") || text.contains("%") || text.contains("^") || text.contains("&") || text.contains("*")
                || text.contains("(") || text.contains(")") || text.contains("-") || text.contains("_") || text.contains("+")
                || text.contains("=") || text.contains("{") || text.contains("}") || text.contains("[") || text.contains("]")
                || text.contains("\\") || text.contains("|") || text.contains(";") || text.contains(":") || text.contains("\"")
                || text.contains("'") || text.contains("<") || text.contains(">") || text.contains(",") || text.contains(".")
                || text.contains("/") || text.contains("?");
    }
}