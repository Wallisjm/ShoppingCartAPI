package api;

public class ItemView {
	
	private String id;
	private String name;
	private String description;
	private String picture;
	
	public ItemView(String id, String name, String description, String picture) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.picture = picture;
	}
	

	/*
	 * Gets the Id
	 * 
	 * Returns Id
	 */
	public String getId() {
		return this.id;
	}
	
	/*
	 * Gets the Name
	 * 
	 * Returns Name
	 */
	public String getName() {
		return this.name;
	}
	
	/*
	 * Gets the Id
	 * 
	 * Returns Id
	 */
	public String getDescription() {
		return this.description;
	}
	
	/*
	 * Gets the Picture
	 * 
	 * Returns Picture
	 */
	public String getPicture() {
		return this.picture;
	}
	
	/*
	 * Checks if two ItemViews are equal
	 * 
	 * Returns True if equal and False if not
	 */
	public boolean equals(Object obj) {
		ItemView itemView;
		if (obj instanceof ItemView) {
			itemView = (ItemView) obj;
		} else {
			return false;
		}
		
		if (!this.getId().equals(itemView.getId())) {
			return false;
		}
		
		if (!this.getName().equals(itemView.getName())) {
			return false;
		}
		
		if (!this.getDescription().equals(itemView.getDescription())) {
			return false;
		}
		
		
		if (!this.getPicture().equals(itemView.getPicture())) {
			return false;
		}
		return true;
		
	}
	
	
}
