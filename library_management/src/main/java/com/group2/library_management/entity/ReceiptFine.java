package com.group2.library_management.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Entity
@Table(name = "receipt_fines")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReceiptFine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "borrowing_receipt_id", nullable = false)
    private BorrowingReceipt borrowingReceipt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_instance_id", nullable = false)
    private BookInstance bookInstance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fine_id", nullable = false)
    private Fine fine;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "fine_note", columnDefinition = "TEXT")
    private String fineNote;
}
