/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package computerproject2;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author Cem
 */
public class Book {
    
    private String title;
    private String author;
    private int pageCount; 
    LocalDateTime dateAdded;  // Changed to LocalDateTime
    private boolean isAvailable; // Whether the book is available for borrowing
    private String borrowedBy = "";
    private String category;
    DBConnection db = new DBConnection();

    public static ArrayList<Book> books = new ArrayList<>();
       public static void initializeBooks(DBConnection db) {
            Book book;

            book = new Book("İnce Memed", "Yaşar Kemal", 576, "Literature");
            books.add(book);
            db.addBook(book);

            book = new Book("Kürk Mantolu Madonna", "Sabahattin Ali", 224, "Literature");
            books.add(book);
            db.addBook(book);

            book = new Book("Tutunamayanlar", "Oğuz Atay", 704, "Literature");
            books.add(book);
            db.addBook(book);

            book = new Book("Aşk", "Elif Şafak", 477, "Literature");
            books.add(book);
            db.addBook(book);

            book = new Book("Saatleri Ayarlama Enstitüsü", "Ahmet Hamdi Tanpınar", 352, "Literature");
            books.add(book);
            db.addBook(book);

            book = new Book("Beyaz Kale", "Orhan Pamuk", 252, "Literature");
            books.add(book);
            db.addBook(book);

            book = new Book("Kırmızı Pazartesi", "Gabriel Garcia Marquez", 221, "Literature");
            books.add(book);
            db.addBook(book);

            book = new Book("Çalıkuşu", "Reşat Nuri Güntekin", 526, "Literature");
            books.add(book);
            db.addBook(book);

            book = new Book("Yalnızız", "Peyami Safa", 351, "Literature");
            books.add(book);
            db.addBook(book);

            book = new Book("Vurun Kahpeye", "Halide Edib Adıvar", 374, "Literature");
            books.add(book);
            db.addBook(book);

            // Felsefe kitapları
            book = new Book("Felsefe Tarihi", "İsmail Tunalı", 464, "Philosophy");
            books.add(book);
            db.addBook(book);

            book = new Book("Felsefenin Temelleri", "Murat Belge", 216, "Philosophy");
            books.add(book);
            db.addBook(book);

            book = new Book("Büyük Felsefe Kitabı", "Metin Lütfi Baydar", 128, "Philosophy");
            books.add(book);
            db.addBook(book);

            // Bilim kitapları
            book = new Book("Matematiksel Düşünme", "İsmail E. Erdem", 312, "Science");
            books.add(book);
            db.addBook(book);

            book = new Book("Türk Dilinin Etimolojisi", "Süleyman Güngör", 412, "Science");
            books.add(book);
            db.addBook(book);

            book = new Book("Biyoloji", "Murat Demirtaş", 500, "Science");
            books.add(book);
            db.addBook(book);

            // Tarih kitapları
            book = new Book("Osmanlı Tarihi", "Halil İnalcık", 850, "History");
            books.add(book);
            db.addBook(book);

            book = new Book("Cumhuriyet Tarihi", "Feroz Ahmad", 789, "History");
            books.add(book);
            db.addBook(book);

            book = new Book("Türklerin Tarihi", "Köksal Yılmaz", 396, "History");
            books.add(book);
            db.addBook(book);
        }
    
        
            public static void addBook(String title, String author, int pageCount, String category) {
        books.add(new Book(title, author, pageCount, category));
    }

    public Book(String title, String author,int pagecount,String category) {
        this.title = title;
        this.author = author;
        this.pageCount = pagecount;
        this.isAvailable = true; // Books are available when created
        this.dateAdded = LocalDateTime.now();
        this.category= category;

    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getPageCount() {
        return pageCount;
    }

    public boolean isAvailable() {
        return isAvailable;
    }
       public LocalDateTime getDateAdded() {
        return dateAdded;
    }

    public void borrowBook() {
        if (isAvailable) {
            isAvailable = false; // Mark the book as borrowed
        } else {
            System.out.println("Book is already borrowed.");
        }
    }

    public void returnBook() {
        isAvailable = true; // Mark the book as available
    }

    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }
    public String getBorrowedBy() {
    return borrowedBy;
}

public void setBorrowedBy(String borrowedBy) {
    this.borrowedBy = borrowedBy;
}

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    @Override
    public String toString() {
        return title + " by " + author + " (Page Count: " + pageCount + ") - Category: " + category;
    }
}


