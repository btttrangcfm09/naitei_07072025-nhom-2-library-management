package com.group2.library_management.repository.specification;

import com.group2.library_management.entity.BorrowingReceipt;
import com.group2.library_management.entity.User;
import com.group2.library_management.entity.enums.BorrowingStatus;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BorrowingReceiptSpecification {

    /**
     * A builder class to dynamically construct a Specification for BorrowingReceipt.
     * This pattern allows for flexible and readable query building.
     */
    public static class Builder {
        private User user;
        private List<BorrowingStatus> statuses;
        private LocalDate fromDate;
        private LocalDate toDate;

        public Builder withUser(User user) {
            this.user = user;
            return this;
        }

        public Builder withStatuses(List<BorrowingStatus> statuses) {
            this.statuses = statuses;
            return this;
        }

        public Builder withFromDate(LocalDate fromDate) {
            this.fromDate = fromDate;
            return this;
        }

        public Builder withToDate(LocalDate toDate) {
            this.toDate = toDate;
            return this;
        }

        public Specification<BorrowingReceipt> build() {
            return (root, query, criteriaBuilder) -> {
                List<Predicate> predicates = new ArrayList<>();

                query.distinct(true);

                if (user != null) {
                    predicates.add(criteriaBuilder.equal(root.get("user"), user));
                }

                if (statuses != null && !statuses.isEmpty()) {
                    predicates.add(root.get("status").in(statuses));
                }
                
                if (fromDate != null) {
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), fromDate.atStartOfDay()));
                }
                
                if (toDate != null) {
                    // Use lessThan the start of the next day to safely include the entire 'toDate'.
                    // This avoids issues with time components and timezones.
                    predicates.add(criteriaBuilder.lessThan(root.get("createdAt"), toDate.plusDays(1).atStartOfDay()));
                }

                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            };
        }
    }
}
