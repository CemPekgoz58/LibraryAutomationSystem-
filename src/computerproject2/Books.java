/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package computerproject2;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Cem
 */
@Entity
@Table(name = "books")
@NamedQueries({
    @NamedQuery(name = "Books.findAll", query = "SELECT b FROM Books b"),
    @NamedQuery(name = "Books.findByTitle", query = "SELECT b FROM Books b WHERE b.booksPK.title = :title"),
    @NamedQuery(name = "Books.findByAuthor", query = "SELECT b FROM Books b WHERE b.booksPK.author = :author"),
    @NamedQuery(name = "Books.findByPageCount", query = "SELECT b FROM Books b WHERE b.pageCount = :pageCount"),
    @NamedQuery(name = "Books.findByDateAdded", query = "SELECT b FROM Books b WHERE b.dateAdded = :dateAdded"),
    @NamedQuery(name = "Books.findByIsAvailable", query = "SELECT b FROM Books b WHERE b.isAvailable = :isAvailable"),
    @NamedQuery(name = "Books.findByBorrowedBy", query = "SELECT b FROM Books b WHERE b.borrowedBy = :borrowedBy"),
    @NamedQuery(name = "Books.findByCategory", query = "SELECT b FROM Books b WHERE b.category = :category")})
public class Books implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected BooksPK booksPK;
    @Column(name = "pageCount")
    private Integer pageCount;
    @Column(name = "dateAdded")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateAdded;
    @Column(name = "isAvailable")
    private Boolean isAvailable;
    @Column(name = "borrowedBy")
    private String borrowedBy;
    @Column(name = "category")
    private String category;

    public Books() {
    }

    public Books(BooksPK booksPK) {
        this.booksPK = booksPK;
    }

    public Books(String title, String author) {
        this.booksPK = new BooksPK(title, author);
    }

    public BooksPK getBooksPK() {
        return booksPK;
    }

    public void setBooksPK(BooksPK booksPK) {
        this.booksPK = booksPK;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
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
    public int hashCode() {
        int hash = 0;
        hash += (booksPK != null ? booksPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Books)) {
            return false;
        }
        Books other = (Books) object;
        if ((this.booksPK == null && other.booksPK != null) || (this.booksPK != null && !this.booksPK.equals(other.booksPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "computerproject2.Books[ booksPK=" + booksPK + " ]";
    }
    
}
