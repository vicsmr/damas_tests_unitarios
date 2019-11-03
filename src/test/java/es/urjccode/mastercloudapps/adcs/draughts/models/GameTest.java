package es.urjccode.mastercloudapps.adcs.draughts.models;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

public class GameTest {

    @Spy
    private Board board;

    @Spy
    private Turn turn;

    @InjectMocks
    private Game gameMock;

    private Game game;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    public GameTest() {
        this.game = new Game();
    }

    @Test()
    public void testGivenGameWhenMoveWithOuterCoordinateThenOutCoordinateError() {
        Coordinate origin = new Coordinate(4, 7);
        when(board.getColor(origin)).thenReturn(Color.WHITE);
        when(board.getPiece(origin)).thenReturn(new Piece(Color.WHITE));
        assertEquals(Error.OUT_COORDINATE, gameMock.move(origin, new Coordinate(3, 8)));
    }

    @Test
    public void testGivenGameWhenMoveEmptySquareThenEmptySquareError() {
        Coordinate[][] coordinates = new Coordinate[][] { { new Coordinate(4, 3), new Coordinate(3, 4) }, };
        assertEquals(Error.EMPTY_ORIGIN, game.move(coordinates[0][0], coordinates[0][1]));
    }

    @Test
    public void testGivenGameWhenMoveOppositePieceThenError() {
        assertEquals(Error.OPPOSITE_PIECE, gameMock.move(new Coordinate(2, 5), new Coordinate(3, 6)));
    }

    @Test
    public void testGivenGameWhenNotDiagonalMovementThenError() {
        assertEquals(Error.NOT_DIAGONAL, this.game.move(new Coordinate(5, 2), new Coordinate(4, 2)));
    }

    @Test
    public void testGivenGameWhenMoveWithNotAdvancedThenError() {
        Coordinate origin = new Coordinate(3, 4);
        when(board.getColor(origin)).thenReturn(Color.WHITE);
        when(board.isEmpty(origin)).thenReturn(false);
        when(board.getPiece(origin)).thenReturn(new Piece(Color.WHITE));
        assertEquals(Error.NOT_ADVANCED, gameMock.move(origin, new Coordinate(4, 5)));
    }

    @Test
    public void testGivenGameWhenNotEmptyTargeThenError() {
        Coordinate target = new Coordinate(4, 5);
        when(board.getPiece(target)).thenReturn(new Piece(Color.BLACK));
        when(board.isEmpty(target)).thenReturn(false);
        assertEquals(Error.NOT_EMPTY_TARGET, gameMock.move(new Coordinate(5, 4), target));
    }

    @Test
    public void testGivenGameWhenCorrectMovementThenOk() {
        Coordinate origin = new Coordinate(5, 0);
        Coordinate target = new Coordinate(4, 1);
        this.game.move(origin, target);
        assertNull(this.game.getColor(origin));
        assertEquals(Color.WHITE, this.game.getColor(target));
        
        origin = new Coordinate(2, 3);
        target = new Coordinate(3, 4);
        this.game.move(origin, target);
        assertNull(this.game.getColor(origin));
        assertEquals(Color.BLACK, this.game.getColor(target));
    }

    @Test
    public void testGivenGameWhenMovementThenEatPiece() {
        Coordinate origin = new Coordinate(2, 1);
        Coordinate target = new Coordinate(4, 3);
        Coordinate originEatPiece = new Coordinate(3, 2);
        when(board.getPiece(originEatPiece)).thenReturn(new Piece(Color.WHITE));
        when(turn.isColor(Color.BLACK)).thenReturn(true);
        Error error = gameMock.move(origin, target);
        assertNull(error);
        assertNull(gameMock.getColor(origin));
        verify(board).remove(originEatPiece);
        assertEquals(Color.BLACK, gameMock.getColor(target));
    }

    @Test
    public void testGivenGameWhenEatEmptyPieceThenError() {
        assertEquals(Error.EATING_EMPTY, this.game.move(new Coordinate(5, 4), new Coordinate(3, 2)));
    }

    @Test
    public void testGivenGameWhenMoveBadDistanceThenError() {
        assertEquals(Error.BAD_DISTANCE, this.game.move(new Coordinate(5, 0), new Coordinate(2, 3)));
    }

}