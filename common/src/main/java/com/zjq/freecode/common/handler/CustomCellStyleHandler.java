/**
 * @Description: 自定义写excel头部样式
 * @Author: zhangjunqiang
 * @Date: 2021/11/18
 */
public class CustomCellStyleHandler extends AbstractCellStyleStrategy {
    private Workbook workbook;

    @Override
    protected void initCellStyle(Workbook book) {
        this.workbook = book;
    }

    @Override
    protected void setHeadCellStyle(Cell cell, Head head, Integer integer) {
        WriteCellStyle writeCellStyle = new WriteCellStyle();
        writeCellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
        WriteFont font = new WriteFont();
        font.setFontHeightInPoints(NumberConstants.SHORT_11);
        writeCellStyle.setWriteFont(font);
        CellStyle style = StyleUtil.buildHeadCellStyle(workbook, writeCellStyle);
        cell.setCellStyle(style);
    }

    @Override
    protected void setContentCellStyle(Cell cell, Head head, Integer integer) {
        // do nothing
    }
}
