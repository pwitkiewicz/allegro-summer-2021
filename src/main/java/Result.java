public class Result {
    private final String repoList;
    private final int starsSum;

    public Result(String s, int n) {
        repoList = s;
        starsSum = n;
    }

    public int getStarsSum() {
        return starsSum;
    }

    public String getRepoList() {
        return repoList;
    }
}
