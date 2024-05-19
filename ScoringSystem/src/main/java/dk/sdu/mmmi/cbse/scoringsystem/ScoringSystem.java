package dk.sdu.mmmi.cbse.scoringsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RequestMapping("/attributes")
@RestController
@CrossOrigin
public class ScoringSystem {

    private int totalscore = 0;
    private int playerlives = 3;

    boolean gameOver = false;
    public static void main(String[] args) {
        SpringApplication.run(ScoringSystem.class, args);
    }

    @GetMapping("/score")
    public int getScore() {
        return totalscore;
    }

    @PutMapping("score/update/{score}")
    public int updateScore(@PathVariable(value = "score") Long score) {
        totalscore += score;
        return totalscore;
    }

    @GetMapping("/lives")
    public int getLives() {
        return playerlives;
    }

    @PutMapping("/lives/decrement/{decrement}")
    public int decrementLives(@PathVariable(value = "decrement") int decrement) {
        playerlives = playerlives - decrement;
        return playerlives;
    }
}