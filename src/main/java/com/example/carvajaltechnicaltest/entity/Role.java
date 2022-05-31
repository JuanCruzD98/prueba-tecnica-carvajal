package com.example.carvajaltechnicaltest.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class Role {
    private static final long serialVersionUID = -6460463158912279419L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String name;
    @Column(name = "create_at")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate createAt;

}
