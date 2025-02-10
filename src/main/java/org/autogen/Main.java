package org.autogen;

public class Main {

    public static void fetchAlibabaRealTimeDataWarehouseUpdates() {
        fetchRecentDiscussions("alibaba-realtime-data-warehouse");
    }

    public static void fetchMeituanRealTimeDataWarehouseUpdates() {
        fetchRecentDiscussions("meituan-realtime-data-warehouse");
    }

    public static void fetchTencentRealTimeDataWarehouseUpdates() {
        fetchRecentDiscussions("tencent-realtime-data-warehouse");
    }


    public static void fetchRecentDiscussions(String repo) {
        try {
            String url = "https://api.github.com/repos/apache/" + repo + "/issues?state=all&sort=updated&direction=desc";
            java.net.HttpURLConnection connection = (java.net.HttpURLConnection) new java.net.URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/vnd.github.v3+json");

            java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            com.alibaba.fastjson.JSONArray issues = com.alibaba.fastjson.JSON.parseArray(response.toString());
            java.util.List<String> recentDiscussions = new java.util.ArrayList<>();
            java.time.LocalDate oneWeekAgo = java.time.LocalDate.now().minusDays(7);

            for (int i = 0; i < issues.size(); i++) {
                com.alibaba.fastjson.JSONObject issue = issues.getJSONObject(i);
                String updatedAt = issue.getString("updated_at");
                if (updatedAt != null) {
                    java.time.LocalDate updatedDate = java.time.LocalDate.parse(updatedAt.substring(0, 10));
                    if (updatedDate.isAfter(oneWeekAgo)) {
                        recentDiscussions.add("Title: " + issue.getString("title") + ", URL: " + issue.getString("html_url"));
                    }
                }
            }

            System.out.println("Recent discussions in the last week for " + repo + ":");
            for (String discussion : recentDiscussions) {
                System.out.println(discussion);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void fetchHBaseDiscussions() {
        fetchRecentDiscussions("hbase");
    }

    public static void fetchHiveDiscussions() {
        fetchRecentDiscussions("hive");
    }


    public static void fetchRecentMergedPRs(String repo) {
        try {
            String url = "https://api.github.com/repos/apache/" + repo + "/pulls?state=closed&sort=updated&direction=desc";
            java.net.HttpURLConnection connection = (java.net.HttpURLConnection) new java.net.URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/vnd.github.v3+json");

            java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            com.alibaba.fastjson.JSONArray pullRequests = com.alibaba.fastjson.JSON.parseArray(response.toString());
            java.util.List<String> recentPRs = new java.util.ArrayList<>();
            java.time.LocalDate oneWeekAgo = java.time.LocalDate.now().minusDays(7);

            for (int i = 0; i < pullRequests.size(); i++) {
                com.alibaba.fastjson.JSONObject pr = pullRequests.getJSONObject(i);
                String mergedAt = pr.getString("merged_at");
                if (mergedAt != null) {
                    java.time.LocalDate mergedDate = java.time.LocalDate.parse(mergedAt.substring(0, 10));
                    if (mergedDate.isAfter(oneWeekAgo)) {
                        recentPRs.add("Title: " + pr.getString("title") + ", URL: " + pr.getString("html_url"));
                    }
                }
            }

            System.out.println("Recent PRs merged in the last week for " + repo + ":");
            for (String pr : recentPRs) {
                System.out.println(pr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void fetchHBasePRs() {
        fetchRecentMergedPRs("hbase");
    }

    public static void fetchFlinkPRs() {
        fetchRecentMergedPRs("flink");
    }

    public static void fetchHadoopPRs() {
        fetchRecentMergedPRs("hadoop");
    }

    public static void fetchHivePRs() {
        fetchRecentMergedPRs("hive");
    }


    public static void main(String[] args) {
//        fetchHBasePRs();
//        fetchFlinkPRs();
//        fetchHadoopPRs();
//        fetchHivePRs();
//        fetchHBaseDiscussions();
//        fetchHBaseDiscussions();
        fetchAlibabaRealTimeDataWarehouseUpdates();
    }

}