package com.seating.examinationManagementSystem.serviceImpl;

import com.seating.examinationManagementSystem.repository.UserDetailsRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CustomerUserDetailService implements UserDetailsService
{

    @Autowired
    public UserDetailsRepository userDetailsRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<com.seating.examinationManagementSystem.entity.UserDetails> userList=this.userDetailsRepository.findByUserName(username);
        com.seating.examinationManagementSystem.entity.UserDetails userDetails = null;
        for(int i=0;i<userList.size();i++){
            if(userList.get(i).getUserName().equalsIgnoreCase(username)){
                userDetails= (com.seating.examinationManagementSystem.entity.UserDetails) userList.get(i);
                break;
            }
        }

        if(userDetails==null){
            throw new UsernameNotFoundException("No User Found !");
        }
        else{
            if(!userDetails.getUserName().isEmpty()){
                return new org.springframework.security.core.userdetails.User(userDetails.getUserName(),userDetails.getPassword() , getAuthority(userDetails));
            }else {
                throw new UsernameNotFoundException("User Not Approved !");
            }
        }

    }

    private Set getAuthority( com.seating.examinationManagementSystem.entity.UserDetails userDetails) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(userDetails.getRole()));
        return authorities;
    }

}