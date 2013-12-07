package data;

import java.io.Serializable;
import java.sql.Blob;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	
	@Column(name = "img_data", nullable=true)
	private byte[] imgData;


public Image() {
	super();
}

public Image(Long id, String name, Date data, User user, byte[] imgData) {
	super();
	this.id = id;
	this.name = name;
	this.date = data;
	this.user = user;
	this.imgData = imgData;

}

public Image(Object[] obj) {
	this.setId((Long) obj[0]);
	this.setName((String) obj[1]);
	this.setDate((Date) obj[2]);
	this.setUser((User)  obj[3]);
	this.setImgData((byte[]) obj[4]);
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

public User getUser() {
	return user;
}

public void setUser(User user) {
	this.user = user;
}


public byte[] getImgData() {
	return imgData;
}

public void setImgData(byte[] imgData) {
	this.imgData = imgData;
}


@Override
public String toString() {
	return "Image [id=" + id + ", name=" + name + ", date=" + date
			+ ", user_id=" + user /*+ ", imgBlob=" + imgBlob*/ + "]";
}
public Object[] toArray() {
	return new Object[]{id,name,date,user,imgData};
}	
	

}
