
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class EnrolmentResults {
	public static void main(String args[]) {
		CourseList cL1 = new CourseList();
		CourseList cL2 = new CourseList(cL1);
		Scanner sc = null;
		try {
			sc = new Scanner(new File("C:\\Users\\Dabris\\Desktop\\workspace\\249A4\\Syllabus.txt"));
			// sc = new Scanner(
			// new
			// File("C:\\Users\\Bryia\\Desktop\\Eclipse\\249_A4\\Comp249_W18_Assg4_Files\\Syllabus.txt"));
			while (sc.hasNextLine()) {
				String ID = sc.next();
				String name = sc.next();
				double credit = sc.nextDouble();
				sc.nextLine();
				String preReq = sc.nextLine();
				String temp[] = preReq.split("\\s+");
				if (temp.length < 2) {
					preReq = null;
				} else {
					preReq = temp[1];
				}
				String coReq = sc.nextLine();
				String temp1[] = coReq.split("\\s+");
				if (temp1.length < 2) {
					coReq = null;
				} else {
					coReq = temp1[1];
				}
				if (!cL1.contains(ID))
					cL1.addToStart(new Course(ID, name, credit, preReq, coReq));

			}
			sc.close();

		} catch (FileNotFoundException e) {
			e.getMessage();
		}

		System.out.println(cL1.toString());
		// string to see if user wants to enter another file
		String yn = "n";
		// repeat if needed
		do {
			Scanner key = new Scanner(System.in), reqSc = null;
			System.out.println("Please enter the name of the file you wish to read");
			// String fName
			// ="C:\\Users\\Bryia\\Desktop\\Eclipse\\249_A4\\Comp249_W18_Assg4_Files\\"+
			// key.next();
			String fName = "C:\\Users\\Dabris\\Desktop\\workspace\\249A4\\" + key.next();
			System.out.println(fName);
			try {
				// read file
				reqSc = new Scanner(new File(fName));
				// reads "finished"
				reqSc.next();
				// course finished
				ArrayList<String> cDone = new ArrayList<String>();
				// course requested
				ArrayList<String> cReq = new ArrayList<String>();
				String finished = reqSc.next();
				String requested = null;

				// read the file line by line
				while (reqSc.hasNextLine()) {
					// read until reaching the line requested
					while (!finished.equalsIgnoreCase("requested")) {
						// add all courses that are finished
						cDone.add(finished);
						// find requested in document
						finished = reqSc.next();
					}
					if (reqSc.hasNext()) {
						// when requested is found add its value the the arraylist
						requested = reqSc.next();
						cReq.add(requested);
					}
				}
				System.out.println("Courses requested:" + Arrays.toString(cReq.toArray()) + "\nCourses done:"
						+ Arrays.toString(cDone.toArray()));
				if (cReq.isEmpty()) {
					System.out.println("No enrollment courses found.");
				} else {
					for (int i = 0; i < cReq.size(); i++) {
						if (cL1.getC1coReq(cReq.get(i)) == null && cL1.getC1PReq(cReq.get(i)) == null) {
							System.out.println("Student can enrol in " + cReq.get(i) + " as all requirements are met.");
						} else if (cDone.contains(cL1.getC1PReq(cReq.get(i)))
								|| cReq.contains(cL1.getC1coReq(cReq.get(i)))) {
							if (cReq.contains(cL1.getC1coReq(cReq.get(i)))) {
								System.out.println("Student can enrol in " + cReq.get(i)
										+ " as he/she/they is enrolling for co-requisite " + cL1.getC1coReq(cReq.get(i))
										+ ".");
							} else if (cDone.contains(cL1.getC1PReq(cReq.get(i)))) {
								System.out.println("Student can enrol in " + cReq.get(i)
										+ " as he/she/they has completed the pre-requisite "
										+ cL1.getC1PReq(cReq.get(i)) + ".");
							} else {
								System.out.println("Student can enrol in " + cReq.get(i)
										+ " as he/she/they is enrolling for co-requisite "
										+ cL1.getC1coReq(cReq.get(i) + ".") + "and has completed the pre-requisite"
										+ cL1.getC1PReq(cReq.get(i)) + ".");
							}
						} else {
							System.out.println("Student can't enrol in " + cReq.get(i)
									+ " as he/she doesn't have sufficient background needed.");

						}
					}
				}
				reqSc.close();

			} catch (FileNotFoundException e) {
				e.getMessage();
			}

			System.out.println("Do you wish to open another file? (y/s)");
			// Scanner again=new Scanner(System.in);
			yn = key.next();
		} while (yn.equalsIgnoreCase("y"));

	}
}
