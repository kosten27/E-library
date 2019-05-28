package com.kostenko.elibrary.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class BookOrder {
    @Id
    @GeneratedValue
    private Long id;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date date;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date deadline;
    @Enumerated(EnumType.ORDINAL)
    private OrderStatus orderStatus;
    @ManyToOne
    private Reader reader;
    @OneToMany(mappedBy = "bookOrder")
    private List<BookOrderLine> bookOrderLines;

    public BookOrder() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }

    public List<BookOrderLine> getBookOrderLines() {
        return bookOrderLines;
    }

    public void setBookOrderLines(List<BookOrderLine> booksOrderLines) {
        this.bookOrderLines = booksOrderLines;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }
}
