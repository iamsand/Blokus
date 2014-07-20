package Players;

import framework.*;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class HumanGraphicalPlayer implements IPlayer {

    public void startGame(int boardWidth, int boardHeight, int numPlayers, Color color) {
        
    }

    public Action getAction(Board.PlayerView board, List<Shape> hand) {
        BlokusGamePane pane = this.new BlokusGamePane(board, hand);
        
        JFrame frame = new JFrame("Blokus");
        frame.setContentPane(pane);
        frame.pack();
        frame.setVisible(true);
        
        return pane.getAction();
    }
    
    private class BlokusGamePane extends JPanel {
        
        public BlokusGamePane(Board.PlayerView board, List<Shape> hand) {
            
        }
        
        public Action getAction() {
            // TODO: implement blocking until user has played a move
            return null;
        }
        
    }
    
}
