package ru.javarush.kolosov.cryptoanalyzer.analyzers;


import ru.javarush.kolosov.cryptoanalyzer.exceptions.InvalidFileException;
import ru.javarush.kolosov.cryptoanalyzer.helpers.FileManager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileCipherService {

    private final Cipher cipher;

    public FileCipherService(Cipher cipher) {
        this.cipher = cipher;
    }


    public void encode(Path sourceFile, Path outputFile) {
        if (!FileManager.isFileExists(sourceFile)) {
            throw new InvalidFileException("Исходного файла не существует");
        }

        try (BufferedReader reader = Files.newBufferedReader(sourceFile);
             BufferedWriter writer = Files.newBufferedWriter(outputFile))
        {
            while (reader.ready()) {
                String line = reader.readLine();
                line = cipher.encode(line);
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException exception) {
            throw new RuntimeException("Произошла ошибка при обработке файла", exception);
        }
    }

    public void decode(Path sourceFile, Path outputFile) {
        if (!FileManager.isFileExists(sourceFile)) {
            throw new InvalidFileException("Исходного файла не существует");
        }

        try (BufferedReader reader = Files.newBufferedReader(sourceFile);
             BufferedWriter writer = Files.newBufferedWriter(outputFile))
        {
            while (reader.ready()) {
                String line = reader.readLine();
                line = cipher.decode(line);
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException exception) {
            throw new RuntimeException("Произошла ошибка при обработке файла", exception);
        }
    }
}
