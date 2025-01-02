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
			writeName(raf);
			raf.writeDouble(grade);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private void writeName(RandomAccessFile raf) throws IOException {
		for (int i = 0; i < StudentManager.NAME_LENG; i++) {
			if (i < name.length()) {
				raf.writeChar(name.charAt(i));
			} else {
				raf.writeChar(0);
			}
		}
	}
	public void readData(RandomAccessFile raf) {
		try {
			this.id = raf.readInt();
			this.name = readName(raf);
			this.grade = raf.readDouble();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private String readName(RandomAccessFile raf) throws IOException {
		String name = "";
		for (int i = 0; i < StudentManager.NAME_LENG; i++) {
			char c = raf.readChar();
			if (c != 0) {
				name += c;
			}
		}
		return name;
	}
}
