package dataprovider;

import java.util.List;

import entities.Repair;
import entities.SmellType;
import entities.stateSpace.State;

public interface DataProvider {
	public List<Repair> getRepairs();
	public List<SmellType> getSmellTypes();
	public State getRootState();
}
