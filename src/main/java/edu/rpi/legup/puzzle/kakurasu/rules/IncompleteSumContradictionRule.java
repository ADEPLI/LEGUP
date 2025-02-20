package edu.rpi.legup.puzzle.kakurasu.rules;

import edu.rpi.legup.model.gameboard.Board;
import edu.rpi.legup.model.gameboard.PuzzleElement;
import edu.rpi.legup.model.rules.ContradictionRule;
import edu.rpi.legup.puzzle.kakurasu.KakurasuBoard;
import edu.rpi.legup.puzzle.kakurasu.KakurasuCell;
import edu.rpi.legup.puzzle.kakurasu.KakurasuType;

import java.awt.*;
import java.util.List;

public class IncompleteSumContradictionRule extends ContradictionRule {
    public IncompleteSumContradictionRule() {
        super(
                "KAKU-CONT-0002",
                "Incomplete Sum",
                "The sum of this row or column cannot fulfill one of the clues.",
                "edu/rpi/legup/images/kakurasu/temp.png");
    }

    /**
     * Checks whether the transition has a contradiction at the specific puzzleElement index using
     * this rule
     *
     * @param board board to check contradiction
     * @param puzzleElement equivalent puzzleElement
     * @return null if the transition contains a contradiction at the specified puzzleElement,
     *     otherwise error message
     */
    @Override
    public String checkContradictionAt(Board board, PuzzleElement puzzleElement) {
        KakurasuBoard kakurasuBoard = (KakurasuBoard) board;
        KakurasuCell cell = (KakurasuCell) puzzleElement;

        Point loc = cell.getLocation();
        List<KakurasuCell> filledRow = kakurasuBoard.getRowCol(loc.y, KakurasuType.FILLED, true);
        List<KakurasuCell> unknownRow = kakurasuBoard.getRowCol(loc.y, KakurasuType.UNKNOWN, true);
        List<KakurasuCell> filledCol = kakurasuBoard.getRowCol(loc.x, KakurasuType.FILLED, false);
        List<KakurasuCell> unknownCol = kakurasuBoard.getRowCol(loc.x, KakurasuType.UNKNOWN, false);

        int rowSum = 0;
        for(KakurasuCell kc : filledRow) {
            rowSum += kc.getLocation().x + 1;
        }
        for(KakurasuCell kc : unknownRow) {
            rowSum += kc.getLocation().x + 1;
        }
        int colSum = 0;
        for(KakurasuCell kc : filledCol) {
            colSum += kc.getLocation().y + 1;
        }
        for(KakurasuCell kc : unknownCol) {
            colSum += kc.getLocation().y + 1;
        }

        if (rowSum < kakurasuBoard.getClue(kakurasuBoard.getWidth(), loc.y).getData()
            || colSum < kakurasuBoard.getClue(loc.x, kakurasuBoard.getHeight()).getData()) {
            return null;
        } else {
            return super.getNoContradictionMessage();
        }
    }
}
