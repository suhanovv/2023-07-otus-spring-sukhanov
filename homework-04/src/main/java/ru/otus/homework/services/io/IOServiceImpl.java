package ru.otus.homework.services.io;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class IOServiceImpl implements IOService {
    private final PrintStream output;

    private final Scanner input;


    public IOServiceImpl(PrintStream output, InputStream input) {
        this.output = output;
        this.input = new Scanner(input);
    }

    @Override
    public void outputString(String s) {
        output.print(s);
    }

    @Override
    public void outputStringWithNewline(String s) {
        output.println(s);
    }

    @Override
    public String readStringWithPrompt(String s) {
        outputString(s);
        return input.nextLine();
    }

    @Override
    public int readIntWithPrompt(String s) {
        outputString(s);
        return readInt();
    }

    @Override
    public int readInt() {
        return Integer.parseInt(input.nextLine());
    }
}
