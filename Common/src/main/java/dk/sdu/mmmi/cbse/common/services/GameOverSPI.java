package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;

public interface GameOverSPI {

    void isGameOver(GameData gamedata, boolean trueOrfalse);
}
