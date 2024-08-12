package BUMIL.Secondhand_Library.service;

import BUMIL.Secondhand_Library.domain.member.entity.CustomUserDetails;
import BUMIL.Secondhand_Library.domain.member.entity.MemberEntity;
import BUMIL.Secondhand_Library.domain.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        throw new UsernameNotFoundException("This method is not supported. Use loadUserByOuthId instead.");
    }

    public UserDetails loadUserByOuthId(Long outhId) throws UsernameNotFoundException {
        MemberEntity optionalMember = memberRepository.findByOuthId(outhId);
        MemberEntity member = optionalMember;
        return new CustomUserDetails(member);
    }
}