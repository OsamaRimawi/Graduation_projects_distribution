package project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Controller {

@SuppressWarnings("deprecation")
public static ArrayList<Group> readExcelFile() {

	ArrayList<Group> groups = new ArrayList<Group>();
	int i = 1;
	int id = 0;
	String student1 = "";
	String student2 = "";
	String student3 = "";
	int option1 = 0;
	int option2 = 0;
	int option3 = 0;
	Group g;
	try {

		File file = new File("C:\\Users\\user\\eclipse-workspace\\AI_Project\\Students+selections.xlsx");
		FileInputStream fis = new FileInputStream(file);
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet sheet = wb.getSheetAt(0);
		Iterator<Row> itr = sheet.iterator();

		Row row = itr.next(); // to skip the first row
		while (itr.hasNext()) {
			row = itr.next();
			Iterator<Cell> cellIterator = row.cellIterator();
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				switch (cell.getCellType()) {
				case Cell.CELL_TYPE_STRING: // field that has student names
					String[] names = cell.getStringCellValue().split(",");
					for (String a : names) {
						switch (i) {
						case 1:
							student1 = a.trim();
							i = 2;
							break;
						case 2:
							student2 = a.trim();
							i = 3;
							student3 = "";
							break;
						case 3:
							student3 = a.trim();
							break;
						default:
						}
					}
					i = 1;
					break;

				case Cell.CELL_TYPE_NUMERIC: // fields that has numbers
					switch (i) {
					case 1:
						option1 = (int) cell.getNumericCellValue();
						i = 2;
						break;
					case 2:
						option2 = (int) cell.getNumericCellValue();
						i = 3;
						break;
					case 3:
						option3 = (int) cell.getNumericCellValue();
						i = 1;
						break;
					default:
					}
					break;
				default:
				}
			}
			id++;
			g = new Group(id, student1, student2, student3, option1, option2, option3);
			groups.add(g);
		}
	wb.close();
	} catch (Exception e) {
		e.printStackTrace();
	}
	return (groups);
}

public static ArrayList<Project> readPdfFile() {
	ArrayList<Project> projects = new ArrayList<Project>();

	try {
		File file = new File(
				"C:\\Users\\user\\eclipse-workspace\\AI_Project\\افكار+مشاريع+التخرج-الفصل+الأول+2021-2022.pdf");

		PDDocument document = PDDocument.load(file);
		document.getClass();
		if (!document.isEncrypted()) {
			PDFTextStripperByArea stripper = new PDFTextStripperByArea();
			stripper.setSortByPosition(true);
			PDFTextStripper Tstripper = new PDFTextStripper();
			String str = Tstripper.getText(document);
			Scanner scn = null;
			scn = new Scanner(str);
			String line = "";
			int id = 1;
			String description= "";
			while (scn.hasNextLine()) {
				line = scn.nextLine();
				if (line.length() > 2) {

					if (isNumeric(line.substring(0, 1)) && !isNumeric(line.substring(0, 2))){
						if (Integer.parseInt(line.substring(0, 1)) == id) {
							projects.add(new Project((id-1),description));
							description = line.substring(2);
							id++;
						}
					}
					else if (isNumeric(line.substring(0, 2))) {
						if (Integer.parseInt(line.substring(0, 2)) == id) {
							projects.add(new Project((id-1),description));
							description = line.substring(3);
							id++;
						}
					}
					else {
						description = description+"\n" + line;
					}
				}
			}
			projects.add(new Project((id-1),description));
			projects.remove(0);
			scn.close();
		}
		document.close();
	} catch (Exception e) {
		e.printStackTrace();
	}
	return projects;
}

private static boolean isNumeric(String str) {
    return str != null && str.matches("[0-9]+");
}

	ArrayList<Project> projects = readPdfFile();
	ArrayList<Group> groups = readExcelFile();
	Genetics algorithm = new Genetics(groups);
	Population population = new Population(algorithm.POPULATION_SIZE,groups).initalizePopul();
	private int iteration = 0;
	private int iterationLimit = 200;

    @FXML
    private Label label;
    @FXML
    private Label label2;
    @FXML
    private ScrollPane scroll;
    @FXML
    private TextField textbox;
    
    @FXML
    void showProjects(ActionEvent event) {
    	scroll.setVvalue(0.0);
    	String str = "Project Number \t\t\t Description \n";
    	for(int i =0;i<projects.size();i++)
    		str = str + projects.get(i).toString() +"\n";
    	label.setText(str);    
    	}

		@FXML
		void showGroups(ActionEvent event) {
			scroll.setVvalue(0.0);
			String str = "ID \t\t\t Names \t\t\t\t First Choice   Second Choice   Third Choice\n";
			for (int i = 0; i < groups.size(); i++)
				str = str + groups.get(i).toString() + "\n";
			label.setText(str);

		}
    
    @FXML
    void generateSolutions(ActionEvent event) {
    	scroll.setVvalue(0.0);
    	population = new Population(algorithm.POPULATION_SIZE,groups).initalizePopul();
    	iteration = 0;
    	while (population.getChromos()[0].getFitness() > 0 && iteration < iterationLimit) {
		iteration++;
		population = algorithm.Reproduction(population);
    	}
    	String str = "After "+iteration+" Iterations\n";
    	str = str + "the Population for Generation number "+ (iteration) + ":\n";
    	for(int i =0;i<population.getChromos().length;i++)
    		str = str +"Chromosome #"+(i+1)+" " + population.getChromos()[i].toString() +"\n";
    	str = str +"Fitnees = Number of Conflicts between Groups \n";
    	str = str +"Benefit = Add 3 when option is 1 \tAdd 2 when option is 2 \tAdd 1 when option is 3 \n";
    	label.setText(str);
    }  
    
    @FXML
    void showBestSolution(ActionEvent event) {
    	scroll.setVvalue(0.0);
    	String str = "the Best Choice for each Group is :\n";
    	for(int i =0;i<population.getChromos()[0].getGenes().length;i++)
    		str = str + "Group #"+ (i+1) +" \t has Project # :"+ population.getChromos()[0].getGenes()[i] +"\n";
    	str = str + "Number of Conflicts between Groups = "+population.getChromos()[0].getFitness()+"\n";
    	str = str + "Benefit = "+population.getChromos()[0].getBenefit()+"\n";
    	label.setText(str);
    }

	@FXML
	void fixConflictingGroups(ActionEvent event) {
		scroll.setVvalue(0.0);
		String str = "Groups IDs that are conflicting with other groups:\n";
		int [] studentsConf = new int[37];
		int [] projectsFree = new int[37];
		
		int count=0;
		for (int i = 0; i < population.getChromos()[0].getGenes().length; i++) {
			for (int j = i + 1; j < population.getChromos()[0].getGenes().length; j++) {
				if (population.getChromos()[0].getGenes()[i] == population.getChromos()[0].getGenes()[j]) {
					str = str + "  " + (j + 1);
					studentsConf[count] = j;
					count++;
					break;
				}
			}
		}
		int count1 =0;
		str = str + "\nProject Numbers that are still available:\n";
		for (int i = 1; i < projects.size() + 1; i++) {
			int flag = 0;
			for (int j = 0; j < population.getChromos()[0].getGenes().length; j++) {
				if (i == population.getChromos()[0].getGenes()[j]) {
					flag = 1;
					break;
				}
			}
			if (flag == 0) {
				str = str + "  " + i;
				projectsFree[count1] = i;
				count1++;
			}
		}
		str = str + "\n";
		for (int i =0;i<count;i++) {
			population.getChromos()[0].getGenes()[studentsConf[i]] = projectsFree[i];
    		str = str + "Group #"+ (studentsConf[i]+1) +" \t is assigned for Project # :"+ projectsFree[i] +"\n";
		}
		population.getChromos()[0].calcFitness();
		population.getChromos()[0].calcBenefit();
		label.setText(str);
	}
	
    @FXML
    void changeLimit(ActionEvent event) {
    	if(isNumeric(textbox.getText())) {
    		iterationLimit = Integer.parseInt(textbox.getText());
    		label2.setText("");
    	}
    	else 
    		label2.setText("Invalid Input");
    }
}