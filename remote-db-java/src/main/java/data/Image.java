package data;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "IMAGES")
public class Image  implements Serializable  {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4977748569131869076L;

	@Id
	@GenericGenerator(name="imggen" , strategy="increment")
	@GeneratedValue(generator="imggen")
	@Column(name = "id")
	private Long id;
	
	@Column(name = "name", nullable=true)
	private String name;

	@Column(name = "date_cr", nullable=true)
	private Date date;
	
	@Column(name = "user_id", nullable=true)
	private Long user_id;
	
	@Column(name = "url", nullable=true)
	private String url;


public Image() {
	super();
}

public Image(Long id, String name, Date data, Long user_id, String url) {
	super();
	this.id = id;
	this.name = name;
	this.date = data;
	this.user_id = user_id;
	this.url = url;

}

public Image(Object[] obj) {
	this.setId((Long) obj[0]);
	this.setName((String) obj[1]);
	this.setDate((Date) obj[2]);
	this.setUser_id((Long)  obj[3]);
	this.setUrl((String) obj[4]);
}

public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public Date getDate() {
	return date;
}

public void setDate(Date date) {
	this.date = date;
}

public Long getUser_id() {
	return user_id;
}

public void setUser_id(Long user_id) {
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
	return "Image [id=" + id + ", name=" + name + ", date=" + date
			+ ", user_id=" + user_id + ", url=" + url + "]";
}
public Object[] toArray() {
	return new Object[]{id,name,date,user_id,url};
}	
	

}
