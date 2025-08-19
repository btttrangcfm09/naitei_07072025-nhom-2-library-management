package com.group2.library_management.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.group2.library_management.entity.enums.BookStatus;

@Entity
@Table(name = "book_instances")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@SQLDelete(sql = "UPDATE book_instances SET delete_at = NOW() WHERE id = ?") // update time when delete
@SQLRestriction("delete_at IS NULL")
public class BookInstance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "edition_id", nullable = false)
    private Edition edition;

    @Column(nullable = false, unique = true, length = 100)
    private String barcode;

    @Column(name = "call_number", nullable = false, length = 100)
    private String callNumber;

    @Column(name = "acquired_date", nullable = false)
    private LocalDate acquiredDate;

    @Column(name = "acquired_price", precision = 10, scale = 2)
    private BigDecimal acquiredPrice;

    @Column(nullable = false)
    private BookStatus status;

    @Column(columnDefinition = "TEXT")
    private String note;

    @Column(name = "delete_at")
    private LocalDateTime deleteAt;

    @OneToMany(mappedBy = "bookInstance")
    private List<BorrowingDetail> borrowingDetails;

    @OneToMany(mappedBy = "bookInstance")
    private List<ReceiptFine> receiptFines;
}
