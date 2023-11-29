package com.chicha;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class LabShooter extends GameApplication {
    private Entity player;
    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(600);
        gameSettings.setHeight(600);
        gameSettings.setTitle("Labyrinth shooter");
        gameSettings.setVersion("0.1");
    }

    @Override
    protected void initGame(){
        player = FXGL.entityBuilder()
                .at(300,300)
                .view(new Rectangle(25,25, Color.BLUE))
                .rotationOrigin(12.5,12.5)
                .buildAndAttach();
    }

    @Override
    protected void initInput(){
        Input input = FXGL.getInput();

        input.addAction(new UserAction("Right") {
            @Override
            protected void onAction() {
                player.rotateBy(1);
                System.out.println(player.getRotation());
            }
        }, KeyCode.D);
        input.addAction(new UserAction("Left") {
            @Override
            protected void onAction() {
                player.rotateBy(-1);
                System.out.println(player.getRotation());
            }
        }, KeyCode.A);
        input.addAction(new UserAction("Up") {

            @Override
            protected void onAction() {
                double angle = player.getRotation()/100;
                if (angle > 0){
                    angle = angle > 0.9 ? angle + (1-angle): angle + (1-angle);
                    System.out.println(angle);
                    player.translateX(angle > 1 ? 1 - (angle-1): angle);
                } else if(angle < 0) {
                    angle = angle < -0.9 ? angle - 0.2: angle - 0.1;
                    System.out.println(angle);
                    player.translateX(angle < -1 ? angle + (angle+1.1): angle);
                    angle *= -1;
                }
                player.translateY(-1+angle);
            }
        }, KeyCode.W);
        input.addAction(new UserAction("Down") {
            @Override
            protected void onAction() {
                player.translateY(1);
            }
        }, KeyCode.S);
    }
    public static void main(String[] args){
        launch(args);
    }
}