package etu2040.framework.servlet;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.HashMap;
import etu2040.framework.*;
import etu2040.framework.servlet.annotations.*;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
public class FrontServlet extends HttpServlet {
    HashMap<String,Mapping> mappingUrl = new HashMap<String, Mapping>();
    public void init() throws ServletException{
        try{
            loadAnnotation();
        }
        catch(Exception e){}
    }
    public void loadAnnotation()throws Exception{ 
        List<Class<?>> controllers = Annot.getClassesWithAnnotation(CAnnot.class);
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
    public String eval(String className,String method) throws Exception{
        String result="";
        Class<?> c=Class.forName(className);
        Method m=c.getDeclaredMethod(method);
        Object obj=c.newInstance();
        result=(String) m.invoke(m);
        return result;
    }   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
            response.setContentType("text/plain");
		    PrintWriter out = response.getWriter();
            String url=request.getRequestURL().toString();
            String[] url_=url.split("/");
            url="";
            for (int i=5;i<url_.length;i++) {
                url+=url_[i];
            }
            String requete=request.getQueryString();
            try{
                out.println("la class: "+mappingUrl.get(url).getClassName());
                out.println("la method: "+mappingUrl.get(url).getMethod());
            }
            catch(Exception e){}
            if (requete!=null) {
                url=url+"?"+requete;
            }
            out.println(url);
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