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
    public Object eval(String className,String method) throws Exception{
        Class<?> c=Class.forName(className);
        Method m=c.getDeclaredMethod(method);
        Object obj=c.newInstance();
        return m.invoke(obj);
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
                out.println("la class: "+mappingUrl.get(url).getClassName());
                out.println("la method: "+mappingUrl.get(url).getMethod());
                try{
                    ModelView valiny = (ModelView) eval(mappingUrl.get(url).getClassName(),mappingUrl.get(url).getMethod());
                    out.println(valiny.getView());
                    out.println(valiny.getData());
                    if(valiny.getClass()==ModelView.class){
                        RequestDispatcher dispat = request.getRequestDispatcher(valiny.getView());
                        for (HashMap.Entry<String,Object> data : valiny.getData().entrySet()) {
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