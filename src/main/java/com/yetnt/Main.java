package com.yetnt;

import com.yetnt.errs.BureaucraticError;

import java.io.File;
import java.util.Scanner;

import static com.yetnt.tokenzier.Tokenizer.tokenize;

public class Main {
    public static void main(String[] args) throws BureaucraticError {

        if (args.length < 1)
            throw new BureaucraticError("Input da filename");

        String filePath = args[0];
        if (!filePath.endsWith(".bdocs")) {
            throw new BureaucraticError("Cannot read any file that is not a .bdocs file");
        }
        File myObj = new File(filePath);
        if (!myObj.exists())
            throw new BureaucraticError("File does not exist");

        try (Scanner sc = new Scanner(myObj)) {
            tokenize(sc);
        } catch (Exception e) {
            throw new BureaucraticError("Someting went wrong parsing");
        }
    }
}
