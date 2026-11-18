package com.yetnt;

import com.yetnt.errs.BureaucraticError;
import com.yetnt.tokeniser.types.Form;
import io.github.yetnt.utils.builders.SimpleJSON;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

import static com.yetnt.tokeniser.Tokeniser.tokenise;

public class Main {
    public static void main(@NotNull String[] args) throws Exception {

        if (args.length < 1)
            throw new BureaucraticError("Input da filename", -1);

        String filePath = args[0];

        if (filePath.equals("--forms")) {
            ArrayList<SimpleJSON> jsons =
                    Form.getAllForms()
                    .stream()
                    .map(s -> {
                        try {
                            return s.toJson();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }).collect(Collectors.toCollection(ArrayList::new));
            System.out.println(new SimpleJSON().collectionToString(jsons));
            return;
        }

        if (!filePath.endsWith(".bdocs")) {
            throw new BureaucraticError("Cannot read any file that is not a .bdocs file", -1);
        }
        File myObj = new File(filePath);
        if (!myObj.exists())
            throw new BureaucraticError("File does not exist", -1);

        try (Scanner sc = new Scanner(myObj)) {
            tokenise(sc);
        }
    }
}
