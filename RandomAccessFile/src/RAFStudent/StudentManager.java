package RAFStudent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class StudentManager {
	public static final int STUDENT_SIZE = 63;
	public static final int HEADER = 4;
	public static final int NAME_LENG = 25;
	RandomAccessFile raf;
	int count = 0;
	
	public StudentManager(String path) throws IOException {
		this.raf = new RandomAccessFile(new File(path), "rw");
		if (raf.length() > 0) {
			count = raf.readInt();
			raf.seek(0);
		} else {
			count = 0;
			raf.writeInt(count);
			raf.seek(0);
		}
	}
	
	public static void main(String[] args) throws IOException {
		String path = "student.txt";
		StudentManager studentManager = new StudentManager(path);
		Student s1 = new Student(4, "Tran Van A", 5);
		Student s2 = new Student(4, "Tran Van B", 9);
		Student s3 = new Student(4, "Tran Van C", 9);
//		studentManager.add(s1);
		studentManager.add(s2);
//		studentManager.add(s3);
//		studentManager.delete(3);
		
	}

	private void delete(int id) throws IOException {
		
	}

	private void add(Student student) throws IOException {
		raf.seek(raf.length());
		student.writeStudent(raf);
		count++;
		raf.seek(0);
		raf.writeInt(count);
	}

}