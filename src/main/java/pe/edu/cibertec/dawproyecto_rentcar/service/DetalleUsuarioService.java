package pe.edu.cibertec.dawproyecto_rentcar.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pe.edu.cibertec.dawproyecto_rentcar.model.bd.Rol;
import pe.edu.cibertec.dawproyecto_rentcar.model.bd.Usuario;
import pe.edu.cibertec.dawproyecto_rentcar.model.dto.security.UsuarioSecurity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Service
public class DetalleUsuarioService implements UserDetailsService {
    private IUsuarioService iUsuarioService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = iUsuarioService.findUserByNomAdmin(username);
        return autenticacionUsuario(usuario,
                obtenerListaRolesUsuario(usuario.getRoles()));
    }
    private List<GrantedAuthority> obtenerListaRolesUsuario(Set<Rol> listRoles){
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        for (Rol rol : listRoles){
            grantedAuthorityList.add(new SimpleGrantedAuthority(rol.getNomrol()));
        }
        return grantedAuthorityList;
    }
    private UserDetails autenticacionUsuario(
            Usuario usuario, List<GrantedAuthority> authorityList
    ){
        UsuarioSecurity usuarioSecurity = new UsuarioSecurity(
                usuario.getNomadmin(),
                usuario.getContrasena(),usuario.getActivo(),
                true, true,
                true, authorityList
        );
        usuarioSecurity.setEmail(usuario.getEmail());
        usuarioSecurity.setNombre(usuario.getNombres());
        return usuarioSecurity;
    }
}
