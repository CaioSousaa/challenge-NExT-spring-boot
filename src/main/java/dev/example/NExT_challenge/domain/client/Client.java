package dev.example.NExT_challenge.domain.client;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.UUID;

@Table(name = "_client")
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @NotNull(message = "The name field cannot be null")
    private String name;

    @NotNull(message = "The age field cannot be null")
    @Min(value = 0, message = "Age must be a positive number")
    private Integer age;

    @NotNull(message = "The dependents field cannot be null")
    @Min(value = 0, message = "Dependents must be a non-negative number")
    private Integer dependents;

    @NotNull(message = "The income field cannot be null")
    private Double income;

    @NotNull(message = "The marital status field cannot be null")
    @Enumerated(EnumType.STRING)
    private MaritalStatus maritalStatus;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    public enum MaritalStatus {
        SINGLE(1, "Single"),
        MARRIED(2, "Married"),
        DIVORCED(3, "Divorced"),
        WIDOWED(4, "Widowed"),
        SEPARATED(5, "Separated"),
        COMMON_LAW(6, "Common Law");

        private final int code;
        private final String description;

        MaritalStatus(int code, String description) {
            this.code = code;
            this.description = description;
        }

        public int getCode() {
            return code;
        }

        public String getDescription() {
            return description;
        }
    }
}
