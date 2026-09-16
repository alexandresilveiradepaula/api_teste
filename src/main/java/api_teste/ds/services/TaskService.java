//Pacote onde está a classe de serviço no projeto
package api_teste.ds.services;

//Importa List da Biblioteca padrão do Java para manipular coleções de objetos. 
import java.util.List;
//Importa Optional, usado para tratar valores que podem não estar presentes (evita NullExceptionPointer)
import java.util.Optional;


//Importa a anotação do Spring para a injeção automática de dependencias
import org.springframework.beans.factory.annotation.Autowired;
//Importa a anotação que define essa classe como um componente de serviço gerenciado pelo Spring
import org.springframework.stereotype.Service;
//Importa a anotação para greenciar transações no banco de dados(garante atomicidade na operação)
import org.springframework.transaction.annotation.Transactional;

//Importa o models.Task
import api_teste.ds.models.Task;
//Importa o models.User
import api_teste.ds.models.User;
//Importa a interface do reppositório responsável pelas operações no banco de dados
import api_teste.ds.repositories.TaskRepository;


//Anotação qie indica para o Spring que essa classe contem as regras de negócio
@Service 
public  class TaskService {

    //Injeta automaticamente a instancia do TaskRepository gerenciado pelo Spring
    @Autowired 
    private TaskRepository taskRepository;

    //Injeta automaticamente a Instancia do UserService para validar o usuário
    @Autowired 
    private UserService userService;

    //Método para buscar task apartir do ID
    public Task findById(Long Id){
        //Executa a busca no banco, retorna um Optional contendo (ou não) a Task
        Optional<Task> task = this.taskRepository.findById(Id);

        // Se a tarefa existir, retorna o objeto, se estiver vazio, lança um RunTimeException
        return task.orElseThrow(()-> new RuntimeException(
            "Tarefa não encontrada! Id:"+ Id + ",Tipo:" + Task.class.getName()
        ));
    }

    //Método para buscar todas as tarefas  vinculadas a um determinado usuario
    public List<Task> findByUserId(Long UserId){
        
        //Chama o UserService para garantir que o usuário existe no banco(lança exceção se não existir)
        this.userService.findById(UserId);

        //Executa a busca customizada no repositótio filtrando pelo id do usuario
        List<Task> tasks = this.taskRepository.findByUserId(UserId);

        //Retorna a lista de tarefas
        return tasks;
        }

        //Garante que criação ocorra dentro de uma transação de banco de dados(rolback automático se falhar)
        @Transactional 
        public Task create(Task obj){
            //Valida se o usuario informado no objeto realmente existe no banco e recupera seus dados
            User user = this.userService.findById(obj.getUser().getId());

            // Define o ID como null para garantir que o JPA realize um inserção(INSERT) e não uma atualização
            obj.setId(id:null);

            //Associa a entidade User completa e validadada a tarefa
            obj.setUser(user);

            //Salva a nva tarefa no banco de dados e atualiza 'obj' com o ID gerado
            obj = this.taskRepository.save(obj);

            //Retorna a tarfa salva
            return obj;
        }

        //Garante que a 
        @Transactional 
        public Task update(Task obj){

        }










    }





   
}




