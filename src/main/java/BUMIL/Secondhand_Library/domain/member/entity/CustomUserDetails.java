package BUMIL.Secondhand_Library.domain.member.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUserDetails implements UserDetails {

    private final MemberEntity member;

    public CustomUserDetails(MemberEntity member) {
        this.member = member;
    }

    public MemberEntity getMember() {
        return member;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null; // 필요한 경우 권한을 반환합니다.
    }

    @Override
    public String getPassword() {
        return null; // 패스워드가 없으므로 null을 반환합니다.
    }

    @Override
    public String getUsername() {
        return String.valueOf(member.getOuthId()); // outhId를 문자열로 반환합니다.
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
}
