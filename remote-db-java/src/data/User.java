package data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "IMG_USERS")
public class User implements Serializable {
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;
	
	@Column(name = "name", nullable=true)
	private String name;
	
	@Column(name = "email", nullable=true)
	private String email;

	public User() {
		super();
	}

	public User(Long id, String name, String email) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	

}
