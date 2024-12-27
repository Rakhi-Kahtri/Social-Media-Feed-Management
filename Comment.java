public class Comment {
    String text;
    int userId;
    Comment next;

    public Comment(String text, int userId) {
        this.text = text;
        this.userId = userId;
        this.next = null;
    }
}
