package com.javarush.kolosov.cryptoanalyzer.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import com.javarush.kolosov.cryptoanalyzer.frontend.Scenes;

public class MainController extends BaseController {

    @FXML
    public Button toEncodeByKeyBtn;
    @FXML
    public Button toDecodeByKeyBtn;
    @FXML
    public Button toDecodeByBruteForceBtn;

    @FXML
    public void actionOpenEncodeByKeyScreen() {
        renderScreen(Scenes.ENCODE_BY_KEY);
    }

    @FXML
    public void actionOpenDecodeByKeyScreen() {
        renderScreen(Scenes.DECODE_BY_KEY);
    }

    @FXML
    public void actionOpenDecodeByBruteForceScreen() {
        renderScreen(Scenes.DECODE_BY_BRUTE_FORCE);
    }
}
