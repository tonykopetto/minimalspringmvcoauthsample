package com.kopetto.sample.domain.entity.profile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import com.kopetto.sample.domain.entity.BaseEntity;

/**
 * Domain Entity for user account.
 * 
 */
@SuppressWarnings("serial")
@Document(collection = "User")
public class User extends BaseEntity implements UserDetails{
    @Indexed
    private String name;
    private String password;
    
    private UserRoleType[] roles;
    
    private List<String> followConversationIds;
    
    private String email;
    
    private String displayName;
    
    private String imageUrl;
    
    private String webSite;
    
	private Date createdDt = new Date ();
	
    public User() {}
	
    public UserRoleType[] getRoles() {
        return roles;
    }

    public void setRoles(UserRoleType[] roles) {
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }


    public String getWebSiteLink(){
        if (StringUtils.hasText(getWebSite())){
            if (getWebSite().startsWith("http://") || getWebSite().startsWith("https://")){
                return getWebSite();
            }
            return "http://"+getWebSite();
            // add http:// to fix URL 
        }
        return "#";
    }

    public String getNameLink(){
        //TODO link to profile page
        return getWebSiteLink();
    }
    
    public boolean isAdmin(){
        for (UserRoleType role : getRoles()) {
            if (role == UserRoleType.ROLE_ADMIN){
                return true;
            }
        }        
        return false;
    }

    public boolean isHasImageUrl(){
        return StringUtils.hasLength(getImageUrl());
    }
    
	public Date getCreatedDt() {
		return createdDt;
	}

	public void setCreatedDt(Date createdDt) {
		this.createdDt = createdDt;
	}

	public List<String> getFollowConversationIds() {
		return followConversationIds;
	}

	public void setFollowConversationIds(List<String> followConversationIds) {
		this.followConversationIds = followConversationIds;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority> ();
		if (this.roles != null){
			for (UserRoleType role : this.roles) {
				authorities.add(new GrantedAuthorityImpl (role.getAuthority()));
			}
		}
		return authorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.name;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



}
