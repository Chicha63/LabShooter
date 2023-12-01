package com.chicha;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class LabShooter extends GameApplication {
    private Entity player;
    PhysicsComponent physics;
    GameWorld gameWorld;
    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(600);
        gameSettings.setHeight(600);
        gameSettings.setTitle("Labyrinth shooter");
        gameSettings.setVersion("0.1");
    }

    @Override
    protected void initGame(){
        physics = new PhysicsComponent();
        physics.setBodyType(BodyType.KINEMATIC);
        gameWorld = FXGL.getGameWorld();
        gameWorld.addEntityFactory(new LabEntityFactory());
        FXGL.setLevelFromMap("map.tmx");
        player = FXGL.entityBuilder()
                .type(EntityTypes.PLAYER)
                .bbox(new HitBox(BoundingShape.box(12,12)))
                .at(300,280)
                .view(new Rectangle(12,12, Color.BLUE))
                .with(new CollidableComponent(true), physics)
                .rotationOrigin(6,6)
                .buildAndAttach();
    }
    @Override
    protected void initPhysics(){
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityTypes.PLAYER, EntityTypes.WALL) {
            @Override
            protected void onCollision(Entity a, Entity b) {
                System.out.println("collision");
            }
        });
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
                physics.setVelocityY(-1);
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