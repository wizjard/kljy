<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@page  import = "java.net.URLDecoder" %>   
<%@ page  import = "java.util.*" %>   
<%@ page  import = "org.apache.commons.fileupload.*" %>
<%@ page  import = "java.io.*" %>   
 
<%   
String contextPath = request.getContextPath() ;   
String SavePath = request.getSession().getServletContext().getRealPath("/")+"image\\";   
String SaveUrl = contextPath+ "/image/" ;  
System.out.println("c--->"+contextPath+"----->"+SavePath+"---->"+SaveUrl);
String[] ExtArr =  new  String[]{ ".gif" , ".jpg" , ".png" , ".bmp" , ".jpeg" };   
String id = ""; 
int  MaxSize =  4000000 ;   
String Msg1 =  "上传文件大小超过限制。" ;   
String Msg2 =  "上传文件的扩展名不被允许。" ;    
String Msg3 =  "文件上传失败。" ;   
String Msg=Msg3;   
//java.io.File files=new java.io.File(".");       
//String FileName = (String)request.getAttribute("fileName");   
String FileWidth =  null ;   
String FileHeight =  null ;   
String FileBorder =  null ;   
String FileTitle =  null ;   
String FileTitle2 =  null ;   
String FileAlign =  null ;   
String FileHspace =  null ;   
String FileVspace =  null ;   
  
Date dt =  new  Date();   
Random random =  new  Random();   
random.nextInt();   
String FileNameAuto = String.format( "%X_%X" , new  Object[]{ new  Integer(( int )(dt.getTime())), new  Integer(random.nextInt())});   

System.out.println(FileNameAuto);
String FilePath =  null ;   
String FileUrl =  null ;   
DiskFileUpload fu =  new  DiskFileUpload();   
fu.setSizeMax(MaxSize); //   
fu.setSizeThreshold( 4096 );   
fu.setRepositoryPath( "d:/" );  
//ServletRequestContext src = new ServletRequestContext(request);      
List fileItems = fu.parseRequest(request); 
Iterator iter = fileItems.iterator();  
while  (iter.hasNext()) {   
    FileItem item = (FileItem) iter.next();   
    String fieldName = item.getFieldName(); 
    if(item.getFieldName().equals("id")){   
        id = item.getString();
    } 
     if  (!item.isFormField()) {   
        String name = item.getName();   
         long  size = item.getSize();   
         if ((name== null ||name.equals( "" )) && size== 0 )   
         continue ;   
         if (size>MaxSize) {   
            Msg=Msg1;   
             break ;   
        }   
         //namename = name.replace(':','_');   
         //namename = name.replace('\\','_');   
         int  pos = name.lastIndexOf( "." );   
        String ext = name.substring(pos);   
         boolean  b= false ;   
         for ( int  m= 0 ;m<ExtArr.length; m++){   
             if (ExtArr[m].equalsIgnoreCase(ext)){   
                b= true ;   
                 break ;   
            }   
        }   
         if  (b== false ){   
            Msg=Msg2;   
             break ;   
        }   
        FilePath = SavePath + FileNameAuto+ext;   
        System.out.println(FilePath+"  "+SavePath+"   "+FileNameAuto+"  "+ext);
        FileUrl = SaveUrl + FileNameAuto+ext;
        File dirFile  =   new File(SavePath);
        if (!(dirFile.exists()) && !(dirFile.isDirectory()))
        {
        	boolean  creadok  =  dirFile.mkdirs();
            if (creadok)  {
            	System.out.println( "ok:创建文件夹成功！ " );
            } else  {
            	System.out.println( "err:创建文件夹失败！ " );                    
        	} 
        } 
        java.io.File f=  new  java.io.File(FilePath); 
        item.write(f);   
    }   
     else {   
        String fieldValue = item.getString();   
         if ( "imgWidth" .equals(fieldName)){   
            FileWidth = fieldValue;   
        }   
         else   if ( "imgHeight" .equals(fieldName)){   
            FileHeight = fieldValue;   
        }   
         else   if ( "imgBorder" .equals(fieldName)){   
            FileBorder = fieldValue;   
        }   
         else   if ( "imgTitle" .equals(fieldName)){   
            FileTitle = fieldValue;   
        }   
         else   if ( "imgTitle2" .equals(fieldName)){   
             //FileTitle2 = URLDecoder.decode(fieldValue,"GB18030");   
            FileTitle2 = URLDecoder.decode(fieldValue, "UTF-8" );   
        }   
         else   if ( "imgAlign" .equals(fieldName)){   
            FileAlign = fieldValue;   
        }   
         else   if ( "imgHspace" .equals(fieldName)){   
            FileHspace = fieldValue;   
        }   
         else   if ( "imgVspace" .equals(fieldName)){   
            FileVspace = fieldValue;   
        }   
    }   
}   
if (FileUrl!= null ){   
    out.println(  "<html>" );   
    out.println(  "<head>" );   
    out.println(  "<title>error</title>" );   
    out.println(  "<meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\">" );   
    out.println(  "</head>" );   
    out.println(  "<body>" );   
    out.println("<script type=\"text/javascript\">parent.KE.plugin[\"image\"].insert(\""+ id +"\",\""+FileUrl+"\",\""+FileTitle+"\",\""+FileWidth+"\",\""+FileHeight+"\",\""+FileBorder+"\");</script>");
  	System.out.println("<script type=\"text/javascript\">parent.KE.plugin[\"image\"].insert(\""+ id +"\",\""+FileUrl+"\",\""+FileTitle+"\",\""+FileWidth+"\",\""+FileHeight+"\",\""+FileBorder+"\");</script>");
    out.println(  "</body>" );   
    out.println(  "</html>" );   
}   
else {   
    out.println(  "<html>" );   
    out.println(  "<head>" );   
    out.println(  "<title>error</title>" );   
    out.println(  "<meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\">" );   
    out.println(  "</head>" );   
    out.println(  "<body>" );   
    out.println(  "<script type=\"text/javascript\">alert(\""  + Msg +  "\");parent.KindDisableMenu();parent.KindReloadIframe();</script>" );   
    out.println(  "</body>" );   
    out.println(  "</html>" );   
}   
%>  