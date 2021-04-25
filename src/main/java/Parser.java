import com.google.gson.JsonArray;

class Parser {
    protected Result parseJsonArray(JsonArray data) {
        if(data == null) {
            return new Result("", -1);
        }

        StringBuilder sb = new StringBuilder();
        int starsSum = 0;

        // get username
        sb.append(data.get(0).getAsJsonObject()
                .get("owner").getAsJsonObject()
                .get("login").toString()
                .replaceAll("\"", "")).append("'s repositories:</br>\n");

        // get all repo's names
        for (int i = 0; i < data.size(); i++) {
            int stars = Integer.parseInt(data.get(i).getAsJsonObject().get("stargazers_count").toString());
            sb.append(i+1)
                    .append(". ")
                    .append(data.get(i).getAsJsonObject().get("name").toString())
                    .append(", stars: " + stars)
                    .append("<br/>");
            starsSum += stars;
        }

        return new Result(sb.toString(), starsSum);
    }
}
