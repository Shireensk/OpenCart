package utilities;

import org.testng.annotations.DataProvider;

import java.io.IOException;

public class DataProviders
{
    //DataProvider1
    @DataProvider(name="LoginData")
    public String[][] getData() throws IOException {
        String path=".\\testData\\Opencart_LoginData.xlsx";

        ExcelUtility xlutil=new ExcelUtility(path);

        int totalrows= xlutil.getRowCount("sheet1");
        int totalcols= xlutil.getCellCount("sheet1",1);

        String [][] logindata=new String[totalrows][totalcols];

        for(int i=1;i<=totalrows;i++)
        {
            for(int j=0;j<totalcols;j++)
            {
                logindata[i-1][j]=xlutil.getCellData("sheet1",i,j);
            }
        }

        return logindata;
    }

    //DataProvider2
    //DataProvider3

}
