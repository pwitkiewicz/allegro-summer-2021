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
                .replaceAll("\"", "")).append("'s repositories:</br>\n");

        // get all repo's names
        for (int i = 0; i < data.size(); i++) {
            sb.append(i+1)
                    .append(". ")
                    .append(data.get(i).getAsJsonObject().get("name").toString())
                    .append("<br/>");
            starsSum += Integer.parseInt(data.get(i).getAsJsonObject().get("stargazers_count").toString());
        }

        // add sum of stars to the output
        sb.append("Total sum of stars: ")
                .append(starsSum)
                .append("</br>");

        if (starsSum == 0) {
            sb.append("Seems like this user have zero stars :(</br>");
        }

        return sb.toString();
    }
}
