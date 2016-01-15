package com.returnsoft.recruitment.enumeration;

public enum YearEnum {
	
	Y2015((short)2015,"2015"),
	Y2016((short)2016,"2016"),
	Y2017((short)2017,"2017"),
	Y2018((short)2018,"2018"),
	Y2019((short)2019,"2019"),
	Y2020((short)2020,"2020");
	
	
	private Short id;
	private String name;
	
	private YearEnum(short id, String name){
		this.id=id;
		this.name=name;
	}
	
	public static YearEnum findById(Short id){
		for(YearEnum yearEnum: YearEnum.values()){
			if (yearEnum.getId().equals(id)) {
				return yearEnum;
			}
		}
		return null;
	}

	public Short getId() {
		return id;
	}

	

	public String getName() {
		return name;
	}
	
	
}
