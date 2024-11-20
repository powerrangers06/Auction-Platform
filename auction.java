import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class User {
    private String name;
    private String undercoverName;
    private int age;
    private String password;

    public User(String name, String undercoverName, int age, String password) {
        this.name = name;
        this.undercoverName = undercoverName;
        this.age = age;
        this.password = password;
    }

    public String getUndercoverName() {
        return undercoverName;
    }

    public int getAge() {
        return age;
    }
}

class Item {
    private String itemName;
    private double highestBid;
    private String highestBidderUndercoverName;

    public Item(String itemName, double startingPrice) {
        this.itemName = itemName;
        this.highestBid = startingPrice;
        this.highestBidderUndercoverName = "No bids yet";
    }

    public String getItemName() {
        return itemName;
    }

    public double getHighestBid() {
        return highestBid;
    }

    public String getHighestBidderUndercoverName() {
        return highestBidderUndercoverName;
    }

    public void placeBid(String bidderUndercoverName, double bidAmount) {
        if (bidAmount > highestBid) {
            highestBid = bidAmount;
            highestBidderUndercoverName = bidderUndercoverName;
            System.out.println("Bid placed successfully by " + bidderUndercoverName + " for $" + bidAmount);
        } else {
            System.out.println("Bid amount must be higher than the current highest bid of $" + highestBid);
        }
    }
}

class AuctionSystem {
    private List<Item> items;
    private List<User> users;
    private Scanner scanner;
    private User currentUser;

    public AuctionSystem() {
        items = new ArrayList<>();
        users = new ArrayList<>();
        scanner = new Scanner(System.in);
        currentUser = null;
    }

    public void userLogin() {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        String undercoverName;
        while (true) {
            System.out.print("Enter your undercover name: ");
            undercoverName = scanner.nextLine();
            if (isUndercoverNameUnique(undercoverName)) {
                break;
            } else {
                System.out.println("This undercover name is already taken. Please choose a different name.");
            }
        }
        System.out.print("Enter your age: ");
        int age = scanner.nextInt();
        scanner.nextLine(); // consume newline

        if (age < 21) {
            System.out.println("Sorry, you must be 21 or older to add items or participate in the auction.");
            return;
        }

        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        currentUser = new User(name, undercoverName, age, password);
        users.add(currentUser);
        System.out.println("Welcome, " + currentUser.getUndercoverName() + "!");
    }

    private boolean isUndercoverNameUnique(String undercoverName) {
        for (User user : users) {
            if (user.getUndercoverName().equalsIgnoreCase(undercoverName)) {
                return false;
            }
        }
        return true;
    }

    public void addItem() {
        if (currentUser == null || currentUser.getAge() < 21) {
            System.out.println("You need to log in with a valid age to add items.");
            return;
        }

        System.out.print("Enter item name to add to auction: ");
        String itemName = scanner.nextLine();

        System.out.print("Enter starting price for the item: ");
        double startingPrice = scanner.nextDouble();
        scanner.nextLine(); // consume newline

        items.add(new Item(itemName, startingPrice));
        System.out.println("Item '" + itemName + "' added to the auction with a starting price of $" + startingPrice);
    }

    public void placeBid() {
        if (items.isEmpty()) {
            System.out.println("No items available for bidding.");
            return;
        }

        System.out.print("Enter your undercover name: ");
        String bidderUndercoverName = scanner.nextLine();

        // Display available items
        System.out.println("\nAvailable items for bidding:");
        for (int i = 0; i < items.size(); i++) {
            System.out.println((i + 1) + ". " + items.get(i).getItemName() + " (Starting price: $" + items.get(i).getHighestBid() + ")");
        }

        System.out.print("Enter the item name to bid on: ");
        String itemName = scanner.nextLine();

        Item item = findItem(itemName);
        if (item == null) {
            System.out.println("Item not found.");
            return;
        }

        System.out.print("Enter your bid amount: ");
        double bidAmount = scanner.nextDouble();
        scanner.nextLine(); // consume newline

        item.placeBid(bidderUndercoverName, bidAmount);
    }

    public void showHighestBids() {
        if (items.isEmpty()) {
            System.out.println("No items available.");
            return;
        }

        System.out.println("\n--- Highest Bids ---");
        for (Item item : items) {
            System.out.println("Item: " + item.getItemName() + ", Highest Bid: $" + item.getHighestBid() + " by " + item.getHighestBidderUndercoverName());
        }
    }

    private Item findItem(String itemName) {
        for (Item item : items) {
            if (item.getItemName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        return null;
    }

    private void congratulateWinner() {
        if (items.isEmpty()) {
            System.out.println("No items were bid on. No winner to declare.");
            return;
        }

        String winnerUndercoverName = "No winner";
        double highestBid = 0.0;
        String winningItemName = "No items";

        for (Item item : items) {
            if (item.getHighestBid() > highestBid) {
                highestBid = item.getHighestBid();
                winnerUndercoverName = item.getHighestBidderUndercoverName();
                winningItemName = item.getItemName();
            }
        }

        if (!winnerUndercoverName.equals("No bids yet")) {
            System.out.println("\nCongratulations to " + winnerUndercoverName + " for winning the bid on the item '" + winningItemName + "' with a bid of $" + highestBid + "!");
        } else {
            System.out.println("No valid bids were placed, so no winner to declare.");
        }
    }

    public void startAuction() {
        System.out.println("Welcome to the Auction System!");

        while (true) {
            System.out.println("\n1. User Login");
            System.out.println("2. Add Item");
            System.out.println("3. Place Bid");
            System.out.println("4. Show Highest Bids");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    userLogin();
                    break;
                case 2:
                    addItem();
                    break;
                case 3:
                    placeBid();
                    break;
                case 4:
                    showHighestBids();
                    break;
                case 5:
                    congratulateWinner();
                    System.out.println("Exiting the auction system. Thank you!");
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}

public class OnlineAuction {
    public static void main(String[] args) {
        AuctionSystem auctionSystem = new AuctionSystem();
        auctionSystem.startAuction();
    }
}
