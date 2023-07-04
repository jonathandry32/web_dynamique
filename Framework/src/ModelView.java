package etu2040.framework;
import java.util.HashMap;
import java.util.*;
public class ModelView {
    String view;
    HashMap<String,Object> data;
    HashMap<String,Object> session;
    boolean isJson = false;

    public ModelView(){}
    public ModelView(String view){
        setView(view);
    }
    public ModelView(String view,HashMap<String,Object> d,HashMap<String,Object> s){
        setView(view);
        setData(d);
        setSession(s);
    }
    public void setView(String value){
        if(value!=""){
            this.view=value;
        }
    }
    public String getView(){
        return this.view;
    }
    public void addItem(String key,Object value){
        data = new HashMap<String, Object>();
        data.put(key,value);
    }
    public HashMap<String,Object> getData(){
        return data;
    }
    public void setData(HashMap<String,Object> d){
        this.data=d;
    }
    public void addSession(String key,Object value){
        session = new HashMap<String, Object>();
        session.put(key,value);
    }
    public HashMap<String,Object> getSession(){
        return session;
    }
    public void setSession(HashMap<String,Object> d){
        this.session=d;
    }
    public boolean getIsJson() {
        return isJson;
    }
    public void setIsJson(boolean isJson) {
        this.isJson = isJson;
    }
}