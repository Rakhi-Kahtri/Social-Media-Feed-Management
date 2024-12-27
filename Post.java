public class Post {
    String caption;
    int likes;
    int[] likedBy;
    Comment commentHead;
    Post next;

    public Post(String caption) {
        this.caption = caption;
        this.likes = 0;
        this.likedBy = new int[100]; // Array to track user IDs who liked the post.
        this.commentHead = null;
        this.next = null;
    }

    public boolean like(int userId) {
        for (int id : likedBy) {
            if (id == userId) {
                return false; // User already liked the post.
            }
        }
        for (int i = 0; i < likedBy.length; i++) {
            if (likedBy[i] == 0) {
                likedBy[i] = userId;
                likes++;
                return true;
            }
        }
        return false; // No space left in the likedBy array.
    }

    public boolean removeLike(int userId) {
        for (int i = 0; i < likedBy.length; i++) {
            if (likedBy[i] == userId) {
                likedBy[i] = 0;
                likes--;
                return true;
            }
        }
        return false; // User has not liked the post.
    }

    public void addComment(String text, int userId) {
        Comment newComment = new Comment(text, userId);
        if (commentHead == null) {
            commentHead = newComment;
        } else {
            Comment temp = commentHead;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = newComment;
        }
    }

    public boolean deleteComment(int userId) {
        if (commentHead == null) return false;

        if (commentHead.userId == userId) {
            commentHead = commentHead.next;
            return true;
        }

        Comment temp = commentHead;
        while (temp.next != null) {
            if (temp.next.userId == userId) {
                temp.next = temp.next.next;
                return true;
            }
            temp = temp.next;
        }

        return false; // Comment not found
    }
}
