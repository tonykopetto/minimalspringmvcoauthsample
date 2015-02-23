package com.kopetto.sample.domain.entity;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


/**Base entity
*
*/
@Document
@SuppressWarnings("serial")
public abstract class BaseEntity implements Serializable {
    @Id
    protected String id;


    @Override
    public int hashCode() {
        return (id == null) ? 0 : id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        BaseEntity other = (BaseEntity) obj;
        if (id == null) return other.id == null;
        return id.equals(other.id);
    }

	public void setId(String id) {
		this.id = id;
	}
	
    public String getId() {
        return id;
    }
    
    
	
}