package etu2040.framework;
public class FileUpload {
    String name;
    String path;
    byte[] data;
    public FileUpload(){}
    public FileUpload(String name,String path,byte[] bytes){
        setName(name);
        setPath(path);
        setData(bytes);
    }
    public void setName(String value){
        if(value!=""){
            this.name=value;
        }
    }
    public void setPath(String value){
        if(value!=""){
            this.path=value;
        }
    }
    public String getName(){
        return this.name;
    }
    public String getPath(){
        return this.path;
    }
    public void setData(byte[] bytes){
        this.data=bytes;
    }
    public byte[] getData(){
        return this.data;
    }
}