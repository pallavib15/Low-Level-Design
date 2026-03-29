package services;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class RandomFiringStrategy implements FiringStrategy{
    private  final Set<String> st=new HashSet<String>();
    private final Random rand=new Random();

    @Override
    public int[] getNextMoves(Board board, boolean isA) {
        int y= isA?rand.nextInt(board.getSize()/2, board.getSize()): rand.nextInt(0, board.getSize()/2);
        int x= rand.nextInt(board.getSize());
        String key=x+"-"+y;
        if(st.contains(key)){
            throw new IllegalArgumentException("It eas already fires");
        }
        st.add(key);
return new int[]{x,y};
    }
}
