package com.group2.library_management.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

import com.group2.library_management.entity.enums.BorrowingStatus;

@Entity
@Table(name = "borrowing_receipts")
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BorrowingReceipt extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "borrowed_date", nullable = false)
    private LocalDateTime borrowedDate;

    @Column(name = "due_date", nullable = false)
    private LocalDateTime dueDate;

    @Column(nullable = false)
    private Integer status;

    @Column(name = "rejected_reason", columnDefinition = "TEXT")
    private String rejectedReason;

    @OneToMany(mappedBy = "borrowingReceipt")
    private List<BorrowingDetail> borrowingDetails;

    @OneToMany(mappedBy = "borrowingReceipt")
    private List<ReceiptFine> receiptFines;

    public BorrowingStatus getStatusEnum() {
        return BorrowingStatus.fromValue(this.status);
    }

    public void setStatus(BorrowingStatus statusEnum) {
        if (statusEnum != null) {
            this.status = statusEnum.getValue();
        }
    }
}
