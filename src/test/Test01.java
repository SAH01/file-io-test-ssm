import com.reliable.dao.ReadDB;
import com.reliable.dao.ReadFile;
import com.reliable.service.ReadDBImpl;
import com.reliable.service.ReadFileImpl;
import org.junit.Test;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class Test01 {
	public static void main(String[] args)  {

	}
	@Test
	public void test1() throws IOException, SQLException {
		ReadFile readFile= new ReadFileImpl();
		//G:\project\高新技术企业备份\高新技术企业\QYJBZB_2013_TB11702018.csv
//		readFile.readExcelFile("G:\\project\\13和16\\国标\\std_subject.xlsx");
//		ArrayList<ArrayList<String>> resCsv = readFile.readCsvFile("G:\\hello.csv");
//		ArrayList<ArrayList<String>> resExcel = readFile.readExcelFile("G:\\hello.xlsx");
//		System.out.println(res.size());
//		String text = "reliableYang";
//		System.out.println(text.substring(1,3));
//		System.out.println(text.substring(text.length()-3));
		ReadDB readDB =new ReadDBImpl();
		readDB.getAllDbData();
	}
}

