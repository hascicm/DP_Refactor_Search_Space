package dataprovider;

import java.util.ArrayList;
import java.util.List;


public class FileHandler {
	List<String> lines = new ArrayList<String>();

	public FileHandler() {

	}

	/*public void loadFileAsText(String filename) {
		try {
			File file = new File(filename);
			String line;
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while ((line = bufferedReader.readLine()) != null)
				lines.add(line);
			fileReader.close();
			System.out.println("file " + file.getName() + "loaded");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void printlines() {
		for (String s : lines) {
			System.out.println(s);
		}
	}

	public static void loadSmells(List<Smell> smells) {

		try {
			File file = new File("smells.xml");
			DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = dBuilder.parse(file);
			NodeList smellList = doc.getElementsByTagName("smell");

			for (int i = 0; i < smellList.getLength(); i++) {
				Node currentNode = smellList.item(i);
				Smell smellTemp = new Smell();
				

				if (currentNode.getNodeType() == Node.ELEMENT_NODE) {

					Element repairE = (Element) currentNode;
					
					smellTemp.setId(Integer.parseInt(repairE.getElementsByTagName("id").item(0).getTextContent()));
					smellTemp.setName(repairE.getElementsByTagName("name").item(0).getTextContent());
					smellTemp.setPriority(Integer.parseInt(repairE.getElementsByTagName("priority").item(0).getTextContent()));
					
					smells.add(smellTemp);
				}
			}
		} catch (ParserConfigurationException | IOException | SAXException e) {
			e.printStackTrace();
		}

	}

	//public static void loadRepairs(List<Repair> repairs, List<Smell> smells) {
	public static void loadRepairs(List<Rule> rules) {
		try {
			File file = new File("rules.xml");
			DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = dBuilder.parse(file);
			NodeList repairList = doc.getElementsByTagName("repair");

			for (int i = 0; i < repairList.getLength(); i++) {
				Node currentNode = repairList.item(i);
				Rule rule = new Rule();
				

				if (currentNode.getNodeType() == Node.ELEMENT_NODE) {

					Element currentE = (Element) currentNode;
					rule.setId(currentE.getElementsByTagName("id").item(0).getTextContent());
					rule.setName(currentE.getElementsByTagName("name").item(0).getTextContent());
					rule.setDescription(currentE.getElementsByTagName("description").item(0).getTextContent());
					

					/*NodeList fixXML = repairE.getElementsByTagName("fix");
					for (int j = 0; j < fixXML.getLength(); j++) {
						Node fixNode = fixXML.item(j);

						if (fixNode.getNodeType() == Node.ELEMENT_NODE) {
							Element fixE = (Element) fixNode;

							RepairFix repairFixTepm = new RepairFix();
							NodeList fixsmellXML = fixE.getElementsByTagName("fixsmell");
							repairFixTepm.setSmell(smells.get(Integer.parseInt(fixsmellXML.item(0).getTextContent())));
							// System.out.println("fixsmell " +
							// fixsmellXML.item(0).getTextContent());

							NodeList priorityXML = fixE.getElementsByTagName("priority");
							repairFixTepm.setPriority(Integer.parseInt(priorityXML.item(0).getTextContent()));
							// System.out.println("priority " +
							// priorityXML.item(0).getTextContent());
							repairTemp.getFixes().add(repairFixTepm);
						}

						// repairTemp.getFixes().add(smells.get(Integer.parseInt(fixXML.item(j).getTextContent())));
						// System.out.println("fix: " +
						// fixXML.item(j).getTextContent());
					}

					NodeList causeXML = repairE.getElementsByTagName("cause");
					for (int j = 0; j < causeXML.getLength(); j++) {

						Node fixNode = causeXML.item(j);

						if (fixNode.getNodeType() == Node.ELEMENT_NODE) {
							Element causeE = (Element) fixNode;

							RepairCause repaircauseTemp = new RepairCause();
							NodeList causesmellXML = causeE.getElementsByTagName("causesmell");
							repaircauseTemp
									.setSmell(smells.get(Integer.parseInt(causesmellXML.item(0).getTextContent())));
							// System.out.println("causesmell " +
							// causesmellXML.item(0).getTextContent());

							NodeList probabilityXML = causeE.getElementsByTagName("probabilty");
							repaircauseTemp.setProbabilty(Integer.parseInt(probabilityXML.item(0).getTextContent()));
							// System.out.println("prob " +
							// probabilityXML.item(0).getTextContent());

						}

						// repairTemp.getCauses().add(smells.get(Integer.parseInt(causeXML.item(j).getTextContent())));
						// System.out.println("cause: " +
						// causeXML.item(j).getTextContent());
					}

					rules.add(rule);
				}
			}
		} catch (ParserConfigurationException | IOException | SAXException e) {
			e.printStackTrace();
		}
	}

	public static void printSmells(List<Smell> smells) {
		System.out.println("number of smells:" + smells.size());
		for (Smell smell : smells) {
			System.out.println("id:        " + smell.getId());
			System.out.println("name:      " + smell.getName());
			System.out.println("priority:  " + smell.getPriority());
			System.out.println("--------------------");
		}
		System.out.println("------smells end-------------");
	}

	public static void printrepairs(List<Repair> repairs) {
		for (Repair repair : repairs) {
			System.out.println("id          : " + repair.getId());
			System.out.println("name        : " + repair.getName());
			System.out.println("description : " + repair.getDescription());

			for (RepairCause cause : repair.getCauses()) {
				System.out.println("cause :" + cause.getSmell().getName());
				System.out.println("prob  :" + cause.getProbabilty());
				System.out.println("------------");

			}
			for (RepairFix fix : repair.getFixes()) {
				System.out.println("fix   : " + fix.getSmell().getName());
				System.out.println("prior : " + fix.getPriority());
				System.out.println("------------");

			}
			System.out.println("----next----");
		}
		System.out.println("--------repairs end----------");
	}*/

}