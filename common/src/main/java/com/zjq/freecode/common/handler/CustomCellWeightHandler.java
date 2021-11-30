/**
 * @Description: 自定义列宽处理器
 * @Author: zhangjunqiang zwx925931
 * @Date: 2021/11/26
 */
public class CustomCellWeightHandler extends AbstractColumnWidthStyleStrategy {
    private Map<Integer, Map<Integer, Integer>> cache = new HashMap<>();

    @SneakyThrows
    @Override
    protected void setColumnWidth(WriteSheetHolder writeSheetHolder, List<CellData> cellDataList, Cell cell, Head head,
            Integer integer, Boolean isHead) {
        boolean needSetWidth = isHead || !CollectionUtils.isEmpty(cellDataList);
        if (needSetWidth) {
            Map<Integer, Integer> maxColumnWidthMap = cache.get(writeSheetHolder.getSheetNo());
            if (maxColumnWidthMap == null) {
                maxColumnWidthMap = new HashMap<>();
                cache.put(writeSheetHolder.getSheetNo(), maxColumnWidthMap);
            }

            Integer columnWidth = this.dataLength(cellDataList, cell, isHead);
            if (columnWidth >= 0) {
                if (columnWidth > NumberConstants.INT_254) {
                    columnWidth = NumberConstants.INT_254;
                }

                Integer maxColumnWidth = maxColumnWidthMap.get(cell.getColumnIndex());
                if (maxColumnWidth == null || columnWidth > maxColumnWidth) {
                    maxColumnWidthMap.put(cell.getColumnIndex(), columnWidth);
                    Sheet sheet = writeSheetHolder.getSheet();
                    sheet.setColumnWidth(cell.getColumnIndex(), columnWidth * NumberConstants.INT_256);
                }

            }
        }
    }

    /**
     * 计算长度
     *
     * @param cellDataList 单元格数据集合
     * @param cell 单元格实例
     * @param isHead 是否是表头
     * @return Integer
     */
    private Integer dataLength(List<CellData> cellDataList, Cell cell, Boolean isHead)
            throws UnsupportedEncodingException {
        if (isHead) {
            return cell.getStringCellValue().getBytes(CodingConstants.UTF_8).length;
        } else {
            CellData cellData = cellDataList.get(0);
            CellDataTypeEnum type = cellData.getType();
            if (type == null) {
                return -1;
            } else {
                switch (type) {
                    case STRING:
                        // 换行符（数据需要提前解析好）
                        int index = cellData.getStringValue().indexOf("\n");
                        return index != -1
                                ? cellData.getStringValue().substring(0, index).getBytes(CodingConstants.UTF_8).length
                                        + 1
                                : cellData.getStringValue().getBytes(CodingConstants.UTF_8).length + 1;
                    case BOOLEAN:
                        return cellData.getBooleanValue().toString().getBytes(CodingConstants.UTF_8).length;
                    case NUMBER:
                        return cellData.getNumberValue().toString().getBytes(CodingConstants.UTF_8).length;
                    default:
                        return -1;
                }
            }
        }
    }
}
