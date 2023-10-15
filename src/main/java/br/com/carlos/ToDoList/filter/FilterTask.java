package br.com.carlos.ToDoList.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.carlos.ToDoList.user.IUserRepository;
import br.com.carlos.ToDoList.user.UserModel;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class FilterTask extends OncePerRequestFilter {

    @Autowired
    private IUserRepository userRepository;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
    throws ServletException, IOException {

        var authrization = request.getHeader("Authorization");

        var enco = authrization.substring("Basic".length(), 0).trim();

        byte[] decode = Base64.getDecoder().decode(enco);
        
        var StringDecode = new String(decode);

        String[] credenciais = StringDecode.split(":");
        String user = credenciais[0];
        String senha = credenciais[1];
        var ValidUser = this.userRepository.findByUsername(user);
        if(ValidUser == null){
            response.sendError(401, "Usuario sem autorização");
        }else{
            UserModel userModel;
            var result = BCrypt.verifyer().verify(senha.toCharArray(),ValidUser.getPassword());
            if(result.verified){
                filterChain.doFilter(request, response);
            }else{
                response.sendError(401, senha);
            }
            
        }

    }

   
    
}
