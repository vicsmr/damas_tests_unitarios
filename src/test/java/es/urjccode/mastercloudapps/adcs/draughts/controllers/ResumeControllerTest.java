package es.urjccode.mastercloudapps.adcs.draughts.controllers;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import es.urjccode.mastercloudapps.adcs.draughts.models.Game;
import es.urjccode.mastercloudapps.adcs.draughts.models.State;
import es.urjccode.mastercloudapps.adcs.draughts.models.StateValue;

public class ResumeControllerTest {

    public ResumeControllerTest() {
    }

    @Test
    public void givenResumeControllerWhenResumeGameMoveToInitialStateRequiereCorrectThenNotError() {
        State state = new State();
        Game game = new Game();
        ResumeController resumeController = new ResumeController(game, state);
        assertEquals(StateValue.INITIAL, state.getStateValue());
        resumeController.nextState();
        assertEquals(StateValue.OPENED_GAME, state.getStateValue());
        resumeController.nextState();
        assertEquals(StateValue.FINAL, state.getStateValue());
        resumeController.resume(true);
        assertEquals(StateValue.INITIAL, state.getStateValue());
    }
}