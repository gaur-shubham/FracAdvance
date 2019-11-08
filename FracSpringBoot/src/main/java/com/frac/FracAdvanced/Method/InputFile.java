package com.frac.FracAdvanced.Method;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.frac.FracAdvanced.model.MiniFracModel;
import com.frac.FracAdvanced.model.ProjectDetails;
import com.frac.FracAdvanced.repository.MiniFracRepo;
import com.frac.FracAdvanced.repository.ProjectDetailRepo;

/**
 * @author ShubhamGaur
 *
 */
@Component
public class InputFile {

	private static MiniFracRepo service;
	private static ProjectDetailRepo prodetails;
	private static HttpSession session;
	
	@Autowired
	public InputFile(MiniFracRepo service1,ProjectDetailRepo prodetails1,HttpSession httpSession) {
		session=httpSession;
		service=service1;
		prodetails=prodetails1;
	}
	public static List<MiniFracModel> process(MultipartFile file,Double pumptime,int p_id) throws Exception{
		
		List<MiniFracModel> minifracList = new ArrayList<MiniFracModel>();
		XSSFWorkbook workbook=new XSSFWorkbook(file.getInputStream());
		XSSFSheet worksheet=workbook.getSheetAt(2);
		Row row;
		Cell c;
		String time;
		String pressure;
		MiniFracModel mini=null;
		//int id=Integer.parseInt(session.getId());
		
		for(int i=worksheet.getFirstRowNum();i<worksheet.getLastRowNum() ;i++) {
			
			row=worksheet.getRow(i+3);
		
			if(row!=null) {
				for(int j=0;j<row.getLastCellNum();j++) {
				c=row.getCell(j);
					if(c==null) {
						continue;
					}else {
				pressure=row.getCell(7).toString();
				time=row.getCell(1).toString();
				
				mini=new MiniFracModel();
				mini.setPumptime(pumptime.toString());
				mini.setPressure(pressure);
				mini.setTime(time);
				Optional<ProjectDetails> detail= prodetails.findById(p_id);
				mini.setDetails(detail.get());
				minifracList.add(mini);
				break;
					}
					}
			}
			else {
				continue;
		}
			service.saveAll(minifracList);
		}
		workbook.close();
		return minifracList;
	}
	
}
