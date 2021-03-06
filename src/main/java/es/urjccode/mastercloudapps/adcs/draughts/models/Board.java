package es.urjccode.mastercloudapps.adcs.draughts.models;

class Board {

    private Square[][] squares;
    static final int DIMENSION = 8;

    Board() {
        this.squares = new Square[Board.DIMENSION][Board.DIMENSION];
        for (int i = 0; i < Board.DIMENSION; i++) {
            for (int j = 0; j < Board.DIMENSION; j++) {
                this.squares[i][j] = new Square();
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < Board.DIMENSION; j++) {
                if (new Coordinate(i, j).isBlack()) {
                    this.squares[i][j].put(new Piece(Color.BLACK));
                }
            }
        }
        for (int i = 5; i < Board.DIMENSION; i++) {
            for (int j = 0; j < Board.DIMENSION; j++) {
                if (new Coordinate(i, j).isBlack()) {
                    this.squares[i][j].put(new Piece(Color.WHITE));
                }
            }
        }
    }

    public void setSquares(Square[][] squares) {
        this.squares = squares;
    }

    public Color getColor(Coordinate coordinate) {
        return this.squares[coordinate.getRow()][coordinate.getColumn()].getColor();
    }

    public Piece getPiece(Coordinate coordinate) {
        return this.squares[coordinate.getRow()][coordinate.getColumn()].getPiece();
    }

    boolean isEmpty(Coordinate coordinate) {
        return this.squares[coordinate.getRow()][coordinate.getColumn()].isEmpty();
    }

    public void move(Coordinate origin, Coordinate target) {
        Piece piece = this.squares[origin.getRow()][origin.getColumn()].remove();
        this.squares[target.getRow()][target.getColumn()].put(piece);
    }

    public void remove(Coordinate coordinate) {
        assert coordinate != null;
        assert this.getPiece(coordinate) != null;
        this.squares[coordinate.getRow()][coordinate.getColumn()].remove();
    }

    public boolean isLoser(Color color) {
        boolean thereArePieces = false;
        boolean even = true;
        for (int i = 0; i < Board.DIMENSION; i++) {
            for (int j = even ? 1 : 0; j < Board.DIMENSION; j += 2) {
                thereArePieces = thereArePieces || (this.squares[i][j].getColor() == color);
                if (thereArePieces) {
                    break;
                }
            }
            if (thereArePieces) {
                break;
            }
            even = !even;
        }
        return !thereArePieces;
    }

    public boolean thereAreNoMovements(Color color) {
        boolean isMovable = false;
        boolean even = true;
        for (int i = 0; i < Board.DIMENSION; i++) {
            for (int j = even ? 1 : 0; j < Board.DIMENSION; j += 2) {
                if (this.squares[i][j].getColor() == color) {
                    if (this.squares[i][j].getColor() == Color.BLACK) {
                        isMovable = isMovable || this.checkSquareMovementIsPossible(i+1, j+1) || 
                        this.checkSquareMovementIsPossible(i+1, j-1);
                    } else {
                        isMovable = isMovable || this.checkSquareMovementIsPossible(i-1, j+1) || 
                        this.checkSquareMovementIsPossible(i-1, j-1);
                    }
                }
                if (isMovable) {
                    break;
                }
            }
            if (isMovable) {
                break;
            }
            even = !even;
        }
        return !isMovable;
    }

    private boolean checkSquareMovementIsPossible(int row, int column) {
        try {
            return this.squares[row][column].isEmpty();
        } catch(Exception  e) {
            return false;
        }
    }

    @Override
    public String toString() {
        String string = " ";
        for (int j = 0; j < Board.DIMENSION; j++) {
            string += j;
        }
        string += "\n";
        for (int i = 0; i < Board.DIMENSION; i++) {
            string += i;
            for (int j = 0; j < Board.DIMENSION; j++) {
                Piece piece = this.squares[i][j].getPiece();
                if (piece == null) {
                    string += " ";
                } else if (piece.isBlack()) {
                    string += "n";
                } else {
                    string += "b";
                }
            }
            string += i + "\n";
        }
        string += " ";
        for (int j = 0; j < Board.DIMENSION; j++) {
            string += j;
        }
        return string;
    }

}