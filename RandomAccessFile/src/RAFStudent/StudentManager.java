package RAFStudent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

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
		Student s1 = new Student(1, "Tran Van A", 5);
		Student s2 = new Student(2, "Tran Van B", 9);
		Student s3 = new Student(3, "Tran Van C", 9);
//		studentManager.add(s1);
//		studentManager.add(s2);
//		studentManager.add(s3);
//		studentManager.delete(4);
//		studentManager.delete(2);
		studentManager.update(new Student(1, "Tran Van B", 7.5));
		System.out.println(studentManager.getListStudent());
	}
	
	public boolean update(Student newStudent) throws IOException {
		for (int i = 0; i < count; i++) {
			raf.seek(HEADER + i * STUDENT_SIZE);
			byte isDeleted = raf.readByte();
			if (isDeleted == 0) {
				int id = raf.readInt();
				if (id == newStudent.getId()) {
					raf.seek(raf.getFilePointer() - 5);
					newStudent.writeStudent(raf);
					return true;
				}
			}
		}
		return false;
	}

	private void delete(int id) throws IOException {
		for (int i = 0; i < count; i++) {
			raf.seek(HEADER + i * STUDENT_SIZE);
			byte isDeleted = raf.readByte();
			if (isDeleted == 0) {
				int idStudent = raf.readInt();
				if (idStudent == id) {
					raf.seek(raf.getFilePointer() - 4 - 1);
					raf.writeByte(1);
				}
			}
		}
	}
	
	public List<Student> getListStudent() throws IOException {
		List<Student> re = new ArrayList<Student>();
		for (int i = 0; i < count; i++) {
			if (get(i) != null) {
				re.add(get(i));
			}
		}
		return re;
	}

	private Student get(int i) throws IOException {
		raf.seek(HEADER + i * STUDENT_SIZE);
		if (raf.readByte() == 1) {
			return null;
		} else {
			Student student = new Student();
			student.readData(raf);
			return student;
		}
	}

	private void add(Student student) throws IOException {
		raf.seek(raf.length());
		student.writeStudent(raf);
		count++;
		raf.seek(0);
		raf.writeInt(count);
	}

}
