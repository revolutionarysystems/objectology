package uk.co.revsys.objectology.view;

import java.util.LinkedList;
import java.util.List;
import uk.co.revsys.objectology.security.SecurityConstraint;

public class View {

    private List<SecurityConstraint> securityConstraints = new LinkedList<SecurityConstraint>();

    public View() {
        
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
