package ru.javarush.kolosov.cryptoanalyzer.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import ru.javarush.kolosov.cryptoanalyzer.Application;
import ru.javarush.kolosov.cryptoanalyzer.analyzers.CaesarCipher;
import ru.javarush.kolosov.cryptoanalyzer.analyzers.FileCipherService;
import ru.javarush.kolosov.cryptoanalyzer.helpers.FileManager;

import java.io.File;
import java.nio.file.Path;

public class EncodeController extends BaseController {

    private Path inputFile;
    private Path outputFile;

    @FXML
    public Button selectFileBtn;
    @FXML
    public TextField keyInputField;

    @FXML
    public Button submitBtn;
    @FXML
    public Label commonHelpBlock;

    @FXML
    public VBox resultBlock;
    @FXML
    public Label labelResult;
    @FXML
    public Button saveFileBtn;

    @FXML
    public void selectFile() {
        File file = FileManager.openFile();
        if (file != null) {
            selectFileBtn.setText(file.getAbsoluteFile().toString());
            inputFile = file.toPath();
        }
    }

    @FXML
    public void encodeByKey() {
        hideHelpBlocks();

        if (inputFile == null) {
            commonHelpBlock.setText("Необходимо выбрать файл");
            commonHelpBlock.setVisible(true);
            return;
        }

        int key;
        try {
            key = Integer.parseInt(keyInputField.getText());
        } catch (NumberFormatException exception) {
            commonHelpBlock.setText("Значение ключа должно быть числом");
            commonHelpBlock.setVisible(true);
            return;
        }

        Path sourceFile = inputFile;
        Path outputFile = Path.of("files/encoded_" + inputFile.getFileName());

        try {
            FileCipherService fileCipherService = new FileCipherService(new CaesarCipher(key, Application.alphabets));

            disableInputFields();
            submitBtn.setText("Обработка...");

            fileCipherService.encode(sourceFile, outputFile);
        } catch (RuntimeException exception) {
            commonHelpBlock.setText(exception.getMessage());
            commonHelpBlock.setVisible(true);
            return;
        }

        this.outputFile = outputFile;
        resultBlock.setVisible(true);
        labelResult.setText("Обработка завершена");
    }

    @FXML
    public void downloadFile() {
        if (outputFile == null) {
            return;
        }

        Path savedFile = FileManager.saveFile(outputFile);

        if (savedFile == null) {
            labelResult.setText("Не удалось сохранить файл");
            labelResult.setVisible(true);
            return;
        }

        labelResult.setText("Файл " + savedFile.getFileName() + " успешно сохранен");
        labelResult.setVisible(true);
    }

    private void hideHelpBlocks() {
        commonHelpBlock.setVisible(false);
    }

    private void clearSubmitBtn() {
        submitBtn.setText("Зашифровать");
        submitBtn.setDisable(false);
    }

    private void clearFields() {
        inputFile = null;
        selectFileBtn.setText("Выбрать файл");
        keyInputField.setText(null);
    }

    private void clearResults() {
        labelResult.setText(null);
        resultBlock.setVisible(false);
    }

    private void clearForm() {
        hideHelpBlocks();
        clearSubmitBtn();
        clearFields();
        clearResults();
    }

    private void disableInputFields() {
        submitBtn.setDisable(true);
        selectFileBtn.setDisable(true);
        keyInputField.setDisable(true);
    }


}
