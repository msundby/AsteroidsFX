package dk.sdu.mmmi.cbse.main;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

import java.io.IOException;
import java.lang.module.Configuration;
import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleFinder;
import java.lang.module.ModuleReference;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
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


    private final Text scoreText = new Text(10, 20, "Destroyed asteroids: 0");
    private final Text livesText = new Text(10, 40, "Lives: 3");

    public static void main(String[] args) {
        launch(Main.class);
    }

    @Override
    public void start(Stage window) throws Exception {
        timer.scheduleAtFixedRate(task, 0, 500);
        Text text = new Text(10, 20, "Destroyed asteroids: 0");
        gameWindow.setPrefSize(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        gameWindow.setStyle("-fx-background-color: grey;");
        gameWindow.getChildren().add(scoreText);
        gameWindow.getChildren().add(livesText);
        livesText.setFill(Color.WHITE);
        scoreText.setFill(Color.WHITE);

        Scene scene = new Scene(gameWindow);
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

    private void updateScoreText() {
        System.out.println();
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/attributes/score"))
                .GET().build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            scoreText.setText("Score: " + response.body());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void updateLives() {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/attributes/lives"))
                .GET().build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.body().equals("0")) {
                Platform.exit();
            }
            livesText.setText("Lives: " + response.body());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void checkPlayerLives() {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/attributes/lives"))
                .GET().build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.body().equals("0")) {
                stopGame();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void stopGame() {
        timer.cancel();
        System.out.println("Game Over");
    }

    Timer timer = new Timer();
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            updateScoreText();
            updateLives();
            checkPlayerLives();
        }
    };

    private void render() {
        new AnimationTimer() {
            private long then = 0;

            @Override
            public void handle(long now) {
                update();
                draw();
                gameData.getKeys().update();
            }

        }.start();
    }

    private void update() {

        // Update
        for (IEntityProcessingService entityProcessorService : getEntityProcessingServices()) {
            entityProcessorService.process(gameData, world);
        }
        for (IPostEntityProcessingService postEntityProcessorService : getPostEntityProcessingServices()) {
            postEntityProcessorService.process(gameData, world);
        }
    }

    private void draw() {
        for (Entity entity : world.getEntities()) {
            Polygon polygon = polygons.get(entity);
            if (polygon == null) {
                polygon = new Polygon(entity.getPolygonCoordinates());
                polygons.put(entity, polygon);
                gameWindow.getChildren().add(polygon);
            }
            polygon.setTranslateX(entity.getX());
            polygon.setTranslateY(entity.getY());
            polygon.setRotate(entity.getRotation());

            polygon.setFill(javafx.scene.paint.Color.rgb(entity.getRgb()[0],entity.getRgb()[1],entity.getRgb()[2]));

//            polygon.setFill(Color.WHITE);
        }

    }

    private Collection<? extends IGamePluginService> getPluginServices() {
        return ServiceLoader.load( IGamePluginService.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }

    private Collection<? extends IEntityProcessingService> getEntityProcessingServices() {
        return ServiceLoader.load(IEntityProcessingService.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }

    private Collection<? extends IPostEntityProcessingService> getPostEntityProcessingServices() {
        return ServiceLoader.load(IPostEntityProcessingService.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }

}
