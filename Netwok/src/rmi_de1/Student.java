package rmi_de1;

import java.io.Serializable;

public class Student implements Serializable {
	private int id;
	private String name;
	private int year;
	private double grade;
	public Student(int id, String name, int year, double grade) {
		super();
		this.id = id;
		this.name = name;
		this.year = year;
		this.grade = grade;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public double getGrade() {
		return grade;
	}
	public void setGrade(double grade) {
		this.grade = grade;
	}
	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", year=" + year + ", grade=" + grade + "]";
	}
	
}
