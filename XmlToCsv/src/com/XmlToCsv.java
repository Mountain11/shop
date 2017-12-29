package com;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.List;

public class XmlToCsv {
	
    public static void main(String[] args) throws Exception{  
        //创建SAXReader对象  
        SAXReader reader = new SAXReader();

		String filepath = ChooseFile();
		
		if(filepath == null){
			return;
		}

		String filename = filepath.substring(filepath.lastIndexOf("\\") + 1,filepath.lastIndexOf("."));

        //读取文件 转换成Document  
        Document document = reader.read(new File(filepath));
        //获取根节点元素对象
        Element root = document.getRootElement();


		System.out.println(filename);

		File file = new File(filepath.substring(0,filepath.lastIndexOf('\\')) + "\\" + filename + ".csv");
		File file2 = new File(filepath.substring(0,filepath.lastIndexOf('\\')) + "\\" + filename + "2.csv");
		if(!file.exists()){
			file.createNewFile();
		}

		PrintWriter pw1 = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file),"gbk"));
		PrintWriter pw2 = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file2),"gbk"));
        StringBuffer sb = new StringBuffer();
        listOne(root,pw1);
       // pw.println();pw.println();pw.println();
        listTwo(root,sb,pw2);
        pw1.close();
        pw2.close();

	}

	public static String ChooseFile(){

		// 创建文件选择器
		JFileChooser fileChooser = new JFileChooser();
		// 设置当前目录
		fileChooser.setCurrentDirectory(new File("."));
		fileChooser.setAcceptAllFileFilterUsed(false);
		final String[][] fileENames = { { ".xml", "xml文件(*.xml)" }
		};

		FileSystemView fsv = FileSystemView.getFileSystemView();  //注意了，这里重要的一句

		fileChooser.setCurrentDirectory(fsv.getHomeDirectory());

		fileChooser.setDialogTitle("请选择要上传的文件...");
		fileChooser.setApproveButtonText("确定");
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

		// 显示所有文件
//		fileChooser.addChoosableFileFilter(new FileFilter() {
//			public boolean accept(File file) {
//				return true;
//			}
//			public String getDescription() {
//				return "所有文件(*.*)";
//			}
//		});
		// 循环添加需要显示的文件
						
		for (final String[] fileEName : fileENames) {

			fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {

				public boolean accept(File file) {

					if (file.getName().endsWith(fileEName[0]) || file.isDirectory()) {

						return true;
					}

					return false;
				}

				public String getDescription() {

					return fileEName[1];
				}

			});
		}

		int result = fileChooser.showDialog(null, null);
		
		if(!(result == fileChooser.APPROVE_OPTION)){
			System.out.println("没有选择文件！");
			return null;
		}
		
		String filepath = fileChooser.getSelectedFile().getAbsolutePath();
		
    	return filepath;
	}

    public static void listOne(Element node,PrintWriter pw) {
    		
    		List<Element> rootlist = node.elements();
    		for (Element element : rootlist) {
    			StringBuffer sb = new StringBuffer();
    			List<Attribute> list = element.attributes();
    			for(Attribute attribute : list){  
    				sb.append(attribute.getValue() + ',');
    			}
    			sb.delete(sb.length()-1, sb.length());
			pw.println(sb.toString());
		}
    }
    
    public static void listTwo(Element node,StringBuffer sb,PrintWriter pw){
    		List<Element> rootlist = node.elements();
    		StringBuffer ss = new StringBuffer(sb);
    		if(rootlist.size() == 0) {
    			List<Attribute> list = node.attributes();  
    			for(Attribute attribute : list){  
    				ss.append(attribute.getValue() + ',');
    	        }
    			ss.delete(ss.length()-1, ss.length());
    			System.out.println(ss.toString());
    				pw.print(ss.toString());
    				pw.println();
    				
    		}else {
    			List<Attribute> list = node.attributes();
    			for(Attribute attribute : list){  
    				sb.append(attribute.getValue() + ',');
    	        }  
    			for (Element element : rootlist) {
    				listTwo(element,sb,pw);
			}
    			sb.delete(0, sb.length());
    		}
    		
    }
    
}
