package es.urjccode.mastercloudapps.adcs.draughts.views;

import es.urjccode.mastercloudapps.adcs.draughts.utils.Console;
import es.urjccode.mastercloudapps.adcs.draughts.controllers.PlayController;
import es.urjccode.mastercloudapps.adcs.draughts.models.Error;
import es.urjccode.mastercloudapps.adcs.draughts.models.Coordinate;

public class CommandView {

    private Console console;

    private static final String[] COLORS = {"blancas", "negras"};

    private final String LOSE_GAME = "Derrota!!! No puedes mover tus fichas!!!";
    private final String COMMAND_MOVE = "1) Mover";
    private final String COMMAND_CANCEL = "2) Cancelar";
    private final String CHOOSE = "Elige opción: ";
    private final String ERROR = "Error!!! ";

    public CommandView() {
        this.console = new Console();
    }

    public void interact(PlayController playController) {
        Error errorOption;
        this.console.writeln(COMMAND_MOVE);
        this.console.writeln(COMMAND_CANCEL);
        do {
            errorOption = null;
            String command = this.console.readString(CHOOSE);
            if ("1".equals(command)) {
                Error error = null;
                String color = CommandView.COLORS[playController.getColor().ordinal()];
                do {
                    String moveOption = this.console.readString("Mueven las " + color + ": ");
                    int originRow = Integer.parseInt(moveOption.substring(0, 1));
                    int originColumn = Integer.parseInt(moveOption.substring(1, 2));
                    int targetRow = Integer.parseInt(moveOption.substring(3, 4));
                    int targetColumn = Integer.parseInt(moveOption.substring(4, 5));
                    error = playController.move(new Coordinate(originRow, originColumn), new Coordinate(targetRow, targetColumn));
                    if (error != null) {
                        console.writeln(ERROR + error.getText());
                    }
                } while (error != null);
                this.console.writeln();
                this.console.writeln(playController.getStringBoardGame());
                if (playController.isLoser()) {
                    playController.nextState();
                    this.console.writeln(LOSE_GAME);
                }
            } else if ("2".equals(command)) {
                playController.nextState();
            } else {
                errorOption = Error.WRONG_OPTION;
                console.writeln(ERROR + errorOption.getText());
            }
        } while (errorOption != null);
    }

}