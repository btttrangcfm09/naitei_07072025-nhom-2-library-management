package com.group2.library_management.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

import com.group2.library_management.entity.enums.FineType;

@Entity
@Table(name = "fines")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Fine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer type;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal fee;

    @OneToMany(mappedBy = "fine")
    private List<ReceiptFine> receiptFines;

    public void setType(FineType fineTypeEnum) {
        if (fineTypeEnum != null) {
            this.type = fineTypeEnum.getValue();
        }
    }

    public FineType getTypeEnum() {
        return FineType.fromValue(this.type);
    }
}
