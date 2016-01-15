package com.returnsoft.recruitment.enumeration;

public enum MonthEnum {
	
	JANUARY((short)1,"01","Enero"),
	FEBRUARY((short)2,"02","Febrero"),
	MARCH((short)3,"03","Marzo"),
	APRIL((short)4,"04","Abril"),
	MAY((short)5,"05","Mayo"),
	JUNE((short)6,"06","Junio"),
	JULY((short)7,"07","Julio"),
	AUGUST((short)8,"08","Agosto"),
	SEPTEMBER((short)9,"09","Septiembre"),
	OCTOBER((short)10,"10","Octubre"),
	NOVEMBER((short)11,"11","Noviembre"),
	DECEMBER((short)12,"12","Diciembre");
	
	private Short id;
	private String code;
	private String name;
	
	private MonthEnum(short id, String code, String name){
		this.id=id;
		this.code=code;
		this.name=name;
	}
	
	public static MonthEnum findById(Short id){
		for(MonthEnum monthEnum: MonthEnum.values()){
			if (monthEnum.getId()==id) {
				return monthEnum;
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
	
	public String getCode() {
		return code;
	}
	

}
