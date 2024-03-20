package dk.sdu.mmmi.cbse.main;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.stream.Collectors.toList;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {

  private final GameData gameData = new GameData();
  private final World world = new World();
  private final Map<Entity, Polygon> polygons = new ConcurrentHashMap<>();
  private final Pane gameWindow = new Pane();

  private final Text info = new Text(10, 50, "Hello");
  private final Text entityList = new Text(10, 80, "");


  public static void main(String[] args) {
    launch(Main.class);
  }

  @Override
  public void start(Stage window) throws Exception {
    Text text = new Text(10, 20, "Destroyed asteroids: 0");
    text.setFill(Color.WHITE);
    gameWindow.setPrefSize(gameData.getDisplayWidth(), gameData.getDisplayHeight());
    gameWindow.getChildren().add(text);
    // Debug texts
    this.info.setFill(Color.WHITE);
    gameWindow.getChildren().add(this.info);
    this.entityList.setFill(Color.WHITE);
    this.entityList.setWrappingWidth(200);
    gameWindow.getChildren().add(this.entityList);

    Scene scene = getScene();

    // Lookup all Game Plugins using ServiceLoader
    for (IGamePluginService iGamePlugin : getPluginServices()) {
      iGamePlugin.start(gameData, world);
    }
    for (Entity entity : world.getEntities()) {
      Polygon polygon = new Polygon(entity.getPolygonCoordinates());
      polygons.put(entity, polygon);
      gameWindow.getChildren().add(polygon);
    }

    render();

    window.setScene(scene);
    window.setTitle("ASTEROIDS");
    window.show();

  }

  private Scene getScene() {
    Scene scene = new Scene(gameWindow);
    scene.setFill(Color.rgb(0, 15, 38));
    scene.setOnKeyPressed(event -> {
      if (event.getCode().equals(KeyCode.LEFT)) {
        gameData.getKeys().setKey(GameKeys.LEFT, true);
      }
      if (event.getCode().equals(KeyCode.RIGHT)) {
        gameData.getKeys().setKey(GameKeys.RIGHT, true);
      }
      if (event.getCode().equals(KeyCode.UP)) {
        gameData.getKeys().setKey(GameKeys.UP, true);
      }
      if (event.getCode().equals(KeyCode.SPACE)) {
        gameData.getKeys().setKey(GameKeys.SPACE, true);
      }
    });
    scene.setOnKeyReleased(event -> {
      if (event.getCode().equals(KeyCode.LEFT)) {
        gameData.getKeys().setKey(GameKeys.LEFT, false);
      }
      if (event.getCode().equals(KeyCode.RIGHT)) {
        gameData.getKeys().setKey(GameKeys.RIGHT, false);
      }
      if (event.getCode().equals(KeyCode.UP)) {
        gameData.getKeys().setKey(GameKeys.UP, false);
      }
      if (event.getCode().equals(KeyCode.SPACE)) {
        gameData.getKeys().setKey(GameKeys.SPACE, false);
      }
    });
    return scene;
  }

  private void render() {
    new AnimationTimer() {
      private long then = 0;

      @Override
      public void handle(long now) {
        update();
        draw();
        gameData.getKeys().update();
        gameData.setCurrentTime(now);

        // stop entities that are out of bounds
//        List<Entity> entitiesToRemove = new ArrayList<>();
//        for (Entity entity : world.getEntities()) {
//          if (entity.getX() < 0 || entity.getX() > gameData.getDisplayWidth() || entity.getY() < 0 || entity.getY() > gameData.getDisplayHeight()) {
//            entitiesToRemove.add(entity);
//          }
//        }
//
//        for (IGamePluginService iGamePlugin : getPluginServices()) {
//          iGamePlugin.stop(gameData, world);
//        }
      }

    }.start();
  }

  private void update() {

    // Update
    for (IEntityProcessingService entityProcessorService : getEntityProcessingServices()) {
      entityProcessorService.process(gameData, world);
    }
    Entity player = world.getEntityByClass("Player");
    if (player != null) {
      // clamp rotation between 0 and 360
      this.info.setText("Player rotation: " + Math.floorMod(((int) player.getRotation()), 360));
    }
    this.entityList.setText("Entities: " + world.getEntities().toString());
    //        for (IPostEntityProcessingService postEntityProcessorService : getPostEntityProcessingServices()) {
    //            postEntityProcessorService.process(gameData, world);
    //        }
  }

  private void draw() {
    for (Entity entity : world.getEntities()) {
      Polygon polygon = polygons.get(entity);
      if (polygon == null) {
        polygon = new Polygon(entity.getPolygonCoordinates());
        polygons.put(entity, polygon);
        gameWindow.getChildren().add(polygon);
      }

      polygon.setFill(Color.valueOf(entity.getColor()));
      polygon.setScaleX(2);
      polygon.setScaleY(2);

      polygon.setTranslateX(entity.getX());
      polygon.setTranslateY(entity.getY());
      polygon.setRotate(entity.getRotation());
    }
  }

  private Collection<? extends IGamePluginService> getPluginServices() {
    return ServiceLoader.load(IGamePluginService.class).stream().map(ServiceLoader.Provider::get).collect(toList());
  }

  private Collection<? extends IEntityProcessingService> getEntityProcessingServices() {
    return ServiceLoader.load(IEntityProcessingService.class).stream().map(ServiceLoader.Provider::get).collect(toList());
  }

  // TODO: collision detection
  private Collection<? extends IPostEntityProcessingService> getPostEntityProcessingServices() {
    return ServiceLoader.load(IPostEntityProcessingService.class).stream().map(ServiceLoader.Provider::get).collect(toList());
  }
}
