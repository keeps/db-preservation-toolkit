package pt.gov.dgarq.roda.common.convert.db.model.structure;

/**
 * 
 * @author Miguel Coutada
 *
 */

public class SIARDRoutineStructure {
	
	private String name;
	
	private String description;
	
	private String source;
	
	private String body;
	
	private String characteristic;
	
	private String returnType;
	
	private String parameters;
	
	

	/**
	 * 
	 */
	public SIARDRoutineStructure() {
	}


	/**
	 * @param name
	 * @param description
	 * @param source
	 * @param body
	 * @param characteristic
	 * @param returnType
	 * @param parameters
	 */
	public SIARDRoutineStructure(String name, String description,
			String source, String body, String characteristic,
			String returnType, String parameters) {
		this.name = name;
		this.description = description;
		this.source = source;
		this.body = body;
		this.characteristic = characteristic;
		this.returnType = returnType;
		this.parameters = parameters;
	}


	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}


	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}


	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}


	/**
	 * @return the source
	 */
	public String getSource() {
		return source;
	}


	/**
	 * @param source the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}


	/**
	 * @return the body
	 */
	public String getBody() {
		return body;
	}


	/**
	 * @param body the body to set
	 */
	public void setBody(String body) {
		this.body = body;
	}


	/**
	 * @return the characteristic
	 */
	public String getCharacteristic() {
		return characteristic;
	}


	/**
	 * @param characteristic the characteristic to set
	 */
	public void setCharacteristic(String characteristic) {
		this.characteristic = characteristic;
	}


	/**
	 * @return the returnType
	 */
	public String getReturnType() {
		return returnType;
	}


	/**
	 * @param returnType the returnType to set
	 */
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}


	/**
	 * @return the parameters
	 */
	public String getParameters() {
		return parameters;
	}


	/**
	 * @param parameters the parameters to set
	 */
	public void setParameters(String parameters) {
		this.parameters = parameters;
	}
	
	
}
