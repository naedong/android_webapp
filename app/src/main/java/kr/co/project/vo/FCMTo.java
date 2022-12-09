package kr.co.project.vo;

public class FCMTo
{
    private FCMData data;
    private String to;

    public FCMData getData() {
        return data;
    }

    public String getTo() {
        return to;
    }

    public void setData(FCMData data) {
        this.data = data;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public static class FCMData {
        private  String title;
        private  String body;
        private String date;

        public String getBody() {
            return body;
        }

        public String getDate() {
            return date;
        }

        public String getTitle() {
            return title;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}

