package com.kostenko.elibrary.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class BookOrder  extends BaseEntity {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date date;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date deadline;
    @Enumerated(EnumType.ORDINAL)
    private OrderStatus orderStatus;
    @ManyToOne
    @NotNull
    private Reader reader;
    @OneToMany(mappedBy = "bookOrder", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BookOrderLine> bookOrderLines;

    public BookOrder() {
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

    public void addLine(BookOrderLine bookOrderLine) {
        bookOrderLines.add(bookOrderLine);
    }

    public int getNumberOfLines() {
        return bookOrderLines.size();
    }

    public List<Series> getSeries() {
        return bookOrderLines.stream()
                .map(BookOrderLine::getSeries)
                .collect(Collectors.toList());
    }
}
