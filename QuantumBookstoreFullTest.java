import java.util.*;

abstract class Book {
    protected String isbn;
    protected String title;
    protected int year;
    protected double price;
    protected String author;

    public Book(String isbn, String title, int year, double price, String author) {
        this.isbn = isbn;
        this.title = title;
        this.year = year;
        this.price = price;
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getYear() {
        return year;
    }

    public String getTitle() {
        return title;
    }

    public abstract boolean isPurchasable();

    public abstract double buy(int quantity, String email, String address);
}

class PaperBook extends Book {
    private int stock;
    private double weight;

    public PaperBook(String isbn, String title, int year, double price, String author, int stock, double weight) {
        super(isbn, title, year, price, author);
        this.stock = stock;
        this.weight = weight;
    }

    @Override
    public boolean isPurchasable() {
        return true;
    }

    @Override
    public double buy(int quantity, String email, String address) {
        if (quantity > stock) {
            throw new RuntimeException("Quantum book store: Not enough stock for " + title);
        }
        stock -= quantity;
        ShippingService.ship(title, quantity, address);
        return price * quantity;
    }
}

class EBook extends Book {
    private String fileType;

    public EBook(String isbn, String title, int year, double price, String author, String fileType) {
        super(isbn, title, year, price, author);
        this.fileType = fileType;
    }

    @Override
    public boolean isPurchasable() {
        return true;
    }

    @Override
    public double buy(int quantity, String email, String address) {
        if (quantity != 1) {
            throw new RuntimeException("Quantum book store: EBook can only be bought once per transaction");
        }
        MailService.send(title, email);
        return price;
    }
}

class ShowcaseBook extends Book {
    public ShowcaseBook(String isbn, String title, int year, String author) {
        super(isbn, title, year, 0.0, author);
    }

    @Override
    public boolean isPurchasable() {
        return false;
    }

    @Override
    public double buy(int quantity, String email, String address) {
        throw new RuntimeException("Quantum book store: Showcase books are not for sale");
    }
}

class ShippingService {
    public static void ship(String title, int quantity, String address) {
        System.out.println("Quantum book store: Shipping " + quantity + "x " + title + " to " + address);
    }
}

class MailService {
    public static void send(String title, String email) {
        System.out.println("Quantum book store: Sending " + title + " to email " + email);
    }
}

class Bookstore {
    private Map<String, Book> inventory = new HashMap<>();

    public void addBook(Book book) {
        inventory.put(book.getIsbn(), book);
        System.out.println("Quantum book store: Added book - " + book.getTitle());
    }

    public List<Book> removeOutdatedBooks(int maxAge) {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        List<Book> removed = new ArrayList<>();

        Iterator<Map.Entry<String, Book>> iterator = inventory.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Book> entry = iterator.next();
            if (currentYear - entry.getValue().getYear() > maxAge) {
                removed.add(entry.getValue());
                System.out.println("Quantum book store: Removed outdated book - " + entry.getValue().getTitle());
                iterator.remove();
            }
        }
        return removed;
    }

    public double buyBook(String isbn, int quantity, String email, String address) {
        if (!inventory.containsKey(isbn)) {
            throw new RuntimeException("Quantum book store: Book not found");
        }

        Book book = inventory.get(isbn);

        if (!book.isPurchasable()) {
            throw new RuntimeException("Quantum book store: Book is not purchasable");
        }

        double amount = book.buy(quantity, email, address);
        System.out.println("Quantum book store: Bought " + quantity + "x " + book.getTitle() + " for $" + amount);
        return amount;
    }
}

public class QuantumBookstoreFullTest {
    public static void main(String[] args) {
        Bookstore store = new Bookstore();

        Book book1 = new PaperBook("ISBN001", "Clean Code", 2010, 300.0, "Robert C. Martin", 5, 1.2);
        Book book2 = new EBook("ISBN002", "Java 8 in Action", 2015, 200.0, "Raoul-Gabriel Urma", "pdf");
        Book book3 = new ShowcaseBook("ISBN003", "The Art of Debugging", 2005, "John Doe");

        store.addBook(book1);
        store.addBook(book2);
        store.addBook(book3);

        // Remove outdated books
        store.removeOutdatedBooks(10);

        // Buy available books
        try {
            store.buyBook("ISBN001", 2, "user@example.com", "123 Main St");
            store.buyBook("ISBN002", 1, "ebookuser@example.com", "");
            store.buyBook("ISBN003", 1, "test@example.com", "no address");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
