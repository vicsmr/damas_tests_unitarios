package es.urjccode.mastercloudapps.adcs.draughts.views;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import es.urjccode.mastercloudapps.adcs.draughts.controllers.PlayController;
import es.urjccode.mastercloudapps.adcs.draughts.models.Color;
import es.urjccode.mastercloudapps.adcs.draughts.models.Coordinate;
import es.urjccode.mastercloudapps.adcs.draughts.utils.Console;

@RunWith(MockitoJUnitRunner.class)
public class CommandViewTest {

    @Mock
    PlayController playController;

    @Mock
    Console console;

    @InjectMocks
    CommandView commandView;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void givenCommandViewWhenMovementRequiereCorrectThenNotError() {
        when(playController.getColor()).thenReturn(Color.BLACK);
        when(console.readString("Mueven las negras: ")).thenReturn("21.30\n");
        when(console.readString("Elige opción: ")).thenReturn("1");
        commandView.interact(playController);
        verify(playController).move(new Coordinate(2,1), new Coordinate(3, 0));
    }

    @Test
    public void givenCommandViewWhenMovementIsLoserContraryPlayerRequiereNextStateThenNotError() {
        when(playController.getColor()).thenReturn(Color.BLACK);
        when(console.readString("Mueven las negras: ")).thenReturn("21.30\n");
        when(console.readString("Elige opción: ")).thenReturn("1");
        when(playController.isLoser()).thenReturn(true);
        commandView.interact(playController);
        verify(playController).nextState();
    }

    @Test(expected = NumberFormatException.class)
    public void givenCommandViewWhenMovementCoordinatesAreNotNumberThenException() {
        when(playController.getColor()).thenReturn(Color.BLACK);
        when(console.readString("Mueven las negras: ")).thenReturn("2A.30\n");
        when(console.readString("Elige opción: ")).thenReturn("1");
        commandView.interact(playController);
        verify(playController).move(new Coordinate(2,1), new Coordinate(3, 0));
    }

    @Test(expected = StringIndexOutOfBoundsException.class)
    public void givenCommandViewWhenMovementCoordinatesCommandWriteLenghtIncorrectThenException() {
        when(playController.getColor()).thenReturn(Color.BLACK);
        when(console.readString("Mueven las negras: ")).thenReturn("2");
        when(console.readString("Elige opción: ")).thenReturn("1");
        commandView.interact(playController);
        verify(playController).move(new Coordinate(2,1), new Coordinate(3, 0));
    }

    @Test
    public void givenCommandViewWhenCancelGameThenIsCancelled() {
        when(console.readString("Elige opción: ")).thenReturn("2");
        commandView.interact(playController);
        verify(playController).nextState();
    }

}