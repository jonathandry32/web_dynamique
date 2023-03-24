package etu2040.framework;
public class ModelView {
    String view;
    public ModelView(){}
    public ModelView(String view){
        setView(view);
    }
    public void setView(String value){
        if(value!=""){
            this.view=value;
        }
    }
    public String getView(){
        return this.view;
    }
}