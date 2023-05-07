package com.gotocode.injecaodedependencias;

//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;


public class InjecaoDeDependenciasApplication {


	public static void main(String[] args) {
		//SpringApplication.run(InjecaoDeDependenciasApplication.class, args);
		// Usando Inversão de controle IoC
		// Se for trocar o FileReader para um DbReader, muda apenas em quem chama
		// o resto do código continua funcionando sem alteracão.
		new MigracaoUsuario(
				new FileReader(),
				new DbWriter()
		).migrar();
	}

}

class MigracaoUsuario {
	// MigracaoUsuario depende do FileReader e do FileWriter
	// Depende dos detalhes da implementacão (Auto acoplamento)
	// resolvendo com uso de abstracoes com uso de interface

	// As dependência está liga agora pela interface e não pela implementacões
	// Problema ainda sabe detalhes das operacões de leitua e escrita
	// Resolvendo usando a inversão de controle IoC
	Reader<User> reader;
	Writer<User> writer;

	// Criar um construtor
	public MigracaoUsuario(Reader<User> reader, Writer<User> writer) {
		// Fica desacoplado
		this.reader = reader;
		this.writer = writer;
	}

	void migrar() {
		// Criar o leitor e o escritor, criar os dois conceitos e
		// Separar a lógica para ter separacão de responsabilidades

		// Ler usuário de A
		List<User> user = reader.read();
		// Escrever usuário em B
		writer.write(user);
	}
}
record User(String email, String username, String password) {

}

interface Reader<T> {
	//Tirar a dependência de User
	//List<User> read();
	//Usando generics
	List<T> read();

}

interface Writer<T> {
	void write(List<T> itens);
}

class FileReader implements Reader<User>{
	public List<User> read() {
		// Foco em trabalhar com dependências de software
		System.out.println("Lendo usuários do arquivo...");
		return List.of(new User("email", "username", "password"));
	}
}

class DbWriter implements Writer<User> {
	public void write(List<User> users) {
		System.out.println("Escrevendo usuários no banco...");
		System.out.println(users);
	}
}