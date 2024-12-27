public class UserHashTable
{
    private UserNode[] table;
    private int size;
    private int elementCount;

    public UserHashTable(int initialSize) {
        this.size = initialSize;
        this.table = new UserNode[size];
        this.elementCount = 0;
    }

    private int hash(int userId) {
        return Math.abs(Integer.valueOf(userId).hashCode()) % size;
    }


    public boolean addUser(int userId, String password) {
        if (getUser(userId) != null) {
            return false;
        }

        if ((double) elementCount / size > 0.7) {
            resizeTable();
        }

        int index = hash(userId);
        User newUser = new User(userId, password);
        UserNode newNode = new UserNode(newUser);

        if (table[index] == null) {
            table[index] = newNode;
        } else {
            UserNode current = table[index];
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        elementCount++;
        return true;
    }

    public User getUser(int userId) {
        int index = hash(userId);
        UserNode current = table[index];
        while (current != null) {
            if (current.user.userId == userId) {
                return current.user;
            }
            current = current.next;
        }
        return null;
    }

    public void displayAllFeeds() {
        System.out.println("\n===== All Feeds =====\n");
        for (UserNode node : table) {
            while (node != null) {
                User user = node.user;
                System.out.println("User ID: " + user.userId);
                System.out.println("------------------------------");
                Post temp = user.postHead;
                int postNumber = 1;
                while (temp != null) {
                    System.out.println("  " + postNumber + ". " + temp.caption + " (Likes: " + temp.likes + ")");
                    if (temp.commentHead != null) {
                        System.out.println("    Comments:");
                        Comment commentTemp = temp.commentHead;
                        while (commentTemp != null) {
                            System.out.println("      - [User ID: " + commentTemp.userId + "] " + commentTemp.text);
                            commentTemp = commentTemp.next;
                        }
                    }
                    temp = temp.next;
                    postNumber++;
                }
                node = node.next;
            }
        }
    }

    private void resizeTable() {
        int newSize = size * 2;
        UserNode[] newTable = new UserNode[newSize];

        for (int i = 0; i < size; i++) {
            UserNode current = table[i];
            while (current != null) {
                int newIndex = hash(current.user.userId);
                UserNode newNode = new UserNode(current.user);
                if (newTable[newIndex] == null) {
                    newTable[newIndex] = newNode;
                } else {
                    UserNode temp = newTable[newIndex];
                    while (temp.next != null) {
                        temp = temp.next;
                    }
                    temp.next = newNode;
                }
                current = current.next;
            }
        }

        table = newTable;
        size = newSize;
    }
}
