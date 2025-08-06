package computerproject2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public class Transaction {
    private Member member;
    private Book book;
    private String transactionType; // "borrow" or "return"
    private Date transactionDate;

    public Transaction(Member member, Book book, String transactionType) {
        this.member = member;
        this.book = book;
        this.transactionType = transactionType;
        this.transactionDate = new Date();
    }

    public void displayTransaction() {
        System.out.println("Transaction: " + transactionType + " | Book: " + book.getTitle()
                + " | Member: " + member.getEmail() + " | Date: " + transactionDate);
    }

    //Save transactions to database
    public void saveTransaction() {
        String url = "jdbc:mysql://localhost:3306/library";
        String user = "root";
        String password = ""; 

        String sql = "INSERT INTO transactions (member_email, book_title, transaction_type, transaction_date) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, member.getEmail());
            stmt.setString(2, book.getTitle());
            stmt.setString(3, transactionType);
            stmt.setTimestamp(4, new java.sql.Timestamp(transactionDate.getTime()));

            stmt.executeUpdate();
            System.out.println("Transaction saved to database.");
        } catch (SQLException e) {
            System.err.println("Database error while saving transaction: " + e.getMessage());
        }
    }

    // === Getter and Setter's ===
    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }
}
