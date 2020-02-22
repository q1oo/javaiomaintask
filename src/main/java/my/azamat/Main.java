package my.azamat;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Main {

    static StringBuffer tab = new StringBuffer("");
    static File fileToWrite = new File("data/fileforwrite.txt");

    public static void showInside(File file) {
        if (file.isDirectory()) {
            writeToFile(tab + file.getName());
            tab.append("    ");
            for (File current : file.listFiles()) {
                showInside(current);
            }
            writeToFile("");
            tab.delete(0,4);
        } else {
            writeToFile(tab + file.getName());
        }
    }

    public static void writeToFile(String file) {
        try (FileWriter fileWriter = new FileWriter(fileToWrite, true); BufferedWriter out = new BufferedWriter(fileWriter)) {
            if (file.equals("")) {
                out.newLine();
            } else {
                out.write(file);
                out.newLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        if (fileToWrite.exists()) {
            fileToWrite.delete();
        }
        File fileOrDir = new File(args[0]);
        if (fileOrDir.exists() && fileOrDir.isDirectory()) {
            //"D:\music\ROCK\КИНО\Кино"
            showInside(fileOrDir);
        }

        if (fileOrDir.exists() && fileOrDir.isFile()) {//"data\fileforread.txt"
            int countOfAllFolders = 0;
            int countOfAllFiles = 0;
            int currentNumberOfFolder = 0;
            int countOfFilesInCurrentFolder = 0;
            double sumOfLengthsOfFileNames = 0;
            Map<Integer, Integer> filesInFolders = new HashMap<>();
            try (FileReader fileReader = new FileReader(fileOrDir); BufferedReader bufferedReader = new BufferedReader(fileReader)) {
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    if (line.contains(".")) {
                        countOfFilesInCurrentFolder++;
                        countOfAllFiles++;
                        sumOfLengthsOfFileNames += line.length();
                    } else if (!line.isEmpty()) {
                        countOfAllFolders++;
                        currentNumberOfFolder++;
                    } else if (line.isEmpty() && countOfFilesInCurrentFolder != 0){
                        filesInFolders.put(currentNumberOfFolder, countOfFilesInCurrentFolder);
                        countOfFilesInCurrentFolder = 0;
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("countOfAllFolders = " + countOfAllFolders);
            System.out.println("countOfAllFiles = " + countOfAllFiles);
            System.out.println("avgerage count of files in folders = " + countOfAllFiles/filesInFolders.size());
            System.out.println("avgerage length of filenames = " + Math.floor(sumOfLengthsOfFileNames/countOfAllFiles*1e1)/1e1);
        }
    }
}
