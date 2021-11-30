/**
 * @Description: 自定义单元格行高处理器
 * @Author: zhangjunqiang zwx925931
 * @Date: 2021/11/26
 */
public class CustomCellHeightHandler extends AbstractRowHeightStyleStrategy {
    /**
     * 默认高度
     */
    private static final Integer DEFAULT_HEIGHT = 300;

    @Override
    protected void setHeadColumnHeight(Row row, int relativeRowIndex) {
        // do nothing
    }

    @Override
    protected void setContentColumnHeight(Row row, int relativeRowIndex) {
        Iterator<Cell> cellIterator = row.cellIterator();
        if (!cellIterator.hasNext()) {
            return;
        }

        // 默认为 1行高度
        Integer maxHeight = 1;
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            switch (cell.getCellType()) {
                case STRING:
                    if (cell.getStringCellValue().indexOf("\n") != -1) {
                        int length = cell.getStringCellValue().split("\n").length;
                        maxHeight = Math.max(maxHeight, length);
                    }
                    break;
                default:
                    break;
            }
        }

        row.setHeight((short) (maxHeight * DEFAULT_HEIGHT));
    }
}
