package my.azamat;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Main {
    static File targetFile = new File("data/fileforwrite.txt");
    public static void main(String[] args) {
        targetFile.delete();
        File fileOrDir = new File(args[0]);
        if (fileOrDir.exists() && fileOrDir.isDirectory()) {
            //"D:\music\ROCK\КИНО\Кино"
            writeToFile(fileOrDir.getName());
            int counter = 1;
            for (File current : fileOrDir.listFiles()) {
                writeToFile("   " + current.getName());
                if (current.isDirectory()) {
                    for (File subCurrent : current.listFiles()) {
                        if (counter < 10) {
                            writeToFile("   0" + counter + " - " + subCurrent.getName());
                        } else {
                            writeToFile("   " + counter + " - " + subCurrent.getName());
                        }
                        counter++;
                    }
                    writeToFile("");
                }
                counter = 1;
            }
        }

        if (fileOrDir.exists() && fileOrDir.isFile()) {////"data\fileforread.txt"
            int countOfFolders = 0;
            int countOfFiles = 0;
            double sumLengthOfFileNames = 0;
            Map<Integer, Integer> filesInEachFolder = new HashMap<>();
            try (FileReader fileReader = new FileReader(fileOrDir); BufferedReader bufferedReader = new BufferedReader(fileReader)) {
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    if (line.contains(".")) {
                        countOfFiles++;
                        sumLengthOfFileNames += line.length();
                    } else if (!line.isEmpty()) {
                        countOfFolders++;
                    } else {
                        filesInEachFolder.put(countOfFolders, countOfFiles);
                        countOfFiles = 0;
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            int sumOfFiles = 0;
            for (Integer value : filesInEachFolder.values()) {
                sumOfFiles += value;
            }
            System.out.println("countOfFolders = " + filesInEachFolder.size());
            System.out.println("countOfFiles = " + sumOfFiles);
            System.out.println("avgerage count of files in folders = " + sumOfFiles/filesInEachFolder.size());
            System.out.println("avgerage length of filenames = " + sumLengthOfFileNames/sumOfFiles);
            System.out.println(filesInEachFolder);
        }



    }

    public static void writeToFile(String file) {
        try (FileWriter fileWriter = new FileWriter(targetFile, true); BufferedWriter out = new BufferedWriter(fileWriter)) {
            if (file.equals("")) {
                out.newLine();
            } else {
                out.write(file + "\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
