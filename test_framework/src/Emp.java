package etu2040.framework.app.modele;
import etu2040.framework.servlet.annotations.*;
@CAnnot
public class Emp{
	int id;
	String name;

	public Emp(){}
	public void set_id(int value){
		if (value>0) {
			this.id=value;
		}
	}
	public void set_name(String value){
		if (value!="") {
			this.name=value;
		}
	}
	public int get_id(){
		return this.id;
	}
	public String get_name(){
		return this.name;
	}
	@UrlAnnot(url="Emp-add")
	public String add(){
		String result="method add appelle de la classe emp";
		return result;
	}
}
