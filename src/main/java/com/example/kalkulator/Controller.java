package com.example.kalkulator;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button Num;

    @FXML
    private Button Operator_button;

    @FXML
    private Button GetResult;

    @FXML
    private Label ResultBar;

    @FXML
    private Label CalculationBar;

    private final static String[] AllOperators = new String[] {"+", "-", "*", "/"};
    private static String firstNum;
    private static String secondNum;
    private static String operator;
    private static byte updatingNumber = 1;
    private static byte updatingOperatorsIndex;
    private static float result;

    AnimationTimer timer = new AnimationTimer() {
        private long lastUpdate = 0;
        private final static long millis = 500_000; //раз в сколько миллисекунд будет обновляться число

        @Override
        public void handle(long now) {
            if ((now - lastUpdate)/1000 >= millis) {
                if (updatingNumber < 9) {
                    updatingNumber++;
                }
                else {
                    if (operator == null) {
                        if (firstNum == null) {
                            updatingNumber = 1;
                        }
                        else {
                            updatingNumber = 0;
                        }
                    }
                    else {
                        if (secondNum == null) {
                            updatingNumber = 1;
                        }
                        else {
                            updatingNumber = 0;
                        }
                    }
                }
                Num.setText(Integer.toString(updatingNumber));

                if (updatingOperatorsIndex < 3) {
                    updatingOperatorsIndex++;
                }
                else {
                    updatingOperatorsIndex = 0;
                }
                Operator_button.setText(AllOperators[updatingOperatorsIndex]);
                lastUpdate = now;
            }
        }
    };
    @FXML
    void num_select(ActionEvent event) {
        if (operator == null) {
            firstNum = firstNum == null ? Byte.toString(updatingNumber) : firstNum + Byte.toString(updatingNumber);
            CalculationBar.setText(firstNum);
        }
        else {
            secondNum = secondNum == null ? Byte.toString(updatingNumber) : secondNum + Byte.toString(updatingNumber);
            CalculationBar.setText(firstNum + " " + operator + " " + secondNum);

        }
    }

    @FXML
    void operator_select(ActionEvent event) {
        if (operator == null && firstNum != null) {
            operator = AllOperators[updatingOperatorsIndex];
            CalculationBar.setText(firstNum + " " + operator);
        }
    }
    void reset() {
        firstNum = null;
        secondNum = null;
        operator = null;
    }
    @FXML
    void get_result(ActionEvent event) {
        switch (operator) {
            case null:
                ResultBar.setText("Молодец, ты всё сломал!");
                break;
            case "+":
                result = Float.parseFloat(firstNum) + Float.parseFloat(secondNum);
                ResultBar.setText(Float.toString(result));
                reset();
            case "-":
                result = Float.parseFloat(firstNum) - Float.parseFloat(secondNum);
                ResultBar.setText(Float.toString(result));
                reset();
            case "*":
                result = Float.parseFloat(firstNum) * Float.parseFloat(secondNum);
                ResultBar.setText(Float.toString(result));
                reset();
            case "/":
                result = Float.parseFloat(firstNum) / Float.parseFloat(secondNum);
                ResultBar.setText(Float.toString(result));
                reset();
            default:
        }
    }

    @FXML
    void initialize() {
        timer.start();
    }

}
