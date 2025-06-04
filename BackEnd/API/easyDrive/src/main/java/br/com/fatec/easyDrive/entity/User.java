package br.com.fatec.easyDrive.entity;

import java.util.Collection;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.fatec.easyDrive.enumerator.UserRole;

@SuppressWarnings("serial")
@Entity
@Table(name = "tb_users")
@Getter
@Setter
public class User implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "username")
	private String username;

	@Column(name = "password")
	private String password;
	
	@Column(name = "role")
    private UserRole role;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == UserRole.ADMIN) 
        	return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), 
        		new SimpleGrantedAuthority("ROLE_USER"));
        else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
	}

}
