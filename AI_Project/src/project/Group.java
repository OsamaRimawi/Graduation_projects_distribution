package project;

public class Group {

	private int id;
	private String student1 = "";
	private String student2 = "";
	private String student3 = "";
	private int option1;
	private int option2;
	private int option3;

	public Group() {
	}

	public Group(int ID, String stu1, String stu2, String stu3, int opt1, int opt2, int opt3) {
		id = ID;
		student1 = stu1;
		student2 = stu2;
		student3 = stu3;
		option1 = opt1;
		option2 = opt2;
		option3 = opt3;
	}
	public int getID() {
		return id;
	}
	public String getStudent1() {
		return student1;
	}

	public String getStudent2() {
		return student2;
	}
	public String getStudent3() {
		return student3;
	}
	public int getOption1() {
		return option1;
	}
	public int getOption2() {
		return option2;
	}
	public int getOption3() {
		return option3;
	}
	
	@Override
	public String toString() {
		return "Group ID: " + id + " \tStudent Names: " + student1 + ", " + student2 + ", " + student3 + " \tChoices :"
				+ " " + option1 + " ," + option2 + " ," + option3;
	}
}
