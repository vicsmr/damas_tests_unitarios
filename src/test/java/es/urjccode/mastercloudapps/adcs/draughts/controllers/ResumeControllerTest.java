package es.urjccode.mastercloudapps.adcs.draughts.controllers;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import es.urjccode.mastercloudapps.adcs.draughts.models.Game;
import es.urjccode.mastercloudapps.adcs.draughts.models.State;
import es.urjccode.mastercloudapps.adcs.draughts.models.StateValue;

public class ResumeControllerTest {

    Game game;
    State state;

    public ResumeControllerTest() {
        this.state = new State();
        this.game = new Game();
    }

    @Test
    public void givenResumeControllerWhenResumeGameMoveToInitialStateRequiereCorrectThenNotError() {
        ResumeController resumeController = new ResumeController(this.game, this.state);
        assertEquals(StateValue.INITIAL, this.state.getStateValue());
        resumeController.nextState();
        assertEquals(StateValue.OPENED_GAME, this.state.getStateValue());
        resumeController.nextState();
        assertEquals(StateValue.FINAL, this.state.getStateValue());
        resumeController.resume(true);
        assertEquals(StateValue.INITIAL, this.state.getStateValue());
    }
}