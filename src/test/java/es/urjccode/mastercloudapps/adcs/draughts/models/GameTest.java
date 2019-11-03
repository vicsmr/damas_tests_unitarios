package es.urjccode.mastercloudapps.adcs.draughts.models;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class GameTest {

    @Mock
    private Board board;

    @Mock
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
        when(turn.isColor(Color.WHITE)).thenReturn(true);
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
        Coordinate origin = new Coordinate(3, 6);
        when(board.getColor(origin)).thenReturn(Color.BLACK);
        when(turn.isColor(Color.WHITE)).thenReturn(true);
        when(board.getPiece(origin)).thenReturn(new Piece(Color.BLACK));
        assertEquals(Error.OPPOSITE_PIECE, gameMock.move(origin, new Coordinate(2, 7)));
    }

    @Test
    public void testGivenGameWhenNotDiagonalMovementThenError() {
        assertEquals(Error.NOT_DIAGONAL, this.game.move(new Coordinate(5, 2), new Coordinate(4, 2)));
    }

    @Test
    public void testGivenGameWhenMoveWithNotAdvancedThenError() {
        Coordinate origin = new Coordinate(3, 4);
        when(board.getColor(origin)).thenReturn(Color.WHITE);
        when(turn.isColor(Color.WHITE)).thenReturn(true);
        when(board.getPiece(origin)).thenReturn(new Piece(Color.WHITE));
        assertEquals(Error.NOT_ADVANCED, gameMock.move(origin, new Coordinate(4, 5)));
    }

    @Test
    public void testGivenGameWhenNotEmptyTargeThenError() {
        Coordinate origin = new Coordinate(4, 7);
        Coordinate target = new Coordinate(3, 6);
        when(board.getColor(origin)).thenReturn(Color.WHITE);
        when(turn.isColor(Color.WHITE)).thenReturn(true);
        when(board.getPiece(origin)).thenReturn(new Piece(Color.WHITE));
        when(board.getPiece(target)).thenReturn(new Piece(Color.BLACK));
        assertEquals(Error.NOT_EMPTY_TARGET, gameMock.move(origin, target));
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
        Coordinate[][] coordinates = new Coordinate[][] { { new Coordinate(5, 0), new Coordinate(4, 1) },
                { new Coordinate(2, 1), new Coordinate(3, 0) }, { new Coordinate(5, 2), new Coordinate(4, 3) },
                { new Coordinate(3, 0), new Coordinate(5, 2) }, };
        Error error = null;
        for (int i = 0; i < coordinates.length; i++) {
            assertNull(error);
            error = game.move(coordinates[i][0], coordinates[i][1]);
        }
        assertNull(error);
        assertNull(game.getColor(new Coordinate(3, 0)));
        assertNull(game.getColor(new Coordinate(4, 1)));
        assertEquals(Color.BLACK, game.getColor(new Coordinate(5, 2)));
    }

    @Test
    public void testGivenGameWhenEatEmptyPieceThenError() {
        assertEquals(Error.EATING_EMPTY, this.game.move(new Coordinate(5, 4), new Coordinate(3, 2)));
    }

    @Test
    public void testGivenGameWhenMoveBadDistanceThenError() {
        assertEquals(Error.BAD_DISTANCE, this.game.move(new Coordinate(5, 0), new Coordinate(2, 3)));
    }

    @Test
    public void testGivenGameWhenIsLoserThenError() {
        Coordinate origin = new Coordinate(4, 7);
        Coordinate target = new Coordinate(3, 6);
        when(board.getColor(origin)).thenReturn(Color.WHITE);
        when(turn.isColor(Color.WHITE)).thenReturn(true);
        when(board.getPiece(origin)).thenReturn(new Piece(Color.WHITE));
        when(board.getPiece(target)).thenReturn(new Piece(Color.BLACK));
        when(board.isLoser(Color.BLACK)).thenReturn(true);
        assertEquals(Error.NOT_EMPTY_TARGET, gameMock.move(origin, target));
    }

}