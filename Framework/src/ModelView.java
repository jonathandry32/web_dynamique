package etu2040.framework;
import java.util.HashMap;
import java.util.*;
public class ModelView {
    String view;
    HashMap<String,Object> data;

    public ModelView(){}
    public ModelView(String view){
        setView(view);
    }
    public ModelView(String view,HashMap<String,Object> d){
        setView(view);
        setData(d);
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
}