package dev.example.NExT_challenge.domain.insurance;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.example.NExT_challenge.domain.client.Client;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Date;

@Table(name = "_insurance")
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Insurance {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Type type;

    private int risk;

    @Enumerated(EnumType.STRING)
    private Analysis analysis;

    private String observation;

    @CreationTimestamp
    private LocalDateTime created_at;

    private Date validate_at;

    @ManyToOne
    @JoinColumn(name = "client_id")
    @JsonIgnore
    private Client client;

    public enum Type {
        LIFE(1, "Life"),
        DISABILITY(2, "Disability"),
        HOME(3, "Home"),
        AUTO(4, "Auto");

        private final int code;
        private final String description;

        Type(int code, String description) {
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

    public enum Analysis {
        ECONOMIC(1, "Economic"),
        REGULAR(2, "Regular"),
        RESPONSIBLE(3, "Responsible");


        private final int code;
        private final String description;

        Analysis(int code, String description) {
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
