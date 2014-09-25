package uk.co.revsys.objectology.action.model;

import java.util.LinkedList;
import java.util.List;

public class CompoundAction extends AbstractAction{

    private List<Action> actions = new LinkedList<Action>();

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }
    
    public void addAction(Action action){
        this.actions.add(action);
    }
    
    @Override
    public String getNature() {
        return "compoundAction";
    }

}
