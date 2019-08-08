package build.dream.iot;

import build.dream.iot.domains.Land;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.kafka.annotation.EnableKafka;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ServletComponentScan
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableDiscoveryClient
@EnableKafka
@MapperScan(basePackages = {"build.dream.common.mappers", "build.dream.iot.mappers"})
public class Application {
    public static void main(String[] args) throws IOException, InvalidFormatException {
//        SpringApplication.run(Application.class, args);

        args = new String[1];
        args[0] = "C:\\Users\\liuyandong\\Desktop\\沈阳土地.xlsx";
        aa(args);
    }

    public static void aa(String[] args) throws IOException, InvalidFormatException {
        String filePath = args[0];
        FileInputStream fileInputStream = new FileInputStream(filePath);
        Workbook workbook = WorkbookFactory.create(fileInputStream);

        Sheet sheet = workbook.getSheetAt(0);
        int lastRowNum = sheet.getLastRowNum();
        List<Land> lands = new ArrayList<Land>();
        for (int index = 1; index <= lastRowNum; index++) {
            Row row = sheet.getRow(index);
            Land land = new Land();
            land.setCity(obtainCellValue(row.getCell(0)));
            land.setCounty(obtainCellValue(row.getCell(1)));
            land.setLandNumber(obtainCellValue(row.getCell(2)));
            land.setLandArea(BigDecimal.valueOf(Double.valueOf(obtainCellValue(row.getCell(3)))));
            land.setLandLocation(obtainCellValue(row.getCell(4)));
            land.setPurpose(obtainCellValue(row.getCell(5)));

            String transferMode = obtainCellValue(row.getCell(6));
            land.setTransferMode(StringUtils.isBlank(transferMode) ? "" : transferMode);

            String transferPrice = obtainCellValue(row.getCell(7));
            if (StringUtils.isNumeric(transferPrice)) {
                land.setTransferPrice(BigDecimal.valueOf(Double.valueOf(transferPrice)));
            } else {
                land.setTransferPrice(BigDecimal.ZERO);
            }

            land.setStartYear(obtainCellValue(row.getCell(8)).substring(0, 4));
            land.setEndYear(obtainCellValue(row.getCell(9)).substring(0, 4));
            land.setDealYear(obtainCellValue(row.getCell(10)).substring(0, 4));

            land.setDealPrice(BigDecimal.valueOf(Double.valueOf(obtainCellValue(row.getCell(11)))));
            land.setDealer(obtainCellValue(row.getCell(12)));

            String premiumRate = obtainCellValue(row.getCell(13));
            if (StringUtils.isNumeric(premiumRate)) {
                land.setPremiumRate(BigDecimal.valueOf(Double.valueOf(premiumRate)));
            } else {
                land.setPremiumRate(BigDecimal.ZERO);
            }
            land.setVolumeRatio(obtainCellValue(row.getCell(14)));
            lands.add(land);
        }

        Map<String, List<Land>> landMap = lands.stream().collect(Collectors.groupingBy(Land::getDealYear));

        Sheet outputSheet = workbook.createSheet("成果输出" + RandomStringUtils.randomAlphanumeric(3));
        int rowNum = 0;
        for (Map.Entry<String, List<Land>> entry : landMap.entrySet()) {
            String year = entry.getKey();
            List<Land> landList = entry.getValue();
            Map<String, List<Land>> listMap = landList.stream().collect(Collectors.groupingBy(Land::getCounty));

            Row row = outputSheet.createRow(rowNum);
            row.createCell(0).setCellValue(year + "年供地");
            row.createCell(1).setCellValue("面积（平方米）");
            row.createCell(2).setCellValue("数量（块数）");
            row.createCell(3).setCellValue("2万平以上（块数）");
            row.createCell(4).setCellValue("2万平以下（块数）");
            rowNum += 1;

            BigDecimal totalSumArea = BigDecimal.ZERO;
            long totalCount = 0;
            long totalGreaterThanCount = 0;
            long totalLessThanCount = 0;
            for (Map.Entry<String, List<Land>> map : listMap.entrySet()) {
                List<Land> landList1 = map.getValue();
                String county = map.getKey();
                BigDecimal sumArea = BigDecimal.ZERO;
                long count = landList1.size();
                long greaterThanCount = 0;
                for (Land land : landList1) {
                    BigDecimal landArea = land.getLandArea();
                    sumArea = sumArea.add(landArea);
                    if (landArea.compareTo(BigDecimal.valueOf(20000)) > 0) {
                        greaterThanCount += 1;
                    }
                }
                long lessThanCount = count - greaterThanCount;


                totalSumArea = totalSumArea.add(sumArea);
                totalCount += count;
                totalGreaterThanCount += greaterThanCount;
                totalLessThanCount += lessThanCount;

                Row row1 = outputSheet.createRow(rowNum);
                row1.createCell(0).setCellValue(county);
                row1.createCell(1).setCellValue(sumArea.doubleValue());
                row1.createCell(2).setCellValue(count);
                row1.createCell(3).setCellValue(greaterThanCount);
                row1.createCell(4).setCellValue(lessThanCount);
                rowNum += 1;
            }
            Row row2 = outputSheet.createRow(rowNum);
            row2.createCell(0).setCellValue("总计");
            row2.createCell(1).setCellValue(totalSumArea.doubleValue());
            row2.createCell(2).setCellValue(totalCount);
            row2.createCell(3).setCellValue(totalGreaterThanCount);
            row2.createCell(4).setCellValue(totalLessThanCount);
            rowNum += 1;

            outputSheet.createRow(rowNum);
            rowNum += 1;
        }
        workbook.write(new FileOutputStream(filePath));
    }

    private static String obtainCellValue(Cell cell) {
        CellType cellType = cell.getCellTypeEnum();

        if (cellType == CellType.STRING) {
            return cell.getStringCellValue();
        }

        if (cellType == CellType.NUMERIC) {
            Double value = cell.getNumericCellValue();
            int intValue = value.intValue();
            if (value - intValue == 0) {
                return String.valueOf(intValue);
            } else {
                return String.valueOf(value);
            }
        }
        return null;
    }
}
