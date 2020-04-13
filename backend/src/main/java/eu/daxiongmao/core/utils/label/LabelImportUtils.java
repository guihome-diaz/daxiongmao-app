package eu.daxiongmao.core.utils.label;

import eu.daxiongmao.core.model.db.Label;
import eu.daxiongmao.core.model.enums.AppLang;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * To import a label file into the application database
 * @version 1.0
 * @since 2020/04
 * @author Guillaume Diaz
 */
@Service
@Log4j2
public class LabelImportUtils {

    private static final String LABEL_SHEET_NAME = "LABELS";

    /**
     * To extract labels from Excel file
     * @param excelFile path of the excel file to process
     * @return corresponding labels or EMPTY
     */
    public Optional<List<Label>> extractLabels(final Path excelFile) {
        // arg check
        if (excelFile == null) {
            log.warn("Cannot import labels, no file given.");
            return Optional.empty();
        }
        if (Files.notExists(excelFile)) {
            log.warn("Cannot import labels, requested file {} does not exist.");
            return Optional.empty();
        }

        // Open workbook
        try (final Workbook wb = WorkbookFactory.create(excelFile.toFile())) {
            // Get sheet
            final Sheet labelsSheet = getLabelsSheet(wb);
            // Get labels
            final List<Label> labels = parseLabelsSheet(labelsSheet);
            log.info("Successfully parsed file '{}', {} labels extracted", excelFile, labels.size());
            return Optional.of(labels);
        } catch (Exception e) {
            log.warn("Failed to process label file '{}'", excelFile, e);
            return Optional.empty();
        }
    }

    /**
     * To retrieve the "LABELS" sheet. If not found => fallback to 1st sheet
     * @param wb excel workbook to analyse
     * @return "LABELS" sheet or 1st sheet as fallback
     */
    private Sheet getLabelsSheet(final Workbook wb) {
        Sheet labelsSheet = wb.getSheetAt(0);
        if (wb.getNumberOfSheets() > 1) {
            for (Sheet wbSheet : wb) {
                if (wbSheet.getSheetName().toUpperCase().equals(LABEL_SHEET_NAME)) {
                    labelsSheet = wbSheet;
                    break;
                }
            }
        }
        return labelsSheet;
    }

    /**
     * To extract Excel sheet's labels
     * @param labelsSheet excel sheet to parse
     * @return corresponding labels
     */
    private List<Label> parseLabelsSheet(final Sheet labelsSheet) {
        final List<Label> labels = new ArrayList<>();
        Map<Integer, AppLang> columnsLang = new ConcurrentHashMap<>();
        for (final Row row : labelsSheet) {
            if (row.getRowNum() == 0) {
                // Headers
                columnsLang = parseHeadersRow(row);
            } else {
                // Content
                labels.add(parseLabelRow(row, columnsLang));
            }
        }
        return labels;
    }

    /**
     * To parse HEADERS row and find columns for each language.
     * @return Dictionary of (column number, language)
     * <ul>
     *     <li>key: column number (ex: 2)</li>
     *     <li>Value: language in the corresponding column (ex: FR)</li>
     * </ul>
     */
    private Map<Integer, AppLang> parseHeadersRow(final Row headerRow) {
        final Map<Integer, AppLang> columnLang = new ConcurrentHashMap<>();

        for (final Cell cell : headerRow) {
            if (cell.getColumnIndex() > 0) {
                final String cellContent = cell.getRichStringCellValue().getString();
                final Optional<AppLang> cellLangTitle = AppLang.getLanguageForCode(cellContent);
                cellLangTitle.ifPresent((lang) -> columnLang.put(cell.getColumnIndex(), lang));
            }
        }

        return columnLang;
    }


    /**
     * To parse a LABEL row that contains:
     * <ul>
     *     <li>LABEL CODE, index 0</li>
     *     <li>Translations in each column. (i) The corresponding language tag depends on the column title</li>
     * </ul>
     * Please note that all translations are not mandatory.
     * If a translation for a given language is missing application will fallback to default lang or label code.
     * @param sheetRow excel row to parse
     * @param columnsLang dictionary of (columns, lang)
     * @return corresponding label.
     */
    private Label parseLabelRow(final Row sheetRow, final Map<Integer, AppLang> columnsLang) {
        final Label label = new Label();
        for (final Cell cell : sheetRow) {
            if (cell.getColumnIndex() == 0) {
                label.setCode(cell.getRichStringCellValue().getString());
            } else {
                label.setLang(columnsLang.get(cell.getColumnIndex()), cell.getRichStringCellValue().getString());
            }
        }
        return label;
    }



}
