/*
* Copyright(C) 2012, XXXXXXXX.
*
* 模块名称：     AAAAAAAAAAA
* 子模块名称：   BBBBBBBBBBB
*
* 备注：
*
* 修改历史：
* 时间			版本号	姓名		修改内容
* 2004/12/12		0.1		张 三		新建
* 2005/02/05		0.1		李 四		Bug修正
*/
package cn.edu.hbcit.smms.dao.gamemanagedao;
/**
 * 赛中管理--生成检录表--生成word文档类
 *
 *简要说明
 *
 *详细解释。
 * @author 杨春华
 * @version 1.00  2011/12/07 新規作成<br>
 */
import java.io.FileNotFoundException;
import java.io.FileOutputStream;   
import java.io.IOException;   
import java.util.ArrayList;

import org.apache.log4j.Logger;

import net.sf.json.JSONArray;

import cn.edu.hbcit.smms.services.gamemanageservices.GameManageCheckTableServices;
import cn.edu.hbcit.smms.util.UtilTools;

import com.lowagie.text.Cell;   
import com.lowagie.text.Document;   
import com.lowagie.text.DocumentException;   
import com.lowagie.text.Element;   
import com.lowagie.text.Font;   
import com.lowagie.text.PageSize;   
import com.lowagie.text.Paragraph;   
import com.lowagie.text.Table;   
import com.lowagie.text.pdf.BaseFont;   
import com.lowagie.text.rtf.RtfWriter2;  
public class GameManageCheckTablePrintScanDao {
	protected final Logger log = Logger.getLogger(GameManageCheckTablePrintScanDao.class.getName());
	 public boolean createDocContext(String file,String finalitemname,String itemtype,String groupname)throws DocumentException, IOException{ 
		 GameManageCheckTableDao gmctd = new GameManageCheckTableDao();
		 String itemtype1 = gmctd.getMatchType(finalitemname) ;
		 boolean flag = false;
		 UtilTools ul = new UtilTools();
	       try{
	    	   
	    	   // 设置纸张大小
	    	   
	        Document document = new Document(PageSize.A4);   
	        //建立一个书写器，与document对象关联   
	        
	        RtfWriter2.getInstance(document, new FileOutputStream(file+groupname+finalitemname+".doc"));
	        
	        document.open();   
	        //设置中文字体   
	        BaseFont bfChinese = BaseFont.createFont("STSongStd-Light","UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);   
	        //标题字体风格   
	        Font titleFont = new Font(bfChinese,12,Font.BOLD);   
	        //正文字体风格   
	        Font contextFont = new Font(bfChinese,10,Font.BOLD);   
	        Paragraph title = new Paragraph(gmctd.getSportsName());   
	        //设置标题格式对齐方式   
	        title.setAlignment(Element.ALIGN_CENTER);   
	        title.setFont(titleFont);   
	        document.add(title);
	       
	        Paragraph context = new Paragraph(groupname+finalitemname);   
	        context.setAlignment(Element.ALIGN_LEFT);   
	        context.setFont(contextFont);   
	        //段间距   
	        context.setSpacingBefore(3);   
	        //设置第一行空的列数   
	        context.setFirstLineIndent(20);   
	        document.add(context);   
	        //设置Table表格,创建一个三列的表格   
	      // System.out.println("document.add(context):"+finalitemname);
	        
	       // System.out.println(itemtype+"============");
	        JSONArray array = gmctd.getItemPlayerMessageAllTeam(finalitemname, itemtype);
	       // System.out.println("array="+array.getJSONArray(0).size());
	        JSONArray array1 = new JSONArray();
	        if(array.isEmpty()){
	        	flag = false;
	        }else{
	       // 	System.out.println(array.size()+"=====");
	         if(itemtype1.equals("2")){
	        	 
	        	 for(int i = 0; i < array.size() ;i++ ){
	        		array1 = array.getJSONArray(i);
	        		Paragraph context1 = new Paragraph("第"+(i+1)+"组");   
	     	        context1.setAlignment(Element.ALIGN_LEFT);   
	     	        context1.setFont(contextFont);   
	     	        context1.setSpacingBefore(2);     
	     	        context1.setFirstLineIndent(20);   
	     	        document.add(context1);    
	        	    Table table = new Table(8);   
	 	            int width[] = {10,10,15,15,15,15,10,10};  
	 	            table.setWidths(width);   
	 	            table.setWidth(90);
	 	            table.setAlignment(Element.ALIGN_CENTER); 
	 	            table.setAlignment(Element.ALIGN_MIDDLE); 
	 	            table.setAutoFillEmptyCells(true);
	 	            table.setBorderWidth(1);
	 	           
	 	            table.addCell(new Cell("道次"));
	        	    table.addCell(new Cell("号码"));   
	 	            table.addCell(new Cell("姓名"));
	 	            table.addCell(new Cell("成绩1"));
	 	            table.addCell(new Cell("成绩2"));
	 	            table.addCell(new Cell("成绩3"));
	 	            table.addCell(new Cell("名次"));
	 	            table.addCell(new Cell("备注"));
	 	         for(int j = 0;j < array1.size();j++ ){
	 	        	JSONArray array2 = new JSONArray();
	 	        	array2 = array1.getJSONArray(j);
	 	           
		    	    table.addCell(new Cell(Integer.toString(array2.getInt(2))));
	 	        	table.addCell(new Cell(array2.getString(0)));   
		    	    table.addCell(new Cell(array2.getString(1)));
		    	    table.addCell(new Cell());
		    	    table.addCell(new Cell()); 
		    	    table.addCell(new Cell());
		    	    table.addCell(new Cell());
		    	    table.addCell(new Cell());
	 	         }
	 	       
	 	         document.add(table);
	 	        Paragraph context2 = new Paragraph("裁判长：____________________");   
     	        context2.setAlignment(Element.ALIGN_CENTER);   
     	        context2.setFont(contextFont);   
     	        context2.setSpacingBefore(2);     
     	        context2.setFirstLineIndent(20);   
     	        document.add(context2);    
	        	 }
	 	       
	         }else{
	        	 for(int i = 0; i < array.size() ;i++ ){
	        		 array1 = array.getJSONArray(i);
	        		Paragraph context1 = new Paragraph("第"+(i+1)+"组");   
	     	        context1.setAlignment(Element.ALIGN_LEFT);   
	     	        context1.setFont(contextFont);   
	     	        context1.setSpacingBefore(2);     
	     	        context1.setFirstLineIndent(20);   
	     	        document.add(context1);    
	        	    Table table = new Table(6);   
	 	            int width[] = {10,15,20,15,10,30};  
	 	            table.setWidths(width);   
	 	            table.setWidth(90);
	 	            table.setAlignment(Element.ALIGN_CENTER); 
	 	            table.setAlignment(Element.ALIGN_MIDDLE); 
	 	            table.setAutoFillEmptyCells(true);
	 	            table.setBorderWidth(1);
	 	           
	 	            table.addCell(new Cell("道次"));
	        	    table.addCell(new Cell("号码"));
	        	    if( "1".equals(itemtype1)){
	        	    	table.addCell(new Cell("姓名"));
	        	    }else{
	        	    	table.addCell(new Cell("部门"));
	        	    }
	 	            table.addCell(new Cell("成绩"));
	 	            table.addCell(new Cell("名次"));
	 	            table.addCell(new Cell("备注"));
	 	         for(int j = 0;j < array1.size();j++ ){
	 	        	JSONArray array2 = array1.getJSONArray(j);
	 	        	log.debug("array2.get(0)"+array2.get(0));
	 	        	log.debug("array2.get(1)"+array2.get(1));
	 	        	log.debug("array2.get(2)"+array2.get(2));
	 	        	   table.addCell(new Cell(Integer.toString(array2.getInt(2))));
	 	        	   //liwei 2012-10-30修改
	 	        	 // table.addCell(new Cell(Integer.toString(array2.getInt(3))));
	 	        	   table.addCell(new Cell(array2.getString(0)));   
		    	       table.addCell(new Cell(array2.getString(1)));   
		    	       table.addCell(new Cell());
		    	       table.addCell(new Cell());
		    	       table.addCell(new Cell()); 
		    	       
	 	         } 
	 	         
	 	         document.add(table);
	 	         Paragraph context2 = new Paragraph("裁判长：____________________");   
     	         context2.setAlignment(Element.ALIGN_CENTER);   
     	         context2.setFont(contextFont);   
     	         context2.setSpacingBefore(2);     
     	         context2.setFirstLineIndent(20);   
     	         document.add(context2);    
	        	 }
	        	 
	         }
	        
	        
	        
	        
	        document.close(); 
	        flag = true;
	        }
	        }catch(FileNotFoundException e){e.printStackTrace();}
	        
	        return flag;
	    } 
	 
	
	 }

