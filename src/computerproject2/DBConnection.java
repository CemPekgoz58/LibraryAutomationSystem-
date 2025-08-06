package computerproject2;

import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DBConnection {

    private static final String url = "jdbc:mysql://localhost:3306/library_db";
    private static final String user = "root";
    private static final String password = "Elmascem2004";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    private static final String FILE_NAME = "books.txt";

    public void addBook(Book book) {
        // Add new book in file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            String line = book.getTitle() + ";" + book.getAuthor() + ";" + book.getPageCount() + ";" + book.getCategory() + ";" + book.isAvailable();
            bw.write(line);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Read books from database
    public ArrayList<Book> readBooksFromDatabase() {
        ArrayList<Book> booksFromDB = new ArrayList<>();
        String sql = "SELECT title, author, pageCount, category, isAvailable, borrowedBy FROM books";

        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String title = rs.getString("title");
                String author = rs.getString("author");
                int pageCount = rs.getInt("pageCount");
                String category = rs.getString("category");
                boolean isAvailable = rs.getBoolean("isAvailable");
                String borrowedBy = rs.getString("borrowedBy");

                Book book = new Book(title, author, pageCount, category);
                book.setAvailable(isAvailable);
                book.setBorrowedBy(borrowedBy != null ? borrowedBy : "");
                booksFromDB.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return booksFromDB;
    }

    //Add transactions to txt file
    public void addTransaction(Transaction transaction) {
        String sql = "INSERT INTO transactions (member_email, book_title, transaction_type, transaction_date) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, transaction.getMember().getEmail());
            ps.setString(2, transaction.getBook().getTitle());
            ps.setString(3, transaction.getTransactionType());  // 
            ps.setTimestamp(4, new Timestamp(transaction.getTransactionDate().getTime()));
            ps.executeUpdate();

            try (FileWriter fw = new FileWriter("transactions.txt", true); BufferedWriter bw = new BufferedWriter(fw); PrintWriter out = new PrintWriter(bw)) {
                out.println(transaction.getMember().getEmail() + ","
                        + transaction.getBook().getTitle() + ","
                        + transaction.getBook().getAuthor() + ","
                        + transaction.getTransactionType() + ","
                        + transaction.getTransactionDate());
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Add member to database
    public void addMember(Member member) {
        String sql = "INSERT INTO members (email, password) VALUES (?, ?)";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, member.getEmail());
            ps.setString(2, member.getPassword());
            ps.executeUpdate();

            try (FileWriter fw = new FileWriter("members.txt", true); BufferedWriter bw = new BufferedWriter(fw); PrintWriter out = new PrintWriter(bw)) {
                out.println(member.getEmail() + "," + member.getPassword());
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Member> getAllMembers() {
        List<Member> members = new ArrayList<>();
        String sql = "SELECT * FROM members";
        try (Connection conn = getConnection(); Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                String email = rs.getString("email");
                String password = rs.getString("password");
                members.add(new Member(email, password));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return members;
    }

    public List<Transaction> getAllTransactions() {
        List<Transaction> list = new ArrayList<>();
        String sql = "SELECT * FROM transactions";
        try (Connection conn = getConnection(); Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                String bookTitle = rs.getString("book_title");
                String memberEmail = rs.getString("member_email");
                String transactionType = rs.getString("transaction_type");
                Timestamp transactionDate = rs.getTimestamp("transaction_date");

                Book book = new Book(bookTitle, "", 0, "Unknown");
                Member member = new Member(memberEmail, "");

                Transaction t = new Transaction(member, book, transactionType);
                t.setTransactionDate(transactionDate);

                list.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Transaction> loadTransactionsFromFile(String fileName) {
        List<Transaction> transactions = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", 5);

                if (parts.length >= 5) {
                    String memberEmail = parts[0].trim();
                    String bookTitle = parts[1].trim();
                    String bookAuthor = parts[2].trim();
                    String transactionType = parts[3].trim();
                    String transactionDateStr = parts[4].trim();

                    Date transactionDate = new Date();

                    Member member = new Member(memberEmail.isEmpty() ? "unknown@example.com" : memberEmail, "");
                    Book book = new Book(bookTitle, bookAuthor, 0, "Unknown");

                    Transaction transaction = new Transaction(member, book, transactionType);
                    transaction.setTransactionDate(transactionDate);

                    transactions.add(transaction);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return transactions;
    }

    public void writeBooksToFile() {
        EntityManagerFactory emf = null;
        EntityManager em = null;

        try {
            emf = Persistence.createEntityManagerFactory("ComputerProject2PU");
            em = emf.createEntityManager();

            List<Books> bookList = em.createNamedQuery("Books.findAll", Books.class).getResultList();
            em.getTransaction().begin();
            try (BufferedWriter bw = new BufferedWriter(new FileWriter("books.txt"))) {
                bw.write(String.format("%-25s %-20s %-7s %-15s %-10s %-30s %-15s%n",
                        "Title", "Author", "Pages", "Date Added", "Availability", "Borrowed By (Email)", "Category"));

                for (Books b : bookList) {
                    String dateAddedStr = "-";
                    if (b.getDateAdded() != null) {
                        dateAddedStr = b.getDateAdded().toString();
                    }

                    String availabilityStr = (Boolean.TRUE.equals(b.getIsAvailable())) ? "Available" : "Borrowed";

                    String borrowedByStr = (b.getBorrowedBy() != null && !b.getBorrowedBy().isEmpty()) ? b.getBorrowedBy() : "-";
                    String categoryStr = (b.getCategory() != null && !b.getCategory().isEmpty()) ? b.getCategory() : "-";

                    bw.write(String.format("%-25s %-20s %-7d %-15s %-10s %-30s %-15s%n",
                            b.getBooksPK().getTitle(),
                            b.getBooksPK().getAuthor(),
                            b.getPageCount(),
                            dateAddedStr,
                            availabilityStr,
                            borrowedByStr,
                            categoryStr));
                    em.persist(b);
                }
                em.getTransaction().commit();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
            if (emf != null) {
                emf.close();
            }
        }
    }


    public void updateBookInDatabase(Book book) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();

            // get the book from database
            String selectSql = "SELECT * FROM books WHERE title = ?";
            pstmt = conn.prepareStatement(selectSql);
            pstmt.setString(1, book.getTitle());
            rs = pstmt.executeQuery();

            if (rs.next()) {
                // find book then update database
                rs.close();
                pstmt.close();

                String updateSql = "UPDATE books SET author=?, pageCount=?, category=?, isAvailable=?, borrowedBy=? WHERE title=?";
                pstmt = conn.prepareStatement(updateSql);
                pstmt.setString(1, book.getAuthor());
                pstmt.setInt(2, book.getPageCount());
                pstmt.setString(3, book.getCategory());
                pstmt.setBoolean(4, book.isAvailable());
                pstmt.setString(5, book.getBorrowedBy());
                pstmt.setString(6, book.getTitle());

                int affectedRows = pstmt.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("Update failed, no rows affected.");
                }
            } else {
                System.out.println("Book not found in database: " + book.getTitle());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        rs.close();
        pstmt.close();
        conn.close();
    }
    public boolean isEmailExistsInDB(String email) {
    boolean exists = false;
    try {
        Connection con = getConnection();
        String sql = "SELECT COUNT(*) FROM members WHERE email = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            int count = rs.getInt(1);
            exists = (count > 0);
        }
        rs.close();
        ps.close();
        con.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return exists;
}

}
