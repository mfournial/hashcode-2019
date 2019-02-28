import com.sun.deploy.util.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Photo {
    final boolean horizontal;
    final int id;
    int id2;
final Set<Integer> tags;
    public Photo(Set<Integer> tags, int id) {
        this.horizontal = true;
        this.tags = tags;
        this.id = id;
    }

    public Photo(Set<Integer> tags, int id, int id2) {
        this.id2 = id2;
        this.horizontal = false;
        this.tags = tags;
        this.id = id;
    }

    @Override
    public String toString() {
        return (horizontal? "H " : "V ") + StringUtils.join(tags, " ");
    }

    public String output() {
        return  "\n" +  id + (horizontal ? "" : " " + id2);
    }


    public int calculateScore(Photo other) {
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
