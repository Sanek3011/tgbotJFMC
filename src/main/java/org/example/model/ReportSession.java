package org.example.model;

import lombok.*;

import java.time.LocalDate;
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter

public class ReportSession {
    private ReportType type;
    private String description;
    private String imgUrl;


    public static Report toReport(ReportSession session) {
        return Report.builder()
                .type(session.type)
                .desc(session.description)
                .imageURL(session.imgUrl)
                .build();
    }

    @Override
    public String toString() {
        return "ReportSession{" +
               "description='" + description + '\'' +
               ", type=" + type +
               ", imgUrl='" + imgUrl + '\'' +
               '}';
    }
}
