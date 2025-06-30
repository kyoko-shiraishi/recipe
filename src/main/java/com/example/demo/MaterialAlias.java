package com.example.demo;
import jakarta.persistence.Column; 
import jakarta.persistence.Entity; 
import jakarta.persistence.GeneratedValue; 
import jakarta.persistence.GenerationType; 
import jakarta.persistence.Id; 
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne; //追記
import jakarta.persistence.JoinColumn;
@Entity
@Table(name="material_aliases")
public class MaterialAlias {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="material_id")
	private Mate mate;
	
	@Column
	private String aliasName;
	 public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }

	    public Mate getMate() {
	        return mate;
	    }

	    public void setMate(Mate mate) {
	        this.mate = mate;
	    }

	    public String getAliasName() {
	        return aliasName;
	    }

	    public void setAliasName(String aliasName) {
	        this.aliasName = aliasName;
	    }
}
