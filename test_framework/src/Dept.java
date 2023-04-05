package etu2040.framework.app.modele;
import etu2040.framework.*;
import etu2040.framework.servlet.annotations.*;
import etu2040.framework.app.modele.*;
@CAnnot
public class Dept{
	int id;
	String name;

	public Dept(){}
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
	@UrlAnnot(url="Dept-insert")
	public ModelView insert(){
		ModelView mv=new ModelView("../list.jsp");
		return mv;
	}
}
