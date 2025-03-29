package org.example.dto;

import lombok.Builder;
import lombok.Data;
import org.example.model.Report;
import org.example.model.ReportType;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
public class ReportDto {
    private ReportType type;
    private String description;
    private LocalDate date;

    public static List<ReportDto> toDtoList(List<Report> list) {
        List<ReportDto> result = new ArrayList<>();
        for (Report report : list) {
            ReportDto dto = toDto(report);
            result.add(dto);
        }
        return result;
     }
     public static ReportDto toDto(Report report) {
         ReportDto build = ReportDto.builder()
                 .description(report.getDesc())
                 .type(report.getType())
                 .date(report.getDateOfCreation())
                 .build();
         return build;
     }

    @Override
    public String toString() {
        String dateStr = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        String format = String.format("Дата: %s,\nтип отчета: %s,\nописание %s\n",
                dateStr, type, description);
        return format;
    }
}
