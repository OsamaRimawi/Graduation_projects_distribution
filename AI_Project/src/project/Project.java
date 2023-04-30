package project;

public class Project {

		private int Pid;
		private String description = "";
		public Project() {	}


		public Project(int ID, String desc) {
			Pid = ID;
			description = desc;
		}
		
		public int getID() {
			return Pid;
		}
		public void setID(int ID) {
			Pid = ID;
		}
		public String getDescription() {
			return description;
		}

		public void setDescription(String desc) {
			description = desc;
		}

		@Override
		public String toString() {
			return "Project # " + Pid + "  \t " + description;
		}
	}

