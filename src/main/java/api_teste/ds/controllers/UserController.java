package api_teste.ds.controllers;



import java.net.URI; // Importa a classe URI para construir e manipular HTTP de novos recursos

import org.springframework.beans.factory.annotation.Autowired; // injção automatica do Spring
import org.springframework.http.ResponseEntity; // impora a classe para montar a resposta HTTP completa(status, headers, corpo)
import org.springframework.validation.annotation.Validated; // importa anotação para habiltar suporte a validação no controller
import org.springframework.web.bind.annotation.DeleteMapping; // mapeia requisições do tipo delete
import org.springframework.web.bind.annotation.GetMapping; // mapeia requisições do tipo GET
import org.springframework.web.bind.annotation.PathVariable; // mapeia variaveis passadas diretamente via caminho da URL
import org.springframework.web.bind.annotation.PostMapping; // mapeia requisições do tipo POST
import org.springframework.web.bind.annotation.PutMapping; // mapeia requisições do tipo PUT
import org.springframework.web.bind.annotation.RequestBody; // converste objetos JSON em ojetos JAVA
import org.springframework.web.bind.annotation.RequestMapping; // importa anotação para definir o caminho/rota bas do controlador
import org.springframework.web.bind.annotation.RestController; //  importa anotação que define esta classe como um controller REST
import org.springframework.web.servlet.support.ServletUriComponentsBuilder; // Importa utilitario para gerar a URI da requisição atual dinamicamente.

import api_teste.ds.models.User;
import api_teste.ds.models.User.CreateUser;
import api_teste.ds.models.User.UpdateUser;
import api_teste.ds.services.UserService;
import org.springframework.web.bind.annotation.RequestParam;


@RestController  // Define a classe como um controlador REST que retorna respostas em JSON
@RequestMapping ("/user") // Define que toas as rotas desta classe terão como prefixo o caminho "/user"
@Validated //Ativa a verificação de validações nos parametros recebidos no controller

public class UserController {

    @Autowired 
    private UserService userService;

    @GetMapping ("/{id}") // Mapeia requisições HTTP GET na rota "/user/{id}"
    public ResponseEntity<User> findById(@PathVariable Long Id){ // Método para buscar usuario por id capturado da URL
        User obj=this.userService.findById(Id); // Invoca a busca do usuário através do ID recebido
        return ResponseEntity.ok().body(obj) // Retorn código HTTP 200(ok) com o onjeto User no corpo da resposta
    } // Fim do método FindbyId

    @PostMapping 
    public ResponseEntity<Void> create(@Validated (CreateUser.class) @RequestBody User obj){
        this.userService.create(obj);
        URI url = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}").buildAndExpand(obj.getId()).toUri();
            return ResponseEntity.created(url).build();
    }

    

    }
    

