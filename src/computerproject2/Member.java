package computerproject2;

import java.util.ArrayList;

public class Member {

    private String email, password;
    private static Member currentMember;
    private ArrayList<Book> borrowedBooks;

    public Member(String email, String password) {
        this.email = email;
        this.borrowedBooks = new ArrayList<>();
        this.password = password;
    }
    //add book 
    public void addBook(Book book) {
        borrowedBooks.add(book);
    }

    //make email accessible
    public String getEmail() {
        return email;
    }
    //make password accessible
    public String getPassword() {
        return password;
    }
    //make current member accessible
    public static Member getCurrentMember() {
        return currentMember;
    }
    //set the current member
    public static void setCurrentMember(Member member) {
        currentMember = member;
    }
    //booklist for the borrow or return transactions
    public ArrayList<Book> getBorrowedBooks() {
        return borrowedBooks;
    }
    //remove the book from borrowed book list
    public void removeBook(Book book) {
        this.borrowedBooks.remove(book); 
    }

}
