package eu.daxiongmao.core.utils.label;


import eu.daxiongmao.core.model.db.Label;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Set;

/**
 * Excel file parser test
 * @author Guillaume Diaz
 * @version 1.0
 * @since 2020/04
 */
@Log4j2
public class LabelExcelReaderTest {

    private final LabelExcelReader excelUtils = new LabelExcelReader();

    @Test
    public void parseExcelFileManyTabs() {
        final Path excelFile = Paths.get("src","test","resources", "labels", "labels_with_many_tabs.xlsx");
        parseAndCheckExcelTestFile(excelFile);
    }

    @Test
    public void parseExcelFileManyTabs_alt() {
        final Path excelFile = Paths.get("src","test","resources", "labels", "labels_with_many_tabs_alt.xlsx");
        parseAndCheckExcelTestFile(excelFile);
    }

    @Test
    public void parseExcelFileManyTabs_alt2() {
        final Path excelFile = Paths.get("src","test","resources", "labels", "labels_with_many_tabs_alt2.xlsx");
        parseAndCheckExcelTestFile(excelFile);
    }

    @Test
    public void parseExcelFileSingleTab() {
        final Path excelFile = Paths.get("src","test","resources", "labels", "labels_single_tab.xlsx");
        parseAndCheckExcelTestFile(excelFile);
    }

    @Test
    public void parseExcelFileSingleTab2() {
        final Path excelFile = Paths.get("src","test","resources", "labels", "labels_single_tab2.xlsx");
        parseAndCheckExcelTestFile(excelFile);
    }

    @Test
    public void parseExcelFileWithMissingColumnsAndRows() {
        final Path excelFile = Paths.get("src","test","resources", "labels", "labels_with_missing_columns_and_rows.xlsx");
        parseAndCheckExcelTestFile(excelFile);
    }

    @Test
    public void parseMalformedFile() {
        final Path excelFile = Paths.get("src","test","resources", "labels", "malformed_file.xlsx");
        final Optional<Set<Label>> labels = excelUtils.importLabelsFromFile(excelFile);
        Assertions.assertNotNull(labels);
        Assertions.assertTrue(labels.isPresent());
        Assertions.assertEquals(0, labels.get().size());
    }

    @Test
    public void parseUnknownFiles() {
        Optional<Set<Label>> labels = excelUtils.importLabelsFromFile(null);
        Assertions.assertNotNull(labels);
        Assertions.assertTrue(labels.isEmpty());

        final Path excelFile = Paths.get("src","moon","resources", "good_labels.xlsx");
        labels = excelUtils.importLabelsFromFile(excelFile);
        Assertions.assertNotNull(labels);
        Assertions.assertTrue(labels.isEmpty());
    }

    /**
     * To parse Excel unit test file and perform common set of assertions
     * @param excelFile excel file to check
     */
    private void parseAndCheckExcelTestFile(final Path excelFile) {
        final Optional<Set<Label>> labels = excelUtils.importLabelsFromFile(excelFile);
        Assertions.assertNotNull(labels);
        Assertions.assertTrue(labels.isPresent());
        Assertions.assertEquals(26, labels.get().size());

        // Check languages
        boolean containsHttp405 = false;
        for (Label lbl : labels.get()) {
            Assertions.assertTrue(StringUtils.isNotBlank(lbl.getFrench()));
            Assertions.assertTrue(StringUtils.isNotBlank(lbl.getEnglish()));
            if ("APP.WELCOME".equals(lbl.getCode())) {
                Assertions.assertTrue(StringUtils.isBlank(lbl.getChinese()));
            } else {
                Assertions.assertTrue(StringUtils.isNotBlank(lbl.getChinese()));
            }
            if ("HTTP_405".equals(lbl.getCode())) {
                containsHttp405 = true;
                // Content assertion
                Assertions.assertEquals("405 Method Not Allowed. A request method is not supported for the requested resource; for example, a GET request on a form that requires data to be presented via POST, or a PUT request on a read-only resource.", lbl.getEnglish());
                Assertions.assertEquals("405 Méthode non autorisée. Une méthode de demande n'est pas prise en charge pour la ressource demandée ; par exemple, une demande GET sur un formulaire qui nécessite que les données soient présentées par POST, ou une demande PUT sur une ressource en lecture seule.", lbl.getFrench());
                Assertions.assertEquals("405 方法不允许。不支持所请求的资源的请求方法；例如，在要求通过POST提交数据的表单上提出GET请求，或者在只读资源上提出PUT请求。", lbl.getChinese());
            }
        }
        // Check for a specific label
        Assertions.assertTrue(containsHttp405);
    }

}
