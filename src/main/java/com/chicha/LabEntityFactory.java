package com.chicha;

import com.almasb.fxgl.dsl.EntityBuilder;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.*;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class LabEntityFactory implements EntityFactory {

    @Spawns("")
    public Entity sprite(SpawnData data){
        return FXGL.entityBuilder()
                .build();
    }

    @Spawns("Wall")
    public Entity wall(SpawnData data){
        return FXGL.entityBuilder()
                .type(EntityTypes.WALL)
                .viewWithBBox(new Rectangle(data.<Integer>get("width"), data.<Integer>get("height")))
                .with(new CollidableComponent(true))
                .with(new PhysicsComponent())
                .buildAndAttach();
    }


}
