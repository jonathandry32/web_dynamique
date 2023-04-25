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
public class FrontServlet extends HttpServlet {
    HashMap<String,Mapping> mappingUrl = new HashMap<String, Mapping>();
    String pck="";
    public void init() throws ServletException{
        ServletContext ctxt=getServletContext();
        this.pck=ctxt.getInitParameter("package");
        
        try{
            loadAnnotation();
        }
        catch(Exception e){}
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
    public Object eval(Object obj,String method) throws Exception{
        Class<?> c = obj.getClass();
        Method m=c.getDeclaredMethod(method);
        Object result = m.invoke(obj);
        return result;
    }
    public Object settena(String className,String[] attributes,HttpServletRequest request) throws Exception{
        Class<?> c=Class.forName(className);
        Object obj=c.newInstance();
        Class<?> cls = obj.getClass();
        Method[] methods = cls.getMethods();
        for(String attribute : attributes){
            String temp = "set_"+attribute;
            for(Method method : methods){
                if(method.getName().equalsIgnoreCase(temp)){
                    if(c.getDeclaredField(attribute).getType()==int.class){
                        method.invoke(obj,Integer.valueOf(request.getParameter(attribute)));
                    }
                    else if(c.getDeclaredField(attribute).getType()==String.class){
                        method.invoke(obj,request.getParameter(attribute));
                    }
                    else{
                        method.invoke(obj,c.getDeclaredField(attribute).getType().cast(request.getParameter(attribute)));
                    }
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
            String requete=request.getQueryString();
            if(mappingUrl.containsKey(url)){
                try{
                    out.println("la class: "+mappingUrl.get(url).getClassName());
                    out.println("la method: "+mappingUrl.get(url).getMethod());
                    String[] viewdata =  getViewData(request);
                    Object tempp = settena(mappingUrl.get(url).getClassName(),viewdata,request);
                    Object valiny = eval(tempp,mappingUrl.get(url).getMethod());
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
//jar -cfv C:\Users\ITU\Desktop\apache-tomcat-8.5.75\webapps\test_framework.war ../../dossier_a_envoyer
}