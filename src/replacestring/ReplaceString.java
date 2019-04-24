/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package replacestring;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author CARLOGON
 */
public class ReplaceString {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            String stringFind = args[0];
            String stringReplace = args[1];
            String directoryToFind = args[2];
            String extensionFile = args[3];

            List<File> listFiles = Files.walk(Paths.get(directoryToFind)).filter(Files::isRegularFile).map(Path::toFile).collect(Collectors.toList());

            listFiles.forEach((file) -> {
                if (file.getAbsolutePath().endsWith(extensionFile)) {
                    System.out.println("File to modify: " + file.getAbsolutePath());
                    ReplaceString.replace(stringFind, stringReplace, file.getAbsolutePath());
                }
            });
            System.out.println("Find and Replace done!!!");
//            String chain = "Condition=\"'$(VSToolsPath)' != ''\" ";
//            System.out.println("Cadena modificada: " + chain.replace("'$(VSToolsPath)' != ''", "false"));
        } catch (IOException ex) {
            Logger.getLogger(ReplaceString.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void replace(String find, String replace, String file) {
        try {
            Path path = Paths.get(file);
            try (Stream<String> lines = Files.lines(path)) {
                List<String> replaced = lines.map(line -> line.replace(find, replace)).collect(Collectors.toList());
                Files.write(path, replaced);
                lines.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
