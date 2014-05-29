package uk.co.revsys.objectology.view;

public class SummaryObjectView{

	private String id;
	private String status;
	private String description;
	private String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
        public String getStatus() {
                return status;
        }

        public void setStatus(String status) {
                this.status = status;
	}
	
        public String getDescription() {
                return description;
        }

        public void setDescription(String description) {
                this.description = description;
	}
	
        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
	}
	
}
