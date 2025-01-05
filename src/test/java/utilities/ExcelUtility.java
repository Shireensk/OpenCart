
package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;

public class ExcelUtility
{
    public static FileInputStream fi;
    public static FileOutputStream fo;
    public static XSSFWorkbook wb;
    public static XSSFSheet ws;
    public static XSSFCell cell;
    public static XSSFRow row;
    public static CellStyle style;

    String path;

    public ExcelUtility(String path)
    {
        this.path=path;
    }

    public int getRowCount(String xlsheet) throws IOException
    {

        fi=new FileInputStream(path);
        wb=new XSSFWorkbook(fi);
        ws=wb.getSheet(xlsheet);
        int rowCount=ws.getLastRowNum();

        wb.close();
        fi.close();

        return rowCount;


    }


    public int getCellCount(String xlsheet,int rownum) throws IOException
    {

        fi=new FileInputStream(path);
        wb=new XSSFWorkbook(fi);
        ws=wb.getSheet(xlsheet);

        row=ws.getRow(rownum);
        int cellCount=row.getLastCellNum();

        wb.close();
        fi.close();

        return cellCount;


    }

    public String getCellData(String xlsheet,int rownum,int cellnum) throws IOException
    {

        fi=new FileInputStream(path);
        wb=new XSSFWorkbook(fi);
        ws=wb.getSheet(xlsheet);
        row=ws.getRow(rownum);
        cell=row.getCell(cellnum);

        //cell can be empty so put the code in try block as it may produce any exception

        String data;

        try
        {

            //data=cell.toString();  //2nd approach for extracting cell value

            DataFormatter formatter=new DataFormatter();

            data=formatter.formatCellValue(cell); //return the formatted value of a cell as a string regardless of the cell type


        }

        catch(Exception e)
        {

            data=" ";
        }

        wb.close();
        fi.close();

        return data;


    }

    public void setCellData(String xlsheet, int rownum, int cellnum, String data) throws IOException
    {

        fi=new FileInputStream(path);
        wb=new XSSFWorkbook(fi);

        File xlfile=new File(path);
        if(!xlfile.exists())
        {
            wb = new XSSFWorkbook();
            fo = new FileOutputStream(path);
            wb.write(fo);
        }

        fi=new FileInputStream(path);
        wb=new XSSFWorkbook(fi);
        if(wb.getSheetIndex(xlsheet)==-1)
            wb.createSheet(xlsheet);
        ws=wb.getSheet(xlsheet);

        if(ws.getRow(rownum)==null)
            ws.createRow(rownum);
        row=ws.getRow(rownum);

        cell=row.createCell(cellnum);
        cell.setCellValue(data);

        fo=new FileOutputStream(path);
        wb.write(fo);

        wb.close();
        fi.close();
        fo.close();



    }

    public void fillGreenColor(String xlsheet,int rownum,int cellnum) throws IOException
    {

        fi=new FileInputStream(path);
        wb=new XSSFWorkbook(fi);
        ws=wb.getSheet(xlsheet);
        row=ws.getRow(rownum);
        cell=row.getCell(cellnum);

        style=wb.createCellStyle();

        style.setFillForegroundColor(IndexedColors.GREEN.getIndex());

        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        cell.setCellStyle(style);

        fo=new FileOutputStream(path);

        wb.write(fo);
        wb.close();
        fi.close();
        fo.close();


    }

    public void fillRedColor(String xlsheet,int rownum,int cellnum) throws IOException
    {

        fi=new FileInputStream(path);
        wb=new XSSFWorkbook(fi);
        ws=wb.getSheet(xlsheet);
        row=ws.getRow(rownum);
        cell=row.getCell(cellnum);

        style=wb.createCellStyle();

        style.setFillForegroundColor(IndexedColors.RED.getIndex());

        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        cell.setCellStyle(style);

        fo=new FileOutputStream(path);

        wb.write(fo);

        wb.close();

        fi.close();

        fo.close();

    }

}
