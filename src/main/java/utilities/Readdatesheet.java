package utilities;

import org.apache.logging.log4j.core.tools.picocli.CommandLine;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.MalformedParametersException;

public class Readdatesheet {
    public XSSFWorkbook wb;
    public XSSFSheet ws;
    int rowcount;
    public String value;

    public String getValue(String classname, String ColHeader, String Sheetname) throws IOException {
        try {

            FileInputStream F = new FileInputStream("C:\\Users\\akash\\Intellij Idea\\Myntra\\target\\data.xlsx");
            wb = new XSSFWorkbook(F);
            ws = wb.getSheet(Sheetname);

            int rowNumber= getRownumber(classname,ColHeader);
        //   System.out.println(rowNumber);
            int columnnumber = getColNumber(ColHeader);
        //    System.out.println(columnnumber);
            XSSFCell cell =ws.getRow(rowNumber).getCell(columnnumber);
            if(cell != null) {
                value=cell.toString();
            }
        }

        catch(Exception e){
            e.printStackTrace();
        }
        return value;

    }

    public int getRownumber(String testcasename, String Colheader) throws CommandLine.MissingParameterException {
        int rownumber =0;

        rowcount = ws.getLastRowNum();
        for(int j=1;j<=rowcount;j++) {
            XSSFRow row = ws.getRow(j);
            if(row.getCell(0).getStringCellValue().equalsIgnoreCase(testcasename)) {
                rownumber = j;
                break;
            }
        }
        if(rownumber ==0) {
            throw new MalformedParametersException("Class entry missing");
        }
		getColNumber(Colheader);
        return rownumber;
    }

    public int getColNumber(String Colheader) {

        XSSFRow row = ws.getRow(0);
        int ColumnNumber=0;
        int isvalid=0;

        for(int j=ws.getFirstRowNum();j<row.getPhysicalNumberOfCells();j++) {
            if(row.getCell(j).toString().equalsIgnoreCase(Colheader)) {
                ColumnNumber=j;
                isvalid=1;
                break;
            }
        }
        if(isvalid==0) {
            throw new MalformedParametersException("Enter proper column header name");
        }

        return ColumnNumber;
    }
}
