import com.google.gson.JsonArray;

import java.util.LinkedHashMap;
import java.util.Map;

class Parser {
    protected Result parseJsonArray(JsonArray data) {
        if(data == null) {
            return new Result(null, -1);
        }

        Map<String, Integer> repoList = new LinkedHashMap<>();
        int starsSum = 0;

        // get all repo's names and stars
        for (int i = 0; i < data.size(); i++) {
            int stars = Integer.parseInt(data.get(i).getAsJsonObject().get("stargazers_count").toString());
            repoList.put(data.get(i).getAsJsonObject().get("name").toString(), stars);
            starsSum += stars;
        }

        return new Result(repoList, starsSum);
    }
}
