package etu2040.framework.servlet;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.HashMap;
import java.util.Map;
import etu2040.framework.*;
import etu2040.framework.servlet.annotations.*;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;
import java.util.*;
import javax.servlet.http.Part;
import java.util.Collection;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;

@WebServlet("/upload")
@MultipartConfig
public class FrontServlet extends HttpServlet {
    HashMap<String,Mapping> mappingUrl = new HashMap<String, Mapping>();
     HashMap<Class,Object> singleton= new HashMap<Class,Object>();
    String pck="";
    public void init() throws ServletException{
        ServletContext ctxt=getServletContext();
        this.pck=ctxt.getInitParameter("package");
        
        try{
            if(c.isAnnotationPresent(Scope.class)){
                Scope scope= c.getAnnotation(Scope.class);
                if(scope.valeur().equals("singleton")){
                    Object obj= c.newInstance();
                    this.singleton.put(c,obj);
                }
            }
            loadAnnotation();
        }
        catch(Exception e){}
    }
    public void reset(HttpServletRequest request, Field[] att, Object o){
        try{
            for(int i=0; i<att.length; i++){
                Method m= o.getClass().getMethod("set_" + att[i].getName(), att[i].getType());
                if(att[i].getType()==String.class) m.invoke(o, null);
                if(att[i].getType()==int.class || att[i].getType()==double.class)  m.invoke(o,0);
                if(att[i].getType()==Date.class)  m.invoke(o, null);
                if(att[i].getType()==Boolean.class)  m.invoke(o, "false");
            }
        }catch(Exception e){
        
        }
    }
    public void uploadFile()throws Exception{
        
    }
    public void loadAnnotation()throws Exception{ 
        List<Class<?>> controllers = Annot.getClassesWithAnnotationBis(CAnnot.class,this.pck);
        for(Class<?> ca : controllers) {
            Method[] controllerMethods = ca.getMethods();
            for(Method method : controllerMethods) {
                if(method.isAnnotationPresent(UrlAnnot.class)) {
                    Mapping mapping = new Mapping(ca.getName(),method.getName());
                    UrlAnnot ua = method.getAnnotation(UrlAnnot.class);
                    String url = ua.url();
                    this.mappingUrl.put(url,mapping);
                }
            }
        }
    }
    public <T> T castin(String value,Class<T> clazz)throws Exception{
        Constructor<T> constructor = clazz.getConstructor(String.class);
        return constructor.newInstance(value);
    } 
    public Object eval(Object obj,String method,String[] attributes,HttpServletRequest request) throws Exception{
        Class<?> c = obj.getClass();
        Method m = null;
        Method[] methods = c.getMethods();
        for(Method me : methods){
            if(me.getName().equalsIgnoreCase(method)){
                m = me;
            }
        }
        ArrayList<Object> temp=new ArrayList<Object>();
        Parameter[] param = m.getParameters();
        for(int i=0;i<param.length;i++){
            for(int j=0;j<attributes.length;j++){
                Class<?> type = param[i].getType();
                if(param[i].getName().equalsIgnoreCase("arg"+j)){
                    temp.add(castin(request.getParameter(attributes[j]),type));
                }
            }
        }
        if(temp.size()>0){
            return m.invoke(obj,(Object[]) temp.toArray());
        }
        else{
            return m.invoke(obj);
        }
    }

    public String getFileName(Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        String[] parts = contentDisposition.split(";");
        for (String partValue : parts) {
            if (partValue.trim().startsWith("filename")) {
                return partValue.substring(partValue.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
    public void getFileUpload (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uploadPath = "C:/Users/ITU/Desktop/apache-tomcat-8.5.75/webapps/test_framework/uploads";
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        /*  try {
            Collection<Part> parts = request.getParts();
            for (Part part : parts) {
                if (part.getName().equals("file")) {
                    String fileName = getFileName(part);
                    InputStream inputStream = part.getInputStream();
                    File file = new File(uploadDir, fileName);
                    OutputStream outputStream = new FileOutputStream(file);
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                    outputStream.close();
                    inputStream.close();
                    response.getWriter().println("Le fichier a été uploadé avec succès : " + file.getAbsolutePath());
                }
            }
        } catch (IOException e) {
            response.getWriter().println("Une erreur s'est produite lors de l'upload du fichier : " + e.getMessage());
        }*/
    }

    public Object settena(String className,String[] attributes,HttpServletRequest request) throws Exception{
        Class<?> c=Class.forName(className);
        Object obj=null;
        if(this.singleton.containsKey(c)){
            Field[] att= c.getDeclaredFields();
            obj = this.singleton.get(c);
            this.reset(request, att, obj);
            //out.print("singleton");
        }else{
            obj= c.newInstance();
            //out.print("tsy singleton");
        }
        Class<?> cls = obj.getClass();
        Method[] methods = cls.getMethods();
        for(String attribute : attributes){
            String temp = "set_"+attribute;
            for(Method method : methods){
                if(method.getName().equalsIgnoreCase(temp)){
                    castin(request.getParameter(attribute),c.getDeclaredField(attribute).getType());
                    break;
                }
            }
        }
        return obj;
    }
    public String[] getViewData(HttpServletRequest request)throws Exception{
        Map<String, String[]> paramMap = request.getParameterMap();
        Set<String> paramNames = paramMap.keySet();
        String[] result = paramNames.toArray(new String[paramNames.size()]);
        return result;
    }
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
            response.setContentType("text/plain");
		    PrintWriter out = response.getWriter();
            String url=request.getRequestURL().toString();
            String[] url_=url.split("/");
            url="";
            for (int i=5;i<url_.length;i++) {
                url+=url_[i];
            }
            if(url.equalsIgnoreCase("upload")){
                this.getFileUpload(request,response);
            }
            String requete = request.getQueryString();
            if(mappingUrl.containsKey(url)){
                try{
                    out.println("la class: "+mappingUrl.get(url).getClassName());
                    out.println("la method: "+mappingUrl.get(url).getMethod());
                    String[] viewdata =  getViewData(request);
                    Object tempp = settena(mappingUrl.get(url).getClassName(),viewdata,request);
                    Object valiny = eval(tempp,mappingUrl.get(url).getMethod(),viewdata,request);
                    if(valiny!=null){
                        if(valiny.getClass()==ModelView.class){
                            ModelView valiny2 = (ModelView) valiny; 
                            RequestDispatcher dispat = request.getRequestDispatcher(valiny2.getView());
                            for (HashMap.Entry<String,Object> data : valiny2.getData().entrySet()) {
                                request.setAttribute(data.getKey(),data.getValue());
                            }
                            dispat.forward(request,response);
                        }
                        else{
                            if (requete!=null) {
                                url=url+"?"+requete;
                            }
                            out.println(url);
                            out.println(this.pck);
                        }
                    }
                }
                catch(Exception ee){
                    ee.printStackTrace(out);
                }
            }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}