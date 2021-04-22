import com.google.gson.JsonArray;

class Parser {
    protected String parseJsonArray(JsonArray data) {
        StringBuilder sb = new StringBuilder();
        int starsSum = 0;

        // get username
        sb.append(data.get(0).getAsJsonObject()
                .get("owner").getAsJsonObject()
                .get("login").toString()
                .replaceAll("\"", "") + "'s repositories:\n");

        // get all repo's names
        for (int i = 0; i < data.size(); i++) {
            sb.append(i + 1 + ". " + data.get(i).getAsJsonObject().get("name").toString() + "\n");
            starsSum += Integer.parseInt(data.get(i).getAsJsonObject().get("stargazers_count").toString());
        }

        sb.append("Total sum of stars: " + starsSum + "\n");

        if (starsSum == 0) {
            sb.append("Seems like you have zero stars :(\n");
        }

        return sb.toString();
    }
}
