package tcp_2;

public class Student {
	int id;
	String name;
	int birthDay;
	double grade;
	public Student(int id, String name, int birthDay, double grade) {
		super();
		this.id = id;
		this.name = name;
		this.birthDay = birthDay;
		this.grade = grade;
	}
	public Student() {
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
	public int getBirthDay() {
		return birthDay;
	}
	public void setBirthDay(int birthDay) {
		this.birthDay = birthDay;
	}
	public double getGrade() {
		return grade;
	}
	public void setGrade(double grade) {
		this.grade = grade;
	}
	
}
