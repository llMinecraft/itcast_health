import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class POITest{

    @Test
    public void test_1()throws Exception{
        //加载指定文件，创建一个Excel对象
        XSSFWorkbook excel = new XSSFWorkbook(new FileInputStream(new File("E:\\暑期项目\\t1.xlsx")));
        //读取Excel文件中第一个Sheet标签页
        XSSFSheet sheet = excel.getSheetAt(0);
        for(Row row :sheet){
            //遍历行，获得每个单元格对象
            for(Cell cell:row){
                System.out.println(cell.getStringCellValue());
            }
        }
        //关闭资源
        excel.close();
    }

    @Test
    public void test_2()throws Exception{
        //加载指定文件，创建一个Excel对象
        XSSFWorkbook excel = new XSSFWorkbook(new FileInputStream(new File("E:\\暑期项目\\t1.xlsx")));
        //读取Excel文件中第一个Sheet标签页 工作表
        XSSFSheet sheet = excel.getSheetAt(0);
        //获得当前工作表最后一个行号,需要注意；行号从0开始
        int lastRowNum = sheet.getLastRowNum();
        for(int i=0;i<=lastRowNum;i++){
            XSSFRow row = sheet.getRow(i);//根据行号
            //获取当前行最后一个单元格索引
            short lastCellNum = row.getLastCellNum();
            for (int j = 0; j < lastCellNum; j++) {
                XSSFCell cell = row.getCell(j);
                System.out.println(cell.getStringCellValue());
            }
        }

        //关闭资源
        excel.close();
    }

    @Test
    public void test_3() throws Exception{
        //从内存中创建Excel文件
        XSSFWorkbook excel = new XSSFWorkbook();

        XSSFSheet sheet = excel.createSheet("张三笔记");

        XSSFRow title = sheet.createRow(0);

        title.createCell(0).setCellValue("姓名");
        title.createCell(1).setCellValue("地址");
        title.createCell(2).setCellValue("年龄");

        XSSFRow dataRow = sheet.createRow(1);

        title.createCell(0).setCellValue("小明");
        title.createCell(1).setCellValue("北京");
        title.createCell(2).setCellValue("20");

        FileOutputStream out = new FileOutputStream(new File(("e:\\hello.xlsx")));
        excel.write(out);
        out.flush();
        excel.close();

    }
}
