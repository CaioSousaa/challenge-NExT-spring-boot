package dev.example.NExT_challenge.domain.house;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.example.NExT_challenge.domain.client.Client;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Table(name = "_house")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class House {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "the zipcode field needs to be filled in")
    @Length(max = 8, min = 8)
    private String zipcode;

    @NotBlank(message = "the location field needs to be filled in")
    private String location;

    @NotNull(message = "The owner field cannot be null")
    @Enumerated(EnumType.STRING)
    private OwnerShipStatus ownership;

    @ManyToOne
    @JoinColumn(name = "client_id")
    @JsonIgnore
    private Client client;

    public enum OwnerShipStatus {
        OWNED(1, "Owned"),
        MORTGAGED(2, "Mortgaged");

        private final int code;
        private final String description;

        OwnerShipStatus(int code, String description) {
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
