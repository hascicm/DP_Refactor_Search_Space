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
		smellOccurances.add(new SmellOccurance(this.getSmellType(3)));
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
		/*smellOccurances.add(new SmellOccurance(this.getSmellType(15)));*/
		
		
		
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
