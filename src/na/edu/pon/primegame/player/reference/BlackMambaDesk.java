package na.edu.pon.primegame.player.reference;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class BlackMambaDesk extends AbstractReferencePlayer {
   private static String NAME = "Namibian Black Mamba II";
   private static final int MIN_NR_OF_MOVES_TO_CONSIDER = 100;

   public BlackMambaDesk() {
      super(NAME);
   }

   public BlackMambaDesk(String name) {
      super(name + " (" + NAME + ")");
   }

   public long getStudentNumber() {
      return 1008955L;
   }

   public Integer makeMove(Set<Integer> availableNumbers) {
      int maxNrOfMoves = Math.max(100, (int)((double)availableNumbers.size() * 0.1D));
      TreeSet<Move> topMoves = this.getTopMoves(availableNumbers, maxNrOfMoves);
      Integer move = this.selectMove(topMoves, availableNumbers, maxNrOfMoves);
      return move;
   }

   private Integer selectMove(TreeSet<Move> moves, Set<Integer> availableNumbers, int maxNrOfLevel2Moves) {
      Iterator<Move> it = moves.iterator();
      int level1MovesToEvaluate = 10;
      int bestScore = Integer.MIN_VALUE;
      Move bestMove = (Move)moves.iterator().next();

      for(int i = 0; it.hasNext() && i < level1MovesToEvaluate; ++i) {
         Move m = (Move)it.next();
         Set<Integer> numbersLeft = this.computeFollowingBoard(availableNumbers, m.move);
         TreeSet<Move> counterMoves = this.getTopMoves(numbersLeft, maxNrOfLevel2Moves);
         if (counterMoves.size() > 0) {
            int level2Score = m.gain - ((Move)counterMoves.iterator().next()).gain;
            if (level2Score > bestScore) {
               bestMove = m;
               bestScore = level2Score;
            }
         }
      }

      return bestMove.move;
   }

   private Set<Integer> computeFollowingBoard(Set<Integer> currentBoard, int move) {
      Set<Integer> newNumbers = new TreeSet(currentBoard);
      newNumbers.remove(move);
      Iterator it = newNumbers.iterator();

      while(it.hasNext()) {
         int n = (Integer)it.next();
         if ((double)n > Math.ceil((double)(move / 2))) {
            break;
         }

         if (move % n == 0) {
            it.remove();
         }
      }

      return newNumbers;
   }

   private TreeSet<Move> getTopMoves(Set<Integer> availableNumbers, int maxNrOfMoves) {
      TreeSet<Move> topMoves = new TreeSet();
      int[] numbers = this.convertIntegerSetToArray(availableNumbers);

      for(int i = numbers.length - 1; i >= 0; --i) {
         int localScore = numbers[i] - this.sumFactors(i, numbers);
         Move move = new Move(numbers[i], localScore, (Move)null);
         topMoves.add(move);
         if (topMoves.size() > maxNrOfMoves) {
            topMoves.remove(topMoves.last());
         }

         if (numbers[i] <= ((Move)topMoves.last()).gain) {
            break;
         }
      }

      return topMoves;
   }

   private class Move implements Comparable<Move> {
      private int move;
      private int gain;

      private Move(int move, int gain) {
         this.move = move;
         this.gain = gain;
      }

      public int compareTo(Move o) {
         return o.gain - this.gain;
      }

      public String toString() {
         return this.move + " (" + this.gain + ")";
      }

      // $FF: synthetic method
      Move(int var2, int var3, Move var4) {
         this(var2, var3);
      }
   }
}
