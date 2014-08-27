package uk.co.revsys.objectology.view;

import java.util.LinkedList;
import java.util.List;
import uk.co.revsys.objectology.security.SecurityConstraint;

public class View {

    private String name;
    private List<SecurityConstraint> securityConstraints = new LinkedList<SecurityConstraint>();

    public View() {
        
    }

    public View(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public View(List<SecurityConstraint> securityConstraints) {
        this.securityConstraints = securityConstraints;
    }

    public List<SecurityConstraint> getSecurityConstraints() {
        return securityConstraints;
    }

    public void setSecurityConstraints(List<SecurityConstraint> securityConstraints) {
        this.securityConstraints = securityConstraints;
    }
}
