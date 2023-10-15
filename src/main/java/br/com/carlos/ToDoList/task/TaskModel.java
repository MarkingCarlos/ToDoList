package br.com.carlos.ToDoList.task;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "tb_task")
public class TaskModel {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    @Column(length = 50)
    private String titulo;
    private String descricao;
    private LocalDateTime inicio;
    private LocalDateTime fim;
    private String prioridade;
    
    @CreationTimestamp
    private LocalDateTime criado;

    private UUID idUser;
}
