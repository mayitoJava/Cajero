package AVilchis.ProgramacionNCapasNoviembre25.Service;

import AVilchis.ProgramacionNCapasNoviembre25.DAO.UsuarioRepository;
import AVilchis.ProgramacionNCapasNoviembre25.JPA.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
    
    @Autowired
    private UsuarioRepository repo;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        Usuario user = repo.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("Usuario no encontrado"));
        
        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
    }
}
