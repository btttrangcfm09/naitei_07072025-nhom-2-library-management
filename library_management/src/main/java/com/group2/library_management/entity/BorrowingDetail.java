package com.group2.library_management.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "borrowing_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BorrowingDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "borrowing_receipt_id", nullable = false)
    BorrowingReceipt borrowingReceipt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_instance_id", nullable = false)
    BookInstance bookInstance;

    @Column(name = "refund_date")
    LocalDateTime refundDate;

    public LocalDateTime getDueDate() {
        return borrowingReceipt != null ? borrowingReceipt.getDueDate() : null;
    }

    public LocalDateTime getActualReturnDate() {
        return refundDate;
    }

    public void setActualReturnDate(LocalDateTime actualReturnDate) {
        this.refundDate = actualReturnDate;
    }
}
