package hmap.core.hms.dto;

public class RequestDTO {
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "RequestDTO{" +
				"id='" + id + '\'' +
				'}';
	}
}
