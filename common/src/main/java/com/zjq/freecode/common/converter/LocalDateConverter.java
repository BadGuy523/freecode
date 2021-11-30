/**
 * @Description: easyexcel:localdate转换
 * @Author: zhangjunqiang
 * @Date: 2021/11/18
 */
public class LocalDateConverter implements Converter<LocalDate> {
    @Override
    public Class supportJavaTypeKey() {
        return LocalDate.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public LocalDate convertToJavaData(CellData cellData, ExcelContentProperty excelContentProperty,
            GlobalConfiguration globalConfiguration) {
        return LocalDate.parse(cellData.getStringValue(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    @Override
    public CellData convertToExcelData(LocalDate value, ExcelContentProperty excelContentProperty,
            GlobalConfiguration globalConfiguration) {
        return new CellData(value.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }
}
