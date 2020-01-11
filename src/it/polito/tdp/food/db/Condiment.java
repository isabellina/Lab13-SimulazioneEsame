package it.polito.tdp.food.db;

public class Condiment implements Comparable<Condiment> {
	private Integer condiment_id;
	private String display_name;
	private String condiment_portion_size;
	private Double condiment_calories;
	
	public Condiment(Integer condiment_id, String display_name, String condiment_portion_size,
			Double condiment_calories) {
		super();
		this.condiment_id = condiment_id;
		this.display_name = display_name;
		this.condiment_portion_size = condiment_portion_size;
		this.condiment_calories = condiment_calories;
	}
	
	public Integer getCondiment_id() {
		return condiment_id;
	}
	public void setCondiment_id(Integer condiment_id) {
		this.condiment_id = condiment_id;
	}
	public String getDisplay_name() {
		return display_name;
	}
	public void setDisplay_name(String display_name) {
		this.display_name = display_name;
	}
	public String getCondiment_portion_size() {
		return condiment_portion_size;
	}
	public void setCondiment_portion_size(String condiment_portion_size) {
		this.condiment_portion_size = condiment_portion_size;
	}
	public Double getCondiment_calories() {
		return condiment_calories;
	}
	public void setCondiment_calories(Double condiment_calories) {
		this.condiment_calories = condiment_calories;
	}

	@Override
	public String toString() {
		return condiment_id + " - "+ display_name + " ("
				+ condiment_calories + " cal)";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((condiment_id == null) ? 0 : condiment_id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Condiment other = (Condiment) obj;
		if (condiment_id == null) {
			if (other.condiment_id != null)
				return false;
		} else if (!condiment_id.equals(other.condiment_id))
			return false;
		return true;
	}

	

	@Override
	public int compareTo(Condiment altro) {
		if(this.getCondiment_calories()<altro.getCondiment_calories()) {
			return +1;
		}
		else if(this.getCondiment_calories()>altro.getCondiment_calories()) {
			return -1;
		}
		return 0;
	}
	
	
	
}
