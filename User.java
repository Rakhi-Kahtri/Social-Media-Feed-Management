public class User {
    int userId;
    String password;
    Post postHead;

    public User(int userId, String password) {
        this.userId = userId;
        this.password = password;
        this.postHead = null;
    }

    public void addPost(String caption) {
        Post newPost = new Post(caption);
        if (postHead == null) {
            postHead = newPost;
        } else {
            Post temp = postHead;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = newPost;
        }
    }

    public boolean deletePost(int postIndex) {
        if (postIndex == 1 && postHead != null) {
            postHead = postHead.next;
            return true;
        }

        Post temp = postHead;
        int currentIndex = 1;

        while (temp != null && temp.next != null) {
            if (currentIndex == postIndex - 1) {
                temp.next = temp.next.next;
                return true;
            }
            temp = temp.next;
            currentIndex++;
        }

        return false;
    }
}
