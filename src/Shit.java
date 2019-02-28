import java.util.HashSet;
import java.util.Set;

public class Shit {
    final Set<Integer> tags;
    final int id;

    public Shit(Set<Integer> tags, int id) {
        this.tags = tags;
        this.id = id;
    }

    public int cmp(Shit other)  {
        Set<Integer> currentSet =  new HashSet<>(this.tags);
        currentSet.retainAll(other.tags);
        Set<Integer> two = new HashSet(this.tags);
        two.removeAll(currentSet);
        Set<Integer> three = new HashSet(other.tags);
        three.removeAll(currentSet);
        int score1 = currentSet.size();
        int score2 = two.size();
        int score3 = three.size();
        return Math.min(score1, Math.min(score2, score3));
    }

}
