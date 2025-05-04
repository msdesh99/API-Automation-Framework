package utils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {
	 private String file;
	 private String sheetName;
	 private Workbook wrk;
	 private Sheet sheet;
	
	 public ExcelReader(String filePath, String sheetName) {
		 this.file = System.getProperty("user.dir")+filePath;
		 this.sheetName = sheetName;
		 try(FileInputStream fis = new FileInputStream(new File(this.file))){
			 wrk = new XSSFWorkbook(fis);
			 sheet = wrk.getSheet(sheetName);
		 }catch(IOException ioe) {
			 System.out.println(ioe.getMessage());
		 }    
		 
	 }
	public List<Map<String,Object>> readExcelToList(){
		Row headerRow = sheet.getRow(0);
		int totalRows = sheet.getPhysicalNumberOfRows();
		
		int totalCells = headerRow.getPhysicalNumberOfCells();
	    List<Map<String,Object>> list = 
	    		IntStream.range(1, totalRows)
	    		.mapToObj(i->{ Row dataRow = sheet.getRow(i);
	    		      Map<String,Object>map1 = IntStream.range(0,totalCells)
	    		       .boxed()
	    		      .collect(Collectors.toMap(
	    		    		  j->headerRow.getCell(j).getStringCellValue(),
	    		    		  j->{
	    		    			  return dataRow.getCell(j).getCellType().equals(CellType.NUMERIC)?
	    		    			 (long)dataRow.getCell(j).getNumericCellValue(): //long to convert double to long
	    		    			dataRow.getCell(j).getStringCellValue();
	    		    		   }	
	    		    		  ));
	    		      return map1;
	    		}).collect(Collectors.toList());
	    		
	    		list.stream().forEach(System.out::println);
 return list;
		
	}
	public void printExcel(){
		     Row headerRow = sheet.getRow(0);
		     int totalRows = sheet.getPhysicalNumberOfRows();
		     int totalCells = headerRow.getPhysicalNumberOfCells();
		     IntStream.range(1, totalRows+1)
		     .forEach(i->{ Row dataRow = sheet.getRow(i); 
		    	           IntStream.range(0,totalCells)
		    	           .forEach(j->{
		    	     System.out.printf("%s\t", headerRow.getCell(j).getStringCellValue()
		    	    	//	 +dataRow.getCell(j).getStringCellValue()
		    		    
		    		 );}); System.out.println();
		     });  //i loop			 
			 
	
	}
	
	public static void main(String[] args) {
		String file = "/src/test/resources/testdata/user-data.xlsx";
		String sheet = "CreateUser";
		ExcelReader eRead = new ExcelReader(file,sheet);
		eRead.printExcel();
		eRead.readExcelToList();
	}
/*
 * 	List<Map<String,Object>> list=
		IntStream.range(1, totalRows+1)
		.mapToObj(i->{ Row dataRow = sheet.getRow(i);
		              Map<String,Object> map1 = IntStream.range(0, totalCells)
		               .boxed()	
		               .collect(Collectors.toMap(
		            		   j->headerRow.getCell(j).getStringCellValue(),
		            		   j->{
		            			   return dataRow.getCell(j).getCellType().equals(CellType.NUMERIC)?
		            					  String.valueOf((long)dataRow.getCell(j).getNumericCellValue()):
		            					  dataRow.getCell(j).getStringCellValue();	  
		            		   }
		            		   ));
		              return map1;
		               
		}).collect(Collectors.toList());
		
		
 */
}
