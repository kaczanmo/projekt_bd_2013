package data;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "IMAGES")
public class Image  implements Serializable  {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4977748569131869076L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;
	
	@Column(name = "name", nullable=true)
	private String name;

	@Column(name = "date_cr", nullable=true)
	private Date data;
	
	@Column(name = "user_id", nullable=true)
	private Integer user_id;
	
	@Column(name = "url", nullable=true)
	private String url;


public Image() {
	super();
}

public Image(int id, String name, Date data, int user_id, String url) {
	super();
	this.id = id;
	this.name = name;
	this.data = data;
	this.user_id = user_id;
	this.url = url;

}

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public Date getData() {
	return data;
}

public void setData(Date data) {
	this.data = data;
}

public int getUser_id() {
	return user_id;
}

public void setUser_id(int user_id) {
	this.user_id = user_id;
}

public String getUrl() {
	return url;
}

public void setUrl(String url) {
	this.url = url;
}



@Override
public String toString() {
	return "Image [id=" + id + ", name=" + name + ", data=" + data
			+ ", user_id=" + user_id + ", url=" + url + "]";
}
	
	

}
