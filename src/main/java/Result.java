import java.util.Map;

public class Result {
    private final Map<String, Integer> repoList;
    private final int starsSum;

    public Result(Map<String, Integer> m, int n) {
        repoList = m;
        starsSum = n;
    }

    public int getStarsSum() {
        return starsSum;
    }

    public Map<String, Integer> getRepoList() {
        return repoList;
    }
}
