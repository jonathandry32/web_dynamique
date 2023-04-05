package etu2040.framework.app.modele;
import etu2040.framework.*;
import etu2040.framework.servlet.annotations.*;
import java.util.*;
@CAnnot
public class Emp{
	int id;
	String name;

	public Emp(){}
	public Emp(int id,String name){
		set_id(id);
		set_name(name);
	}
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
	public ModelView add(){
		ArrayList<Emp> listEmp=new ArrayList<Emp>();
			Emp a=new Emp(1,"hgfyu");
			Emp b=new Emp(2,"tydi");
			listEmp.add(a);
			listEmp.add(b);
		ModelView mv=new ModelView("../list.jsp");
		//mv.setView("../list.jsp");
		mv.addItem("listEmp",listEmp);
		return mv;
	}
}
