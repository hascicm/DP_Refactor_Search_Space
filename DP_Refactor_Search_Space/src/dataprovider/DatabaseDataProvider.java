package dataprovider;

import java.util.ArrayList;
import java.util.List;

import dataprovider.dbsManager.PostgresManager;
import entities.Repair;
import entities.SmellType;
import entities.stateSpace.SmellOccurance;
import entities.stateSpace.State;

public class DatabaseDataProvider implements DataProvider {

	private List<Repair> repairs = null;
	private List<SmellType> smells = null;
	private State root;

	public DatabaseDataProvider() {
		smells = PostgresManager.getInstance().getSmellTypes();
		repairs = PostgresManager.getInstance().getRepairs(smells);
		initRoot();
	}

	@Override
	public List<Repair> getRepairs() {
		return repairs;
	}

	@Override
	public List<SmellType> getSmellTypes() {
		return smells;
	}

	@Override
	public State getRootState() {
		return root;
	}

	private void initRoot(){
		
		List<SmellOccurance> smellOccurances = new ArrayList<SmellOccurance>();
				
		//DataClumps
		smellOccurances.add(new SmellOccurance(this.getSmellType(4)));
		//LazyClass
		smellOccurances.add(new SmellOccurance(this.getSmellType(9)));
		//FeatureEnvy
		smellOccurances.add(new SmellOccurance(this.getSmellType(10)));
		//LongParameterList
		/*smellOccurances.add(new SmellOccurance(this.getSmellType(3)));
		//DataClass	
		smellOccurances.add(new SmellOccurance(this.getSmellType(8)));
		//Large class
		smellOccurances.add(new SmellOccurance(this.getSmellType(1)));
		//Large class
		smellOccurances.add(new SmellOccurance(this.getSmellType(1)));
		//Divergent change
		smellOccurances.add(new SmellOccurance(this.getSmellType(13)));
		//Feature envy
		smellOccurances.add(new SmellOccurance(this.getSmellType(10)));
		//Switch statement
		smellOccurances.add(new SmellOccurance(this.getSmellType(15)));
		//Large Class
		smellOccurances.add(new SmellOccurance(this.getSmellType(1)));
		//DataClumps
		smellOccurances.add(new SmellOccurance(this.getSmellType(4)));
		//Large Class
		smellOccurances.add(new SmellOccurance(this.getSmellType(1)));
		//Shotgun Surgery
		smellOccurances.add(new SmellOccurance(this.getSmellType(14)));
		//Switch statement
		smellOccurances.add(new SmellOccurance(this.getSmellType(15)));
		
		//-----------15----------------------
		//middle man
		smellOccurances.add(new SmellOccurance(this.getSmellType(21)));
		//message chain
		smellOccurances.add(new SmellOccurance(this.getSmellType(22)));
		//switch statement
		smellOccurances.add(new SmellOccurance(this.getSmellType(15)));
		//Feature envy
		smellOccurances.add(new SmellOccurance(this.getSmellType(10)));
		//divergent changes
		smellOccurances.add(new SmellOccurance(this.getSmellType(13)));
		
		//-----------------20-----------------------
		
		//long method
		smellOccurances.add(new SmellOccurance(this.getSmellType(2)));
		//data clumps
		smellOccurances.add(new SmellOccurance(this.getSmellType(4)));
		//shotgun surgery
		smellOccurances.add(new SmellOccurance(this.getSmellType(14)));
		//large class
		smellOccurances.add(new SmellOccurance(this.getSmellType(1)));
		//lazy class
		smellOccurances.add(new SmellOccurance(this.getSmellType(9)));
		
		//------------------25---------------------------------------
		//long parameter list
		smellOccurances.add(new SmellOccurance(this.getSmellType(3)));
		//large class
		smellOccurances.add(new SmellOccurance(this.getSmellType(1)));
		//divergent change
		smellOccurances.add(new SmellOccurance(this.getSmellType(13)));
		//data class
		smellOccurances.add(new SmellOccurance(this.getSmellType(8)));
		//middle man
		smellOccurances.add(new SmellOccurance(this.getSmellType(21)));
		
		
		//--------------------30------------------------------------
		//data class
		smellOccurances.add(new SmellOccurance(this.getSmellType(8)));
		//lazy class
		smellOccurances.add(new SmellOccurance(this.getSmellType(9)));
		//large class
		smellOccurances.add(new SmellOccurance(this.getSmellType(1)));
		//long method
		smellOccurances.add(new SmellOccurance(this.getSmellType(2)));
		//long parameter list
		smellOccurances.add(new SmellOccurance(this.getSmellType(3)));
		
			
		//---------------------35--------------------------------------
		
		//feature envy
		smellOccurances.add(new SmellOccurance(this.getSmellType(10)));
		//divergent change
		smellOccurances.add(new SmellOccurance(this.getSmellType(13)));
		//shotgun surgery
		smellOccurances.add(new SmellOccurance(this.getSmellType(14)));
		//switch statement
		smellOccurances.add(new SmellOccurance(this.getSmellType(15)));
		//middle man
		smellOccurances.add(new SmellOccurance(this.getSmellType(21)));
		
		//---------------------40---------------------------------------
		*/
		
		this.root = new State();
		this.root.setSmells(smellOccurances);	
	}
	
	private SmellType getSmellType(int id){
		
		SmellType result = null;
		
		for(SmellType st : this.smells){
			if(st.getId() == id){
				result = st;
			}
		}
		
		return result;
	}
	
}
