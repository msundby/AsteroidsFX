package dk.sdu.mmmi.collision;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import java.net.http.HttpRequest;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.io.IOException;

public class CollisionPostProcessingSystem implements IPostEntityProcessingService {
    HttpClient httpClient = HttpClient.newHttpClient();
    int scoreToAdd = 1;
    int livesDecrement = 1;

    @Override
    public void process(GameData gameData, World world) {

        System.out.println(world.getEntities().size());
        for (Entity e1 : world.getEntities()) {
            for (Entity e2 : world.getEntities()) {
                if (e1.equals(e2)) continue;
                if ((e1.getName().equals("Player") && e2.getName().equals("Player Bullet")) ||
                        e2.getName().equals("Player") && e1.getName().equals("Player Bullet")) {
                    continue;
                }

                if ((e1.getName().equals("Asteroid") && e2.getName().equals("Asteroid")) ||
                        e2.getName().equals("Asteroid") && e1.getName().equals("Asteroid")) {
                    continue;
                }

                if ((e1.getName().equals("Enemy") && e2.getName().equals("Enemy Bullet")) ||
                        e2.getName().equals("Enemy") && e1.getName().equals("Enemy Bullet")) {
                    continue;
                }

                if ((e1.getName().equals("Enemy Bullet") && e2.getName().equals("Enemy Bullet")) ||
                        e2.getName().equals("Enemy Bullet") && e1.getName().equals("Enemy Bullet")) {
                    continue;
                }

                double distanceX = e1.getX() - e2.getX();
                double distanceY = e1.getY() - e2.getY();
                double distanceBetween = Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));

                if (distanceBetween < 10) {

                    if ((e1.getName().equals("Enemy Bullet") && e2.getName().equals("Player")) ||
                            e2.getName().startsWith("Player") && e1.getName().equals("Enemy Bullet")) {

                        HttpRequest request = HttpRequest.newBuilder()
                                .uri(URI.create("http://localhost:8080/attributes/lives/decrement/" + livesDecrement))
                                .PUT(HttpRequest.BodyPublishers.noBody())
                                .build();

                        try {
                            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

                            if (response.statusCode() == 200) {
                                System.out.println("Lives decremented successfully");
                            } else {
                                System.out.println("Failed to decrement lives. HTTP status code: " + response.statusCode());
                            }
                        } catch (IOException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                        if ((e1.getName().equals("Player Bullet") && e2.getName().startsWith("Asteroid")) ||
                                e2.getName().startsWith("Asteroid") && e1.getName().equals("Player Bullet")) {
                            world.removeEntity(e1);
                            world.removeEntity(e2);

                            HttpRequest request = HttpRequest.newBuilder()
                                    .uri(URI.create("http://localhost:8080/attributes/score/update/" + scoreToAdd))
                                    .PUT(HttpRequest.BodyPublishers.noBody())
                                    .build();

                            try {
                                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

                                if (response.statusCode() == 200) {
                                    System.out.println("Score updated successfully");
                                } else {
                                    System.out.println("Failed to update score. HTTP status code: " + response.statusCode());
                                }
                            } catch (IOException | InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                    if ((e1.getName().equals("Player Bullet") && e2.getName().startsWith("Enemy")) ||
                            e2.getName().startsWith("Enemy") && e1.getName().equals("Player Bullet")) {
                        world.removeEntity(e1);
                        world.removeEntity(e2);

                        HttpRequest request = HttpRequest.newBuilder()
                                .uri(URI.create("http://localhost:8080/attributes/score/update/" + scoreToAdd))
                                .PUT(HttpRequest.BodyPublishers.noBody())
                                .build();

                        try {
                            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

                            if (response.statusCode() == 200) {
                                System.out.println("Score updated successfully");
                            } else {
                                System.out.println("Failed to update score. HTTP status code: " + response.statusCode());
                            }
                        } catch (IOException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    }
                }
            }
        }
    }



