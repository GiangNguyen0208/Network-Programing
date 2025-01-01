package RAFStudent;

import java.io.IOException;
import java.io.RandomAccessFile;

public class Student {
	private int id;
	private String name;
	private double grade;
	public Student(int id, String name, double grade) {
		this.id = id;
		this.name = name;
		this.grade = grade;
	}
	public Student() {
		super();
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

	public double getGrade() {
		return grade;
	}

	public void setGrade(double grade) {
		this.grade = grade;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", grade=" + grade + "]";
	}
	public void writeStudent(RandomAccessFile raf) {
		try {
			raf.writeByte(0);
			raf.writeInt(id);
			raf.writeUTF(name);
			raf.writeDouble(grade);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
