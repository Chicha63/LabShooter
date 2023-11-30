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

        input.addAction(new UserAction("Rotate Right") {
            @Override
            protected void onAction() {
                if(player.getRotation() > 180){
                    player.setRotation(-180);
                    player.rotateBy(1);
                } else {
                    player.rotateBy(1);
                }
                System.out.println(player.getRotation());
            }
        }, KeyCode.D);
        input.addAction(new UserAction("Rotate Left") {
            @Override
            protected void onAction() {
                if(player.getRotation() < -180){
                    player.setRotation(180);
                    player.rotateBy(-1);
                } else {
                    player.rotateBy(-1);
                }
                System.out.println(player.getRotation());
            }
        }, KeyCode.A);
        input.addAction(new UserAction("Forward") {
            @Override
            protected void onAction() {
                double angle = player.getRotation()/180*2;
                if(angle > 0){
                    player.translateX(angle > 1 ? 1 + (1 - angle): angle);
                    player.translateY(-1 + angle);
                } else {
                    player.translateX(angle < -1 ? -1 - (1 + angle): angle);
                    player.translateY(-1 - angle);
                }
            }
        }, KeyCode.W);
        input.addAction(new UserAction("Backward") {
            @Override
            protected void onAction() {
                double angle = player.getRotation()/180*2;
                if(angle > 0){
                    player.translateX(angle > 1 ? -1 - (1 - angle): -angle);
                    player.translateY(1 - angle);
                } else {
                    player.translateX(angle < -1 ? 1 + (1 + angle): -angle);
                    player.translateY(1 + angle);
                }
            }
        }, KeyCode.S);
    }
    public static void main(String[] args){
        launch(args);
    }
}