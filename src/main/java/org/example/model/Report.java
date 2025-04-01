package org.example.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Data
@AllArgsConstructor
@Builder
@ToString(exclude = "user")
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "reports", schema = "bolka")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Enumerated(EnumType.STRING)
    ReportType type;
    @Column(name = "description")
    String desc;
    @Column(name = "url")
    String imageURL;
    @Column(name = "data")
    LocalDate dateOfCreation;
    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;


    @Override
    public String toString() {
        String dateStr = dateOfCreation.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        return String.format("ID отчета:\n %d Автор отчета: %s\nТип отчета: %s\nОписание: %s\nСкрин: %s\nДата загрузки: %s\n-------------------\n",
                getId(), user.getName(), getType(), getDesc(), getImageURL(), getDateOfCreation());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Report report = (Report) o;
        return Objects.equals(id, report.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
