import java.util.Scanner;

public class Main
{
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        UserHashTable userHashTable = new UserHashTable(5);
        User loggedInUser = null;

        while (true) {
            if (loggedInUser == null) {
                loggedInUser = loginMenu(userHashTable);
            } else {
                loggedInUser = userMenu(loggedInUser, userHashTable);
            }
        }
    }

    private static User loginMenu(UserHashTable userHashTable) {
        System.out.println("\nSocial Media Feed - Login");
        System.out.println("1. Login");
        System.out.println("2. Create New Account");
        System.out.println("3. Exit");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                return loginUser(userHashTable);

            case 2:
                createAccount(userHashTable);
                return null;

            case 3:
                System.out.println("Exiting... Goodbye!");
                System.exit(0);
                return null;

            default:
                System.out.println("Invalid choice. Please try again.");
                return null;
        }
    }

    private static User userMenu(User loggedInUser, UserHashTable userHashTable) {
        System.out.println("\nSocial Media Feed - User Menu");
        System.out.println("1. Add Post");
        System.out.println("2. Like or Comment");
        System.out.println("3. View Feed");
        System.out.println("4. View All Feeds");
        System.out.println("5. Delete Post");
        System.out.println("6. Logout");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                addPost(loggedInUser);
                break;

            case 2:
                likeOrComment(userHashTable, loggedInUser);
                break;

            case 3:
                viewFeed(loggedInUser);
                break;

            case 4:
                userHashTable.displayAllFeeds();
                break;

            case 5:
                deletePost(loggedInUser);
                break;

            case 6:
                System.out.println("Logged out successfully.");
                return null;

            default:
                System.out.println("Invalid choice. Please try again.");
        }
        return loggedInUser;
    }

    private static User loginUser(UserHashTable userHashTable) {
        System.out.print("Enter User ID: ");
        int userId = scanner.nextInt();
        User user = userHashTable.getUser(userId);

        if (user == null) {
            System.out.println("User not found.");
        } else {
            System.out.print("Enter Password: ");
            scanner.nextLine();
            String password = scanner.nextLine();
            if (!user.password.equals(password)) {
                System.out.println("Incorrect password.");
            } else {
                System.out.println("Login successful. Welcome, User ID: " + user.userId);
                return user;
            }
        }
        return null;
    }

    private static void createAccount(UserHashTable userHashTable) {
        System.out.print("Enter New User ID: ");
        int newUserId = scanner.nextInt();
        System.out.print("Enter Password: ");
        scanner.nextLine();
        String newPassword = scanner.nextLine();

        if (userHashTable.addUser(newUserId, newPassword)) {
            System.out.println("Account created successfully! Your User ID is " + newUserId);
        } else {
            System.out.println("User ID already exists. Please try another ID.");
        }
    }

    private static void addPost(User loggedInUser) {
        System.out.print("Enter Post Caption: ");
        scanner.nextLine();
        String caption = scanner.nextLine();
        loggedInUser.addPost(caption);
        System.out.println("Post added.");
    }

    private static void likeOrComment(UserHashTable userHashTable, User loggedInUser) {
        System.out.println("1. Like");
        System.out.println("2. Comment");
        System.out.println("3. Delete Comment");
        System.out.println("4. Delete like");
        System.out.println("4. Exit");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                likePost(userHashTable, loggedInUser);
                break;

            case 2:
                commentOnPost(userHashTable, loggedInUser);
                break;

            case 3:
                deleteComment(userHashTable, loggedInUser);
                break;

            case 4:
                deletelike(userHashTable, loggedInUser);
                break;
            case 5:
                return;

            default:
                System.out.println("Invalid choice.");
        }
    }

    private static void likePost(UserHashTable userHashTable, User loggedInUser) {
        System.out.println("Select a post to like:");
        userHashTable.displayAllFeeds();

        System.out.print("Enter the User ID of the post owner: ");
        int postOwnerId = scanner.nextInt();
        User postOwner = userHashTable.getUser(postOwnerId);

        if (postOwner == null) {
            System.out.println("User not found.");
        } else {
            Post temp = postOwner.postHead;
            int postNumber = 1;

            while (temp != null) {
                System.out.println(postNumber + ". " + temp.caption + " (Likes: " + temp.likes + ")");
                temp = temp.next;
                postNumber++;
            }

            System.out.print("Select the post number to like: ");
            int postIndex = scanner.nextInt();
            temp = postOwner.postHead;

            for (int i = 1; i < postIndex && temp != null; i++) {
                temp = temp.next;
            }

            if (temp != null) {
                if (temp.like(loggedInUser.userId)) {
                    System.out.println("Post liked successfully!");
                } else {
                    System.out.println("You have already liked this post.");
                }
            } else {
                System.out.println("Invalid post number.");
            }
        }
    }

    private static void commentOnPost(UserHashTable userHashTable, User loggedInUser) {
        System.out.println("Select a post to comment on:");
        userHashTable.displayAllFeeds();

        System.out.print("Enter the User ID of the post owner: ");
        int postOwnerId = scanner.nextInt();
        User postOwner = userHashTable.getUser(postOwnerId);

        if (postOwner == null) {
            System.out.println("User not found.");
        } else {
            Post temp = postOwner.postHead;
            int postNumber = 1;

            while (temp != null) {
                System.out.println(postNumber + ". " + temp.caption + " (Likes: " + temp.likes + ")");
                temp = temp.next;
                postNumber++;
            }

            System.out.print("Select the post number to comment on: ");
            int postIndex = scanner.nextInt();
            temp = postOwner.postHead;

            for (int i = 1; i < postIndex && temp != null; i++) {
                temp = temp.next;
            }

            if (temp != null) {
                System.out.print("Enter your comment: ");
                scanner.nextLine();
                String commentText = scanner.nextLine();
                temp.addComment(commentText, loggedInUser.userId);
                System.out.println("Comment added successfully!");
            } else {
                System.out.println("Invalid post number.");
            }
        }
    }

    private static void deleteComment(UserHashTable userHashTable, User loggedInUser) {
        System.out.println("Select a post to delete a comment on:");
        userHashTable.displayAllFeeds();

        System.out.print("Enter the User ID of the post owner: ");
        int postOwnerId = scanner.nextInt();
        User postOwner = userHashTable.getUser(postOwnerId);

        if (postOwner == null) {
            System.out.println("User not found.");
        } else {
            Post temp = postOwner.postHead;
            int postNumber = 1;

            while (temp != null) {
                System.out.println(postNumber + ". " + temp.caption + " (Likes: " + temp.likes + ")");
                temp = temp.next;
                postNumber++;
            }

            System.out.print("Select the post number to delete comment: ");
            int postIndex = scanner.nextInt();
            temp = postOwner.postHead;

            for (int i = 1; i < postIndex && temp != null; i++) {
                temp = temp.next;
            }

            if (temp != null) {
                boolean success = temp.deleteComment(loggedInUser.userId);
                if (success) {
                    System.out.println("Comment deleted successfully!");
                } else {
                    System.out.println("No comment found to delete.");
                }
            } else {
                System.out.println("Invalid post number.");
            }
        }
    }

    private static void deletelike(UserHashTable userHashTable, User loggedInUser) {
        System.out.println("Select a post to remove your like:");
        userHashTable.displayAllFeeds();

        System.out.print("Enter the User ID of the post owner: ");
        int postOwnerId = scanner.nextInt();
        User postOwner = userHashTable.getUser(postOwnerId);

        if (postOwner == null) {
            System.out.println("User not found.");
        } else {
            Post temp = postOwner.postHead;
            int postNumber = 1;

            while (temp != null) {
                System.out.println(postNumber + ". " + temp.caption + " (Likes: " + temp.likes + ")");
                temp = temp.next;
                postNumber++;
            }

            System.out.print("Select the post number to remove your like: ");
            int postIndex = scanner.nextInt();
            temp = postOwner.postHead;

            for (int i = 1; i < postIndex && temp != null; i++) {
                temp = temp.next;
            }

            if (temp != null) {
                boolean success = temp.removeLike(loggedInUser.userId);
                if (success) {
                    System.out.println("Like removed successfully!");
                } else {
                    System.out.println("You have not liked this post.");
                }
            } else {
                System.out.println("Invalid post number.");
            }
        }
    }



    private static void viewFeed(User loggedInUser) {
        System.out.println("Your Feed:");
        Post temp = loggedInUser.postHead;
        int postNumber = 1;

        while (temp != null) {
            System.out.println(postNumber + ". " + temp.caption);
            System.out.println("Likes: " + temp.likes);
            System.out.println("Comments: " + (temp.commentHead == null ? "None" : ""));
            Comment commentTemp = temp.commentHead;
            while (commentTemp != null) {
                System.out.println("[User ID: " + commentTemp.userId + "] " + commentTemp.text);
                commentTemp = commentTemp.next;
            }

            temp = temp.next;
            postNumber++;
        }

        if (postNumber == 1) {
            System.out.println("No posts available.");
        }
    }

    private static void deletePost(User loggedInUser) {
        System.out.println("Select a post to delete:");
        Post temp = loggedInUser.postHead;
        int postNumber = 1;

        while (temp != null) {
            System.out.println(postNumber + ". " + temp.caption);
            temp = temp.next;
            postNumber++;
        }

        if (postNumber == 1) {
            System.out.println("No posts to delete.");
        } else {
            System.out.print("Enter the post number to delete: ");
            int deletePostIndex = scanner.nextInt();

            if (loggedInUser.deletePost(deletePostIndex)) {
                System.out.println("Post deleted successfully.");
            } else {
                System.out.println("Invalid post number.");
            }
        }
    }
}
