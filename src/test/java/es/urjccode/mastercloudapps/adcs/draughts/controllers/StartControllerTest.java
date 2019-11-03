package es.urjccode.mastercloudapps.adcs.draughts.controllers;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import es.urjccode.mastercloudapps.adcs.draughts.models.Game;
import es.urjccode.mastercloudapps.adcs.draughts.models.State;
import es.urjccode.mastercloudapps.adcs.draughts.models.StateValue;

public class StartControllerTest {

    Game game;
    State state;

    public StartControllerTest() {
        this.state = new State();
        this.game = new Game();
    }

    @Test
    public void givenStartControllerWhenStartGameThenChangeState() {
        StartController startController = new StartController(this.game, this.state);
        assertEquals(StateValue.INITIAL, this.state.getStateValue());
        startController.start();
        assertEquals(StateValue.OPENED_GAME, this.state.getStateValue());
    }
}