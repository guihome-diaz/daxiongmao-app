package eu.daxiongmao.core.utils.label;

import eu.daxiongmao.core.model.db.Label;
import eu.daxiongmao.core.model.enums.AppLang;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

/**
 * To export database labels to Excel file
 * @version 1.0
 * @since 2020/04
 * @author Guillaume Diaz
 */
@Service
@Log4j2
public class LabelExcelWriter {

    private static final String LABEL_SHEET_NAME = "LABELS";

    protected LabelExcelWriter() {
        // package factory
    }

    /**
     * To export database labels into Excel file
     * @param dbLabels database labels
     * @return Corresponding file
     */
    public Optional<Path> exportLabelsToFile(final List<Label> dbLabels) {
        // arg check
        if (dbLabels == null || dbLabels.isEmpty()) {
            log.warn("Cannot export labels, nothing given.");
            return Optional.empty();
        }

        // Create new workbook in 2003+ format
        final Workbook wb = new XSSFWorkbook();
        final Sheet labelSheet = wb.createSheet(LABEL_SHEET_NAME);
        createHeaderRow(wb, labelSheet);

        // Populate file
        int rowNumber = 1;
        for (Label label : dbLabels) {
            Optional<Row> contentRow = createContentRow(label, wb, labelSheet, rowNumber);
            if (contentRow.isPresent()) {
                rowNumber++;
            }
        }

        // Save file
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        try {
            Path excelFile = Files.createTempFile(date.format(formatter), ".xlsx");
            wb.write(Files.newOutputStream(excelFile));
            log.info("Successfully wrote {} labels to file {}", dbLabels.size(), excelFile.toAbsolutePath());
            return Optional.of(excelFile);
        } catch (Exception e) {
            log.warn("Failed to export database labels to Excel file", e);
            return Optional.empty();
        }
    }

    /**
     * To create headers row
     * @param wb Excel workbook
     * @param sheet excel sheet to update
     * @return Headers row
     */
    private Row createHeaderRow(Workbook wb, Sheet sheet) {
        Row headersRow = sheet.createRow(0);
        int columnNumber = 0;
        headersRow.createCell(columnNumber++).setCellValue(wb.getCreationHelper().createRichTextString("CODE"));
        for (AppLang appLang : AppLang.values()) {
            headersRow.createCell(columnNumber++).setCellValue(wb.getCreationHelper().createRichTextString(appLang.getLanguageCode()));
        }
        return headersRow;
    }

    /**
     * To create content row
     * @return content row
     */
    private Optional<Row> createContentRow(Label label, Workbook wb, Sheet sheet, int rowNumber) {
        if (StringUtils.isBlank(label.getCode())) {
            return Optional.empty();
        }
        // Create content row
        Row contentRow = sheet.createRow(rowNumber);
        int columnNumber = 0;
        contentRow.createCell(columnNumber++).setCellValue(wb.getCreationHelper().createRichTextString(label.getCode()));
        for (AppLang appLang : AppLang.values()) {
            String langText = label.getLang(appLang);
            String langContent = StringUtils.isNotBlank(langText) ? langText : "";
            contentRow.createCell(columnNumber++).setCellValue(wb.getCreationHelper().createRichTextString(langContent));
        }
        return Optional.of(contentRow);
    }
}
