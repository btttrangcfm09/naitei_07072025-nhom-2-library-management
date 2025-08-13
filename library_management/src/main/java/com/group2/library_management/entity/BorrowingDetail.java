package com.group2.library_management.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

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
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "borrowing_receipt_id", nullable = false)
    private BorrowingReceipt borrowingReceipt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_instance_id", nullable = false)
    private BookInstance bookInstance;

    @Column(name = "refund_date")
    private LocalDateTime refundDate;
}
