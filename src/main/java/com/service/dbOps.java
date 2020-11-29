package com.service;


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class dbOps {

    public static String writeOp(String content) throws IOException {
        File file = new File("C:/Users/Илья Михайлов/Desktop/localDB.txt");
        FileWriter fw = new FileWriter(file, true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(content);
        bw.close();
        return content;
    }

    public static String addOp (String content) throws IOException {
        File file = new File("C:/Users/Илья Михайлов/Desktop/localDB.txt");
        FileWriter fw = new FileWriter(file,true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.append('\n');
        bw.append(content);
        bw.close();
        return content;
        //TBD: сделать проверку на наличие дня рождения в добавленной строке
    }

    public static String getOp() throws IOException {
        Path path = Paths.get("C:/Users/Илья Михайлов/Desktop/localDB.txt");
        /*
        final LineNumberReader lnr = new LineNumberReader(new FileReader(path));
        int linesCount = 0;
        while(null != lnr.readLine()) {
            linesCount++;
        }
*/
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(path, StandardCharsets.UTF_8))
        {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        }



        //long lineCount = Files.lines(path).count();
        //String convertedLineCount = Long.toString(lineCount);
        //return Files.readAllLines(path).get(0);
        return contentBuilder.toString();
    }

}
