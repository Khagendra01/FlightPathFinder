import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class CsvReader {

	public HashMap<String, Integer> myMap = new HashMap<>();
	public ArrayList<String> myMapReverse = new ArrayList<>();
	public EdgeWeightedDigraph thisGraph;

	public CsvReader() {
		String fileName = "FlightCostsSmall119.csv";

		File myFile = new File(fileName);

		ArrayList<ArrayList<String>> myList = new ArrayList<>();
		ArrayList<String> temp;

		try {
			Scanner myScanner = new Scanner(myFile);

			myScanner.nextLine();// removing the first line that is not necessary detail for modeling
			while (myScanner.hasNextLine()) {
				String newLine = myScanner.nextLine();
				String[] thisLine = newLine.split(",");
				temp = new ArrayList<>();
				for (String cell : thisLine) {
					temp.add(cell);
				}
				myList.add(temp);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		int vertices = 0;

		for (ArrayList<String> list : myList) {
			for (int i = 0; i < 3; i++)
				if (myMap.putIfAbsent(list.get(i), vertices) == null) {
					myMapReverse.add(list.get(i));
					vertices++;
				}
		}

		thisGraph = new EdgeWeightedDigraph(vertices);
		for (ArrayList<String> list : myList) {
			thisGraph.addEdge(new DirectedEdge(myMap.get(list.get(1)), myMap.get(list.get(0)), 0));
			thisGraph.addEdge(
					new DirectedEdge(myMap.get(list.get(0)), myMap.get(list.get(2)), Double.parseDouble(list.get(3))));
		}

		//Key-Value printer
//		int x = 0;
//		for(String thisString: myMapReverse) {
//			System.out.println(thisString + " : " + myMap.get(thisString));
//		}

	}

}
