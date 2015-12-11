// This file contains a SUGGESTION for the structure of your program.  You
// may change any of it, or add additional files to this directory (package),
// as long as you conform to the project specification.  Do not, however,
// modify the contents of the 'gui' subpackage.

// We have indicated parts of the file that you might especially want to
// fill in with "// FIXME"  or "// REPLACE..." comments.  But again,
// you can change just about anything.

// Comments that start with "//" are intended to be removed from your
// solutions.

package game2048;

import ucb.util.CommandArgs;

import game2048.gui.Game;
import static game2048.Main.Side.*;

import java.util.Random;

/** The main class for the 2048 game.
 *  @author
 */
public class Main {

    /** Size of the board: number of rows and of columns. */
    static final int SIZE = 4;
    /** Number of squares on the board. */
    static final int SQUARES = SIZE * SIZE;

    /** Symbolic names for the four sides of a board. */
    static enum Side { NORTH, EAST, SOUTH, WEST };

    /** The main program.  ARGS may contain the options --seed=NUM,
     *  (random seed); --log (record moves and random tiles
     *  selected.); --testing (take random tiles and moves from
     *  standard input); and --no-display. */
    public static void main(String... args) {
        CommandArgs options =
            new CommandArgs("--seed=(\\d+) --log --testing --no-display",
                            args);
        if (!options.ok()) {
            System.err.println("Usage: java game2048.Main [ --seed=NUM ] "
                               + "[ --log ] [ --testing ] [ --no-display ]");
            System.exit(1);
        }

        Main game = new Main(options);

        while (game.play()) {
            /* No action */
        }
        System.exit(0);
    }

    /** A new Main object using OPTIONS as options (as for main). */
    Main(CommandArgs options) {
        boolean log = options.contains("--log"),
            display = !options.contains("--no-display");
        long seed = !options.contains("--seed") ? 0 : options.getLong("--seed");
        _testing = options.contains("--testing");
        _game = new Game("2048", SIZE, seed, log, display, _testing);
    }

    /** Reset the score for the current game to 0 and clear the board. */
    void clear() {
        _score = 0;
        _count = 0;
        _game.clear();
        _game.setScore(_score, _maxScore);
        for (int r = 0; r < SIZE; r += 1) {
            for (int c = 0; c < SIZE; c += 1) {
                _board[r][c] = 0;
            }
        }
    }

    /** Play one game of 2048, updating the maximum score. Return true
     *  iff play should continue with another game, or false to exit. */
    boolean play() {
        // FIXME?

        while (true) {
            // FIXME?
            if (_count == 0){
                setRandomPiece();
                setRandomPiece();
            }

            else {
                setRandomPiece();
            }

            if (gameOver()) {
                if (_maxScore < _score) {
                    _maxScore = _score;
                }
                _game.endGame();
            }

        GetMove:
            while (true) {
                String key = _game.readKey();
                if (key.equals("\u2191")) {
                    key = "Up";
                }
                if (key.equals("\u2193")) {
                    key = "Down";
                }
                if (key.equals("\u2190")) {
                    key = "Left";
                } 
                if (key.equals("\u2192")) {
                    key = "Right";
                }
                System.out.println(key);
                switch (key) {
                case "Up": case "Down": case "Left": case "Right":
                    if (!gameOver() && tiltBoard(keyToSide(key))) {
                        break GetMove;
                    }
                    break;
                // FIXME?
                case "Quit":
                    return false;
                case "New Game":
                    clear();
                    play();
                default:
                    break;
                }
            
            }
            // FIXME?
        }
    }

    /** Return true iff the current game is over (no more moves
     *  possible). */
    boolean gameOver() {
        // FIXME?
        if (_count == SQUARES) {
            for (int r = 0; r < SIZE - 1; r += 1) { 
                for (int c = 0; c < SIZE - 1; c += 1) {        
                    if (_board[r][c] == _board[r][c + 1]) {
                        return false;
                        }
                    if (_board[r][c] == _board[r + 1][c]) {
                        return false;
                    }
                }
                for (int i = 0; i < SIZE - 1; i++){
                    if (_board[3][i] == _board[3][i + 1]) {
                        return false;
                    }
                }
                for (int x = 0; x < SIZE - 1; x++) {
                    if (_board[x][3] == _board[x + 1][3]) {
                        return false;
                    }
                }
            }
            
            return true;
        }
        else {
            return false;
        }
    }



    /** Add a tile to a random, empty position, choosing a value (2 or
     *  4) at random.  Has no effect if the board is currently full. */
    void setRandomPiece() { //try this first!
        if (_count == SQUARES) {
            return;
        }
        int [] randtile = _game.getRandomTile();
        while (_board[randtile[1]][randtile[2]] != 0) {
            randtile = _game.getRandomTile();
            }
        _board[randtile[1]][randtile[2]] = randtile[0];
         _game.addTile(randtile[0], randtile[1], randtile[2]);
        _count += 1;
        }
    

    // private final int[][] _board = new int[SIZE][SIZE];




    /** Perform the result of tilting the board toward SIDE.
     *  Returns true iff the tilt changes the board. **/
    boolean tiltBoard(Side side) {
        /* As a suggestion (see the project text), you might try copying
         * the board to a local array, turning it so that edge SIDE faces
         * north.  That way, you can re-use the same logic for all
         * directions.  (As usual, you don't have to). */
        int[][] board = new int[SIZE][SIZE];
        // FIXME?
        System.out.println(_board);

        for (int r = 0; r < SIZE; r += 1) { //row
            for (int c = 0; c < SIZE; c += 1) { //column
                board[r][c] =
                    _board[tiltRow(side, r, c)][tiltCol(side, r, c)];
            }
        }
        int counter = 0;
        for (int r = 1; r < SIZE; r += 1) {
            for (int c = 0; c < SIZE; c += 1) {
                if (board[r][c] != 0) {
                    int temp = r;
                    while ((temp > 1) && (board[temp - 1][c] == 0)) {
                        temp = temp - 1;
                    }
                    if (board[temp - 1][c] == 0) {
                        _game.moveTile(board[r][c], tiltRow(side, r, c), tiltCol(side, r, c), 
                                tiltRow(side, temp - 1, c), tiltCol(side, temp - 1, c));
                        board[temp-1][c] = board[r][c];
                        board[r][c] = 0;
                        counter = counter + 1;
                    }
                    else if (board[r][c] == board[temp - 1][c]) {
                        _game.mergeTile(board[r][c], (board[r][c] * 2), tiltRow(side, r, c), 
                                tiltCol(side, r, c), tiltRow(side, temp - 1, c), tiltCol(side, temp - 1, c));
                        board[temp - 1][c] = 2 * board[r][c] + 1;
                        _score = _score + (2 * board[r][c]);
                        _game.setScore(_score, _maxScore);
                        board[r][c] = 0;
                        _count -= 1;
                        counter = counter + 1;
                    }  
                    else if (board[temp][c] == 0){
                        _game.moveTile(board[r][c], tiltRow(side, r, c), tiltCol(side, r, c), 
                                tiltRow(side, temp, c), tiltCol(side, temp, c));
                        board[temp][c] = board[r][c];
                        board[r][c] = 0;
                        counter = counter + 1;
                    }
                }
            }
        }
        for (int r = 0; r < SIZE; r += 1) {
            for (int c = 0; c < SIZE; c += 1) {
                if (board[r][c] % 2 == 1) {
                    board[r][c] = board[r][c] - 1;
                }
                _board[tiltRow(side, r, c)][tiltCol(side, r, c)]
                    = board[r][c];
            }
        }
        _game.displayMoves();
        if (counter == 0) {
            return false;
        }
        return true;
    }

    /** Return the row number on a playing board that corresponds to row R
     *  and column C of a board turned so that row 0 is in direction SIDE (as
     *  specified by the definitions of NORTH, EAST, etc.).  So, if SIDE
     *  is NORTH, then tiltRow simply returns R (since in that case, the
     *  board is not turned).  If SIDE is WEST, then column 0 of the tilted
     *  board corresponds to row SIZE - 1 of the untilted board, and
     *  tiltRow returns SIZE - 1 - C. */
    int tiltRow(Side side, int r, int c) {
        switch (side) {
        case NORTH:
            return r;
        case EAST:
            return c;
        case SOUTH:
            return SIZE - 1 - r;
        case WEST:
            return SIZE - 1 - c;
        default:
            throw new IllegalArgumentException("Unknown direction");
        }
    }

    /** Return the column number on a playing board that corresponds to row
     *  R and column C of a board turned so that row 0 is in direction SIDE
     *  (as specified by the definitions of NORTH, EAST, etc.). So, if SIDE
     *  is NORTH, then tiltCol simply returns C (since in that case, the
     *  board is not turned).  If SIDE is WEST, then row 0 of the tilted
     *  board corresponds to column 0 of the untilted board, and tiltCol
     *  returns R. */
    int tiltCol(Side side, int r, int c) {
        switch (side) {
        case NORTH:
            return c;
        case EAST:
            return SIZE - 1 - r;
        case SOUTH:
            return SIZE - 1 - c;
        case WEST:
            return r;
        default:
            throw new IllegalArgumentException("Unknown direction");
        }
    }

    /** Return the side indicated by KEY ("Up", "Down", "Left",
     *  or "Right"). */
    Side keyToSide(String key) {
        switch (key) {
        case "Up":
            return NORTH;
        case "Down":
            return SOUTH;
        case "Left":
            return WEST;
        case "Right":
            return EAST;
        default:
            throw new IllegalArgumentException("unknown key designation");
        }
    }

    /** Represents the board: _board[r][c] is the tile value at row R,
     *  column C, or 0 if there is no tile there. */
    private final int[][] _board = new int[SIZE][SIZE];

    /** True iff --testing option selected. */
    private boolean _testing;
    /** THe current input source and output sink. */
    private Game _game;
    /** The score of the current game, and the maximum final score
     *  over all games in this session. */
    private int _score, _maxScore;
    /** Number of tiles on the board. */
    private int _count;
}
