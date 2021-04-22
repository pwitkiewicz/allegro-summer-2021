import com.google.gson.JsonArray;

class Parser {
    protected String parseJsonArray(JsonArray data) {
        if(data == null) {
            return "User not found";
        }

        StringBuilder sb = new StringBuilder();
        int starsSum = 0;

        // get username
        sb.append(data.get(0).getAsJsonObject()
                .get("owner").getAsJsonObject()
                .get("login").toString()
                .replaceAll("\"", "") + "'s repositories:\n\r");

        // get all repo's names
        for (int i = 0; i < data.size(); i++) {
            sb.append(i + 1 + ". " + data.get(i).getAsJsonObject().get("name").toString() + "\n\r");
            starsSum += Integer.parseInt(data.get(i).getAsJsonObject().get("stargazers_count").toString());
        }

        sb.append("Total sum of stars: " + starsSum + "\n\r");

        if (starsSum == 0) {
            sb.append("Seems like this user have zero stars :(\n\r");
        }

        return sb.toString();
    }
}
